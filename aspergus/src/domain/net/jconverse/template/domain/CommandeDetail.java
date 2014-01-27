package net.jconverse.template.domain;


import java.math.*;

import javax.persistence.*;

import net.sf.jconverse.crud.annotations.gui.Required;
import net.sf.jconverse.crud.annotations.gui.Visibilities.Hidden;
import net.sf.jconverse.crud.annotations.gui.Visibilities.Uneditable;
import net.sf.jconverse.crud.annotations.interceptors.GeneratedUID;
import net.sourceforge.cristalmodel.annotations.Order;


@Entity
public class CommandeDetail  {
	
	@ManyToOne
	private Commande commande;
	
		private Produit produit;
	
	@Required
	private BigDecimal quantite;
	
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
	 
	@Transient
	public BigDecimal getAmount() {
		return getQuantite().multiply(getProduit().getPrixUnitaire());
	}

	@ManyToOne()
	public Commande getCommande() {
		return commande;
	}

	public void setCommande(Commande commande) {
		this.commande = commande;
	}
	@ManyToOne(optional=false, fetch=FetchType.LAZY)
	public Produit getProduit() {
		return produit;
	}

	public void setProduit(Produit produit) {
		this.produit = produit;
	}

	public BigDecimal getQuantite() {
		return quantite;
	}

	public void setQuantite(BigDecimal quantity) {
		this.quantite = quantity;
	}
	
}
