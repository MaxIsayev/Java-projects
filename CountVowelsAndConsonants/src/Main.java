/**
 * 
 */


public class Main {	 
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		String text = "Programuotojo ar tiesiog bet kokio IT specialisto profesija taps vis labiau įprasta.";		
		Letters letters = new Letters();		
		
		// TODO Auto-generated method stub		
		System.out.println("This program counts numbers of vowels and consonants "+"in text:"+'\n'
							+text);
		
		System.out.println("Number of vowels: "+letters.vowelsCount(text)+'\n'
							+"Number of consonants: "+letters.consonantsCount(text));
		
	}

}
