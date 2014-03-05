package fr.jg.aspergus.domain;

import java.math.BigDecimal;

import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.MappedSuperclass;
import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;
import javax.persistence.Transient;

import net.sf.jconverse.components.JScriptActions.JScriptEvent;
import net.sf.jconverse.conversation.transitions.BasicActions;
import net.sf.jconverse.conversation.transitions.Transition;
import net.sf.jconverse.crud.Action;
import net.sf.jconverse.crud.CrudEvent;
import net.sf.jconverse.crud.actions.CrudCommand;
import net.sf.jconverse.crud.annotations.gui.Choice;
import net.sf.jconverse.crud.annotations.gui.Choice.Display;
import net.sf.jconverse.crud.annotations.gui.Choice.Mode;
import net.sf.jconverse.crud.annotations.gui.Required;
import net.sf.jconverse.crud.annotations.gui.Visibilities.Hidden;
import net.sf.jconverse.crud.annotations.gui.Visibilities.InEdit;
import net.sf.jconverse.crud.annotations.gui.Visibilities.InEditableList;
import net.sf.jconverse.crud.annotations.gui.Visibilities.InList;
import net.sf.jconverse.crud.annotations.gui.Visibilities.InSelect;
import net.sf.jconverse.crud.annotations.gui.Visibilities.InView;
import net.sf.jconverse.crud.annotations.gui.Visibilities.Uneditable;
import net.sf.jconverse.crud.annotations.gui.format.FormatDecimal;
import net.sf.jconverse.crud.annotations.interceptors.GeneratedUID;
import net.sf.jconverse.crud.builder.DisplayMode;
import net.sf.jconverse.crud.builder.Finder;
import net.sf.jconverse.crud.field.Hints;
import net.sf.jconverse.crud.field.Hints.PropertyHint;
import net.sf.jconverse.laf.styles.Styles;
import net.sourceforge.cristalmodel.annotations.DisplayConstraints;
import net.sourceforge.cristalmodel.annotations.Order;

@MappedSuperclass
public class LigneDetail {

  private Produit produit;
  private BigDecimal quantite;
  private BigDecimal prixUnitaire;
  private BigDecimal total;
  private Long id;

  public void setId(Long id) {
    this.id = id;
  }

  @Order(700)
  @Uneditable
  @Hidden
  @Id
  @GeneratedUID
  public Long getId() {
    return this.id;
  }

  @Order(500)
  @InList
  @InEditableList()
  @InEdit(view = "Vente", visibleIfEmpty = true)
  @InView(view = "Vente")
  public BigDecimal getTotal() {
    return total;
  }

  @Order(200)
  @ManyToOne(fetch = FetchType.LAZY)
  @Choice(display = Display.COMBO, value = Mode.SELECTION)
  @InList
  @InEditableList
  @Required(displayComboWithEmptyValue = true)
  @InEdit(view = "Vente")
  @InView(view = "Vente")
  public Produit getProduit() {
    return produit;
  }

  public void setProduit(Produit produit) {
    this.produit = produit;
  }

  @Order(300)
  @InList
  @Required
  @InEditableList
  @InEdit(view = "Vente")
  @InView(view = "Vente")
  @FormatDecimal(value = "0.00", edit = "0.00")
  public BigDecimal getQuantite() {
    return quantite;
  }

  public void setQuantite(BigDecimal quantity) {
    this.quantite = quantity;
  }

  @Order(100)
  @Transient
  @InSelect()
  @InList(groupBy = true)
  public Categorie getCategorie() {
    if (getProduit() != null)
      return getProduit().getCategorie();
    return null;
  }

  public void setTotal(BigDecimal total) {
    this.total = total;
  }

  @Order(400)
  @InList
  @InEditableList()
  @Hidden
  @InEdit(visibleIfEmpty = true)
  public BigDecimal getPrixUnitaire() {
    return prixUnitaire;
  }

  public void setPrixUnitaire(BigDecimal prixUnitaire) {
    this.prixUnitaire = prixUnitaire;
  }

  @PreUpdate
  @PrePersist
  public void update() {
    if (getProduit() != null) {
      setPrixUnitaire(getProduit().getPrixUnitaire());
      if (getQuantite() != null) {
        setTotal(getQuantite().multiply(getProduit().getPrixUnitaire()));
      }
    }

  }

  @DisplayConstraints
  public void setupConstraints(final Hints hints, final DisplayMode mode, final Finder f) {
    if (mode.in(DisplayMode.EDITABLE_LIST, DisplayMode.EDITION)) {
      hints.addPropertyHint("getPrixUnitaire", PropertyHint.UNEDITABLE);
      hints.addPropertyHint("getTotal", PropertyHint.UNEDITABLE);
      hints.addAction("getProduit", JScriptEvent.OnChange, onChangeAction());
      hints.addAction("getQuantite", JScriptEvent.OnChange, onChangeAction());
      hints.addAction("getQuantite", new Action("+", new CrudCommand() {

        @Override
        public Transition run(CrudEvent event) throws Exception {
          setQuantite(getQuantite() != null ? getQuantite().add(BigDecimal.ONE) : BigDecimal.ONE);
          update();
          return BasicActions.Start;
        }
      }));
      hints.addAction("getQuantite", new Action("-", new CrudCommand() {

        @Override
        public Transition run(CrudEvent event) throws Exception {
          setQuantite(getQuantite() != null && getQuantite().compareTo(BigDecimal.ONE) > 0 ? getQuantite()
              .subtract(BigDecimal.ONE) : getQuantite());
          update();
          return BasicActions.Start;
        }
      }));
    }
  }

  private Action onChangeAction() {
    return new Action("test", new CrudCommand() {

      @Override
      public Transition run(CrudEvent event) throws Exception {
        if (getProduit() != null) {
          setPrixUnitaire(getProduit().getPrixUnitaire());
          if (getQuantite() != null)
            setTotal(getQuantite().multiply(getProduit().getPrixUnitaire()));
        }
        return BasicActions.Start;
      }
    }).addStyle(Styles.HIDDEN);
  }
}
