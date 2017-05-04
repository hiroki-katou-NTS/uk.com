/******************************************************************
 * Copyright (c) 2016 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.pr.report.dom.payment.contact;

import java.util.Set;

import nts.arc.time.YearMonth;
import nts.uk.shr.com.primitive.sample.ProcessingNo;

/**
 * The Interface ContactItemsSettingGetMemento.
 */
public interface ContactItemsSettingGetMemento {

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
	 * Gets the initial cp comment.
	 *
	 * @return the initial cp comment
	 */
	 ReportComment getInitialCpComment();

	/**
	 * Gets the month cp comment.
	 *
	 * @return the month cp comment
	 */
	 ReportComment getMonthCpComment();

	/**
	 * Gets the month em comments.
	 *
	 * @return the month em comments
	 */
	Set<EmpComment> getMonthEmComments();

}
