/******************************************************************
 * Copyright (c) 2015 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.pr.file.infra.accumulatedpayment;

import java.util.List;

import javax.ejb.Stateless;

import nts.uk.file.pr.app.export.accumulatedpayment.AccPaymentRepository;
import nts.uk.file.pr.app.export.accumulatedpayment.data.AccPaymentItemData;
import nts.uk.file.pr.app.export.accumulatedpayment.query.AccPaymentReportQuery;



@Stateless
public class JpaAccPaymentReportRepository implements AccPaymentRepository{

	/* (non-Javadoc)
	 * @see nts.uk.ctx.pr.screen.app.report.qet002.AccPaymentRepository
	 * #getItems(java.lang.String, nts.uk.ctx.pr.screen.app.report.qet002.query.AccPaymentReportQuery)
	 */
	@Override
	public List<AccPaymentItemData> getItems(String companyCode, AccPaymentReportQuery query) {
		// TODO Auto-generated method stub
		return null;
	}
}
