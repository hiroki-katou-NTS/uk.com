/******************************************************************
 * Copyright (c) 2016 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.pr.report.infra.repository.payment.contact;

import lombok.Setter;
import nts.arc.time.DateTimeConstraints;
import nts.arc.time.YearMonth;
import nts.uk.ctx.pr.report.dom.payment.contact.ContactItemsCodeGetMemento;
import nts.uk.ctx.pr.report.infra.entity.payment.contact.QcmtCommentMonthCp;
import nts.uk.shr.com.primitive.sample.ProcessingNo;

/**
 * The Class JpaContactItemsCodeGetMemento.
 */
public class JpaContactItemsCodeGetMemento implements ContactItemsCodeGetMemento {

	/** The comment month cp. */
	@Setter
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
		if (commentMonthCp != null) {
			return this.commentMonthCp.getQcmtCommentMonthCpPK().getCcd();
		}
		return null;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see nts.uk.ctx.pr.report.dom.payment.contact.ContactItemsCodeGetMemento#
	 * getProcessingNo()
	 */
	@Override
	public ProcessingNo getProcessingNo() {
		if (commentMonthCp != null) {
			return new ProcessingNo(
				this.commentMonthCp.getQcmtCommentMonthCpPK().getProcessingNo());
		}
		return new ProcessingNo(0);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see nts.uk.ctx.pr.report.dom.payment.contact.ContactItemsCodeGetMemento#
	 * getProcessingYm()
	 */
	@Override
	public YearMonth getProcessingYm() {
		if (commentMonthCp != null) {
			return YearMonth.of(this.commentMonthCp.getQcmtCommentMonthCpPK().getProcessingYm());
		}
		return YearMonth.of(DateTimeConstraints.LIMIT_YEAR.min(),
			DateTimeConstraints.LIMIT_MONTH.min());
	}

}
