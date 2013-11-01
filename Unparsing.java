/***
 * CS 536: PROJECT 3 - CSX SCANNER
 *
 * Caela Northey (cs login: caela)	905 653 2238 
 * Alan Irish    (cs login: irish) 	906 591 2819
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
		case sym.CAND:
			System.out.print(" && ");
			break;
		case sym.COR:
			System.out.print(" || ");
			break;
		case sym.GEQ:
			System.out.print(" >= ");
			break;
		case sym.GT:
			System.out.print(" > ");
			break;
		case sym.LEQ:
			System.out.print(" <= ");
			break;
		case sym.LT:
			System.out.print(" < ");
			break;
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
		this.visit(n.thisStmt,indent);
		this.visit(n.moreStmts,indent);
	}
	
	void visit(nullStmtsNode n,int indent){}

	void visit(varDeclNode n,int indent){
		
		System.out.print(n.linenum + ":\t");
		genIndent(indent);
		this.visit(n.varType,0);
		System.out.print(" ");
		this.visit(n.varName,0);
		
		//If the variable is initialized, print its value.
		if(!n.initValue.isNull())	
		{
			System.out.print(" = ");
			this.visit(n.initValue, indent);
		}
		System.out.println(";");
	};

	void visit(nullTypeNode n,int ident){}

	void visit(intTypeNode n,int ident){
		System.out.print("int ");
	}
	void visit(boolTypeNode n,int ident){
		System.out.print("bool ");
	}
 	void visit(charTypeNode n,int ident){
		System.out.print("char ");
	}
	void visit(voidTypeNode n,int ident){
		System.out.print("void ");
	}
	void visit(identNode n,int indent){
		System.out.print(n.idname);
	}

	// Extend nameNode's method to handle subscripts CHECK
	void visit(nameNode n,int indent){
		this.visit(n.varName,0);
    if(n.subscriptVal != exprNode.NULL){ //ie, is array
			System.out.print("[");
			this.visit(n.subscriptVal,0);
			System.out.print("]");
		}
	}
	void visit(asgNode n,int indent){
		System.out.print(n.linenum + ":\t"); 
		genIndent(indent);
		this.visit(n.target,0);
		System.out.print(" = ");
		this.visit(n.source,0);
		System.out.print(";");
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

	
	void visit(classNode n,int indent){
		System.out.println(n.linenum + ": class " + n.className.idname + " {");
		this.visit(n.members, indent+1);
		System.out.println(n.linenum + ": } EOF");
	}

	void  visit(memberDeclsNode n,int indent){
		this.visit(n.fields, indent);
		this.visit(n.methods, indent);
	}

	void  visit(methodDeclsNode n,int indent){
		this.visit(n.thisDecl, indent);
		this.visit(n.moreDecls, indent);
	}

	void visit(nullStmtNode n,int indent){}

	void visit(nullReadNode n,int indent){}

	void visit(nullPrintNode n,int indent){}

	void visit(nullExprNode n,int indent){}

	void visit(nullMethodDeclsNode n,int indent){}

	void visit(methodDeclNode n,int indent){
		System.out.print(n.linenum+":\t");
		genIndent(indent);
		this.visit(n.returnType, indent);
		this.visit(n.name, indent);
		System.out.print(" (");
		this.visit(n.args, indent);
		System.out.println(") {");
		this.visit(n.decls, indent+1);
		this.visit(n.stmts, indent+1);
		System.out.print(n.linenum+":\t");
		genIndent(indent);
		System.out.println("}");
	}


	void visit(argDeclsNode n,int indent){
		this.visit(n.thisDecl, indent);
		this.visit(n.moreDecls, indent);
	}

	void visit(nullArgDeclsNode n,int indent){}


	void visit(valArgDeclNode n,int indent){
		this.visit(n.argType, indent);
		System.out.print(" ");
		this.visit(n.argName, indent);
	}

	void visit(arrayArgDeclNode n,int indent){
		this.visit(n.elementType, indent);
		System.out.print(" ");
		this.visit(n.argName, indent);
		System.out.println("[]");
	}

	void visit(constDeclNode n,int indent){
		System.out.print("CONST ");
		this.visit(n.constName, indent);
		System.out.print(" = ");
		this.visit(n.constValue, indent);
		System.out.print(";");
	}

	void visit(arrayDeclNode n,int indent){
		this.visit(n.elementType, indent);
		this.visit(n.arrayName, indent);
		System.out.print("[");
		this.visit(n.arraySize, indent);
		System.out.print("];");
	}

	void visit(whileNode n,int indent){
		if(!n.label.isNull()){ //ie, if lable
			this.visit(n.label,0); //indent should be 0?
		}
		System.out.print("while (");
		this.visit(n.condition,0);
		System.out.print(") {\n");
		this.visit(n.loopBody,indent+1);
		System.out.print("}\n");
		
	}

	void visit(breakNode n,int indent){
		System.out.println("break ");
		this.visit(n.label,0);
	}
	void visit(continueNode n,int indent){
		System.out.println("continue ");
		this.visit(n.label,0);
	}

	void visit(callNode n,int indent){
		this.visit(n.methodName,0);
		System.out.print("(");
		this.visit(n.args, 0); //will print nothing if null
		System.out.print(")");
	}


	void visit(printNode n,int indent){
		if(n.outputValue.linenum==-1){
			System.out.print(n.colnum+":\t");
			genIndent(indent);
			System.out.print("print(");
			if(!n.morePrints.isNull()){
				this.visit(n.morePrints,0);
			}else{ //ie, done printing
				System.out.print(")");
			}
		}else{
			this.visit(n.outputValue,0);
			if(!n.morePrints.isNull()){
				System.out.print(", ");
				this.visit(n.morePrints,0);
			}else{ //ie, done printing
				System.out.print(")");
			}
		}
	}

	void visit(readNode n,int indent){
		if(n.targetVar.varName.linenum==-1){
			System.out.print(n.colnum+":\t");
			genIndent(indent);
			System.out.print("read(");
			if(!n.moreReads.isNull()){
				System.out.print(", ");
				this.visit(n.moreReads,0);
			}else{ //ie, done reading
				System.out.print(")");
			}
		}else{
			this.visit(n.targetVar,0);
			if(!n.moreReads.isNull()){
				System.out.print(", ");
				this.visit(n.moreReads,0);
			}else{ //ie, done reading
				System.out.print(")");
			}
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
		this.visit(n.argVal,0);
		if(!n.moreArgs.isNull()){
			System.out.print(", ");
			this.visit(n.moreArgs,0);
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
		this.visit(n.methodName,0);
		System.out.print("(");
		this.visit(n.methodArgs,0);
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
		System.out.print("true");
	}

	void visit(falseNode n,int indent){
		System.out.print("false");
	}

}
