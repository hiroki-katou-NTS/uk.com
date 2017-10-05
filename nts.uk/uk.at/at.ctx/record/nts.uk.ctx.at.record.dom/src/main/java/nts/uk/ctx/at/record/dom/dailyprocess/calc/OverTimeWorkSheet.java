package nts.uk.ctx.at.record.dom.dailyprocess.calc;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import lombok.RequiredArgsConstructor;
import lombok.Value;
import nts.uk.ctx.at.record.dom.daily.AttendanceLeavingWork;
import nts.uk.ctx.at.record.dom.daily.OverTimeWorkOfDaily;
import nts.uk.ctx.at.record.dom.dailyprocess.calc.DeductionAtr;
import nts.uk.ctx.at.record.dom.dailyprocess.calc.DeductionTimeSheet;
import nts.uk.ctx.at.record.dom.dailyprocess.calc.withinstatutory.WithinWorkTimeSheet;
import nts.uk.ctx.at.shared.dom.common.DailyTime;
import nts.uk.ctx.at.shared.dom.common.time.AttendanceTime;
import nts.uk.ctx.at.shared.dom.common.time.BreakdownTimeDay;
import nts.uk.ctx.at.shared.dom.common.time.TimeSpanForCalc;
import nts.uk.ctx.at.shared.dom.employment.statutory.worktime.employment.WorkingSystem;
import nts.uk.ctx.at.shared.dom.worktime.CommomSetting.OverWorkSet.StatutoryOverTimeWorkSet;
import nts.uk.ctx.at.shared.dom.worktime.fixedworkset.OverTimeHourSet;
import nts.uk.ctx.at.shared.dom.worktime.fluidworkset.fluidbreaktimeset.FluidOverTimeWorkSheet;
import nts.uk.ctx.at.shared.dom.worktime.fluidworkset.fluidbreaktimeset.FluidWorkTimeSetting;
import nts.uk.shr.com.time.TimeWithDayAttr;

/**
 * 残業時間帯
 * @author keisuke_hoshina
 *
 */
public class OverTimeWorkSheet {
	
	private OverTimeWorkOfDaily dailyOverWorkTime; 
	
	private StatutoryOverTimeWorkSet statutoryOverWorkSet;
	
	private DailyTime dailyTime;
	
	private  OverTimeWorkSheet(OverTimeWorkOfDaily dailyOverWork) {
		this.dailyOverWorkTime = dailyOverWork;
	}
	
	
	/**
	 * 残業枠分ループし残業枠時間帯の作成
	 * @param overTimeHourSet
	 * @param workingSystem
	 * @return
	 */
	public static OverTimeWorkSheet createOverWorkFrame(List<OverTimeHourSet> overTimeHourSet,WorkingSystem workingSystem,
												AttendanceLeavingWork attendanceLeave,TimeWithDayAttr secondStartTime,int workNo) {
		OverTimeWorkSheet overTimeWorkSheet = new OverTimeWorkSheet(OverTimeWorkOfDaily.of(overTimeHourSet,attendanceLeave,secondStartTime,workNo));
		overTimeWorkSheet = dicisionCalcVariableWork(workingSystem,overTimeWorkSheet);
		return 
	}
	
	/**
	 * 変形基準内時間の計算をするか判定
	 * @param workingSystem 労働制
	 */
	public OverTimeWorkSheet dicisionCalcVariableWork(WorkingSystem workingSystem,OverTimeWorkSheet overTimeWorkSheet
															,BreakdownTimeDay breakdownTimeDay,DailyTime dailyTime) {
		if(workingSystem.isVariableWorkingTimeWork()) {
			/*基準内残業時間を計算する*/
			hurikaesyori(calcDeformationCriterionOvertime(dailyTime.valueAsMinutes(),breakdownTimeDay.getPredetermineWorkTime()));
			/*振替処理*/
		}
		return overTimeWorkSheet;
	}
	/**
	 * 法定内残業時間の計算をするか判定
	 * @param statutoryOverWorkSet 法定内残業設定クラス
	 */
	public OverTimeWorkSheet calcStatutory(StatutoryOverTimeWorkSet statutorySet,DailyTime dailyTime) {
		if(statutorySet.isAutoCalcStatutoryOverWork()) {
			/*法定内基準時間を計算する*/
			hurikaesyori(calcDeformationCriterionOvertime(dailyTime.valueAsMinutes(),calcOverTimeWork()));
			/*振替処理*/
		}
	}

	
	/**
	 * 振替処理
	 * @param oneRange
	 */
	public void hurikaesyori(int ableRangeTime) {
		int overTime = 0;
		/*残業時間帯分のループ*/
		for(OverTimeWorkFrameTimeSheet overTimeWork : dailyOverWorkTime.getOverTimeWorkFrameTimeSheet()) {
			overTime = calcOverTimeWorkSheet(DeductionTimeSheet,overTimeWork);
			overTimeWork.
			if(overTime<0) {
				break;
			}
		}
	}

	/**
	 * 振替にできる時間の計算
	 * @param statutoryTime 法定労働時間
	 * @param 
	 * @return 変形基準内残業にできるじかん
	 */
	public  int calcDeformationCriterionOvertime(int statutoryTime,int predetermineWorkTime) {
		return statutoryTime - predetermineWorkTime;
	}
	

	
	/**
	 * 残業時間枠の計算
	 */
	public void calcOverWorkTime(int limitTime) {
		List<OverTimeWorkFrameTimeSheet> copyList = dailyOverWorkTime.getOverTimeWorkFrameTimeSheet();
		dailyOverWorkTime.getOverTimeWorkFrameTimeSheet().clear();
		
		for(OverTimeWorkFrameTimeSheet overWorkFrameTimeSheet :copyList){
			
			if(overWorkFrameTimeSheet.OverWorkFrameTime.limitgreaterhanOverWorkTime(limitTime)) {
				dailyOverWorkTime.getOverWorkTimeSheet().add(overWorkFrameTimeSheet.createOverWorkFramTimeSheet());
			}
			else {
				dailyOverWorkTime.getOverTimeWorkFrameTimeSheet().add(overWorkFrameTimeSheet.devideAfterJudgeWorkTime(limitTime));
				dailyOverWorkTime.getOverTimeWorkFrameTimeSheet().add(overWorkFrameTimeSheet.devideAfterJudgeOutOfWorkTime(limitTime));
			}
			limitTime = overWorkFrameTimeSheet.OverWorkFrameTime.calcLimit(limitTime);
			if(limitTime ==0) {
				break;
			}
		}
	}
	
	/**
	 * 残業時間枠時間帯から控除時間を差し引いた時間を算出する
	 * @param dedTimeSheet　控除時間帯クラス
	 * @param overTimeWorkFrameTimeSheet　残業枠時間帯クラス
	 * @return　残業時間
	 */
	public int calcOverTimeWorkSheet(DeductionTimeSheet dedTimeSheet,OverTimeWorkFrameTimeSheet overTimeWorkFrameTimeSheet) {
		int dedTime = dedTimeSheet.calcDeductionAllTimeSheet(, overTimeWorkFrameTimeSheet.getTimeSheet().getSpan());
		int overTimeWork = overTimeWorkFrameTimeSheet.getTimeSheet().getSpan().lengthAsMinutes() - dedTime;
		return overTimeWork;
	}
	
	/**
	 * 残業時間の計算(残業時間帯の合計の時間を取得し1日の範囲に返す)
	 * @return
	 */
	public int calcOverTimeWork() {
		int overTimeWorkTime = 0;
		return overTimeWorkTime;
	}
	
	
	
	//＊＊＊＊＊＊＊＊＊＊＊＊＊＊＊＊＊＊＊＊＊＊＊＊＊＊＊＊＊＊＊＊＊＊＊＊＊＊＊＊＊＊＊＊＊＊＊＊＊＊＊

	/**
	 * 流動勤務（就外、平日）
	 * @return
	 */
	public OverTimeWorkSheet createOverTimeWorkSheet(
			TimeSpanForCalc calcRange,/*1日の計算範囲の計算範囲*/
			WithinWorkTimeSheet withinWorkTimeSheet,/*流動勤務（平日・就内）で作成した就業時間内時間帯*/
			DeductionTimeSheet deductionTimeSheet,
			FluidWorkTimeSetting fluidWorkTimeSetting
			) {
		
		//計算範囲の取得
		TimeSpanForCalc timeSpan = new TimeSpanForCalc(
				withinWorkTimeSheet.getFrameAt(0).getTimeSheet().getEnd(),
				calcRange.getEnd());
		
		//控除時間帯を取得　（保科くんが作ってくれた処理を呼ぶ）
		
		//残業枠の開始時刻
		TimeWithDayAttr startClock = calcRange.getStart();
		//残業枠設定分ループ
		for(FluidOverTimeWorkSheet fluidOverTimeWorkSheet: fluidWorkTimeSetting.overTimeWorkSheet) {
			//残業枠n+1の経過時間を取得
			AttendanceTime nextElapsedTime = getnextElapsedTime(
					fluidOverTimeWorkSheet,
					fluidWorkTimeSetting,
					new AttendanceTime(calcRange.lengthAsMinutes()));
			//控除時間から残業時間帯を作成
			OverTimeWorkFrameTimeSheet overTimeWorkFrameTimeSheet;
			
			
			//次の残業枠の開始時刻に終了時刻を入れる。
			startClock = overTimeWorkFrameTimeSheet.getTimeSheet().getEnd();
		}
		//時間休暇溢れ分の割り当て
			
		
	}
	
	
	/**
	 * 残業枠ｎ+1．経過時間を取得する
	 * @param fluidOverTimeWorkSheet
	 * @param fluidWorkTimeSetting
	 * @param timeOfCalcRange
	 * @return
	 */
	public AttendanceTime getnextElapsedTime(
			FluidOverTimeWorkSheet fluidOverTimeWorkSheet,
			FluidWorkTimeSetting fluidWorkTimeSetting,
			AttendanceTime timeOfCalcRange) {
		int nextOverWorkTimeNo = fluidOverTimeWorkSheet.getOverWorkTimeNo() + 1;
		AttendanceTime nextlapsedTime;
		Optional<FluidOverTimeWorkSheet> nextFluidOverTimeWorkSheet = 
				fluidWorkTimeSetting.getMatchWorkNoOverTimeWorkSheet(nextOverWorkTimeNo);
		if(nextFluidOverTimeWorkSheet==null) {
			nextlapsedTime = timeOfCalcRange;
			return nextlapsedTime;
		}
		nextlapsedTime = nextFluidOverTimeWorkSheet.get().getFluidWorkTimeSetting().getElapsedTime();
		return nextlapsedTime;
	}
	
	
	
	
}
