package net.jconverse.template.domain;


import java.math.*;
import java.util.*;

import javax.persistence.*;
import javax.xml.ws.soap.*;

import net.sf.jconverse.crud.annotations.gui.Choice;
import net.sf.jconverse.crud.annotations.gui.Choice.Display;
import net.sf.jconverse.crud.annotations.gui.Choice.Mode;
import net.sf.jconverse.crud.annotations.gui.Required;


@Entity
public class Commande  {
	
	public enum Statut { Commandee, Preparee, Livree };
	@Column(length=4) @Required
	private int annee;
	
	@Column(length=6) @Required	
	private Integer numero;
	
	@Required 
	private Date dateCommande;
	
	@ManyToOne()
	@Required
	private Salarie prisePar;
	
	private Date datePreparation;
	
	@ManyToOne()
	private Salarie preparePar;
	
	private Date dateLivraison;
	
	@ManyToOne()
	private Salarie livrePar;
	
	@Column(length=2) @Required
	private int tva;
	
	@ManyToOne(optional=false, fetch=FetchType.LAZY)
	private Client client;
	
	@OneToMany(mappedBy="commande", cascade=CascadeType.REMOVE)
	private Collection<CommandeDetail> details;
	
	private String remarque;
	
	private Statut statut;
	
	public BigDecimal getQuantite() {
		BigDecimal sum = BigDecimal.ZERO;
		for (CommandeDetail detail: details) {
			sum = sum.add(detail.getQuantite());
		}
		return sum;
	}
	
	public BigDecimal getSum() {
		BigDecimal sum = BigDecimal.ZERO;
		for (CommandeDetail detail: details) {
			sum = sum.add(detail.getAmount());
		}
		return sum;
	}
	
	public BigDecimal getVat() {
		return getSum().multiply(new BigDecimal(getTva()).divide(new BigDecimal(100))).setScale(2, RoundingMode.UP);
	}
	
	public BigDecimal getTotal() {
		return getSum().add(getVat()).setScale(2, RoundingMode.UP);
	}
	

	public int getAnnee() {
		return annee;
	}

	public void setAnnee(int year) {
		this.annee = year;
	}

	public Integer getNumero() {
		return numero;
	}

	public void setNumero(Integer number) {
		this.numero = number;
	}

	public Date getDateCommande() {
		return dateCommande;
	}

	public void setDateCommande(Date date) {
		this.dateCommande = date;
	}

	public int getTva() {
		return tva;
	}

	public void setTva(int vatPercentage) {
		this.tva = vatPercentage;
	}

	public Client getClient() {
		return client;
	}

	public void setClient(Client client) {
		this.client = client;
	}

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
	@Choice(value=Mode.SELECTION,display=Display.COMBO)
	public Salarie getPrisePar() {
		return prisePar;
	}

	public void setPrisePar(Salarie prisePar) {
		this.prisePar = prisePar;
	}

	@Choice(value=Mode.SELECTION,display=Display.COMBO)
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

	@Choice(value=Mode.SELECTION,display=Display.COMBO)
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
