/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.infra.repository.workingcondition;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import nts.uk.ctx.at.shared.dom.workingcondition.SingleDayScheduleGetMemento;
import nts.uk.ctx.at.shared.dom.workingcondition.TimeZone;
import nts.uk.ctx.at.shared.dom.worktime.common.WorkTimeCode;
import nts.uk.ctx.at.shared.dom.worktype.WorkTypeCode;
import nts.uk.ctx.at.shared.infra.entity.workingcondition.KshmtPerWorkCat;
import nts.uk.ctx.at.shared.infra.entity.workingcondition.KshmtPerWorkCatPK;

/**
 * The Class JpaSingleDayScheduleGetMemento.
 */
public class JpaSDayScheWorkCatGetMemento implements SingleDayScheduleGetMemento {

	/** The entity. */
	private KshmtPerWorkCat entity;

	/**
	 * Instantiates a new jpa single day schedule work category get memento.
	 *
	 * @param entity
	 *            the entity
	 */
	public JpaSDayScheWorkCatGetMemento(KshmtPerWorkCat entity) {
		if (entity.getKshmtPerWorkCatPK() == null) {
			entity.setKshmtPerWorkCatPK(new KshmtPerWorkCatPK());
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
		return Optional.ofNullable(this.entity.getWorkTypeCode() != null
				? new WorkTypeCode(this.entity.getWorkTypeCode()) : null);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * nts.uk.ctx.at.shared.dom.workingcondition.SingleDayScheduleGetMemento#
	 * getWorkingHours()
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<TimeZone> getWorkingHours() {
		return this.entity.getKshmtWorkCatTimeZones().stream()
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
