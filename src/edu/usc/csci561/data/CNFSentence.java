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
	private boolean isValid;
	

	public CNFSentence() {
		symbols = new LinkedHashSet<Symbol>();
		isValid = true;
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

	public boolean addSymbol(Symbol s) {
		return this.symbols.add(s);
	}

	public Set<Symbol> getPositiveSymbols() {
		Set<Symbol> result = new HashSet<Symbol>();
		Iterator<Symbol> iter = symbols.iterator();
		while (iter.hasNext()) {
			Symbol s = iter.next();
			if (s.isPositive()) {
				result.add(s);
			}
		}
		return result;
	}

	public Set<Symbol> getNegativeSymbols() {
		Set<Symbol> result = new HashSet<Symbol>();
		Iterator<Symbol> iter = symbols.iterator();
		while (iter.hasNext()) {
			Symbol s = iter.next();
			if (!s.isPositive()) {
				result.add(s);
			}
		}
		return result;
	}

	public boolean isSame(CNFSentence o) {
		LinkedHashSet<Symbol> a = new LinkedHashSet<Symbol>(
				getPositiveSymbols());
		a.retainAll(o.getNegativeSymbols());
		LinkedHashSet<Symbol> b = new LinkedHashSet<Symbol>(
				getNegativeSymbols());
		b.retainAll(o.getPositiveSymbols());
		LinkedHashSet<Symbol> c = new LinkedHashSet<Symbol>(a);
		c.addAll(b);
		return c.size() == 0;
	}

	public boolean contains(Symbol s) {
		return this.symbols.contains(s);
	}

	public void removeAll() {
		this.symbols.clear();
	}

	/**
	 * @return the isValid
	 */
	public boolean isValid() {
		return isValid;
	}

	/**
	 * @param isValid the isValid to set
	 */
	public void setValid(boolean isValid) {
		this.isValid = isValid;
	}

}
