package net.jconverse.template.domain;


import java.math.*;

import javax.persistence.*;

import net.sf.jconverse.crud.annotations.gui.Required;
import net.sf.jconverse.crud.annotations.gui.Visibilities.Hidden;
import net.sf.jconverse.crud.annotations.gui.Visibilities.Uneditable;
import net.sf.jconverse.crud.annotations.interceptors.GeneratedUID;
import net.sourceforge.cristalmodel.annotations.Order;


@Entity
public class Produit {
	
	@Id @Column(length=9)
	private int numero;
	
	@Column(length=40) @Required
	private String description;
	
	@Required
	@ManyToOne(optional=false, fetch=FetchType.LAZY)
	private Categorie categorie;
	
	@Required
	private BigDecimal prixUnitaire;
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
	 
	public int getNumero() {
		return numero;
	}

	public void setNumero(int number) {
		this.numero = number;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public BigDecimal getPrixUnitaire() {
		return prixUnitaire;
	}

	public void setPrixUnitaire(BigDecimal unitPrice) {
		this.prixUnitaire = unitPrice;
	}

	
	@ManyToOne
	public Categorie getCategorie() {
		return categorie;
	}

	public void setCategorie(Categorie categorie) {
		this.categorie = categorie;
	}

}
