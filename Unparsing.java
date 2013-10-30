/***
 * CS 536: PROJECT 3 - CSX SCANNER
 *
 * Caela Northey (cs login: caela)	905 653 2238 
 * Alan Irish    (cs login: irish) 906 591 2819
 *
 *
 * DUE DATE: FRIDAY NOV 1, 2013
 *
 * The following methods unparse  AST nodes used in CSX Lite
 *  You will need to complete the methods to unparse all
 *    of CSX
 *  Be sure to extend method printOp to handle all CSX operators
 ***/
public class Unparsing extends Visitor {

	static void genIndent(int indent){
		for (int i=1;i<=indent;i++)
			//			System.out.print("\t");
			System.out.print("   ");
	}
	// Extend this to handle all CSX binary operators
	static void printOp(int op) {
		switch (op) {
		case sym.PLUS:
			System.out.print(" + ");
			break;
		case sym.MINUS:
			System.out.print(" - ");
			break;
		case sym.TIMES:
			System.out.print(" * ");
			break;
		case sym.SLASH:
			System.out.print(" / ");
			break;
		case sym.EQ:
			System.out.print(" == ");
			break;
		case sym.NOTEQ:
			System.out.print(" != ");
			break;
     /* GT, LT, etc */
		default:
			throw new Error();
		}
	}


	void visit(csxLiteNode n,int indent){
		//System.out.println ("\n\nStart 2nd unparsing:\n"); 
		System.out.println(n.linenum + ":\t" + " {");
		this.visit(n.progDecls,1);
		this.visit(n.progStmts,1);
		System.out.println(n.linenum + ":\t" + " } EOF");
	}

	void visit(fieldDeclsNode n,int indent){
		this.visit(n.thisField,indent);
		this.visit(n.moreFields,indent);
	}
	void visit(nullFieldDeclsNode n,int indent){}

	void visit(stmtsNode n,int indent){
		//System.out.println ("In stmtsNode\n");
		this.visit(n.thisStmt,indent);
		this.visit(n.moreStmts,indent);

	}
	void visit(nullStmtsNode n,int indent){}

	// Extend varDeclNode's method to handle initialization
	void visit(varDeclNode n,int indent){
		System.out.print(n.linenum + ":\t");
		genIndent(indent);
		this.visit(n.varType,0);
		System.out.print(" ");
		this.visit(n.varName,0);
		System.out.println(";");
	};

	void visit(nullTypeNode n,int ident){}

	void visit(intTypeNode n,int ident){
		System.out.print("int");
	}
	void visit(boolTypeNode n,int ident){
		System.out.print("bool");
	}
 	void visit(charTypeNode n,int ident){
		System.out.print("char");
	}
	void visit(voidTypeNode n,int ident){
		System.out.println("Unparsing for voidTypeNode not yet implemented");
	}
	void visit(identNode n,int indent){
		System.out.print(n.idname);
	}

	// Extend nameNode's method to handle subscripts CHECK
	void visit(nameNode n,int indent){
		System.out.print(n.varName.idname);
    if(n.subscriptVal != experNode.NULL){ //ie, is array
			System.out.print("["+n.subscriptVal+"]");
		}
	}
	void visit(asgNode n,int indent){
		System.out.print(n.linenum + ":\t"); 
		genIndent(indent);
		this.visit(n.target,0);
		System.out.print(" = ");
		this.visit(n.source,0);
		System.out.println(";");
	} 
	void visit(incrementNode n,int indent){
		System.out.println(n.target.varName.idname+"++");
	}

	void visit(decrementNode n,int indent){
		System.out.println(n.target.varName.idname+"--");
	}

	// Extend ifThenNode's method to handle else parts
	void visit(ifThenNode n,int indent){
		System.out.print(n.linenum + ":\t");
		genIndent(indent);
		System.out.print("if (");
		this.visit(n.condition,0);
		System.out.println(")");
		this.visit(n.thenPart,indent+1);
		// No else parts in CSXlite
	}

	void visit(blockNode n,int indent){
		System.out.print(n.linenum + ":\t");
		genIndent(indent);
		System.out.println("{");
		this.visit(n.decls,indent+1);
		this.visit(n.stmts,indent+1);
		System.out.print(n.linenum + ":\t");
		genIndent(indent);
		System.out.println("}");
	}


	void visit(binaryOpNode n,int indent){

		System.out.print("(");
		this.visit(n.leftOperand,0);
		printOp(n.operatorCode);
		this.visit(n.rightOperand,0);
		System.out.print(")");
	}



	void visit(intLitNode n,int indent){
		if (n.intval>=0)
			System.out.print(n.intval);
		else	System.out.print("~"+-n.intval);
	}



	// Extend these unparsing methods to correctly unparse CSX AST nodes

	void visit(classNode n,int indent){
		System.out.println(n.linenum + ": class " + n.className.idname + " {");
		this.visit(n.members, indent+1);
		System.out.println(n.linenum + ": } EOF");
	}

	void  visit(memberDeclsNode n,int indent){
		this.visit(n.fields, indent);
		this.visit(n.methods, indent+1);
	}

	void  visit(methodDeclsNode n,int indent){
		this.visit(n.thisDecl, indent);
	}

	void visit(nullStmtNode n,int indent){}

	void visit(nullReadNode n,int indent){}

	void visit(nullPrintNode n,int indent){}

	void visit(nullExprNode n,int indent){}

	void visit(nullMethodDeclsNode n,int indent){}

	void visit(methodDeclNode n,int indent){
		System.out.println("Unparsing for methodDeclNode not yet implemented");
	}


	void visit(argDeclsNode n,int indent){
		System.out.println("Unparsing for argDeclsNode not yet implemented");
	}

	void visit(nullArgDeclsNode n,int indent){}


	void visit(valArgDeclNode n,int indent){
		System.out.println("Unparsing for valArgDeclNode not yet implemented");
	}

	void visit(arrayArgDeclNode n,int indent){
		System.out.println("Unparsing for arrayArgDeclNode not yet implemented");
	}

	void visit(constDeclNode n,int indent){
		System.out.println("Unparsing for constDeclNode not yet implemented");
	}

	void visit(arrayDeclNode n,int indent){
		System.out.println("Unparsing for arrayDeclNode not yet implemented");
	}

/** v caela v **/
	void visit(whileNode n,int indent){
		if(!n.label.isNull()){ //ie, if lable
			this.visit(n.label,0); //indent should be 0?
		}
		System.out.print("while (");
		this.visit(n.condition,0);
		System.out.print(") {\n");
		this.visit(n.loopBody,ident+1);
		System.out.print("}\n");
		
	}

	void visit(breakNode n,int indent){
		if(!n.label.isNUll()){ //ie, if lable (identNode)
			System.out.print(n.label.idname+" : ");
		}
		System.out.println("break;");
	}
	void visit(continueNode n,int indent){
		if(!n.label.isNull()){ //ie, if lable (identNode)
			System.out.print(n.label.idname+" : ");
		}
		System.out.println("continue;");
	}

	void visit(callNode n,int indent){
		System.out.print(n.methodName.idName+"(");
		this.visit(n.args, 0); //will print nothing if null
		System.out.print(")");
	}


	void visit(printNode n,int indent){
		this.visit(n.outputValue,0);
		if(!n.morePrints.isNull()){
			System.out.print(",");
			this.visit(n.morePrints,0);
		}
	}

	void visit(readNode n,int indent){
		this.visit(n.targetVar,0);
		if(!n.moreReads.isNull()){
			System.out.print(",");
			this.visit(n.moreReads,0);
		}
	}

	void visit(returnNode n,int indent){
		System.out.print("return");
		if(!n.returnVal.isNull()){
			this.visit(n.returnVal,0);
		}
		System.out.println(";");
	}


	void visit(argsNode n,int indent){
		this.visit(n.argVal,ident);
		if(!n.moreArgs.isNull()){
			System.out.print(", ");
			this.visit(n.moreArgs);
		}
	}

	void visit(nullArgsNode n,int indent){}

	void visit(castNode n,int indent){
		System.out.print("(");
		this.visit(n.resultType,0);
		System.out.print(")");
		this.visit(n.operand, 0);
	}

	void visit(fctCallNode n,int indent){
		System.out.print(n.methodName.idname+"(");
		this.visit(n.methodArgs);
		System.out.print(")");
	}

	void visit(unaryOpNode n,int indent){ //only 1 unary op = !
		System.out.print("!");
		this.visit(n.operand,0);
	}


	void visit(charLitNode n,int indent){
		System.out.print(n.charval);
	}

	void visit(strLitNode n,int indent){
		System.out.print(n.strval);
	}


	void visit(trueNode n,int indent){
		System.out.println("true");
	}

	void visit(falseNode n,int indent){
		System.out.print("false");
	}
/* ^ caela ^ */

}
