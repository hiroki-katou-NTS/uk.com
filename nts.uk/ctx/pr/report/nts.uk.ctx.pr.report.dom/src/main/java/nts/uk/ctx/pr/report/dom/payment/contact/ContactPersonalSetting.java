/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.pr.report.dom.payment.contact;

import lombok.Getter;
import nts.arc.layer.dom.AggregateRoot;
import nts.arc.time.YearMonth;
import nts.uk.shr.com.primitive.sample.ProcessingNo;

/**
 * The Class ContactPersonalSetting.
 */
@Getter
public class ContactPersonalSetting extends AggregateRoot {

	/** The pay bonus atr. */
	public static int PAY_BONUS_ATR = 0;

	/** The spare pay atr. */
	public static int SPARE_PAY_ATR = 0;

	/** The company code. */
	private String companyCode;

	/** The processing no. */
	private ProcessingNo processingNo;

	/** The processing ym. */
	private YearMonth processingYm;

	/** The employee code. */
	private String employeeCode;

	/** The comment. */
	private ReportComment comment;

	/**
	 * Instantiates a new contact personal setting.
	 */
	public ContactPersonalSetting() {
		super();
	}

	/**
	 * Instantiates a new contact personal setting.
	 *
	 * @param memento the memento
	 */
	public ContactPersonalSetting(ContactPersonalSettingGetMemento memento) {
		this.comment = memento.getComment();
		this.companyCode = memento.getCompanyCode();
		this.employeeCode = memento.getEmployeeCode();
		this.processingNo = memento.getProcessingNo();
		this.processingYm = memento.getProcessingYm();
	}

	/**
	 * Save to memento.
	 *
	 * @param memento the memento
	 */
	public void saveToMemento(ContactPersonalSettingSetMemento memento) {
		memento.setCompanyCode(this.companyCode);
		memento.setEmployeeCode(this.employeeCode);
		memento.setSparePayAtr(SPARE_PAY_ATR);
		memento.setPayBonusAtr(PAY_BONUS_ATR);
		memento.setProcessingNo(this.processingNo);
		memento.setProcessingYm(this.processingYm);
		memento.setComment(this.comment);
	}

}
