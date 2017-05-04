/******************************************************************
 * Copyright (c) 2016 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.pr.report.dom.payment.contact;

import java.util.Set;

/**
 * The Interface ContactItemsSettingSetMemento.
 */
public interface ContactItemsSettingSetMemento {

	/**
	 * Sets the contact items code.
	 *
	 * @param contactItemsCode the new contact items code
	 */
	void setContactItemsCode(ContactItemsCode contactItemsCode);

	/**
	 * Sets the initial cp comment.
	 *
	 * @param reportComment the new initial cp comment
	 */
	 void setInitialCpComment(ReportComment reportComment);

	/**
	 * Sets the month cp comment.
	 *
	 * @param reportComment the new month cp comment
	 */
	 void  setMonthCpComment(ReportComment reportComment);

	/**
	 * Sets the month em comments.
	 *
	 * @param monthEmComments the new month em comments
	 */
	void setMonthEmComments(Set<EmpComment> monthEmComments);

}
