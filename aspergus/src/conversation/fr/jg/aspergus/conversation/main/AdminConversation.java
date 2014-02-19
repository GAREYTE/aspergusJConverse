package fr.jg.aspergus.conversation.main;

import fr.jg.aspergus.AspergusFactory;
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
    AspergusFactory.getUserProcess().clearCache();
    AspergusFactory.getInstance(false).resetCache();
    return action;
  }

  @Override
  public String getInfos() {
    return AspergusFactory.getInstance(false).getInfos();
  }
}
