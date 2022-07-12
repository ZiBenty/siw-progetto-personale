package it.uniroma3.siw.model.comparator;

import java.util.Comparator;

import it.uniroma3.siw.model.OCharacter;

public class OCharacterComparator implements Comparator<OCharacter>{

	@Override
	public int compare(OCharacter o1, OCharacter o2) {
		return o1.getName().compareTo(o2.getName());
	}

}
