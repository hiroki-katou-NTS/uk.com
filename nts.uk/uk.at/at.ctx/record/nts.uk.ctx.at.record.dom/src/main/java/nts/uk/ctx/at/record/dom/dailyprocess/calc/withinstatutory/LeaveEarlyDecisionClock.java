package nts.uk.ctx.at.record.dom.dailyprocess.calc.withinstatutory;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import lombok.AllArgsConstructor;
import lombok.Value;
import lombok.val;
import nts.uk.ctx.at.record.dom.dailyprocess.calc.DeductionTimeSheet;
import nts.uk.ctx.at.record.dom.dailyprocess.calc.PredetermineTimeSetForCalc;
import nts.uk.ctx.at.record.dom.worktime.TimeLeavingWork;
import nts.uk.ctx.at.shared.dom.common.time.TimeSpanForCalc;
import nts.uk.ctx.at.shared.dom.worktime.common.GraceTimeSetting;
import nts.uk.ctx.at.shared.dom.worktime.flexset.CoreTimeSetting;
import nts.uk.ctx.at.shared.dom.worktime.predset.PredetemineTimeSetting;
import nts.uk.ctx.at.shared.dom.worktime.predset.TimezoneUse;
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
			int workNo,
			PredetermineTimeSetForCalc predetermineTimeSet,
			DeductionTimeSheet deductionTimeSheet,
			GraceTimeSetting leaveEarlyGraceTime,
			TimeLeavingWork timeLeavingWork,
			Optional<CoreTimeSetting> coreTimeSetting) {
		
		val predetermineTimeSheet = predetermineTimeSet.getTimeSheets(workNo);
		TimeWithDayAttr decisionClock = new TimeWithDayAttr(0);
		
		//計算範囲の取得
		TimeSpanForCalc calｃRange = getCalcRange(predetermineTimeSheet,timeLeavingWork.getTimespan().getEnd(),coreTimeSetting);
		if (calｃRange!=null) {
			// 猶予時間が0：00の場合、所定時間の終了時刻を判断時刻にする
			decisionClock = calｃRange.getEnd();
		} else {
			// 猶予時間帯の作成
			TimeSpanForCalc graceTimeSheet = new TimeSpanForCalc(predetermineTimeSet.getTimeSheets().get(workNo).getEnd().forwardByMinutes(leaveEarlyGraceTime.getGraceTime().minute()),
																 predetermineTimeSet.getTimeSheets().get(workNo).getEnd());
			
//			//猶予時間帯に重複する休憩時間帯の時間を取得する
//			int breakTime = deductionTimeSheet.sumBreakTimeIn(graceTimeSheet);
			//猶予時間帯の開始時間を控除時間と重複する時間分ズラした時刻を早退判断時刻とする
			//decisionClock = graceTimeSheet.getStart().backByMinutes(breakTime);
			decisionClock = graceTimeSheet.getStart();
		}

		// 補正後の猶予時間帯の開始時刻を判断時刻とする
		return new LeaveEarlyDecisionClock(decisionClock, workNo);
	}
	
	/**
	 * 早退時間の計算範囲の取得
	 * @param predetermineTimeSet
	 * @param timeLeavingWork
	 * @return
	 */
	static public TimeSpanForCalc getCalcRange(TimezoneUse predetermineTimeSet,TimeWithDayAttr leave,Optional<CoreTimeSetting> coreTimeSetting)
	{
		//フレックス勤務では無い場合の計算範囲
		TimeSpanForCalc result = new TimeSpanForCalc(leave, predetermineTimeSet.getEnd());
		//計算範囲を取得
		
		//フレ勤務かどうか判断
		if(coreTimeSetting.isPresent()) {
			//コアタイム使用するかどうか
			if(coreTimeSetting.get().getTimesheet().isNOT_USE()) {
				result = null;
			}
			result = new TimeSpanForCalc(leave,coreTimeSetting.get().getCoreTimeSheet().getEndTime());
		}	
		return result;
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







