# Getting Started

1. Download.
2. Inside Maven surfire plugin.
3. Standalone in JUnit test class.
4. Configuration oprions.
5. Velocity variables.

## 1. Download.

### pom.xml
```xml     
<dependency>
  <groupId>com.github.jt-org</groupId>
  <artifactId>jt-report</artifactId>
  <version>0.0.1</version>
</dependency>
```


## 2. Inside Maven surfire plugin.
### pom.xml

```xml  
</plugin>  
	<groupId>org.apache.maven.plugins</groupId>
	<artifactId>maven-surefire-plugin</artifactId>
	<version>2.12</version>
	<configuration>
		<properties>
			<property>
				<name>listener</name>
				<value>com.github.jtreport.core.SurefireReportTestListner</value>
			</property>
		</properties>
		<systemProperties>
			<property>
				<name>jtreportConfig</name>
				<value>jtreport.properties</value>
			</property>
		</systemProperties>
	</configuration>
</plugin>
```

### SimpleExamplesTest.java
```java
@TestClassReport(description = "Put here the description for this test Class.")
public class SimpleExamplesTest {

	@Test
	@TestSingleReport(description = "Put here the description for this test...", expectations = "Put here the expectations test of the test...")
	public void simpleTest1() {
	}

	@Ignore("Put here why this test is skipped")
	@Test
	@TestSingleReport(description = "Put here the description for this test...", expectations = "Put here the expectations test of the test...")
	public void simpleTest2() {
	}
}
```

## 3. Standalone in JUnit test class.

### SimpleExamplesTest.java

```java
@RunWith(ReportTestRunner.class)
@TestClassReport(description = "Put here the description for this test Class.")
public class SimpleExamplesTest extends JtreportRunner {

	@Override
	public ReportSummary initalizeReport() {
		return new ReportSummary("/tmp/jtreportDir",
				new ArrayList&lt;ReportTypePrinterEnum&gt;() {

					private static final long serialVersionUID = 1L;

					{
						this.add(ReportTypePrinterEnum.PDF);
						this.add(ReportTypePrinterEnum.ODT);
						this.add(ReportTypePrinterEnum.ODS);
					}
				});
	}

	@Test
	@TestSingleReport(description = "Put here the description for this test...", expectations = "Put here the expectations test of the test...")
	public void simpleTest1() {
	}

	@Ignore("Put here why this test is skipped")
	@Test
	@TestSingleReport(description = "Put here the description for this test...", expectations = "Put here the expectations test of the test...")
	public void simpleTest2() {
	}

	@Ignore("This comment was override in the report")
	@Test
	@TestSingleReport(description = "Put here the description for this test...", ignored = "Put here why this test is skipped. This comment go in the test report", expectations = "Put here the expectations test of the test...")
	public void simpleTest3() {
	}

	@Test
	@TestSingleReport(description = "Put here the description for this test...", expectations = "Put here the expectations test of the test...")
	public void simpleTest4() {
		Assert.assertEquals("Are the same", 0, 1);
	}

	@Test
	@TestSingleReport(description = "Put here the description for this test...", expectations = "Put here the expectations test of the test...", failed = "This ovverride the failed massage.")
	public void simpleTest5() {
		Assert.assertEquals("Are the same", 0, 1);
	}

	@Test
	@TestSingleReport(description = "Put here the description for this test...", expectations = "Put here the expectations test of the test...")
	public void simpleTest6() {
		final Integer x = null;
		x.intValue();
	}

	@Test
	@TestSingleReport(description = "Put here the description for this test...", expectations = "Put here the expectations test of the test...", error = "This ovverride the error massage.")
	public void simpleTest7() {
		final Integer x = null;
		x.intValue();
	}
}

```


