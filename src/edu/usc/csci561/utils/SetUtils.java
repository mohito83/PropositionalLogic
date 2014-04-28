/**
 * 
 */
package edu.usc.csci561.utils;

import java.util.LinkedHashSet;
import java.util.Set;

/**
 * This class defines methods to perform operations on the Sets
 * 
 * @author mohit aggarwl
 * 
 */
public class SetUtils<T> {
	/**
	 * Performs s1 U s2 operations
	 * 
	 * @param s1
	 * @param s2
	 * @return
	 */
	public static <T> Set<T> union(Set<T> s1, Set<T> s2) {
		Set<T> union = new LinkedHashSet<T>(s1);
		union.addAll(s2);
		return union;
	}

	/**
	 * Performs s1
	 * 
	 * @param s1
	 * @param s2
	 * @return
	 */
	public static <T> Set<T> intersection(Set<T> s1, Set<T> s2) {
		Set<T> intersection = new LinkedHashSet<T>(s1);
		intersection.retainAll(s2);
		return intersection;
	}

	/**
	 * Performs s1- s2 operation on the sets
	 * 
	 * @param s1
	 * @param s2
	 * @return
	 */
	public static <T> Set<T> difference(Set<T> s1, Set<T> s2) {
		Set<T> difference = new LinkedHashSet<T>(s1);
		difference.removeAll(s2);
		return difference;
	}
}
