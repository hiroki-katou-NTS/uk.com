/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.infra.repository.worktime.predset;

import java.util.List;
import java.util.stream.Collectors;

import nts.uk.ctx.at.shared.dom.worktime.predset.PrescribedTimezoneSettingGetMemento;
import nts.uk.ctx.at.shared.dom.worktime.predset.TimezoneUse;
import nts.uk.ctx.at.shared.infra.entity.worktime.predset.KshmtWtComPredTime;
import nts.uk.shr.com.time.TimeWithDayAttr;

/**
 * The Class JpaPrescribedTimezoneSettingGetMemento.
 */
public class JpaPrescribedTimezoneSettingGetMemento implements PrescribedTimezoneSettingGetMemento {

	/** The entity. */
	private KshmtWtComPredTime entity;

	/**
	 * Instantiates a new jpa prescribed timezone setting get memento.
	 *
	 * @param entity
	 *            the entity
	 * @param lstEntityTime
	 *            the lst entity time
	 */
	public JpaPrescribedTimezoneSettingGetMemento(KshmtWtComPredTime entity) {
		super();
		this.entity = entity;
	}

	/**
	 * Gets the morning end time.
	 *
	 * @return the morning end time
	 */
	@Override
	public TimeWithDayAttr getMorningEndTime() {
		return new TimeWithDayAttr(this.entity.getMorningEndTime());
	}

	/**
	 * Gets the afternoon start time.
	 *
	 * @return the afternoon start time
	 */
	@Override
	public TimeWithDayAttr getAfternoonStartTime() {
		return new TimeWithDayAttr(this.entity.getAfternoonStartTime());
	}

	/**
	 * Gets the lst timezone.
	 *
	 * @return the lst timezone
	 */
	@Override
	public List<TimezoneUse> getLstTimezone() {
		return this.entity.getKshmtWtComPredTss().stream()
				.map(entity -> new TimezoneUse(new JpaTimezoneGetMemento(entity)))
				.collect(Collectors.toList());
	}

}
