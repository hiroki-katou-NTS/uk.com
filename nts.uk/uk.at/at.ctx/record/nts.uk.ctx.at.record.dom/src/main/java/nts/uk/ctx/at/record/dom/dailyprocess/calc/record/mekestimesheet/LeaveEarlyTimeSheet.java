package nts.uk.ctx.at.record.dom.dailyprocess.calc.record.mekestimesheet;

import lombok.AllArgsConstructor;
import lombok.val;
import nts.uk.ctx.at.record.dom.dailyprocess.calc.DeductionTimeSheet;
import nts.uk.ctx.at.record.dom.dailyprocess.calc.TimeSheetOfDeductionItem;
import nts.uk.ctx.at.record.dom.dailyprocess.calc.withinstatutory.WithinWorkTimeSheet;
import nts.uk.ctx.at.shared.dom.common.time.TimeSpanForCalc;
import nts.uk.ctx.at.shared.dom.worktime.WorkTime;
import nts.uk.ctx.at.shared.dom.worktime.CommomSetting.PredetermineTimeSheetSetting;
import nts.uk.ctx.at.shared.dom.worktime.CommonSetting.lateleaveearly.LateLeaveEarlyClassification;
import nts.uk.ctx.at.shared.dom.worktime.fixedworkset.WorkTimeCommonSet;
import nts.uk.shr.com.time.TimeWithDayAttr;

/**
 * 早退時間帯
 * @author ken_takasu
 *
 */

@AllArgsConstructor
public class LeaveEarlyTimeSheet {
	
	private TimeSpanForCalc forRecordTimeSheet;
	private TimeSpanForCalc forDeducationTimeSheet;
	
	
	/**
	 * 早退時間の計算
	 * @param specifiedTimeSheet
	 * @param goWorkTime
	 * @param workNo
	 * @param workTimeCommonSet
	 * @param withinWorkTimeSheet
	 * @param workTime
	 * @param deductionTimeSheet
	 * @return
	 */
	public static LeaveEarlyTimeSheet leaveEarlyTimeCalc(PredetermineTimeSheetSetting specifiedTimeSheet,TimeWithDayAttr leaveWorkTime,int workNo
			,WorkTimeCommonSet workTimeCommonSet,WithinWorkTimeSheet withinWorkTimeSheet, WorkTime workTime,DeductionTimeSheet deductionTimeSheet) {	
		TimeSpanForCalc fordeduct;
		TimeSpanForCalc forRec;
		//出勤時刻と早退判断時刻を比較	
		if(leaveWorkTime.lessThan(withinWorkTimeSheet.getleaveEarlyDecisionClock(workNo).getLeaveEarlyDecisionClock())/*猶予時間考慮した上で早退の場合*/
				||workTimeCommonSet.getGraceTimeSet(LateLeaveEarlyClassification.LEAVEEARLY).isIncludeInWorkingHours()){//猶予時間を加算しない場合
			fordeduct = leaveEarlyTimeSheetCreate(leaveWorkTime, workTime, deductionTimeSheet,workNo/*,控除区分:控除*/);
			forRec = leaveEarlyTimeSheetCreate(leaveWorkTime, workTime, deductionTimeSheet,workNo/*,控除区分:計上*/);		

			//休暇時間相殺処理(未作成)
			//早退猶予加算時間←0：00（未作成）
			
		}	
		return new LeaveEarlyTimeSheet(fordeduct,forRec);
	}
	
	
	public static TimeSpanForCalc leaveEarlyTimeSheetCreate(TimeWithDayAttr leaveWorkTime, WorkTime workTime,DeductionTimeSheet deductionTimeSheet,int workNo/*,控除区分*/) {
		//▶▶▶▶▶▶▶▶▶▶▶▶▶▶▶▶▶▶▶▶▶▶▶▶▶▶▶▶▶▶1.早退時間帯の作成▶▶▶▶▶▶▶▶▶▶▶▶▶▶▶▶▶▶▶▶▶▶▶▶▶▶▶▶▶▶▶▶▶▶▶▶▶▶▶▶▶▶▶▶▶▶▶▶▶▶▶▶▶▶
		//計算範囲の取得
		val calcRange = workTime.getleaveEarlyTimeCalcRange(leaveWorkTime, workNo);
		//早退時間帯の開始時刻の作成
		TimeWithDayAttr start;
		//遅刻時間帯の終了時刻の作成
		TimeWithDayAttr end = calcRange.getEnd();
		if(leaveWorkTime.lessThanOrEqualTo(calcRange.getEnd())) {
			start = leaveWorkTime;
		}else {
			start = calcRange.getEnd();
		}
		TimeSpanForCalc leaveEarlyTimeSheet = new TimeSpanForCalc(start, end);
		//▶▶▶▶▶▶▶▶▶▶▶▶▶▶▶▶▶▶▶▶▶▶▶▶▶▶▶▶2.遅刻時間を計算▶▶▶▶▶▶▶▶▶▶▶▶▶▶▶▶▶▶▶▶▶▶▶▶▶▶▶▶▶▶▶▶▶▶▶▶▶▶▶▶▶▶▶▶▶▶▶▶▶▶▶▶▶▶▶▶▶▶
		//早退時間を計算
		int leaveEarlyTime = leaveEarlyTimeSheet.lengthAsMinutes();
		//控除時間の計算（仮作成）
		int deductionTime = 0;
		//遅刻時間から控除時間を控除する
		leaveEarlyTime -= deductionTime;
		//丸め処理（未作成）		
		//▶▶▶▶▶▶▶▶▶▶▶▶▶▶▶▶▶▶▶▶▶▶▶▶▶▶3.遅刻時間帯を再度補正▶▶▶▶▶▶▶▶▶▶▶▶▶▶▶▶▶▶▶▶▶▶▶▶▶▶▶▶▶▶▶▶▶▶▶▶▶▶▶▶▶▶▶▶▶▶▶▶▶▶▶▶▶▶▶▶▶
		//終了から丸め後の早退時間分を減算した時刻を求める
		leaveEarlyTimeSheet.getEnd().backByMinutes(leaveEarlyTime);
		//全ての控除時間帯を取得しソート（未作成）	
		//控除時間帯分ループ（仮作成）
		for(TimeSheetOfDeductionItem deduTimeSheet : deductionTimeSheet.getForDeductionTimeZoneList()) {
			//計算範囲内の時間帯を取得（早退時間帯と控除時間帯の重複している時間帯を取得）
			//控除時間の計算
			//控除時間分、終了時刻を後ろにズラす		
		}
		//▶▶▶▶▶▶▶▶▶▶▶▶▶▶▶▶▶▶▶▶▶▶▶▶▶▶4.丸め設定を保持（未作成）
		return leaveEarlyTimeSheet;
	}
	
	
}



