//package library;

public class Main {
	/**
	*
	*/
	public static void main (String[] args){
		String value1;
		String value2;
	
		Reader reader1 = new Reader();
		Copy copy1 = new Copy();
		Book book1 = new Book();
		//testing Reader 
		System.out.println("Testing class Reader");
		reader1.set(3333333);
		reader1.printMsg();
		//check of overloaded methods 
		reader1.printInfo();
		reader1.printInfo("Antanas Lenkshas");
		//check of get and static
		System.out.println("Got value is"+reader1.get());
		reader1.set(485620); 
		reader1.printInfo();	
		//--------------------------- 
		//testing Copy 
		System.out.println("Testing class Copy");
		copy1.setTaken("2010-05-01");
		copy1.setReturn("2010-06-04");
		//check of overloaded methods 
		copy1.info();
		copy1.info(30); 
		//check of get and static		
		System.out.println("Got value is"+copy1.getTaken()+" and "+copy1.getReturn());
		copy1.setTaken("2010-11-01");
		copy1.info();
		//--------------------------- 
		//testing book 
		System.out.println("Testing class Book");
		book1.set("20000 years under water");
	
		//check of overloaded methods 
		book1.info();
		book1.info("Romas Baronas");
		//check of get and static
		System.out.println("Got value is"+book1.get());
		book1.set("Viktor Kishko"); 
		book1.info();	
	}
	
}




















