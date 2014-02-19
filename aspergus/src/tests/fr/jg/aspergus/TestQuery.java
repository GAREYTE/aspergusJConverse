package fr.jg.aspergus;

import junit.framework.TestCase;
import fr.jg.aspergus.AspergusFactory;
import fr.jg.aspergus.domain.Commande;
import net.sf.cristaltools.tools.text.Message;
import net.sf.jconverse.crud.storage.BaseTransaction;
import net.sf.jconverse.crud.storage.DataAccessLayer;

public class TestQuery extends TestCase {

  public void testQuery() {

    try {
      AspergusFactory.getInstance(false).initialize(new Message());

      AspergusFactory.getInstance(false).execute(false, new BaseTransaction() {

        @Override
        protected void execute(final DataAccessLayer t) throws Exception {
          assertEquals(0, t.size(Commande.class));
        }
      });

    } catch (Exception e) {
      net.sf.cristaltools.log.Log.getCurrent().error("", e);
    }
  }
}
