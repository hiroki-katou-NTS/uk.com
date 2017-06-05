/******************************************************************
 * Copyright (c) 2016 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.pr.report.dom.payment.contact;

import nts.arc.time.YearMonth;
import nts.uk.shr.com.primitive.sample.ProcessingNo;

/**
 * The Interface ContactItemsCodeGetMemento.
 */
public interface ContactItemsCodeGetMemento {
	
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

}
