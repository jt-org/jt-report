package com.github.jtreport.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Annotation for single test comment and descriptions.
 * 
 * @author Fabio Rubino.
 * 
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
public @interface TestSingleReport {

	/**
	 * Specify the general description for this test
	 * 
	 * @return the general description for this test
	 */
	public String description() default "";

	/**
	 * Specify the description for the test error
	 * 
	 * @return the description for the test error
	 */
	public String error() default "";

	/**
	 * Specify the expectations for the this test
	 * 
	 * @return the expectations for the this test
	 */
	public String expectations() default "";

	/**
	 * Specify the description for the failed test
	 * 
	 * @return the description for the failed test
	 */
	public String failed() default "";

	/**
	 * Specify the description for the ignored test
	 * 
	 * @return the description for the ignored test
	 */
	public String ignored() default "";

	/**
	 * Specify the key of the report input path for this test
	 * 
	 * @return the key of the report input path for this test
	 */
	public String keyCustomReport() default "";

	/**
	 * Specify the description for the passed test
	 * 
	 * @return the description for the passed test
	 */
	public String passed() default "";

}
