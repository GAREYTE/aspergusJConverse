package fr.jg.aspergus;

import fr.jg.aspergus.AspergusFactory;
import net.sf.cristaltools.log.Log;
import net.sf.cristaltools.tools.text.Message;
import net.sf.jasql.cn.SQLSource;
import net.sf.jconverse.crud.storage.EJBBaseFactory.DdlMode;

public class UpdateDatabase {

  public static void main(final String[] argv) {

    final Message message = new Message();
    run(message);
  }

  public static void run(final Message message) {
    try {
      SQLSource.DEBUG_LEVEL = Log.LEVEL_INFO;
      AspergusFactory.getInstance(false).setDdlMode(DdlMode.update);

      AspergusFactory.getInstance(false).initialize(message);
      AspergusFactory.getInstance(false).shutdown();

    } catch (Exception e) {
      net.sf.cristaltools.log.Log.getCurrent().error("", e);
      message.addError("Impossible de mettre à jour la base", e);
    }
  }

}
