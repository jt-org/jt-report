package com.github.jtreport.printer.jrxml;

import java.io.OutputStream;

import net.sf.dynamicreports.jasper.builder.JasperConcatenatedReportBuilder;
import net.sf.dynamicreports.jasper.builder.JasperReportBuilder;
import net.sf.dynamicreports.report.builder.ReportTemplateBuilder;
import net.sf.dynamicreports.report.exception.DRException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.github.jtreport.printer.core.AbstractPrinterStrategyTemaplate;
import com.github.jtreport.printer.core.Templates;

/**
 * 
 * @author Fabio Rubino.
 * 
 */
public class PrinterJrxmlStrategy extends AbstractPrinterStrategyTemaplate {

	private static final Logger L = LoggerFactory.getLogger(PrinterJrxmlStrategy.class);

	// private class ImageExpression extends AbstractSimpleExpression<InputStream> {
	//
	// private static final long serialVersionUID = 1L;
	//
	// @Override
	// public InputStream evaluate(final ReportParameters reportParameters) {
	// final String value = reportParameters.getValue("state");
	// final InputStream resourceAsStream = this.getClass().getClassLoader()
	// .getResourceAsStream("img/" + value + ".png");
	// return resourceAsStream;
	// }
	// }

	// private class StateExpression extends AbstractSimpleExpression<String> {
	//
	// private static final long serialVersionUID = 1L;
	//
	// @Override
	// public String evaluate(final ReportParameters reportParameters) {
	// return " " + reportParameters.getValue("state");
	// }
	// }

	public static void main(final String[] args) {
		new PrinterJrxmlStrategy();
	}

	@Override
	protected void addColumn(final JasperReportBuilder report) {
	}

	@Override
	protected void addTitle(final JasperReportBuilder report, final String title) {
		report.addTitle(Templates.createTitleComponent(title, true));
	}

	@Override
	public void printReport(final JasperReportBuilder report, final OutputStream os) throws DRException {
		report.toJrXml(os);
	}

	@Override
	protected void setBackground(final JasperReportBuilder report) {
	}

	@Override
	public String setExtension() {
		return ".jrxml";
	}

	@Override
	protected ReportTemplateBuilder setReportTemplate() {
		return Templates.reportTemplate;
	}

	@Override
	protected void printConcatenateReport(JasperConcatenatedReportBuilder report, OutputStream os) throws DRException {
		String msgError = "Not implemented yet!";
		L.error(msgError);
		throw new UnsupportedOperationException(msgError);
	}
}
