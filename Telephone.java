import java.util.Scanner;
import java.util.regex.*;



public class Telephone{


    public String input;
    private boolean isValid = false;
    private int type = 0;	//1 = 0508 didgits, 2 = 0508 letters, 3 = 0800 didgits...

    public void checkInput(String number){
    	
    	this.input = number;	//Save original input to output if invalid
    	
    	matchInitialCode(number);
    	
    	if(isValid == false)reject();
    	if(isValid == true)valid();
    }

    
    public void checkParenthesis(String number){

    	System.out.println("Checking Parenthesis on number: " + number);
    	String str = number; //.replaceAll("\\s+","");	//Removes all whitespace
    	System.out.println("Trim worked?" + str);

		boolean openParenthesis = false; //Used to detect open paranthesis
		System.out.println("Before loop");
    	for(int i = 0; i < str.length(); i++){
    		char c = str.charAt(i);
    		System.out.println("In loop at position: " + i + ", char: " + c);
    		
    		//Dealing with '('
    		if(c == '('){
    			System.out.println("Found (");
    			if(openParenthesis == true){
    				System.out.println("Rejected: Double open paranthesis");
    				reset();
    			}else{
    				openParenthesis = true;
    				System.out.println("Open paranthesis");
    			}
    		}

    		// Dealing with ')'
			if(c == ')'){
    			System.out.println("Found )");
    			if(openParenthesis == false){
    				System.out.println("Rejected: Double close parenthesis");
    				reset();
    			}else{
    				openParenthesis = false;
    				System.out.println("Closed paranthesis");
    			}
    		}


    	}//end of loop

    }

    public void matchInitialCode(String number){
    	
    	//System.out.println("Attempting to match: " + number);
    	//TEST BELOW
    	//String test = number.trim......
		
		if(number.matches("([\\(]?(0508)[\\)]?)((\\s[0-9]{3}\\s[0-9]{3})|([0-9]{6}))")){
			System.out.println("Found 0508 number: " + number);
			isValid = true;
			type = 1;
		}
		if(number.matches("[\\(]?(0508)[\\)]?[\\s]?[A-Z]{6,9}")){
			System.out.println("Found 0508 number");
			isValid = true;
			type = 2;
		}

		if(number.matches("[\\(]?(0800)[\\)]?\\s[0-9]{3}\\s[0-9]{3,4}")){
			System.out.println("Found 0800 Number");
			isValid = true;
			type = 3;
		}
		if(number.matches("[\\(]?(0800)[\\)]?\\s[A-Z]{6,9}")){
			System.out.println("Found 0800 Number");
			isValid = true;
			type = 4;
		}

		if(number.matches("[\\(]?(0900)[\\)]?\\s[0-9]{5}")){
			System.out.println("Found 0900 Number");
			isValid = true;
			type = 5;
		}
		if(number.matches("[\\(]?(0900)[\\)]?\\s[A-Z]{5,9}")){
			System.out.println("Found 0900 Number");
			isValid = true;
			type = 6;
		}
	}

	public void reject(){
		System.out.println(input + " INV");
		reset();
	}

	public void valid(){
		System.out.println("Valid Number of Type: " + type);
		reset();
	}

	public void reset(){
		isValid = false;
		System.out.println("\n- - - - - - - - - - - - - - -\n");
		System.out.println();
	}
   

}//end of class
