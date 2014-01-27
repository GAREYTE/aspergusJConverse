package net.jconverse.template.domain;


import java.math.*;

import javax.persistence.*;

import net.sf.jconverse.crud.annotations.gui.Required;


@Entity
public class CommandeDetail  {
	
	@ManyToOne
	private Commande commande;
	
		private Produit produit;
	
	@Required
	private BigDecimal quantite;
	
	public BigDecimal getAmount() {
		return getQuantite().multiply(getProduit().getPrixUnitaire());
	}

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
