public class Copy{	
	
	private String taken;
	private String toReturn;	
	private final int period = 5; 
	private static int nr = 0; 
	//constructor 
	public Copy(){
		this.taken = "unknown";
		this.toReturn = "unknown"; 
	}
	
	// methods 
	//set  
	public void setTaken(String newValue){
		taken = newValue;
		nr++;
	}
	
	public void setReturn(String newValue){
		toReturn = newValue;
	}
	//get 
	public String getTaken(){
		return taken;
	} 
	
	public String getReturn(){
		return toReturn;
	} 
	
	//overloading 
	public void info(){
		System.out.println("N " + nr);
		System.out.println(" The date taken is " + taken);
		System.out.println(" The date toReturn is " + toReturn);
		System.out.println(" The period is " + period + " weeks");
	}
	
	public void info(int n){
		System.out.println("the term " + toReturn + " is increased " + n +" days");
	}
}