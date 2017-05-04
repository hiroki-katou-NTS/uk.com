/******************************************************************
 * Copyright (c) 2016 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.pr.report.dom.payment.contact;

import java.util.Set;

import lombok.Getter;
import nts.arc.layer.dom.AggregateRoot;
import nts.arc.time.YearMonth;
import nts.uk.shr.com.primitive.sample.ProcessingNo;

/**
 * The Class ContactItemsSetting.
 */
@Getter
public class ContactItemsSetting extends AggregateRoot {

	/** The company code. */
	private String companyCode;

	/** The processing no. */
	private ProcessingNo processingNo;

	/** The processing ym. */
	private YearMonth processingYm;

	/** The comment initial cp. */
	private ReportComment initialCpComment;

	/** The comment month cp. */
	private ReportComment monthCpComment;

	/** The month em comments. */
	private Set<EmpComment> monthEmComments;

	// =================== Memento State Support Method ===================
	/**
	 * Instantiates a new contact items setting.
	 *
	 * @param memento
	 *            the memento
	 */
	public ContactItemsSetting(ContactItemsSettingGetMemento memento) {
		this.companyCode = memento.getCompanyCode();
		this.processingNo = memento.getProcessingNo();
		this.processingYm = memento.getProcessingYm();
		this.initialCpComment = memento.getInitialCpComment();
		this.monthCpComment = memento.getMonthCpComment();
		this.monthEmComments = memento.getMonthEmComments();
	}

	/**
	 * Save to memento.
	 *
	 * @param memento
	 *            the memento
	 */
	public void saveToMemento(ContactItemsSettingSetMemento memento) {
		memento.setCompanyCode(this.companyCode);
		memento.setProcessingNo(this.processingNo);
		memento.setProcessingYm(this.processingYm);
		memento.setInitialCpComment(this.initialCpComment);
		memento.setMonthCpComment(this.monthCpComment);
		memento.setMonthEmComments(this.monthEmComments);
	}
}
