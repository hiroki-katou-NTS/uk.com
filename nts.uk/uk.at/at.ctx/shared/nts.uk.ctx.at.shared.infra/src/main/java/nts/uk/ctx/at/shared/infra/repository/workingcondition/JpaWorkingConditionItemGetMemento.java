/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.infra.repository.workingcondition;

import java.util.Optional;

import nts.uk.ctx.at.shared.dom.workingcondition.AttendanceTime;
import nts.uk.ctx.at.shared.dom.workingcondition.BreakdownTimeDay;
import nts.uk.ctx.at.shared.dom.workingcondition.LaborContractTime;
import nts.uk.ctx.at.shared.dom.workingcondition.NotUseAtr;
import nts.uk.ctx.at.shared.dom.workingcondition.PersonalDayOfWeek;
import nts.uk.ctx.at.shared.dom.workingcondition.PersonalWorkCategory;
import nts.uk.ctx.at.shared.dom.workingcondition.ScheduleMethod;
import nts.uk.ctx.at.shared.dom.workingcondition.WorkingConditionItemGetMemento;
import nts.uk.ctx.at.shared.dom.workingcondition.WorkingSystem;
import nts.uk.ctx.at.shared.infra.entity.workingcondition.KshmtScheduleMethod;
import nts.uk.ctx.at.shared.infra.entity.workingcondition.KshmtWorkingCondItem;

/**
 * The Class JpaWorkingConditionItemGetMemento.
 */
public class JpaWorkingConditionItemGetMemento implements WorkingConditionItemGetMemento {

	/** The entity. */
	private KshmtWorkingCondItem entity;

	/**
	 * Instantiates a new jpa working condition item get memento.
	 *
	 * @param entity
	 *            the entity
	 */
	public JpaWorkingConditionItemGetMemento(KshmtWorkingCondItem entity) {
		super();
		this.entity = entity;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * nts.uk.ctx.at.shared.dom.workingcondition.WorkingConditionItemGetMemento#
	 * getScheduleManagementAtr()
	 */
	@Override
	public NotUseAtr getScheduleManagementAtr() {
		return NotUseAtr.valueOf(this.entity.getScheManagementAtr());
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * nts.uk.ctx.at.shared.dom.workingcondition.WorkingConditionItemGetMemento#
	 * getVacationAddedTimeAtr()
	 */
	@Override
	public NotUseAtr getVacationAddedTimeAtr() {
		return NotUseAtr.valueOf(this.entity.getVacationAddTimeAtr());
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * nts.uk.ctx.at.shared.dom.workingcondition.WorkingConditionItemGetMemento#
	 * getLaborSystem()
	 */
	@Override
	public WorkingSystem getLaborSystem() {
		return WorkingSystem.valueOf(this.entity.getLaborSys());
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * nts.uk.ctx.at.shared.dom.workingcondition.WorkingConditionItemGetMemento#
	 * getWorkCategory()
	 */
	@Override
	public PersonalWorkCategory getWorkCategory() {
		return new PersonalWorkCategory(
				new JpaPerWorkCatGetMemento(this.entity.getKshmtPerWorkCats()));
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * nts.uk.ctx.at.shared.dom.workingcondition.WorkingConditionItemGetMemento#
	 * getContractTime()
	 */
	@Override
	public LaborContractTime getContractTime() {
		return new LaborContractTime(this.entity.getContractTime());
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * nts.uk.ctx.at.shared.dom.workingcondition.WorkingConditionItemGetMemento#
	 * getAutoIntervalSetAtr()
	 */
	@Override
	public NotUseAtr getAutoIntervalSetAtr() {
		return NotUseAtr.valueOf(this.entity.getAutoIntervalSetAtr());
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * nts.uk.ctx.at.shared.dom.workingcondition.WorkingConditionItemGetMemento#
	 * getHistoryId()
	 */
	@Override
	public String getHistoryId() {
		return this.entity.getHistoryId();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * nts.uk.ctx.at.shared.dom.workingcondition.WorkingConditionItemGetMemento#
	 * getWorkDayOfWeek()
	 */
	@Override
	public PersonalDayOfWeek getWorkDayOfWeek() {
		return new PersonalDayOfWeek(
				new JpaPerDayOfWeekGetMemento(this.entity.getKshmtPersonalDayOfWeeks()));
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * nts.uk.ctx.at.shared.dom.workingcondition.WorkingConditionItemGetMemento#
	 * getEmployeeId()
	 */
	@Override
	public String getEmployeeId() {
		return this.entity.getSid();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * nts.uk.ctx.at.shared.dom.workingcondition.WorkingConditionItemGetMemento#
	 * getAutoStampSetAtr()
	 */
	@Override
	public NotUseAtr getAutoStampSetAtr() {
		return NotUseAtr.valueOf(this.entity.getAutoStampSetAtr());
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * nts.uk.ctx.at.shared.dom.workingcondition.WorkingConditionItemGetMemento#
	 * getScheduleMethod()
	 */
	@Override
	public Optional<ScheduleMethod> getScheduleMethod() {
		KshmtScheduleMethod method = this.entity.getKshmtScheduleMethod();
		return method != null
				? Optional.of(new ScheduleMethod(new JpaScheduleMethodGetMemento(method)))
				: Optional.empty();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * nts.uk.ctx.at.shared.dom.workingcondition.WorkingConditionItemGetMemento#
	 * getHolidayAddTimeSet()
	 */
	@Override
	public Optional<BreakdownTimeDay> getHolidayAddTimeSet() {
		Integer hdAddTimeOneDay = this.entity.getHdAddTimeOneDay();
		return hdAddTimeOneDay != null
				? Optional.of(
						new BreakdownTimeDay(new AttendanceTime(this.entity.getHdAddTimeOneDay()),
								new AttendanceTime(this.entity.getHdAddTimeMorning()),
								new AttendanceTime(this.entity.getHdAddTimeAfternoon())))
				: Optional.empty();
	}

}
