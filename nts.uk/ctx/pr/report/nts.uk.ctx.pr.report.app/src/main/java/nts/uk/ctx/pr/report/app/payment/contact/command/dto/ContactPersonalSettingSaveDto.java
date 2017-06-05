/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.pr.report.app.payment.contact.command.dto;

import lombok.Getter;
import lombok.Setter;
import nts.arc.time.YearMonth;
import nts.uk.ctx.pr.report.dom.payment.contact.ContactPersonalSettingGetMemento;
import nts.uk.ctx.pr.report.dom.payment.contact.ReportComment;
import nts.uk.shr.com.context.AppContexts;
import nts.uk.shr.com.primitive.sample.ProcessingNo;

@Getter
@Setter
public class ContactPersonalSettingSaveDto implements ContactPersonalSettingGetMemento {
	/** The processing no. */
	private int processingNo;

	/** The processing ym. */
	private int processingYm;

	/** The employee code. */
	private String employeeCode;

	/** The comment. */
	private String comment;

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * nts.uk.ctx.pr.report.dom.payment.contact.ContactPersonalSettingGetMemento
	 * #getCompanyCode()
	 */
	@Override
	public String getCompanyCode() {
		return AppContexts.user().companyCode();
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
		return new ProcessingNo(this.processingNo);
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
		return YearMonth.of(processingYm);
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
		return this.employeeCode;
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
		return new ReportComment(this.comment);
	}
}
