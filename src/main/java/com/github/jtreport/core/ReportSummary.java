package com.github.jtreport.core;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Properties;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.github.jtreport.printer.core.IPrinterStrategy;

/**
 * This class contains global information for the report
 * 
 * @author Fabio Rubino.
 * 
 * 
 */
public class ReportSummary {

	private static final Logger L = LoggerFactory.getLogger(ReportSummary.class);

	private Collection<ReportTypePrinterEnum> reportPrinterType = new ArrayList<ReportTypePrinterEnum>() {

		private static final long serialVersionUID = 1L;

		{
			add(ReportTypePrinterEnum.PDF);
		}
	};

	private String outPathDir = null;

	private String velocityTemplatePath = null;

	private boolean templateWithKey = false;

	private IPrinterStrategy customJtPrinter;

	private PrinterGlobalTypeEnum printerType = PrinterGlobalTypeEnum.DEFAULT;

	private boolean margeSurefireReport = false;

	/**
	 * Constructor for custom printer type report.
	 * 
	 * @param customJtPrinter
	 *            Custom implementation of {@link IPrinterStrategy} class.
	 * @param templateWithKey
	 *            <b>true</b> to use the key used in the test, as a variable in
	 *            the template
	 */
	public ReportSummary(final IPrinterStrategy customJtPrinter, final boolean templateWithKey) {
		printerType = PrinterGlobalTypeEnum.CUSTOM;
		this.templateWithKey = templateWithKey;
		this.customJtPrinter = customJtPrinter;
	}

	/**
	 * Constructor for the default report.
	 * 
	 * @param reportPrinterType
	 *            Type of printer {@link ReportTypePrinterEnum}
	 * 
	 * @param outPathDir
	 *            Directory path to save the report.
	 */
	public ReportSummary(final String outPathDir, final Collection<ReportTypePrinterEnum> reportPrinterType) {
		printerType = PrinterGlobalTypeEnum.DEFAULT;
		this.reportPrinterType = reportPrinterType;
		this.outPathDir = outPathDir;

	}

	/**
	 * Constructor for the custom report, using Velocity template.
	 * 
	 * @param velocityTemplatePath
	 *            to custom velocity template file
	 * @param outPathDir
	 *            Directory path to save the report.
	 * @param templateWithKey
	 *            <b>true</b> to use the key used in the test, as a variable in
	 *            the template
	 */
	public ReportSummary(final String velocityTemplatePath, final String outPathDir, final boolean templateWithKey) {
		printerType = PrinterGlobalTypeEnum.VELOCITY;
		this.outPathDir = outPathDir;
		this.velocityTemplatePath = velocityTemplatePath;
		this.templateWithKey = templateWithKey;
	}

	/**
	 * Constructor for all type of printer using Properties file.
	 * 
	 * @param configFilePath
	 *            the path of the proprties file.
	 */
	public ReportSummary(final String configFilePath) {
		InputStream is = getClass().getClassLoader().getResourceAsStream(configFilePath);
		Properties configProp = new Properties();
		try {
			configProp.load(is);
			outPathDir = configProp.getProperty("jtreportDir");
			velocityTemplatePath = configProp.getProperty("veolocityTemplatePath");
			String istemplateWithKey = configProp.getProperty("templateWithKey");
			templateWithKey = StringUtils.equalsIgnoreCase(istemplateWithKey, "true") ? true : false;
			String margeReport = configProp.getProperty("margeReport");
			margeSurefireReport = StringUtils.equalsIgnoreCase(margeReport, "true") ? true : false;
			String printerTypeString = configProp.getProperty("printerType");
			String reportPrinterFormat = configProp.getProperty("reportPrinterFormat");

			String[] splitPrinterFormat = StringUtils.split(reportPrinterFormat, ",");
			if ((splitPrinterFormat != null) && (splitPrinterFormat.length > 0)) {
				reportPrinterType = new ArrayList<ReportTypePrinterEnum>();
				for (String printerFormat : splitPrinterFormat) {
					try {
						ReportTypePrinterEnum printerTypeEnum = ReportTypePrinterEnum.valueOf(printerFormat);
						reportPrinterType.add(printerTypeEnum);
					} catch (IllegalArgumentException e) {
						L.warn("ReportTypePrinterEnum value [" + printerFormat + "] not found.");
						L.debug("ReportTypePrinterEnum value [" + printerFormat + "] not found.", e);
					}
				}
			}

			if (reportPrinterType.size() == 0) {
				reportPrinterType.add(ReportTypePrinterEnum.PDF);
			}
			if (StringUtils.equalsIgnoreCase(printerTypeString, PrinterGlobalTypeEnum.CUSTOM.name())) {
				printerType = PrinterGlobalTypeEnum.CUSTOM;
			}
			if (StringUtils.equalsIgnoreCase(printerTypeString, PrinterGlobalTypeEnum.VELOCITY.name())) {
				printerType = PrinterGlobalTypeEnum.VELOCITY;
			}
			String customPrinterClass = configProp.getProperty("customPrinterClass");
			if (StringUtils.isNotBlank(customPrinterClass)) {
				try {
					customJtPrinter = (IPrinterStrategy) Class.forName(customPrinterClass).newInstance();
				} catch (ClassNotFoundException e) {
					L.error("Error Class [" + customPrinterClass + "] not found!", e);
				} catch (InstantiationException e) {
					L.error("Instantiation Exception for class  [" + customPrinterClass + "]", e);
				} catch (IllegalAccessException e) {
					L.error("Illegal acces for class  [" + customPrinterClass + "]", e);
				}
			}
		} catch (IOException e) {
			L.error("Load jterport properties file error.", e);
		}

	}

	IPrinterStrategy getCustomJtPrinter() {
		return customJtPrinter;
	}

	String getOutPathDir() {
		return outPathDir;
	}

	PrinterGlobalTypeEnum getPrinterType() {
		return printerType;
	}

	Collection<ReportTypePrinterEnum> getReportPrinterType() {
		return reportPrinterType;
	}

	String getVelocityTemplatePath() {
		return velocityTemplatePath;
	}

	boolean isTemplateWithKey() {
		return templateWithKey;
	}

	public boolean isMargeSurefireReport() {
		return margeSurefireReport;
	}

}
