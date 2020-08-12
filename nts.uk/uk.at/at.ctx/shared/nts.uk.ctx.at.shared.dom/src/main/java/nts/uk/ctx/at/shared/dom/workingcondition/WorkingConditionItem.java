/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.dom.workingcondition;

import java.util.List;
import java.util.Optional;

import lombok.Getter;
import nts.arc.enums.EnumAdaptor;
import nts.arc.layer.dom.AggregateRoot;

/**
 * The Class WorkingConditionItem.
 * 労働条件項目
 */
@Getter
public class WorkingConditionItem extends AggregateRoot {

	/** The history id. */
	// 履歴ID
	private String historyId;
	
	/** The hourly ppayment atr. */
	// 時給者区分
	private HourlyPaymentAtr hourlyPaymentAtr;

	/** The schedule management atr. */
	// 予定管理区分
	private ManageAtr scheduleManagementAtr;

	/** The work day of week. */
	// 曜日別勤務
	private PersonalDayOfWeek workDayOfWeek;

	/** The work category. */
	// 区分別勤務
	private PersonalWorkCategory workCategory;

	/** The auto stamp set atr. */
	// 自動打刻セット区分
	private NotUseAtr autoStampSetAtr;

	/** The auto interval set atr. */
	// 就業時間帯の自動設定区分
	private NotUseAtr autoIntervalSetAtr;

	/** The employee id. */
	// 社員ID
	private String employeeId;

	/** The vacation added time atr. */
	// 休暇加算時間利用区分
	private NotUseAtr vacationAddedTimeAtr;

	/** The contract time. */
	// 契約時間
	private LaborContractTime contractTime;

	/** The labor system. */
	// 労働制
	private WorkingSystem laborSystem = WorkingSystem.REGULAR_WORK;

	/** The holiday add time set. */
	// 休暇加算時間設定
	private Optional<BreakdownTimeDay> holidayAddTimeSet;

	/** The schedule method. */
	// 予定作成方法
	private Optional<ScheduleMethod> scheduleMethod;
	
	/** The time apply. */
	// 加給時間帯
	private Optional<BonusPaySettingCode> timeApply;
	
	/** The monthly pattern. */
	// 月間パターン
	private Optional<MonthlyPatternCode> monthlyPattern;

	/**
	 * Instantiates a new working condition item.
	 *
	 * @param memento
	 *            the memento
	 */
	public WorkingConditionItem(WorkingConditionItemGetMemento memento) {
		this.historyId = memento.getHistoryId();
		this.hourlyPaymentAtr = memento.getHourlyPaymentAtr();
		this.scheduleManagementAtr = memento.getScheduleManagementAtr();
		this.vacationAddedTimeAtr = memento.getVacationAddedTimeAtr();
		this.laborSystem = Optional.ofNullable(memento.getLaborSystem()).orElseGet(() -> WorkingSystem.REGULAR_WORK);
		this.workCategory = memento.getWorkCategory();
		this.contractTime = memento.getContractTime();
		this.autoIntervalSetAtr = memento.getAutoIntervalSetAtr();
		this.workDayOfWeek = memento.getWorkDayOfWeek();
		this.employeeId = memento.getEmployeeId();
		this.autoStampSetAtr = memento.getAutoStampSetAtr();
		this.scheduleMethod = memento.getScheduleMethod();
		this.holidayAddTimeSet = memento.getHolidayAddTimeSet();
		this.timeApply = memento.getTimeApply();
		this.monthlyPattern = memento.getMonthlyPattern();
	}

	/**
	 * Save to memento.
	 *
	 * @param memento
	 *            the memento
	 */
	public void saveToMemento(WorkingConditionItemSetMemento memento) {
		memento.setHistoryId(this.historyId);
		memento.setHourlyPaymentAtr(this.hourlyPaymentAtr);
		memento.setScheduleManagementAtr(this.scheduleManagementAtr);
		memento.setVacationAddedTimeAtr(this.vacationAddedTimeAtr);
		memento.setLaborSystem(Optional.ofNullable(this.laborSystem).orElseGet(() -> WorkingSystem.REGULAR_WORK));
		//KSHMT_PER_WORK_CAT
		memento.setWorkCategory(this.workCategory, this.employeeId);
		memento.setContractTime(this.contractTime);
		memento.setAutoIntervalSetAtr(this.autoIntervalSetAtr);
		//KSHMT_PERSONAL_DAY_OF_WEEK
		memento.setWorkDayOfWeek(this.workDayOfWeek, this.employeeId);
		memento.setEmployeeId(this.employeeId);
		memento.setAutoStampSetAtr(this.autoStampSetAtr);
		//KSHMT_SCHEDULE_METHOD
		memento.setScheduleMethod(this.scheduleMethod, this.employeeId);
		memento.setHolidayAddTimeSet(this.holidayAddTimeSet);
		memento.setTimeApply(this.timeApply);
		memento.setMonthlyPattern(this.monthlyPattern);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((historyId == null) ? 0 : historyId.hashCode());
		return result;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		WorkingConditionItem other = (WorkingConditionItem) obj;
		if (historyId == null) {
			if (other.historyId != null)
				return false;
		} else if (!historyId.equals(other.historyId))
			return false;
		return true;
	}

	/**
	 * Instantiates a new working condition item.
	 *
	 * @param historyId
	 *            the history id
	 * @param scheduleManagementAtr
	 *            the schedule management atr
	 * @param workDayOfWeek
	 *            the work day of week
	 * @param workCategory
	 *            the work category
	 * @param autoStampSetAtr
	 *            the auto stamp set atr
	 * @param autoIntervalSetAtr
	 *            the auto interval set atr
	 * @param employeeId
	 *            the employee id
	 * @param vacationAddedTimeAtr
	 *            the vacation added time atr
	 * @param contractTime
	 *            the contract time
	 * @param laborSystem
	 *            the labor system
	 * @param holidayAddTimeSet
	 *            the holiday add time set
	 * @param scheduleMethod
	 *            the schedule method
	 * @param hourlyPaymentAtr
	 * 			  the hourlyPaymentAtr
	 * @param timeApply
	 * 			  the timeApply
	 * @param monthlyPattern
	 * 			  the monthlyPattern
	 */
	public WorkingConditionItem(String historyId, ManageAtr scheduleManagementAtr, PersonalDayOfWeek workDayOfWeek,
			PersonalWorkCategory workCategory, NotUseAtr autoStampSetAtr, NotUseAtr autoIntervalSetAtr,
			String employeeId, NotUseAtr vacationAddedTimeAtr, LaborContractTime contractTime, WorkingSystem laborSystem,
			BreakdownTimeDay holidayAddTimeSet, ScheduleMethod scheduleMethod, Integer hourlyPaymentAtr,
			BonusPaySettingCode timeApply, MonthlyPatternCode monthlyPattern) {
		super();
		this.historyId = historyId;
		this.scheduleManagementAtr = scheduleManagementAtr;
		this.workDayOfWeek = workDayOfWeek;
		this.workCategory = workCategory;
		this.autoStampSetAtr = autoStampSetAtr;
		this.autoIntervalSetAtr = autoIntervalSetAtr;
		this.employeeId = employeeId;
		this.vacationAddedTimeAtr = vacationAddedTimeAtr;
		this.contractTime = contractTime;
		this.laborSystem = Optional.ofNullable(laborSystem).orElseGet(() -> WorkingSystem.REGULAR_WORK);
		this.holidayAddTimeSet = Optional.ofNullable(holidayAddTimeSet);
		this.scheduleMethod = Optional.ofNullable(scheduleMethod);
		if (hourlyPaymentAtr != null){
			this.hourlyPaymentAtr = EnumAdaptor.valueOf(hourlyPaymentAtr.intValue(),HourlyPaymentAtr.class);
		}
		this.timeApply = Optional.ofNullable(timeApply);
		this.monthlyPattern = Optional.ofNullable(monthlyPattern);
		
		
	}

	public WorkingConditionItem(String historyId, ManageAtr scheduleManagementAtr, String employeeId) {
		super();
		this.historyId = historyId;
		this.scheduleManagementAtr = scheduleManagementAtr;
		this.employeeId = employeeId;
	}


}
