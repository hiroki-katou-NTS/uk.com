/******************************************************************
 * Copyright (c) 2016 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.pr.report.infra.repository.payment.contact;

import java.util.List;
import java.util.Set;
import java.util.stream.Collector;
import java.util.stream.Collectors;

import javax.ejb.Stateless;

import nts.uk.ctx.pr.report.dom.payment.contact.ContactItemsCode;
import nts.uk.ctx.pr.report.dom.payment.contact.ContactItemsSettingGetMemento;
import nts.uk.ctx.pr.report.dom.payment.contact.EmpComment;
import nts.uk.ctx.pr.report.dom.payment.contact.ReportComment;
import nts.uk.ctx.pr.report.infra.entity.payment.contact.QcmtCommentMonthCp;
import nts.uk.ctx.pr.report.infra.entity.payment.contact.QctmtCommentMonthEm;
import nts.uk.ctx.pr.report.infra.entity.payment.contact.QctmtCpInitialCmt;
import nts.uk.ctx.pr.report.infra.entity.payment.contact.QctmtEmInitialCmt;

/**
 * The Class JpaContactItemsSettingGetMemento.
 */
@Stateless
public class JpaContactItemsSettingGetMemento implements ContactItemsSettingGetMemento {

	/** The comment month cp. */
	private QcmtCommentMonthCp commentMonthCp;

	/** The comment initial cp. */
	private QctmtCpInitialCmt commentInitialCp;

	/** The comment month emps. */
	private List<QctmtCommentMonthEm> commentMonthEmps;

	/** The comment initial emps. */
	private List<QctmtEmInitialCmt> commentInitialEmps;

	/**
	 * Instantiates a new jpa contact items setting get memento.
	 *
	 * @param commentMonthCp
	 *            the comment month cp
	 */
	public JpaContactItemsSettingGetMemento(QcmtCommentMonthCp commentMonthCp,
		QctmtCpInitialCmt commentInitialCp, List<QctmtCommentMonthEm> commentMonthEmps,
		List<QctmtEmInitialCmt> commentInitialEmps) {
		this.commentMonthCp = commentMonthCp;
		this.commentInitialCp = commentInitialCp;
		this.commentMonthEmps = commentMonthEmps;
		this.commentInitialEmps = commentInitialEmps;
	}

	/**
	 * Gets the contact items code.
	 *
	 * @return the contact items code
	 */
	@Override
	public ContactItemsCode getContactItemsCode() {
		return new ContactItemsCode(new JpaContactItemsCodeGetMemento(this.commentMonthCp));
	}

	/**
	 * Gets the initial cp comment.
	 *
	 * @return the initial cp comment
	 */
	@Override
	public ReportComment getInitialCpComment() {
		return new ReportComment(this.commentInitialCp.getComment());
	}

	/**
	 * Gets the month cp comment.
	 *
	 * @return the month cp comment
	 */
	@Override
	public ReportComment getMonthCpComment() {
		return new ReportComment(this.commentMonthCp.getComment());
	}

	/**
	 * Gets the month em comments.
	 *
	 * @return the month em comments
	 */
	@Override
	public Set<EmpComment> getMonthEmComments() {
		
		return this.commentMonthEmps.stream().map(comment -> {
			EmpComment empComment = new EmpComment();
			empComment.setEmpCd(comment.getQctmtCommentMonthEmPK().getCcd());
			empComment.setMonthlyComment(new ReportComment(comment.getComment()));
			return empComment;
		}).collect(Collectors.toSet());
	}

}
