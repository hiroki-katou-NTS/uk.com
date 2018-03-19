/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.app.command.statutory.worktime.employeenew;

import lombok.Getter;
import lombok.Setter;
import nts.uk.ctx.at.shared.dom.common.CompanyId;
import nts.uk.ctx.at.shared.dom.common.EmployeeId;
import nts.uk.ctx.at.shared.dom.statutory.worktime.employeeNew.ShainRegularWorkTimeGetMemento;
import nts.uk.ctx.at.shared.dom.statutory.worktime.sharedNew.WorkingTimeSetting;
import nts.uk.shr.com.context.AppContexts;

/**
 * The Class UpdateShainRegularWorkHourCommand.
 */
@Getter
@Setter
public class UpdateShainRegularWorkHourCommand implements ShainRegularWorkTimeGetMemento {

	/** The employee id. */
	/* 社員ID. */
	private String employeeId;

	/** The working time setting new. */
	/* 時間. */
	private WorkingTimeSetting workingTimeSetting;

	/*
	 * (non-Javadoc)
	 * 
	 * @see nts.uk.ctx.at.shared.dom.statutory.worktime.employeeNew.
	 * EmployeeRegularWorkHourGetMemento#getCompanyId()
	 */
	@Override
	public CompanyId getCompanyId() {
		return new CompanyId(AppContexts.user().companyId());
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see nts.uk.ctx.at.shared.dom.statutory.worktime.employeeNew.
	 * EmployeeRegularWorkHourGetMemento#getEmployeeId()
	 */
	@Override
	public EmployeeId getEmployeeId() {
		return new EmployeeId(this.employeeId);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see nts.uk.ctx.at.shared.dom.statutory.worktime.employeeNew.
	 * EmployeeRegularWorkHourGetMemento#getWorkingTimeSettingNew()
	 */
	@Override
	public WorkingTimeSetting getWorkingTimeSet() {
		return this.workingTimeSetting;
	}

}
