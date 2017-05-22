/******************************************************************
 * Copyright (c) 2016 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.pr.file.infra.payment;

import nts.uk.file.pr.app.export.payment.data.PaymentReportData;
import nts.uk.shr.infra.file.report.aspose.cells.AsposeCellsReportContext;

/**
 * The Interface Generator.
 */
public interface PaymentGenerator {
	
	/**
	 * Generate.
	 *
	 * @param context the context
	 * @param query the query
	 */
	void generate(AsposeCellsReportContext context, PaymentReportData data);
}
