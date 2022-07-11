package it.uniroma3.siw.model;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Transient;
import javax.validation.constraints.NotBlank;


@Entity
public class OCharacter {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	
	@NotBlank
	@Column(nullable = false)
	private String name;
	
	private String description;
	
	private String pic;
	
	private boolean privateChr = false;
	
	@ManyToOne
	private User user;
	
	@OneToMany(mappedBy = "character", fetch = FetchType.EAGER, cascade = {CascadeType.ALL})
	private List<Chapter> story = new ArrayList<Chapter>();

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

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getPic() {
		return pic;
	}

	public void setPic(String pic) {
		this.pic = pic;
	}

	public boolean isPrivateChr() {
		return privateChr;
	}

	public void setPrivateChr(boolean privateChr) {
		this.privateChr = privateChr;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public List<Chapter> getStory() {
		return story;
	}

	public void setStory(List<Chapter> story) {
		this.story = story;
	}
	
	@Transient
    public String getPicImagePath(Long userId) {
        if (pic == null || userId == null) return null;
         
        return "/user-pics/" + userId + "/" + pic;
    }
	
	public void addChapter(Chapter chapter) {
		this.story.add(chapter);
		chapter.setCharacter(this);
	}
	
	public void removeChapter(Chapter chapter) {
		this.story.remove(chapter);
		chapter.setCharacter(null);
	}
}