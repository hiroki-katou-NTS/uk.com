/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.infra.repository.workingcondition;

import java.util.List;
import java.util.Optional;

import nts.uk.ctx.at.shared.dom.workingcondition.PersonalDayOfWeekSetMemento;
import nts.uk.ctx.at.shared.dom.workingcondition.SingleDaySchedule;
import nts.uk.ctx.at.shared.infra.entity.workingcondition.KshmtPersonalDayOfWeek;

/**
 * The Class JpaPersonalDayOfWeekSetMemento.
 */
public class JpaPerDayOfWeekSetMemento implements PersonalDayOfWeekSetMemento {

	/** The history id. */
	private String historyId;

	/** The entities. */
	private List<KshmtPersonalDayOfWeek> entities;

	/**
	 * Instantiates a new jpa personal day of week set memento.
	 *
	 * @param entities
	 *            the entities
	 */
	public JpaPerDayOfWeekSetMemento(String historyId, List<KshmtPersonalDayOfWeek> entities) {
		this.historyId = historyId;
		// Check empty
//		if (CollectionUtil.isEmpty(entities)) {
//			this.entities = new ArrayList<>();
//		} else {
//			this.entities = entities;
//		}
		this.entities = entities;
		
		// Clear item
		this.entities.clear();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see nts.uk.ctx.at.shared.dom.personallaborcondition.
	 * PersonalDayOfWeekSetMemento#setSaturday(java.util.Optional)
	 */
	@Override
	public void setSaturday(Optional<SingleDaySchedule> saturday) {
		if (saturday.isPresent()) {
			this.entities.add(
					this.toEntity(saturday.get(), this.historyId, DayOfWeekAtr.SATURDAY.value));
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see nts.uk.ctx.at.shared.dom.personallaborcondition.
	 * PersonalDayOfWeekSetMemento#setSunday(java.util.Optional)
	 */
	@Override
	public void setSunday(Optional<SingleDaySchedule> sunday) {
		if (sunday.isPresent()) {
			this.entities
					.add(this.toEntity(sunday.get(), this.historyId, DayOfWeekAtr.SUNDAY.value));
		}

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see nts.uk.ctx.at.shared.dom.personallaborcondition.
	 * PersonalDayOfWeekSetMemento#setMonday(java.util.Optional)
	 */
	@Override
	public void setMonday(Optional<SingleDaySchedule> monday) {
		if (monday.isPresent()) {
			this.entities
					.add(this.toEntity(monday.get(), this.historyId, DayOfWeekAtr.MONDAY.value));
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see nts.uk.ctx.at.shared.dom.personallaborcondition.
	 * PersonalDayOfWeekSetMemento#setThursday(java.util.Optional)
	 */
	@Override
	public void setThursday(Optional<SingleDaySchedule> thursday) {
		if (thursday.isPresent()) {
			this.entities.add(
					this.toEntity(thursday.get(), this.historyId, DayOfWeekAtr.THURSDAY.value));
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see nts.uk.ctx.at.shared.dom.personallaborcondition.
	 * PersonalDayOfWeekSetMemento#setWednesday(java.util.Optional)
	 */
	@Override
	public void setWednesday(Optional<SingleDaySchedule> wednesday) {
		if (wednesday.isPresent()) {
			this.entities.add(
					this.toEntity(wednesday.get(), this.historyId, DayOfWeekAtr.WEDNESDAY.value));
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see nts.uk.ctx.at.shared.dom.personallaborcondition.
	 * PersonalDayOfWeekSetMemento#setTuesday(java.util.Optional)
	 */
	@Override
	public void setTuesday(Optional<SingleDaySchedule> tuesday) {
		if (tuesday.isPresent()) {
			this.entities
					.add(this.toEntity(tuesday.get(), this.historyId, DayOfWeekAtr.TUESDAY.value));
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see nts.uk.ctx.at.shared.dom.personallaborcondition.
	 * PersonalDayOfWeekSetMemento#setFriday(java.util.Optional)
	 */
	@Override
	public void setFriday(Optional<SingleDaySchedule> friday) {
		if (friday.isPresent()) {
			this.entities
					.add(this.toEntity(friday.get(), this.historyId, DayOfWeekAtr.FRIDAY.value));
		}
	}

	/**
	 * To entity.
	 *
	 * @param domain
	 *            the domain
	 * @param dayOfWeekAtr
	 *            the day of week atr
	 * @return the kshmt per day of week
	 */
	private KshmtPersonalDayOfWeek toEntity(SingleDaySchedule domain, String historyId,
			int dayOfWeekAtr) {
		KshmtPersonalDayOfWeek entity = new KshmtPersonalDayOfWeek();
		domain.saveToMemento(new JpaSDayScheDayOfSetMemento(historyId, dayOfWeekAtr, entity));
		return entity;
	}

}
