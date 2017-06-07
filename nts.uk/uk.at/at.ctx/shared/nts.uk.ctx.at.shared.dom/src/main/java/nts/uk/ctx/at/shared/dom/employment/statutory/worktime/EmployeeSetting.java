/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.dom.employment.statutory.worktime;

import nts.arc.layer.dom.AggregateRoot;
import nts.arc.time.YearMonth;
import nts.uk.ctx.at.shared.dom.common.CompanyId;
import nts.uk.ctx.at.shared.dom.employment.statutory.worktime.shared.WorkingTimeSetting;

/**
 * 社員労働時間設定.
 */
public class EmployeeSetting extends AggregateRoot {

	/** 会社ID. */
	private CompanyId companyId;

	/** 労働時間設定. */
	private WorkingTimeSetting workingTimeSetting;

	/** 年月. */
	private YearMonth yearMonth;

	/** 社員ID. */
	private String employeeId;

	/**
	 * Instantiates a new employee setting.
	 *
	 * @param memento the memento
	 */
	public EmployeeSetting(EmployeeSettingGetMemento memento) {
		this.companyId = memento.getCompanyId();
		this.workingTimeSetting = memento.getWorkingTimeSetting();
		this.yearMonth = memento.getYearMonth();
		this.employeeId = memento.getEmployeeId();
	}

	/**
	 * Save to memento.
	 *
	 * @param memento the memento
	 */
	public void saveToMemento(EmployeeSettingSetMemento memento) {
		memento.setCompanyId(this.companyId);
		memento.setEmployeeId(this.employeeId);
		memento.setWorkingTimeSetting(this.workingTimeSetting);
		memento.setYearMonth(this.yearMonth);
	}
}
