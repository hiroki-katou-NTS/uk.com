/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.dom.statutory.worktime.employeeNew;

import lombok.Getter;
import nts.arc.layer.dom.AggregateRoot;
import nts.uk.ctx.at.shared.dom.common.CompanyId;
import nts.uk.ctx.at.shared.dom.common.EmployeeId;
import nts.uk.ctx.at.shared.dom.statutory.worktime.sharedNew.StatutoryWorkTimeSetting;
import nts.uk.ctx.at.shared.dom.statutory.worktime.sharedNew.WorkingTimeSettingNew;


/**
 * The Class EmployeeSpeDeforLaborHour.
 */
@Getter
// 社員別変形労働労働時間
public class EmployeeSpeDeforLaborHour extends AggregateRoot implements StatutoryWorkTimeSetting{
	
	/** The company id. */
	private CompanyId companyId;
	
	/** The employee id. */
	private EmployeeId employeeId;
	
	/** The working time setting new. */
	private WorkingTimeSettingNew workingTimeSettingNew;

	/* (non-Javadoc)
	 * @see nts.uk.ctx.at.shared.dom.statutory.worktime.sharedNew.StatutoryWorkTimeSetting#getWorkingTimeSettingNew()
	 */
	@Override
	public WorkingTimeSettingNew getWorkingTimeSettingNew() {
		return workingTimeSettingNew;
	}
	
	/**
	 * Instantiates a new employee spe defor labor hour.
	 *
	 * @param memento the memento
	 */
	public EmployeeSpeDeforLaborHour (EmployeeSpeDeforLaborHour memento) {
		this.companyId  = memento.getCompanyId();
		this.employeeId = memento.getEmployeeId();
		this.workingTimeSettingNew = memento.getWorkingTimeSettingNew();
	}
	
	/**
	 * Save to memento.
	 *
	 * @param memento the memento
	 */
	public void saveToMemento (EmployeeSpeDeforLaborHourSetMemento memento) {
		memento.setCompanyId(this.companyId);
		memento.setEmployeeId(this.employeeId);
		memento.setWorkingTimeSettingNew(this.workingTimeSettingNew);
	}
}
