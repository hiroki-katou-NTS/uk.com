package nts.uk.ctx.at.record.dom.dailyprocess.calc;

import java.util.Optional;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.val;
import nts.uk.ctx.at.record.dom.dailyprocess.calc.withinstatutory.LateDecisionClock;
import nts.uk.ctx.at.record.dom.dailyprocess.calc.withinstatutory.WithinWorkTimeFrame;
import nts.uk.ctx.at.record.dom.dailyprocess.calc.withinstatutory.WithinWorkTimeSheet;
import nts.uk.ctx.at.shared.dom.common.time.TimeSpanForCalc;
import nts.uk.ctx.at.shared.dom.worktime.WorkTime;
import nts.uk.ctx.at.shared.dom.worktime.CommomSetting.PredetermineTimeSheetSetting;
import nts.uk.ctx.at.shared.dom.worktime.CommonSetting.lateleaveearly.LateLeaveEarlyClassification;
import nts.uk.ctx.at.shared.dom.worktime.fixedworkset.WorkTimeCommonSet;
import nts.uk.ctx.at.shared.dom.worktime.fixedworkset.timespan.TimeSpanWithRounding;
import nts.uk.shr.com.time.TimeWithDayAttr;

/**
 * 遅刻時間帯
 * @author keisuke_hoshina
 *
 */
public class LateTimeSheet {
	
	// 遅刻していない場合はempty
	
	private Optional<TimeSpanForCalc> forRecordTimeSheet;
	
	@Getter
	private Optional<TimeSpanForCalc> forDeducationTimeSheet;
	
	
	private LateTimeSheet(
			Optional<TimeSpanForCalc> recordTimeSheet,
			Optional<TimeSpanForCalc> deductionTimeSheet) {
		
		this.forRecordTimeSheet = recordTimeSheet;
		this.forDeducationTimeSheet = deductionTimeSheet;
	}
	
	public static LateTimeSheet createAsLate(TimeSpanForCalc timeSheet) {
		return new LateTimeSheet(Optional.of(timeSheet), Optional.of(timeSheet));
	}
	
	public static LateTimeSheet createAsNotLate() {
		return new LateTimeSheet(Optional.empty(), Optional.empty());
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
	public static LateTimeSheet lateTimeCalc(
			WithinWorkTimeFrame withinWorkTimeFrame,
			TimeSpanForCalc lateRangeForCalc,
			WorkTimeCommonSet workTimeCommonSet,
			LateDecisionClock lateDecisionClock,
			DeductionTimeSheet deductionTimeSheet) {
		
		TimeWithDayAttr goWorkTime = lateRangeForCalc.getEnd();
		
		//出勤時刻と遅刻判断時刻を比較	
		if(lateDecisionClock.isLate(goWorkTime)/*猶予時間考慮した上で遅刻の場合*/
				||!workTimeCommonSet.getLateSetting().getGraceTimeSetting().isIncludeInWorkingHours()){//猶予時間を加算しない場合
			
			return withinWorkTimeFrame.getTimeSheet().getDuplicatedWith(lateRangeForCalc)
					.map(initialLateTimeSheet -> {
						val revisedLateTimeSheet = reviceLateTimeSheet(initialLateTimeSheet, deductionTimeSheet);
						
						//休暇時間相殺処理(未作成)
						
						//遅刻猶予加算時間←0：00（未作成）
						
						return LateTimeSheet.createAsLate(revisedLateTimeSheet);
					})
					.orElse(LateTimeSheet.createAsNotLate());
		}
		
		return LateTimeSheet.createAsNotLate();//遅刻していない
	}
	
	/**
	 * 遅刻時間帯作成
	 * @param goWorkTime
	 * @param workTime
	 * @param deductionTimeSheet
	 * @param workNo
	 * @param deductionAtr
	 * @return 控除用または計上用の早退時間帯
	 */
	private static TimeSpanForCalc reviceLateTimeSheet(
			TimeSpanForCalc lateTimeSheet,
			DeductionTimeSheet deductionTimeSheet) {
		//遅刻時間を計算
		int lateTime = getLateTime(lateTimeSheet, deductionTimeSheet);
		//遅刻時間帯を再度補正
		lateTimeSheet = getCorrectedLateTimeSheet(lateTimeSheet, lateTime, deductionTimeSheet);		
		//丸め設定を保持（未作成）
		
		return lateTimeSheet;
	}

	/**
	 * 遅刻時間の計算
	 * @param lateTimeSpan
	 * @param deductionTimeSheet
	 * @return　遅刻時間
	 */
	public static int getLateTime(TimeSpanForCalc lateTimeSpan,DeductionTimeSheet deductionTimeSheet) {
		//遅刻時間を計算
		int lateTime = lateTimeSpan.lengthAsMinutes();

		//遅刻時間の取得（未作成）	
		
		//控除時間の計算（未作成）	
		
		//遅刻時間から控除時間を控除する（未作成）	
		//lateTime -= deductionTime;

		//丸め処理（未作成）		
		
		return lateTime;
	}
	
	/**
	 * 遅刻時間帯を再度補正
	 * @param lateTimeSheet
	 * @param lateTime
	 * @param deductionTimeSheet
	 * @return 補正後の遅刻時間帯
	 */
	public static TimeSpanForCalc getCorrectedLateTimeSheet(TimeSpanForCalc lateTimeSheet,int lateTime,DeductionTimeSheet deductionTimeSheet) {
		//開始から丸め後の遅刻時間分を加算した時刻を求める
		lateTimeSheet.getStart().forwardByMinutes(lateTime);
		//全ての控除時間帯を取得しソート（未作成）	

		//控除時間帯分ループ（仮作成）
		for(TimeSheetOfDeductionItem deduTimeSheet : deductionTimeSheet.getForDeductionTimeZoneList()) {
			//計算範囲内の時間帯を取得（遅刻時間帯と控除時間帯の重複している時間帯を取得）
			//控除時間の計算
			//控除時間分、終了時刻を後ろにズラす		
		}
			
		return lateTimeSheet;
	}
	
//	public void calcLateTime(計上控除区分) {
//	}
	
	public int getLateDeductionTime() {
		return this.forDeducationTimeSheet.map(ts -> ts.lengthAsMinutes()).orElse(0);
	}
	
	public boolean isLate() {
		return this.forDeducationTimeSheet.isPresent();
	}
	
	public TimeSpanWithRounding deductForm(TimeSpanWithRounding source) {
		
		if (!this.isLate()) {
			return source;
		}

		//遅刻時間帯の終了時刻を開始時刻にする
		return source.newTimeSpan(
				source.shiftOnlyStart(this.forDeducationTimeSheet.get().getEnd()));
	}
}
