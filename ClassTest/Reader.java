public class Reader{
	private int asmKodas;
	private static int nr = 0; 
	
	
	// methods 
	//set  
	public void set(int newAsmKodas){
		asmKodas = newAsmKodas;
		nr++;
	}
	//get 
	public int get(){	
		return asmKodas;
	}	
	
	public final void printMsg(){
		System.out.println("Welcome to MIF library information system");
	}
	//overloading 	
	public void printInfo(){
		System.out.println(nr+" - "+asmKodas);
	}
	
	public void printInfo(String s){
		System.out.println(asmKodas + " belongs to " + s);
	}
}