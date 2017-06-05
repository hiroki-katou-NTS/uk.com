/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.pr.report.infra.repository.payment.contact;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import javax.ejb.Stateless;

import lombok.Getter;
import lombok.Setter;
import nts.uk.ctx.pr.report.dom.payment.contact.ContactItemsCode;
import nts.uk.ctx.pr.report.dom.payment.contact.ContactItemsSettingSetMemento;
import nts.uk.ctx.pr.report.dom.payment.contact.EmpComment;
import nts.uk.ctx.pr.report.dom.payment.contact.ReportComment;
import nts.uk.ctx.pr.report.infra.entity.payment.contact.QcmtCommentMonthCp;
import nts.uk.ctx.pr.report.infra.entity.payment.contact.QctmtCommentMonthEm;
import nts.uk.ctx.pr.report.infra.entity.payment.contact.QctmtCommentMonthEmPK;
import nts.uk.ctx.pr.report.infra.entity.payment.contact.QctmtCpInitialCmt;
import nts.uk.ctx.pr.report.infra.entity.payment.contact.QctmtCpInitialCmtPK;
import nts.uk.ctx.pr.report.infra.entity.payment.contact.QctmtEmInitialCmt;
import nts.uk.ctx.pr.report.infra.entity.payment.contact.QctmtEmInitialCmtPK;

/**
 * The Class JpaLaborInsuranceOfficeSetMemento.
 */
@Stateless
@Getter
public class JpaContactItemsSettingSetMemento implements ContactItemsSettingSetMemento {

	/** The Constant PAY_BONUS_ATR. */
	public static final int PAY_BONUS_ATR = 0;

	/** The Constant SPARE_PAY_ATR. */
	public static final int SPARE_PAY_ATR = 0;

	/** The comment month cp. */
	@Setter
	private QcmtCommentMonthCp commentMonthCp;

	/** The comment initial cp. */
	@Setter
	private QctmtCpInitialCmt commentInitialCp;

	/** The comment month emps. */
	@Setter
	private List<QctmtCommentMonthEm> commentMonthEmps;

	/** The comment initial emps. */
	@Setter
	private List<QctmtEmInitialCmt> commentInitialEmps;

	/**
	 * Instantiates a new jpa contact items setting set memento.
	 *
	 * @param commentMonthCp
	 *            the comment month cp
	 */
	public JpaContactItemsSettingSetMemento() {
		super();
		this.commentInitialCp = new QctmtCpInitialCmt();
		this.commentMonthCp = new QcmtCommentMonthCp();
	}

	/**
	 * Sets the contact items code.
	 *
	 * @param contactItemsCode
	 *            the new contact items code
	 */
	@Override
	public void setContactItemsCode(ContactItemsCode contactItemsCode) {

		contactItemsCode.saveToMemento(new JpaContactItemsCodeSetMemento(this.commentMonthCp));
		QctmtCpInitialCmtPK pk = new QctmtCpInitialCmtPK();
		pk.setCcd(contactItemsCode.getCompanyCode());
		pk.setPayBonusAtr(PAY_BONUS_ATR);
		pk.setSparePayAtr(SPARE_PAY_ATR);
		this.commentInitialCp.setQctmtCpInitialCmtPK(pk);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * nts.uk.ctx.pr.report.dom.payment.contact.ContactItemsSettingSetMemento#
	 * setInitialCpComment(nts.uk.ctx.pr.report.dom.payment.contact.
	 * ReportComment)
	 */
	@Override
	public void setInitialCpComment(ReportComment reportComment) {
		this.commentInitialCp.setComment(reportComment.v());
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * nts.uk.ctx.pr.report.dom.payment.contact.ContactItemsSettingSetMemento#
	 * setMonthCpComment(nts.uk.ctx.pr.report.dom.payment.contact.ReportComment)
	 */
	@Override
	public void setMonthCpComment(ReportComment reportComment) {
		this.commentMonthCp.setComment(reportComment.v());
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * nts.uk.ctx.pr.report.dom.payment.contact.ContactItemsSettingSetMemento#
	 * setMonthEmComments(java.util.Set)
	 */
	@Override
	public void setMonthEmComments(Set<EmpComment> monthEmComments) {
		JpaContactItemsSettingSetMemento jpa = this;
		this.commentInitialEmps = monthEmComments.stream().map(item -> {
			QctmtEmInitialCmtPK pk = new QctmtEmInitialCmtPK();
			pk.setCcd(jpa.commentInitialCp.getQctmtCpInitialCmtPK().getCcd());
			pk.setEmpCd(item.getEmpCd());
			pk.setPayBonusAtr(PAY_BONUS_ATR);
			pk.setSparePayAtr(SPARE_PAY_ATR);
			QctmtEmInitialCmt cmt = new QctmtEmInitialCmt(pk);
			cmt.setComment(item.getInitialComment().v());
			return cmt;
		}).collect(Collectors.toList());

		this.commentMonthEmps = monthEmComments.stream().map(comment -> {
			QctmtCommentMonthEmPK pk = new QctmtCommentMonthEmPK();
			pk.setCcd(jpa.commentMonthCp.getQcmtCommentMonthCpPK().getCcd());
			pk.setEmpCd(comment.getEmpCd());
			pk.setPayBonusAtr(PAY_BONUS_ATR);
			pk.setProcessingNo(jpa.commentMonthCp.getQcmtCommentMonthCpPK().getProcessingNo());
			pk.setProcessingYm(jpa.commentMonthCp.getQcmtCommentMonthCpPK().getProcessingYm());
			pk.setSparePayAtr(SPARE_PAY_ATR);
			QctmtCommentMonthEm cmt = new QctmtCommentMonthEm(pk);
			cmt.setComment(comment.getMonthlyComment().v());
			return cmt;
		}).collect(Collectors.toList());
	}

}
