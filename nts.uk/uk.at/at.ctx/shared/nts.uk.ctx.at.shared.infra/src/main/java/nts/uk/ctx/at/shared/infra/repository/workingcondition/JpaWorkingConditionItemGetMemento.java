/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.infra.repository.workingcondition;

import java.util.List;
import java.util.Optional;

import nts.uk.ctx.at.shared.dom.common.time.AttendanceTime;
import nts.uk.ctx.at.shared.dom.workingcondition.BonusPaySettingCode;
import nts.uk.ctx.at.shared.dom.workingcondition.BreakdownTimeDay;
import nts.uk.ctx.at.shared.dom.workingcondition.HourlyPaymentAtr;
import nts.uk.ctx.at.shared.dom.workingcondition.LaborContractTime;
import nts.uk.ctx.at.shared.dom.workingcondition.ManageAtr;
import nts.uk.ctx.at.shared.dom.workingcondition.MonthlyPatternCode;
import nts.uk.ctx.at.shared.dom.workingcondition.NotUseAtr;
import nts.uk.ctx.at.shared.dom.workingcondition.PersonalDayOfWeek;
import nts.uk.ctx.at.shared.dom.workingcondition.PersonalWorkCategory;
import nts.uk.ctx.at.shared.dom.workingcondition.ScheduleMethod;
import nts.uk.ctx.at.shared.dom.workingcondition.WorkingConditionItemGetMemento;
import nts.uk.ctx.at.shared.dom.workingcondition.WorkingSystem;
import nts.uk.ctx.at.shared.infra.entity.workingcondition.KshmtPerWorkCat;
import nts.uk.ctx.at.shared.infra.entity.workingcondition.KshmtPersonalDayOfWeek;
import nts.uk.ctx.at.shared.infra.entity.workingcondition.KshmtScheduleMethod;
import nts.uk.ctx.at.shared.infra.entity.workingcondition.KshmtWorkingCondItem;

/**
 * The Class JpaWorkingConditionItemGetMemento.
 */
public class JpaWorkingConditionItemGetMemento implements WorkingConditionItemGetMemento {

	/** The entity. */
	private KshmtWorkingCondItem entity;
	/** The entity. */
	private List<KshmtPerWorkCat> perWorkCat;
	/** The entity. */
	private List<KshmtPersonalDayOfWeek> perDayWeek;
	/** The entity. */
	private KshmtScheduleMethod method;

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
	
	public JpaWorkingConditionItemGetMemento(KshmtWorkingCondItem entity, List<KshmtPerWorkCat> perWorkCat, 
			List<KshmtPersonalDayOfWeek> perDayWeek, KshmtScheduleMethod method) {
		super();
		this.entity = entity;
		this.perWorkCat = perWorkCat;
		this.perDayWeek = perDayWeek;
		this.method = method;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * nts.uk.ctx.at.shared.dom.workingcondition.WorkingConditionItemGetMemento#
	 * getScheduleManagementAtr()
	 */
	@Override
	public ManageAtr getScheduleManagementAtr() {
		return ManageAtr.valueOf(this.entity.getScheManagementAtr());
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
		if(perWorkCat == null){
			perWorkCat = this.entity.getKshmtPerWorkCats();
		}
		
		return new PersonalWorkCategory(new JpaPerWorkCatGetMemento(perWorkCat));
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
		if(perDayWeek == null){
			perDayWeek = this.entity.getKshmtPersonalDayOfWeeks();
		}
		
		return new PersonalDayOfWeek(new JpaPerDayOfWeekGetMemento(perDayWeek));
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
		if(method == null){
			method = this.entity.getKshmtScheduleMethod();
		}
		
		return method == null ? Optional.empty() : 
			Optional.of(new ScheduleMethod(new JpaScheduleMethodGetMemento(method)));
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
						new BreakdownTimeDay(this.entity.getHdAddTimeOneDay() != null? new AttendanceTime(this.entity.getHdAddTimeOneDay()): null,
								this.entity.getHdAddTimeMorning() != null ? new AttendanceTime(this.entity.getHdAddTimeMorning()) : null,
										this.entity.getHdAddTimeAfternoon() != null ? new AttendanceTime(this.entity.getHdAddTimeAfternoon()): null))
				: Optional.empty();
	}

	/* (non-Javadoc)
	 * @see nts.uk.ctx.at.shared.dom.workingcondition.WorkingConditionItemGetMemento#getHourlyPaymentAtr()
	 */
	@Override
	public HourlyPaymentAtr getHourlyPaymentAtr() {
		return HourlyPaymentAtr.valueOf(this.entity.getHourlyPayAtr());
	}

	/* (non-Javadoc)
	 * @see nts.uk.ctx.at.shared.dom.workingcondition.WorkingConditionItemGetMemento#getTimeApply()
	 */
	@Override
	public Optional<BonusPaySettingCode> getTimeApply() {
		return Optional.of(new BonusPaySettingCode(this.entity.getTimeApply()));
	}

	/* (non-Javadoc)
	 * @see nts.uk.ctx.at.shared.dom.workingcondition.WorkingConditionItemGetMemento#getMonthlyPattern()
	 */
	@Override
	public Optional<MonthlyPatternCode> getMonthlyPattern() {
		return Optional.of(new MonthlyPatternCode(this.entity.getMonthlyPattern()));
	}

}
