/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.infra.repository.personallaborcondition;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import nts.gul.collection.CollectionUtil;
import nts.uk.ctx.at.shared.dom.personallaborcondition.PersonalWorkCategoryGetMemento;
import nts.uk.ctx.at.shared.dom.personallaborcondition.SingleDaySchedule;
import nts.uk.ctx.at.shared.infra.entity.personallaborcondition.KshmtSingleDaySche;
import nts.uk.ctx.at.shared.infra.entity.personallaborcondition.PersonalWorkAtr;

/**
 * The Class JpaPersonalWorkCategoryGetMemento.
 */
public class JpaPersonalWorkCategoryGetMemento implements PersonalWorkCategoryGetMemento {
	
	/** The entitys. */
	private List<KshmtSingleDaySche> entitys;

	/** The Constant FIRST_DATA. */
	public static final int FIRST_DATA = 0;

	/**
	 * Instantiates a new jpa personal work category get memento.
	 *
	 * @param entitys
	 *            the entitys
	 */
	public JpaPersonalWorkCategoryGetMemento(List<KshmtSingleDaySche> entitys) {
		this.entitys = entitys;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see nts.uk.ctx.at.shared.dom.personallaborcondition.
	 * PersonalWorkCategoryGetMemento#getHolidayWork()
	 */
	@Override
	public SingleDaySchedule getHolidayWork() {
		Optional<KshmtSingleDaySche> optionalHolidayWork = this.findById(this.entitys,
				PersonalWorkAtr.HOLIDAY_WORK.value);
		if (optionalHolidayWork.isPresent()) {
			return this.toDomain(optionalHolidayWork.get());
		}
		return null;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see nts.uk.ctx.at.shared.dom.personallaborcondition.
	 * PersonalWorkCategoryGetMemento#getHolidayTime()
	 */
	@Override
	public SingleDaySchedule getHolidayTime() {
		Optional<KshmtSingleDaySche> optionalHolidayTime = this.findById(this.entitys,
				PersonalWorkAtr.HOLIDAY_TIME.value);
		if (optionalHolidayTime.isPresent()) {
			return this.toDomain(optionalHolidayTime.get());
		}
		return null;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see nts.uk.ctx.at.shared.dom.personallaborcondition.
	 * PersonalWorkCategoryGetMemento#getWeekdayTime()
	 */
	@Override
	public SingleDaySchedule getWeekdayTime() {
		Optional<KshmtSingleDaySche> optionalWeekTime = this.findById(this.entitys,
				PersonalWorkAtr.WEEKDAY_TIME.value);
		if (optionalWeekTime.isPresent()) {
			return this.toDomain(optionalWeekTime.get());
		}
		return null;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see nts.uk.ctx.at.shared.dom.personallaborcondition.
	 * PersonalWorkCategoryGetMemento#getPublicHolidayWork()
	 */
	@Override
	public Optional<SingleDaySchedule> getPublicHolidayWork() {
		Optional<KshmtSingleDaySche> optionalPublicHolidayWork = this.findById(this.entitys,
				PersonalWorkAtr.PUBLIC_HOLIDAY_WORK.value);
		if (optionalPublicHolidayWork.isPresent()) {
			return optionalPublicHolidayWork.map(entity -> this.toDomain(entity));
		}
		return null;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see nts.uk.ctx.at.shared.dom.personallaborcondition.
	 * PersonalWorkCategoryGetMemento#getInLawBreakTime()
	 */
	@Override
	public Optional<SingleDaySchedule> getInLawBreakTime() {
		Optional<KshmtSingleDaySche> optionalInlawBreakTime = this.findById(this.entitys,
				PersonalWorkAtr.INLAW_BREAK_TIME.value);
		if (optionalInlawBreakTime.isPresent()) {
			return optionalInlawBreakTime.map(entity -> this.toDomain(entity));
		}
		return null;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see nts.uk.ctx.at.shared.dom.personallaborcondition.
	 * PersonalWorkCategoryGetMemento#getOutsideLawBreakTime()
	 */
	@Override
	public Optional<SingleDaySchedule> getOutsideLawBreakTime() {
		Optional<KshmtSingleDaySche> optionalOutsideLawBreakTime = this.findById(this.entitys,
				PersonalWorkAtr.OUTSIDE_LAW_BREAK_TIME.value);
		if (optionalOutsideLawBreakTime.isPresent()) {
			return optionalOutsideLawBreakTime.map(entity -> this.toDomain(entity));
		}
		return null;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see nts.uk.ctx.at.shared.dom.personallaborcondition.
	 * PersonalWorkCategoryGetMemento#getHolidayAttendanceTime()
	 */
	@Override
	public Optional<SingleDaySchedule> getHolidayAttendanceTime() {
		Optional<KshmtSingleDaySche> optionalHolidayAttendance = this.findById(this.entitys,
				PersonalWorkAtr.HOLIDAY_ATTENDANCE_TIME.value);
		if (optionalHolidayAttendance.isPresent()) {
			return optionalHolidayAttendance.map(entity -> this.toDomain(entity));
		}
		return null;
	}
	
	/**
	 * Find by id.
	 *
	 * @param entitys the entitys
	 * @param perWorkAtr the per work atr
	 * @return the optional
	 */
	private Optional<KshmtSingleDaySche> findById(List<KshmtSingleDaySche> entitys,
			int perWorkAtr) {
		List<KshmtSingleDaySche> enityfinders = entitys.stream()
				.filter(singleDaySchedule -> singleDaySchedule.getKshmtSingleDaySchePK()
						.getPersWorkAtr() == perWorkAtr)
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
	private SingleDaySchedule toDomain( KshmtSingleDaySche entity){
		return new SingleDaySchedule(new JpaSingleDayScheduleGetMemento(entity));
	}

}
