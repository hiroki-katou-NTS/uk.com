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
import nts.uk.ctx.at.shared.infra.entity.workingcondition.KshmtWorkcondCtg;


/**
 * The Class JpaPersonalWorkCategorySetMemento.
 */
public class JpaPerWorkCatSetMemento implements PersonalWorkCategorySetMemento {

	/** The entities. */
	private List<KshmtWorkcondCtg> entities;
	
	/** The sid. */
	private String sid;

	/** The history id. */
	private String historyId;

	/** The map single day schedule. */
	private Map<Integer, KshmtWorkcondCtg> mapSingleDaySchedule;

	/**
	 * Instantiates a new jpa personal work category set memento.
	 *
	 * @param entities
	 *            the entitys
	 */
	public JpaPerWorkCatSetMemento(String historyId, List<KshmtWorkcondCtg> entities, String sid) {
		this.mapSingleDaySchedule = new HashMap<>();
		if (!CollectionUtil.isEmpty(entities)) {
			this.mapSingleDaySchedule = entities.stream().collect(Collectors.toMap(
					entity -> entity.getKshmtWorkcondCtgPK().getPerWorkCatAtr(), Function.identity()));
		}
		this.entities = entities;
		this.entities.clear();
		this.historyId = historyId;
		this.sid = sid;
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
		this.toEntity(Optional.of(holidayWork), WorkCategoryAtr.HOLIDAY_WORK.value);
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
		this.toEntity(Optional.of(holidayTime), WorkCategoryAtr.HOLIDAY_TIME.value);
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
		this.toEntity(Optional.of(weekdayTime), WorkCategoryAtr.WEEKDAY_TIME.value);
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
		this.toEntity(publicHolidayWork, WorkCategoryAtr.PUBLIC_HOLIDAY_WORK.value);
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
		this.toEntity(inLawBreakTime, WorkCategoryAtr.INLAW_BREAK_TIME.value);
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
		this.toEntity(outsideLawBreakTime, WorkCategoryAtr.OUTSIDE_LAW_BREAK_TIME.value);
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
		this.toEntity(holidayAttendanceTime, WorkCategoryAtr.HOLIDAY_ATTENDANCE_TIME.value);
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
	private void toEntity(Optional<SingleDaySchedule> domain, int workCategoryAtr) {

		if (!domain.isPresent()) {
			this.mapSingleDaySchedule.remove(workCategoryAtr);
			return;
		}
		
		// Create primary key
		KshmtWorkcondCtg entity = this.mapSingleDaySchedule
				.getOrDefault(Integer.valueOf(workCategoryAtr), new KshmtWorkcondCtg());
		entity.setSid(this.sid);
		domain.get().saveToMemento(
				new JpaSDayScheWorkCatSetMemento(this.historyId, workCategoryAtr, entity));
		if (!CollectionUtil.isEmpty(entity.getKshmtWorkcondCtgTss())) {
			entity.getKshmtWorkcondCtgTss().stream().forEach(catTimeZone -> {
				catTimeZone.setSid(this.sid);
			});
		}
		
		// Put new/updated entity into map
		this.mapSingleDaySchedule.put(workCategoryAtr, entity);

		// Put back to the entities list
		this.entities.add(this.mapSingleDaySchedule.get(workCategoryAtr));

	}
	

}
