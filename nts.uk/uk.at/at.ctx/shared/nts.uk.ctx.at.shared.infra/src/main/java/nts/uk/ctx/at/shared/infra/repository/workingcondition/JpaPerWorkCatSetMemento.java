/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.infra.repository.workingcondition;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

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

	/**
	 * Instantiates a new jpa personal work category set memento.
	 *
	 * @param entities
	 *            the entitys
	 */
	public JpaPerWorkCatSetMemento(String historyId, List<KshmtPerWorkCat> entities) {
		// Check empty
		if (CollectionUtil.isEmpty(entities)) {
			this.entities = new ArrayList<>();
		} else {
			this.entities = entities;
		}

		// Clean all
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
		this.entities.add(this.toEntity(holidayWork, WorkCategoryAtr.HOLIDAY_WORK.value));
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
		this.entities.add(this.toEntity(holidayTime, WorkCategoryAtr.HOLIDAY_TIME.value));
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
		this.entities.add(this.toEntity(weekdayTime, WorkCategoryAtr.WEEKDAY_TIME.value));
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
			this.entities.add(this.toEntity(publicHolidayWork.get(),
					WorkCategoryAtr.PUBLIC_HOLIDAY_WORK.value));
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
			this.entities.add(
					this.toEntity(inLawBreakTime.get(), WorkCategoryAtr.INLAW_BREAK_TIME.value));
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
			this.entities.add(this.toEntity(outsideLawBreakTime.get(),
					WorkCategoryAtr.OUTSIDE_LAW_BREAK_TIME.value));
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
			this.entities.add(this.toEntity(holidayAttendanceTime.get(),
					WorkCategoryAtr.HOLIDAY_ATTENDANCE_TIME.value));
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
	private KshmtPerWorkCat toEntity(SingleDaySchedule domain, int workCategoryAtr) {
		KshmtPerWorkCat entity = new KshmtPerWorkCat();
		domain.saveToMemento(
				new JpaSDayScheWorkCatSetMemento(this.historyId, workCategoryAtr, entity));
		return entity;
	}

}
