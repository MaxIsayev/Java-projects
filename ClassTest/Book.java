public class Book{
	private String title;
	private static int nr = 0; 
	private final static int nOfCopiesToTake = 1;
	
	//constructor 
	public Book(){
		this.title = "empty";
	}
	
	// methods 
	//set  
	public void set(String newTitle){
		title = newTitle;
		nr++;
	}
	//get 
	public String get(){
		return title;
	}
	
	//overloading 
	public void info(){
		System.out.println("N "+nr + " "+title);
		System.out.println(nOfCopiesToTake + " should be taken ");
		
	}

	public void info(String s){
		System.out.println(title + " is written by " + s);
	}
}
