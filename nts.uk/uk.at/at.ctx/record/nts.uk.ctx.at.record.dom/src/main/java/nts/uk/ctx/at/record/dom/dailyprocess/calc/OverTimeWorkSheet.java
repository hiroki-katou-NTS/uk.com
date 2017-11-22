package nts.uk.ctx.at.record.dom.dailyprocess.calc;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collector;
import java.util.stream.Collectors;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Value;
import lombok.val;
import nts.gul.util.value.Finally;
import nts.uk.ctx.at.record.dom.daily.ExcessOfStatutoryMidNightTime;
import nts.uk.ctx.at.record.dom.daily.ExcessOverTimeWorkMidNightTime;
import nts.uk.ctx.at.record.dom.daily.OverTimeWorkOfDaily;
import nts.uk.ctx.at.record.dom.daily.overtimework.enums.StatutoryAtr;
import nts.uk.ctx.at.record.dom.dailyprocess.calc.DeductionTimeSheet;
import nts.uk.ctx.at.record.dom.dailyprocess.calc.withinstatutory.WithinWorkTimeSheet;
import nts.uk.ctx.at.shared.dom.common.DailyTime;
import nts.uk.ctx.at.shared.dom.common.time.AttendanceTime;
import nts.uk.ctx.at.shared.dom.common.time.BreakdownTimeDay;
import nts.uk.ctx.at.shared.dom.common.time.TimeSpanForCalc;
import nts.uk.ctx.at.shared.dom.employment.statutory.worktime.employment.WorkingSystem;
import nts.uk.ctx.at.shared.dom.workrule.outsideworktime.AutoCalculationOfOverTimeWork;
import nts.uk.ctx.at.shared.dom.workrule.outsideworktime.OverDayEndCalcSet;
import nts.uk.ctx.at.shared.dom.workrule.overtimework.StatutoryPrioritySet;
import nts.uk.ctx.at.shared.dom.worktime.CommomSetting.OverWorkSet.StatutoryOverTimeWorkSet;
import nts.uk.ctx.at.shared.dom.worktime.fixedworkset.OverTimeHourSet;
import nts.uk.ctx.at.shared.dom.worktime.fluidworkset.fluidbreaktimeset.FluidOverTimeWorkSheet;
import nts.uk.ctx.at.shared.dom.worktime.fluidworkset.fluidbreaktimeset.FluidWorkTimeSetting;
import nts.uk.ctx.at.shared.dom.worktime.fixedworkset.WorkTimeCommonSet;
import nts.uk.ctx.at.shared.dom.worktype.WorkType;
import nts.uk.shr.com.time.TimeWithDayAttr;

/**
 * 残業時間帯
 * @author keisuke_hoshina
 *
 */

public class OverTimeWorkSheet {
	
	@Getter
	private static OverTimeWorkOfDaily overWorkTimeOfDaily; 
	
	public  OverTimeWorkSheet(OverTimeWorkOfDaily dailyOverWorkTime) {
		this.overWorkTimeOfDaily = dailyOverWorkTime;
	}
	
	/**
	 * 分割後の残業時間枠時間帯を受け取り
	 * @param insertList　補正した時間帯
	 * @param originList　補正する前の時間帯
	 * @return　
	 */
	public static List<OverTimeWorkFrameTimeSheet> correctTimeSpan(List<OverTimeWorkFrameTimeSheet> insertList,List<OverTimeWorkFrameTimeSheet> originList,int nowNumber){
		originList.remove(nowNumber);
		originList.addAll(insertList);
		return originList;
	}
	
	
	/**
	 * 残業時間の計算(残業時間帯の合計の時間を取得し1日の範囲に返す)
	 * @return
	 */
	public static OverTimeWorkOfDaily calcOverTimeWork(AutoCalculationOfOverTimeWork autoCalcSet) {
		ControlOverFrameTime returnClass = new ControlOverFrameTime(overWorkTimeOfDaily.collectOverTimeWorkTime(autoCalcSet));
		
		overWorkTimeOfDaily.addToList(returnClass);
		
		return  overWorkTimeOfDaily;
	}
	
	/**
	 * 深夜時間計算後の時間帯再作成
	 * @return
	 */
	public OverTimeWorkSheet reCreateToCalcExcessWork(OverTimeWorkSheet overTimeWorkSheet,AutoCalculationOfOverTimeWork autoCalcSet) {
		ExcessOverTimeWorkMidNightTime midNightTime = overTimeWorkSheet.overWorkTimeOfDaily.calcMidNightTimeIncludeOverTimeWork(autoCalcSet);
		OverTimeWorkOfDaily overTimeWorkOfDaily = new OverTimeWorkOfDaily(overTimeWorkSheet.overWorkTimeOfDaily.getOverTimeWorkFrameTimeSheet(),
																		  overTimeWorkSheet.overWorkTimeOfDaily.getOverTimeWorkFrameTime(),
																		  Finally.of(midNightTime));
		return new OverTimeWorkSheet(overTimeWorkOfDaily);
	}
	
	//＊＊＊＊＊＊＊＊＊＊＊＊＊＊＊＊＊＊＊＊＊＊＊＊＊＊＊＊＊＊＊＊＊＊＊＊＊＊＊＊＊＊＊＊＊＊＊＊＊＊＊

//	/**
//	 * 流動勤務（就外、平日）
//	 * @return
//	 */
//	public OverTimeWorkSheet createOverTimeWorkSheet(
//			TimeSpanForCalc calcRange,/*1日の計算範囲の計算範囲*/
//			WithinWorkTimeSheet withinWorkTimeSheet,/*流動勤務（平日・就内）で作成した就業時間内時間帯*/
//			DeductionTimeSheet deductionTimeSheet,
//			FluidWorkTimeSetting fluidWorkTimeSetting
//			) {
//		
//		//計算範囲の取得
//		TimeSpanForCalc timeSpan = new TimeSpanForCalc(
//				withinWorkTimeSheet.getFrameAt(0).getTimeSheet().getEnd(),
//				calcRange.getEnd());
//		
//		//控除時間帯を取得　（保科くんが作ってくれた処理を呼ぶ）
//		
//		//残業枠の開始時刻
//		TimeWithDayAttr startClock = calcRange.getStart();
//		//残業枠設定分ループ
//		for(FluidOverTimeWorkSheet fluidOverTimeWorkSheet: fluidWorkTimeSetting.overTimeWorkSheet) {
//			//残業枠n+1の経過時間を取得
////			AttendanceTime nextElapsedTime = getnextElapsedTime(
////					fluidOverTimeWorkSheet,
////					fluidWorkTimeSetting,
////					new AttendanceTime(calcRange.lengthAsMinutes()));
//			//控除時間から残業時間帯を作成
//			OverTimeWorkFrameTimeSheet overTimeWorkFrameTimeSheet;
//			
//			
//			//次の残業枠の開始時刻に終了時刻を入れる。
//			startClock = overTimeWorkFrameTimeSheet.getTimeSheet().getEnd();
//		}
//		//時間休暇溢れ分の割り当て
//			
//		
//	}
	
	
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
