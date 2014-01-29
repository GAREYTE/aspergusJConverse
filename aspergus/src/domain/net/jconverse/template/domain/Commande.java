package net.jconverse.template.domain;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.PreUpdate;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import net.sf.cristaltools.tools.date.CalendarFactory;
import net.sf.jasql.command.ReadonlyStatement;
import net.sf.jasql.data.Result;
import net.sf.jasql.sql.TextQuery;
import net.sf.jconverse.crud.annotations.gui.Choice;
import net.sf.jconverse.crud.annotations.gui.Choice.Display;
import net.sf.jconverse.crud.annotations.gui.Choice.Mode;
import net.sf.jconverse.crud.annotations.gui.DefaultEditValue;
import net.sf.jconverse.crud.annotations.gui.DefaultToday;
import net.sf.jconverse.crud.annotations.gui.Required;
import net.sf.jconverse.crud.annotations.gui.Size;
import net.sf.jconverse.crud.annotations.gui.View;
import net.sf.jconverse.crud.annotations.gui.Views;
import net.sf.jconverse.crud.annotations.gui.Visibilities.Hidden;
import net.sf.jconverse.crud.annotations.gui.Visibilities.InEdit;
import net.sf.jconverse.crud.annotations.gui.Visibilities.InLabel;
import net.sf.jconverse.crud.annotations.gui.Visibilities.InList;
import net.sf.jconverse.crud.annotations.gui.Visibilities.InSearch;
import net.sf.jconverse.crud.annotations.gui.Visibilities.InView;
import net.sf.jconverse.crud.annotations.gui.Visibilities.Uneditable;
import net.sf.jconverse.crud.annotations.interceptors.GeneratedUID;
import net.sf.jconverse.crud.builder.Finder;
import net.sourceforge.cristalmodel.annotations.Order;

import org.joda.time.DateTime;

@Entity
@Views(value = { @View(name = "Préparation", multicolumn = 2) })
public class Commande {
  public enum Tva {
    Vingt("20%"),
    Cinq("5.5%"),
    Zero("0%");

    private String label;

    private Tva(String label) {
      this.label = label;
    }

  }

  public enum Statut {
    Commandee,
    Preparee,
    Livree
  };

  private Integer annee;

  private Integer numero;

  private Date dateCommande;

  private Salarie prisePar;

  private Date datePreparation;

  private Salarie preparePar;

  private Date dateLivraison;

  private Salarie livrePar;

  private Tva tva;

  private Client client;

  private Collection<CommandeDetail> details;

  private String remarque;

  private Statut statut;

  private Long id;

  public Commande() {
    this.details = new ArrayList<>();
  }

  public void init(Finder f) throws Exception {
    setDateCommande(CalendarFactory.getNow().getTime());
    TextQuery tq = new TextQuery();
    tq.select("max(numero)").from(" commande");
    Result res = f.execute(new ReadonlyStatement(tq));
    Integer numero = 0;
    if (res.getRowCount() > 0 && res.getInteger(0, 0) != null)
      numero = res.getInteger(0, 0);
    setNumero(numero + 1);
  }

  public void setId(Long id) {
    this.id = id;
  }

  @Order(1800)
  @Uneditable
  @Hidden
  @Id
  @GeneratedUID
  public Long getId() {
    return this.id;
  }

  @InSearch()
  @InList()
  @Order(500)
  @Column(length = 4)
  @Required
  @Uneditable
  public Integer getAnnee() {
    return annee;
  }

  public void setAnnee(Integer year) {
    this.annee = year;
  }

  @InSearch()
  @InList()
  @Order(600)
  @Column(length = 6)
  @Required
  @Uneditable(exceptInAdminMode = true)
  @InLabel
  public Integer getNumero() {
    return numero;
  }

  public void setNumero(Integer number) {
    this.numero = number;
  }

  @InSearch(view = "Préparation")
  @InList()
  @Order(700)
  @Required
  @Temporal(TemporalType.TIMESTAMP)
  @InView(view = "Préparation")
  @InEdit(view = "Préparation")
  @DefaultEditValue(DefaultToday.class)
  public Date getDateCommande() {
    return dateCommande;
  }

  public void setDateCommande(Date date) {
    this.dateCommande = date;
  }

  @InSearch()
  @InList()
  @Order(1300)
  @Required
  @Enumerated(EnumType.STRING)
  public Tva getTva() {
    return tva;
  }

  public void setTva(Tva vatPercentage) {
    this.tva = vatPercentage;
  }

  @InSearch()
  @InList()
  @Order(1400)
  @ManyToOne(optional = false, fetch = FetchType.LAZY)
  @Required(displayComboWithEmptyValue = true)
  @Choice(value = Mode.EDITION, display = Display.COMBO)
  @InLabel
  public Client getClient() {
    return client;
  }

  public void setClient(Client client) {
    this.client = client;
  }

  @Order(1500)
  @OneToMany(mappedBy = "commande", cascade = CascadeType.ALL)
  public Collection<CommandeDetail> getDetails() {
    return details;
  }

  public void setDetails(Collection<CommandeDetail> details) {
    this.details = details;
  }

  public void addDetails(CommandeDetail detail) {
    detail.setCommande(this);
    this.details.add(detail);
  }

  public void removeDetails(CommandeDetail detail) {
    detail.setCommande(null);
    this.details.remove(detail);
  }

  public CommandeDetail summaryDetails() {
    CommandeDetail sum = new CommandeDetail();
    BigDecimal qte = BigDecimal.ZERO;
    BigDecimal total = BigDecimal.ZERO;
    for (CommandeDetail det : getDetails()) {
      if (det.getQuantite() != null)
        qte = qte.add(det.getQuantite());
      if (det.getTotal() != null)
        total = total.add(det.getTotal());
    }
    sum.setQuantite(qte);
    sum.setTotal(total);
    return sum;
  }

  @Order(1600)
  @Size(value = { 40, 3 })
  public String getRemarque() {
    return remarque;
  }

  public void setRemarque(String remarks) {
    this.remarque = remarks;
  }

  @InSearch(view = "Préparation")
  @InList()
  @Order(900)
  @Temporal(TemporalType.TIMESTAMP)
  @InView(view = "Préparation")
  @InEdit(view = "Préparation")
  public Date getDatePreparation() {
    return datePreparation;
  }

  public void setDatePreparation(Date datePreparation) {
    this.datePreparation = datePreparation;
  }

  @InSearch(view = "Préparation")
  @InList()
  @Order(800)
  @Choice(value = Mode.SELECTION, display = Display.COMBO)
  @ManyToOne()
  @Required(displayComboWithEmptyValue = true)
  @InView(view = "Préparation")
  @InEdit(view = "Préparation")
  public Salarie getPrisePar() {
    return prisePar;
  }

  public void setPrisePar(Salarie prisePar) {
    this.prisePar = prisePar;
  }

  @InSearch(view = "Préparation")
  @InList()
  @Order(1000)
  @Choice(value = Mode.SELECTION, display = Display.COMBO)
  @ManyToOne()
  @InView(view = "Préparation")
  @InEdit(view = "Préparation")
  @InLabel
  public Salarie getPreparePar() {
    return preparePar;
  }

  public void setPreparePar(Salarie preparePar) {
    this.preparePar = preparePar;
  }

  @InSearch(view = "Préparation")
  @InList()
  @Order(1100)
  @Temporal(TemporalType.TIMESTAMP)
  @InView(view = "Préparation")
  @InEdit(view = "Préparation")
  public Date getDateLivraison() {
    return dateLivraison;
  }

  public void setDateLivraison(Date dateLivraison) {
    this.dateLivraison = dateLivraison;
  }

  @InSearch(view = "Préparation")
  @InList()
  @Order(1200)
  @Choice(value = Mode.SELECTION, display = Display.COMBO)
  @ManyToOne()
  @InView(view = "Préparation")
  @InEdit(view = "Préparation")
  public Salarie getLivrePar() {
    return livrePar;
  }

  public void setLivrePar(Salarie livrePar) {
    this.livrePar = livrePar;
  }

  @InSearch()
  @InList()
  @Order(1700)
  @Uneditable
  public Statut getStatut() {
    return statut;
  }

  public void setStatut(Statut type) {
    this.statut = type;
  }

  @PreUpdate
  public void update() {
    DateTime date = new DateTime(getDateCommande());
    setAnnee(date.getYear());

  }
}
