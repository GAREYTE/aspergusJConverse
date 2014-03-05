package fr.jg.aspergus.conversation.main;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import net.sf.jconverse.components.ActionEvent;
import net.sf.jconverse.components.ActionListener;
import net.sf.jconverse.components.Button;
import net.sf.jconverse.components.containers.WPage;
import net.sf.jconverse.components.containers.WPanel;
import net.sf.jconverse.conversation.SecureConversation;
import net.sf.jconverse.conversation.listeners.ConversationListener;
import net.sf.jconverse.conversation.transitions.BasicActions;
import net.sf.jconverse.conversation.transitions.Transition;
import net.sf.jconverse.crud.ListParameters;
import net.sf.jconverse.crud.ListParameters.SearchMode;
import net.sf.jconverse.crud.ParameterConversation;
import net.sf.jconverse.crud.ViewParamerters;
import net.sf.jconverse.crud.storage.BaseTransaction;
import net.sf.jconverse.crud.storage.DataAccessLayer;
import net.sf.jconverse.crud.storage.condition.SearchFilter;
import net.sf.jconverse.extensions.conversation.AbstractConversation;
import net.sf.jconverse.extensions.conversation.RootConversation;
import net.sf.jconverse.extensions.conversation.VersionConversation;
import net.sf.jconverse.laf.styles.Styles;
import net.sf.jconverse.laf.styles.StylesColor;
import fr.jg.aspergus.AddParameters;
import fr.jg.aspergus.AspergusFactory;
import fr.jg.aspergus.CreateDatabase;
import fr.jg.aspergus.UpdateDatabase;
import fr.jg.aspergus.domain.Categorie;
import fr.jg.aspergus.domain.Commande;
import fr.jg.aspergus.domain.Commande.Statut;
import fr.jg.aspergus.domain.Mailing;
import fr.jg.aspergus.domain.Produit;
import fr.jg.aspergus.domain.VenteDetail;
import fr.jg.aspergus.presentation.AspergusBundle;

public class MainConversation extends AbstractConversation implements SecureConversation, RootConversation {

  @Override
  public Transition start() {
    return start(null);
  }

  @Override
  public Transition start(final String... argv) {

    WPage menu = new WPage(AspergusBundle.application);

    addCommandePanel(menu);
    addClientPanel(menu);
    addVentePanel(menu);

    WPanel admin = new WPanel("Paramétrage");

    admin.addButton(new Button(AspergusBundle.gestionParametres, new ParameterConversation(AspergusFactory.getInstance(false),
        BasicActions.Start), AspergusFactory.admin_role));

    boolean autoriserModificationDeBaseEnWeb = true;

    if (autoriserModificationDeBaseEnWeb) {
      admin.addButton(new Button(AspergusBundle.creerBase, new ActionListener() {

        @Override
        public Transition actionPerformed(ActionEvent event) throws Exception {

          CreateDatabase.run(event.getStatusMessage());
          AddParameters.run(event.getStatusMessage());

          return BasicActions.Start;
        }
      }, AspergusFactory.admin_role).addStyle(Styles.LINE_BREAK_BEFORE));

      admin.addButton(new Button(AspergusBundle.majBase, new ActionListener() {

        @Override
        public Transition actionPerformed(ActionEvent event) throws Exception {

          UpdateDatabase.run(event.getStatusMessage());
          return BasicActions.Start;
        }
      }, AspergusFactory.admin_role));
    }

    admin.addButton(new Button(AspergusBundle.showAdmin, new AdminConversation(BasicActions.Start), AspergusFactory.admin));

    admin.addButton(new Button(AspergusBundle.showVersion, new VersionConversation(BasicActions.Start)));
    menu.add(admin);

    return menu;
  }

  private void addClientPanel(WPage menu) {
    WPanel panel = new WPanel("Clients");

    panel.addButton(new Button(AspergusBundle.ajoutClient, ListParameters
        .create(AspergusFactory.getInstance(false), fr.jg.aspergus.domain.Client.class).setAllowAdd(true).setAllowEdit(true)
        .setAllowRemove(true).setSearchMode(SearchMode.FILTER).setExitListener(BasicActions.Start).createConversation(),
        AspergusFactory.user_role));

    panel.addButton(new Button(AspergusBundle.creerMailing, ListParameters
        .create(AspergusFactory.getInstance(false), Mailing.class).setAllowAdd(true).setAllowEdit(true).setAllowRemove(true)
        .setSearchMode(SearchMode.FILTER).setExitListener(BasicActions.Start).createConversation(), AspergusFactory.user_role));

    menu.add(panel);
  }

  private void addVentePanel(WPage menu) {

    final WPanel panel = new WPanel("Ventes directes au magasin");
    panel.addComment("Saisir une vente au magasin (Client de passage)");
    AspergusFactory.getInstance(false).execute(false, new BaseTransaction() {

      @Override
      protected void execute(DataAccessLayer t) throws Exception {
        List<StylesColor> styles = new ArrayList<StylesColor>();
        styles.add(StylesColor.BG_Lightblue);
        styles.add(StylesColor.BG_Lightcyan);
        styles.add(StylesColor.BG_Lightskyblue);
        styles.add(StylesColor.BG_Lightgreen);

        Iterator<StylesColor> it = styles.iterator();
        for (Categorie cat : t.find(Categorie.class)) {
          WPanel panelCat = new WPanel(cat.getNom());
          Produit filterProduit = new Produit();
          filterProduit.setCategorie(cat);
          List<Produit> produits = t.findSome(filterProduit);
          StylesColor nextColor = it.next();
          for (Produit produit : produits) {
            VenteDetail filterVente = new VenteDetail();
            filterVente.setProduit(produit);
            filterVente.setQuantite(BigDecimal.ONE);
            filterVente.update();
            ViewParamerters<VenteDetail> params = ViewParamerters.create(AspergusFactory.getInstance(false), filterVente);
            params.setStartInEditMode(true);
            Button button = new Button(produit.getDescription(), params.createConversation(), AspergusFactory.user_role);
            panelCat.addButton(button).addStyle(nextColor);
          }
          panel.add(panelCat);
        }

      }
    });
    menu.add(panel);
  }

  private void addStatsPanel(WPage menu) {
    WPanel panel = new WPanel("Stats");
    panel.addButton(new Button(AspergusBundle.ajoutCommande, ListParameters
        .create(AspergusFactory.getInstance(false), Commande.class).setAllowAdd(true).setAllowEdit(true).setAllowRemove(true)
        .setSearchMode(SearchMode.SEARCH).setExitListener(BasicActions.Start).createConversation(), AspergusFactory.user_role)
        .addStyle(Styles.DOUBLE_SIZE));

    menu.add(panel);
  }

  private void addCommandePanel(WPage menu) {
    WPanel panel = new WPanel("Commandes");
    panel.addComment("Saisir une commande passée par un client régulier");
    final Commande commande = new Commande();
    commande.init(AspergusFactory.getInstance(false).getPermanentReadOnlyDataAccessLayer());
    commande.setStatut(Statut.Commandee);
    final ListParameters<Commande> listParameter = ListParameters.create(AspergusFactory.getInstance(false), Commande.class)
        .setAllowEdit(true).setAllowRemove(true).setSearchMode(SearchMode.FILTER).setExitListener(BasicActions.Start);
    panel.addButton(new Button(AspergusBundle.listerCommandes, listParameter.createConversation(), AspergusFactory.user_role));

    ViewParamerters<Commande> params = ViewParamerters.create(AspergusFactory.getInstance(false), commande);
    params.setStartInEditMode(true);
    params.setExitListener(new ConversationListener() {

      @Override
      public Transition exit() {
        // TODO Auto-generated method stub
        listParameter.setSelection(commande);
        return listParameter.createConversation();
      }
    });
    panel.addButton(new Button(AspergusBundle.ajoutCommande, params.createConversation(), AspergusFactory.user_role));

    SearchFilter<Commande> sf = SearchFilter.create(Commande.class);
    sf.and("src.statut is null or src.statut!='" + Statut.Livree + "'");
    sf.orderBy("src.dateLivraison", "src.dateCommande");
    final ListParameters<Commande> listNonLivreeParam = ListParameters.create(AspergusFactory.getInstance(false), Commande.class)
        .setAllowEdit(true).setAllowRemove(true).setFfilter(sf).setSearchMode(SearchMode.FILTER)
        .setExitListener(BasicActions.Start);
    panel.addButton(new Button(AspergusBundle.listerNonLivrees, listNonLivreeParam.createConversation(),
        AspergusFactory.user_role));

    menu.add(panel);
  }
}