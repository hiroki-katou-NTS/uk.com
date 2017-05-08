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
public interface PaySalaryReportRepository {

	
	/**
	 * Find report data.
	 *
	 * @param companyCode the company code
	 * @param query the query
	 * @return the payment salary report data
	 */
	PaymentSalaryReportData findReportData(String companyCode,PaymentSalaryQuery query);

	/**
	 * Checks if is available data.
	 *
	 * @param companyCode the company code
	 * @param query the query
	 * @return true, if is available data
	 */
	boolean isAvailableData(String companyCode, PaymentSalaryQuery query);
}
