/******************************************************************
 * Copyright (c) 2016 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.file.pr.app.export.detailpaymentsalary;

import nts.uk.file.pr.app.export.detailpaymentsalary.data.PaymentSalaryReportData;
import nts.uk.file.pr.app.export.detailpaymentsalary.query.PaymentSalaryQuery;

/**
 * The Interface PaymentSalaryReportRepository.
 */
public interface PaymentSalaryReportRepository {

	/**
	 * Export PDF payment salary.
	 *
	 * @param query the query
	 * @return the payment salary report data
	 */
	public PaymentSalaryReportData exportPDFPaymentSalary(String companyCode,PaymentSalaryQuery query);

	/**
	 * Check export.
	 *
	 * @param companyCode the company code
	 * @param query the query
	 * @return true, if successful
	 */
	public boolean checkExport(String companyCode, PaymentSalaryQuery query);
	
	
}
