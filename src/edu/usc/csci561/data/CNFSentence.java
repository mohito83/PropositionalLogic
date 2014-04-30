/**
 * 
 */
package edu.usc.csci561.data;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

import edu.usc.csci561.utils.CNFSymbolComparator;

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

	/**
	 * 
	 * @param s
	 * @return
	 */
	public boolean addSymbol(Symbol s) {
		return this.symbols.add(s);
	}

	/**
	 * 
	 * @return
	 */
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

	/**
	 * 
	 * @return
	 */
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

	/**
	 * 
	 * @param o
	 * @return
	 */
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

	/**
	 * 
	 * @param s
	 * @return
	 */
	public boolean contains(Symbol s) {
		return this.symbols.contains(s);
	}

	/**
	 * 
	 */
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
	 * @param isValid
	 *            the isValid to set
	 */
	public void setValid(boolean isValid) {
		this.isValid = isValid;
	}

	/**
	 * 
	 */
	public boolean equals(Object o) {
		CNFSentence cnf = (CNFSentence) o;
		List<Symbol> symbolList1 = new ArrayList<Symbol>(this.getSymbols());
		List<Symbol> symbolList2 = new ArrayList<Symbol>(cnf.getSymbols());
		if (symbolList1.size() != symbolList2.size()) {
			return false;
		} else {
			Collections.sort(symbolList1, new CNFSymbolComparator());
			Collections.sort(symbolList2, new CNFSymbolComparator());

			for (int i = 0; i < symbolList1.size(); i++) {
				Symbol s1 = symbolList1.get(i);
				Symbol s2 = symbolList2.get(i);
				if (!s1.getValue().equals(s2.getValue())) {
					return false;
				} else {
					if (s1.isPositive() != s2.isPositive()) {
						return false;
					}
				}
			}
		}
		return true;
	}
	
	/**
	 * 
	 */
	public int hashCode() {
		int result = 17;
		Iterator<Symbol> iter = this.symbols.iterator();
		while(iter.hasNext()){
			Symbol s= iter.next();
			result = 37 * result + s.getValue().hashCode();
		}
		return result;
	}
}
