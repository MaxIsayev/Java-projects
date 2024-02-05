
public class Letters {
	private String ltVowels = "AĄEĘĖIĮYOUŲŪaąeęėiįyouųū"; 
	private String ltConsonants = "BCČDFGHJKLMNPRSŠTVZŽbcčdfghjklmnprsštvzž";
	//texto ilgio skaiciavimas
	public int lengthCount(String text) {		
		int i = 0;
		StringBuilder build = new StringBuilder(text);
		while (!build.toString().isEmpty()) {
			build.deleteCharAt(0);			
			i=i+1;
		}		
		return i;		
	}
	//tikrinimas ar raide - balsis
	public boolean isVowel(char character) {
		int i;
		boolean vowelfound = false;		 
		for ( i = 0; i < this.lengthCount(ltVowels)-1; i++) {
			if (character == ltVowels.charAt(i)) {
				vowelfound = true;
			}		
		}
		return vowelfound;		
	}
	//tikrinimas ar raide - priebalsis
	public boolean isConsonant(char character) {
		int i;
		boolean consonantFound = false;		 
		for ( i = 0; i < this.lengthCount(ltConsonants)-1; i++) {
			if (character == ltConsonants.charAt(i)) {
				consonantFound = true;
			}		
		}
		return consonantFound;		
	}
	//balsiu skaiciavimas
	public int vowelsCount(String text) {
		int i, numOfVowels = 0;
		for ( i = 0; i < this.lengthCount(text)-1; i++) {
			if (this.isVowel(text.charAt(i))) {
				numOfVowels++;
			}
		}
		return numOfVowels;		
	} 
	//priebalsiu skaiciavimas
	public int consonantsCount(String text) {
		int i, numOfConsonants = 0;
		for ( i = 0; i < this.lengthCount(text)-1; i++) {
			if (this.isConsonant(text.charAt(i))) {
				numOfConsonants++;
			}
		}
		return numOfConsonants;	
	}
}
