/***
 * CS 536: PROJECT 3 - CSX SCANNER
 *
 * Caela Northey (cs login: caela)	905 653 2238 
 * Alan Irish    (cs login: irish) 906 591 2819
 *
 *
 * DUE DATE: FRIDAY NOV 1, 2013
 *
 *  This is the driver of the compilation process.
 *  The CSX Lite program is scanned and parsed, and an AST is built.
 *  The AST is then unparsed.
 *  Nothing needs to be changed here.
 * 
 ***/

import java.io.*;
import java_cup.runtime.*;

public class P3 {
  public static void
  main(String args[]) throws java.io.IOException {  

	String fileName;
	fileName = ArgsProcessor.getArg(args);
	
    java.io.FileInputStream yyin = null;
    if (ArgsProcessor.openedFile != null) {
    	// file chooser GUI already opened a file; create a 
    	//  FileInputStream from it
    	yyin = new FileInputStream(ArgsProcessor.openedFile);
    } else { // open file using name provided
    	try {
    		yyin = new FileInputStream(fileName);  
    	} catch (FileNotFoundException notFound){
    		System.out.println ("Error: unable to open file: "+fileName);
    		System.exit(-1);
    	}
    }
    
    Scanner.init(yyin); // Initialize Scanner class for parser

    parser csxParser = new parser(); 
    System.out.print ("Begin test of CSX parser.");
	System.out.println (" Parsing file "+ fileName+ ".");
    Symbol root=null;
    try {
    	root = csxParser.parse(); // do the parse
    	
    	System.out.println (fileName +" parsed correctly.");
	
    } catch (Exception e) {
    	System.out.println ("Compilation terminated due to syntax errors.");  
    	System.exit(0);
    }
    System.out.println ("Here is its unparsing:");
  
    Unparsing unparse = new Unparsing();

		unparse.visit((classNode)root.value,0);

    return;
    }
}
