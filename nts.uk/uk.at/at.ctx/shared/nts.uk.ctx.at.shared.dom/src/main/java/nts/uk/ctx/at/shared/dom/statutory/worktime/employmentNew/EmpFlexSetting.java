/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.dom.statutory.worktime.employmentNew;

import lombok.Getter;
import nts.uk.ctx.at.shared.dom.common.CompanyId;
import nts.uk.ctx.at.shared.dom.statutory.worktime.sharedNew.FlexSetting;
import nts.uk.ctx.at.shared.dom.statutory.worktime.sharedNew.MonthStatutoryWorkingHourFlexWork;
import nts.uk.ctx.at.shared.dom.vacation.setting.compensatoryleave.EmploymentCode;

/**
 * The Class EmpMentFlexSetting.
 */
@Getter
// 雇用別フレックス勤務月間労働時間.
public class EmpFlexSetting extends FlexSetting implements MonthStatutoryWorkingHourFlexWork {

	/** The company id. */
	/** 会社ID. */
	private CompanyId companyId;

	/** The employment code. */
	/** 社員ID. */
	private EmploymentCode employmentCode;

	/**
	 * Instantiates a new emp ment flex setting.
	 *
	 * @param memento
	 *            the memento
	 */
	public EmpFlexSetting(EmpFlexSettingGetMemento memento) {
		this.companyId = memento.getCompanyId();
		this.employmentCode = memento.getEmploymentCode();
		this.year = memento.getYear();
		this.statutorySetting = memento.getStatutorySetting();
		this.specifiedSetting = memento.getSpecifiedSetting();
	}

	/**
	 * Save to memento.
	 *
	 * @param memento
	 *            the memento
	 */
	public void saveToMemento(EmpFlexSettingSetMemento memento) {
		memento.setCompanyId(this.companyId);
		memento.setEmploymentCode(this.employmentCode);
		memento.setYear(this.year);
		memento.setStatutorySetting(this.statutorySetting);
		memento.setSpecifiedSetting(this.specifiedSetting);
	}
}
