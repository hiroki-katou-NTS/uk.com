/******************************************************************
 * Copyright (c) 2016 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.pr.file.infra.paymentdata;

import nts.arc.time.YearMonth;
import nts.uk.ctx.pr.core.infra.entity.paymentdata.QstdtPaymentHeader;
import nts.uk.ctx.pr.report.dom.payment.contact.ContactItemsCodeGetMemento;
import nts.uk.shr.com.primitive.sample.ProcessingNo;

/**
 * The Class JpaPaymentContactCodeSetMemento.
 */
public class JpaPaymentContactCodeGetMemento implements ContactItemsCodeGetMemento {

	/** The header. */
	private QstdtPaymentHeader header;

	public JpaPaymentContactCodeGetMemento(QstdtPaymentHeader header) {
		this.header = header;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see nts.uk.ctx.pr.report.dom.payment.contact.ContactItemsCodeGetMemento#
	 * getCompanyCode()
	 */
	@Override
	public String getCompanyCode() {
		return header.qstdtPaymentHeaderPK.companyCode;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see nts.uk.ctx.pr.report.dom.payment.contact.ContactItemsCodeGetMemento#
	 * getProcessingNo()
	 */
	@Override
	public ProcessingNo getProcessingNo() {
		return new ProcessingNo(header.qstdtPaymentHeaderPK.processingNo);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see nts.uk.ctx.pr.report.dom.payment.contact.ContactItemsCodeGetMemento#
	 * getProcessingYm()
	 */
	@Override
	public YearMonth getProcessingYm() {
		return new YearMonth(header.qstdtPaymentHeaderPK.processingYM);
	}

}
