package com.github.jtreport.printer.core;

import java.util.Collection;

import com.github.jtreport.core.TestClassResult;

/**
 * Printer strategy interface.
 * 
 * Interface to implements for custom printer stratgy
 * 
 * @author Fabio Rubino.
 * 
 */
public interface IPrinterStrategy {

	/**
	 * Print the report.
	 * 
	 * @param globalTestResult
	 *            Test result information.
	 * @param dirOutPath
	 *            Directory path to save the report.
	 */
	public void print(Collection<TestClassResult> globalTestResult, String dirOutPath);

}
