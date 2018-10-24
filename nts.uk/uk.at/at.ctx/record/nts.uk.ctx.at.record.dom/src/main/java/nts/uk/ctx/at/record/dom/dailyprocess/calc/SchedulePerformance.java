package nts.uk.ctx.at.record.dom.dailyprocess.calc;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import lombok.Getter;
import lombok.val;
import nts.uk.ctx.at.record.dom.actualworkinghours.daily.workschedule.WorkScheduleTimeOfDaily;
import nts.uk.ctx.at.record.dom.calculationattribute.AutoCalcSetOfDivergenceTime;
import nts.uk.ctx.at.record.dom.calculationattribute.CalAttrOfDailyPerformance;
import nts.uk.ctx.at.record.dom.calculationattribute.enums.DivergenceTimeAttr;
import nts.uk.ctx.at.record.dom.calculationattribute.enums.LeaveAttr;
//import nts.uk.ctx.at.record.dom.actualworkinghours.WorkScheduleTimeOfDaily;
//import nts.uk.ctx.at.record.dom.daily.WorkInformationOfDaily;
import nts.uk.ctx.at.record.dom.workinformation.ScheduleTimeSheet;
import nts.uk.ctx.at.record.dom.workinformation.WorkInfoOfDailyPerformance;
import nts.uk.ctx.at.record.dom.worklocation.WorkLocationCD;
//import nts.uk.ctx.at.record.dom.workinformation.WorkInformation;
import nts.uk.ctx.at.record.dom.worktime.TimeActualStamp;
import nts.uk.ctx.at.record.dom.worktime.TimeLeavingOfDailyPerformance;
import nts.uk.ctx.at.record.dom.worktime.TimeLeavingWork;
import nts.uk.ctx.at.record.dom.worktime.WorkStamp;
import nts.uk.ctx.at.record.dom.worktime.enums.StampSourceInfo;
import nts.uk.ctx.at.record.dom.worktime.primitivevalue.WorkTimes;
import nts.uk.ctx.at.shared.dom.WorkInformation;
import nts.uk.ctx.at.shared.dom.ot.autocalsetting.AutoCalAtrOvertime;
import nts.uk.ctx.at.shared.dom.ot.autocalsetting.AutoCalFlexOvertimeSetting;
import nts.uk.ctx.at.shared.dom.ot.autocalsetting.AutoCalOvertimeSetting;
import nts.uk.ctx.at.shared.dom.ot.autocalsetting.AutoCalRestTimeSetting;
import nts.uk.ctx.at.shared.dom.ot.autocalsetting.AutoCalSetting;
import nts.uk.ctx.at.shared.dom.ot.autocalsetting.AutoCalcOfLeaveEarlySetting;
import nts.uk.ctx.at.shared.dom.ot.autocalsetting.TimeLimitUpperLimitSetting;
import nts.uk.ctx.at.shared.dom.workrule.outsideworktime.AutoCalRaisingSalarySetting;

/**
 * 予定実績
 * @author keisuke_hoshina
 *
 */
@Getter
public class SchedulePerformance {
	//勤務情報
	private WorkInformation workInformation;
	//日別実績の予定時間
	private WorkScheduleTimeOfDaily actualTime;
	
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
		WorkInfoOfDailyPerformance workInfo = integrationOfDaily.getWorkInformation();
		workInfo.setRecordInfo(workInfo.getScheduleInfo());
		
		List<TimeLeavingWork> scheduleTimeSheetList = new ArrayList<TimeLeavingWork>(); 
		for(ScheduleTimeSheet schedule : workInfo.getScheduleTimeSheets()) {
			WorkStamp attendance = new WorkStamp(schedule.getAttendance(),schedule.getAttendance(), new WorkLocationCD("01"), StampSourceInfo.CORRECTION_RECORD_SET );
			WorkStamp leaving    = new WorkStamp(schedule.getLeaveWork(),schedule.getLeaveWork(), new WorkLocationCD("01"), StampSourceInfo.CORRECTION_RECORD_SET );
			TimeActualStamp atStamp = new TimeActualStamp(attendance,attendance,workInfo.getScheduleTimeSheets().size());
			TimeActualStamp leStamp = new TimeActualStamp(leaving,leaving,workInfo.getScheduleTimeSheets().size());
			TimeLeavingWork timeLeavingWork = new TimeLeavingWork(schedule.getWorkNo(),atStamp,leStamp);
			scheduleTimeSheetList.add(timeLeavingWork);
		}
		val timeLeavingOfDaily = new TimeLeavingOfDailyPerformance(workInfo.getEmployeeId(),new WorkTimes(workInfo.getScheduleTimeSheets().size()), scheduleTimeSheetList, workInfo.getYmd());
		copyIntegration.setAttendanceLeave(Optional.of(timeLeavingOfDaily));
		return copyIntegration;
	}
	
	/**
	 * 計算区分を変更する
	 * @return 計算区分変更後の日別実績(WORK)
	 */
	private static IntegrationOfDaily changeCalcAtr(IntegrationOfDaily integrationOfDaily){
		
		CalAttrOfDailyPerformance calAttr = new CalAttrOfDailyPerformance(integrationOfDaily.getWorkInformation().getEmployeeId(), 
																		  integrationOfDaily.getWorkInformation().getYmd(),
																		  new AutoCalFlexOvertimeSetting(new AutoCalSetting(TimeLimitUpperLimitSetting.NOUPPERLIMIT, AutoCalAtrOvertime.CALCULATEMBOSS)),
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
			calAttr = new CalAttrOfDailyPerformance(integrationOfDaily.getWorkInformation().getEmployeeId(), 
													integrationOfDaily.getWorkInformation().getYmd(),
													new AutoCalFlexOvertimeSetting(new AutoCalSetting(integrationOfDaily.getCalAttr().getFlexExcessTime().getFlexOtTime().getUpLimitORtSet(), AutoCalAtrOvertime.CALCULATEMBOSS)),
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
