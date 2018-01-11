/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.schedule.infra.repository.schedule.basicschedule;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import nts.arc.time.GeneralDate;
import nts.gul.collection.CollectionUtil;
import nts.uk.ctx.at.schedule.dom.schedule.basicschedule.BasicScheduleGetMemento;
import nts.uk.ctx.at.schedule.dom.schedule.basicschedule.ConfirmedAtr;
import nts.uk.ctx.at.schedule.dom.schedule.basicschedule.childcareschedule.ChildCareSchedule;
import nts.uk.ctx.at.schedule.dom.schedule.basicschedule.personalfee.WorkSchedulePersonFee;
import nts.uk.ctx.at.schedule.dom.schedule.basicschedule.workschedulebreak.WorkScheduleBreak;
import nts.uk.ctx.at.schedule.dom.schedule.basicschedule.workscheduletime.WorkScheduleTime;
import nts.uk.ctx.at.schedule.dom.schedule.basicschedule.workscheduletimezone.WorkScheduleTimeZone;
import nts.uk.ctx.at.schedule.infra.entity.schedule.basicschedule.KscdtBasicSchedule;
import nts.uk.ctx.at.schedule.infra.entity.schedule.basicschedule.KscdtBasicSchedulePK;
import nts.uk.ctx.at.schedule.infra.entity.schedule.basicschedule.workscheduletimezone.KscdtWorkScheduleTimeZone;
import nts.uk.ctx.at.schedule.infra.repository.schedule.basicschedule.workscheduletimezone.JpaWorkScheduleTimeZoneGetMemento;

/**
 * The Class JpaBasicScheduleGetMemento.
 */
public class JpaBasicScheduleGetMemento implements BasicScheduleGetMemento{
	
	/** The entity. */
	private KscdtBasicSchedule entity;	
	
	/** The entity time zones. */
	private List<KscdtWorkScheduleTimeZone> entityTimeZones;
	
	/**
	 * Instantiates a new jpa basic schedule get memento.
	 *
	 * @param entity the entity
	 */
	public JpaBasicScheduleGetMemento(KscdtBasicSchedule entity, List<KscdtWorkScheduleTimeZone> entityTimeZones) {
		if (entity.getKscdpBSchedulePK() == null) {
			entity.setKscdpBSchedulePK(new KscdtBasicSchedulePK());
		}
		this.entity = entity;
		this.entityTimeZones = entityTimeZones;
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
	 * @see nts.uk.ctx.at.schedule.dom.schedule.basicschedule.BasicScheduleGetMemento#getWorkScheduleTimeZones()
	 */
	@Override
	public List<WorkScheduleTimeZone> getWorkScheduleTimeZones() {
		if (CollectionUtil.isEmpty(this.entityTimeZones)) {
			return new ArrayList<>();
		}
		return this.entityTimeZones.stream()
				.map(entity -> new WorkScheduleTimeZone(new JpaWorkScheduleTimeZoneGetMemento(entity)))
				.collect(Collectors.toList());
	}

	/* (non-Javadoc)
	 * @see nts.uk.ctx.at.schedule.dom.schedule.basicschedule.BasicScheduleGetMemento#getWorkScheduleBreaks()
	 */
	@Override
	public List<WorkScheduleBreak> getWorkScheduleBreaks() {
		return new ArrayList<>();
	}

	/* (non-Javadoc)
	 * @see nts.uk.ctx.at.schedule.dom.schedule.basicschedule.BasicScheduleGetMemento#getWorkScheduleTime()
	 */
	@Override
	public Optional<WorkScheduleTime> getWorkScheduleTime() {
		return Optional.empty();
	}

	/* (non-Javadoc)
	 * @see nts.uk.ctx.at.schedule.dom.schedule.basicschedule.BasicScheduleGetMemento#getWorkSchedulePersonFees()
	 */
	@Override
	public List<WorkSchedulePersonFee> getWorkSchedulePersonFees() {
		return new ArrayList<>();
	}

	/* (non-Javadoc)
	 * @see nts.uk.ctx.at.schedule.dom.schedule.basicschedule.BasicScheduleGetMemento#getChildCareSchedules()
	 */
	@Override
	public List<ChildCareSchedule> getChildCareSchedules() {
		return new ArrayList<>();
	}

}
