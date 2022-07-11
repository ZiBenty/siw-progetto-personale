package it.uniroma3.siw.model;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name = "users") // cambiamo nome perchè in postgres user e' una parola riservata
public class User {
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	private String name;
	private String surname;
	private Boolean banned = false;
	private String lol;
	@OneToMany(mappedBy = "user", cascade = {CascadeType.ALL}, orphanRemoval = true)
	List<OCharacter> characters = new ArrayList<OCharacter>();
	
	@OneToMany
	List<OCharacter> favored = new ArrayList<OCharacter>();

	public Long getId() {
		return id;
	}
	
	public void setId(Long id) {
		this.id = id;
	}
	
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getSurname() {
		return surname;
	}

	public void setSurname(String surname) {
		this.surname = surname;
	}

	public Boolean getBanned() {
		return banned;
	}

	public void setBanned(Boolean banned) {
		this.banned = banned;
	}

	public List<OCharacter> getCharacters() {
		return characters;
	}

	public void setCharacters(List<OCharacter> characters) {
		this.characters = characters;
	}

	public List<OCharacter> getFavored() {
		return favored;
	}

	public void setFavored(List<OCharacter> favored) {
		this.favored = favored;
	}
	
	public void addCharacter(OCharacter character) {
		this.characters.add(character);
		character.setUser(this);
	}
	
	public void removeCharacter(OCharacter character) {
		this.characters.remove(character);
		character.setUser(null);
	}
}
