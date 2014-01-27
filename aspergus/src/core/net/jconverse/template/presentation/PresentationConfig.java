package net.jconverse.template.presentation;

import net.jconverse.template.TemplateFactory;
import net.jconverse.template.conversation.main.MainConversation;
import net.sf.jconverse.conversation.Conversation;
import net.sf.jconverse.extensions.conversation.RootConversation;
import net.sf.jconverse.extensions.login.PassportLogonConversation;
import net.sf.jconverse.extensions.servlet.AbstractPresentationConfig;
import net.sf.jconverse.laf.HttpDecoder;
import net.sf.jconverse.laf.PageDecoder;
import net.sf.jconverse.laf.lang.LBundle;
import net.sf.jconverse.security.UserManager;
import fr.sstic.babel.constants.MailAdresses;

public class PresentationConfig extends AbstractPresentationConfig {
  @Override
  public boolean isInProdMode() {
    return TemplateFactory.getInstance(false).isInProduction();
  }

  @Override
  public void runAtShutdown() {
    TemplateFactory.getInstance(false).shutdown();
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
    return TemplateBundle.class;
  }

  @Override
  public PageDecoder getPageDecoder() {
    return new HttpDecoder();
  }

  public static String getAppName() {
    return "template";
  }

  @Override
  public String getApplicationName() {
    return getAppName();
  }

  @Override
  public String getTitle() {
    return "Template";
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
    return TemplateFactory.getUserProcess();
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
    return "babel.vindemia.re";
  }
}