/******************************************************************
 * Copyright (c) 2016 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.pr.report.dom.payment.contact;

import lombok.Getter;
import nts.arc.layer.dom.AggregateRoot;
import nts.arc.time.YearMonth;
import nts.uk.shr.com.primitive.sample.ProcessingNo;

/**
 * The Class ContactItemsCode.
 */
@Getter
public class ContactItemsCode extends AggregateRoot {

	/** The company code. */
	private String companyCode;

	/** The processing no. */
	private ProcessingNo processingNo;

	/** The processing ym. */
	private YearMonth processingYm;

	// =================== Memento State Support Method ===================
	/**
	 * Instantiates a new contact items code.
	 *
	 * @param memento the memento
	 */
	public ContactItemsCode(ContactItemsCodeGetMemento memento) {
		this.companyCode = memento.getCompanyCode();
		this.processingNo = memento.getProcessingNo();
		this.processingYm = memento.getProcessingYm();
	}

	/**
	 * Save to memento.
	 *
	 * @param memento the memento
	 */
	public void saveToMemento(ContactItemsCodeSetMemento memento) {
		memento.setCompanyCode(this.companyCode);
		memento.setProcessingNo(this.processingNo);
		memento.setProcessingYm(this.processingYm);
	}
}
