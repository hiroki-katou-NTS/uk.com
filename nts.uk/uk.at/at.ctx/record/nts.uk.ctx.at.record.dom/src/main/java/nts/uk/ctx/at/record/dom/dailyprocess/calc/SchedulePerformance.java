//package nts.uk.ctx.at.record.dom.dailyprocess.calc;
//
//import java.util.ArrayList;
//import java.util.List;
//import java.util.Optional;
//
//import lombok.Getter;
////import nts.uk.ctx.at.record.dom.actualworkinghours.WorkScheduleTimeOfDaily;
////import nts.uk.ctx.at.record.dom.daily.WorkInformationOfDaily;
//import nts.uk.ctx.at.record.dom.workinformation.ScheduleTimeSheet;
//import nts.uk.ctx.at.record.dom.workinformation.WorkInfoOfDailyPerformance;
////import nts.uk.ctx.at.record.dom.workinformation.WorkInformation;
//import nts.uk.ctx.at.record.dom.worktime.TimeActualStamp;
//import nts.uk.ctx.at.record.dom.worktime.TimeLeavingOfDailyPerformance;
//import nts.uk.ctx.at.record.dom.worktime.TimeLeavingWork;
//import nts.uk.ctx.at.record.dom.worktime.WorkStamp;
////import nts.uk.ctx.at.shared.dom.worktime.WorkTimeDivision;
////import nts.uk.ctx.at.shared.dom.worktime.fixedworkset.FixRestSetting;
////import nts.uk.ctx.at.shared.dom.worktime.flexworkset.FlexWorkSetting;
////import nts.uk.ctx.at.shared.dom.worktime.fluidworkset.FluRestTime;
////import nts.uk.ctx.at.shared.dom.worktimeset.common.FlowWorkRestSetting;
//
///**
// * 予定実績
// * @author keisuke_hoshina
// *
// */
//@Getter
//public class SchedulePerformance {
//	private WorkInformation workInformation;
//	private WorkScheduleTimeOfDaily actualTime;
//	//private  出退勤
//	
//	public static SchedulePerformance createScheduleTimeSheet(WorkInfoOfDailyPerformance workInformationOfDaily,TimeLeavingOfDailyPerformance attendanceLeaving,
//															  Optional<FixRestSetting> fixRestSetting ,Optional<FlowWorkRestSetting> flowWorkRestSetting) {
//		/*勤務予定を取得*/
//		/*勤務予定を日別実績に変換*/
//		convertScheduleToRecord(workInformationOfDaily,attendanceLeaving);
//		/*計算区分を変更*/
//		changeCalcAtr();
//		/*休憩情報を変更*/
//		changeBreakSet(fixRestSetting ,flowWorkRestSetting);
//		/*時間帯を作成*/
//	}
//	
//	/**
//	 * 勤務予定を日別実績に変換
//	 * @param 日別実績の勤務情報
//	 * @param 日別実績の出退勤
//	 */
//	public static void convertScheduleToRecord(WorkInfoOfDailyPerformance workInformationOfDaily,TimeLeavingOfDailyPerformance attendanceLeaving) {
//		workInformationOfDaily.shiftFromScheduleToRecord();
//		List<TimeLeavingWork> scheduleTimeSheetList = new ArrayList<TimeLeavingWork>(); 
//		for(ScheduleTimeSheet schedule : workInformationOfDaily.getWorkScheduleTimeSheet()) {
//			//勤怠打刻(打刻元情報、勤務場所コード、時刻、丸め後の時刻)
//			WorkStamp workStamp = new WorkStamp();
//			//実打刻付き(回数、勤怠打刻、勤怠打刻)
//			scheduleTimeSheetList.add(new TimeLeavingWork()));
//		}
//		attendanceLeaving.getTimeLeavingWorks().clear();
//		attendanceLeaving.getTimeLeavingWorks().addAll(scheduleTimeSheetList);
//	}
//	
//	/**
//	 * 休憩設定の変更
//	 */
//	public static void changeBreakSet(Optional<FixRestSetting> fixRestSetting ,Optional<FlowWorkRestSetting> flowWorkRestSetting) {
//		//流動休憩
//		if() {
//			flowWorkRestSetting.get().getFlowRestSetting().getFlowFixedRestSetting().changeCalcMethodToSchedule();
//			flowWorkRestSetting.get().getCommonRestSetting().changeCalcMethodToRecordUntilLeaveWork();
//		}
//		//固定休憩
//		else {
//			fixRestSetting.get().changeCalcMethodToSchedule();
//			fixRestSetting.get().changeCalcMethodToSchedule();
//		}
//	}
//	
//	/**
//	 * 固定休憩設定にする
//	 */
//	public static void create(WorkTimeDivision workTimeDivision, FluRestTime fluRestTime) {
//		if(workTimeDivision.isfluidorFlex()) {
//			fluRestTime.changeTrueUseFixedRestTime();
//		}
//	}
//	
//	/**
//	 * 計算区分を変更する
//	 */
//	public static void changeCalcAtr(){
//		
//	}
//	
//
//	
//
//	
//	
//	
//	
//
//}
