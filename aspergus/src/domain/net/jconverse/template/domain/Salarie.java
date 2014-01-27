package net.jconverse.template.domain;


import javax.persistence.*;

@Entity
public class Salarie {


	private Integer numero;
	private String nom;
	private Boolean visible;
	
	@Id
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
