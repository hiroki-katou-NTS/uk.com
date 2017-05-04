/******************************************************************
 * Copyright (c) 2016 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.pr.report.infra.repository.payment.contact;

import nts.arc.time.YearMonth;
import nts.uk.ctx.pr.report.dom.payment.contact.ContactItemsCodeGetMemento;
import nts.uk.ctx.pr.report.infra.entity.payment.contact.QcmtCommentMonthCp;
import nts.uk.shr.com.primitive.sample.ProcessingNo;

/**
 * The Class JpaContactItemsCodeGetMemento.
 */
public class JpaContactItemsCodeGetMemento implements ContactItemsCodeGetMemento {

	/** The comment month cp. */
	private QcmtCommentMonthCp commentMonthCp;

	/**
	 * Instantiates a new jpa contact items code get memento.
	 *
	 * @param commentMonthCp
	 *            the comment month cp
	 */
	public JpaContactItemsCodeGetMemento(QcmtCommentMonthCp commentMonthCp) {
		this.commentMonthCp = commentMonthCp;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see nts.uk.ctx.pr.report.dom.payment.contact.ContactItemsCodeGetMemento#
	 * getCompanyCode()
	 */
	@Override
	public String getCompanyCode() {
		return this.commentMonthCp.getQcmtCommentMonthCpPK().getCcd();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see nts.uk.ctx.pr.report.dom.payment.contact.ContactItemsCodeGetMemento#
	 * getProcessingNo()
	 */
	@Override
	public ProcessingNo getProcessingNo() {
		return new ProcessingNo(this.commentMonthCp.getQcmtCommentMonthCpPK().getProcessingNo());
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see nts.uk.ctx.pr.report.dom.payment.contact.ContactItemsCodeGetMemento#
	 * getProcessingYm()
	 */
	@Override
	public YearMonth getProcessingYm() {
		return YearMonth.of(this.commentMonthCp.getQcmtCommentMonthCpPK().getProcessingYm());
	}

}
