import java.util.ArrayList; // import the ArrayList class

public class NumberGenerator {
	private int coefficient;
	private long divisionBy;
	private int iterationsNumber;
	private int initialValue;
	private long remaining;
	private ArrayList<Long> myNumbers = new ArrayList<Long>();
	
	 // Create a class constructor for the NumberGenerator class
	public NumberGenerator() {
		divisionBy = 1;  // Set the initial value for the class attribute
		coefficient = 1;
		iterationsNumber = 1; 
		initialValue = 1;
		remaining = initialValue;		
	}
	
	// Setters
	public void setCoefficient(int coefficient) {
	  this.coefficient = coefficient;
	}
	
	public void setDivisionBy(long divisionBy) {
	  this.divisionBy = divisionBy;
	}	
	
	public void setIterationsNumber(int iterationsNumber) {
		  this.iterationsNumber = iterationsNumber;
	}
	
	public void setInitialValue(int initialValue) {
		  this.initialValue = initialValue;
	}
	
	//Numbers generation
	public void generateNumbers() {
		remaining = initialValue;
		for(int i = 0; i < iterationsNumber; i++) {
			remaining =( remaining*coefficient )%divisionBy;			
			//put got reminder to container
			myNumbers.add(remaining);			
		};
		
	}
	
	// Getters
	public int getCoefficient() {
	  return coefficient;
	}	
	
	public long getDivisionBy() {
	  return divisionBy;
	}
	
	public int getIterationsNumber() {
	  return iterationsNumber;
	}
	
	public int getInitialValue() {
		return initialValue;
	}
	
	public Long getListItem(int index) {
		Long result;
		if (index < iterationsNumber) {
		 	result=myNumbers.get(index);
		} 	else {result = (long) 0;};
		return result;
	}
}
