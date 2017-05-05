/******************************************************************
 * Copyright (c) 2016 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.pr.report.app.payment.contact.find.dto;

import java.util.List;
import java.util.Map;

import lombok.Getter;
import lombok.Setter;
import java.util.Set;

import java.util.stream.Collectors;

import nts.gul.collection.CollectionUtil;
import nts.uk.ctx.pr.report.app.payment.contact.command.dto.EmpCommentDto;
import nts.uk.ctx.pr.report.dom.payment.contact.ContactItemsCode;
import nts.uk.ctx.pr.report.dom.payment.contact.ContactItemsSettingSetMemento;
import nts.uk.ctx.pr.report.dom.payment.contact.EmpComment;
import nts.uk.ctx.pr.report.dom.payment.contact.ReportComment;

/**
 * The Class ContactItemsSettingOut.
 */

/**
 * Sets the emp comment dtos.
 *
 * @param empCommentDtos
 *            the new emp comment dtos
 */
@Setter

/**
 * Gets the emp comment dtos.
 *
 * @return the emp comment dtos
 */
@Getter
public class ContactItemsSettingOut implements ContactItemsSettingSetMemento {

	/** The processing no. */
	private int processingNo;

	/** The processing ym. */
	private int processingYm;

	/** The initial cp comment. */
	private String initialCpComment;

	/** The month cp comment. */
	private String monthCpComment;

	/** The emp comment dtos. */
	private List<EmpCommentDto> empCommentDtos;

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * nts.uk.ctx.pr.report.dom.payment.contact.ContactItemsSettingSetMemento#
	 * setContactItemsCode(nts.uk.ctx.pr.report.dom.payment.contact.
	 * ContactItemsCode)
	 */
	@Override
	public void setContactItemsCode(ContactItemsCode contactItemsCode) {
		this.processingNo = contactItemsCode.getProcessingNo().v();
		this.processingYm = contactItemsCode.getProcessingYm().v();
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
		this.initialCpComment = reportComment.v();
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
		this.monthCpComment = reportComment.v();
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
		this.empCommentDtos = monthEmComments.stream().map(comment -> {
			EmpCommentDto dto = new EmpCommentDto();
			dto.setEmpCd(comment.getEmpCd());
			dto.setEmployeeName(comment.getEmployeeName());
			dto.setInitialComment(comment.getInitialComment().v());
			dto.setMonthlyComment(comment.getMonthlyComment().v());
			return dto;
		}).collect(Collectors.toList());
	}

	/**
	 * Merge data.
	 *
	 * @param dto
	 *            the dto
	 */
	public void mergeData(List<EmpCommentFindDto> dto) {
		if (CollectionUtil.isEmpty(this.empCommentDtos)) {
			this.empCommentDtos = dto.stream().map(comment -> {
				EmpCommentDto empCommentDto = new EmpCommentDto();
				empCommentDto.setEmpCd(comment.getEmployeeCode());
				empCommentDto.setEmployeeName(comment.getEmployeeName());
				empCommentDto.setInitialComment("");
				empCommentDto.setMonthlyComment("");
				return empCommentDto;
			}).collect(Collectors.toList());
		} else {
			List<EmpCommentDto> dataNew = dto.stream().map(comment -> {
				EmpCommentDto empCommentDto = new EmpCommentDto();
				empCommentDto.setEmpCd(comment.getEmployeeCode());
				empCommentDto.setEmployeeName(comment.getEmployeeName());
				empCommentDto.setInitialComment("");
				empCommentDto.setMonthlyComment("");

				List<EmpCommentDto> empCommentBeforeDto = this.empCommentDtos.stream()
					.filter(item -> {
						return item.getEmpCd().equals(comment.getEmployeeCode());
					}).collect(Collectors.toList());

				if (!CollectionUtil.isEmpty(empCommentBeforeDto)) {
					empCommentDto.setInitialComment(empCommentBeforeDto.get(0).getInitialComment());
					empCommentDto.setMonthlyComment(empCommentBeforeDto.get(0).getMonthlyComment());
				}
				return empCommentDto;
			}).collect(Collectors.toList());
		}

	}
}
