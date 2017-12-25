package nts.uk.ctx.at.record.dom.dailyprocess.calc.withinstatutory;

import java.util.ArrayList;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Value;
import lombok.val;
import nts.uk.ctx.at.record.dom.dailyprocess.calc.DeductionTimeSheet;
import nts.uk.ctx.at.shared.dom.worktime.common.GraceTimeSetting;
import nts.uk.ctx.at.shared.dom.worktimeset_old.WorkTimeSet;
import nts.uk.shr.com.time.TimeWithDayAttr;

/**
 * 早退判断時刻
 * @author ken_takasu
 *
 */
@AllArgsConstructor
@Value
public class LeaveEarlyDecisionClock {
	private  TimeWithDayAttr leaveEarlyDecisionClock;
	private int workNo;
	
	
	public static LeaveEarlyDecisionClock create(
			int workNo, WorkTimeSet predetermineTimeSet,
			DeductionTimeSheet deductionTimeSheet,
			GraceTimeSetting leaveEarlyGraceTime) {
		
		val predetermineTimeSheet = predetermineTimeSet.getTimeSheetOf(workNo);
		TimeWithDayAttr decisionClock;

		if (leaveEarlyGraceTime.isZero()) {
			// 猶予時間が0：00の場合、所定時間の終了時刻を判断時刻にする
			decisionClock = predetermineTimeSheet.getEnd();
		} else {
//			// 猶予時間帯の作成
//			TimeSpanForCalc graceTimeSheet = leaveEarlyGraceTime.createLeaveEarlyGraceTimeSheet(predetermineTimeSheet);
//			//猶予時間帯に重複する休憩時間帯の時間を取得する
//			int breakTime = deductionTimeSheet.sumBreakTimeIn(graceTimeSheet);
			//猶予時間帯の開始時間を控除時間と重複する時間分ズラした時刻を早退判断時刻とする
			//decisionClock = graceTimeSheet.getStart().backByMinutes(breakTime);
			decisionClock = new TimeWithDayAttr(0);
		}

		// 補正後の猶予時間帯の開始時刻を判断時刻とする
		return new LeaveEarlyDecisionClock(decisionClock, workNo);
	}
	
	/**
	 * 全勤務回数（最大２回）分の早退判断時刻のリストを作る
	 * @param predetermineTimeSet
	 * @param deductionTimeSheet
	 * @param leaveEarlyGraceTime
	 * @return
	 */
	public static List<LeaveEarlyDecisionClock> createListOfAllWorks(
			WorkTimeSet predetermineTimeSet,
			DeductionTimeSheet deductionTimeSheet,
			GraceTimeSetting leaveEarlyGraceTime) {

		List<LeaveEarlyDecisionClock> clocks = new ArrayList<LeaveEarlyDecisionClock>();
		for (int workNo = 1; workNo < 3; workNo++) {// 勤務回数分ループ
			// 早退猶予時間の取得
			clocks.add(LeaveEarlyDecisionClock.create(workNo, predetermineTimeSet, deductionTimeSheet, leaveEarlyGraceTime));
		}
		
		return clocks;
	}
	
	/**
	 * 早退しているか判断する
	 * @param time
	 * @return
	 */
	public boolean isLeaveEarly(TimeWithDayAttr time) {
		return time.lessThan(this.leaveEarlyDecisionClock);
	}
	
}







