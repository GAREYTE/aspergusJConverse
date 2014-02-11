package net.jconverse.template.conversation.main;

import net.jconverse.template.AddParameters;
import net.jconverse.template.CreateDatabase;
import net.jconverse.template.TemplateFactory;
import net.jconverse.template.UpdateDatabase;
import net.jconverse.template.domain.Commande;
import net.jconverse.template.domain.Commande.Statut;
import net.jconverse.template.domain.Mailing;
import net.jconverse.template.presentation.TemplateBundle;
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
import net.sf.jconverse.crud.storage.condition.SearchFilter;
import net.sf.jconverse.extensions.conversation.AbstractConversation;
import net.sf.jconverse.extensions.conversation.RootConversation;
import net.sf.jconverse.extensions.conversation.VersionConversation;
import net.sf.jconverse.laf.styles.Styles;

public class MainConversation extends AbstractConversation implements SecureConversation, RootConversation {

  @Override
  public Transition start() {
    return start(null);
  }

  @Override
  public Transition start(final String... argv) {

    WPage menu = new WPage(TemplateBundle.application);

    addCommandePanel(menu);
    addClientPanel(menu);
    addVentePanel(menu);

    WPanel admin = new WPanel("Paramétrage");

    admin.addButton(new Button(TemplateBundle.gestionParametres, new ParameterConversation(TemplateFactory.getInstance(false),
        BasicActions.Start), TemplateFactory.admin_role));

    boolean autoriserModificationDeBaseEnWeb = true;

    if (autoriserModificationDeBaseEnWeb) {
      admin.addButton(new Button(TemplateBundle.creerBase, new ActionListener() {

        @Override
        public Transition actionPerformed(ActionEvent event) throws Exception {

          CreateDatabase.run(event.getStatusMessage());
          AddParameters.run(event.getStatusMessage());

          return BasicActions.Start;
        }
      }, TemplateFactory.admin_role).addStyle(Styles.LINE_BREAK_BEFORE));

      admin.addButton(new Button(TemplateBundle.majBase, new ActionListener() {

        @Override
        public Transition actionPerformed(ActionEvent event) throws Exception {

          UpdateDatabase.run(event.getStatusMessage());
          return BasicActions.Start;
        }
      }, TemplateFactory.admin_role));
    }

    admin.addButton(new Button(TemplateBundle.showAdmin, new AdminConversation(BasicActions.Start), TemplateFactory.admin));

    admin.addButton(new Button(TemplateBundle.showVersion, new VersionConversation(BasicActions.Start)));
    menu.add(admin);

    return menu;
  }

  private void addClientPanel(WPage menu) {
    WPanel panel = new WPanel("Clients");

    panel.addButton(new Button(TemplateBundle.ajoutClient, ListParameters
        .create(TemplateFactory.getInstance(false), net.jconverse.template.domain.Client.class).setAllowAdd(true)
        .setAllowEdit(true).setAllowRemove(true).setSearchMode(SearchMode.FILTER).setExitListener(BasicActions.Start)
        .createConversation(), TemplateFactory.user_role));

    panel.addButton(new Button(TemplateBundle.creerMailing, ListParameters
        .create(TemplateFactory.getInstance(false), Mailing.class).setAllowAdd(true).setAllowEdit(true).setAllowRemove(true)
        .setSearchMode(SearchMode.FILTER).setExitListener(BasicActions.Start).createConversation(), TemplateFactory.user_role));

    menu.add(panel);
  }

  private void addVentePanel(WPage menu) {
    WPanel panel = new WPanel("Ventes directes au magasin");
    panel.addComment("Saisir une vente au magasin (Client de passage)");
    panel.addButton(new Button(TemplateBundle.ajoutVente, ListParameters
        .create(TemplateFactory.getInstance(false), Commande.class).setAllowAdd(true).setAllowEdit(true).setAllowRemove(true)
        .setSearchMode(SearchMode.FILTER).setExitListener(BasicActions.Start).createConversation(), TemplateFactory.user_role)
        .addStyle(Styles.DOUBLE_SIZE));

    menu.add(panel);
  }

  private void addStatsPanel(WPage menu) {
    WPanel panel = new WPanel("Stats");
    panel.addButton(new Button(TemplateBundle.ajoutCommande, ListParameters
        .create(TemplateFactory.getInstance(false), Commande.class).setAllowAdd(true).setAllowEdit(true).setAllowRemove(true)
        .setSearchMode(SearchMode.SEARCH).setExitListener(BasicActions.Start).createConversation(), TemplateFactory.user_role)
        .addStyle(Styles.DOUBLE_SIZE));

    menu.add(panel);
  }

  private void addCommandePanel(WPage menu) {
    WPanel panel = new WPanel("Commandes");
    panel.addComment("Saisir une commande passée par un client régulier");
    final Commande commande = new Commande();
    commande.init(TemplateFactory.getInstance(false).getPermanentReadOnlyDataAccessLayer());
    commande.setStatut(Statut.Commandee);
    final ListParameters<Commande> listParameter = ListParameters.create(TemplateFactory.getInstance(false), Commande.class)
        .setAllowEdit(true).setAllowRemove(true).setSearchMode(SearchMode.FILTER).setExitListener(BasicActions.Start);
    panel.addButton(new Button(TemplateBundle.listerCommandes, listParameter.createConversation(), TemplateFactory.user_role));

    ViewParamerters<Commande> params = ViewParamerters.create(TemplateFactory.getInstance(false), commande);
    params.setStartInEditMode(true);
    params.setExitListener(new ConversationListener() {

      @Override
      public Transition exit() {
        // TODO Auto-generated method stub
        listParameter.setSelection(commande);
        return listParameter.createConversation();
      }
    });
    panel.addButton(new Button(TemplateBundle.ajoutCommande, params.createConversation(), TemplateFactory.user_role));

    SearchFilter<Commande> sf = SearchFilter.create(Commande.class);
    sf.and("src.statut is null or src.statut!='" + Statut.Livree + "'");
    sf.orderBy("src.dateLivraison", "src.dateCommande");
    final ListParameters<Commande> listNonLivreeParam = ListParameters.create(TemplateFactory.getInstance(false), Commande.class)
        .setAllowEdit(true).setAllowRemove(true).setFfilter(sf).setSearchMode(SearchMode.FILTER)
        .setExitListener(BasicActions.Start);
    panel.addButton(new Button(TemplateBundle.listerNonLivrees, listNonLivreeParam.createConversation(),
        TemplateFactory.user_role));

    menu.add(panel);
  }
}