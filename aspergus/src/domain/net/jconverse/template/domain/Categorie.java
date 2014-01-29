package net.jconverse.template.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

import net.sf.jconverse.crud.annotations.gui.Required;
import net.sf.jconverse.crud.annotations.gui.Visibilities.Hidden;
import net.sf.jconverse.crud.annotations.gui.Visibilities.InLabel;
import net.sf.jconverse.crud.annotations.gui.Visibilities.InList;
import net.sf.jconverse.crud.annotations.gui.Visibilities.InSelect;
import net.sf.jconverse.crud.annotations.gui.Visibilities.Uneditable;
import net.sf.jconverse.crud.annotations.interceptors.GeneratedUID;
import net.sourceforge.cristalmodel.annotations.Order;

@Entity
public class Categorie {

  @Required
  @Column(length = 40)
  private String nom;
  private Long id;

  public void setId(Long id) {
    this.id = id;
  }

  @Order(0)
  @Uneditable
  @Hidden
  @Id
  @GeneratedUID
  public Long getId() {
    return this.id;
  }

  @InList
  @InLabel
  @InSelect
  public String getNom() {
    return nom;
  }

  public void setNom(String nom) {
    this.nom = nom;
  }

}
