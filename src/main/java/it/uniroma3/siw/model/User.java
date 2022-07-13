package it.uniroma3.siw.model;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name = "users") // cambiamo nome perchè in postgres user e' una parola riservata
public class User {
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long uid;
	
	private String name;
	
	private boolean banned = false;
	
	@OneToMany(mappedBy = "user", cascade = {CascadeType.ALL}, orphanRemoval = true)
	List<OCharacter> characters = new ArrayList<OCharacter>();
	
	@ManyToMany
	@JoinTable(
			name = "users_favored", 
	        joinColumns = @JoinColumn(
	        		name = "user_id", 
	        		referencedColumnName = "uid"
	        ),
	        inverseJoinColumns = @JoinColumn(
	        		name = "favored_id", 
	        		referencedColumnName = "cid"))
	List<OCharacter> favored = new ArrayList<OCharacter>();

	public Long getId() {
		return uid;
	}
	
	public void setId(Long id) {
		this.uid = id;
	}
	
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public boolean getBanned() {
		return banned;
	}

	public void setBanned(boolean banned) {
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
	
	public List<OCharacter> getPublicCharacters(){
		List<OCharacter> publicChars = new ArrayList<OCharacter>();
		for (OCharacter c : this.characters) {
			if (c.isPrivateChr())
				publicChars.add(c);
		}
		return publicChars;
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
