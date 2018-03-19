/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.app.find.statutory.worktime.employmentNew;

import lombok.Getter;
import nts.uk.ctx.at.shared.dom.common.CompanyId;
import nts.uk.ctx.at.shared.dom.statutory.worktime.employmentNew.EmpDeforLaborWorkingTimeSetMemento;
import nts.uk.ctx.at.shared.dom.statutory.worktime.sharedNew.WorkingTimeSetting;
import nts.uk.ctx.at.shared.dom.vacation.setting.compensatoryleave.EmploymentCode;

/**
 * Gets the working time setting new.
 *
 * @return the working time setting new
 */
@Getter
public class EmpDeforLaborWorkingHourDto implements EmpDeforLaborWorkingTimeSetMemento {

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
	 * EmploymentDeforLaborWorkingHourSetMemento#setCompanyId(nts.uk.ctx.at.
	 * shared.dom.common.CompanyId)
	 */
	@Override
	public void setCompanyId(CompanyId companyId) {
		// TODO Auto-generated method stub

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see nts.uk.ctx.at.shared.dom.statutory.worktime.employmentNew.
	 * EmploymentDeforLaborWorkingHourSetMemento#setEmploymentCode(nts.uk.ctx.at
	 * .shared.dom.vacation.setting.compensatoryleave.EmploymentCode)
	 */
	@Override
	public void setEmploymentCode(EmploymentCode employmentCode) {
		this.employmentCode = employmentCode.toString();

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see nts.uk.ctx.at.shared.dom.statutory.worktime.employmentNew.
	 * EmploymentDeforLaborWorkingHourSetMemento#setWorkingTimeSettingNew(nts.uk
	 * .ctx.at.shared.dom.statutory.worktime.sharedNew.WorkingTimeSettingNew)
	 */
	@Override
	public void setWorkingTimeSet(WorkingTimeSetting workingTimeSetting) {
		this.workingTimeSetting = workingTimeSetting;

	}

}
