/******************************************************************
 * Copyright (c) 2016 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.pr.report.infra.repository.payment.contact;

import nts.arc.time.YearMonth;
import nts.uk.ctx.pr.report.dom.payment.contact.ContactItemsCodeSetMemento;
import nts.uk.ctx.pr.report.infra.entity.payment.contact.QcmtCommentMonthCp;
import nts.uk.ctx.pr.report.infra.entity.payment.contact.QcmtCommentMonthCpPK;
import nts.uk.shr.com.primitive.sample.ProcessingNo;

/**
 * The Class JpaContactItemsCodeGetMemento.
 */
public class JpaContactItemsCodeSetMemento implements ContactItemsCodeSetMemento {

	/** The pay bonus atr. */
	public static int PAY_BONUS_ATR = 0;

	/** The spare pay atr. */
	public static int SPARE_PAY_ATR = 0;

	/** The comment month cp. */
	private QcmtCommentMonthCp commentMonthCp;

	/**
	 * Instantiates a new jpa contact items code get memento.
	 *
	 * @param commentMonthCp
	 *            the comment month cp
	 */
	public JpaContactItemsCodeSetMemento(QcmtCommentMonthCp commentMonthCp) {
		this.commentMonthCp = commentMonthCp;
	}

	@Override
	public void setCompanyCode(String companyCode) {
		QcmtCommentMonthCpPK pk = new QcmtCommentMonthCpPK();
		pk.setCcd(companyCode);
		this.commentMonthCp.setQcmtCommentMonthCpPK(pk);
	}

	@Override
	public void setProcessingNo(ProcessingNo processingNo) {
		QcmtCommentMonthCpPK pk = this.commentMonthCp.getQcmtCommentMonthCpPK();
		pk.setProcessingNo(processingNo.v());
		this.commentMonthCp.setQcmtCommentMonthCpPK(pk);
	}

	@Override
	public void setProcessingYm(YearMonth yearMonth) {
		QcmtCommentMonthCpPK pk = this.commentMonthCp.getQcmtCommentMonthCpPK();
		pk.setProcessingYm(yearMonth.v());
		pk.setPayBonusAtr(PAY_BONUS_ATR);
		pk.setSparePayAtr(SPARE_PAY_ATR);
		this.commentMonthCp.setQcmtCommentMonthCpPK(pk);
	}

}
