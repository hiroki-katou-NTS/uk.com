/******************************************************************
 * Copyright (c) 2015 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.file.pr.app.export.wageledger;

import nts.arc.layer.infra.file.export.FileGeneratorContext;
import nts.uk.file.pr.app.export.wageledger.data.WageLedgerReportData;

/**
 * The Interface WageLedgerReportGenerator.
 */
public interface WageLedgerReportGenerator {
	
	/**
	 * Generate.
	 *
	 * @param fileContext the file context
	 * @param reportData the report data
	 */
	void generate(FileGeneratorContext fileContext, WageLedgerReportData reportData);
}
