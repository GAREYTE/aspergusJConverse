package fr.jg.aspergus;

import net.sf.cristaltools.tools.text.Message;
import net.sf.jasql.cn.SQLSource;
import net.sf.jconverse.crud.storage.EJBFactory;
import net.sf.jconverse.crud.storage.exceptions.PersistenceException;
import net.sf.jconverse.security.MockUserProcess;
import net.sf.jconverse.security.UserProcess;
import fr.jg.aspergus.domain.Commande;
import fr.jg.aspergus.domain.Mailing;
import fr.jg.aspergus.presentation.PresentationConfig;

public class AspergusFactory extends EJBFactory {

  public final static String admin = "admin";
  public final static String user_role = "user_role";
  public final static String admin_role = "admin_role";

  protected static AspergusFactory homeFactory;
  private static UserProcess user;

  public static AspergusFactory getInstance(boolean batch) {
    if (homeFactory == null) {
      homeFactory = new AspergusFactory();
      //homeFactory.setDdlMode(DdlMode.create);
    }
    return homeFactory;
  }

  public static UserProcess getUserProcess() {
    if (user == null) {
      if (true == true) {
        user = new MockUserProcess(PresentationConfig.getAppName());
        user.setAutoLogAsAdmin(true);
        return user;
      } else {
        //        user = new UserProcess(PassportSource.getInstance(), PresentationConfig.getAppName(), new String[] {
        //            admin,
        //            user_role,
        //            admin_role });
      }
    }
    return user;
  }

  @Override
  protected void setup(final Message message) throws PersistenceException {

  }

  @Override
  public Class[] getEntities() {
    return new Class[] { Commande.class, Mailing.class };
  }

  @Override
  public Integer getDatabaseVersion() {
    return 2;
  }

  @Override
  public SQLSource getSource() {
    return AspergusDerbySource.getInstance();
    //    return TemplateMySqlSource.getInstance();
  }

  @Override
  public boolean isInProduction() {
    return false;
  }

}
