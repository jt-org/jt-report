package com.github.jtreport.datasource;

import java.util.Collection;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.github.jtreport.core.TestMethodResult;

/**
 * Utils to create a datasource,
 * 
 * @author Fabio Rubino.
 * 
 */
public class DataSourceUtils {

	private static final Logger L = LoggerFactory
			.getLogger(DataSourceUtils.class);

	public static DataSource createDataSource(
			final Collection<TestMethodResult> singleTestResultList) {
		final DataSource dataSource = new DataSource("state", "testName",
				"testDescription", "resultDescription", "expectations",
				"formattedDate", "runningTime");
		L.debug("Number of test adding in the datasource ["
				+ singleTestResultList.size() + "]");
		for (final TestMethodResult testMethodResult : singleTestResultList) {
			final String testState = testMethodResult.getTestState().name();
			final String testMethodName = testMethodResult.getTestMethodName();
			// String testDisplayName = testMethodResult.getTestDisplayName();
			final String testDescription = testMethodResult
					.getTestDescription();
			final String resultDescription = testMethodResult
					.getResultDescription();
			final String formattdDate = testMethodResult.getFormattdDate();
			final String expectations = testMethodResult.getTestExpectations();
			final String runningTime = testMethodResult.getRunningTime();
			// Description descriptionResult =
			// testMethodResult.getDescriptionResult();
			dataSource.add(testState, testMethodName, testDescription,
					resultDescription, expectations, formattdDate, runningTime);
		}
		return dataSource;
	}
}
