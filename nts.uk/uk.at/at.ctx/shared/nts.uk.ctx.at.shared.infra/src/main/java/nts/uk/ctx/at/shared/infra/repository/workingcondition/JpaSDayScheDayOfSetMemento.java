/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.infra.repository.workingcondition;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import nts.uk.ctx.at.shared.dom.workingcondition.SingleDayScheduleSetMemento;
import nts.uk.ctx.at.shared.dom.workingcondition.TimeZone;
import nts.uk.ctx.at.shared.dom.worktime.common.WorkTimeCode;
import nts.uk.ctx.at.shared.dom.worktype.WorkTypeCode;
import nts.uk.ctx.at.shared.infra.entity.workingcondition.KshmtDayofweekTimeZone;
import nts.uk.ctx.at.shared.infra.entity.workingcondition.KshmtPersonalDayOfWeek;
import nts.uk.ctx.at.shared.infra.entity.workingcondition.KshmtPersonalDayOfWeekPK;

/**
 * The Class JpaSingleDayScheduleDayOfSetMemento.
 */
public class JpaSDayScheDayOfSetMemento implements SingleDayScheduleSetMemento {

	/** The history id. */
	private String historyId;

	/** The per work day off atr. */
	private int perWorkDayOffAtr;

	/** The entity. */
	private KshmtPersonalDayOfWeek entity;

	/**
	 * Instantiates a new jpa single day schedule day of set memento.
	 *
	 * @param entity
	 *            the entity
	 */
	public JpaSDayScheDayOfSetMemento(String historyId, int perWorkDayOffAtr,
			KshmtPersonalDayOfWeek entity) {
		this.historyId = historyId;
		this.perWorkDayOffAtr = perWorkDayOffAtr;
		if (entity.getKshmtPersonalDayOfWeekPK() == null) {
			entity.setKshmtPersonalDayOfWeekPK(
					new KshmtPersonalDayOfWeekPK(historyId, perWorkDayOffAtr));
		}
		this.entity = entity;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see nts.uk.ctx.at.shared.dom.personallaborcondition.
	 * SingleDayScheduleSetMemento#setWorkTypeCode(nts.uk.ctx.at.shared.dom.
	 * worktype.WorkTypeCode)
	 */
	@Override
	public void setWorkTypeCode(WorkTypeCode workTypeCode) {
		if (workTypeCode != null) {
			this.entity.setWorkTypeCode(workTypeCode.v());
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see nts.uk.ctx.at.shared.dom.personallaborcondition.
	 * SingleDayScheduleSetMemento#setWorkingHours(java.util.List)
	 */
	@Override
	public void setWorkingHours(List<TimeZone> workingHours) {
		List<KshmtDayofweekTimeZone> kshmtDayofweekTimeZones = workingHours.stream().map(item -> {
			KshmtDayofweekTimeZone entity = new KshmtDayofweekTimeZone();
			item.saveToMemento(new JpaTimezoneSetMemento<KshmtDayofweekTimeZone>(historyId,
					this.perWorkDayOffAtr, entity));
			return entity;
		}).collect(Collectors.toList());
		this.entity.setKshmtDayofweekTimeZones(kshmtDayofweekTimeZones);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see nts.uk.ctx.at.shared.dom.personallaborcondition.
	 * SingleDayScheduleSetMemento#setWorkTimeCode(java.util.Optional)
	 */
	@Override
	public void setWorkTimeCode(Optional<WorkTimeCode> workTimeCode) {
		if (workTimeCode.isPresent()) {
			this.entity.setWorkTimeCode(workTimeCode.get().v());
		}
	}

}
