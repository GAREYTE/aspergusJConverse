package net.jconverse.template.conversation.main;

import net.jconverse.template.TemplateFactory;
import net.sf.jconverse.conversation.listeners.ConversationListener;
import net.sf.jconverse.conversation.transitions.Transition;
import net.sf.jconverse.extensions.conversation.AbstractAdminConversation;

/**
 * @author Mahmoud
 * 
 */
public class AdminConversation extends AbstractAdminConversation {

  public AdminConversation(ConversationListener conversationListener) {
    super(conversationListener);
  }

  @Override
  public Transition resetCache() {
    final Transition action = super.resetCache();
    TemplateFactory.getUserProcess().clearCache();
    TemplateFactory.getInstance(false).resetCache();
    return action;
  }

  @Override
  public String getInfos() {
    return TemplateFactory.getInstance(false).getInfos();
  }
}
