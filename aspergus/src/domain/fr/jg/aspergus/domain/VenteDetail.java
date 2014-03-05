package fr.jg.aspergus.domain;

import java.math.BigDecimal;
import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.ManyToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import net.sf.jconverse.components.JScriptActions.JScriptEvent;
import net.sf.jconverse.conversation.transitions.BasicActions;
import net.sf.jconverse.conversation.transitions.Transition;
import net.sf.jconverse.crud.Action;
import net.sf.jconverse.crud.CrudEvent;
import net.sf.jconverse.crud.actions.CrudCommand;
import net.sf.jconverse.crud.annotations.gui.Choice;
import net.sf.jconverse.crud.annotations.gui.Choice.Display;
import net.sf.jconverse.crud.annotations.gui.Choice.Mode;
import net.sf.jconverse.crud.annotations.gui.DefaultEditValue;
import net.sf.jconverse.crud.annotations.gui.DefaultTodayWithTime;
import net.sf.jconverse.crud.annotations.gui.Required;
import net.sf.jconverse.crud.annotations.gui.Visibilities.InList;
import net.sf.jconverse.crud.annotations.gui.Visibilities.InSearch;
import net.sf.jconverse.crud.builder.DisplayMode;
import net.sf.jconverse.crud.builder.Finder;
import net.sf.jconverse.crud.field.Hints;
import net.sf.jconverse.crud.field.Hints.PropertyHint;
import net.sf.jconverse.laf.styles.Styles;
import net.sourceforge.cristalmodel.annotations.DisplayConstraints;
import net.sourceforge.cristalmodel.annotations.Order;

import org.joda.time.DateTime;

@Entity
public class VenteDetail extends LigneDetail {
  public enum ModePaiement {
    Liquide,
    CB,
    Cheque,

  }

  private Date date;
  private Client client;
  private Salarie vendeur;
  private ModePaiement modeDePaiement;

  @Order(700)
  @ManyToOne()
  @InList
  @InSearch
  @Choice(value = Mode.EDITION, display = Display.COMBO)
  public Client getClient() {
    return client;
  }

  public void setClient(Client client) {
    this.client = client;
  }

  @Order(800)
  @ManyToOne
  @InList
  @Choice(value = Mode.SELECTION, display = Display.COMBO)
  public Salarie getVendeur() {
    return vendeur;
  }

  public void setVendeur(Salarie vendeur) {
    this.vendeur = vendeur;
  }

  @Order(100)
  @DefaultEditValue(value = DefaultTodayWithTime.class)
  @Temporal(TemporalType.TIMESTAMP)
  @InList
  @InSearch()
  public Date getDate() {
    return date;
  }

  public void setDate(Date date) {
    this.date = date;
  }

  @Order(900)
  @Enumerated(EnumType.STRING)
  @Required
  @InList
  @InSearch
  public ModePaiement getModeDePaiement() {
    return modeDePaiement;
  }

  public void setModeDePaiement(ModePaiement modeDePaiement) {
    this.modeDePaiement = modeDePaiement;
  }

  @Override
  @DisplayConstraints
  public void setupConstraints(final Hints hints, final DisplayMode mode, final Finder f) {
    super.setupConstraints(hints, mode, f);
    if (mode.in(DisplayMode.EDITABLE_LIST, DisplayMode.EDITION)) {
      hints.addPropertyHint("getTotal", PropertyHint.EDITABLE);
      hints.addAction("getTotal", JScriptEvent.OnChange, new Action("onchange2", new CrudCommand() {

        @Override
        public Transition run(CrudEvent event) throws Exception {
          if (getTotal() != null && getProduit() != null) {
            setQuantite(getTotal().divide(getProduit().getPrixUnitaire(), 1, BigDecimal.ROUND_HALF_UP));
          }
          return BasicActions.Start;
        }
      }).addStyle(Styles.HIDDEN));
    }
    if (mode.in(DisplayMode.SEARCH)) {
      hints.addAction("getDate", new Action("<", new CrudCommand() {

        @Override
        public Transition run(CrudEvent event) throws Exception {
          setDate(new DateTime(getDate()).minusDays(1).toDate());
          return BasicActions.Start;
        }
      }));
      hints.addAction("getDate", new Action(">", new CrudCommand() {

        @Override
        public Transition run(CrudEvent event) throws Exception {
          setDate(new DateTime(getDate()).plusDays(1).toDate());
          return BasicActions.Start;
        }
      }));

    }

  }
}
