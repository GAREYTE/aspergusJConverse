package net.jconverse.template.domain;


import java.util.*;

import javax.persistence.*;


@Entity
public class Client {
	
	@Id
	private int numero;
	
	@Column(length=40) 
	private String nom;
	
	private String prenom;
	private Adresse adresse;

	private String telephone;
	private String email;
	
	@OneToMany(mappedBy="client", cascade=CascadeType.REMOVE)
	private Collection<Commande> commande;
	
	public Collection<Commande> getCommande() {
		return commande;
	}

	public void setCommande(Collection<Commande> commande) {
		this.commande = commande;
	}

	public int getNumero() {
		return numero;
	}

	public void setNumero(int number) {
		this.numero = number;
	}

	public String getNom() {
		return nom;
	}

	public void setNom(String name) {
		this.nom = name;
	}

	public String getPrenom() {
		return prenom;
	}

	public void setPrenom(String prenom) {
		this.prenom = prenom;
	}

	

	public String getTelephone() {
		return telephone;
	}

	public void setTelephone(String telephone) {
		this.telephone = telephone;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public Adresse getAdresse() {
		return adresse;
	}

	public void setAdresse(Adresse adresse) {
		this.adresse = adresse;
	}
	@Override
	public String toString() {
		// TODO Auto-generated method stub
		return getNom()+ " "+getPrenom();
	}

	
}
