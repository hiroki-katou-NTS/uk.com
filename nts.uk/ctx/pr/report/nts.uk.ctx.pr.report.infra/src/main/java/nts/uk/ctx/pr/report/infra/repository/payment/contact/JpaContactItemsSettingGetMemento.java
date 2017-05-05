/******************************************************************
 * Copyright (c) 2016 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.pr.report.infra.repository.payment.contact;

import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import javax.ejb.Stateless;

import lombok.Setter;
import nts.gul.collection.CollectionUtil;
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
	 * Instantiates a new jpa contact items setting get memento.
	 *
	 * @param commentMonthCp
	 *            the comment month cp
	 */
	public JpaContactItemsSettingGetMemento() {
		super();
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
		if (this.commentInitialCp != null) {
			return new ReportComment(this.commentInitialCp.getComment());
		}
		return new ReportComment("");
	}

	/**
	 * Gets the month cp comment.
	 *
	 * @return the month cp comment
	 */
	@Override
	public ReportComment getMonthCpComment() {
		if (this.commentMonthCp != null) {
			return new ReportComment(this.commentMonthCp.getComment());
		}
		return new ReportComment("");
	}

	/**
	 * Gets the month em comments.
	 *
	 * @return the month em comments
	 */
	@Override
	public Set<EmpComment> getMonthEmComments() {
		if(CollectionUtil.isEmpty(this.commentMonthEmps) && CollectionUtil.isEmpty(this.commentInitialEmps)){
			return new HashSet<>();
		}
		
		Map<String, EmpComment> mapEmpComment = this.commentMonthEmps.stream().map(comment -> {
			EmpComment empComment = new EmpComment();
			empComment.setEmpCd(comment.getQctmtCommentMonthEmPK().getCcd());
			empComment.setMonthlyComment(new ReportComment(comment.getComment()));
			return empComment;
		}).collect(Collectors.toMap(empComment -> empComment.getEmpCd(), empComment -> empComment));
		this.commentInitialEmps.stream().forEach(comment -> {
			EmpComment empComment = mapEmpComment.get(comment.getQctmtEmInitialCmtPK().getEmpCd());
			if (empComment == null) {
				empComment = new EmpComment();
				empComment.setEmpCd(comment.getQctmtEmInitialCmtPK().getCcd());
				empComment.setInitialComment(new ReportComment(comment.getComment()));
				mapEmpComment.put(comment.getQctmtEmInitialCmtPK().getEmpCd(), empComment);
			} else {
				empComment.setInitialComment(new ReportComment(comment.getComment()));
				mapEmpComment.replace(comment.getQctmtEmInitialCmtPK().getEmpCd(), empComment);
			}
		});

		Set<String> setEmpCd = mapEmpComment.keySet();
		return setEmpCd.stream().map(item -> {
			return mapEmpComment.get(item);
		}).collect(Collectors.toSet());
	}

}
