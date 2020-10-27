/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.infra.repository.workingcondition;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import nts.gul.collection.CollectionUtil;
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
import nts.uk.ctx.at.shared.dom.workingcondition.WorkingConditionItemSetMemento;
import nts.uk.ctx.at.shared.dom.workingcondition.WorkingSystem;
import nts.uk.ctx.at.shared.infra.entity.workingcondition.KshmtWorkcondCtg;
import nts.uk.ctx.at.shared.infra.entity.workingcondition.KshmtWorkcondWeek;
import nts.uk.ctx.at.shared.infra.entity.workingcondition.KshmtWorkcondScheMeth;
import nts.uk.ctx.at.shared.infra.entity.workingcondition.KshmtWorkcondHistItem;

/**
 * The Class JpaWorkingConditionItemSetMemento.
 */
public class JpaWorkingConditionItemSetMemento implements WorkingConditionItemSetMemento {

	/** The entity. */
	private KshmtWorkcondHistItem entity;

	/**
	 * Instantiates a new jpa working condition item set memento.
	 *
	 * @param entity
	 *            the entity
	 */
	public JpaWorkingConditionItemSetMemento(KshmtWorkcondHistItem entity) {
		this.entity = entity;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * nts.uk.ctx.at.shared.dom.workingcondition.WorkingConditionItemSetMemento#
	 * setHistoryId(java.lang.String)
	 */
	@Override
	public void setHistoryId(String historyId) {
		this.entity.setHistoryId(historyId);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * nts.uk.ctx.at.shared.dom.workingcondition.WorkingConditionItemSetMemento#
	 * setScheduleManagementAtr(nts.uk.ctx.at.shared.dom.workingcondition.
	 * NotUseAtr)
	 */
	@Override
	public void setScheduleManagementAtr(ManageAtr scheduleManagementAtr) {
		if (scheduleManagementAtr != null) {
			this.entity.setScheManagementAtr(scheduleManagementAtr.value);
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * nts.uk.ctx.at.shared.dom.workingcondition.WorkingConditionItemSetMemento#
	 * setVacationAddedTimeAtr(nts.uk.ctx.at.shared.dom.workingcondition.
	 * NotUseAtr)
	 */
	@Override
	public void setVacationAddedTimeAtr(NotUseAtr vacationAddedTimeAtr) {
		if(vacationAddedTimeAtr != null) {
			this.entity.setVacationAddTimeAtr(vacationAddedTimeAtr.value);
		}
		
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * nts.uk.ctx.at.shared.dom.workingcondition.WorkingConditionItemSetMemento#
	 * setLaborSystem(nts.uk.ctx.at.shared.dom.workingcondition.WorkingSystem)
	 */
	@Override
	public void setLaborSystem(WorkingSystem laborSystem) {
		this.entity.setLaborSys(laborSystem.value);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * nts.uk.ctx.at.shared.dom.workingcondition.WorkingConditionItemSetMemento#
	 * setWorkCategory(nts.uk.ctx.at.shared.dom.workingcondition.
	 * PersonalWorkCategory)
	 */
	@Override
	public void setWorkCategory(PersonalWorkCategory workCategory, String employeeId) {
		if (workCategory != null) {
			List<KshmtWorkcondCtg> kshmtWorkcondCtgs = new ArrayList<>();
			if (this.entity.getKshmtWorkcondCtgs() != null) {
				kshmtWorkcondCtgs = this.entity.getKshmtWorkcondCtgs();
			}
			workCategory.saveToMemento(new JpaPerWorkCatSetMemento(this.entity.getHistoryId(), kshmtWorkcondCtgs, employeeId));
			kshmtWorkcondCtgs.stream().forEach(c -> {
				c.setSid(employeeId);
				if (!CollectionUtil.isEmpty(c.getKshmtWorkcondCtgTss())) {
					c.getKshmtWorkcondCtgTss().stream().forEach(catTimeZone -> {
						catTimeZone.setSid(employeeId);
					});
				}
			});
			this.entity.setKshmtWorkcondCtgs(kshmtWorkcondCtgs);
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * nts.uk.ctx.at.shared.dom.workingcondition.WorkingConditionItemSetMemento#
	 * setContractTime(nts.uk.ctx.at.shared.dom.workingcondition.
	 * LaborContractTime)
	 */
	@Override
	public void setContractTime(LaborContractTime contractTime) {
		if(contractTime != null) {
			this.entity.setContractTime(contractTime.v());
		}
		
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * nts.uk.ctx.at.shared.dom.workingcondition.WorkingConditionItemSetMemento#
	 * setAutoIntervalSetAtr(nts.uk.ctx.at.shared.dom.workingcondition.
	 * NotUseAtr)
	 */
	@Override
	public void setAutoIntervalSetAtr(NotUseAtr autoIntervalSetAtr) {
		if(autoIntervalSetAtr != null ) {
			this.entity.setAutoIntervalSetAtr(autoIntervalSetAtr.value);
		}
		
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * nts.uk.ctx.at.shared.dom.workingcondition.WorkingConditionItemSetMemento#
	 * setWorkDayOfWeek(nts.uk.ctx.at.shared.dom.workingcondition.
	 * PersonalDayOfWeek)
	 */
	@Override
	public void setWorkDayOfWeek(PersonalDayOfWeek workDayOfWeek, String employeeId) {
		if (workDayOfWeek != null) {
			List<KshmtWorkcondWeek> kshmtWorkcondWeeks = new ArrayList<>();
			if (this.entity.getKshmtWorkcondWeeks() != null) {
				kshmtWorkcondWeeks = this.entity.getKshmtWorkcondWeeks();
			}
			workDayOfWeek
					.saveToMemento(new JpaPerDayOfWeekSetMemento(this.entity.getHistoryId(), kshmtWorkcondWeeks, employeeId));
			this.entity.setKshmtWorkcondWeeks(kshmtWorkcondWeeks);
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * nts.uk.ctx.at.shared.dom.workingcondition.WorkingConditionItemSetMemento#
	 * setEmployeeId(java.lang.String)
	 */
	@Override
	public void setEmployeeId(String employeeId) {
		this.entity.setSid(employeeId);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * nts.uk.ctx.at.shared.dom.workingcondition.WorkingConditionItemSetMemento#
	 * setAutoStampSetAtr(nts.uk.ctx.at.shared.dom.workingcondition.NotUseAtr)
	 */
	@Override
	public void setAutoStampSetAtr(NotUseAtr autoStampSetAtr) {
		if(autoStampSetAtr != null) {
			this.entity.setAutoStampSetAtr(autoStampSetAtr.value);
		}
		
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * nts.uk.ctx.at.shared.dom.workingcondition.WorkingConditionItemSetMemento#
	 * setScheduleMethod(nts.uk.ctx.at.shared.dom.workingcondition.
	 * ScheduleMethod)
	 */
	@Override
	public void setScheduleMethod(Optional<ScheduleMethod> scheduleMethod, String employeeId) {
		// Check exist
		if (!scheduleMethod.isPresent()) {
			this.entity.setKshmtWorkcondScheMeth(null);
			return;
		}

		KshmtWorkcondScheMeth kshmtWorkcondScheMeth = this.entity.getKshmtWorkcondScheMeth();

		if (kshmtWorkcondScheMeth == null) {
			kshmtWorkcondScheMeth = new KshmtWorkcondScheMeth();
		}

		kshmtWorkcondScheMeth.setSid(employeeId);
		scheduleMethod.get().saveToMemento(
				new JpaScheduleMethodSetMemento(this.entity.getHistoryId(), kshmtWorkcondScheMeth));

		this.entity.setKshmtWorkcondScheMeth(kshmtWorkcondScheMeth);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * nts.uk.ctx.at.shared.dom.workingcondition.WorkingConditionItemSetMemento#
	 * setHolidayAddTimeSet(nts.uk.ctx.at.shared.dom.workingcondition.
	 * BreakdownTimeDay)
	 */
	@Override
	public void setHolidayAddTimeSet(Optional<BreakdownTimeDay> holidayAddTimeSet) {
		// Check exist
		if (!holidayAddTimeSet.isPresent()) {
			this.entity.setHdAddTimeMorning(null);
			this.entity.setHdAddTimeAfternoon(null);
			this.entity.setHdAddTimeOneDay(null);
			return;
		}
		if (holidayAddTimeSet.get().getMorning() != null){
			this.entity.setHdAddTimeMorning(holidayAddTimeSet.get().getMorning().v());
		} else {
			this.entity.setHdAddTimeMorning(null);
		}
		if (holidayAddTimeSet.get().getAfternoon() != null){
			this.entity.setHdAddTimeAfternoon(holidayAddTimeSet.get().getAfternoon().v());
		} else {
			this.entity.setHdAddTimeAfternoon(null);
		}
		if (holidayAddTimeSet.get().getOneDay() != null){
			this.entity.setHdAddTimeOneDay(holidayAddTimeSet.get().getOneDay().v());
		} else {
				this.entity.setHdAddTimeOneDay(null);
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * nts.uk.ctx.at.shared.dom.workingcondition.WorkingConditionItemSetMemento#
	 * setHourlyPaymentAtr(nts.uk.ctx.at.shared.dom.workingcondition.
	 * HourlyPaymentAtr)
	 */
	@Override
	public void setHourlyPaymentAtr(HourlyPaymentAtr hourlyPaymentAtr) {
		if(hourlyPaymentAtr != null) {
			this.entity.setHourlyPayAtr(hourlyPaymentAtr.value);
		}
		
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * nts.uk.ctx.at.shared.dom.workingcondition.WorkingConditionItemSetMemento#
	 * setTimeApply(java.util.Optional)
	 */
	@Override
	public void setTimeApply(Optional<BonusPaySettingCode> timeApply) {
		if (timeApply.isPresent()){
			this.entity.setTimeApply(timeApply.get().v());
		} else {
			this.entity.setTimeApply(null);
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * nts.uk.ctx.at.shared.dom.workingcondition.WorkingConditionItemSetMemento#
	 * setMonthlyPattern(java.util.Optional)
	 */
	@Override
	public void setMonthlyPattern(Optional<MonthlyPatternCode> monthlyPattern) {
		if (monthlyPattern.isPresent()){
			this.entity.setMonthlyPattern(monthlyPattern.get().v());
		} else {
			this.entity.setMonthlyPattern(null);
		}
	}

}
