/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.infra.repository.statutory.worktime_new.employment;

import lombok.Getter;
import nts.uk.ctx.at.shared.dom.common.CompanyId;
import nts.uk.ctx.at.shared.dom.statutory.worktime.employmentNew.EmpTransLaborTimeSetMemento;
import nts.uk.ctx.at.shared.dom.statutory.worktime.sharedNew.WorkingTimeSetting;
import nts.uk.ctx.at.shared.dom.vacation.setting.compensatoryleave.EmploymentCode;
import nts.uk.ctx.at.shared.infra.entity.statutory.worktime_new.employment.KshstEmpTransLabTime;
import nts.uk.ctx.at.shared.infra.entity.statutory.worktime_new.employment.KshstEmpTransLabTimePK;
import nts.uk.ctx.at.shared.infra.repository.statutory.worktime_new.share.JpaDefaultSettingSetMemento;

/**
 * The Class JpaEmpTransLaborTimeSetMemento.
 */

/**
 * Gets the entity.
 *
 * @return the entity
 */
@Getter
public class JpaEmpTransLaborTimeSetMemento extends JpaDefaultSettingSetMemento implements EmpTransLaborTimeSetMemento{
	
	/** The entity. */
	private KshstEmpTransLabTime entity;

	public JpaEmpTransLaborTimeSetMemento(KshstEmpTransLabTime entity) {
		super();
		if(entity.getKshstEmpTransLabTimePK() == null) {
			entity.setKshstEmpTransLabTimePK(new KshstEmpTransLabTimePK());
		}
		this.entity = entity;
	}

	/* 
	 * @see nts.uk.ctx.at.shared.dom.statutory.worktime.employmentNew.EmpTransWorkTimeSetMemento#setCompanyId(nts.uk.ctx.at.shared.dom.common.CompanyId)
	 */
	@Override
	public void setCompanyId(CompanyId companyId) {
		this.entity.getKshstEmpTransLabTimePK().setCid(companyId.v());
	}

	/* 
	 * @see nts.uk.ctx.at.shared.dom.statutory.worktime.employmentNew.EmpTransWorkTimeSetMemento#setWorkingTimeSet(nts.uk.ctx.at.shared.dom.statutory.worktime.sharedNew.WorkingTimeSetting)
	 */
	@Override
	public void setWorkingTimeSet(WorkingTimeSetting workingTimeSettingNew) {
		this.entity.setDailyTime(workingTimeSettingNew.getDailyTime().getDailyTime().v());
		this.entity.setWeeklyTime(workingTimeSettingNew.getWeeklyTime().getTime().v());
		this.entity.setWeekStr(workingTimeSettingNew.getWeeklyTime().getStart().value);
	}

	/* 
	 * @see nts.uk.ctx.at.shared.dom.statutory.worktime.employmentNew.EmpTransWorkTimeSetMemento#setEmploymentCode(nts.uk.ctx.at.shared.dom.vacation.setting.compensatoryleave.EmploymentCode)
	 */
	@Override
	public void setEmploymentCode(EmploymentCode employmentCode) {
		this.entity.getKshstEmpTransLabTimePK().setEmpCd(employmentCode.v());;
	}


}
