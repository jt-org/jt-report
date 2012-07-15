package com.github.jtreport;

import org.apache.commons.lang.StringUtils;
import org.joda.time.DateTime;

import com.github.jtreport.core.StateTestEnum;
import com.github.jtreport.core.TestMethodResult;

public class Utility4Test {

	public static TestMethodResult createTestMethodResult() {
		final TestMethodResult tMr = new TestMethodResult(null,
				Utility4Test.class.getName(), "createDatasourceTest",
				"Data source cration utils test", "Expectations",
				new DateTime(), StateTestEnum.PASSED, null, "0.0", null);
		return tMr;
	}

	public static String getJtreportDir() {
		System.getProperty("jtreportDir");
		String jtreportDir = null;
		if (StringUtils.isBlank(jtreportDir)) {
			jtreportDir = System.getProperty("java.io.tmpdir");
		}
		return jtreportDir;
	}
}
