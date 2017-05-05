/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.pr.report.dom.payment.contact;

import nts.arc.time.YearMonth;
import nts.uk.shr.com.primitive.sample.ProcessingNo;

/**
 * The Interface ContactPersonalSettingGetMemento.
 */
public interface ContactPersonalSettingGetMemento {

	/**
	 * Gets the company code.
	 *
	 * @return the company code
	 */
	String getCompanyCode();

	/**
	 * Gets the processing no.
	 *
	 * @return the processing no
	 */
	 ProcessingNo getProcessingNo();

	/**
	 * Gets the processing ym.
	 *
	 * @return the processing ym
	 */
	 YearMonth getProcessingYm();

	/**
	 * Gets the employee code.
	 *
	 * @return the employee code
	 */
	 String getEmployeeCode();

	/**
	 * Gets the comment.
	 *
	 * @return the comment
	 */
	 ReportComment getComment();

}
