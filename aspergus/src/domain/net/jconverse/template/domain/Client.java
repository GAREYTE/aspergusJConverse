package net.jconverse.template.domain;

import java.util.ArrayList;
import java.util.Collection;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToMany;

import net.sf.jconverse.crud.annotations.gui.Choice;
import net.sf.jconverse.crud.annotations.gui.Choice.Mode;
import net.sf.jconverse.crud.annotations.gui.Inline;
import net.sf.jconverse.crud.annotations.gui.Visibilities.Hidden;
import net.sf.jconverse.crud.annotations.gui.Visibilities.InLabel;
import net.sf.jconverse.crud.annotations.gui.Visibilities.InList;
import net.sf.jconverse.crud.annotations.gui.Visibilities.InSearch;
import net.sf.jconverse.crud.annotations.gui.Visibilities.InSelect;
import net.sf.jconverse.crud.annotations.gui.Visibilities.Uneditable;
import net.sf.jconverse.crud.annotations.interceptors.GeneratedUID;
import net.sf.jconverse.crud.builder.Finder;
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
    this.commandes = new ArrayList<>();
  }

  public void setId(Long id) {
    this.id = id;
  }

  @Order(600)
  @Uneditable
  @Hidden
  @Id
  @GeneratedUID
  public Long getId() {
    return this.id;
  }

  private Collection<Commande> commandes;

  @Order(700)
  @OneToMany(mappedBy = "client", cascade = CascadeType.REMOVE)
  @Inline(editDirectly = true, inEdit = true)
  public Collection<Commande> getCommandes() {
    return commandes;
  }

  public void setCommandes(Collection<Commande> commandes) {
    this.commandes = commandes;
  }

  public Commande createCommandes(Finder f) throws Exception {
    Commande commande = new Commande();
    commande.init(f);
    commande.setClient(this);
    return commande;
  }

  public void addCommandes(Commande commande) {
    commande.setClient(this);
    this.commandes.add(commande);
  }

  public void removeCommandes(Commande commande) {
    commande.setClient(null);
    this.commandes.remove(commande);
  }

  @InSearch()
  @InList()
  @Order(100)
  @Column(length = 40)
  @InLabel
  @InSelect
  public String getNom() {
    return nom;
  }

  public void setNom(String name) {
    this.nom = name;
  }

  @InSearch()
  @InList()
  @Order(200)
  @InSelect
  @InLabel
  public String getPrenom() {
    return prenom;
  }

  public void setPrenom(String prenom) {
    this.prenom = prenom;
  }

  @InList()
  @Order(300)
  public String getTelephone() {
    return telephone;
  }

  public void setTelephone(String telephone) {
    this.telephone = telephone;
  }

  @InList()
  @Order(400)
  public String getEmail() {
    return email;
  }

  public void setEmail(String email) {
    this.email = email;
  }

  @InSearch()
  @InList()
  @Inline(editDirectly = true, inEdit = true, inView = true)
  @Choice(value = Mode.SELECTION)
  @Order(500)
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
