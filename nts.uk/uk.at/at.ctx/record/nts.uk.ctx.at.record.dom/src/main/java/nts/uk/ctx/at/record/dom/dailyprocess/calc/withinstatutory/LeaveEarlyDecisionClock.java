package nts.uk.ctx.at.record.dom.dailyprocess.calc.withinstatutory;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import lombok.AllArgsConstructor;
import lombok.Value;
import lombok.val;
import nts.uk.ctx.at.record.dom.dailyprocess.calc.PredetermineTimeSetForCalc;
import nts.uk.ctx.at.record.dom.dailyprocess.calc.TimeSheetOfDeductionItem;
import nts.uk.ctx.at.record.dom.worktime.TimeLeavingWork;
import nts.uk.ctx.at.record.dom.dailyprocess.calc.TimeSpanForDailyCalc;
import nts.uk.ctx.at.shared.dom.worktime.common.GraceTimeSetting;
import nts.uk.ctx.at.shared.dom.worktime.flexset.CoreTimeSetting;
import nts.uk.ctx.at.shared.dom.worktime.predset.TimezoneUse;
import nts.uk.ctx.at.shared.dom.worktype.AttendanceHolidayAttr;
import nts.uk.ctx.at.shared.dom.worktype.WorkType;
import nts.uk.shr.com.time.TimeWithDayAttr;

/**
 * 早退判断時刻
 * @author ken_takasu
 *
 */
@AllArgsConstructor
@Value
public class LeaveEarlyDecisionClock {
	//早退判断時刻
	private  TimeWithDayAttr leaveEarlyDecisionClock;
	//勤務No
	private int workNo;
	
	
	public static Optional<LeaveEarlyDecisionClock> create(
			int workNo,
			PredetermineTimeSetForCalc predetermineTimeSet,
			GraceTimeSetting leaveEarlyGraceTime,
			TimeLeavingWork timeLeavingWork,
			Optional<CoreTimeSetting> coreTimeSetting,WorkType workType, List<TimeSheetOfDeductionItem> breakTimeList) {
		
		val predetermineTimeSheet = predetermineTimeSet.getTimeSheets(workType.getDailyWork().decisionNeedPredTime(),workNo);
		if(!predetermineTimeSheet.isPresent())
			return Optional.empty();
		TimeWithDayAttr decisionClock = new TimeWithDayAttr(0);
		
		//計算範囲の取得
		Optional<TimeSpanForDailyCalc> calｃRange = getCalcRange(predetermineTimeSheet.get(),timeLeavingWork,coreTimeSetting,predetermineTimeSet,workType.getDailyWork().decisionNeedPredTime());
		if (calｃRange.isPresent()) {
			if(leaveEarlyGraceTime.isZero()) {
				// 猶予時間が0：00の場合、所定時間の終了時刻を判断時刻にする
				decisionClock = calｃRange.get().getEnd();
			} else {
				// 猶予時間帯の作成
				TimeSpanForDailyCalc graceTimeSheet = new TimeSpanForDailyCalc(calｃRange.get().getEnd().backByMinutes(leaveEarlyGraceTime.getGraceTime().valueAsMinutes()),
																	 calｃRange.get().getEnd());
				// 重複している控除分をずらす(短時間・休憩)
				List<TimeSheetOfDeductionItem> breakTimeSheetList = breakTimeList;
				breakTimeSheetList = breakTimeSheetList.stream().sorted((first,second) -> second.getTimeSheet().getStart().compareTo(first.getTimeSheet().getStart())).collect(Collectors.toList());

				
				for(TimeSheetOfDeductionItem breakTime:breakTimeSheetList) {
					TimeSpanForDailyCalc deductTime = new TimeSpanForDailyCalc(breakTime.getTimeSheet().getStart(),breakTime.getTimeSheet().getEnd());
					val dupRange = deductTime.getDuplicatedWith(graceTimeSheet);
					if(dupRange.isPresent()) {
						graceTimeSheet = new TimeSpanForDailyCalc(graceTimeSheet.getStart().backByMinutes(breakTime.calcTotalTime().valueAsMinutes())
															,graceTimeSheet.getEnd());
					}
				}
				decisionClock = graceTimeSheet.getStart();
			}
			// 補正後の猶予時間帯の開始時刻を判断時刻とする
			return Optional.of(new LeaveEarlyDecisionClock(decisionClock, workNo));
		}
		return Optional.empty();
	}
	
	
	/**
	 * 早退時間の計算範囲の取得
	 * @param predetermineTimeSet
	 * @param timeLeavingWork
	 * @return
	 */
	static public Optional<TimeSpanForDailyCalc> getCalcRange(TimezoneUse predetermineTimeSet,
														 TimeLeavingWork timeLeavingWork,
														 Optional<CoreTimeSetting> coreTimeSetting,
														 PredetermineTimeSetForCalc predetermineTimeSetForCalc,AttendanceHolidayAttr attr)
	{
		//退勤時刻
		TimeWithDayAttr leave = null;
		if(timeLeavingWork.getLeaveStamp().isPresent()) {
			if(timeLeavingWork.getLeaveStamp().get().getStamp().isPresent()) {
				if(timeLeavingWork.getLeaveStamp().get().getStamp().get().getTimeWithDay()!=null) {
					leave =  timeLeavingWork.getLeaveStamp().get().getStamp().get().getTimeWithDay();
				}
			}
		}
		
		//フレックス勤務では無い場合の計算範囲
		Optional<TimeSpanForDailyCalc> result = Optional.empty();
		if(leave!=null) {
			result = Optional.of(new TimeSpanForDailyCalc(leave, predetermineTimeSet.getEnd()));
			//フレ勤務かどうか判断
			if(coreTimeSetting.isPresent()) {
				//コアタイム使用するかどうか
				if(coreTimeSetting.get().getTimesheet().isNOT_USE()) {
					return Optional.empty();
				}
				val coreTime = coreTimeSetting.get().getDecisionCoreTimeSheet(attr, predetermineTimeSetForCalc.getAMEndTime(),predetermineTimeSetForCalc.getPMStartTime());
				if(leave.lessThanOrEqualTo(coreTime.getStartTime())) {
					return Optional.of(new TimeSpanForDailyCalc(coreTime.getStartTime(),coreTime.getEndTime()));
				}
				return Optional.of(new TimeSpanForDailyCalc(leave,coreTime.getEndTime()));
			}
			if(leave.lessThanOrEqualTo(predetermineTimeSet.getStart())) {
				result = Optional.of(new TimeSpanForDailyCalc(predetermineTimeSet.getStart(),predetermineTimeSet.getEnd()));
			}
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







