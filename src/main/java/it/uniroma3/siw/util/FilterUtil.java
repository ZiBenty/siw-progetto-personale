package it.uniroma3.siw.util;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;

import it.uniroma3.siw.model.OCharacter;
import it.uniroma3.siw.model.comparator.OCharacterComparator;

public class FilterUtil {
	
	//filtra secondo i parametri passati
		@SuppressWarnings("deprecation")
		public List<OCharacter> filter(List<OCharacter> toFilter, 
				String name, String order, String creationTime){
			List<OCharacter> filteredChars = new ArrayList<OCharacter>();
			//filtra per l'attributo name e se è stato creato entro creationTime
			for(OCharacter c : toFilter) {
				if(c.getName().toLowerCase().contains(name.toLowerCase())) {
					Date today = new Date();
				    Date chr = c.getCreationDate();
					switch(creationTime) {
					case "today":
						if(chr.getDay() - today.getDay() == 0)
							filteredChars.add(c);
						break;
					case "week":
						if(chr.getMonth() == today.getMonth()  && chr.getYear() == today.getYear()) {
							if(today.getDay() - chr.getDay() < 8)
								filteredChars.add(c);
						}
						else {
							//febbraio
							if(chr.getMonth() == 1) {
								if(28 - chr.getDay() + today.getDay() < 8)
									filteredChars.add(c);
								break;
							//mesi 30 giorni
							} else if (chr.getMonth() == 3 || chr.getMonth() == 5 ||
									chr.getMonth() == 8 || chr.getMonth() == 10) {
								if(30 - chr.getDay() + today.getDay() < 8)
									filteredChars.add(c);
								break;
							//mesi 31 giorni
							} else {
								if(31 - chr.getDay() + today.getDay() < 8)
									filteredChars.add(c);
								break;
							}
						}
						break;
					case "month":
						if(chr.getMonth() == today.getMonth() && chr.getYear() == today.getYear())
							filteredChars.add(c);
						break;
					case "all":
						filteredChars.add(c);
						break;
					}
				}
			}
			//ordina la lista secondo l'order scelto
			switch (order) {
			case "ascend":
				Collections.sort(filteredChars,new OCharacterComparator());
				break;
			case "descend":
				Collections.sort(filteredChars,new OCharacterComparator());
				Collections.reverse(filteredChars);
				break;
			case "newer":
				Collections.sort(filteredChars);
				break;
			case "older":
				Collections.sort(filteredChars);
				Collections.reverse(filteredChars);
				break;
			}
			return filteredChars;
		}
}
