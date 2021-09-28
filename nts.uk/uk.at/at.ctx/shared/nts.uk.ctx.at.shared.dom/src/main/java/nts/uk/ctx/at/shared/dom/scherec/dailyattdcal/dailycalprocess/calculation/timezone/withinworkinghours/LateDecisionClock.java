package nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailycalprocess.calculation.timezone.withinworkinghours;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import lombok.Value;
import lombok.val;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.attendancetime.TimeLeavingWork;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.dailyattendancework.IntegrationOfDaily;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailycalprocess.calculation.DeductionTimeSheet;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailycalprocess.calculation.PredetermineTimeSetForCalc;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailycalprocess.calculation.TimeSpanForDailyCalc;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailycalprocess.calculation.timezone.deductiontime.TimeSheetOfDeductionItem;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailycalprocess.ootsuka.OotsukaStaticService;
import nts.uk.ctx.at.shared.dom.worktime.IntegrationOfWorkTime;
import nts.uk.ctx.at.shared.dom.worktime.common.EmTimeZoneSet;
import nts.uk.ctx.at.shared.dom.worktime.common.GraceTimeSetting;
import nts.uk.ctx.at.shared.dom.worktime.common.LateEarlyAtr;
import nts.uk.ctx.at.shared.dom.worktime.fixedset.FixHalfDayWorkTimezone;
import nts.uk.ctx.at.shared.dom.worktime.fixedset.FixedWorkSetting;
import nts.uk.ctx.at.shared.dom.worktime.flexset.CoreTimeSetting;
import nts.uk.ctx.at.shared.dom.worktime.predset.TimezoneUse;
import nts.uk.ctx.at.shared.dom.worktime.service.WorkTimeForm;
import nts.uk.ctx.at.shared.dom.worktype.AttendanceHolidayAttr;
import nts.uk.ctx.at.shared.dom.worktype.WorkType;
import nts.uk.shr.com.time.TimeWithDayAttr;

/**
 * 遅刻判断時刻
 * @author ken_takasu
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
	 * @param predetermineTimeSet 計算用所定時間設定
	 * @param integrationOfWorkTime 統合就業時間帯
	 * @param integrationOfDaily 日別実績(Work)
	 * @param timeLeavingWork 出退勤
	 * @param workType 勤務種類
	 * @param deductionTimeSheet 控除時間帯
	 * @return 遅刻判断時刻
	 */
	public static Optional<LateDecisionClock> create(
			int workNo,
			PredetermineTimeSetForCalc predetermineTimeSet,
			IntegrationOfWorkTime integrationOfWorkTime,
			IntegrationOfDaily integrationOfDaily,
			TimeLeavingWork timeLeavingWork,
			WorkType workType,
			DeductionTimeSheet deductionTimeSheet) {

		// 所定時間設定の取得
		Optional<TimezoneUse> predetermineTimeSheet = predetermineTimeSet.getTimeSheets(workType.getDailyWork().decisionNeedPredTime(),workNo);
		if (!predetermineTimeSheet.isPresent()) return Optional.empty();
		
		TimeWithDayAttr decisionClock = new TimeWithDayAttr(0);		// 遅刻判断時刻

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
				List<TimeSheetOfDeductionItem> breakTimeSheetList = deductionTimeSheet.getForDeductionTimeZoneList()
						.stream().filter(t -> t.getDeductionAtr().isBreak()).collect(Collectors.toList());
				// 大塚モードの休憩時間帯取得
				breakTimeSheetList.addAll(OotsukaStaticService.getBreakTimeSheet(
						workType, integrationOfWorkTime, integrationOfDaily.getAttendanceLeave()));
				
				//控除時間帯(休憩)と猶予時間帯の重複を調べ猶予時間帯の調整
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
	 * @param predetermineTimeSet 所定時間設定
	 * @param timeLeavingWork 出退勤
	 * @param integrationOfWorkTime 統合就業時間帯
	 * @param predetermineTimeSetForCalc 計算用所定時間帯
	 * @param attr 出勤休日区分
	 * @return 計算範囲
	 */
	static public Optional<TimeSpanForDailyCalc> getCalcRange(
			TimezoneUse predetermineTimeSet,
			TimeLeavingWork timeLeavingWork,
			IntegrationOfWorkTime integrationOfWorkTime,
			PredetermineTimeSetForCalc predetermineTimeSetForCalc,
			AttendanceHolidayAttr attr)
	{
		Optional<TimeSpanForDailyCalc> result = Optional.empty();
		
		// 出勤時刻の取得
		TimeWithDayAttr attendance = null;
		if(timeLeavingWork.getAttendanceStamp().isPresent()) {
			if(timeLeavingWork.getAttendanceStamp().get().getStamp().isPresent()) {
				if(timeLeavingWork.getAttendanceStamp().get().getStamp().get().getTimeDay().getTimeWithDay().isPresent()) {
					attendance =  timeLeavingWork.getAttendanceStamp().get().getStamp().get().getTimeDay().getTimeWithDay().get();
				}
			}
		}
		if (attendance != null){
			result = Optional.of(new TimeSpanForDailyCalc(predetermineTimeSet.getStart(), attendance));
			// 勤務形態を取得する
			WorkTimeForm workTimeform = integrationOfWorkTime.getWorkTimeSetting().getWorkTimeDivision().getWorkTimeForm();
			switch (workTimeform){
			case FLEX:		// フレックス勤務
				CoreTimeSetting coreTimeSetting = integrationOfWorkTime.getFlexWorkSetting().get().getCoreTimeSetting();
				// コアタイム使用するかどうか
				if(coreTimeSetting.getTimesheet().isNOT_USE()) {
					result = Optional.empty();
				}
				else {
					val coreTime = coreTimeSetting.getDecisionCoreTimeSheet(attr, predetermineTimeSetForCalc.getAMEndTime(),predetermineTimeSetForCalc.getPMStartTime());
					if(attendance.greaterThanOrEqualTo(coreTime.getEndTime())) {
						result = Optional.of(new TimeSpanForDailyCalc(coreTime.getStartTime(), coreTime.getEndTime()));
					}
					else {
						result = Optional.of(new TimeSpanForDailyCalc(coreTime.getStartTime(), attendance));
					}
				}
				break;
			case FIXED:		// 固定勤務
				// 固定勤務の計算範囲の取得
				result = getCalcRangeForFixed(predetermineTimeSet, attendance, integrationOfWorkTime, attr);
				break;
			case FLOW:		// 流動勤務
				if(attendance.greaterThanOrEqualTo(predetermineTimeSet.getEnd())) {
					result = Optional.of(new TimeSpanForDailyCalc(predetermineTimeSet.getStart(), predetermineTimeSet.getEnd()));
				}
				break;
			default:
				break;
			}
		}
		if(!result.isPresent() || result.get().isReverse() || result.get().isEqual()) {
			return Optional.empty();
		}
		return result;
	}
	
	/**
	 * 固定勤務の遅刻時間の計算範囲の取得
	 * @param predetermineTimeSet 所定時間設定
	 * @param attendance 出勤時刻
	 * @param integrationOfWorkTime 統合就業時間帯
	 * @param attr 出勤休日区分
	 * @return 計算範囲
	 */
	private static Optional<TimeSpanForDailyCalc> getCalcRangeForFixed(
			TimezoneUse predetermineTimeSet,
			TimeWithDayAttr attendance,
			IntegrationOfWorkTime integrationOfWorkTime,
			AttendanceHolidayAttr attr){
		
		// 所定時間内に含まれる就業時間帯を取得
		Optional<FixedWorkSetting> fixedSetOpt = integrationOfWorkTime.getFixedWorkSetting();
		if (!fixedSetOpt.isPresent()) return Optional.empty();
		Optional<FixHalfDayWorkTimezone> workTimeSetOpt = fixedSetOpt.get().getFixHalfDayWorkTimezone(attr);
		if (!workTimeSetOpt.isPresent()) return Optional.empty();
		List<EmTimeZoneSet> workTimeZoneList = workTimeSetOpt.get().getWorkTimezone()
				.getWorkTimeSpanWithinPred(predetermineTimeSet);
		if (workTimeZoneList.size() <= 0) return Optional.empty();
		// 時間帯を作成
		TimeWithDayAttr minTime = null;
		for (EmTimeZoneSet workTimeZone : workTimeZoneList){
			TimeWithDayAttr start = workTimeZone.getTimezone().getStart();
			if (minTime == null){
				minTime = start;
				continue;
			}
			if (minTime.greaterThan(start)) minTime = start;
		}
		if (minTime == null) return Optional.empty();
		if (minTime.greaterThanOrEqualTo(attendance)){
			return Optional.empty();
		}
		return Optional.of(new TimeSpanForDailyCalc(minTime, attendance));
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
