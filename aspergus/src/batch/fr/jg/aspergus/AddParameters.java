package fr.jg.aspergus;

import java.math.BigDecimal;
import java.util.ArrayList;

import fr.jg.aspergus.domain.Categorie;
import fr.jg.aspergus.domain.Client;
import fr.jg.aspergus.domain.Produit;
import fr.jg.aspergus.domain.Salarie;
import net.sf.cristaltools.tools.text.Message;
import net.sf.jconverse.crud.storage.BaseTransaction;
import net.sf.jconverse.crud.storage.DataAccessLayer;
import net.sf.jconverse.crud.storage.EJBBaseFactory.DdlMode;
import net.sf.jconverse.crud.storage.exceptions.PersistenceException;

public class AddParameters {

  public static void main(final String[] argv) {

    run(new Message());
  }

  public static void run(final Message message) {
    try {
      AspergusFactory.getInstance(false).setDdlMode(DdlMode.none);
      AspergusFactory.getInstance(false).initialize(new Message());

      AspergusFactory.getInstance(false).execute(false, new BaseTransaction() {

        @Override
        protected void execute(final DataAccessLayer t) throws Exception {
          setupParameters(t, message);
        }

      });

      AspergusFactory.getInstance(false).shutdown();

    } catch (Exception e) {
      net.sf.cristaltools.log.Log.getCurrent().error("", e);
      message.addError("Impossible de rajouter les paramètres", e);

    }
  }

  protected static void setupParameters(final DataAccessLayer t, final Message message) throws PersistenceException {
    if (t.size(Salarie.class) == 0) {
      Salarie sal = new Salarie();
      sal.setNom("Nicole");
      t.add(sal);
      sal = new Salarie();
      sal.setNom("Fabrice");
      t.add(sal);
      sal = new Salarie();
      sal.setNom("Nathalie");
      t.add(sal);
    }

    if (t.size(Categorie.class) == 0) {

      message.addText("Création des catégories...");
      ArrayList<Categorie> cats = new ArrayList<Categorie>();
      Categorie cat = new Categorie();
      cat.setNom("Détails");
      t.add(cat);
      cats.add(cat);
      cat = new Categorie();
      cat.setNom("Conserve");
      t.add(cat);
      cats.add(cat);

      int indice = 0;
      message.addText("Création des produits...");
      Float[] prix = new Float[] { 4.0f, 4f, 4.5f, 5.6f, 3.0f, 3.5f, 4.0f, 4.5f };
      int indicePrix = 0;
      for (String s : new String[] { "12/16", "16+", "22+", "30+" }) {
        for (Categorie categorie : cats) {
          Produit prod = new Produit();
          prod.setCategorie(categorie);
          prod.setNumero(indice);
          prod.setDescription(s);
          prod.setPrixUnitaire(new BigDecimal(prix[indicePrix++]));
          indice++;
          t.add(prod);
        }

      }

      message.addText("Création des clients...");
      Client cli = new Client();
      cli.setCodePostal("97422");
      cli.setRue("impasse bec alouette");
      cli.setVille("la Saline");
      cli.setEmail("jerome_gareyte@yahoo.fr");
      cli.setNom("GAREYTE");
      cli.setPrenom("jerome");
      cli.setTelephone("0693300519");
      t.add(cli);

      cli = new Client();
      cli.setCodePostal("24220");
      cli.setRue("le mas");
      cli.setVille("Castels");
      cli.setEmail("luciengareyte@wanadoo.fr");
      cli.setNom("GAREYTE");
      cli.setPrenom("Lucien");
      cli.setTelephone("0553292309");
      t.add(cli);
    }

  }
}
