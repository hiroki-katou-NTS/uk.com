/******************************************************************
 * Copyright (c) 2015 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.pr.screen.app.report.accumulatedpayment;

import nts.arc.layer.infra.file.export.FileGeneratorContext;
import nts.uk.ctx.pr.screen.app.report.accumulatedpayment.data.AccPaymentDataSource;


/**
 * The Interface AccPaymentReportGenerator.
 */
public interface AccPaymentReportGenerator {
	
	/**
	 * Generate.
	 *
	 * @param generatorContext the generator context
	 * @param dataSource the data source
	 */
	void generate(FileGeneratorContext generatorContext, AccPaymentDataSource dataSource);	
}
