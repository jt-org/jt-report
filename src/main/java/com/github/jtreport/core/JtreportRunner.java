package com.github.jtreport.core;

/**
 * Abstract class to be extended to generate the report.
 * 
 * @author Fabio Rubino.
 * 
 */
public abstract class JtreportRunner {
	/**
	 * This method is call one way after the test is finished.
	 * 
	 * @return {@link ReportSummary} of this test class.
	 */
	public abstract ReportSummary initalizeReport();

}
