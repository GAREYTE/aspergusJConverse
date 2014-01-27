package net.jconverse.template.domain;

import javax.persistence.*;

import net.sf.jconverse.crud.annotations.gui.Required;


@Entity
public class Categorie {

	@Required
	@Column(length=40)
	private String nom;

	public String getNom() {
		return nom;
	}

	public void setNom(String nom) {
		this.nom = nom;
	}
	
	
}
