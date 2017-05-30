/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.pr.report.dom.payment.contact;

import nts.arc.time.YearMonth;
import nts.uk.shr.com.primitive.sample.ProcessingNo;

/**
 * The Interface ContactPersonalSettingSetMemento.
 */
public interface ContactPersonalSettingSetMemento {

	/**
	 * Sets the company code.
	 *
	 * @param companyCode the new company code
	 */
	void setCompanyCode(String companyCode);

	/**
	 * Sets the processing no.
	 *
	 * @param processingNo the new processing no
	 */ 
	void setProcessingNo(ProcessingNo processingNo);

	/**
	 * Sets the processing ym.
	 *
	 * @param processingYm the new processing ym
	 */
	void setProcessingYm(YearMonth processingYm);

	/**
	 * Sets the employee code.
	 *
	 * @param employeeCode the new employee code
	 */
	void setEmployeeCode(String employeeCode);

	/**
	 * Sets the spare pay atr.
	 */
	void setSparePayAtr(int sparePayAtr);

	/**
	 * Sets the pay bonus atr.
	 */
	void setPayBonusAtr(int payBonusAtr);

	/**
	 * Sets the comment.
	 *
	 * @param comment the new comment
	 */
	void setComment(ReportComment comment);

}
