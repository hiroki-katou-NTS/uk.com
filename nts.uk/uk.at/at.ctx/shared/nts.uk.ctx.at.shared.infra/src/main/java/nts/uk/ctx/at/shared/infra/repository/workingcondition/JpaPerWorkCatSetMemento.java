/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.infra.repository.workingcondition;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.function.Function;
import java.util.stream.Collectors;

import nts.gul.collection.CollectionUtil;
import nts.uk.ctx.at.shared.dom.workingcondition.PersonalWorkCategorySetMemento;
import nts.uk.ctx.at.shared.dom.workingcondition.SingleDaySchedule;
import nts.uk.ctx.at.shared.infra.entity.workingcondition.KshmtPerWorkCat;


/**
 * The Class JpaPersonalWorkCategorySetMemento.
 */
public class JpaPerWorkCatSetMemento implements PersonalWorkCategorySetMemento {

	/** The entities. */
	private List<KshmtPerWorkCat> entities;

	/** The history id. */
	private String historyId;

	/** The map single day schedule. */
	private Map<Integer, KshmtPerWorkCat> mapSingleDaySchedule;

	/**
	 * Instantiates a new jpa personal work category set memento.
	 *
	 * @param entities
	 *            the entitys
	 */
	public JpaPerWorkCatSetMemento(String historyId, List<KshmtPerWorkCat> entities) {
		this.mapSingleDaySchedule = new HashMap<>();
		if (!CollectionUtil.isEmpty(entities)) {
			this.mapSingleDaySchedule = entities.stream().collect(Collectors.toMap(
					entity -> entity.getKshmtPerWorkCatPK().getPerWorkCatAtr(), Function.identity()));
		}

		this.entities = entities;
		this.entities.clear();

		this.historyId = historyId;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * nts.uk.ctx.at.shared.dom.workingcondition.PersonalWorkCategorySetMemento#
	 * setHolidayWork(nts.uk.ctx.at.shared.dom.workingcondition.
	 * SingleDaySchedule)
	 */
	@Override
	public void setHolidayWork(SingleDaySchedule holidayWork) {
		this.toEntity(holidayWork, WorkCategoryAtr.HOLIDAY_WORK.value);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * nts.uk.ctx.at.shared.dom.workingcondition.PersonalWorkCategorySetMemento#
	 * setHolidayTime(nts.uk.ctx.at.shared.dom.workingcondition.
	 * SingleDaySchedule)
	 */
	@Override
	public void setHolidayTime(SingleDaySchedule holidayTime) {
		this.toEntity(holidayTime, WorkCategoryAtr.HOLIDAY_TIME.value);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * nts.uk.ctx.at.shared.dom.workingcondition.PersonalWorkCategorySetMemento#
	 * setWeekdayTime(nts.uk.ctx.at.shared.dom.workingcondition.
	 * SingleDaySchedule)
	 */
	@Override
	public void setWeekdayTime(SingleDaySchedule weekdayTime) {
		this.toEntity(weekdayTime, WorkCategoryAtr.WEEKDAY_TIME.value);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * nts.uk.ctx.at.shared.dom.workingcondition.PersonalWorkCategorySetMemento#
	 * setPublicHolidayWork(java.util.Optional)
	 */
	@Override
	public void setPublicHolidayWork(Optional<SingleDaySchedule> publicHolidayWork) {
		if (publicHolidayWork.isPresent()) {
			this.toEntity(publicHolidayWork.get(),
					WorkCategoryAtr.PUBLIC_HOLIDAY_WORK.value);
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * nts.uk.ctx.at.shared.dom.workingcondition.PersonalWorkCategorySetMemento#
	 * setInLawBreakTime(java.util.Optional)
	 */
	@Override
	public void setInLawBreakTime(Optional<SingleDaySchedule> inLawBreakTime) {
		if (inLawBreakTime.isPresent()) {
			this.toEntity(inLawBreakTime.get(), WorkCategoryAtr.INLAW_BREAK_TIME.value);
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * nts.uk.ctx.at.shared.dom.workingcondition.PersonalWorkCategorySetMemento#
	 * setOutsideLawBreakTime(java.util.Optional)
	 */
	@Override
	public void setOutsideLawBreakTime(Optional<SingleDaySchedule> outsideLawBreakTime) {
		if (outsideLawBreakTime.isPresent()) {
			this.toEntity(outsideLawBreakTime.get(),
					WorkCategoryAtr.OUTSIDE_LAW_BREAK_TIME.value);
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * nts.uk.ctx.at.shared.dom.workingcondition.PersonalWorkCategorySetMemento#
	 * setHolidayAttendanceTime(java.util.Optional)
	 */
	@Override
	public void setHolidayAttendanceTime(Optional<SingleDaySchedule> holidayAttendanceTime) {
		if (holidayAttendanceTime.isPresent()) {
			this.toEntity(holidayAttendanceTime.get(),
					WorkCategoryAtr.HOLIDAY_ATTENDANCE_TIME.value);
		}
	}

	/**
	 * To entity.
	 *
	 * @param domain
	 *            the domain
	 * @param workCategoryAtr
	 *            the work category atr
	 * @return the kshmt per work category
	 */
	private void toEntity(SingleDaySchedule domain, int workCategoryAtr) {
		// Create primary key
		KshmtPerWorkCat entity = this.mapSingleDaySchedule.getOrDefault(Integer.valueOf(workCategoryAtr), new KshmtPerWorkCat());
		domain.saveToMemento(new JpaSDayScheWorkCatSetMemento(this.historyId, workCategoryAtr, entity));

		// Put new/updated entity into map
		this.mapSingleDaySchedule.put(workCategoryAtr, entity);

		// Put back to the entities list
		this.entities.clear();
		this.entities.addAll(this.mapSingleDaySchedule.values());
	}
	

}
