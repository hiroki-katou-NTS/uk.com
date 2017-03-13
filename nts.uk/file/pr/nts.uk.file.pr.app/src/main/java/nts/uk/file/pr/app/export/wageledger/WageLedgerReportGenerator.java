/******************************************************************
 * Copyright (c) 2015 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.file.pr.app.export.wageledger;

import nts.arc.layer.infra.file.export.FileGeneratorContext;
import nts.uk.file.pr.app.export.wageledger.data.WLNewLayoutReportData;
import nts.uk.file.pr.app.export.wageledger.data.WLOldLayoutReportData;

/**
 * The Interface WageLedgerReportGenerator.
 */
public interface WageLedgerReportGenerator {
	
	/**
	 * Generate with old layout.
	 *
	 * @param fileContext the file context
	 * @param reportData the report data
	 */
	void generateWithOldLayout(FileGeneratorContext fileContext, WLOldLayoutReportData reportData);
	
	/**
	 * Generate with new layout.
	 *
	 * @param fileContext the file context
	 * @param reportData the report data
	 */
	void generateWithNewLayout(FileGeneratorContext fileContext, WLNewLayoutReportData reportData);
}
