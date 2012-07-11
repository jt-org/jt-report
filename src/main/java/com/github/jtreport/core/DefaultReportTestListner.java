package com.github.jtreport.core;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Default listener strategy for single class test execution.
 * 
 * @author Fabio Rubino.
 * 
 */
public class DefaultReportTestListner extends AbstractReportTestListner {

	private static final Logger L = LoggerFactory
			.getLogger(DefaultReportTestListner.class);

	@Override
	ReportSummary getReportSummary(final Class<?> testClass) {
		Method method;
		ReportSummary reportSummary = null;
		try {
			method = testClass.getMethod("initalizeReport");
			reportSummary = (ReportSummary) method.invoke(testClass
					.newInstance());
		} catch (final NoSuchMethodException e) {
			L.error("No Such method [initalizeReport] in class ["
					+ testClass.getName() + "]");
			L.debug("No Such method [initalizeReport] in class ["
					+ testClass.getName() + "]", e);
		} catch (final SecurityException e) {
			L.error("Security Exception method [initalizeReport] in class ["
					+ testClass.getName() + "]");
			L.error("Security Exception method [initalizeReport] in class ["
					+ testClass.getName() + "]", e);
		} catch (final IllegalAccessException e) {
			L.error("Illegal Access method [initalizeReport] in class ["
					+ testClass.getName() + "]");
			L.debug("Illegal Access method [initalizeReport] in class ["
					+ testClass.getName() + "]", e);
		} catch (final IllegalArgumentException e) {
			L.error("Illegal Argument method [initalizeReport] in class ["
					+ testClass.getName() + "]");
			L.debug("Illegal Argument method [initalizeReport] in class ["
					+ testClass.getName() + "]", e);
		} catch (final InvocationTargetException e) {
			L.error("Error Invocation Target method [initalizeReport] in class ["
					+ testClass.getName() + "]");
			L.debug("Error Invocation Target method [initalizeReport] in class ["
					+ testClass.getName() + "]", e);
		} catch (final InstantiationException e) {
			L.error("Error Instantiationclass [" + testClass.getName() + "]");
			L.debug("Error Instantiation class [" + testClass.getName() + "]",
					e);
		}
		return reportSummary;
	}

	@Override
	String setReportName(final String currentName) {
		return currentName;
	}
}
