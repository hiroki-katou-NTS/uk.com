/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.app.find.statutory.worktime.employeeNew;

import lombok.Getter;
import nts.uk.ctx.at.shared.dom.common.CompanyId;
import nts.uk.ctx.at.shared.dom.common.EmployeeId;
import nts.uk.ctx.at.shared.dom.statutory.worktime.employeeNew.ShainRegularWorkHourSetMemento;
import nts.uk.ctx.at.shared.dom.statutory.worktime.sharedNew.WorkingTimeSetting;

/**
 * The Class EmployeeRegularWorkHourDto.
 */
@Getter
public class ShainRegularWorkHourDto implements ShainRegularWorkHourSetMemento {

	/** The employee id. */
	/** 社員ID. */
	private String employeeId;

	/** The working time setting new. */
	/** 会社労働時間設定. */
	private WorkingTimeSetting workingTimeSetting;

	/*
	 * (non-Javadoc)
	 * 
	 * @see nts.uk.ctx.at.shared.dom.statutory.worktime.employeeNew.
	 * EmployeeRegularWorkHourSetMemento#setCompanyId(nts.uk.ctx.at.shared.dom.
	 * common.CompanyId)
	 */
	@Override
	public void setCompanyId(CompanyId companyId) {
		// TODO Auto-generated method stub

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see nts.uk.ctx.at.shared.dom.statutory.worktime.employeeNew.
	 * EmployeeRegularWorkHourSetMemento#setEmployeeId(nts.uk.ctx.at.shared.dom.
	 * common.EmployeeId)
	 */
	@Override
	public void setEmployeeId(EmployeeId employeeId) {
		this.employeeId = employeeId.toString();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see nts.uk.ctx.at.shared.dom.statutory.worktime.employeeNew.
	 * EmployeeRegularWorkHourSetMemento#setWorkingTimeSettingNew(nts.uk.ctx.at.
	 * shared.dom.statutory.worktime.sharedNew.WorkingTimeSettingNew)
	 */
	@Override
	public void setWorkingTimeSet(WorkingTimeSetting workingTimeSetting) {
		this.workingTimeSetting = workingTimeSetting;
	}
}
