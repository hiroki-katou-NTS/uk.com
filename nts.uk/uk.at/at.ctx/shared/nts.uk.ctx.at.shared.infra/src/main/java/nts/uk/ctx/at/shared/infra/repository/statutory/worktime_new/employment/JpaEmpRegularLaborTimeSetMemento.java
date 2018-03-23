/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.infra.repository.statutory.worktime_new.employment;

import lombok.Getter;
import nts.uk.ctx.at.shared.dom.common.CompanyId;
import nts.uk.ctx.at.shared.dom.statutory.worktime.employmentNew.EmpRegularLaborTimeSetMemento;
import nts.uk.ctx.at.shared.dom.statutory.worktime.sharedNew.WorkingTimeSetting;
import nts.uk.ctx.at.shared.dom.vacation.setting.compensatoryleave.EmploymentCode;
import nts.uk.ctx.at.shared.infra.entity.statutory.worktime_new.employment.KshstEmpRegLaborTime;
import nts.uk.ctx.at.shared.infra.entity.statutory.worktime_new.employment.KshstEmpRegLaborTimePK;

/**
 * The Class JpaEmpRegularLaborTimeSetMemento.
 */

/**
 * Gets the entity.
 *
 * @return the entity
 */
@Getter
public class JpaEmpRegularLaborTimeSetMemento implements EmpRegularLaborTimeSetMemento {
	
	/** The entity. */
	private KshstEmpRegLaborTime entity;

	/* 
	 * @see nts.uk.ctx.at.shared.dom.statutory.worktime.employmentNew.EmpRegularWorkTimeSetMemento#setCompanyId(nts.uk.ctx.at.shared.dom.common.CompanyId)
	 */

	public JpaEmpRegularLaborTimeSetMemento(KshstEmpRegLaborTime entity) {
		if(entity.getKshstEmpRegLaborTimePK() == null) {
			entity.setKshstEmpRegLaborTimePK(new KshstEmpRegLaborTimePK());
		}
		this.entity = entity;
	}
	
	@Override
	public void setCompanyId(CompanyId companyId) {
		this.entity.getKshstEmpRegLaborTimePK().setCid(companyId.v());
	}

	/* 
	 * @see nts.uk.ctx.at.shared.dom.statutory.worktime.employmentNew.EmpRegularWorkTimeSetMemento#setWorkingTimeSet(nts.uk.ctx.at.shared.dom.statutory.worktime.sharedNew.WorkingTimeSetting)
	 */
	@Override
	public void setWorkingTimeSet(WorkingTimeSetting workingTimeSettingNew) {
		this.entity.setDailyTime(workingTimeSettingNew.getDailyTime().getDailyTime().v());
		this.entity.setWeeklyTime(workingTimeSettingNew.getWeeklyTime().getTime().v());
		this.entity.setWeekStr(workingTimeSettingNew.getWeeklyTime().getStart().value);
	}

	/* 
	 * @see nts.uk.ctx.at.shared.dom.statutory.worktime.employmentNew.EmpRegularWorkTimeSetMemento#setEmploymentCode(nts.uk.ctx.at.shared.dom.vacation.setting.compensatoryleave.EmploymentCode)
	 */
	@Override
	public void setEmploymentCode(EmploymentCode employmentCode) {
		this.entity.getKshstEmpRegLaborTimePK().setEmpCd(employmentCode.v());
	}

}
