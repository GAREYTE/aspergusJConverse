package net.jconverse.template.domain;


import javax.persistence.*;

import net.sf.jconverse.crud.annotations.gui.Visibilities.Hidden;
import net.sf.jconverse.crud.annotations.gui.Visibilities.Uneditable;
import net.sf.jconverse.crud.annotations.interceptors.GeneratedUID;
import net.sourceforge.cristalmodel.annotations.Order;

@Entity
public class Salarie {


	private Integer numero;
	private String nom;
	private Boolean visible;
	
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
	 
	public Integer getNumero() {
		return numero;
	}
	public void setNumero(Integer numero) {
		this.numero = numero;
	}
	public String getNom() {
		return nom;
	}
	public void setNom(String nom) {
		this.nom = nom;
	}
	public Boolean getVisible() {
		return visible;
	}
	public void setVisible(Boolean visible) {
		this.visible = visible;
	}
	
	
	
}
