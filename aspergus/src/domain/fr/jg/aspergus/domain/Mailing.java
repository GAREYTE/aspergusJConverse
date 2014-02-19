package fr.jg.aspergus.domain;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import net.sf.cristaltools.log.Log;
import net.sf.cristaltools.tools.date.CalendarFactory;
import net.sf.cristaltools.tools.mail.MessageException;
import net.sf.cristaltools.tools.mail.MessageSender;
import net.sf.jconverse.conversation.transitions.BasicActions;
import net.sf.jconverse.conversation.transitions.Transition;
import net.sf.jconverse.crud.Action;
import net.sf.jconverse.crud.CrudEvent;
import net.sf.jconverse.crud.actions.CrudCommand;
import net.sf.jconverse.crud.annotations.gui.Choice;
import net.sf.jconverse.crud.annotations.gui.Choice.Mode;
import net.sf.jconverse.crud.annotations.gui.Comment;
import net.sf.jconverse.crud.annotations.gui.Size;
import net.sf.jconverse.crud.annotations.gui.Visibilities.Hidden;
import net.sf.jconverse.crud.annotations.gui.Visibilities.InList;
import net.sf.jconverse.crud.annotations.gui.Visibilities.Uneditable;
import net.sf.jconverse.crud.annotations.interceptors.GeneratedUID;
import net.sf.jconverse.crud.builder.DisplayMode;
import net.sf.jconverse.crud.builder.Finder;
import net.sf.jconverse.crud.field.Hints;
import net.sourceforge.cristalmodel.annotations.DisplayConstraints;
import net.sourceforge.cristalmodel.annotations.Order;

@Entity
public class Mailing {
  private static final String FROM = "jerome_gareyte@yahoo.fr";
  private static final String SMTP = "smtp.mail.yahoo.fr";
  private static final String PORT = "465";
  private String description;
  private String objet;
  private String message;
  private Date dateEnvoi;
  private List<Client> clients;
  private Long id;

  public Mailing() {
    this.clients = new ArrayList<Client>();
  }

  public void setId(Long id) {
    this.id = id;
  }

  @Order(500)
  @Uneditable
  @Hidden
  @Id
  @GeneratedUID
  public Long getId() {
    return this.id;
  }

  @Order(100)
  @Comment("Ne sera pas communiquée au client")
  @InList
  public String getDescription() {
    return description;
  }

  public void setDescription(String nom) {
    this.description = nom;
  }

  @Order(200)
  @Size(value = { 50, 1 })
  public String getObjet() {
    return objet;
  }

  public void setObjet(String object) {
    this.objet = object;
  }

  @Order(300)
  @Size(value = { 50, 10 })
  public String getMessage() {
    return message;
  }

  public void setMessage(String message) {
    this.message = message;
  }

  @Order(400)
  @ManyToMany()
  @Choice(value = Mode.SELECTION)
  public List<Client> getClients() {
    return clients;
  }

  public void setClients(List<Client> clients) {
    this.clients = clients;
  }

  public void addClients(Client... clients) {
    for (Client client : clients) {
      this.clients.add(client);
    }
  }

  public void removeClients(Client... clients) {
    for (Client client : clients) {
      this.clients.remove(client);
    }
  }

  @InList
  @Temporal(TemporalType.TIMESTAMP)
  @Uneditable
  public Date getDateEnvoi() {
    return dateEnvoi;
  }

  public void setDateEnvoi(Date dateEnvoi) {
    this.dateEnvoi = dateEnvoi;
  }

  @DisplayConstraints
  public void setupConstraints(final Hints hints, final DisplayMode mode, final Finder f) {

    if (mode.equals(DisplayMode.EDITION)) {
      hints.addAction(new Action("Envoyer", new CrudCommand() {

        @Override
        public Transition run(CrudEvent event) throws Exception {
          List<String> emails = new ArrayList<String>();
          for (Client client : getClients()) {
            emails.add(client.getEmail());
          }
          try {
            MessageSender.sendMessageWithPort(SMTP, PORT, FROM, getObjet(), getMessage(), emails.toArray(new String[0]));
            setDateEnvoi(CalendarFactory.getToday().getTime());
          } catch (MessageException e) {
            Log.getCurrent().error(e.getMessage());
          }
          return BasicActions.Start;
        }
      }));
    }
  }

}
