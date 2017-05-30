/******************************************************************
 * Copyright (c) 2016 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.file.pr.app.export.payment;

import nts.uk.file.pr.app.export.payment.data.PaymentReportData;

/**
 * The Interface PaymentReportRepository.
 */
public interface PaymentReportRepository {

	/**
	 * Find data.
	 *
	 * @param query the query
	 * @return the payment report data
	 */
	PaymentReportData findData(PaymentReportQuery query);

	/**
	 * Check export data.
	 *
	 * @param query the query
	 */
	void checkExportData(PaymentReportQuery query);

}
