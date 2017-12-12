/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.infra.repository.workingcondition;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import nts.gul.collection.CollectionUtil;
import nts.uk.ctx.at.shared.dom.workingcondition.PersonalWorkCategoryGetMemento;
import nts.uk.ctx.at.shared.dom.workingcondition.SingleDaySchedule;
import nts.uk.ctx.at.shared.infra.entity.workingcondition.KshmtPerWorkCat;

/**
 * The Class JpaPersonalWorkCategoryGetMemento.
 */
public class JpaPersonalWorkCategoryGetMemento implements PersonalWorkCategoryGetMemento {
	
	/** The entitys. */
	private List<KshmtPerWorkCat> entitys;

	/** The Constant FIRST_DATA. */
	public static final int FIRST_DATA = 0;

	/**
	 * Instantiates a new jpa personal work category get memento.
	 *
	 * @param entitys the entitys
	 */
	public JpaPersonalWorkCategoryGetMemento(List<KshmtPerWorkCat> entitys) {
		this.entitys = entitys;
	}

	
	/* (non-Javadoc)
	 * @see nts.uk.ctx.at.shared.dom.workingcondition.PersonalWorkCategoryGetMemento#getHolidayWork()
	 */
	@Override
	public SingleDaySchedule getHolidayWork() {
		Optional<KshmtPerWorkCat> optionalHolidayWork = this.findById(this.entitys,
				WorkCategoryAtr.HOLIDAY_WORK.value);
		if (optionalHolidayWork.isPresent()) {
			return this.toDomain(optionalHolidayWork.get());
		}
		return null;
	}

	
	/* (non-Javadoc)
	 * @see nts.uk.ctx.at.shared.dom.workingcondition.PersonalWorkCategoryGetMemento#getHolidayTime()
	 */
	@Override
	public SingleDaySchedule getHolidayTime() {
		Optional<KshmtPerWorkCat> optionalHolidayTime = this.findById(this.entitys,
				WorkCategoryAtr.HOLIDAY_TIME.value);
		if (optionalHolidayTime.isPresent()) {
			return this.toDomain(optionalHolidayTime.get());
		}
		return null;
	}

	
	/* (non-Javadoc)
	 * @see nts.uk.ctx.at.shared.dom.workingcondition.PersonalWorkCategoryGetMemento#getWeekdayTime()
	 */
	@Override
	public SingleDaySchedule getWeekdayTime() {
		Optional<KshmtPerWorkCat> optionalWeekTime = this.findById(this.entitys,
				WorkCategoryAtr.WEEKDAY_TIME.value);
		if (optionalWeekTime.isPresent()) {
			return this.toDomain(optionalWeekTime.get());
		}
		return null;
	}

	
	/* (non-Javadoc)
	 * @see nts.uk.ctx.at.shared.dom.workingcondition.PersonalWorkCategoryGetMemento#getPublicHolidayWork()
	 */
	@Override
	public Optional<SingleDaySchedule> getPublicHolidayWork() {
		Optional<KshmtPerWorkCat> optionalPublicHolidayWork = this.findById(this.entitys,
				WorkCategoryAtr.PUBLIC_HOLIDAY_WORK.value);
		if (optionalPublicHolidayWork.isPresent()) {
			return optionalPublicHolidayWork.map(entity -> this.toDomain(entity));
		}
		return Optional.empty();
	}

	
	/* (non-Javadoc)
	 * @see nts.uk.ctx.at.shared.dom.workingcondition.PersonalWorkCategoryGetMemento#getInLawBreakTime()
	 */
	@Override
	public Optional<SingleDaySchedule> getInLawBreakTime() {
		Optional<KshmtPerWorkCat> optionalInlawBreakTime = this.findById(this.entitys,
				WorkCategoryAtr.INLAW_BREAK_TIME.value);
		if (optionalInlawBreakTime.isPresent()) {
			return optionalInlawBreakTime.map(entity -> this.toDomain(entity));
		}
		return Optional.empty();
	}

	
	/* (non-Javadoc)
	 * @see nts.uk.ctx.at.shared.dom.workingcondition.PersonalWorkCategoryGetMemento#getOutsideLawBreakTime()
	 */
	@Override
	public Optional<SingleDaySchedule> getOutsideLawBreakTime() {
		Optional<KshmtPerWorkCat> optionalOutsideLawBreakTime = this.findById(this.entitys,
				WorkCategoryAtr.OUTSIDE_LAW_BREAK_TIME.value);
		if (optionalOutsideLawBreakTime.isPresent()) {
			return optionalOutsideLawBreakTime.map(entity -> this.toDomain(entity));
		}
		return Optional.empty();
	}

	
	/* (non-Javadoc)
	 * @see nts.uk.ctx.at.shared.dom.workingcondition.PersonalWorkCategoryGetMemento#getHolidayAttendanceTime()
	 */
	@Override
	public Optional<SingleDaySchedule> getHolidayAttendanceTime() {
		Optional<KshmtPerWorkCat> optionalHolidayAttendance = this.findById(this.entitys,
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
	private Optional<KshmtPerWorkCat> findById(List<KshmtPerWorkCat> entitys,
			int workCategoryAtr) {
		List<KshmtPerWorkCat> enityfinders = entitys.stream()
				.filter(workCategory -> workCategory.getKshmtPerWorkCatPK()
						.getPerWorkCatAtr() == workCategoryAtr)
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
	private SingleDaySchedule toDomain( KshmtPerWorkCat entity){
		return new SingleDaySchedule(new JpaSingleDayScheduleWorkCategoryGetMemento(entity));
	}

}
