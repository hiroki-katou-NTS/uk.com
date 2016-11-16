package nts.uk.ctx.pr.proto.dom.paymentdata.service;

import nts.uk.ctx.core.dom.company.CompanyCode;
import nts.uk.shr.com.primitive.PersonId;

public interface PaymentDetailService {
	/**
	 * Calculate value in payment detail for personal.
	 * @param companyCode company code
	 * @param personId person id
	 * @param baseYearMonth base year month
	 * @return payment value
	 */
	Double calculatePayValue(CompanyCode companyCode, PersonId personId, int baseYearMonth);
}
