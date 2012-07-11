package com.github.jtreport.printer.core;

import java.util.Collection;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.github.jtreport.core.TestClassResult;
import com.github.jtreport.printer.pdf.PrinterPdfStrategy;

/**
 * Context for printer strategy {@link IPrinterStrategy}
 * 
 * @author Fabio Rubino
 * 
 */
public class PrinterContext {

	// defaultPrinter
	IPrinterStrategy printerStrategy = new PrinterPdfStrategy();

	boolean margeTemplate = false;

	private static final Logger L = LoggerFactory
			.getLogger(PrinterContext.class);

	protected IPrinterStrategy getPrinterStrategy() {
		return this.printerStrategy;
	}

	public void printReport(
			final Collection<TestClassResult> globalTestResultList,
			final String dirOutPath) {
		L.debug("dirOutPath [" + dirOutPath + "]");
		this.printerStrategy.print(globalTestResultList, dirOutPath);
	}

	void setPrinterStrategy(final IPrinterStrategy printerStrategy) {
		this.printerStrategy = printerStrategy;
	}

}
