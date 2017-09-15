package nts.uk.ctx.at.record.dom.dailyprocess.calc.record.mekestimesheet;

import java.util.Optional;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.val;
import nts.uk.ctx.at.record.dom.dailyprocess.calc.DeductionTimeSheet;
import nts.uk.ctx.at.record.dom.dailyprocess.calc.TimeSheetOfDeductionItem;
import nts.uk.ctx.at.record.dom.dailyprocess.calc.withinstatutory.WithinWorkTimeSheet;
import nts.uk.ctx.at.shared.dom.common.time.TimeSpanForCalc;
import nts.uk.ctx.at.shared.dom.worktime.WorkTime;
import nts.uk.ctx.at.shared.dom.worktime.CommomSetting.SpecifiedTimeSheetSetting;
import nts.uk.ctx.at.shared.dom.worktime.CommonSetting.lateleaveearly.LateLeaveEarlyClassification;
import nts.uk.ctx.at.shared.dom.worktime.fixedworkset.WorkTimeCommonSet;
import nts.uk.shr.com.time.TimeWithDayAttr;

/**
 * 遅刻時間帯
 * @author keisuke_hoshina
 *
 */
public class LateTimeSheet {
	
	@Getter
	private TimeSpanForCalc forRecordTimeSheet;
	@Getter
	private TimeSpanForCalc forDeducationTimeSheet;
	
	
	public LateTimeSheet(TimeSpanForCalc recordTimeSheet,TimeSpanForCalc deductionTimeSheet) {
		this.forRecordTimeSheet = recordTimeSheet;
		this.forDeducationTimeSheet = deductionTimeSheet;
	}
	
	
	/**
	 * 遅刻時間の計算
	 * @param specifiedTimeSheet
	 * @param goWorkTime
	 * @param workNo
	 * @param classification
	 * @param lateDecisionClock
	 * @return
	 */
	
	public static Optional<LateTimeSheet> lateTimeCalc(SpecifiedTimeSheetSetting specifiedTimeSheet,TimeWithDayAttr goWorkTime,int workNo
			,WorkTimeCommonSet workTimeCommonSet,WithinWorkTimeSheet withinWorkTimeSheet, WorkTime workTime,DeductionTimeSheet deductionTimeSheet) {	
		//出勤時刻と遅刻判断時刻を比較	
		if(goWorkTime.greaterThan(withinWorkTimeSheet.getlateDecisionClock(workNo).getLateDecisionClock())/*猶予時間考慮した上で遅刻の場合*/
				||workTimeCommonSet.getGraceTimeSet(LateLeaveEarlyClassification.LATE).isIncludeInWorkingHours()){//猶予時間を加算しない場合
			TimeSpanForCalc forDeduct = new TimeSpanForCalc(lateTimeSheetCreate(goWorkTime, workTime, deductionTimeSheet,workNo/*,控除区分:控除*/).getStart(),
					lateTimeSheetCreate(goWorkTime, workTime, deductionTimeSheet,workNo/*,控除区分:控除*/).getEnd());
			TimeSpanForCalc forRec = new TimeSpanForCalc(lateTimeSheetCreate(goWorkTime, workTime, deductionTimeSheet,workNo/*,控除区分:計上*/).getStart()
					,lateTimeSheetCreate(goWorkTime, workTime, deductionTimeSheet,workNo/*,控除区分:計上*/).getEnd());		

			//休暇時間相殺処理(未作成)
			//遅刻猶予加算時間←0：00（未作成）
			
			return Optional.of(new LateTimeSheet(forRec,forDeduct));
		}	
		return null;//遅刻していない場合はNULLでよい？
	}
	
	/**
	 * 遅刻時間帯作成
	 * @param span 控除用時間帯または計上用時間帯
	 * @return 控除用または計上用の遅刻時間帯
	 */
	public static TimeSpanForCalc lateTimeSheetCreate(TimeWithDayAttr goWorkTime, WorkTime workTime,DeductionTimeSheet deductionTimeSheet,int workNo/*,控除区分*/) {
		//▶▶▶▶▶▶▶▶▶▶▶▶▶▶▶▶▶▶▶▶▶▶▶▶▶▶▶▶▶▶1.遅刻時間帯の作成▶▶▶▶▶▶▶▶▶▶▶▶▶▶▶▶▶▶▶▶▶▶▶▶▶▶▶▶▶▶▶▶▶▶▶▶▶▶▶▶▶▶▶▶▶▶▶▶▶▶▶▶▶▶
		//計算範囲の取得
		val calcRange = workTime.getLateTimeCalcRange(goWorkTime,workNo);
		//遅刻時間帯の開始時刻の作成
		TimeWithDayAttr start = calcRange.getStart();
		//遅刻時間帯の終了時刻の作成
		TimeWithDayAttr end;
		if(goWorkTime.lessThanOrEqualTo(calcRange.getEnd())) {
			end = goWorkTime;
		}else {
			end = calcRange.getEnd();
		}
		TimeSpanForCalc lateTimeSheet = new TimeSpanForCalc(start, end);
		//▶▶▶▶▶▶▶▶▶▶▶▶▶▶▶▶▶▶▶▶▶▶▶▶▶▶▶▶2.遅刻時間を計算▶▶▶▶▶▶▶▶▶▶▶▶▶▶▶▶▶▶▶▶▶▶▶▶▶▶▶▶▶▶▶▶▶▶▶▶▶▶▶▶▶▶▶▶▶▶▶▶▶▶▶▶▶▶▶▶▶▶
		//遅刻時間を計算
		int lateTime = lateTimeSheet.lengthAsMinutes();
		//控除時間の計算（仮作成）
		int deductionTime = 0;
		//遅刻時間から控除時間を控除する
		lateTime -= deductionTime;
		//丸め処理（未作成）		
		//▶▶▶▶▶▶▶▶▶▶▶▶▶▶▶▶▶▶▶▶▶▶▶▶▶▶3.遅刻時間帯を再度補正▶▶▶▶▶▶▶▶▶▶▶▶▶▶▶▶▶▶▶▶▶▶▶▶▶▶▶▶▶▶▶▶▶▶▶▶▶▶▶▶▶▶▶▶▶▶▶▶▶▶▶▶▶▶▶▶▶
		//開始から丸め後の遅刻時間分を加算した時刻を求める
		lateTimeSheet.getStart().forwardByMinutes(lateTime);
		//全ての控除時間帯を取得しソート（未作成）	
		//控除時間帯分ループ（仮作成）
		for(TimeSheetOfDeductionItem deduTimeSheet : deductionTimeSheet.getForDeductionTimeZoneList()) {
			//計算範囲内の時間帯を取得（遅刻時間帯と控除時間帯の重複している時間帯を取得）
			//控除時間の計算
			//控除時間分、終了時刻を後ろにズラす		
		}
		//▶▶▶▶▶▶▶▶▶▶▶▶▶▶▶▶▶▶▶▶▶▶▶▶▶▶4.丸め設定を保持（未作成）
		return lateTimeSheet;
	}
	
	
//	public void calcLateTime(計上控除区分) {
//	}
	
}
