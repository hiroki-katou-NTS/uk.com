/******************************************************************
 * Copyright (c) 2015 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.pr.screen.app.report.accumulatedpayment;

import java.util.List;

import nts.uk.ctx.pr.screen.app.report.accumulatedpayment.data.AccPaymentItemData;
import nts.uk.ctx.pr.screen.app.report.accumulatedpayment.query.AccPaymentReportQuery;

/**
 * The Interface AccPaymentRepository.
 */
public interface AccPaymentRepository {

	/**
	 * Gets the items.
	 *
	 * @return the items
	 */
	List<AccPaymentItemData> getItems(String companyCode, AccPaymentReportQuery query);
	
}
