package com.github.jtreport.datasource;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;

import org.junit.Assert;
import org.junit.Test;

import com.github.jtreport.Utility4Test;
import com.github.jtreport.annotation.TestClassReport;
import com.github.jtreport.annotation.TestSingleReport;
import com.github.jtreport.core.JTFieldConstants;
import com.github.jtreport.core.StateTestEnum;
import com.github.jtreport.core.TestMethodResult;

@TestClassReport(description = "Datasource Test.")
public class DataSourceTest {

	@Test
	@TestSingleReport(description = "Test the cration of datasource.")
	public void createDatasourceTest() {
		final Collection<TestMethodResult> singleTestResultList = new ArrayList<TestMethodResult>();
		final TestMethodResult tMr = Utility4Test.createTestMethodResult();
		singleTestResultList.add(tMr);
		final DataSource ds = DataSourceUtils
				.createDataSource(singleTestResultList);

		final List<Map<String, Object>> dsValues = ds.getValues();
		for (final Map<String, Object> dsValue : dsValues) {
			Assert.assertEquals("createDatasourceTest",
					dsValue.get(JTFieldConstants.FIELD_TEST_NAME));
			Assert.assertEquals("Expectations",
					dsValue.get(JTFieldConstants.FIELD_TEST_EXPECTATIONS));
			Assert.assertEquals(StateTestEnum.PASSED.name(),
					dsValue.get(JTFieldConstants.FIELD_TEST_STATE));
			Assert.assertEquals("0.0",
					dsValue.get(JTFieldConstants.FIELD_TEST_RUNNING_TIME));
			Assert.assertEquals("Data source cration utils test",
					dsValue.get(JTFieldConstants.FIELD_TEST_DESCRIPTION));
		}
	}
}
