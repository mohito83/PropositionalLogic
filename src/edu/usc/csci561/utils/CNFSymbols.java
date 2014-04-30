/**
 * 
 */
package edu.usc.csci561.utils;

import java.util.Set;

import edu.usc.csci561.data.CNFSentence;
import edu.usc.csci561.data.Symbol;

/**
 * @author mohit aggarwl
 *
 */
public class CNFSymbols {
	Set<Symbol> clause1Symbols, clause1PositiveSymbols,
			clause1NegativeSymbols;

	Set<Symbol> clause2Symbols, clause2PositiveSymbols,
			clause2NegativeSymbols;

	Set<Symbol> positiveInClause1NegativeInClause2,
			negativeInClause1PositiveInClause2;

	public CNFSymbols(CNFSentence clause1, CNFSentence clause2) {

		clause1Symbols = clause1.getSymbols();
		clause1PositiveSymbols = clause1.getPositiveSymbols();
		clause1NegativeSymbols = clause1.getNegativeSymbols();

		clause2Symbols = clause2.getSymbols();
		clause2PositiveSymbols = clause2.getPositiveSymbols();
		clause2NegativeSymbols = clause2.getNegativeSymbols();

		positiveInClause1NegativeInClause2 = CartesianSetUtils.intersection(
				clause1PositiveSymbols, clause2NegativeSymbols);
		negativeInClause1PositiveInClause2 = CartesianSetUtils.intersection(
				clause1NegativeSymbols, clause2PositiveSymbols);

	}

	public Set<Symbol> getComplementedSymbols() {
		return CartesianSetUtils.union(positiveInClause1NegativeInClause2,
				negativeInClause1PositiveInClause2);
	}

	/**
	 * @return the clause1Symbols
	 */
	public Set<Symbol> getClause1Symbols() {
		return clause1Symbols;
	}

	/**
	 * @param clause1Symbols the clause1Symbols to set
	 */
	public void setClause1Symbols(Set<Symbol> clause1Symbols) {
		this.clause1Symbols = clause1Symbols;
	}

	/**
	 * @return the clause1PositiveSymbols
	 */
	public Set<Symbol> getClause1PositiveSymbols() {
		return clause1PositiveSymbols;
	}

	/**
	 * @param clause1PositiveSymbols the clause1PositiveSymbols to set
	 */
	public void setClause1PositiveSymbols(Set<Symbol> clause1PositiveSymbols) {
		this.clause1PositiveSymbols = clause1PositiveSymbols;
	}

	/**
	 * @return the clause1NegativeSymbols
	 */
	public Set<Symbol> getClause1NegativeSymbols() {
		return clause1NegativeSymbols;
	}

	/**
	 * @param clause1NegativeSymbols the clause1NegativeSymbols to set
	 */
	public void setClause1NegativeSymbols(Set<Symbol> clause1NegativeSymbols) {
		this.clause1NegativeSymbols = clause1NegativeSymbols;
	}

	/**
	 * @return the clause2Symbols
	 */
	public Set<Symbol> getClause2Symbols() {
		return clause2Symbols;
	}

	/**
	 * @param clause2Symbols the clause2Symbols to set
	 */
	public void setClause2Symbols(Set<Symbol> clause2Symbols) {
		this.clause2Symbols = clause2Symbols;
	}

	/**
	 * @return the clause2PositiveSymbols
	 */
	public Set<Symbol> getClause2PositiveSymbols() {
		return clause2PositiveSymbols;
	}

	/**
	 * @param clause2PositiveSymbols the clause2PositiveSymbols to set
	 */
	public void setClause2PositiveSymbols(Set<Symbol> clause2PositiveSymbols) {
		this.clause2PositiveSymbols = clause2PositiveSymbols;
	}

	/**
	 * @return the clause2NegativeSymbols
	 */
	public Set<Symbol> getClause2NegativeSymbols() {
		return clause2NegativeSymbols;
	}

	/**
	 * @param clause2NegativeSymbols the clause2NegativeSymbols to set
	 */
	public void setClause2NegativeSymbols(Set<Symbol> clause2NegativeSymbols) {
		this.clause2NegativeSymbols = clause2NegativeSymbols;
	}

	/**
	 * @return the positiveInClause1NegativeInClause2
	 */
	public Set<Symbol> getPositiveInClause1NegativeInClause2() {
		return positiveInClause1NegativeInClause2;
	}

	/**
	 * @param positiveInClause1NegativeInClause2 the positiveInClause1NegativeInClause2 to set
	 */
	public void setPositiveInClause1NegativeInClause2(
			Set<Symbol> positiveInClause1NegativeInClause2) {
		this.positiveInClause1NegativeInClause2 = positiveInClause1NegativeInClause2;
	}

	/**
	 * @return the negativeInClause1PositiveInClause2
	 */
	public Set<Symbol> getNegativeInClause1PositiveInClause2() {
		return negativeInClause1PositiveInClause2;
	}

	/**
	 * @param negativeInClause1PositiveInClause2 the negativeInClause1PositiveInClause2 to set
	 */
	public void setNegativeInClause1PositiveInClause2(
			Set<Symbol> negativeInClause1PositiveInClause2) {
		this.negativeInClause1PositiveInClause2 = negativeInClause1PositiveInClause2;
	}

}