import java.util.Scanner;
import java.util.regex.*;
import java.util.HashSet;


public class Telephone{


    public String input;
    private int type = 0;	//1 = 0508 didgits, 2 = 0508 letters, 3 = 0800 didgits...
    public HashSet<String> book = new HashSet<String>();
    private boolean hasLetters = false;

    public void checkInput(String number){
    	
    	this.input = number;	//Save original input to output if invalid
    	
    	if (checkParenthesis(number) == false){
    		//System.out.println("Bracket fail");
    		reject();
    		return;
    	}
    	
    	//Attempt to match input number
    	if (matchInitialCode(number)){
    		valid();
    		String test = formatInitialCode(number);
    		System.out.println("Format Test: " + test);
    	}else  if (matchAreaCode(number)){
    		valid();
    		String test = formatAreaCode(number);
    		System.out.println("Format Test: " + test);

    	}else if (matchMobileCode(number)){
    		valid();
    		String test = formatMobileCode(number);
    		System.out.println("Format Test: " + test);

    	}else{
    		reject();
    		return;
    	}



    	//Chgeck if number is known, if not add it to the book
    	if (book.contains(number)){
    		//System.out.println("Duplicate Number");
    		rejectDuplicate();
    		return;
    	}else{
			book.add(cleanUp(translateLetters(number)));
			//System.out.println("ADDING NUMBERL:  " + number);
    		//System.out.println(book);
    	}

    	reset();
    }


	public String formatInitialCode(String number){
		String result = cleanUp(number);
		int length = result.length();

		//Case where letters are used, only a space after initial code
		if(hasLetters){
			result = insertSpace(result,4);
			return result;
		}

		//All other cases
		switch (length){
			case 10: result = insertSpace(result,7);
			break;
			case 11: result = insertSpace(result,7);
			break;
			case 12: result = insertSpace(result,8);
			break;
		}
		//Add the space after the inital code
		result = insertSpace(result,4);
		return result;
	}

	public String formatAreaCode(String number){
		String result = cleanUp(number);

		//Add Inter didgit space
		result = insertSpace(result,5);
		//Add the space after the AREA code
		result = insertSpace(result,2);


		return result;

	}

	public String formatMobileCode(String number){

		String result = cleanUp(number); 

		//Fix old 025 number, replacing with 0274
		if (type == 10){
			//System.out.println("Reformatting old number: " + number);
			result = fixOldNumber(result);
			//System.out.println("Fixed? " + result);
		}


		int length = result.length();
		switch (length){
			//6 didgits
			case 9: result = insertSpace(result,6);
			//7 didgots
			break;
			case 10: result = insertSpace(result,6);
			break;
			//8 didgits
			case 11: result = insertSpace(result,7);
			break;
		}
		//Add the space after the MOBILE code
		result = insertSpace(result,3);


		//System.out.println("Mobile reformatted: " + result);
		return result;
	}




	public static String fixOldNumber(String number){

		String result = "0274";
		int length = number.length();
		for(int i = 3; i < length; i++){
			result = result + number.charAt(i);
		}
		return result;
	}



	public String translateLetters(String number){
	
	String digits = "22233344455566677778889999";
   	String alphabet = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
    String str = "";
    
    for (char c : number.toCharArray()) {
          int i = alphabet.indexOf(c);
          str += (i == -1 ? c : digits.charAt(i));
      }
      return str;
	}

	public static String insertSpace(String number, int index){
		
		String result = "";
		
		for(int i = 0; i < number.length(); i++){
			if(i == index){
				result = result + " ";
			}
			result = result + number.charAt(i);
		}

		return result;
	}

    //Sets isValid to false if parenthesis...
	public boolean checkParenthesis(String number){

		boolean openParenthesis = false; //Used to detect open paranthesis
    	
    	for(int i = 0; i < number.length(); i++){
    		char c = number.charAt(i);
    		//System.out.println("In loop at position: " + i + ", char: " + c);
    		
    		//Dealing with '('
    		if(c == '('){
    			//System.out.println("Found (");
    			if(openParenthesis == true){
    				//System.out.println("Rejected: Double open paranthesis");
    				//reject();
    				return false;
    			}else{
    				openParenthesis = true;
    				//System.out.println("Open paranthesis");
    			}
    		}
    		// Dealing with ')'
			if(c == ')'){
    			//System.out.println("Found )");
    			if(openParenthesis == false){
    				//System.out.println("Rejected: Double close parenthesis");
    				return false;
    				//reject();
    			}else{
    				openParenthesis = false;
    				//System.out.println("Closed paranthesis");
    			}
    		}
    	}//end of loop

    	if (openParenthesis == true){
    		//System.out.println("Unclosed brackets");
    		return false;
    	}

    	//System.out.println("Should I be here?");
    	return true;
    }

    public boolean matchInitialCode(String number){
    	
    	//System.out.println("Attempting to match: " + number);
    	//TEST BELOW
    	//String test = number.trim......
		
		if(number.matches("([\\(]?(0508)[\\)]?)((\\s[0-9]{3}(\\s|-)[0-9]{3})|([0-9]{6}))")){
			System.out.println("Found 0508 number: ");
			type = 1;
			return true;

		}

		//if (number.matches("(0508)"))

		if(number.matches("[\\(]?(0508)[\\)]?[\\s]?[A-Z0-9]{6}[A-Z]{0,3}")){
			String test = number.replaceAll("\\s|-|\\(|\\)","");
			if(test.matches("(0508)[0-9]{6}")){
				//System.out.println("Sneaky case");
			}else{
				//System.out.println("Found 0508 number");
				type = 2;
				hasLetters = true;
				return true;

			}
		}

		if(number.matches("[\\(]?(0800)[\\)]?((\\s[0-9]{3}(\\s|-)[0-9]{3,4})|([0-9]{6,7}))")){
			System.out.println("Found 0800 Number");
			type = 3;
			return true;

		}
		if(number.matches("[\\(]?(0800)[\\)]?\\s[0-9A-Z]{6,7}[A-Z]{0,2}")){
			String test = number.replaceAll("\\s|-|\\(|\\)","");
			if(test.matches("(0800)[0-9]{6,7}")){
				System.out.println("Sneaky case");
			}else{
				System.out.println("Found 0800 Number");
				type = 4;
				hasLetters = true;
				return true;

			}
		}

		if(number.matches("[\\(]?(0900)[\\)]?\\s?[0-9]{5}")){
			System.out.println("Found 0900 Number");
			type = 5;
			return true;

		}
		if(number.matches("[\\(]?(0900)[\\)]?\\s[A-Z0-9]{5}[A-Z]{0,4}")){
			System.out.println("Found 0900 Number");
			type = 6;
			hasLetters = true;
			return true;

		}

		//Number is not an Initial Code number
		return false;
	}

	public void reject(){
		System.out.println(input + " INV");
		reset();
	}

	public void rejectDuplicate(){

		System.out.println(input + " DUP");
		reset();
	}

	public void valid(){
		System.out.println(input);
		System.out.println("Valid Number of Type: " + type);
	}

	public void reset(){
		System.out.println("\n- - - - - - - - - - - - - - -\n");
		System.out.println();
		hasLetters = false;
		type = 0;
	}

	public String cleanUp(String number){
		String simple = number.replaceAll("\\s|-|\\(|\\)","");
		return simple;
	}

	public boolean matchAreaCode(String number){

		if(number.matches("([\\(]?(02)[\\)]?)((\\s409(\\s|-)[0-9]{4})|((409)[0-9]{4}))")){
			//System.out.println("Found Area Code Number: 02 (Scott Base) ");
			type = 7;
			return true;
		}

		if(number.matches("([\\(]?0(3|4|6|7|9)[\\)]?)((\\s[2-9]{1}[0-9]{2}(\\s|-)[0-9]{4})|([2-9][0-9]{6}))")){
			//System.out.println("Found Area Code Number: 0x ");
			
			//Simplify the string for further testing
			String test = number.replaceAll("\\s|-|\\(|\\)","");
			
			//Further testing, detect invalid sequences
			if(test.matches("[0-9]{2}(911|900|999)[0-9]{4}")){
				//System.out.println("Invalid Sequence for Area Code");
			}else{
				type = 7;
				return true;
				}
		}

		//Not an area code number
		return false;
	}

	public boolean matchMobileCode(String number){

			if(number.matches("[\\(]?(027|022)[\\)]?((\\s[0-9]{3}(\\s|-)[0-9]{4})|([0-9]{7}))")){
				//System.out.println("Found Mobile Number: 027 or 022");
				type = 8;
				return true;
			}

			//String prefix = ("");
			//"[\\(]?(021)[\\)]?((\\s[0-9]{3,4}(\\s|-)[0-9]{4})|([0-9]{6,8})|(\\s[0-9]{6}))"

			if(number.matches("[\\(]?(021)[\\)]?((\\s[0-9]{3,4}(\\s|-)[0-9]{4})|([0-9]{6,8})|(\\s[0-9]{3}(\\s|-)[0-9]{3}))")){
				//System.out.println("Found Mobile Number: 021");
				type = 9;
				return true;
			}

			if(number.matches("[\\(]?(025)[\\)]?((\\s[0-9]{3}(\\s|-)[0-9]{3})|([0-9]{6}))")){
				//System.out.println("Found Valid Mobile Number: 025 REDUNDANT");
				type = 10;
				return true;

			}
			//Not a mobile number
			return false;
	}
}//end of class
