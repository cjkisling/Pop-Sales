import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Scanner;
import java.io.*;

/*
 * PopSales
 * 
 * Program Reads in dat File validates and formats it and if it is 
 * correct then it will either print to a correct prt file or an incorrect prt file
 * 
 * CJKisling™
 * 
 * This is an update
 *yaaaasss
 * 
 * Date: 2/7/2018
 */
public class PopSales {
	//Variables
	static SimpleDateFormat simpleDF;
	static Scanner fileScanner, scanner;
	static DecimalFormat df6v2;
	static Date today;
	static PrintWriter pw;
	static PrintWriter errPw;
	static String iFName, iLName, iAdress, iCity, iState, iZip, iTeam, iPopType, iCases, vError, oPopType;
	static String oErrorRecord;
	static double cDepos, cTotal, cATeamSold, cBTeamSold, cCTeamSold, cDTeamSold, cETeamSold, cGrandTotal;
	static int vPopType, vCases, cCokeCtr, cDCokeCtr, cMYCtr, cChCokeCtr, cDChCokeCtr, cSpriteCtr, cATeamCtr, cBTeamCtr, cCTeamCtr, cDTeamCtr, cETeamCtr;
	static int vZip;
	static boolean eof = false;
	static boolean errSw = false;
	static int i;
	static int itemQty [ ] = new int [ 7 ];
	
	public static void main(String[] args) {
		//main logic for program looping and calling moduales
		init();		
		headings();
			while(!eof) {	
			    validation();
			    if(errSw) {
			    	errorPrint();
			    }
			    else {
			    	calcs();
			    	print();
				}
			    read();
			}
		totals();
		closing();
		
		for(i = 0; i < 7; i = i + 1)
		{
			itemQty [ i ] =0;
		}
		 
		for(i = 2; i < 6; i = i + 2)
		{
			itemQty[i] = i + 3;
			System.out.print(itemQty[i]);
		}

	}
	
	private static void init() {
		//Setting the dat file to be able to read by the program
		try {
			fileScanner = new Scanner(new File("JAVPOPSL.DAT"));
		} catch (FileNotFoundException e) {
			System.out.println("File Error");
			System.exit(1);
		}
		try {
			pw = new PrintWriter(new File("JAVPOPSL.PRT"));
		}
		catch(FileNotFoundException e){
			
			System.out.println("Output file error");
		}
		try {
			errPw = new PrintWriter(new File("JAVPOPER.PRT"));
		}
		catch(FileNotFoundException e){
			
			System.out.println("Output file error");
		}
	    scanner = new Scanner(System.in);
	    scanner.useDelimiter("\r\n");
	    fileScanner.useDelimiter("\r\n");
	    //format the decimal for mat date and simpledate format
	    df6v2 = new DecimalFormat("###,###.00");
	    today = new Date();
	    simpleDF = new SimpleDateFormat("MM DD, YYYY");	
	    read();
	}
	
	private static void headings() {
		//prints headings
		String Headings;
		Headings = "First Name\t\tLast Name\t\tAdress\t\t\tCity\t\t\tState\t\tZip\t\t\tPop Type\t\tNumber of Cases\t\tTotal\t\tTeam\r\n";
		pw.println(Headings);				
	}


	private static void read() {
		String record;		
		//section up the records into varables
	    if(fileScanner.hasNext()){
	    	record = fileScanner.next();
	    	iFName = record.substring(0,15);
	    	iLName = record.substring(15,30);
	    	iAdress = record.substring(30,45);
	    	iCity = record.substring(45,55);
	    	iState = record.substring(55,57);
	    	iZip = record.substring(57,65);	    	
	    	iPopType = record.substring(65,67);	    	    	
	    	iCases = record.substring(67,69);	    		    	
	    	iTeam = record.substring(69,70);
	    }
	    else{
	    	eof = true;
	    }
	}
	
	private static void validation() {
		errSw = false;
		cTotal = 0;
		//checks to see if there is an error in the read in code if so turn error switch on
		if(iState.equals("IA") || iState.equals("NE") || iState.equals("WI") || iState.equals("MI") || iState.equals("IL") || iState.equals("MO")) {			
		}
		else{
			vError = "State Error";
			errSw = true;
			return;
		}
		try {
    		vZip = Integer.parseInt(iZip);
    	}
    	catch(Exception e){				
			vError = "Zip Non-Numeric";
			errSw = true;
			return;
		}
		
		try {
    		vPopType = Integer.parseInt(iPopType);
    	}
    	catch(Exception e){				
			vError = "Pop Type Non-Numeric";
			errSw = true;
			return;
		}	
		
		try {
    		vCases = Integer.parseInt(iCases);
    	}
    	catch(Exception e){				
			vError = "Number of Cases Non-Numeric";
			errSw = true;
			return;
		}
		
		if((iFName.trim()).isEmpty() || (iLName.trim()).isEmpty() || (iAdress.trim()).isEmpty() || (iCity.trim()).isEmpty()){
			vError = "Name, Adress or City is Blank";
			errSw = true;
			return;
		}
		else{
			
		}
		
		
		
		if(vPopType >= 1 && vPopType <= 6) {
		}
		else{
			vError = "Pop Type out of range Error";
			errSw = true;
			return;
		}
		
		if(vCases < 1 || vCases > 99) {
			vError = "Number of Cases out of range Error";
			errSw = true;
			return;
		}
		else{
			
		}
		
		if(!iTeam.matches("A|B|C|D|E")) {
			vError = "Team out of range Error";
			errSw = true;
			return;
		}
		else{
			
		}		
	}	

	private static void calcs() {
		//switches the state name for what deposit is needed
		switch(iState) {
			case "IA":
				cDepos = .05;
				break;
			case "NE":
				cDepos = .05;
				break;
			case "WI":
				cDepos = .05;
				break;
			case "MI":
				cDepos = .1;
				break;
			default:
				cDepos = Double.NaN;
				break;				
		}
		//switches poptype from a number to a word and counts up how many cases there are of each kind of coke
		switch(vPopType) {
			case 1:
				oPopType = "Coke             ";				
				cCokeCtr += vCases;
				break;
			case 2:
				oPopType = "Diet Coke        ";
				cDCokeCtr += vCases;
				break;
			case 3:
				oPopType = "Mello Yello      ";
				cMYCtr += vCases;
				break;
			case 4:
				oPopType = "Cherry Coke      ";
				cChCokeCtr += vCases;
				break;
			case 5:
				oPopType = "Diet Cherry Coke";
				cDChCokeCtr += vCases;
				break;
			case 6:
				oPopType = "Sprite          ";
				cSpriteCtr += vCases;
			 	break;			
		}
		//calculates total costs
		cTotal = (vCases *18.71)+((vCases*24)*cDepos);
		cGrandTotal += cTotal;
		//gives an amount that the team sold
		switch(iTeam) {
			case "A":
				cATeamSold += cTotal;
				break;
			case "B":
				cBTeamSold += cTotal;
				break;
			case "C":
				cCTeamSold += cTotal;
				break;
			case "D":
				cDTeamSold += cTotal;
				break;
			case "E":
				cETeamSold += cTotal;
				break;
		}
		
		
		
	}
	//prints valid reords
	public static void print() {
		String record, oTotal;
		oTotal = df6v2.format(cTotal);
		record = "\t\t";
		pw.print(iFName+record);
		pw.print(iLName+record);		
		pw.print(iAdress+record);
		pw.print(iCity+record);
		pw.print(iState+record);
		pw.print(iZip+record);
		pw.print(oPopType+"\t");
		pw.print(vCases+record+"\t");
		pw.print("$"+oTotal+record);
		pw.println(iTeam);	
		
	}
	//perints invalid records
	private static void errorPrint() {		
		oErrorRecord = iFName+iLName+iAdress+iCity+iState+iZip+iPopType+iCases+iTeam+"\t\t"+vError+"\r\n";		
		errPw.format(oErrorRecord);	
	}
	
	public static void totals() {
		//printing toals and grand totals
		String oATeamSold, oBTeamSold, oCTeamSold, oDTeamSold, oETeamSold, oGrandTotal;
		oATeamSold = df6v2.format(cATeamSold);
		oBTeamSold = df6v2.format(cBTeamSold);
		oCTeamSold = df6v2.format(cCTeamSold);
		oDTeamSold = df6v2.format(cDTeamSold);
		oETeamSold = df6v2.format(cETeamSold);
		oGrandTotal = df6v2.format(cGrandTotal);
		
		pw.println("\r\n\r\n\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\tGrand Total:    $"+oGrandTotal);
		pw.println("\r\n\r\nTotals For Teams");
		pw.println("Team A: $" + oATeamSold);
		pw.println("Team B: $" + oBTeamSold);
		pw.println("Team C: $" + oCTeamSold);
		pw.println("Team D: $" + oDTeamSold);
		pw.println("Team E: $" + oETeamSold);
		
		
		pw.println("\r\n\r\nTotal Number of Pop");
		pw.println("Coke:             " + cCokeCtr);
		pw.println("Diet Coke:        " + cDCokeCtr);
		pw.println("Mello Yello:      " + cMYCtr);
		pw.println("Cherry Coke:      " + cChCokeCtr);
		pw.println("Diet Cherry Coke: " + cDChCokeCtr);
		pw.println("Sprite:           " + cSpriteCtr);
	}
	
	private static void closing() {
		//closing files
		pw.close();
		errPw.close();
	}
	
	
}