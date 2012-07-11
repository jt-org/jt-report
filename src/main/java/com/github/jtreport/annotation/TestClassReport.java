package com.github.jtreport.annotation;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 * Annotation for general test report comment and initialization.
 * 
 * @author Fabio Rubino.
 * 
 */
@Retention(RetentionPolicy.RUNTIME)
public @interface TestClassReport {
	/**
	 * Specify the general description for this test class
	 * 
	 * @return the general description for this test class
	 */
	public String description() default "";

}
