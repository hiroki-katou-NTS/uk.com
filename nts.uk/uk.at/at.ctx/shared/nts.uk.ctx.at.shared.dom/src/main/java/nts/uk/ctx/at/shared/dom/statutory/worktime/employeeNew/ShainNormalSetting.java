/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.dom.statutory.worktime.employeeNew;

import lombok.Getter;
import nts.uk.ctx.at.shared.dom.common.CompanyId;
import nts.uk.ctx.at.shared.dom.common.EmployeeId;
import nts.uk.ctx.at.shared.dom.statutory.worktime.sharedNew.MonthStatutoryWorkingHourDeforWorker;
import nts.uk.ctx.at.shared.dom.statutory.worktime.sharedNew.NormalSetting;

/**
 * The Class EmpNormalSetting.
 */
@Getter
// 社員別通常勤務月間労働時間
public class ShainNormalSetting extends NormalSetting
		implements MonthStatutoryWorkingHourDeforWorker {

	/** The company id. */
	// 会社ID
	private CompanyId companyId;

	/** The employee id. */
	// 社員ID
	private EmployeeId employeeId;

	/**
	 * Instantiates a new emp normal setting.
	 *
	 * @param memento
	 *            the memento
	 */
	public ShainNormalSetting(ShainNormalSettingGetMemento memento) {
		this.companyId = memento.getCompanyId();
		this.employeeId = memento.getEmployeeId();
		this.year = memento.getYear();
		this.statutorySetting = memento.getStatutorySetting();
	}

	/**
	 * Save to memento.
	 *
	 * @param memento
	 *            the memento
	 */
	public void saveToMemento(ShainNormalSettingSetMemento memento) {
		memento.setCompanyId(this.companyId);
		memento.setEmployeeId(this.employeeId);
		memento.setYear(this.year);
		memento.setStatutorySetting(this.statutorySetting);
	}
}
