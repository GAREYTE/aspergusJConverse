package net.jconverse.template.presentation;

import java.util.Locale;

import net.jconverse.template.TemplateFactory;
import net.sf.jconverse.crud.CrudBundle;
import net.sf.jconverse.laf.lang.Common;
import net.sf.jconverse.laf.lang.FR;
import net.sf.jconverse.laf.lang.InternationalizedCommand;
import net.sf.jconverse.laf.lang.InternationalizedText;
import net.sf.jconverse.laf.lang.LBundle;
import net.sf.jconverse.laf.lang.Langage;
import net.sf.jconverse.laf.lang.Langages;

public enum TemplateBundle implements InternationalizedText, InternationalizedCommand, LBundle {

  back(new FR("Retour")),
  menu(new FR("Menu")),
  start(new FR("Démarrer")),

  showAdmin(new FR("Administration")),

  application(new FR("Application")),
  ajoutCommande(new FR("Ajouter commande", "Permet d'ajouter une commande")),
  ajoutClient(new FR("Ajouter / lister clients", "Permet de voir la liste et d'ajouter des clients")),
  ajoutVente(new FR("Ajouter vente", "Ajoute une vente")),

  gestionParametres(new FR("Paramètres")),

  showVersion(new FR("Version")),
  chooseCommand(new FR("Retour")),
  newDossier(new FR("Nouveau")),
  searchDossier(new FR("Rechercher")),
  choose(new FR("Choisir")),

  creerBase(new FR("Créer/Réinitialiser la base")),
  majBase(new FR("Mettre à jour la base")),

  dossierClos(new FR("Dossier clos")),
  dossierOuverts(new FR("Dossier Ouverts")),
  test(new FR("Test")),
  testAjaxPojo(new FR("Recherche Ajax Pojo")),
  listEntitesNonPersistantes(new FR("ListConversation avec entités non persistantes")),

  consulter(new FR("Consulter")),
  description(new FR("Description")),
  autresDetails(new FR("Autres détails")),
  listerCommandes(new FR("Lister commandes", "Permet de lister les commandes")),
  listerNonLivrees(new FR("Non Livrées", "Liste les commandes non livrées"));

  // -----------------------------------------------------------
  //                  Common definitions
  // -----------------------------------------------------------

  protected static final Common common = new Common(TemplateBundle.class);

  static {

    try {
      common.add(CrudBundle.class);
      common.add(TemplateFactory.getInstance(false).getIgnoreClassesPrefix(), TemplateFactory.getInstance(false)
          .getEntitiesList());

      //    common.add(Poste.class, "Poste");
      //    common.add(Dossier.class, "Bon de commande");
    } catch (Throwable e) {
      e.printStackTrace();
    }
  }

  // -----------------------------------------------------------
  //                  Static definitions
  // -----------------------------------------------------------

  private final Langages langages;

  TemplateBundle(final Langage text, final Langage... texts) {
    this.langages = new Langages(text, texts);
  }

  @Override
  public Langages getLangages() {
    return this.langages;
  }

  @Override
  public Langage getLangage(Locale locale, Object... parameters) {
    return this.langages.get(locale, parameters);
  }

  @Override
  public Common getCommon() {
    return common;
  }
}
