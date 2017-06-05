/******************************************************************
 * Copyright (c) 2016 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.pr.report.app.payment.contact.command.dto;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import lombok.Getter;
import lombok.Setter;
import nts.arc.time.YearMonth;
import nts.uk.ctx.pr.report.dom.payment.contact.ContactItemsCode;
import nts.uk.ctx.pr.report.dom.payment.contact.ContactItemsCodeGetMemento;
import nts.uk.ctx.pr.report.dom.payment.contact.ContactItemsSetting;
import nts.uk.ctx.pr.report.dom.payment.contact.ContactItemsSettingGetMemento;
import nts.uk.ctx.pr.report.dom.payment.contact.EmpComment;
import nts.uk.ctx.pr.report.dom.payment.contact.ReportComment;
import nts.uk.shr.com.primitive.sample.ProcessingNo;

/**
 * The Class ContactItemsSettingDto.
 */
@Getter
@Setter
public class ContactItemsSettingDto {
	/** The processing no. */
	private int processingNo;

	/** The processing ym. */
	private int processingYm;

	/** The comment initial cp. */
	private String initialCpComment;

	/** The comment month cp. */
	private String monthCpComment;

	/** The emp comment dtos. */
	private List<EmpCommentDto> empCommentDtos;

	/**
	 * To domain.
	 *
	 * @param companyCode
	 *            the company code
	 * @return the contact items setting
	 */
	public ContactItemsSetting toDomain(String companyCode) {
		return new ContactItemsSetting(new ContactItemsSettingGetMementoImpl(companyCode, this));
	}

	/**
	 * The Class ContactItemsSettingGetMementoImpl.
	 */
	public class ContactItemsSettingGetMementoImpl implements ContactItemsSettingGetMemento {

		public ContactItemsSettingGetMementoImpl(String companyCode, ContactItemsSettingDto dto) {

			super();
			this.companyCode = companyCode;
			this.dto = dto;
		}

		/** The dto. */
		private ContactItemsSettingDto dto;

		/** The company code. */
		private String companyCode;

		@Override
		public ContactItemsCode getContactItemsCode() {
			return new ContactItemsCode(new ContactItemsCodeGetMemento() {

				@Override
				public YearMonth getProcessingYm() {
					return YearMonth.of(dto.getProcessingYm());
				}

				@Override
				public ProcessingNo getProcessingNo() {
					return new ProcessingNo(dto.getProcessingNo());
				}

				@Override
				public String getCompanyCode() {
					return companyCode;
				}
			});
		}

		@Override
		public ReportComment getInitialCpComment() {
			return new ReportComment(this.dto.getInitialCpComment());
		}

		@Override
		public ReportComment getMonthCpComment() {
			return new ReportComment(this.dto.getMonthCpComment());
		}

		@Override
		public Set<EmpComment> getMonthEmComments() {
			return this.dto.getEmpCommentDtos().stream().map(empCommentDto -> {
				EmpComment empComment = new EmpComment();
				empComment.setEmpCd(empCommentDto.getEmpCd());
				empComment.setEmployeeName(empCommentDto.getEmployeeName());
				empComment.setInitialComment(new ReportComment(empCommentDto.getInitialComment()));
				empComment.setMonthlyComment(new ReportComment(empCommentDto.getMonthlyComment()));
				return empComment;
			}).collect(Collectors.toSet());
		}

	}
}
