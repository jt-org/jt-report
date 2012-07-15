package com.github.jtreport.core;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.joda.time.DateTime;
import org.junit.Ignore;
import org.junit.runner.Description;
import org.junit.runner.Result;
import org.junit.runner.notification.Failure;
import org.junit.runner.notification.RunListener;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.github.jtreport.annotation.TestClassReport;
import com.github.jtreport.annotation.TestSingleReport;
import com.github.jtreport.printer.core.IPrinterStrategy;
import com.github.jtreport.printer.core.PrinterContext;
import com.github.jtreport.printer.core.PrinterContextFactory;
import com.github.jtreport.printer.velocity.PrintVelocityResult;
import com.github.jtreport.utils.JTreportUtils;

/**
 * Abstract listener template class to be extended.
 * 
 * @author Fabio Rubino.
 * 
 */
public abstract class AbstractReportTestListner extends RunListener {

	private static final Logger L = LoggerFactory
			.getLogger(AbstractReportTestListner.class);

	private final ArrayList<TestMethodResult> testMethodResultList = new ArrayList<TestMethodResult>();

	private final Map<String, BigDecimal> testRunningTime = new HashMap<String, BigDecimal>();

	private Map<Class<?>, TestClassResult> createTestResult(final Result result) {

		final List<Failure> failures = result.getFailures();

		for (final Failure failure : failures) {
			final Description description = failure.getDescription();
			final String methodName = description.getMethodName();
			for (final TestMethodResult testMethodResult : this.testMethodResultList) {

				final String methodNameFailure = testMethodResult
						.getTestMethodName();
				if (StringUtils.equals(methodNameFailure, methodName)) {
					final TestSingleReport testResult = testMethodResult
							.getDescriptionResult().getAnnotation(
									TestSingleReport.class);
					final Throwable exception = failure.getException();
					final String failureClass = exception.getClass().getName();
					final String failureMessage = exception.getMessage();

					final String failureDescription = failureClass + ": "
							+ failureMessage;
					testMethodResult.setResultDescription(failureDescription);
					if (exception instanceof AssertionError) {
						// totalFail++;
						testMethodResult.setTestState(StateTestEnum.FAILED);
						if (testResult != null) {
							if (StringUtils.isNotBlank(testResult.failed())) {
								testMethodResult
										.setResultDescription(testResult
												.failed());
							}
						}
					} else {
						// totalError++;
						testMethodResult.setTestState(StateTestEnum.ERROR);
						if (testResult != null) {
							if (StringUtils.isNotBlank(testResult.error())) {
								testMethodResult
										.setResultDescription(testResult
												.error());
							}
						}
					}
				}
			}
		}

		final Map<Class<?>, TestClassResult> testClassResultMap = new HashMap<Class<?>, TestClassResult>();

		for (final TestMethodResult testMethodResult : this.testMethodResultList) {
			final Class<?> testClass = testMethodResult.getDescriptionResult()
					.getTestClass();

			if (!testClassResultMap.containsKey(testClass)) {
				String descriptionSummary = "";
				final TestClassReport testClassReportAnnotation = testClass
						.getAnnotation(TestClassReport.class);

				if (testClassReportAnnotation != null) {
					descriptionSummary = testClassReportAnnotation
							.description();
				} else {
					L.debug("The test Class [" + testClass.getName()
							+ "] not have annotation for report");
				}
				final TestClassResult testClassResult = new TestClassResult(
						this.setReportName(testClass.getName()),
						descriptionSummary);
				testClassResultMap.put(testClass, testClassResult);
			}

			final TestClassResult testClassResult = testClassResultMap
					.get(testClass);
			final StateTestEnum testState = testMethodResult.getTestState();
			testClassResult.addTestRun();
			testClassResult.addTestMethodResult(testMethodResult);
			switch (testState) {
			case PASSED:
				testClassResult.addTestPassed();
				break;
			case ERROR:
				testClassResult.addTestError();
				break;
			case FAILED:
				testClassResult.addTestFailed();
				break;
			case IGNORED:
				testClassResult.addTestIgnored();
				break;
			}
			testClassResultMap.put(testClass, testClassResult);
		}
		return testClassResultMap;
	}

	/**
	 * 
	 * @param testClass
	 *            test class executed
	 * @return {@link ReportSummary} of the tests.
	 */
	abstract ReportSummary getReportSummary(Class<?> testClass);

	/**
	 * 
	 * @param currentName
	 *            name of the report based on method name.
	 * @return report name.
	 */
	abstract String setReportName(String currentName);

	@Override
	public void testAssumptionFailure(final Failure failure) {
		super.testAssumptionFailure(failure);
	}

	@Override
	public void testFailure(final Failure failure) throws Exception {
		super.testFailure(failure);
	}

	@Override
	public void testFinished(final Description description) throws Exception {
		final TestSingleReport testReport = description
				.getAnnotation(TestSingleReport.class);
		final String methodName = description.getMethodName();
		if (testReport != null) {
			final String passingComment = testReport.passed();
			final String testDescription = testReport.description();
			final String testExpectations = testReport.expectations();
			final String testKey = testReport.keyCustomReport();
			final BigDecimal timeStart = this.testRunningTime.get(methodName);
			final BigDecimal timeEnd = new BigDecimal(System.nanoTime());

			final long testDuration = timeEnd.subtract(timeStart).longValue();
			final String runningTimeString = JTreportUtils
					.convertTime(testDuration);
			String resultDesctipion = "Passed";
			if (StringUtils.isNotBlank(passingComment)) {
				resultDesctipion = passingComment;
			}
			final TestMethodResult testMethodResult = new TestMethodResult(
					description, description.getTestClass().getSimpleName(),
					methodName, testDescription, testExpectations,
					new DateTime(), StateTestEnum.PASSED, resultDesctipion,
					runningTimeString, testKey);
			this.testMethodResultList.add(testMethodResult);
			L.debug("Test [" + methodName + "] duration[" + runningTimeString
					+ "]");
		} else {
			L.debug("The test [" + methodName
					+ "] not have annotation for report");
		}

		super.testFinished(description);
	}

	@Override
	public void testIgnored(final Description description) throws Exception {
		final TestSingleReport testReport = description
				.getAnnotation(TestSingleReport.class);
		final String methodName = description.getMethodName();
		if (testReport != null) {
			final String ingoredComment = testReport.ignored();
			final String descriptionComment = testReport.description();
			final String testExpectations = testReport.expectations();
			final String testKey = testReport.keyCustomReport();
			String resultDesctipion = "Ignored";
			final Ignore ignoredAnnotation = description
					.getAnnotation(Ignore.class);

			if (ignoredAnnotation != null) {
				final String value = ignoredAnnotation.value();
				resultDesctipion = StringUtils.isNotBlank(value) ? value
						: resultDesctipion;
			}

			if (StringUtils.isNotBlank(ingoredComment)) {
				resultDesctipion = ingoredComment;
			}

			final TestMethodResult testMethodResult = new TestMethodResult(
					description, description.getTestClass().getSimpleName(),
					methodName, descriptionComment, testExpectations,
					new DateTime(), StateTestEnum.IGNORED, resultDesctipion,
					"0.0 s", testKey);
			this.testMethodResultList.add(testMethodResult);
			L.debug("Test [" + methodName + "] duration[" + 0.0 + "]");

		} else {
			L.debug("The test [" + methodName
					+ "] not have annotation for report");
		}
		super.testIgnored(description);
	}

	@Override
	public void testRunFinished(final Result result) throws Exception {

		if (this.testMethodResultList.size() > 0) {
			String velocityTemplate = "";
			String outFilePath = "";
			final Class<?> testClass = this.testMethodResultList.get(0)
					.getDescriptionResult().getTestClass();

			final ReportSummary reportSummary = this
					.getReportSummary(testClass);
			if (reportSummary != null) {
				IPrinterStrategy customJtPrinter = null;
				final boolean margeSurefireReport = reportSummary
						.isMargeSurefireReport();
				velocityTemplate = reportSummary.getVelocityTemplatePath();
				outFilePath = reportSummary.getOutPathDir();
				customJtPrinter = reportSummary.getCustomJtPrinter();
				final Collection<ReportTypePrinterEnum> reportType = reportSummary
						.getReportPrinterType();

				final Map<Class<?>, TestClassResult> testClassResultMap = this
						.createTestResult(result);

				final Collection<TestClassResult> testClassResultList = new ArrayList<TestClassResult>();
				for (final Class<?> testClassEntry : testClassResultMap
						.keySet()) {
					testClassResultList.add(testClassResultMap
							.get(testClassEntry));
				}

				final PrinterGlobalTypeEnum reportGlobalPrinterType = reportSummary
						.getPrinterType();
				switch (reportGlobalPrinterType) {
				case CUSTOM:
					customJtPrinter.print(testClassResultList, outFilePath);
					break;

				case DEFAULT:
					for (final ReportTypePrinterEnum report : reportType) {
						final PrinterContextFactory printerContextFactory = new PrinterContextFactory();
						final PrinterContext printerContext = printerContextFactory
								.createPrinter(report, margeSurefireReport);
						printerContext.printReport(testClassResultList,
								outFilePath);
					}
					break;

				case VELOCITY:
					L.debug("Velocity printer selected why velocityTemplate: ["
							+ velocityTemplate + "]");
					final PrintVelocityResult printVelocityResult = new PrintVelocityResult();
					printVelocityResult.print(testClassResultList,
							velocityTemplate, outFilePath,
							reportSummary.isTemplateWithKey());
					break;
				}
			} else {
				L.debug("ReportSummary is null");
			}
		} else {
			L.debug("Not have tests to report");
		}
		super.testRunFinished(result);
	}

	@Override
	public void testStarted(final org.junit.runner.Description description)
			throws java.lang.Exception {
		final TestSingleReport testReport = description
				.getAnnotation(TestSingleReport.class);
		final String methodName = description.getMethodName();
		if (testReport != null) {
			this.testRunningTime.put(methodName,
					new BigDecimal(System.nanoTime()));
		} else {
			L.debug("The test [" + methodName
					+ "] not have annotation for report");
		}
		super.testStarted(description);
	}
}
