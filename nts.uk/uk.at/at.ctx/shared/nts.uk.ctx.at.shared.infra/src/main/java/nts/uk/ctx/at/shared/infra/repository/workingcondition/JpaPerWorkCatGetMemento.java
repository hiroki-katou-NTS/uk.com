/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.infra.repository.workingcondition;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import nts.uk.ctx.at.shared.dom.workingcondition.PersonalDayOfWeek;
import nts.uk.ctx.at.shared.dom.workingcondition.PersonalWorkCategoryGetMemento;
import nts.uk.ctx.at.shared.dom.workingcondition.SingleDaySchedule;

/**
 * The Class JpaPersonalWorkCategoryGetMemento.
 */
public class JpaPerWorkCatGetMemento implements PersonalWorkCategoryGetMemento {

	/** The map single day schedule. */
	private Map<WorkCategoryAtr, SingleDaySchedule> mapSingleDaySchedule;

	/**
	 * Instantiates a new jpa personal work category get memento.
	 *
	 * @param entities
	 *            the entities
	 */
	public JpaPerWorkCatGetMemento(List<KshmtWorkcondCtg> entities) {
		this.mapSingleDaySchedule = entities.stream().collect(Collectors.toMap(
				entity -> WorkCategoryAtr.valueOf(entity.getKshmtPerWorkCatPK().getPerWorkCatAtr()),
				entity -> new SingleDaySchedule(
						new JpaSDayScheWorkCatGetMemento(entity))));
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * nts.uk.ctx.at.shared.dom.workingcondition.PersonalWorkCategoryGetMemento#
	 * getHolidayWork()
	 */
	@Override
	public SingleDaySchedule getHolidayWork() {
		return this.mapSingleDaySchedule.get(WorkCategoryAtr.HOLIDAY_WORK);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * nts.uk.ctx.at.shared.dom.workingcondition.PersonalWorkCategoryGetMemento#
	 * getWeekdayTime()
	 */
	@Override
	public SingleDaySchedule getWeekdayTime() {
		return this.mapSingleDaySchedule.get(WorkCategoryAtr.WEEKDAY_TIME);
	}

	@Override
	public PersonalDayOfWeek getDayOfWeek() {
		return null;
	}




}
