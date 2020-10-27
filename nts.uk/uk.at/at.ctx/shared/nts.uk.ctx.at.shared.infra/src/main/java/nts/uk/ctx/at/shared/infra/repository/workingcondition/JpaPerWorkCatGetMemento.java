/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.infra.repository.workingcondition;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import nts.uk.ctx.at.shared.dom.workingcondition.PersonalWorkCategoryGetMemento;
import nts.uk.ctx.at.shared.dom.workingcondition.SingleDaySchedule;
import nts.uk.ctx.at.shared.infra.entity.workingcondition.KshmtWorkcondCtg;

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
				entity -> WorkCategoryAtr.valueOf(entity.getKshmtWorkcondCtgPK().getPerWorkCatAtr()),
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
	 * getHolidayTime()
	 */
	@Override
	public SingleDaySchedule getHolidayTime() {
		return this.mapSingleDaySchedule.get(WorkCategoryAtr.HOLIDAY_TIME);
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

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * nts.uk.ctx.at.shared.dom.workingcondition.PersonalWorkCategoryGetMemento#
	 * getPublicHolidayWork()
	 */
	@Override
	public Optional<SingleDaySchedule> getPublicHolidayWork() {
		return Optional
				.ofNullable(this.mapSingleDaySchedule.get(WorkCategoryAtr.PUBLIC_HOLIDAY_WORK));
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * nts.uk.ctx.at.shared.dom.workingcondition.PersonalWorkCategoryGetMemento#
	 * getInLawBreakTime()
	 */
	@Override
	public Optional<SingleDaySchedule> getInLawBreakTime() {
		return Optional.ofNullable(this.mapSingleDaySchedule.get(WorkCategoryAtr.INLAW_BREAK_TIME));
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * nts.uk.ctx.at.shared.dom.workingcondition.PersonalWorkCategoryGetMemento#
	 * getOutsideLawBreakTime()
	 */
	@Override
	public Optional<SingleDaySchedule> getOutsideLawBreakTime() {
		return Optional
				.ofNullable(this.mapSingleDaySchedule.get(WorkCategoryAtr.OUTSIDE_LAW_BREAK_TIME));
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * nts.uk.ctx.at.shared.dom.workingcondition.PersonalWorkCategoryGetMemento#
	 * getHolidayAttendanceTime()
	 */
	@Override
	public Optional<SingleDaySchedule> getHolidayAttendanceTime() {
		return Optional
				.ofNullable(this.mapSingleDaySchedule.get(WorkCategoryAtr.HOLIDAY_ATTENDANCE_TIME));
	}

}
