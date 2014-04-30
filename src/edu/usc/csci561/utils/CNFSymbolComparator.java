/**
 * 
 */
package edu.usc.csci561.utils;

import java.util.Comparator;

import edu.usc.csci561.data.Symbol;

/**
 * @author mohit aggarwl
 * 
 */
public class CNFSymbolComparator implements Comparator<Symbol> {

	public int compare(Symbol symbol1, Symbol symbol2) {
		Symbol one = symbol1;
		Symbol two = symbol2;
		return one.getValue().compareTo(two.getValue());
	}

}
