package nts.uk.ctx.at.record.dom.dailyprocess.calc;

import lombok.RequiredArgsConstructor;
import nts.uk.ctx.at.shared.dom.common.time.AttendanceTime;
import nts.uk.ctx.at.shared.dom.common.time.TimeSpanForCalc;
import nts.uk.ctx.at.shared.dom.worktime.fixedworkset.timespan.TimeSpanWithRounding;
import nts.uk.ctx.at.shared.dom.worktime.fluidworkset.fluidbreaktimeset.FluidOverTimeWorkSheet;
import nts.uk.ctx.at.shared.dom.worktime.fluidworkset.fluidbreaktimeset.FluidWorkTimeSetting;
import nts.uk.shr.com.time.TimeWithDayAttr;

/**
 * 残業枠時間帯
 * @author keisuke_hoshina
 *
 */
public class OverTimeWorkFrameTimeSheet extends CalculationTimeSheet{
	/**残業時間帯NO**/
	private final int frameNo;
	
	private final OverTimeWorkFrameTime overWorkFrameTime;
	
	private final boolean goEarly;
	
	public OverTimeWorkFrameTimeSheet(
			TimeSpanWithRounding timesheet
			,int frameNo
			,TimeSpanForCalc calculationTimeSheet
			,OverTimeWorkFrameTime overTimeWorkFrameTime
			,boolean goEarly) {
		super(timesheet,calculationTimeSheet);
		this.frameNo = frameNo;
		this.overWorkFrameTime = overTimeWorkFrameTime;
		this.goEarly = goEarly;
	}
	
	/**
	 *残業枠時間帯の作成
	 * @param frameNo
	 * @param timeSpanWithRounding
	 * @return
	 */
	public static OverTimeWorkFrameTimeSheet createOverWorkFramTimeSheet(TimeSpanWithRounding timeSpanWithRounding) {
		return new OverTimeWorkFrameTimeSheet(getFrameNO(),timeSpanWithRounding.startValue(),timeSpanWithRounding.endValue());
	}
	/**
	 * 残業枠時間帯の作成(法定内時間帯と判断された場合)
	 * @param frameNo
	 * @param timeSpanWithRounding
	 * @return
	 */
	public static OverTimeWorkFrameTimeSheet createOverWorkFramTimeSheet() {
		return new OverTimeWorkFrameTimeSheet(getFrameNO(),getStart(),getEnd(),true);
	}
	
	private int getStart() {
		return start;
	}
	
	private int getEnd() {
		return end;
	}
	
	private int getFrameNO() {
		return frameNo;
	}
	/**
	 * 分割後　就内と判断された場合
	 * @param frameNo
	 * @return
	 */
	public static OverWorkFrameTimeSheet devideAfterJudgeWorkTime(int limit) {
		return new OverWorkFrameTimeSheet(getFrameNO(),getStart(),getStart()+limit,true);
	}
	
	/**
	 * 分割後はみ出した時間
	 * @param frameNo
	 * @return
	 */
	public static OverWorkFrameTimeSheet devideAfterJudgeOutOfWorkTime(int limit) {
		return new OverWorkFrameTimeSheet(getFrameNO(),getStart()+limit,getEnd(),false);
	}
	public OverTimeHourSet(
			int workingHoursTimeNo,
			TimeSpanWithRounding timeSheet,
			TimeSpanForCalc calculationTimeSheet) {
		
		super(timeSheet, calculationTimeSheet);
	}
	
	//＊＊＊＊＊＊＊＊＊＊＊＊＊＊＊＊＊＊＊＊＊＊＊＊＊＊＊＊＊＊＊＊＊＊＊＊＊＊＊＊＊＊＊＊＊＊＊＊＊＊＊
	
	//控除時間から残業時間帯を作成
	public OverTimeWorkFrameTimeSheet createOverTimeWorkFrameTimeSheet(
			FluidOverTimeWorkSheet fluidOverTimeWorkSheet,
			DeductionTimeSheet deductionTimeSheet,
			TimeWithDayAttr startClock,/*残業枠の開始時刻*/
			AttendanceTime nextElapsedTime/*残業枠ｎ+1．経過時間*/
			) {
		//今回のループで処理する経過時間
		AttendanceTime elapsedTime = fluidOverTimeWorkSheet.getFluidWorkTimeSetting().getElapsedTime();
		//残業枠の時間を計算する  (残業枠ｎ+1．経過時間　-　残業枠n．経過時間)
		AttendanceTime overTimeWorkFrameTime = new AttendanceTime(nextElapsedTime.valueAsMinutes()-elapsedTime.valueAsMinutes());
		//残業枠時間から終了時刻を計算する
		TimeWithDayAttr endClock = startClock.backByMinutes(overTimeWorkFrameTime.valueAsMinutes());
		//残業枠時間帯を作成する
		TimeSpanForCalc overTimeWorkFrameTimeSheet = new TimeSpanForCalc(startClock,endClock);
		//控除時間帯分ループ
		for(TimeSheetOfDeductionItem timeSheetOfDeductionItem : deductionTimeSheet.getForDeductionTimeZoneList()) {
			TimeSpanForCalc duplicateTime = overTimeWorkFrameTimeSheet.getDuplicatedWith(timeSheetOfDeductionItem.getTimeSheet().getSpan()).orElse(null);
			if(duplicateTime!=null) {
				//控除項目の時間帯に法定内区分をセット
				timeSheetOfDeductionItem = timeSheetOfDeductionItem.reateBreakTimeSheetAsFixed(
						timeSheetOfDeductionItem.getTimeSheet().getSpan(),
						timeSheetOfDeductionItem.getGoOutReason(),
						timeSheetOfDeductionItem.getBreakAtr(),
						timeSheetOfDeductionItem.getDeductionAtr(),
						WithinStatutoryAtr.WithinStatutory);
				//控除時間分、終了時刻を遅くする
				TimeSpanForCalc collectTimeSheet = this.timeSheet.shiftEndBack(duplicateTime.lengthAsMinutes());
				TimeSpanWithRounding newTimeSheet = this.timeSheet;
				newTimeSheet.newTimeSpan(collectTimeSheet);
			}
		}
		//控除時間帯に丸め設定を付与
		
		//加給時間帯を作成
		
		//深夜時間帯を作成
		
		
		OverTimeWorkFrameTimeSheet overTimeWorkFrameTimeSheet = new OverTimeWorkFrameTimeSheet();
		return overTimeWorkFrameTimeSheet;
	}

}
