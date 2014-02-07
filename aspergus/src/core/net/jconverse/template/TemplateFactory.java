package net.jconverse.template;

import net.jconverse.template.domain.Commande;
import net.jconverse.template.presentation.PresentationConfig;
import net.sf.cristaltools.tools.text.Message;
import net.sf.jasql.cn.SQLSource;
import net.sf.jconverse.crud.storage.EJBFactory;
import net.sf.jconverse.crud.storage.exceptions.PersistenceException;
import net.sf.jconverse.security.MockUserProcess;
import net.sf.jconverse.security.UserProcess;
import fr.sstic.babel.datasources.PassportSource;

public class TemplateFactory extends EJBFactory {

  public final static String admin = "admin";
  public final static String user_role = "user_role";
  public final static String admin_role = "admin_role";

  protected static TemplateFactory homeFactory;
  private static UserProcess user;

  public static TemplateFactory getInstance(boolean batch) {
    if (homeFactory == null) {
      homeFactory = new TemplateFactory();
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
        user = new UserProcess(PassportSource.getInstance(), PresentationConfig.getAppName(), new String[] {
            admin,
            user_role,
            admin_role });
      }
    }
    return user;
  }

  @Override
  protected void setup(final Message message) throws PersistenceException {

  }

  @Override
  public Class[] getEntities() {
    return new Class[] { Commande.class };
  }

  @Override
  public Integer getDatabaseVersion() {
    return 2;
  }

  @Override
  public SQLSource getSource() {
    return TemplateDerbySource.getInstance();
    //    return TemplateMySqlSource.getInstance();
  }

  @Override
  public boolean isInProduction() {
    return false;
  }

}
