/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.pr.report.infra.repository.payment.conctact.memento;

import nts.arc.time.YearMonth;
import nts.uk.ctx.pr.report.dom.payment.contact.ContactPersonalSettingSetMemento;
import nts.uk.ctx.pr.report.dom.payment.contact.ReportComment;
import nts.uk.ctx.pr.report.infra.entity.payment.contact.QctmtCommentMonthPs;
import nts.uk.ctx.pr.report.infra.entity.payment.contact.QctmtCommentMonthPsPK;
import nts.uk.shr.com.primitive.sample.ProcessingNo;

/**
 * The Class JpaContactPersonalSettingSetMemento.
 */
public class JpaContactPersonalSettingSetMemento implements ContactPersonalSettingSetMemento {

	/** The entity. */
	private QctmtCommentMonthPs entity;

	/**
	 * Instantiates a new jpa contact personal setting set memento.
	 *
	 * @param entity the entity
	 */
	public JpaContactPersonalSettingSetMemento(QctmtCommentMonthPs entity) {
		super();
		this.entity = entity;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * nts.uk.ctx.pr.report.dom.payment.contact.ContactPersonalSettingSetMemento
	 * #setCompanyCode(java.lang.String)
	 */
	@Override
	public void setCompanyCode(String companyCode) {
		QctmtCommentMonthPsPK pk = new QctmtCommentMonthPsPK();
		pk.setCcd(companyCode);
		this.entity.setQctmtCommentMonthPsPK(pk);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * nts.uk.ctx.pr.report.dom.payment.contact.ContactPersonalSettingSetMemento
	 * #setProcessingNo(nts.uk.shr.com.primitive.sample.ProcessingNo)
	 */
	@Override
	public void setProcessingNo(ProcessingNo processingNo) {
		QctmtCommentMonthPsPK pk = this.entity.getQctmtCommentMonthPsPK();
		pk.setProcessingNo(processingNo.v());
		this.entity.setQctmtCommentMonthPsPK(pk);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * nts.uk.ctx.pr.report.dom.payment.contact.ContactPersonalSettingSetMemento
	 * #setProcessingYm(nts.arc.time.YearMonth)
	 */
	@Override
	public void setProcessingYm(YearMonth processingYm) {
		QctmtCommentMonthPsPK pk = this.entity.getQctmtCommentMonthPsPK();
		pk.setProcessingYm(processingYm.v());
		this.entity.setQctmtCommentMonthPsPK(pk);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * nts.uk.ctx.pr.report.dom.payment.contact.ContactPersonalSettingSetMemento
	 * #setEmployeeCode(java.lang.String)
	 */
	@Override
	public void setEmployeeCode(String employeeCode) {
		QctmtCommentMonthPsPK pk = this.entity.getQctmtCommentMonthPsPK();
		pk.setPId(employeeCode);
		this.entity.setQctmtCommentMonthPsPK(pk);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * nts.uk.ctx.pr.report.dom.payment.contact.ContactPersonalSettingSetMemento
	 * #setComment(nts.uk.ctx.pr.report.dom.payment.contact.ReportComment)
	 */
	@Override
	public void setComment(ReportComment comment) {
		this.entity.setComment(comment.v());
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * nts.uk.ctx.pr.report.dom.payment.contact.ContactPersonalSettingSetMemento
	 * #setPayBonusAtr(int)
	 */
	@Override
	public void setPayBonusAtr(int payBonusAtr) {
		QctmtCommentMonthPsPK pk = this.entity.getQctmtCommentMonthPsPK();
		pk.setPayBonusAtr(payBonusAtr);
		this.entity.setQctmtCommentMonthPsPK(pk);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * nts.uk.ctx.pr.report.dom.payment.contact.ContactPersonalSettingSetMemento
	 * #setSparePayAtr(int)
	 */
	@Override
	public void setSparePayAtr(int sparePayAtr) {
		QctmtCommentMonthPsPK pk = this.entity.getQctmtCommentMonthPsPK();
		pk.setSparePayAtr(sparePayAtr);
		this.entity.setQctmtCommentMonthPsPK(pk);
	}

}
