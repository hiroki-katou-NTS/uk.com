package nts.uk.ctx.at.record.dom.actualworkinghours.daily.workschedule;

import java.util.Collections;
import java.util.Optional;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import nts.uk.ctx.at.record.dom.actualworkinghours.TotalWorkingTime;
import nts.uk.ctx.at.record.dom.calculationattribute.BonusPayAutoCalcSet;
import nts.uk.ctx.at.record.dom.calculationattribute.HolidayTimesheetCalculationSetting;
import nts.uk.ctx.at.record.dom.calculationattribute.OvertimeTimesheetCalculationSetting;
import nts.uk.ctx.at.record.dom.calculationattribute.WorkingTimesheetCalculationSetting;
import nts.uk.ctx.at.record.dom.dailyprocess.calc.ManagePerPersonDailySet;
import nts.uk.ctx.at.record.dom.dailyprocess.calc.ManageReGetClass;
import nts.uk.ctx.at.record.dom.dailyprocess.calc.VacationClass;
import nts.uk.ctx.at.record.dom.dailyprocess.calc.converter.CalcDefaultValue;
import nts.uk.ctx.at.shared.dom.common.CompanyId;
import nts.uk.ctx.at.shared.dom.common.time.AttendanceTime;
import nts.uk.ctx.at.shared.dom.worktime.common.WorkTimezoneShortTimeWorkSet;
import nts.uk.ctx.at.shared.dom.worktime.flowset.FlowOTSet;
import nts.uk.ctx.at.shared.dom.worktime.worktimeset.WorkTimeDailyAtr;
import nts.uk.ctx.at.shared.dom.worktype.WorkType;
import nts.uk.shr.com.context.AppContexts;

/**
 * 
 * @author nampt
 * 日別実績の勤務予定時間
 *
 */

@AllArgsConstructor
@NoArgsConstructor
@Getter
public class WorkScheduleTimeOfDaily {
	
	//勤務予定時間
	private WorkScheduleTime workScheduleTime;
	
	//計画所定労働時間
	private AttendanceTime schedulePrescribedLaborTime;
	
	//実績所定労働時間
	private AttendanceTime recordPrescribedLaborTime;
	
	public static WorkScheduleTimeOfDaily defaultValue(){
		return new WorkScheduleTimeOfDaily(WorkScheduleTime.defaultValue(), new AttendanceTime(0), new AttendanceTime(0));
	}
	
	/**
	* 時刻から所定時間を計算する
	* @param manageReGetClassOfSchedule 時間帯作成、時間計算で再取得が必要になっているクラスたちの管理クラス(予定）
	* @param workType 勤務種類
	* @param personDailySetting 毎日変更の可能性のあるマスタ管理クラス（予定）
	* @param FlowOTSet 流動残業設定
	* @return
	*/
	public static AttendanceTime calcPredeterminedFromTime(
			ManageReGetClass manageReGetClassOfSchedule,
			WorkType workType,
			ManagePerPersonDailySet personDailySetting,
			FlowOTSet flowOTSet) {
		
		//設定を退避する
		WorkTimezoneShortTimeWorkSet cloneWorkTimezoneShortTimeWorkSet = manageReGetClassOfSchedule.getWorkTimezoneCommonSet().get().getShortTimeWorkSet().clone();
		
		//短時間勤務を勤務として扱う
		manageReGetClassOfSchedule.getWorkTimezoneCommonSet().get().getShortTimeWorkSet().correctDataForFixedChange(flowOTSet.getFixedChangeAtr());
		
		//総労働時間計算用にクラスを作成
		//加給時間計算設定
		String companyId = AppContexts.user().companyId();
		BonusPayAutoCalcSet bonusPayAutoCalcSet = new BonusPayAutoCalcSet(new CompanyId(companyId), 1,
				WorkingTimesheetCalculationSetting.CalculateAutomatic,
				OvertimeTimesheetCalculationSetting.CalculateAutomatic,
				HolidayTimesheetCalculationSetting.CalculateAutomatical);
		
		//休暇クラス
		VacationClass vacation = VacationClass.createAllZero();
		
		//時刻から所定時間を計算
		TotalWorkingTime totalWorkingTime = TotalWorkingTime.calcAllDailyRecord(
				manageReGetClassOfSchedule,
				vacation,
				manageReGetClassOfSchedule.getWorkType().get(),
				Optional.of(WorkTimeDailyAtr.REGULAR_WORK),
				Optional.empty(),
				bonusPayAutoCalcSet,
				Collections.emptyList(),
				personDailySetting.getPersonInfo(),
				Optional.of(manageReGetClassOfSchedule.getCalculationRangeOfOneDay().getPredetermineTimeSetForCalc()),
				manageReGetClassOfSchedule.getWorkRegularAdditionSet().getVacationCalcMethodSet().getWorkTimeCalcMethodOfHoliday().getAdvancedSet().get().getNotDeductLateLeaveEarly(),
				Optional.of(manageReGetClassOfSchedule.getIntegrationOfDaily().getWorkInformation().getRecordInfo().getWorkTimeCode()));
		
		//設定を元に戻す
		manageReGetClassOfSchedule.getWorkTimezoneCommonSet().get().getShortTimeWorkSet().restoreWorkTimezoneShortTimeWorkSet(cloneWorkTimezoneShortTimeWorkSet);

		return totalWorkingTime.getTotalTime();
	}
}
