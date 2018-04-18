/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.dom.statutory.worktime.employeeNew;

import lombok.Getter;
import nts.arc.layer.dom.AggregateRoot;
import nts.uk.ctx.at.shared.dom.common.CompanyId;
import nts.uk.ctx.at.shared.dom.common.EmployeeId;
import nts.uk.ctx.at.shared.dom.statutory.worktime.sharedNew.StatutoryWorkTimeSet;
import nts.uk.ctx.at.shared.dom.statutory.worktime.sharedNew.WorkingTimeSetting;

/**
 * The Class EmployeeRegularWorkHour.
 */
@Getter
// 社員別通常勤務労働時間.
public class ShainRegularLaborTime extends AggregateRoot implements StatutoryWorkTimeSet {

	/** The company id. */
	// 会社ID
	private CompanyId companyId;

	/** The employee id. */
	// 社員ID
	private EmployeeId employeeId;

	/** The working time setting new. */
	// 会社労働時間設定
	private WorkingTimeSetting workingTimeSet;

	/**
	 * Instantiates a new employee regular work hour.
	 *
	 * @param memento
	 *            the memento
	 */
	public ShainRegularLaborTime(ShainRegularLaborTimeGetMemento memento) {
		this.companyId = memento.getCompanyId();
		this.employeeId = memento.getEmployeeId();
		this.workingTimeSet = memento.getWorkingTimeSet();
	}

	/**
	 * Save to memento.
	 *
	 * @param memento
	 *            the memento
	 */
	public void saveToMemento(ShainRegularLaborTimeSetMemento memento) {
		memento.setCompanyId(this.companyId);
		memento.setEmployeeId(this.employeeId);
		memento.setWorkingTimeSet(this.workingTimeSet);
	}
}
