package nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailycalprocess.calculation.timezone.withinworkinghours;

import java.util.List;
import java.util.Optional;

import lombok.Value;
import lombok.val;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.attendancetime.TimeLeavingWork;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailycalprocess.calculation.PredetermineTimeSetForCalc;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailycalprocess.calculation.TimeSpanForDailyCalc;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailycalprocess.calculation.timezone.deductiontime.TimeSheetOfDeductionItem;
import nts.uk.ctx.at.shared.dom.worktime.IntegrationOfWorkTime;
import nts.uk.ctx.at.shared.dom.worktime.common.GraceTimeSetting;
import nts.uk.ctx.at.shared.dom.worktime.common.LateEarlyAtr;
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
	//遅刻判断時刻
	private TimeWithDayAttr lateDecisionClock;
	//勤務No
	private int workNo;

	
	/**
	 * 遅刻判断時刻を作成する
	 * @param workNo 勤務No
	 * @param predetermineTimeSet 所定時間帯
	 * @param deductionTimeSheet　控除時間帯
	 * @param lateGraceTime　遅刻猶予時間
	 * @param breakTimeList 
	 * @return
	 */
	public static Optional<LateDecisionClock> create(
			int workNo,
			PredetermineTimeSetForCalc predetermineTimeSet,
			IntegrationOfWorkTime integrationOfWorkTime,
			TimeLeavingWork timeLeavingWork,
			WorkType workType, 
			List<TimeSheetOfDeductionItem> breakTimeList) {


		Optional<TimezoneUse> predetermineTimeSheet = predetermineTimeSet.getTimeSheets(workType.getDailyWork().decisionNeedPredTime(),workNo);
		if(!predetermineTimeSheet.isPresent())
			return Optional.empty();
		TimeWithDayAttr decisionClock = new TimeWithDayAttr(0);

		//計算範囲取得
		Optional<TimeSpanForDailyCalc> calｃRange = getCalcRange(predetermineTimeSheet.get(),timeLeavingWork,integrationOfWorkTime,predetermineTimeSet,workType.getDailyWork().decisionNeedPredTime());
		if(calｃRange.isPresent()) {
			GraceTimeSetting graceTimeSetting = integrationOfWorkTime.getCommonSetting().getLateEarlySet().getOtherEmTimezoneLateEarlySet(LateEarlyAtr.LATE).getGraceTimeSet();
			
			if (graceTimeSetting.isZero()) {
				// 猶予時間が0：00の場合、所定時間の開始時刻を判断時刻にする
				decisionClock = calｃRange.get().getStart();
			} else {
				// 猶予時間帯の作成                                                                                                   
				TimeSpanForDailyCalc graceTimeSheet = new TimeSpanForDailyCalc(calｃRange.get().getStart(),
																	 calｃRange.get().getStart().forwardByMinutes(graceTimeSetting.getGraceTime().valueAsMinutes()));
				// 重複している控除分をずらす(休憩)
				List<TimeSheetOfDeductionItem> breakTimeSheetList = breakTimeList;
				//EnterPriseでは短時間系との重複はとっていないためコメントアウト
//				List<TimeZoneRounding> breakTimeSheetList = deductionTimeSheet.getForDeductionTimeZoneList().stream().filter(tc -> tc.getDeductionAtr().isBreak() || tc.getDeductionAtr().isChildCare()).map(t -> t.getTimeSheet()).collect(Collectors.toList());
				
				//控除時間帯(休憩＆短時間)と猶予時間帯の重複を調べ猶予時間帯の調整
				for(TimeSheetOfDeductionItem breakTime:breakTimeSheetList) {
					TimeSpanForDailyCalc deductTime = new TimeSpanForDailyCalc(breakTime.getTimeSheet().getStart(),breakTime.getTimeSheet().getEnd());
					val dupRange = deductTime.getDuplicatedWith(graceTimeSheet);
						if(dupRange.isPresent()) {
							graceTimeSheet = new TimeSpanForDailyCalc(graceTimeSheet.getStart(), 
																 graceTimeSheet.getEnd().forwardByMinutes(breakTime.calcTotalTime().valueAsMinutes()));
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
	static public Optional<TimeSpanForDailyCalc> getCalcRange(TimezoneUse predetermineTimeSet,
														 TimeLeavingWork timeLeavingWork,
														 IntegrationOfWorkTime integrationOfWorkTime,
														 PredetermineTimeSetForCalc predetermineTimeSetForCalc,AttendanceHolidayAttr attr)
	{
		//出勤時刻
		TimeWithDayAttr attendance = null;
		if(timeLeavingWork.getAttendanceStamp().isPresent()) {
			if(timeLeavingWork.getAttendanceStamp().get().getStamp().isPresent()) {
				if(timeLeavingWork.getAttendanceStamp().get().getStamp().get().getTimeDay().getTimeWithDay().isPresent()) {
					attendance =  timeLeavingWork.getAttendanceStamp().get().getStamp().get().getTimeDay().getTimeWithDay().get();
				}
			}
		}
		//フレックス勤務では無い場合の計算範囲
		Optional<TimeSpanForDailyCalc> result = Optional.empty();
		if(attendance!=null) {
			result = Optional.of(new TimeSpanForDailyCalc(predetermineTimeSet.getStart(), attendance));
			//フレ勤務かどうか判断
			if(integrationOfWorkTime.getWorkTimeSetting().getWorkTimeDivision().isFlex()) {
				CoreTimeSetting coreTimeSetting = integrationOfWorkTime.getFlexWorkSetting().get().getCoreTimeSetting();
				
				//コアタイム使用するかどうか
				if(coreTimeSetting.getTimesheet().isNOT_USE()) {
					return Optional.empty();
				}
				val coreTime = coreTimeSetting.getDecisionCoreTimeSheet(attr, predetermineTimeSetForCalc.getAMEndTime(),predetermineTimeSetForCalc.getPMStartTime());
				if(attendance.greaterThanOrEqualTo(coreTime.getEndTime())) {
					return Optional.of(new TimeSpanForDailyCalc(coreTime.getStartTime(), coreTime.getEndTime()));
				}
				return Optional.of(new TimeSpanForDailyCalc(coreTime.getStartTime(), attendance));
			}
			if(attendance.greaterThanOrEqualTo(predetermineTimeSet.getEnd())) {
				result = Optional.of(new TimeSpanForDailyCalc(predetermineTimeSet.getStart(), predetermineTimeSet.getEnd()));
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
