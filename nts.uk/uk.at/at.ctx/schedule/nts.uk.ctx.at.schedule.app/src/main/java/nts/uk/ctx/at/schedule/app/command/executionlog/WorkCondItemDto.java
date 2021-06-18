package nts.uk.ctx.at.schedule.app.command.executionlog;

import java.util.Optional;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import nts.arc.time.calendar.period.DatePeriod;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.bonuspay.primitives.BonusPaySettingCode;
import nts.uk.ctx.at.shared.dom.workingcondition.BreakdownTimeDay;
import nts.uk.ctx.at.shared.dom.workingcondition.HourlyPaymentAtr;
import nts.uk.ctx.at.shared.dom.workingcondition.LaborContractTime;
import nts.uk.ctx.at.shared.dom.workingcondition.ManageAtr;
import nts.uk.ctx.at.shared.dom.workingcondition.MonthlyPatternCode;
import nts.uk.ctx.at.shared.dom.workingcondition.NotUseAtr;
import nts.uk.ctx.at.shared.dom.workingcondition.PersonalDayOfWeek;
import nts.uk.ctx.at.shared.dom.workingcondition.PersonalWorkCategory;
import nts.uk.ctx.at.shared.dom.workingcondition.ScheduleMethod;
import nts.uk.ctx.at.shared.dom.workingcondition.WorkingConditionItem;
import nts.uk.ctx.at.shared.dom.workingcondition.WorkingSystem;

/**
 * 労働条件項目
 * @author sonnh1
 *
 */
@AllArgsConstructor
@NoArgsConstructor
@Data
public class WorkCondItemDto {

	private DatePeriod datePeriod;

	private String historyId;

	private HourlyPaymentAtr hourlyPaymentAtr;

	/** 予定管理区分 */
	private ManageAtr scheduleManagementAtr;

	/** 曜日別勤務 */
	private PersonalDayOfWeek workDayOfWeek;

	/** 区分別勤務 */
	private PersonalWorkCategory workCategory;

	private NotUseAtr autoStampSetAtr;

	private NotUseAtr autoIntervalSetAtr;

	private String employeeId;

	private NotUseAtr vacationAddedTimeAtr;

	private LaborContractTime contractTime;

	private WorkingSystem laborSystem;

	private Optional<BreakdownTimeDay> holidayAddTimeSet;

	/** 予定作成方法 */
	private Optional<ScheduleMethod> scheduleMethod;

	private Optional<BonusPaySettingCode> timeApply;

	private Optional<MonthlyPatternCode> monthlyPattern;

	public WorkCondItemDto(WorkingConditionItem workingConditionItem) {
		super();
		this.historyId = workingConditionItem.getHistoryId();
		this.hourlyPaymentAtr = workingConditionItem.getHourlyPaymentAtr();
		this.scheduleManagementAtr = workingConditionItem.getScheduleManagementAtr();
		this.workDayOfWeek = workingConditionItem.getWorkDayOfWeek();
		this.workCategory = workingConditionItem.getWorkCategory();
		this.autoStampSetAtr = workingConditionItem.getAutoStampSetAtr();
		this.autoIntervalSetAtr = workingConditionItem.getAutoIntervalSetAtr();
		this.employeeId = workingConditionItem.getEmployeeId();
		this.vacationAddedTimeAtr = workingConditionItem.getVacationAddedTimeAtr();
		this.contractTime = workingConditionItem.getContractTime();
		this.laborSystem = workingConditionItem.getLaborSystem();
		this.holidayAddTimeSet = workingConditionItem.getHolidayAddTimeSet();
		this.scheduleMethod = workingConditionItem.getScheduleMethod();
		this.timeApply = workingConditionItem.getTimeApply();
		this.monthlyPattern = workingConditionItem.getMonthlyPattern();
	}
}
