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
import nts.uk.ctx.at.shared.infra.entity.personallaborcondition.KshmtSingleDaySche;
import nts.uk.ctx.at.shared.infra.entity.personallaborcondition.PersonalWorkAtr;

/**
 * The Class JpaPersonalWorkCategorySetMemento.
 */
public class JpaPersonalWorkCategorySetMemento implements PersonalWorkCategorySetMemento{
	

	/** The entitys. */
	private List<KshmtSingleDaySche> entitys;
	

	/**
	 * Instantiates a new jpa personal work category set memento.
	 *
	 * @param entitys the entitys
	 */
	public JpaPersonalWorkCategorySetMemento(List<KshmtSingleDaySche> entitys) {
		if (CollectionUtil.isEmpty(entitys)) {
			this.entitys = new ArrayList<>();
		} else
			this.entitys = entitys;
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
		this.entitys.add(this.toEntity(holidayWork, PersonalWorkAtr.HOLIDAY_WORK.value));
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
		this.entitys.add(this.toEntity(holidayTime, PersonalWorkAtr.HOLIDAY_TIME.value));
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
		this.entitys.add(this.toEntity(weekdayTime, PersonalWorkAtr.WEEKDAY_TIME.value));
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
			this.entitys.add(this.toEntity(publicHolidayWork.get(), PersonalWorkAtr.PUBLIC_HOLIDAY_WORK.value));
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
			this.entitys.add(this.toEntity(inLawBreakTime.get(), PersonalWorkAtr.INLAW_BREAK_TIME.value));
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
			this.entitys.add(this.toEntity(outsideLawBreakTime.get(), PersonalWorkAtr.OUTSIDE_LAW_BREAK_TIME.value));
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
			this.entitys.add(this.toEntity(holidayAttendanceTime.get(), PersonalWorkAtr.HOLIDAY_ATTENDANCE_TIME.value));
		}
	}
	
	/**
	 * To entity.
	 *
	 * @param domain the domain
	 * @return the kshmt single day sche
	 */
	private KshmtSingleDaySche toEntity(SingleDaySchedule domain, int personalWorkAtr) {
		KshmtSingleDaySche entity = new KshmtSingleDaySche();
		domain.saveToMemento(new JpaSingleDayScheduleSetMemento(entity));
		entity.getKshmtSingleDaySchePK().setPersWorkAtr(personalWorkAtr);
		return entity;
	}

}
