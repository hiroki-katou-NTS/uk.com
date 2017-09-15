package nts.uk.ctx.at.record.dom.dailyprocess.calc.record.mekestimesheet;

import lombok.AllArgsConstructor;
import nts.uk.ctx.at.record.dom.dailyprocess.calc.DeductionTimeSheet;
import nts.uk.ctx.at.record.dom.dailyprocess.calc.withinstatutory.WithinWorkTimeSheet;
import nts.uk.ctx.at.shared.dom.attendance.UseSetting;
import nts.uk.ctx.at.shared.dom.worktime.WorkTime;
import nts.uk.ctx.at.shared.dom.worktime.CommomSetting.SpecifiedTimeSheetSetting;
import nts.uk.ctx.at.shared.dom.worktime.CommonSetting.lateleaveearly.GraceTimeSetting;
import nts.uk.ctx.at.shared.dom.worktime.fixedworkset.WorkTimeCommonSet;
import nts.uk.ctx.at.shared.dom.worktime.fixedworkset.timespan.TimeSpanWithRounding;
import nts.uk.shr.com.time.TimeWithDayAttr;

/**
 * 遅刻早退管理時間枠
 * @author ken_takasu
 *
 */

public class LateLeaveEarlyManagementTimeFrame {
	private LateTimeSheet lateTimeSheet;
	private LeaveEarlyTimeSheet leaveEarlyTimeSheet;
	
	public LateTimeSheet lateTimeSheet(LateTimeSheet timeSheet) {
		return this.lateTimeSheet = timeSheet;
	}
	
	
	
	/**
	 * 遅刻早退時間から時間帯を控除
	 */
	public TimeSpanWithRounding deductTimeZoneFromLeaveEarlyLateArrival(SpecifiedTimeSheetSetting specifiedTimeSheet,TimeWithDayAttr goWorkTime,int workNo
			,TimeWithDayAttr leaveWorkTime,WorkTimeCommonSet workTimeCommonSet,WithinWorkTimeSheet withinWorkTimeSheet, WorkTime workTime
			,DeductionTimeSheet deductionTimeSheet,UseSetting notDeductLateLeaveEarly,GraceTimeSetting graceTimeSetting,TimeSpanWithRounding timeSheet) {
		
		//遅刻時間を計算する
		lateTimeSheet = new lateTimeSheet(lateTimeSheet.lateTimeCalc(specifiedTimeSheet, goWorkTime, workNo, workTimeCommonSet, withinWorkTimeSheet, workTime, deductionTimeSheet));
		
		int lateDeductTime = lateTimeSheet.getForDeducationTimeSheet().lengthAsMinutes();
		
		//就業時間内時間帯から控除するか判断し控除する				
		if(lateDeductionJudment(notDeductLateLeaveEarly, lateDeductTime, graceTimeSetting)) {
			//遅刻時間帯の終了時刻を開始時刻にする
			timeSheet.shiftOnlyStart(lateTimeSheet.getForDeducationTimeSheet().getEnd());
		}
		
		//早退時間を計算する
		leaveEarlyTimeSheet.leaveEarlyTimeCalc(specifiedTimeSheet, leaveWorkTime, workNo, workTimeCommonSet, 
				withinWorkTimeSheet, workTime, deductionTimeSheet);
		
		int leaveEarlyDeductTime = leaveEarlyTimeSheet.getForDeducationTimeSheet().lengthAsMinutes();
		
		//就業時間内時間帯から控除するか判断し控除する
		if(lateDeductionJudment(notDeductLateLeaveEarly, leaveEarlyDeductTime, graceTimeSetting)) {
			//早退時間帯の開始時刻を終了時刻にする
			timeSheet.shiftOnlyEnd(leaveEarlyTimeSheet.getForDeducationTimeSheet().getStart());
		}		
		return timeSheet;
	}
	
	/**
	 * 就業時間内時間帯から控除するか判断
	 * @param notDeductLateLeaveEarly
	 * @param DeductTime
	 * @param graceTimeSetting
	 * @return
	 */
	public boolean lateDeductionJudment(UseSetting notDeductLateLeaveEarly,int DeductTime,GraceTimeSetting graceTimeSetting) {
		if(notDeductLateLeaveEarly.isUse()) {//早退設定を控除項目にするかをチェックする
			if(DeductTime > 0||!graceTimeSetting.isIncludeInWorkingHours()) {
				return true;
			}			
		}
		return false;		
	}
	
	
}
