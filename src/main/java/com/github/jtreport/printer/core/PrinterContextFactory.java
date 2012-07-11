package com.github.jtreport.printer.core;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.github.jtreport.core.ReportTypePrinterEnum;
import com.github.jtreport.printer.csv.PrinterCsvStrategy;
import com.github.jtreport.printer.html.PrinterHtmlStrategy;
import com.github.jtreport.printer.jrxml.PrinterJrxmlStrategy;
import com.github.jtreport.printer.ods.PrinterOdsStrategy;
import com.github.jtreport.printer.odt.PrinterOdtStrategy;
import com.github.jtreport.printer.pdf.PrinterPdfStrategy;
import com.github.jtreport.printer.png.PrinterPngStrategy;
import com.github.jtreport.printer.xhtml.PrinterXHtmlStrategy;
import com.github.jtreport.printer.xml.PrinterXmlStrategy;

/**
 * Factory for printer context creation
 * 
 * @author Fabio Rubino
 * 
 */
public class PrinterContextFactory {

	private static final Logger L = LoggerFactory.getLogger(PrinterContextFactory.class);

	public PrinterContext createPrinter(final ReportTypePrinterEnum printerType, boolean margeReport) {
		final PrinterContext resultPrinterContext = new PrinterContext();
		switch (printerType) {
			case HTML :
				L.debug("HTML printer selected why reportType: [" + printerType.name() + "]");
				PrinterHtmlStrategy printerHtmlStrategy = new PrinterHtmlStrategy();
				printerHtmlStrategy.setMargeReport(margeReport);
				resultPrinterContext.setPrinterStrategy(printerHtmlStrategy);
				break;
			case PDF :
				L.debug("PDF printer selected why reportType: [" + printerType.name() + "]");
				PrinterPdfStrategy printerPdfStrategy = new PrinterPdfStrategy();
				printerPdfStrategy.setMargeReport(margeReport);
				resultPrinterContext.setPrinterStrategy(printerPdfStrategy);
				break;
			case XML :
				L.debug("XML printer selected why reportType: [" + printerType.name() + "]");
				PrinterXmlStrategy printerXmlStrategy = new PrinterXmlStrategy();
				printerXmlStrategy.setMargeReport(margeReport);
				resultPrinterContext.setPrinterStrategy(printerXmlStrategy);
				break;
			case CSV :
				L.debug("CSV printer selected why reportType: [" + printerType.name() + "]");
				PrinterCsvStrategy printerCsvStrategy = new PrinterCsvStrategy();
				printerCsvStrategy.setMargeReport(margeReport);
				resultPrinterContext.setPrinterStrategy(printerCsvStrategy);
				break;
			case XHTML :
				L.debug("XHTML printer selected why reportType: [" + printerType.name() + "]");
				PrinterXHtmlStrategy printerXHtmlStrategy = new PrinterXHtmlStrategy();
				printerXHtmlStrategy.setMargeReport(margeReport);
				resultPrinterContext.setPrinterStrategy(printerXHtmlStrategy);
				break;
			case ODS :
				L.debug("ODS printer selected why reportType: [" + printerType.name() + "]");
				PrinterOdsStrategy printerOdsStrategy = new PrinterOdsStrategy();
				printerOdsStrategy.setMargeReport(margeReport);
				resultPrinterContext.setPrinterStrategy(printerOdsStrategy);
				break;
			case ODT :
				L.debug("ODT printer selected why reportType: [" + printerType.name() + "]");
				PrinterOdtStrategy printerOdtStrategy = new PrinterOdtStrategy();
				printerOdtStrategy.setMargeReport(margeReport);
				resultPrinterContext.setPrinterStrategy(printerOdtStrategy);
				break;
			case PNG :
				L.debug("PNG printer selected why reportType: [" + printerType.name() + "]");
				PrinterPngStrategy printerPngStrategy = new PrinterPngStrategy();
				printerPngStrategy.setMargeReport(margeReport);
				resultPrinterContext.setPrinterStrategy(printerPngStrategy);
				break;
			case JRXML :
				L.debug("JRXML printer selected why reportType: [" + printerType.name() + "]");
				PrinterJrxmlStrategy printerJrxmlStrategy = new PrinterJrxmlStrategy();
				printerJrxmlStrategy.setMargeReport(margeReport);
				resultPrinterContext.setPrinterStrategy(printerJrxmlStrategy);
				break;
		}
		return resultPrinterContext;
	}
}
