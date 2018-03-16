/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.app.command.statutory.worktime.employmentnew;

import lombok.Getter;
import lombok.Setter;
import nts.uk.ctx.at.shared.dom.common.CompanyId;
import nts.uk.ctx.at.shared.dom.statutory.worktime.employmentNew.EmpRegularWorkHourGetMemento;
import nts.uk.ctx.at.shared.dom.statutory.worktime.sharedNew.WorkingTimeSetting;
import nts.uk.ctx.at.shared.dom.vacation.setting.compensatoryleave.EmploymentCode;
import nts.uk.shr.com.context.AppContexts;

/**
 * The Class SaveEmploymentRegularWorkHourCommand.
 */
@Getter
@Setter
public class SaveEmpRegularWorkHourCommand implements EmpRegularWorkHourGetMemento {

	/** The employment code. */
	/* 雇用コード. */
	private String employmentCode;

	/** The working time setting new. */
	/* 時間. */
	private WorkingTimeSetting  workingTimeSetting;

	/*
	 * (non-Javadoc)
	 * 
	 * @see nts.uk.ctx.at.shared.dom.statutory.worktime.employmentNew.
	 * EmploymentRegularWorkHourGetMemento#getCompanyId()
	 */
	@Override
	public CompanyId getCompanyId() {
		return new CompanyId(AppContexts.user().companyId());
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see nts.uk.ctx.at.shared.dom.statutory.worktime.employmentNew.
	 * EmploymentRegularWorkHourGetMemento#getEmploymentCode()
	 */
	@Override
	public EmploymentCode getEmploymentCode() {
		return new EmploymentCode(this.employmentCode);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see nts.uk.ctx.at.shared.dom.statutory.worktime.employmentNew.
	 * EmploymentRegularWorkHourGetMemento#getWorkingTimeSettingNew()
	 */
	@Override
	public WorkingTimeSetting getWorkingTimeSet() {
		return this.workingTimeSetting;
	}

}
