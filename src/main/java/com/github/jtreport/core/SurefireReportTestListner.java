package com.github.jtreport.core;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Surefire listener strategy for multy test execution.
 * 
 * @author Fabio Rubino.
 * 
 */
public class SurefireReportTestListner extends AbstractReportTestListner {

	private static final Logger L = LoggerFactory
			.getLogger(SurefireReportTestListner.class);

	@Override
	ReportSummary getReportSummary(final Class<?> testClass) {
		final String jtreportConfig = System.getProperty("jtreportConfig");
		L.debug("jtreportConfig [" + jtreportConfig + "]");
		return new ReportSummary(jtreportConfig);
	}

	@Override
	String setReportName(final String currentName) {
		return currentName;
	}

}
