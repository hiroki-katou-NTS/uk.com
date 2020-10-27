/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.schedule.infra.repository.pattern.work;

import nts.arc.enums.EnumAdaptor;
import nts.uk.ctx.at.schedule.dom.shift.basicworkregister.WorkdayDivision;
import nts.uk.ctx.at.schedule.dom.shift.pattern.work.WeeklyWorkSettingGetMemento;
import nts.uk.ctx.at.schedule.dom.shift.weeklywrkday.DayOfWeek;
import nts.uk.ctx.at.schedule.infra.entity.shift.pattern.work.KscmtWeeklyWorkSet;
import nts.uk.ctx.at.schedule.infra.entity.shift.pattern.work.KscmtWeeklyWorkSetPK;
import nts.uk.ctx.at.shared.dom.common.CompanyId;

/**
 * The Class JpaWeeklyWorkSettingGetMemento.
 */
public class JpaWeeklyWorkSettingGetMemento implements WeeklyWorkSettingGetMemento{

	
	/** The entity. */
	private KscmtWeeklyWorkSet entity;
	
	/**
	 * Instantiates a new jpa weekly work setting get memento.
	 *
	 * @param entity the entity
	 */
	public JpaWeeklyWorkSettingGetMemento(KscmtWeeklyWorkSet entity) {
		if(entity.getKscmtWeeklyWorkSetPK() == null){
			entity.setKscmtWeeklyWorkSetPK(new KscmtWeeklyWorkSetPK());
		}
		this.entity = entity;
	}
	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * nts.uk.ctx.at.schedule.dom.shift.pattern.work.WeeklyWorkSettingGetMemento
	 * #getCompanyId()
	 */
	@Override
	public CompanyId getCompanyId() {
		return new CompanyId(this.entity.getKscmtWeeklyWorkSetPK().getCid());
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * nts.uk.ctx.at.schedule.dom.shift.pattern.work.WeeklyWorkSettingGetMemento
	 * #getDayOfWeek()
	 */
	@Override
	public DayOfWeek getDayOfWeek() {
		return DayOfWeek.valueOf(this.entity.getKscmtWeeklyWorkSetPK().getDayOfWeek());
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * nts.uk.ctx.at.schedule.dom.shift.pattern.work.WeeklyWorkSettingGetMemento
	 * #getWorkdayDivision()
	 */
	@Override
	public WorkdayDivision getWorkdayDivision() {
		return WorkdayDivision.valuesOf(this.entity.getWorkDayAtr());
	}

	@Override
	public String getContractCode() {
		return this.entity.contractCd;
	}

}
