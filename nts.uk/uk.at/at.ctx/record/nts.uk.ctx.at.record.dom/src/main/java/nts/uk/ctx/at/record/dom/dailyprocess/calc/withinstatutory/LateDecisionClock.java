package nts.uk.ctx.at.record.dom.dailyprocess.calc.withinstatutory;

import java.util.ArrayList;
import java.util.List;

import lombok.Value;
import lombok.val;
import nts.uk.ctx.at.record.dom.dailyprocess.calc.DeductionTimeSheet;
import nts.uk.ctx.at.shared.dom.worktime.common.GraceTimeSetting;
import nts.uk.ctx.at.shared.dom.worktime.predset.PredetemineTimeSetting;
import nts.uk.shr.com.time.TimeWithDayAttr;

/**
 * 遅刻判断時刻
 * 
 * @author ken_takasu
 *
 */
@Value
public class LateDecisionClock {
	private TimeWithDayAttr lateDecisionClock;
	private int workNo;

	
	/**
	 * 遅刻判断時刻を作成する
	 * @param workNo
	 * @param predetermineTimeSet
	 * @param deductionTimeSheet
	 * @param lateGraceTime
	 * @return
	 */
	public static LateDecisionClock create(
			int workNo, PredetemineTimeSetting predetermineTimeSet,
			DeductionTimeSheet deductionTimeSheet,
			GraceTimeSetting lateGraceTime) {

		val predetermineTimeSheet = predetermineTimeSet.getTimeSheetOf(workNo);
		TimeWithDayAttr decisionClock = new TimeWithDayAttr(0);

		if (lateGraceTime.isZero()) {
			// 猶予時間が0：00の場合、所定時間の開始時刻を判断時刻にする
			decisionClock = predetermineTimeSheet.getStart();
		} else {
			// 猶予時間帯の作成
			//TimeSpanForCalc graceTimeSheet = lateGraceTime.createLateGraceTimeSheet(predetermineTimeSheet);

//			int breakTime = deductionTimeSheet.sumBreakTimeIn(graceTimeSheet);
//			decisionClock = graceTimeSheet.getEnd().forwardByMinutes(breakTime);
		}

		//補正後の猶予時間帯の開始時刻を判断時刻とする
		return new LateDecisionClock(decisionClock, workNo);
	}

	/**
	 * 全勤務回数（最大２回）分の遅刻判断時刻のリストを作る
	 * @param predetermineTimeSet
	 * @param deductionTimeSheet
	 * @param lateGraceTime
	 * @return
	 */
	public static List<LateDecisionClock> createListOfAllWorks(
			PredetemineTimeSetting predetermineTimeSet,
			DeductionTimeSheet deductionTimeSheet,
			GraceTimeSetting lateGraceTime) {

		List<LateDecisionClock> clocks = new ArrayList<LateDecisionClock>();
		for (int workNo = 1; workNo < 3; workNo++) {// 勤務回数分ループ　→　workNoは引数として渡すように修正
			// 遅刻猶予時間の取得
			clocks.add(LateDecisionClock.create(workNo, predetermineTimeSet, deductionTimeSheet, lateGraceTime));
		}
		
		return clocks;
	}
	
	/**
	 * 遅刻しているか判断する
	 * @param time
	 * @return
	 */
	public boolean isLate(TimeWithDayAttr time) {
		return time.greaterThan(this.lateDecisionClock);
	}
}
