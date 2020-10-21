package nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailycalprocess.calculation;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.val;
import nts.uk.ctx.at.shared.dom.calculationattribute.enums.DivergenceTimeAttr;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.autocalsetting.AutoCalAtrOvertime;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.autocalsetting.AutoCalFlexOvertimeSetting;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.autocalsetting.AutoCalOvertimeSetting;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.autocalsetting.AutoCalRestTimeSetting;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.autocalsetting.AutoCalSetting;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.autocalsetting.AutoCalcOfLeaveEarlySetting;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.autocalsetting.TimeLimitUpperLimitSetting;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.autocalsetting.deviationtime.AutoCalcSetOfDivergenceTime;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.attendancetime.TimeLeavingOfDailyAttd;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.attendancetime.TimeLeavingWork;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.attendancetime.WorkTimes;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.calcategory.CalAttrOfDailyAttd;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.common.TimeActualStamp;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.common.timestamp.TimeChangeMeans;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.common.timestamp.WorkLocationCD;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.common.timestamp.WorkStamp;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.dailyattendancework.IntegrationOfDaily;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.workinfomation.ScheduleTimeSheet;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.workinfomation.WorkInfoOfDailyAttendance;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailycalprocess.calculation.timezone.CalculationRangeOfOneDay;
import nts.uk.ctx.at.shared.dom.workrule.outsideworktime.AutoCalRaisingSalarySetting;
import nts.uk.ctx.at.shared.dom.worktime.IntegrationOfWorkTime;
import nts.uk.ctx.at.shared.dom.worktype.WorkType;

/**
 * 予定実績
 * @author keisuke_hoshina
 *
 */
@AllArgsConstructor
@Getter
public class SchedulePerformance {
	
	/** 1日の計算範囲 */
	private CalculationRangeOfOneDay calculationRangeOfOneDay;
	/** 勤務種類 */
	private Optional<WorkType> workType;
	/** 統合就業時間帯 */
	private Optional<IntegrationOfWorkTime> integrationOfWorkTime;
	
	public static IntegrationOfDaily createScheduleTimeSheet(IntegrationOfDaily integrationOfDaily) {
		/*勤務予定を日別実績に変換*/
		val changedShedule = convertScheduleToRecord(integrationOfDaily);
		/*計算区分を変更*/
		val changedCalcAtr = changeCalcAtr(changedShedule);
		return changedCalcAtr;
	}
	
	/**
	 * 勤務予定を日別実績に変換
	 * @param 日別実績の勤務情報
	 * @param 日別実績の出退勤
	 */
	private static IntegrationOfDaily convertScheduleToRecord(IntegrationOfDaily integrationOfDaily) {
		
		IntegrationOfDaily copyIntegration = integrationOfDaily;
		//勤務情報を移す
		WorkInfoOfDailyAttendance workInfo = integrationOfDaily.getWorkInformation();
//		workInfo.setRecordInfo(workInfo.getScheduleInfo());
		
		List<TimeLeavingWork> scheduleTimeSheetList = new ArrayList<TimeLeavingWork>(); 
		for(ScheduleTimeSheet schedule : workInfo.getScheduleTimeSheets()) {
			WorkStamp attendance = new WorkStamp(schedule.getAttendance(),schedule.getAttendance(), new WorkLocationCD("01"), TimeChangeMeans.AUTOMATIC_SET );
			WorkStamp leaving    = new WorkStamp(schedule.getLeaveWork(),schedule.getLeaveWork(), new WorkLocationCD("01"), TimeChangeMeans.AUTOMATIC_SET );
			TimeActualStamp atStamp = new TimeActualStamp(attendance,attendance,workInfo.getScheduleTimeSheets().size());
			TimeActualStamp leStamp = new TimeActualStamp(leaving,leaving,workInfo.getScheduleTimeSheets().size());
			TimeLeavingWork timeLeavingWork = new TimeLeavingWork(schedule.getWorkNo(),atStamp,leStamp);
			scheduleTimeSheetList.add(timeLeavingWork);
		}
		val timeLeavingOfDaily = new TimeLeavingOfDailyAttd(scheduleTimeSheetList, new WorkTimes(workInfo.getScheduleTimeSheets().size()));
		copyIntegration.setAttendanceLeave(Optional.of(timeLeavingOfDaily));
		return copyIntegration;
	}
	
	/**
	 * 計算区分を変更する
	 * @return 計算区分変更後の日別実績(WORK)
	 */
	private static IntegrationOfDaily changeCalcAtr(IntegrationOfDaily integrationOfDaily){
		
		CalAttrOfDailyAttd calAttr = new CalAttrOfDailyAttd(new AutoCalFlexOvertimeSetting(new AutoCalSetting(TimeLimitUpperLimitSetting.NOUPPERLIMIT, AutoCalAtrOvertime.CALCULATEMBOSS)),
														  new AutoCalRaisingSalarySetting(true,true),
														  new AutoCalRestTimeSetting(new AutoCalSetting(TimeLimitUpperLimitSetting.NOUPPERLIMIT, AutoCalAtrOvertime.CALCULATEMBOSS)
																  ,new AutoCalSetting(TimeLimitUpperLimitSetting.NOUPPERLIMIT, AutoCalAtrOvertime.CALCULATEMBOSS)),
														  new AutoCalOvertimeSetting(new AutoCalSetting(TimeLimitUpperLimitSetting.NOUPPERLIMIT, AutoCalAtrOvertime.CALCULATEMBOSS), 
																  new AutoCalSetting(TimeLimitUpperLimitSetting.NOUPPERLIMIT, AutoCalAtrOvertime.CALCULATEMBOSS), 
																  new AutoCalSetting(TimeLimitUpperLimitSetting.NOUPPERLIMIT, AutoCalAtrOvertime.CALCULATEMBOSS), 
																  new AutoCalSetting(TimeLimitUpperLimitSetting.NOUPPERLIMIT, AutoCalAtrOvertime.CALCULATEMBOSS), 
																  new AutoCalSetting(TimeLimitUpperLimitSetting.NOUPPERLIMIT, AutoCalAtrOvertime.CALCULATEMBOSS), 
																  new AutoCalSetting(TimeLimitUpperLimitSetting.NOUPPERLIMIT, AutoCalAtrOvertime.CALCULATEMBOSS)),
														  new AutoCalcOfLeaveEarlySetting(true, true),
														  new AutoCalcSetOfDivergenceTime(DivergenceTimeAttr.USE));
		if(integrationOfDaily.getCalAttr() != null) {
			calAttr = new CalAttrOfDailyAttd(new AutoCalFlexOvertimeSetting(new AutoCalSetting(integrationOfDaily.getCalAttr().getFlexExcessTime().getFlexOtTime().getUpLimitORtSet(), AutoCalAtrOvertime.CALCULATEMBOSS)),
											new AutoCalRaisingSalarySetting(true,true),
											new AutoCalRestTimeSetting(new AutoCalSetting(integrationOfDaily.getCalAttr().getHolidayTimeSetting().getLateNightTime().getUpLimitORtSet(), AutoCalAtrOvertime.CALCULATEMBOSS)
													,new AutoCalSetting(integrationOfDaily.getCalAttr().getHolidayTimeSetting().getRestTime().getUpLimitORtSet(), AutoCalAtrOvertime.CALCULATEMBOSS)),
											new AutoCalOvertimeSetting(new AutoCalSetting(integrationOfDaily.getCalAttr().getOvertimeSetting().getEarlyOtTime().getUpLimitORtSet(), AutoCalAtrOvertime.CALCULATEMBOSS), 
													new AutoCalSetting(integrationOfDaily.getCalAttr().getOvertimeSetting().getEarlyMidOtTime().getUpLimitORtSet(), AutoCalAtrOvertime.CALCULATEMBOSS), 
													new AutoCalSetting(integrationOfDaily.getCalAttr().getOvertimeSetting().getNormalOtTime().getUpLimitORtSet(), AutoCalAtrOvertime.CALCULATEMBOSS), 
													new AutoCalSetting(integrationOfDaily.getCalAttr().getOvertimeSetting().getNormalMidOtTime().getUpLimitORtSet(), AutoCalAtrOvertime.CALCULATEMBOSS), 
													new AutoCalSetting(integrationOfDaily.getCalAttr().getOvertimeSetting().getLegalOtTime().getUpLimitORtSet(), AutoCalAtrOvertime.CALCULATEMBOSS), 
													new AutoCalSetting(integrationOfDaily.getCalAttr().getOvertimeSetting().getLegalMidOtTime().getUpLimitORtSet(), AutoCalAtrOvertime.CALCULATEMBOSS)),
											new AutoCalcOfLeaveEarlySetting(true, true),
											new AutoCalcSetOfDivergenceTime(DivergenceTimeAttr.USE));
		}
		integrationOfDaily.setCalAttr(calAttr);
		return integrationOfDaily;
	}
	
}
