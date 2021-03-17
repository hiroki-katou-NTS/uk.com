/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.schedule.infra.repository.pattern.work;

import nts.uk.ctx.at.schedule.dom.shift.basicworkregister.WorkdayDivision;
import nts.uk.ctx.at.schedule.dom.shift.pattern.work.WeeklyWorkSettingSetMemento;
import nts.uk.ctx.at.schedule.dom.shift.weeklywrkday.DayOfWeek;
import nts.uk.ctx.at.schedule.infra.entity.shift.pattern.work.KscmtWeeklyWorkSet;
import nts.uk.ctx.at.schedule.infra.entity.shift.pattern.work.KscmtWeeklyWorkSetPK;
import nts.uk.ctx.at.shared.dom.common.CompanyId;

/**
 * The Class JpaWeeklyWorkSettingSetMemento.
 */
public class JpaWeeklyWorkSettingSetMemento implements WeeklyWorkSettingSetMemento{
	
	/** The entity. */
	private KscmtWeeklyWorkSet entity;
	
	/**
	 * Instantiates a new jpa weekly work setting set memento.
	 *
	 * @param entity the entity
	 */
	public JpaWeeklyWorkSettingSetMemento(KscmtWeeklyWorkSet entity) {
		if(entity.getKscmtWeeklyWorkSetPK() == null){
			entity.setKscmtWeeklyWorkSetPK(new KscmtWeeklyWorkSetPK());
		}
		this.entity = entity;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * nts.uk.ctx.at.schedule.dom.shift.pattern.work.WeeklyWorkSettingSetMemento
	 * #setCompanyId(nts.uk.ctx.at.shared.dom.common.CompanyId)
	 */
	@Override
	public void setCompanyId(CompanyId companyId) {
		this.entity.getKscmtWeeklyWorkSetPK().setCid(companyId.v());
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * nts.uk.ctx.at.schedule.dom.shift.pattern.work.WeeklyWorkSettingSetMemento
	 * #setDayOfWeek(nts.uk.ctx.at.schedule.dom.shift.pattern.work.DayOfWeek)
	 */
	@Override
	public void setDayOfWeek(DayOfWeek dayOfWeek) {
		this.entity.getKscmtWeeklyWorkSetPK().setDayOfWeek(dayOfWeek.value);

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * nts.uk.ctx.at.schedule.dom.shift.pattern.work.WeeklyWorkSettingSetMemento
	 * #setWorkdayDivision(nts.uk.ctx.at.schedule.dom.shift.basicworkregister.
	 * WorkdayDivision)
	 */
	@Override
	public void setWorkdayDivision(WorkdayDivision workdayDivision) {
		this.entity.setWorkDayAtr(workdayDivision.value);
	}

	@Override
	public void setContractCode(String constractCode) {
		this.entity.contractCd = (constractCode);
	}

}
