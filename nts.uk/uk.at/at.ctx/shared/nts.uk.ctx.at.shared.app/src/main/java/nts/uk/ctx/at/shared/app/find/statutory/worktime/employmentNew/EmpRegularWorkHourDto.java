/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.app.find.statutory.worktime.employmentNew;

import lombok.Getter;
import nts.uk.ctx.at.shared.dom.common.CompanyId;
import nts.uk.ctx.at.shared.dom.statutory.worktime.employmentNew.EmpRegularWorkTimeSetMemento;
import nts.uk.ctx.at.shared.dom.statutory.worktime.sharedNew.WorkingTimeSetting;
import nts.uk.ctx.at.shared.dom.vacation.setting.compensatoryleave.EmploymentCode;

/**
 * The Class EmploymentRegularWorkHourDto.
 */
@Getter
public class EmpRegularWorkHourDto implements EmpRegularWorkTimeSetMemento {

	/** The employment code. */
	/** 雇用コード. */
	private String employmentCode;

	/** The working time setting new. */
	/** 会社労働時間設定. */
	private WorkingTimeSetting workingTimeSetting;

	/*
	 * (non-Javadoc)
	 * 
	 * @see nts.uk.ctx.at.shared.dom.statutory.worktime.employmentNew.
	 * EmploymentRegularWorkHourSetMemento#setCompanyId(nts.uk.ctx.at.shared.dom
	 * .common.CompanyId)
	 */
	@Override
	public void setCompanyId(CompanyId companyId) {
		// TODO Auto-generated method stub

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see nts.uk.ctx.at.shared.dom.statutory.worktime.employmentNew.
	 * EmploymentRegularWorkHourSetMemento#setEmploymentCode(nts.uk.ctx.at.
	 * shared.dom.vacation.setting.compensatoryleave.EmploymentCode)
	 */
	@Override
	public void setEmploymentCode(EmploymentCode employmentCode) {
		this.employmentCode = employmentCode.toString();

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see nts.uk.ctx.at.shared.dom.statutory.worktime.employmentNew.
	 * EmploymentRegularWorkHourSetMemento#setWorkingTimeSettingNew(nts.uk.ctx.
	 * at.shared.dom.statutory.worktime.sharedNew.WorkingTimeSettingNew)
	 */
	@Override
	public void setWorkingTimeSet(WorkingTimeSetting workingTimeSetting) {
		this.workingTimeSetting = workingTimeSetting;
	}

}
