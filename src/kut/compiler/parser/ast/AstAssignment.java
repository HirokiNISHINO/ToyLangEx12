package kut.compiler.parser.ast;

import java.io.IOException;

import kut.compiler.compiler.CodeGenerator;
import kut.compiler.exception.CompileErrorException;
import kut.compiler.lexer.Token;
import kut.compiler.symboltable.SymbolType;

public class AstAssignment extends AstNode 
{
	/**
	 * 
	 */
	protected Token t;
	
	protected AstIdentifier	varname;
	protected AstNode			rhs;
	
	/**
	 * @param t
	 */
	public AstAssignment(AstIdentifier varname, AstNode rhs, Token t)
	{
		this.varname 	= varname	;
		this.rhs 		= rhs		;
		this.t 			= t			;
	}
	
	/**
	 *
	 */
	public void printTree(int indent) {
		this.println(indent, "assignment:" + t);
		varname.printTree(indent + 1);
		rhs.printTree(indent + 1);
	}

	

	/**
	 *
	 */
	@Override
	public void cgen(CodeGenerator gen) throws IOException, CompileErrorException
	{	
		rhs.cgen(gen);
		String id = this.varname.getIdentifier();
		
		SymbolType t = gen.getSymbolType(id);
		
		if (t != SymbolType.GlobalVariable) {
			throw new CompileErrorException("expecting a global variable here. but found: " + id);
		}
		
		gen.printCode("mov [rel " + gen.getGlobalVariableLabel(id) + "], rax");
		
		return;
	}
	


}
