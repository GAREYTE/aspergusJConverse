package net.jconverse.template.domain;


import javax.persistence.*;

@Embeddable
public class Adresse {
	private String rue;
	
	private String codePostal;
	
	private String ville;
	

	public String getRue() {
		return rue;
	}

	public void setRue(String adresse) {
		this.rue = adresse;
	}

	public String getCodePostal() {
		return codePostal;
	}

	public void setCodePostal(String codePostal) {
		this.codePostal = codePostal;
	}

	public String getVille() {
		return ville;
	}

	public void setVille(String ville) {
		this.ville = ville;
	}

}
