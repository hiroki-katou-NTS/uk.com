/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.infra.repository.workingcondition;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import nts.gul.collection.CollectionUtil;
import nts.uk.ctx.at.shared.dom.workingcondition.SingleDayScheduleGetMemento;
import nts.uk.ctx.at.shared.dom.workingcondition.TimeZone;
import nts.uk.ctx.at.shared.dom.worktime.common.WorkTimeCode;
import nts.uk.ctx.at.shared.dom.worktype.WorkTypeCode;
import nts.uk.ctx.at.shared.infra.entity.workingcondition.KshmtWorkcondWeek;
import nts.uk.ctx.at.shared.infra.entity.workingcondition.KshmtWorkcondWeekPK;

/**
 * The Class JpaSingleDayScheduleGetMemento.
 */
public class JpaSDayScheDayOfGetMemento implements SingleDayScheduleGetMemento {

	/** The entity. */
	private KshmtWorkcondWeek entity;

	/**
	 * Instantiates a new jpa single day schedule get memento.
	 *
	 * @param entity
	 *            the entity
	 */
	public JpaSDayScheDayOfGetMemento(KshmtWorkcondWeek entity) {
		if (entity.getKshmtWorkcondWeekPK() == null) {
			entity.setKshmtWorkcondWeekPK(new KshmtWorkcondWeekPK());
		}
		this.entity = entity;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * nts.uk.ctx.at.shared.dom.workingcondition.SingleDayScheduleGetMemento#
	 * getWorkTypeCode()
	 */
	@Override
	public Optional<WorkTypeCode> getWorkTypeCode() {
		return Optional.ofNullable(this.entity.getWorkTypeCode() == null ? null
				: new WorkTypeCode(this.entity.getWorkTypeCode()));
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * nts.uk.ctx.at.shared.dom.workingcondition.SingleDayScheduleGetMemento#
	 * getWorkingHours()
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Override
	public List<TimeZone> getWorkingHours() {
		if(CollectionUtil.isEmpty(this.entity.getKshmtWorkcondWeekTss())) {
			return Collections.emptyList();
		}
		return this.entity.getKshmtWorkcondWeekTss().stream()
				.map(entity -> new TimeZone(new JpaTimezoneGetMemento(entity)))
				.collect(Collectors.toList());
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * nts.uk.ctx.at.shared.dom.workingcondition.SingleDayScheduleGetMemento#
	 * getWorkTimeCode()
	 */
	@Override
	public Optional<WorkTimeCode> getWorkTimeCode() {
		return Optional.ofNullable(this.entity.getWorkTimeCode() != null
				? new WorkTimeCode(this.entity.getWorkTimeCode()) : null);
	}

}
