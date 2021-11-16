package nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailycalprocess.calculation.timezone.withinworkinghours;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import lombok.AllArgsConstructor;
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
 * 早退判断時刻
 * @author ken_takasu
 */
@AllArgsConstructor
@Value
public class LeaveEarlyDecisionClock {
	//早退判断時刻
	private  TimeWithDayAttr leaveEarlyDecisionClock;
	//勤務No
	private int workNo;

	/**
	 * 早退判断時刻の取得
	 * @param workNo 勤務No
	 * @param predetermineTimeSet 計算用所定時間設定
	 * @param integrationOfWorkTime 統合就業時間帯
	 * @param integrationOfDaily 日別実績(Work)
	 * @param timeLeavingWork 出退勤
	 * @param workType 勤務種類
	 * @param deductionTimeSheet 控除時間帯
	 * @return 早退判断時刻
	 */
	public static Optional<LeaveEarlyDecisionClock> create(
			int workNo,
			PredetermineTimeSetForCalc predetermineTimeSet,
			IntegrationOfWorkTime integrationOfWorkTime,
			IntegrationOfDaily integrationOfDaily,
			TimeLeavingWork timeLeavingWork,
			WorkType workType,
			DeductionTimeSheet deductionTimeSheet) {
		
		// 所定時間設定の取得
		val predetermineTimeSheet = predetermineTimeSet.getTimeSheets(workType.getDailyWork().decisionNeedPredTime(),workNo);
		if(!predetermineTimeSheet.isPresent()) return Optional.empty();
		
		TimeWithDayAttr decisionClock = new TimeWithDayAttr(0);
		
		//計算範囲の取得
		Optional<TimeSpanForDailyCalc> calｃRange = getCalcRange(predetermineTimeSheet.get(),timeLeavingWork,integrationOfWorkTime,predetermineTimeSet,workType.getDailyWork().decisionNeedPredTime());
		if (calｃRange.isPresent()) {
			GraceTimeSetting graceTimeSetting = integrationOfWorkTime.getCommonSetting().getLateEarlySet().getOtherEmTimezoneLateEarlySet(LateEarlyAtr.EARLY).getGraceTimeSet();
			
			if(graceTimeSetting.isZero()) {
				// 猶予時間が0：00の場合、所定時間の終了時刻を判断時刻にする
				decisionClock = calｃRange.get().getEnd();
			} else {
				// 猶予時間帯の作成
				TimeSpanForDailyCalc graceTimeSheet = new TimeSpanForDailyCalc(calｃRange.get().getEnd().backByMinutes(graceTimeSetting.getGraceTime().valueAsMinutes()),
																	 calｃRange.get().getEnd());
				// 重複している控除分をずらす(休憩)
				List<TimeSheetOfDeductionItem> breakTimeSheetList = deductionTimeSheet.getForDeductionTimeZoneList()
						.stream().filter(t -> t.getDeductionAtr().isBreak()).collect(Collectors.toList());
				// 大塚モードの休憩時間帯取得
				breakTimeSheetList.addAll(OotsukaStaticService.getBreakTimeSheet(
						workType, integrationOfWorkTime, integrationOfDaily.getAttendanceLeave()));
				// 逆順ソート
				breakTimeSheetList = breakTimeSheetList.stream()
						.sorted((first,second) -> second.getTimeSheet().getStart().compareTo(first.getTimeSheet().getStart()))
						.collect(Collectors.toList());
				
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
	 * @param predetermineTimeSet 所定時間設定
	 * @param timeLeavingWork 出退勤
	 * @param workTime 統合就業時間帯
	 * @param predetermineTimeSetForCalc 計算用所定時間設定
	 * @param attr 出勤休日区分
	 * @return 計算範囲
	 */
	static public Optional<TimeSpanForDailyCalc> getCalcRange(
			TimezoneUse predetermineTimeSet,
			TimeLeavingWork timeLeavingWork,
			IntegrationOfWorkTime workTime,
			PredetermineTimeSetForCalc predetermineTimeSetForCalc,
			AttendanceHolidayAttr attr)
	{
		Optional<TimeSpanForDailyCalc> result = Optional.empty();
		
		// 退勤時刻の取得
		TimeWithDayAttr leave = null;
		if(timeLeavingWork.getLeaveStamp().isPresent()) {
			if(timeLeavingWork.getLeaveStamp().get().getStamp().isPresent()) {
				if(timeLeavingWork.getLeaveStamp().get().getStamp().get().getTimeDay().getTimeWithDay().isPresent()) {
					leave =  timeLeavingWork.getLeaveStamp().get().getStamp().get().getTimeDay().getTimeWithDay().get();
				}
			}
		}
		if (leave != null){
			result = Optional.of(new TimeSpanForDailyCalc(leave, predetermineTimeSet.getEnd()));
			// フレックス勤務かどうか判断
			if(workTime.getWorkTimeSetting().getWorkTimeDivision().isFlex()) {
				// フレックス勤務
				CoreTimeSetting coreTimeSetting = workTime.getFlexWorkSetting().get().getCoreTimeSetting();
				
				// コアタイム使用するかどうか
				if(coreTimeSetting.getTimesheet().isNOT_USE()) {
					result = Optional.empty();
				}
				val coreTime = coreTimeSetting.getDecisionCoreTimeSheet(attr, predetermineTimeSetForCalc.getAMEndTime(),predetermineTimeSetForCalc.getPMStartTime());
				if(leave.lessThanOrEqualTo(coreTime.getStartTime())) {
					result = Optional.of(new TimeSpanForDailyCalc(coreTime.getStartTime(),coreTime.getEndTime()));
				}
				result = Optional.of(new TimeSpanForDailyCalc(leave,coreTime.getEndTime()));
			}
			// 勤務形態を取得する
			WorkTimeForm workTimeform = workTime.getWorkTimeSetting().getWorkTimeDivision().getWorkTimeForm();
			if (workTimeform.isFixed()){
				// 固定勤務
				// 固定勤務の計算範囲の取得
				result = getCalcRangeForFixed(predetermineTimeSet, leave, workTime, attr);
			}
			// 流動勤務
			if(workTimeform.isFlow()) {
				if(leave.lessThanOrEqualTo(predetermineTimeSet.getStart())) {
					result = Optional.of(new TimeSpanForDailyCalc(predetermineTimeSet.getStart(),predetermineTimeSet.getEnd()));
				}
			}
		}
		if(!result.isPresent() || result.get().isReverse() || result.get().isEqual()) {
			return Optional.empty();
		}
		return result;
	}
	
	/**
	 * 固定勤務の早退時間の計算範囲の取得
	 * @param predetermineTimeSet 所定時間設定
	 * @param leave 退勤時刻
	 * @param integrationOfWorkTime 統合就業時間帯
	 * @param attr 出勤休日区分
	 * @return 計算範囲
	 */
	private static Optional<TimeSpanForDailyCalc> getCalcRangeForFixed(
			TimezoneUse predetermineTimeSet,
			TimeWithDayAttr leave,
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
		TimeWithDayAttr maxTime = null;
		for (EmTimeZoneSet workTimeZone : workTimeZoneList){
			TimeWithDayAttr end = workTimeZone.getTimezone().getEnd();
			if (maxTime == null){
				maxTime = end;
				continue;
			}
			if (maxTime.lessThan(end)) maxTime = end;
		}
		if (maxTime == null) return Optional.empty();
		if (maxTime.lessThanOrEqualTo(leave)){
			return Optional.empty();
		}
		return Optional.of(new TimeSpanForDailyCalc(leave, maxTime));
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







