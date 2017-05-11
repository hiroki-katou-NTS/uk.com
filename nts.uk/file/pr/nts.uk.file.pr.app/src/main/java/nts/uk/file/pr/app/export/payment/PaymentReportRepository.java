/******************************************************************
 * Copyright (c) 2016 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.file.pr.app.export.payment;

import nts.uk.file.pr.app.export.payment.data.PaymentReportData;

public interface PaymentReportRepository {
	
	/**
	 * Find data.
	 *
	 * @param query the query
	 * @return the payment report data
	 */
	PaymentReportData findData(PaymentReportQuery query);

}
