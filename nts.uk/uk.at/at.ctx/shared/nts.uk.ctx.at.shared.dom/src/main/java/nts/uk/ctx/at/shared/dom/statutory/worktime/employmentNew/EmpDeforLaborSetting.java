/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.dom.statutory.worktime.employmentNew;

import lombok.Getter;
import nts.uk.ctx.at.shared.dom.common.CompanyId;
import nts.uk.ctx.at.shared.dom.statutory.worktime.sharedNew.DeforLaborSetting;
import nts.uk.ctx.at.shared.dom.statutory.worktime.sharedNew.MonthStatutoryWorkingHourDeforWorker;
import nts.uk.ctx.at.shared.dom.vacation.setting.compensatoryleave.EmploymentCode;

/**
 * The Class EmpMentDeforLaborSetting.
 */
@Getter
// 雇用別変形労働月間労働時間.
public class EmpDeforLaborSetting extends DeforLaborSetting
		implements MonthStatutoryWorkingHourDeforWorker {

	/** The company id. */
	// 会社ID
	private CompanyId companyId;

	/** The employee code. */
	// 社員ID
	private EmploymentCode employeeCode;

	/**
	 * Instantiates a new emp ment defor labor setting.
	 *
	 * @param memento
	 *            the memento
	 */
	public EmpDeforLaborSetting(EmpDeforLaborSettingGetMemento memento) {
		this.companyId = memento.getCompanyId();
		this.employeeCode = memento.getEmploymentCode();
		this.year = memento.getYear();
		this.statutorySetting = memento.getStatutorySetting();
	}

	/**
	 * Save to memento.
	 *
	 * @param memento
	 *            the memento
	 */
	public void saveToMemento(EmpDeforLaborSettingSetMemento memento) {
		memento.setCompanyId(this.companyId);
		memento.setEmploymentCode(this.employeeCode);
		memento.setYear(this.year);
		memento.setStatutorySetting(this.statutorySetting);
	}
}
