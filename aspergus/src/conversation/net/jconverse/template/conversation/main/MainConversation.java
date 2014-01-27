package net.jconverse.template.conversation.main;

import net.jconverse.template.AddParameters;
import net.jconverse.template.CreateDatabase;
import net.jconverse.template.TemplateFactory;
import net.jconverse.template.UpdateDatabase;
import net.jconverse.template.domain.Commande;
import net.jconverse.template.presentation.TemplateBundle;
import net.sf.jconverse.components.ActionEvent;
import net.sf.jconverse.components.ActionListener;
import net.sf.jconverse.components.Button;
import net.sf.jconverse.components.containers.WPage;
import net.sf.jconverse.components.containers.WPanel;
import net.sf.jconverse.conversation.SecureConversation;
import net.sf.jconverse.conversation.transitions.BasicActions;
import net.sf.jconverse.conversation.transitions.Transition;
import net.sf.jconverse.crud.ListParameters;
import net.sf.jconverse.crud.ListParameters.SearchMode;
import net.sf.jconverse.crud.ParameterConversation;
import net.sf.jconverse.extensions.conversation.AbstractConversation;
import net.sf.jconverse.extensions.conversation.RootConversation;
import net.sf.jconverse.extensions.conversation.VersionConversation;
import net.sf.jconverse.laf.styles.Styles;

public class MainConversation extends AbstractConversation implements SecureConversation, RootConversation {

  @Override
  public Transition start() {
    return start(null);
  }

  @Override
  public Transition start(final String... argv) {

    WPage menu = new WPage(TemplateBundle.application);

    WPanel field = new WPanel("");

    field.addButton(new Button(TemplateBundle.gestion, ListParameters.create(TemplateFactory.getInstance(false), Commande.class)
        .setAllowAdd(true).setAllowEdit(true).setAllowRemove(true).setSearchMode(SearchMode.SEARCH)
        .setExitListener(BasicActions.Start).createConversation(), TemplateFactory.user_role));

    menu.add(field);

    field.addButton(new Button(TemplateBundle.gestionParametres, new ParameterConversation(TemplateFactory.getInstance(false),
        BasicActions.Start), TemplateFactory.admin_role));

    boolean autoriserModificationDeBaseEnWeb = true;

    if (autoriserModificationDeBaseEnWeb) {
      field.addButton(new Button(TemplateBundle.creerBase, new ActionListener() {

        @Override
        public Transition actionPerformed(ActionEvent event) throws Exception {

          CreateDatabase.run(event.getStatusMessage());
          AddParameters.run(event.getStatusMessage());

          return BasicActions.Start;
        }
      }, TemplateFactory.admin_role).addStyle(Styles.LINE_BREAK_BEFORE));

      field.addButton(new Button(TemplateBundle.majBase, new ActionListener() {

        @Override
        public Transition actionPerformed(ActionEvent event) throws Exception {

          UpdateDatabase.run(event.getStatusMessage());
          return BasicActions.Start;
        }
      }, TemplateFactory.admin_role));
    }

    menu.add(field);

    menu.addButton(new Button(TemplateBundle.showAdmin, new AdminConversation(BasicActions.Start), TemplateFactory.admin));

    menu.addButton(new Button(TemplateBundle.showVersion, new VersionConversation(BasicActions.Start)));

    return menu;
  }
}