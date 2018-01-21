import java.io.File;
import java.util.Scanner;

public class TelephoneReader{

public static String input;


	public static void main(String[] args){

		//set up
		Scanner scan = new Scanner(System.in);
		boolean exit = false;
		Telephone telephone = new Telephone();

		System.out.println("\n\n\nTELEPHONE\n\n\nHello and welcome friend\n\n\n");
		System.out.println("- - - - - - - - - - - - - - - ");
		

		//Read a word and send to telephone, exit if input is EXIT
		while(exit == false){
		input = scan.nextLine();
		if (input.equals("EXIT"))exit = true;
		//System.out.println("Input: " + input);
		telephone.checkInput(input);
		/////////////////////////////////////////////////////////////////





		/////////////////////////////////////////////////////////////////
		}
	
	}
}
