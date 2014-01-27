package net.jconverse.template.domain;

import java.util.ArrayList;
import java.util.Collection;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToMany;

import net.sf.jconverse.crud.annotations.gui.Visibilities.Hidden;
import net.sf.jconverse.crud.annotations.gui.Visibilities.Uneditable;
import net.sf.jconverse.crud.annotations.interceptors.GeneratedUID;
import net.sourceforge.cristalmodel.annotations.Order;

@Entity
public class Client {

  private String nom;

  private String prenom;
  private Adresse adresse;

  private String telephone;
  private String email;

  private Long id;

  public Client() {
    this.commande = new ArrayList<>();
  }

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

  private Collection<Commande> commande;

  @OneToMany(mappedBy = "client", cascade = CascadeType.REMOVE)
  public Collection<Commande> getCommande() {
    return commande;
  }

  public void setCommande(Collection<Commande> commande) {
    this.commande = commande;
  }

  @Column(length = 40)
  public String getNom() {
    return nom;
  }

  public void setNom(String name) {
    this.nom = name;
  }

  public String getPrenom() {
    return prenom;
  }

  public void setPrenom(String prenom) {
    this.prenom = prenom;
  }

  public String getTelephone() {
    return telephone;
  }

  public void setTelephone(String telephone) {
    this.telephone = telephone;
  }

  public String getEmail() {
    return email;
  }

  public void setEmail(String email) {
    this.email = email;
  }

  public Adresse getAdresse() {
    return adresse;
  }

  public void setAdresse(Adresse adresse) {
    this.adresse = adresse;
  }

  @Override
  public String toString() {
    // TODO Auto-generated method stub
    return getNom() + " " + getPrenom();
  }

}
