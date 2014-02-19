package fr.jg.aspergus.presentation;

import net.sf.jconverse.conversation.Conversation;
import net.sf.jconverse.extensions.conversation.RootConversation;
import net.sf.jconverse.extensions.login.PassportLogonConversation;
import net.sf.jconverse.extensions.servlet.AbstractPresentationConfig;
import net.sf.jconverse.laf.HttpDecoder;
import net.sf.jconverse.laf.PageDecoder;
import net.sf.jconverse.laf.lang.LBundle;
import net.sf.jconverse.security.UserManager;
import fr.jg.aspergus.AspergusFactory;
import fr.jg.aspergus.conversation.main.MainConversation;
import fr.sstic.babel.constants.MailAdresses;

public class PresentationConfig extends AbstractPresentationConfig {
  @Override
  public boolean isInProdMode() {
    return AspergusFactory.getInstance(false).isInProduction();
  }

  @Override
  public void runAtShutdown() {
    AspergusFactory.getInstance(false).shutdown();
    super.runAtShutdown();

  }

  @Override
  public void runAtStartup() {
    super.runAtStartup();
  }

  public PresentationConfig() {
    super(getAppName());
  }

  @Override
  public Class<? extends LBundle> getBundle() {
    return AspergusBundle.class;
  }

  @Override
  public PageDecoder getPageDecoder() {
    return new HttpDecoder();
  }

  public static String getAppName() {
    return "";
  }

  @Override
  public String getApplicationName() {
    return getAppName();
  }

  @Override
  public String getTitle() {
    return "Aspergus";
  }

  @Override
  public boolean isLogoPrintable() {
    return true;
  }

  @Override
  public boolean increasePrintSize() {
    return false;
  }

  @Override
  public Class<? extends RootConversation> getRootConversation() {
    return MainConversation.class;
  }

  @Override
  public Class<? extends Conversation> getLogonConversation() {
    return PassportLogonConversation.class;
  }

  @Override
  public UserManager getUserProcess() {
    return AspergusFactory.getUserProcess();
    //return null;
  }

  @Override
  public String getHomeURL() {
    return MailAdresses.HOME;
  }

  @Override
  public String getSupportServer() {
    return MailAdresses.HOST;
  }

  @Override
  public String getSupportMel() {
    return MailAdresses.SUPPORT;
  }

  @Override
  public String getSupportSender() {
    return MailAdresses.SENDER;
  }

  @Override
  public String getHelpURL() {
    return "http://moka.vindemia.re/manuel:template:start";
  }

  @Override
  protected String getProductionServerURL() {
    return null;
  }
}