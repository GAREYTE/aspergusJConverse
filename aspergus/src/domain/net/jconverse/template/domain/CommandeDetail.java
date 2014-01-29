package net.jconverse.template.domain;

import java.math.BigDecimal;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;
import javax.persistence.Transient;

import net.sf.jconverse.crud.annotations.gui.Choice;
import net.sf.jconverse.crud.annotations.gui.Choice.Display;
import net.sf.jconverse.crud.annotations.gui.Choice.Mode;
import net.sf.jconverse.crud.annotations.gui.Required;
import net.sf.jconverse.crud.annotations.gui.Visibilities.Hidden;
import net.sf.jconverse.crud.annotations.gui.Visibilities.InList;
import net.sf.jconverse.crud.annotations.gui.Visibilities.Uneditable;
import net.sf.jconverse.crud.annotations.interceptors.GeneratedUID;
import net.sourceforge.cristalmodel.annotations.Order;

@Entity
public class CommandeDetail {

  private Commande commande;

  private Produit produit;

  private BigDecimal quantite;
  private BigDecimal prixUnitaire;
  private Long id;

  private BigDecimal total;

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
  @Uneditable
  public BigDecimal getTotal() {
    return total;
  }

  @Order(600)
  @ManyToOne()
  @Uneditable
  public Commande getCommande() {
    return commande;
  }

  public void setCommande(Commande commande) {
    this.commande = commande;
  }

  @Order(200)
  @ManyToOne(optional = false, fetch = FetchType.LAZY)
  @Choice(display = Display.COMBO, value = Mode.SELECTION)
  @InList
  @Required
  public Produit getProduit() {
    return produit;
  }

  public void setProduit(Produit produit) {
    this.produit = produit;
  }

  @Order(300)
  @InList
  @Required
  public BigDecimal getQuantite() {
    return quantite;
  }

  public void setQuantite(BigDecimal quantity) {
    this.quantite = quantity;
  }

  @Order(100)
  @Transient
  @InList(groupBy = true)
  public Categorie getCategorie() {
    if (getProduit() != null)
      return getProduit().getCategorie();
    return null;
  }

  public void setTotal(BigDecimal total) {
    this.total = total;
  }

  @PreUpdate
  @PrePersist
  public void update() {
    if (getQuantite() != null && getProduit() != null) {
      setTotal(getQuantite().multiply(getProduit().getPrixUnitaire()));
      setPrixUnitaire(getProduit().getPrixUnitaire());
    }
  }

  @Order(400)
  @InList
  @Uneditable
  public BigDecimal getPrixUnitaire() {
    return prixUnitaire;
  }

  public void setPrixUnitaire(BigDecimal prixUnitaire) {
    this.prixUnitaire = prixUnitaire;
  }

}
