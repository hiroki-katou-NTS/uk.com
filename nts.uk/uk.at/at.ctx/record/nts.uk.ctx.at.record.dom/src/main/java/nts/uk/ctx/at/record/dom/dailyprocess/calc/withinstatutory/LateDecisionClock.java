package nts.uk.ctx.at.record.dom.dailyprocess.calc.withinstatutory;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import lombok.Value;
import lombok.val;
import nts.uk.ctx.at.record.dom.dailyprocess.calc.DeductionTimeSheet;
import nts.uk.ctx.at.record.dom.dailyprocess.calc.PredetermineTimeSetForCalc;
import nts.uk.ctx.at.record.dom.worktime.TimeLeavingWork;
import nts.uk.ctx.at.shared.dom.common.time.TimeSpanForCalc;
import nts.uk.ctx.at.shared.dom.worktime.common.GraceTimeSetting;
import nts.uk.ctx.at.shared.dom.worktime.common.TimeZoneRounding;
import nts.uk.ctx.at.shared.dom.worktime.flexset.CoreTimeSetting;
import nts.uk.ctx.at.shared.dom.worktime.predset.TimezoneUse;
import nts.uk.ctx.at.shared.dom.worktype.AttendanceHolidayAttr;
import nts.uk.ctx.at.shared.dom.worktype.WorkType;
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
	public static Optional<LateDecisionClock> create(
			int workNo,
			PredetermineTimeSetForCalc predetermineTimeSet,
			DeductionTimeSheet deductionTimeSheet,
			GraceTimeSetting lateGraceTime,
			TimeLeavingWork timeLeavingWork,
			Optional<CoreTimeSetting> coreTimeSetting,WorkType workType) {

		Optional<TimezoneUse> predetermineTimeSheet = predetermineTimeSet.getTimeSheets(workType.getDailyWork().decisionNeedPredTime(),workNo);
		if(!predetermineTimeSheet.isPresent())
			return Optional.empty();
//		TimezoneUse predetermineTimeSheet = new TimezoneUse(new TimeWithDayAttr(0), new TimeWithDayAttr(0), UseSetting.NOT_USE , workNo);
		TimeWithDayAttr decisionClock = new TimeWithDayAttr(0);

		//計算範囲取得
		Optional<TimeSpanForCalc> calｃRange = getCalcRange(predetermineTimeSheet.get(),timeLeavingWork,coreTimeSetting,predetermineTimeSet,workType.getDailyWork().decisionNeedPredTime());
		if(calｃRange.isPresent()) {
			if (lateGraceTime.isZero()) {
				// 猶予時間が0：00の場合、所定時間の開始時刻を判断時刻にする
				decisionClock = calｃRange.get().getStart();
			} else {
				// 猶予時間帯の作成                                                                                                   
				TimeSpanForCalc graceTimeSheet = new TimeSpanForCalc(calｃRange.get().getStart(),
																	 calｃRange.get().getStart().forwardByMinutes(lateGraceTime.getGraceTime().valueAsMinutes()));
				// 重複している控除分をずらす
				List<TimeZoneRounding> breakTimeSheetList = deductionTimeSheet.getForDeductionTimeZoneList().stream().filter(t -> t.getDeductionAtr().isBreak()==true).map(t -> t.getTimeSheet()).collect(Collectors.toList());
				for(TimeZoneRounding breakTime:breakTimeSheetList) {
					TimeSpanForCalc deductTime = new TimeSpanForCalc(breakTime.getStart(),breakTime.getEnd());
					if(deductTime.contains(graceTimeSheet.getEnd())){
						graceTimeSheet = new TimeSpanForCalc(graceTimeSheet.getStart(), deductTime.getEnd());
					}
				}
				decisionClock = graceTimeSheet.getEnd();
			}
			//補正後の猶予時間帯の開始時刻を判断時刻とする
			return Optional.of(new LateDecisionClock(decisionClock, workNo));
		}
		return Optional.empty();
	}
	
	/**
	 * 遅刻時間の計算範囲の取得
	 * @param predetermineTimeSet
	 * @param timeLeavingWork
	 * @return
	 */
	static public Optional<TimeSpanForCalc> getCalcRange(TimezoneUse predetermineTimeSet,
														 TimeLeavingWork timeLeavingWork,
														 Optional<CoreTimeSetting> coreTimeSetting,
														 PredetermineTimeSetForCalc predetermineTimeSetForCalc,AttendanceHolidayAttr attr)
	{
		//出勤時刻
		TimeWithDayAttr attendance = null;
		if(timeLeavingWork.getAttendanceStamp().isPresent()) {
			if(timeLeavingWork.getAttendanceStamp().get().getStamp().isPresent()) {
				if(timeLeavingWork.getAttendanceStamp().get().getStamp().get().getTimeWithDay()!=null) {
					attendance =  timeLeavingWork.getAttendanceStamp().get().getStamp().get().getTimeWithDay();
				}
			}
		}
		//フレックス勤務では無い場合の計算範囲
		Optional<TimeSpanForCalc> result = Optional.empty();
		if(attendance!=null) {
			result = Optional.of(new TimeSpanForCalc(predetermineTimeSet.getStart(), attendance));
			//フレ勤務かどうか判断
			if(coreTimeSetting.isPresent()) {
				//コアタイム使用するかどうか
				if(coreTimeSetting.get().getTimesheet().isNOT_USE()) {
					return Optional.empty();
				}
//				if(attendance.greaterThanOrEqualTo(coreTimeSetting.get().getCoreTimeSheet().getEndTime())) {
				val coreTime = coreTimeSetting.get().getDecisionCoreTimeSheet(attr, predetermineTimeSetForCalc.getAMEndTime(),predetermineTimeSetForCalc.getPMStartTime());
				if(attendance.greaterThanOrEqualTo(coreTime.getEndTime())) {
					return Optional.of(new TimeSpanForCalc(coreTime.getStartTime(), coreTime.getEndTime()));
				}
				return Optional.of(new TimeSpanForCalc(coreTime.getStartTime(), attendance));
			}
			if(attendance.greaterThanOrEqualTo(predetermineTimeSet.getEnd())) {
				result = Optional.of(new TimeSpanForCalc(predetermineTimeSet.getStart(), predetermineTimeSet.getEnd()));
			}
		}
		return result;
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
