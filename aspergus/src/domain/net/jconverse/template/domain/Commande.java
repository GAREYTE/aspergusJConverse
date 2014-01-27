package net.jconverse.template.domain;

import java.math.*;
import java.util.*;

import javax.persistence.*;
import javax.xml.ws.soap.*;

import net.sf.jconverse.crud.annotations.gui.Choice;
import net.sf.jconverse.crud.annotations.gui.Choice.Display;
import net.sf.jconverse.crud.annotations.gui.Choice.Mode;
import net.sf.jconverse.crud.annotations.gui.Required;
import net.sf.jconverse.crud.annotations.gui.Visibilities.Hidden;
import net.sf.jconverse.crud.annotations.gui.Visibilities.Uneditable;
import net.sf.jconverse.crud.annotations.interceptors.GeneratedUID;
import net.sourceforge.cristalmodel.annotations.Order;

@Entity
public class Commande {

  public enum Statut {
    Commandee,
    Preparee,
    Livree
  };

  private int annee;

  private Integer numero;

  private Date dateCommande;

  private Salarie prisePar;

  private Date datePreparation;

  private Salarie preparePar;

  private Date dateLivraison;

  private Salarie livrePar;

  private int tva;

  private Client client;

  private Collection<CommandeDetail> details;

  private String remarque;

  private Statut statut;

  private Long id;

  public Commande() {
    this.details = new ArrayList<>();
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

  @Transient
  public BigDecimal getQuantite() {
    BigDecimal sum = BigDecimal.ZERO;
    for (CommandeDetail detail : details) {
      sum = sum.add(detail.getQuantite());
    }
    return sum;
  }

  @Transient
  public BigDecimal getSum() {
    BigDecimal sum = BigDecimal.ZERO;
    for (CommandeDetail detail : details) {
      sum = sum.add(detail.getAmount());
    }
    return sum;
  }

  @Transient
  public BigDecimal getVat() {
    return getSum().multiply(new BigDecimal(getTva()).divide(new BigDecimal(100))).setScale(2, RoundingMode.UP);
  }

  @Transient
  public BigDecimal getTotal() {
    return getSum().add(getVat()).setScale(2, RoundingMode.UP);
  }

  @Column(length = 4)
  @Required
  public int getAnnee() {
    return annee;
  }

  public void setAnnee(int year) {
    this.annee = year;
  }

  @Column(length = 6)
  @Required
  public Integer getNumero() {
    return numero;
  }

  public void setNumero(Integer number) {
    this.numero = number;
  }

  @Required
  public Date getDateCommande() {
    return dateCommande;
  }

  public void setDateCommande(Date date) {
    this.dateCommande = date;
  }

  @Column(length = 2)
  @Required
  public int getTva() {
    return tva;
  }

  public void setTva(int vatPercentage) {
    this.tva = vatPercentage;
  }

  @ManyToOne(optional = false, fetch = FetchType.LAZY)
  public Client getClient() {
    return client;
  }

  public void setClient(Client client) {
    this.client = client;
  }

  @OneToMany(mappedBy = "commande", cascade = CascadeType.REMOVE)
  public Collection<CommandeDetail> getDetails() {
    return details;
  }

  public void setDetails(Collection<CommandeDetail> details) {
    this.details = details;
  }

  public String getRemarque() {
    return remarque;
  }

  public void setRemarque(String remarks) {
    this.remarque = remarks;
  }

  public Date getDatePreparation() {
    return datePreparation;
  }

  public void setDatePreparation(Date datePreparation) {
    this.datePreparation = datePreparation;
  }

  @Choice(value = Mode.SELECTION, display = Display.COMBO)
  @ManyToOne()
  @Required
  public Salarie getPrisePar() {
    return prisePar;
  }

  public void setPrisePar(Salarie prisePar) {
    this.prisePar = prisePar;
  }

  @Choice(value = Mode.SELECTION, display = Display.COMBO)
  @ManyToOne()
  public Salarie getPreparePar() {
    return preparePar;
  }

  public void setPreparePar(Salarie preparePar) {
    this.preparePar = preparePar;
  }

  public Date getDateLivraison() {
    return dateLivraison;
  }

  public void setDateLivraison(Date dateLivraison) {
    this.dateLivraison = dateLivraison;
  }

  @Choice(value = Mode.SELECTION, display = Display.COMBO)
  @ManyToOne()
  public Salarie getLivrePar() {
    return livrePar;
  }

  public void setLivrePar(Salarie livrePar) {
    this.livrePar = livrePar;
  }

  public Statut getStatut() {
    return statut;
  }

  public void setStatut(Statut type) {
    this.statut = type;
  }

}
