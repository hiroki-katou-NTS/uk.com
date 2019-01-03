/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.infra.repository.workingcondition;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import nts.gul.collection.CollectionUtil;
import nts.uk.ctx.at.shared.dom.workingcondition.PersonalDayOfWeekGetMemento;
import nts.uk.ctx.at.shared.dom.workingcondition.SingleDaySchedule;
import nts.uk.ctx.at.shared.infra.entity.workingcondition.KshmtPersonalDayOfWeek;

/**
 * The Class JpaPersonalDayOfWeekGetMemento.
 */
public class JpaPerDayOfWeekGetMemento implements PersonalDayOfWeekGetMemento {

	/** The map single day schedule. */
	private Map<DayOfWeekAtr, SingleDaySchedule> mapSingleDaySchedule;

	/**
	 * Instantiates a new jpa personal day of week get memento.
	 *
	 * @param entitys
	 *            the entitys
	 */
	public JpaPerDayOfWeekGetMemento(List<KshmtPersonalDayOfWeek> entities) {
		mapSingleDaySchedule = new HashMap<>();
		if (!CollectionUtil.isEmpty(entities)) {
			this.mapSingleDaySchedule = entities.stream().collect(Collectors.toMap(
					entity -> DayOfWeekAtr
							.valueOf(entity.getKshmtPersonalDayOfWeekPK().getPerWorkDayOffAtr()),
					entity -> new SingleDaySchedule(new JpaSDayScheDayOfGetMemento(entity))));
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * nts.uk.ctx.at.shared.dom.workingcondition.PersonalDayOfWeekGetMemento#
	 * getSaturday()
	 */
	@Override
	public Optional<SingleDaySchedule> getSaturday() {
		return Optional.ofNullable(this.mapSingleDaySchedule.get(DayOfWeekAtr.SATURDAY));
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * nts.uk.ctx.at.shared.dom.workingcondition.PersonalDayOfWeekGetMemento#
	 * getSunday()
	 */
	@Override
	public Optional<SingleDaySchedule> getSunday() {
		return Optional.ofNullable(this.mapSingleDaySchedule.get(DayOfWeekAtr.SUNDAY));
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * nts.uk.ctx.at.shared.dom.workingcondition.PersonalDayOfWeekGetMemento#
	 * getMonday()
	 */
	@Override
	public Optional<SingleDaySchedule> getMonday() {
		return Optional.ofNullable(this.mapSingleDaySchedule.get(DayOfWeekAtr.MONDAY));
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * nts.uk.ctx.at.shared.dom.workingcondition.PersonalDayOfWeekGetMemento#
	 * getThursday()
	 */
	@Override
	public Optional<SingleDaySchedule> getThursday() {
		return Optional.ofNullable(this.mapSingleDaySchedule.get(DayOfWeekAtr.THURSDAY));
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * nts.uk.ctx.at.shared.dom.workingcondition.PersonalDayOfWeekGetMemento#
	 * getWednesday()
	 */
	@Override
	public Optional<SingleDaySchedule> getWednesday() {
		return Optional.ofNullable(this.mapSingleDaySchedule.get(DayOfWeekAtr.WEDNESDAY));
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * nts.uk.ctx.at.shared.dom.workingcondition.PersonalDayOfWeekGetMemento#
	 * getTuesday()
	 */
	@Override
	public Optional<SingleDaySchedule> getTuesday() {
		return Optional.ofNullable(this.mapSingleDaySchedule.get(DayOfWeekAtr.TUESDAY));
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * nts.uk.ctx.at.shared.dom.workingcondition.PersonalDayOfWeekGetMemento#
	 * getFriday()
	 */
	@Override
	public Optional<SingleDaySchedule> getFriday() {
		return Optional.ofNullable(this.mapSingleDaySchedule.get(DayOfWeekAtr.FRIDAY));
	}

}
