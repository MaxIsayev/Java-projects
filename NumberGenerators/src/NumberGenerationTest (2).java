import java.io.IOException;
import java.math.*;

/**
 * 
 */

/**
 * @author Pain
 *
 */
public class NumberGenerationTest {
	public static int positiveResultsNumber(int compNumber, NumberGenerator A, NumberGenerator B) {
		int result = 0;
		compNumber = Math.min(compNumber, Math.min(A.getIterationsNumber(), B.getIterationsNumber())); 
		//deshimtainis palyginimas 256(10)=100000000(2)
		for (int i = 0; i < compNumber; i++) {
			if ((A.getListItem(i)%256)==(B.getListItem(i)%256)) {
				result++;
			}
		}
		
		return result;		
	} 
	/**
	 * @param args
	 */
	public static void main(String[] args)throws IOException {
		// TODO Auto-generated method stub
		int[] intArgs = {0,0};
		//generators numbers read from command line
		System.out.println("Arguments: "+args[0]+", "+args[1]);
		
		for ( int i = 0; i < args.length; i++ )
		{
			intArgs[i] = Integer.parseInt(args[i]);
		}
				
		//generate numbers and print them
		
		NumberGenerator A = new NumberGenerator();
		NumberGenerator	B =	new NumberGenerator();
		
		A.setCoefficient(16807);
		A.setDivisionBy(2147483647);
		A.setInitialValue(intArgs[0]);
		A.setIterationsNumber(1000000);
		
		B.setDivisionBy(2147483647);
		B.setCoefficient(48271);
		B.setIterationsNumber(1000000);
		B.setInitialValue(intArgs[1]);
		
		System.out.println("Generating numbers A  and B...");
		A.generateNumbers();
		B.generateNumbers();		
		System.out.println("Numbers generation successful!");
		//printing for test
		for ( int i = 0; i<4; i++) {
			System.out.println(Long.toString(A.getListItem(i))+" "+Long.toString(B.getListItem(i)));
		}		
		
		System.out.println("After many(1000 000) A,B comparisons number of positive results is "
				+Integer.toString(positiveResultsNumber(1000000,A,B)));
	}

}
