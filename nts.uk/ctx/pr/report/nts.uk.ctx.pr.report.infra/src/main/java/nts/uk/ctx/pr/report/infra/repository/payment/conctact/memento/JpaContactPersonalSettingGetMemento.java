/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.pr.report.infra.repository.payment.conctact.memento;

import nts.arc.time.YearMonth;
import nts.uk.ctx.pr.report.dom.payment.contact.ContactPersonalSettingGetMemento;
import nts.uk.ctx.pr.report.dom.payment.contact.ReportComment;
import nts.uk.ctx.pr.report.infra.entity.payment.contact.QctmtCommentMonthPs;
import nts.uk.shr.com.primitive.sample.ProcessingNo;

/**
 * The Class JpaContactPersonalSettingGetMemento.
 */
public class JpaContactPersonalSettingGetMemento implements ContactPersonalSettingGetMemento {

	/** The entity. */
	private QctmtCommentMonthPs entity;

	/**
	 * Instantiates a new jpa contact personal setting get memento.
	 *
	 * @param entity
	 *            the entity
	 */
	public JpaContactPersonalSettingGetMemento(QctmtCommentMonthPs entity) {
		super();
		this.entity = entity;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * nts.uk.ctx.pr.report.dom.payment.contact.ContactPersonalSettingGetMemento
	 * #getCompanyCode()
	 */
	@Override
	public String getCompanyCode() {
		return this.entity.getQctmtCommentMonthPsPK().getCcd();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * nts.uk.ctx.pr.report.dom.payment.contact.ContactPersonalSettingGetMemento
	 * #getProcessingNo()
	 */
	@Override
	public ProcessingNo getProcessingNo() {
		return new ProcessingNo(this.entity.getQctmtCommentMonthPsPK().getProcessingNo());
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * nts.uk.ctx.pr.report.dom.payment.contact.ContactPersonalSettingGetMemento
	 * #getProcessingYm()
	 */
	@Override
	public YearMonth getProcessingYm() {
		return YearMonth.of(this.entity.getQctmtCommentMonthPsPK().getProcessingYm());
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * nts.uk.ctx.pr.report.dom.payment.contact.ContactPersonalSettingGetMemento
	 * #getEmployeeCode()
	 */
	@Override
	public String getEmployeeCode() {
		return this.entity.getQctmtCommentMonthPsPK().getPId();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * nts.uk.ctx.pr.report.dom.payment.contact.ContactPersonalSettingGetMemento
	 * #getComment()
	 */
	@Override
	public ReportComment getComment() {
		return new ReportComment(this.entity.getComment());
	}

}
