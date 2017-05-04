/******************************************************************
 * Copyright (c) 2016 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.pr.report.dom.payment.contact;

import java.util.Set;

/**
 * The Interface ContactItemsSettingGetMemento.
 */
public interface ContactItemsSettingGetMemento {

	/**
	 * Gets the contact items code.
	 *
	 * @return the contact items code
	 */
	ContactItemsCode getContactItemsCode();

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
