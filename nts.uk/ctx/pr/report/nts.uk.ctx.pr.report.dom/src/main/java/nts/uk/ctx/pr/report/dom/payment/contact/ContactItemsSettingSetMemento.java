/******************************************************************
 * Copyright (c) 2016 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.pr.report.dom.payment.contact;

import java.util.Set;

import nts.arc.time.YearMonth;
import nts.uk.shr.com.primitive.sample.ProcessingNo;

/**
 * The Interface ContactItemsSettingSetMemento.
 */
public interface ContactItemsSettingSetMemento {

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
	 * @param yearMonth the new processing ym
	 */
	void setProcessingYm(YearMonth yearMonth);

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
