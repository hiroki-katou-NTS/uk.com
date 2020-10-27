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
import nts.uk.ctx.at.shared.infra.entity.personallaborcondition.KshmtWorkcondCtgegory;
import nts.uk.ctx.at.shared.infra.entity.personallaborcondition.WorkCategoryAtr;

/**
 * The Class JpaPersonalWorkCategoryGetMemento.
 */
public class JpaPersonalWorkCategoryGetMemento implements PersonalWorkCategoryGetMemento {
	
	/** The entitys. */
	private List<KshmtWorkcondCtgegory> entitys;

	/** The Constant FIRST_DATA. */
	public static final int FIRST_DATA = 0;

	/**
	 * Instantiates a new jpa personal work category get memento.
	 *
	 * @param entitys the entitys
	 */
	public JpaPersonalWorkCategoryGetMemento(List<KshmtWorkcondCtgegory> entitys) {
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
		Optional<KshmtWorkcondCtgegory> optionalHolidayWork = this.findById(this.entitys,
				WorkCategoryAtr.HOLIDAY_WORK.value);
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
		Optional<KshmtWorkcondCtgegory> optionalHolidayTime = this.findById(this.entitys,
				WorkCategoryAtr.HOLIDAY_TIME.value);
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
		Optional<KshmtWorkcondCtgegory> optionalWeekTime = this.findById(this.entitys,
				WorkCategoryAtr.WEEKDAY_TIME.value);
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
		Optional<KshmtWorkcondCtgegory> optionalPublicHolidayWork = this.findById(this.entitys,
				WorkCategoryAtr.PUBLIC_HOLIDAY_WORK.value);
		if (optionalPublicHolidayWork.isPresent()) {
			return optionalPublicHolidayWork.map(entity -> this.toDomain(entity));
		}
		return Optional.empty();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see nts.uk.ctx.at.shared.dom.personallaborcondition.
	 * PersonalWorkCategoryGetMemento#getInLawBreakTime()
	 */
	@Override
	public Optional<SingleDaySchedule> getInLawBreakTime() {
		Optional<KshmtWorkcondCtgegory> optionalInlawBreakTime = this.findById(this.entitys,
				WorkCategoryAtr.INLAW_BREAK_TIME.value);
		if (optionalInlawBreakTime.isPresent()) {
			return optionalInlawBreakTime.map(entity -> this.toDomain(entity));
		}
		return Optional.empty();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see nts.uk.ctx.at.shared.dom.personallaborcondition.
	 * PersonalWorkCategoryGetMemento#getOutsideLawBreakTime()
	 */
	@Override
	public Optional<SingleDaySchedule> getOutsideLawBreakTime() {
		Optional<KshmtWorkcondCtgegory> optionalOutsideLawBreakTime = this.findById(this.entitys,
				WorkCategoryAtr.OUTSIDE_LAW_BREAK_TIME.value);
		if (optionalOutsideLawBreakTime.isPresent()) {
			return optionalOutsideLawBreakTime.map(entity -> this.toDomain(entity));
		}
		return Optional.empty();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see nts.uk.ctx.at.shared.dom.personallaborcondition.
	 * PersonalWorkCategoryGetMemento#getHolidayAttendanceTime()
	 */
	@Override
	public Optional<SingleDaySchedule> getHolidayAttendanceTime() {
		Optional<KshmtWorkcondCtgegory> optionalHolidayAttendance = this.findById(this.entitys,
				WorkCategoryAtr.HOLIDAY_ATTENDANCE_TIME.value);
		if (optionalHolidayAttendance.isPresent()) {
			return optionalHolidayAttendance.map(entity -> this.toDomain(entity));
		}
		return Optional.empty();
	}
	
	/**
	 * Find by id.
	 *
	 * @param entitys the entitys
	 * @param workCategoryAtr the work category atr
	 * @return the optional
	 */
	private Optional<KshmtWorkcondCtgegory> findById(List<KshmtWorkcondCtgegory> entitys,
			int workCategoryAtr) {
		List<KshmtWorkcondCtgegory> enityfinders = entitys.stream()
				.filter(workCategory -> workCategory.getKshmtWorkcondCtgegoryPK()
						.getWorkCategoryAtr() == workCategoryAtr)
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
	private SingleDaySchedule toDomain( KshmtWorkcondCtgegory entity){
		return new SingleDaySchedule(new JpaSingleDayScheduleWorkCategoryGetMemento(entity));
	}

}
