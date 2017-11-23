/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.infra.repository.personallaborcondition;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import nts.gul.collection.CollectionUtil;
import nts.uk.ctx.at.shared.dom.personallaborcondition.PersonalDayOfWeekGetMemento;
import nts.uk.ctx.at.shared.dom.personallaborcondition.SingleDaySchedule;
import nts.uk.ctx.at.shared.infra.entity.personallaborcondition.DayOfWeekAtr;
import nts.uk.ctx.at.shared.infra.entity.personallaborcondition.KshmtPerDayOfWeek;

/**
 * The Class JpaPersonalDayOfWeekGetMemento.
 */
public class JpaPersonalDayOfWeekGetMemento implements PersonalDayOfWeekGetMemento {

	/** The entitys. */
	private List<KshmtPerDayOfWeek> entitys;

	/** The Constant FIRST_DATA. */
	public static final int FIRST_DATA = 0;
	
	/**
	 * Instantiates a new jpa personal day of week get memento.
	 *
	 * @param entitys the entitys
	 */
	public JpaPersonalDayOfWeekGetMemento(List<KshmtPerDayOfWeek> entitys) {
		this.entitys = entitys;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see nts.uk.ctx.at.shared.dom.personallaborcondition.
	 * PersonalDayOfWeekGetMemento#getSaturday()
	 */
	@Override
	public Optional<SingleDaySchedule> getSaturday() {
		return this.findById(this.entitys, DayOfWeekAtr.SATURDAY.value)
				.map(entity -> this.toDomain(entity));
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see nts.uk.ctx.at.shared.dom.personallaborcondition.
	 * PersonalDayOfWeekGetMemento#getSunday()
	 */
	@Override
	public Optional<SingleDaySchedule> getSunday() {
		return this.findById(this.entitys, DayOfWeekAtr.SUNDAY.value)
				.map(entity -> this.toDomain(entity));
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see nts.uk.ctx.at.shared.dom.personallaborcondition.
	 * PersonalDayOfWeekGetMemento#getMonday()
	 */
	@Override
	public Optional<SingleDaySchedule> getMonday() {
		return this.findById(this.entitys, DayOfWeekAtr.MONDAY.value)
				.map(entity -> this.toDomain(entity));
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see nts.uk.ctx.at.shared.dom.personallaborcondition.
	 * PersonalDayOfWeekGetMemento#getThursday()
	 */
	@Override
	public Optional<SingleDaySchedule> getThursday() {
		return this.findById(this.entitys, DayOfWeekAtr.THURSDAY.value)
				.map(entity -> this.toDomain(entity));
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see nts.uk.ctx.at.shared.dom.personallaborcondition.
	 * PersonalDayOfWeekGetMemento#getWednesday()
	 */
	@Override
	public Optional<SingleDaySchedule> getWednesday() {
		return this.findById(this.entitys, DayOfWeekAtr.WEDNESDAY.value)
				.map(entity -> this.toDomain(entity));
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see nts.uk.ctx.at.shared.dom.personallaborcondition.
	 * PersonalDayOfWeekGetMemento#getTuesday()
	 */
	@Override
	public Optional<SingleDaySchedule> getTuesday() {
		return this.findById(this.entitys, DayOfWeekAtr.TUESDAY.value)
				.map(entity -> this.toDomain(entity));
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see nts.uk.ctx.at.shared.dom.personallaborcondition.
	 * PersonalDayOfWeekGetMemento#getFriday()
	 */
	@Override
	public Optional<SingleDaySchedule> getFriday() {
		return this.findById(this.entitys, DayOfWeekAtr.FRIDAY.value)
				.map(entity -> this.toDomain(entity));
	}

	/**
	 * Find by id.
	 *
	 * @param entitys the entitys
	 * @param perWorkAtr the per work atr
	 * @return the optional
	 */
	private Optional<KshmtPerDayOfWeek> findById(List<KshmtPerDayOfWeek> entitys, int perWorkAtr) {
		List<KshmtPerDayOfWeek> enityfinders = entitys.stream().filter(
				dayOfWeek -> dayOfWeek.getKshmtPerDayOfWeekPK().getDayOfWeekAtr() == perWorkAtr)
				.collect(Collectors.toList());
		if (CollectionUtil.isEmpty(enityfinders)) {
			return Optional.empty();
		}
		return Optional.ofNullable(enityfinders.get(FIRST_DATA));
	}

	/**
	 * To domain.
	 *
	 * @param entity the entity
	 * @return the single day schedule
	 */
	private SingleDaySchedule toDomain(KshmtPerDayOfWeek entity) {
		return new SingleDaySchedule(new JpaSingleDayScheduleDayOfGetMemento(entity));
	}

}
