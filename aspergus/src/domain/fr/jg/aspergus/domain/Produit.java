package fr.jg.aspergus.domain;

import java.math.BigDecimal;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

import net.sf.jconverse.crud.annotations.gui.Required;
import net.sf.jconverse.crud.annotations.gui.Visibilities.Hidden;
import net.sf.jconverse.crud.annotations.gui.Visibilities.InLabel;
import net.sf.jconverse.crud.annotations.gui.Visibilities.InList;
import net.sf.jconverse.crud.annotations.gui.Visibilities.InSearch;
import net.sf.jconverse.crud.annotations.gui.Visibilities.InSelect;
import net.sf.jconverse.crud.annotations.gui.Visibilities.Uneditable;
import net.sf.jconverse.crud.annotations.gui.format.FormatDecimal;
import net.sf.jconverse.crud.annotations.interceptors.GeneratedUID;
import net.sourceforge.cristalmodel.annotations.Order;

@Entity
public class Produit {

  private Integer numero;

  private String description;

  private Categorie categorie;

  private BigDecimal prixUnitaire;
  private Long id;

  public void setId(Long id) {
    this.id = id;
  }

  @Order(500)
  @Uneditable
  @Hidden
  @GeneratedUID
  @Id
  public Long getId() {
    return this.id;
  }

  @InSearch()
  @Order(100)
  public Integer getNumero() {
    return numero;
  }

  public void setNumero(Integer number) {
    this.numero = number;
  }

  @InSearch()
  @Order(200)
  @Column(length = 40)
  @Required
  @InList
  @InSelect
  @InLabel
  public String getDescription() {
    return description;
  }

  public void setDescription(String description) {
    this.description = description;
  }

  @Order(300)
  @Required
  @InLabel
  @InSelect
  @InList
  @FormatDecimal(edit = "0.00€", value = "0,00€")
  public BigDecimal getPrixUnitaire() {
    return prixUnitaire;
  }

  public void setPrixUnitaire(BigDecimal unitPrice) {
    this.prixUnitaire = unitPrice;
  }

  @InSearch()
  @Order(400)
  @Required
  @ManyToOne(optional = false, fetch = FetchType.LAZY)
  @InLabel
  @InSelect(groupBy = true)
  @InList
  public Categorie getCategorie() {
    return categorie;
  }

  public void setCategorie(Categorie categorie) {
    this.categorie = categorie;
  }

}
