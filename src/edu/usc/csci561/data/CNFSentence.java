/**
 * 
 */
package edu.usc.csci561.data;

import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.Set;

/**
 * This class represents the Conjunctive Normal Form of Propositional Logic
 * sentences
 * 
 * @author mohit aggarwl
 * 
 */
public class CNFSentence {
	private Set<Symbol> symbols;
	private String operator;

	public CNFSentence() {
		symbols = new LinkedHashSet<Symbol>();
	}

	/**
	 * @return the symbols
	 */
	public Set<Symbol> getSymbols() {
		return symbols;
	}

	/**
	 * @param symbols
	 *            the symbols to set
	 */
	public void setSymbols(Set<Symbol> symbols) {
		this.symbols = symbols;
	}

	public void addSymbol(Symbol s) {
		this.symbols.add(s);
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
	
	public Set<Symbol> getPositiveSymbols(){
		Set<Symbol> result = new HashSet<Symbol>();
		Iterator<Symbol> iter = symbols.iterator();
		while(iter.hasNext()){
			Symbol s = iter.next();
			if(s.isPositive()){
				result.add(s);
			}
		}
		return result;
	}
	
	public Set<Symbol> getNegativeSymbols(){
		Set<Symbol> result = new HashSet<Symbol>();
		Iterator<Symbol> iter = symbols.iterator();
		while(iter.hasNext()){
			Symbol s = iter.next();
			if(!s.isPositive()){
				result.add(s);
			}
		}
		return result;
	}
	
	public boolean isSame(CNFSentence o){
		LinkedHashSet<Symbol> a = new LinkedHashSet<Symbol>(getPositiveSymbols());
		a.retainAll(o.getNegativeSymbols());
		LinkedHashSet<Symbol> b = new LinkedHashSet<Symbol>(getNegativeSymbols());
		b.retainAll(o.getPositiveSymbols());
		LinkedHashSet<Symbol> c = new LinkedHashSet<Symbol>(a);
		c.addAll(b);
		return c.size() == 0;
	}

}
