/******************************************************************
 * Copyright (c) 2016 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.pr.report.dom.payment.contact;

import nts.arc.time.YearMonth;
import nts.uk.shr.com.primitive.sample.ProcessingNo;

/**
 * The Interface ContactItemsCodeSetMemento.
 */
public interface ContactItemsCodeSetMemento {

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
}
