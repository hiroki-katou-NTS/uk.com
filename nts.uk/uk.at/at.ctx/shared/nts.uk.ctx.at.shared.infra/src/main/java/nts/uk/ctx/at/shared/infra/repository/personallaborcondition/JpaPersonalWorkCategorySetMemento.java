/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.infra.repository.personallaborcondition;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import nts.gul.collection.CollectionUtil;
import nts.uk.ctx.at.shared.dom.personallaborcondition.PersonalWorkCategorySetMemento;
import nts.uk.ctx.at.shared.dom.personallaborcondition.SingleDaySchedule;
import nts.uk.ctx.at.shared.infra.entity.personallaborcondition.KshmtWorkcondCtgegory;
import nts.uk.ctx.at.shared.infra.entity.personallaborcondition.WorkCategoryAtr;

/**
 * The Class JpaPersonalWorkCategorySetMemento.
 */
public class JpaPersonalWorkCategorySetMemento implements PersonalWorkCategorySetMemento{
	

	/** The entitys. */
	private List<KshmtWorkcondCtgegory> entitys;
	

	/**
	 * Instantiates a new jpa personal work category set memento.
	 *
	 * @param entitys the entitys
	 */
	public JpaPersonalWorkCategorySetMemento(List<KshmtWorkcondCtgegory> entitys) {
		if (CollectionUtil.isEmpty(entitys)) {
			this.entitys = new ArrayList<>();
		} else {
			this.entitys = entitys;
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see nts.uk.ctx.at.shared.dom.personallaborcondition.
	 * PersonalWorkCategorySetMemento#setHolidayWork(nts.uk.ctx.at.shared.dom.
	 * personallaborcondition.SingleDaySchedule)
	 */
	@Override
	public void setHolidayWork(SingleDaySchedule holidayWork) {
		this.entitys.add(this.toEntity(holidayWork, WorkCategoryAtr.HOLIDAY_WORK.value));
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see nts.uk.ctx.at.shared.dom.personallaborcondition.
	 * PersonalWorkCategorySetMemento#setHolidayTime(nts.uk.ctx.at.shared.dom.
	 * personallaborcondition.SingleDaySchedule)
	 */
	@Override
	public void setHolidayTime(SingleDaySchedule holidayTime) {
		this.entitys.add(this.toEntity(holidayTime, WorkCategoryAtr.HOLIDAY_TIME.value));
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see nts.uk.ctx.at.shared.dom.personallaborcondition.
	 * PersonalWorkCategorySetMemento#setWeekdayTime(nts.uk.ctx.at.shared.dom.
	 * personallaborcondition.SingleDaySchedule)
	 */
	@Override
	public void setWeekdayTime(SingleDaySchedule weekdayTime) {
		this.entitys.add(this.toEntity(weekdayTime, WorkCategoryAtr.WEEKDAY_TIME.value));
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see nts.uk.ctx.at.shared.dom.personallaborcondition.
	 * PersonalWorkCategorySetMemento#setPublicHolidayWork(java.util.Optional)
	 */
	@Override
	public void setPublicHolidayWork(Optional<SingleDaySchedule> publicHolidayWork) {
		if (publicHolidayWork.isPresent()) {
			this.entitys.add(this.toEntity(publicHolidayWork.get(), WorkCategoryAtr.PUBLIC_HOLIDAY_WORK.value));
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see nts.uk.ctx.at.shared.dom.personallaborcondition.
	 * PersonalWorkCategorySetMemento#setInLawBreakTime(java.util.Optional)
	 */
	@Override
	public void setInLawBreakTime(Optional<SingleDaySchedule> inLawBreakTime) {
		if (inLawBreakTime.isPresent()) {
			this.entitys.add(this.toEntity(inLawBreakTime.get(), WorkCategoryAtr.INLAW_BREAK_TIME.value));
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see nts.uk.ctx.at.shared.dom.personallaborcondition.
	 * PersonalWorkCategorySetMemento#setOutsideLawBreakTime(java.util.Optional)
	 */
	@Override
	public void setOutsideLawBreakTime(Optional<SingleDaySchedule> outsideLawBreakTime) {
		if (outsideLawBreakTime.isPresent()) {
			this.entitys.add(this.toEntity(outsideLawBreakTime.get(), WorkCategoryAtr.OUTSIDE_LAW_BREAK_TIME.value));
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see nts.uk.ctx.at.shared.dom.personallaborcondition.
	 * PersonalWorkCategorySetMemento#setHolidayAttendanceTime(java.util.
	 * Optional)
	 */
	@Override
	public void setHolidayAttendanceTime(Optional<SingleDaySchedule> holidayAttendanceTime) {
		if (holidayAttendanceTime.isPresent()) {
			this.entitys.add(this.toEntity(holidayAttendanceTime.get(), WorkCategoryAtr.HOLIDAY_ATTENDANCE_TIME.value));
		}
	}
	
	/**
	 * To entity.
	 *
	 * @param domain the domain
	 * @param workCategoryAtr the work category atr
	 * @return the kshmt per work category
	 */
	private KshmtWorkcondCtgegory toEntity(SingleDaySchedule domain, int workCategoryAtr) {
		KshmtWorkcondCtgegory entity = new KshmtWorkcondCtgegory();
		domain.saveToMemento(new JpaSingleDayScheduleWorkCategorySetMemento(entity));
		entity.getKshmtWorkcondCtgegoryPK().setWorkCategoryAtr(workCategoryAtr);
		return entity;
	}

}
