/**
 * 
 */
package edu.usc.csci561.data;

import java.util.ArrayList;
import java.util.List;

/**
 * This class represents the Conjunctive Normal Form of Propositional Logic
 * sentences
 * 
 * @author mohit aggarwl
 * 
 */
public class CNFSentence {
	private List<Symbol> symbols;
	private String operator;

	public CNFSentence() {
		symbols = new ArrayList<Symbol>();
	}

	/**
	 * @return the symbols
	 */
	public List<Symbol> getSymbols() {
		return symbols;
	}

	/**
	 * @param symbols
	 *            the symbols to set
	 */
	public void setSymbols(List<Symbol> symbols) {
		this.symbols = symbols;
	}

	/**
	 * @return the operator
	 */
	public String getOperator() {
		return operator;
	}

	/**
	 * @param operator
	 *            the operator to set
	 */
	public void setOperator(String operator) {
		this.operator = operator;
	}

}
