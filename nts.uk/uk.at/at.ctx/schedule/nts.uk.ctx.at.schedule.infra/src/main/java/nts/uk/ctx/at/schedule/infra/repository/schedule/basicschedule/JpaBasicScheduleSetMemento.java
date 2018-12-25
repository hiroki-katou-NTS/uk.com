/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.schedule.infra.repository.schedule.basicschedule;

import java.util.List;
import java.util.Optional;

import nts.arc.time.GeneralDate;
import nts.uk.ctx.at.schedule.dom.schedule.basicschedule.BasicScheduleSetMemento;
import nts.uk.ctx.at.schedule.dom.schedule.basicschedule.ConfirmedAtr;
import nts.uk.ctx.at.schedule.dom.schedule.basicschedule.childcareschedule.ChildCareSchedule;
import nts.uk.ctx.at.schedule.dom.schedule.basicschedule.personalfee.WorkSchedulePersonFee;
import nts.uk.ctx.at.schedule.dom.schedule.basicschedule.workschedulebreak.WorkScheduleBreak;
import nts.uk.ctx.at.schedule.dom.schedule.basicschedule.workscheduletime.WorkScheduleTime;
import nts.uk.ctx.at.schedule.dom.schedule.basicschedule.workscheduletimezone.WorkScheduleTimeZone;
//import nts.uk.ctx.at.schedule.dom.shift.basicworkregister.WorkdayDivision;
import nts.uk.ctx.at.schedule.infra.entity.schedule.basicschedule.KscdtBasicSchedulePK;
import nts.uk.ctx.at.schedule.infra.entity.schedule.basicschedule.KscdtBasicSchedule;

/**
 * The Class JpaBasicScheduleSetMemento.
 */
public class JpaBasicScheduleSetMemento implements BasicScheduleSetMemento{
	
	/** The entity. */
	private KscdtBasicSchedule entity;

	/**
	 * Instantiates a new jpa basic schedule set memento.
	 *
	 * @param entity the entity
	 */
	public JpaBasicScheduleSetMemento(KscdtBasicSchedule entity) {
		if (entity.getKscdpBSchedulePK() == null) {
			entity.setKscdpBSchedulePK(new KscdtBasicSchedulePK());
		}
		this.entity = entity;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * nts.uk.ctx.at.schedule.dom.schedule.basicschedule.BasicScheduleSetMemento
	 * #setEmployeeId(java.lang.String)
	 */
	@Override
	public void setEmployeeId(String employeeId) {
		this.entity.getKscdpBSchedulePK().setSId(employeeId);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * nts.uk.ctx.at.schedule.dom.schedule.basicschedule.BasicScheduleSetMemento
	 * #setDate(nts.arc.time.GeneralDate)
	 */
	@Override
	public void setDate(GeneralDate date) {
		this.entity.getKscdpBSchedulePK().setDate(date);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * nts.uk.ctx.at.schedule.dom.schedule.basicschedule.BasicScheduleSetMemento
	 * #setWorkTypeCode(java.lang.String)
	 */
	@Override
	public void setWorkTypeCode(String workTypeCode) {
		this.entity.setWorkTypeCode(workTypeCode);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * nts.uk.ctx.at.schedule.dom.schedule.basicschedule.BasicScheduleSetMemento
	 * #setWorkTimeCode(java.lang.String)
	 */
	@Override
	public void setWorkTimeCode(String workTimeCode) {
		this.entity.setWorkTimeCode(workTimeCode);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * nts.uk.ctx.at.schedule.dom.schedule.basicschedule.BasicScheduleSetMemento
	 * #setConfirmedAtr(nts.uk.ctx.at.schedule.dom.schedule.basicschedule.
	 * ConfirmedAtr)
	 */
	@Override
	public void setConfirmedAtr(ConfirmedAtr confirmedAtr) {
		this.entity.setConfirmedAtr(confirmedAtr.value);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * nts.uk.ctx.at.schedule.dom.schedule.basicschedule.BasicScheduleSetMemento
	 * #setWorkScheduleTimeZones(java.util.List)
	 */
	@Override
	public void setWorkScheduleTimeZones(List<WorkScheduleTimeZone> workScheduleTimeZones) {
		//TODO: domain pending
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * nts.uk.ctx.at.schedule.dom.schedule.basicschedule.BasicScheduleSetMemento
	 * #setWorkScheduleBreaks(java.util.List)
	 */
	@Override
	public void setWorkScheduleBreaks(List<WorkScheduleBreak> workScheduleBreaks) {
		//TODO: domain pending
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * nts.uk.ctx.at.schedule.dom.schedule.basicschedule.BasicScheduleSetMemento
	 * #setWorkScheduleTime(java.util.Optional)
	 */
	@Override
	public void setWorkScheduleTime(Optional<WorkScheduleTime> workScheduleTime) {
		//TODO: domain pending
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * nts.uk.ctx.at.schedule.dom.schedule.basicschedule.BasicScheduleSetMemento
	 * #setWorkSchedulePersonFees(java.util.List)
	 */
	@Override
	public void setWorkSchedulePersonFees(List<WorkSchedulePersonFee> workSchedulePersonFees) {
		//TODO: domain pending
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * nts.uk.ctx.at.schedule.dom.schedule.basicschedule.BasicScheduleSetMemento
	 * #setChildCareSchedules(java.util.List)
	 */
	@Override
	public void setChildCareSchedules(List<ChildCareSchedule> childCareSchedules) {
		//TODO: domain pending
	}

}
