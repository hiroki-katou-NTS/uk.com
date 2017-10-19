/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.schedule.infra.repository.schedule.basicschedule;

import java.util.List;
import java.util.Optional;

import nts.arc.time.GeneralDate;
import nts.uk.ctx.at.schedule.dom.schedule.basicschedule.BasicScheduleGetMemento;
import nts.uk.ctx.at.schedule.dom.schedule.basicschedule.ConfirmedAtr;
import nts.uk.ctx.at.schedule.dom.schedule.basicschedule.childcareschedule.ChildCareSchedule;
import nts.uk.ctx.at.schedule.dom.schedule.basicschedule.personalfee.WorkSchedulePersonFee;
import nts.uk.ctx.at.schedule.dom.schedule.basicschedule.workschedulebreak.WorkScheduleBreak;
import nts.uk.ctx.at.schedule.dom.schedule.basicschedule.workscheduletime.WorkScheduleTime;
import nts.uk.ctx.at.schedule.dom.schedule.basicschedule.workscheduletimezone.WorkScheduleTimeZone;
import nts.uk.ctx.at.schedule.dom.shift.basicworkregister.WorkdayDivision;
import nts.uk.ctx.at.schedule.infra.entity.schedule.basicschedule.KscdpBasicSchedulePK;
import nts.uk.ctx.at.schedule.infra.entity.schedule.basicschedule.KscdtBasicSchedule;

/**
 * The Class JpaBasicScheduleGetMemento.
 */
public class JpaBasicScheduleGetMemento implements BasicScheduleGetMemento{
	
	/** The entity. */
	private KscdtBasicSchedule entity;	

	/**
	 * Instantiates a new jpa basic schedule get memento.
	 *
	 * @param entity the entity
	 */
	public JpaBasicScheduleGetMemento(KscdtBasicSchedule entity) {
		if(entity.getKscdpBSchedulePK() == null){
			entity.setKscdpBSchedulePK(new KscdpBasicSchedulePK());
		}
		this.entity = entity;
	}

	/* (non-Javadoc)
	 * @see nts.uk.ctx.at.schedule.dom.schedule.basicschedule.BasicScheduleGetMemento#getEmployeeId()
	 */
	@Override
	public String getEmployeeId() {
		return this.entity.getKscdpBSchedulePK().getSId();
	}

	/* (non-Javadoc)
	 * @see nts.uk.ctx.at.schedule.dom.schedule.basicschedule.BasicScheduleGetMemento#getDate()
	 */
	@Override
	public GeneralDate getDate() {
		return this.entity.getKscdpBSchedulePK().getDate();
	}

	/* (non-Javadoc)
	 * @see nts.uk.ctx.at.schedule.dom.schedule.basicschedule.BasicScheduleGetMemento#getWorkTypeCode()
	 */
	@Override
	public String getWorkTypeCode() {
		return this.entity.getWorkTypeCode();
	}

	/* (non-Javadoc)
	 * @see nts.uk.ctx.at.schedule.dom.schedule.basicschedule.BasicScheduleGetMemento#getWorkTimeCode()
	 */
	@Override
	public String getWorkTimeCode() {
		return this.entity.getWorkTimeCode();
	}

	/* (non-Javadoc)
	 * @see nts.uk.ctx.at.schedule.dom.schedule.basicschedule.BasicScheduleGetMemento#getConfirmedAtr()
	 */
	@Override
	public ConfirmedAtr getConfirmedAtr() {
		return ConfirmedAtr.valueOf(this.entity.getConfirmedAtr());
	}

	/* (non-Javadoc)
	 * @see nts.uk.ctx.at.schedule.dom.schedule.basicschedule.BasicScheduleGetMemento#getWorkDayAtr()
	 */
	@Override
	public WorkdayDivision getWorkDayAtr() {
		return WorkdayDivision.WORKINGDAYS;
	}

	/* (non-Javadoc)
	 * @see nts.uk.ctx.at.schedule.dom.schedule.basicschedule.BasicScheduleGetMemento#getWorkScheduleTimeZones()
	 */
	@Override
	public List<WorkScheduleTimeZone> getWorkScheduleTimeZones() {
		return null;
	}

	/* (non-Javadoc)
	 * @see nts.uk.ctx.at.schedule.dom.schedule.basicschedule.BasicScheduleGetMemento#getWorkScheduleBreaks()
	 */
	@Override
	public List<WorkScheduleBreak> getWorkScheduleBreaks() {
		return null;
	}

	/* (non-Javadoc)
	 * @see nts.uk.ctx.at.schedule.dom.schedule.basicschedule.BasicScheduleGetMemento#getWorkScheduleTime()
	 */
	@Override
	public Optional<WorkScheduleTime> getWorkScheduleTime() {
		return null;
	}

	/* (non-Javadoc)
	 * @see nts.uk.ctx.at.schedule.dom.schedule.basicschedule.BasicScheduleGetMemento#getWorkSchedulePersonFees()
	 */
	@Override
	public List<WorkSchedulePersonFee> getWorkSchedulePersonFees() {
		return null;
	}

	/* (non-Javadoc)
	 * @see nts.uk.ctx.at.schedule.dom.schedule.basicschedule.BasicScheduleGetMemento#getChildCareSchedules()
	 */
	@Override
	public List<ChildCareSchedule> getChildCareSchedules() {
		return null;
	}

}
