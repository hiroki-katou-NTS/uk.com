package nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailycalprocess.calculation.timezone.withinworkinghours;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import lombok.Getter;
import nts.uk.ctx.at.shared.dom.common.time.AttendanceTime;
import nts.uk.ctx.at.shared.dom.common.timerounding.TimeRoundingSetting;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.TimevacationUseTimeOfDaily;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.attendancetime.TimeLeavingOfDailyAttd;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.attendancetime.TimeLeavingWork;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.common.TimeActualStamp;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.common.TimeWithCalculation;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.common.timestamp.WorkStamp;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.dailyattendancework.IntegrationOfDaily;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.latetime.LateTimeOfDaily;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailycalprocess.calculation.DeductionOffSetTime;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailycalprocess.calculation.DeductionTimeSheet;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailycalprocess.calculation.PredetermineTimeSetForCalc;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailycalprocess.calculation.TimeSpanForDailyCalc;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailycalprocess.calculation.timezone.deductiontime.DeductionAtr;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailycalprocess.calculation.timezone.deductiontime.TimeSheetOfDeductionItem;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailycalprocess.holidaypriorityorder.CompanyHolidayPriorityOrder;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailycalprocess.ootsuka.OotsukaStaticService;
import nts.uk.ctx.at.shared.dom.worktime.IntegrationOfWorkTime;
import nts.uk.ctx.at.shared.dom.worktime.common.OtherEmTimezoneLateEarlySet;
import nts.uk.ctx.at.shared.dom.worktime.common.WorkTimezoneCommonSet;
import nts.uk.ctx.at.shared.dom.worktime.predset.TimezoneUse;
import nts.uk.ctx.at.shared.dom.worktype.WorkType;
import nts.uk.shr.com.enumcommon.NotUseAtr;
import nts.uk.shr.com.time.TimeWithDayAttr;

/**
 * 早退時間帯
 * @author ken_takasu
 *
 */
@Getter
public class LeaveEarlyTimeSheet {
	
	//計上用遅刻早退時間帯
	//早退していない場合はempty
	@Getter
	private Optional<LateLeaveEarlyTimeSheet> forRecordTimeSheet;
	
	//控除用遅刻早退時間帯
	@Getter
	private Optional<LateLeaveEarlyTimeSheet> forDeducationTimeSheet;

	@Getter
	//勤務No
	private int workNo;
	
	//控除相殺時間
	private Optional<DeductionOffSetTime> OffsetTime;
	
	public LeaveEarlyTimeSheet(
			Optional<LateLeaveEarlyTimeSheet> recordTimeSheet,
			Optional<LateLeaveEarlyTimeSheet> deductionTimeSheet,
			int workNo,
			Optional<DeductionOffSetTime> OffsetTime) {
		
		this.forRecordTimeSheet = recordTimeSheet;
		this.forDeducationTimeSheet = deductionTimeSheet;
		this.workNo = workNo;
		this.OffsetTime = OffsetTime;
	}
	
	public static LeaveEarlyTimeSheet createAsLeaveEarly(LateLeaveEarlyTimeSheet recordTimeSheet,LateLeaveEarlyTimeSheet deductionTimeSheet,int workNo,Optional<LateTimeOfDaily> lateTime,Optional<DeductionOffSetTime> OffsetTime) {
		return new LeaveEarlyTimeSheet(Optional.of(recordTimeSheet), Optional.of(deductionTimeSheet),workNo,OffsetTime);
	}
	
	/**
	 * 指定された区分の時間帯を返す
	 * @param dedAtr
	 * @return
	 */
	public Optional<LateLeaveEarlyTimeSheet> getDecitionTimeSheet(DeductionAtr dedAtr){
		if(dedAtr.isAppropriate()) {
			return this.forRecordTimeSheet;
		}
		return this.forDeducationTimeSheet;
	}
	
	/**
	 * 指定された区分の時間帯に入れる
	 * @param dedAtr
	 * @return
	 */
	public void setDecitionTimeSheet(DeductionAtr dedAtr, Optional<LateLeaveEarlyTimeSheet> forTimeSheet){
		if(dedAtr.isAppropriate()) {
			this.forRecordTimeSheet = forTimeSheet;
		}
		this.forDeducationTimeSheet = forTimeSheet;
	}

	/**
	 * 早退時間帯の作成
	 * @param leaveEarlyDesClock 早退判断時刻
	 * @param timeLeavingWork 出退勤
	 * @param otherEmTimezoneLateEarlySet 就業時間帯の遅刻・早退別設定
	 * @param withinWorkTimeFrame 就業時間内時間枠
	 * @param deductionTimeSheet 控除時間帯
	 * @param predetermineTime 所定時間設定
	 * @param workNo 勤務NO
	 * @param workType 勤務種類
	 * @param predetermineTimeSetForCalc 計算用所定時間設定
	 * @param integrationOfWorkTime 統合就業時間帯
	 * @param integrationOfDaily 日別実績(Work)
	 * @return 早退時間帯
	 */
	public static Optional<LeaveEarlyTimeSheet> createLeaveEarlyTimeSheet(
			Optional<LeaveEarlyDecisionClock> leaveEarlyDesClock,
			TimeLeavingWork timeLeavingWork,
			OtherEmTimezoneLateEarlySet otherEmTimezoneLateEarlySet,
			WithinWorkTimeFrame withinWorkTimeFrame,
			DeductionTimeSheet deductionTimeSheet,
			Optional<TimezoneUse> predetermineTime,
			int workNo,
			WorkType workType,
			PredetermineTimeSetForCalc predetermineTimeSetForCalc,
			IntegrationOfWorkTime integrationOfWorkTime,
			IntegrationOfDaily integrationOfDaily) {
		
		//退勤時刻
		Optional<TimeWithDayAttr> leave = Optional.empty();
		leave = timeLeavingWork.getLeaveTime();
		if(!leave.isPresent() || !leaveEarlyDesClock.isPresent() || !predetermineTime.isPresent()) {
			return Optional.empty();
		}
		
		//早退時間帯
		Optional<LateLeaveEarlyTimeSheet> leaveEarlyAppTimeSheet = Optional.empty();
		//早退控除時間帯
		Optional<LateLeaveEarlyTimeSheet> leaveEarlyDeductTimeSheet = Optional.empty();
		
		//退勤時刻と早退判断時刻を比較	
		if(leaveEarlyDesClock.get().getLeaveEarlyDecisionClock().greaterThan(leave.get())) {
			//早退控除時間帯の作成
			leaveEarlyDeductTimeSheet = createLateLeaveEarlyTimeSheet(
					DeductionAtr.Deduction,
					timeLeavingWork,
					integrationOfWorkTime,
					integrationOfDaily,
					predetermineTime.get(),
					withinWorkTimeFrame,
					deductionTimeSheet,
					workType,
					predetermineTimeSetForCalc,
					otherEmTimezoneLateEarlySet);
			//早退時間帯の作成
			leaveEarlyAppTimeSheet = createLateLeaveEarlyTimeSheet(
					DeductionAtr.Appropriate,
					timeLeavingWork,
					integrationOfWorkTime,
					integrationOfDaily,
					predetermineTime.get(),
					withinWorkTimeFrame,
					deductionTimeSheet,
					workType,
					predetermineTimeSetForCalc,
					otherEmTimezoneLateEarlySet);
		}else {	
			//早退控除時間帯の作成
			leaveEarlyDeductTimeSheet = createLateLeaveEarlyTimeSheet(
					DeductionAtr.Deduction,
					timeLeavingWork,
					integrationOfWorkTime,
					integrationOfDaily,
					predetermineTime.get(),
					withinWorkTimeFrame,
					deductionTimeSheet,
					workType,
					predetermineTimeSetForCalc,
					otherEmTimezoneLateEarlySet);
		}
		if(!leaveEarlyAppTimeSheet.isPresent() && !leaveEarlyDeductTimeSheet.isPresent()) {
			return Optional.empty();
		}
		return Optional.of(new LeaveEarlyTimeSheet(leaveEarlyAppTimeSheet,leaveEarlyDeductTimeSheet, workNo, Optional.empty()));
	}
	
	/**
	 * 作成処理
	 * @param deductionAtr 控除区分
	 * @param timeLeavingWork 出退勤
	 * @param integrationOfWorkTime 統合就業時間帯
	 * @param integrationOfDaily 日別実績(Work)
	 * @param predetermineTimeSet 所定時間帯（使用区分付き）
	 * @param withinWorkTimeFrame 就業時間内時間枠
	 * @param deductionTimeSheet 控除時間帯
	 * @param workType 勤務種類
	 * @param predetermineTimeForSet 所定時間設定
	 * @param otherEmTimezoneLateEarlySet 就業時間帯の遅刻・早退別設定
	 * @return 遅刻早退時間帯
	 */
	private static Optional<LateLeaveEarlyTimeSheet> createLateLeaveEarlyTimeSheet(
			DeductionAtr deductionAtr,
			TimeLeavingWork timeLeavingWork,
			IntegrationOfWorkTime integrationOfWorkTime,
			IntegrationOfDaily integrationOfDaily,
			TimezoneUse predetermineTimeSet,
			WithinWorkTimeFrame withinWorkTimeFrame,
			DeductionTimeSheet deductionTimeSheet,
			WorkType workType,
			PredetermineTimeSetForCalc predetermineTimeForSet,
			OtherEmTimezoneLateEarlySet otherEmTimezoneLateEarlySet){

		// 遅刻早退用控除時間帯
		DeductionTimeSheet deductForLateEarly = new DeductionTimeSheet(
				new ArrayList<>(deductionTimeSheet.getForDeductionTimeZoneList()),
				new ArrayList<>(deductionTimeSheet.getForRecordTimeZoneList()),
				deductionTimeSheet.getBreakTimeOfDailyList(),
				deductionTimeSheet.getDailyGoOutSheet(),
				deductionTimeSheet.getShortTimeSheets());
		// 大塚モードの休憩時間帯取得
		List<TimeSheetOfDeductionItem> ootsukaBreak = OotsukaStaticService.getBreakTimeSheet(
				workType, integrationOfWorkTime, integrationOfDaily.getAttendanceLeave());
		deductForLateEarly.getForDeductionTimeZoneList().addAll(ootsukaBreak);
		deductForLateEarly.getForRecordTimeZoneList().addAll(ootsukaBreak);
		// 早退時間帯を作成する
		Optional<LateLeaveEarlyTimeSheet> instance = createLeaveEarlyTimeSheetInstance(
				deductionAtr,
				timeLeavingWork,
				integrationOfWorkTime,
				predetermineTimeSet,
				withinWorkTimeFrame,
				deductForLateEarly,
				workType,
				predetermineTimeForSet,
				otherEmTimezoneLateEarlySet);

		return instance;
	}
	
	/**
	 * 早退時間帯を作成する
	 * @param deductionAtr 控除区分
	 * @param timeLeavingWork 出退勤
	 * @param integrationOfWorkTime 統合就業時間帯
	 * @param predetermineTimeSet 所定時間帯（使用区分付き）
	 * @param withinWorkTimeFrame 就業時間内時間枠
	 * @param deductTimeSheet 遅刻早退用控除時間帯
	 * @param workType 勤務種類
	 * @param predetermineTimeForSet 所定時間設定
	 * @param otherEmTimezoneLateEarlySet 就業時間帯の遅刻・早退別設定
	 * @return 遅刻早退時間帯
	 */
	private static Optional<LateLeaveEarlyTimeSheet> createLeaveEarlyTimeSheetInstance(
			DeductionAtr deductionAtr,
			TimeLeavingWork timeLeavingWork,
			IntegrationOfWorkTime integrationOfWorkTime,
			TimezoneUse predetermineTimeSet,
			WithinWorkTimeFrame withinWorkTimeFrame,
			DeductionTimeSheet deductTimeSheet,
			WorkType workType,
			PredetermineTimeSetForCalc predetermineTimeForSet,
			OtherEmTimezoneLateEarlySet otherEmTimezoneLateEarlySet){
		
		// 早退終了時刻を取得
		Optional<TimeSpanForDailyCalc> calcRange = LeaveEarlyDecisionClock.getCalcRange(
				predetermineTimeSet, timeLeavingWork, integrationOfWorkTime,
				predetermineTimeForSet, workType.getDailyWork().decisionNeedPredTime());
		if (!calcRange.isPresent() || calcRange.get().isReverse()) return Optional.empty();
		TimeWithDayAttr leaveEndClock = calcRange.get().getEnd();
		// 早退時間を計算する時間帯を判断
		Optional<LateLeaveEarlyTimeSheet> beforeAdjustOpt = checkTimeSheetForCalcLeaveTime(
				deductionAtr, timeLeavingWork, integrationOfWorkTime,
				deductTimeSheet, leaveEndClock, withinWorkTimeFrame, otherEmTimezoneLateEarlySet);
		if (!beforeAdjustOpt.isPresent()) return Optional.empty();
		LateLeaveEarlyTimeSheet beforeAdjust = beforeAdjustOpt.get();
		// 早退時間を計算（丸め前、丸め後）
		AttendanceTime beforeRounding = beforeAdjust.calcTotalTime(NotUseAtr.USE, NotUseAtr.NOT_USE);
		AttendanceTime afterRounding = beforeAdjust.calcTotalTime();
		// 早退開始時刻を取得
		TimeWithDayAttr leaveStartClock = beforeAdjust.getLeaveStartTime(beforeRounding, afterRounding, deductTimeSheet);
		// 遅刻早退時間帯を作成
		LateLeaveEarlyTimeSheet result = new LateLeaveEarlyTimeSheet(
				new TimeSpanForDailyCalc(leaveStartClock, leaveEndClock), beforeAdjust.getRounding());
		// 控除時間帯の登録
		result.registDeductionList(ActualWorkTimeSheetAtrForLate.LeaveEarly,
				deductTimeSheet, integrationOfWorkTime.getCommonSetting());
		// 遅刻早退時間帯を返す
		return Optional.of(result);
	}

	/**
	 * 早退時間を計算する時間帯を判断
	 * @param deductionAtr 控除区分
	 * @param timeLeavingWork 出退勤
	 * @param integrationOfWorkTime 統合就業時間帯
	 * @param deductTimeSheet 控除時間帯
	 * @param leaveEndClock 早退終了時刻
	 * @param withinWorkTimeFrame 就業時間内時間枠
	 * @param otherEmTimezoneLateEarlySet 就業時間帯の遅刻・早退別設定
	 * @return 遅刻早退時間帯
	 */
	private static Optional<LateLeaveEarlyTimeSheet> checkTimeSheetForCalcLeaveTime(
			DeductionAtr deductionAtr,
			TimeLeavingWork timeLeavingWork,
			IntegrationOfWorkTime integrationOfWorkTime,
			DeductionTimeSheet deductTimeSheet,
			TimeWithDayAttr leaveEndClock,
			WithinWorkTimeFrame withinWorkTimeFrame,
			OtherEmTimezoneLateEarlySet otherEmTimezoneLateEarlySet){
		
		// 退勤時刻を確認する
		TimeWithDayAttr leave = null;
		if (timeLeavingWork.getLeaveStamp().isPresent()){
			TimeActualStamp leaveStamp = timeLeavingWork.getLeaveStamp().get();
			if (leaveStamp.getStamp().isPresent()){
				WorkStamp stamp = leaveStamp.getStamp().get();
				if (stamp.getTimeDay().getTimeWithDay().isPresent()) {
					leave = stamp.getTimeDay().getTimeWithDay().get();
				}
			}
		}
		if (leave == null) return Optional.empty();
		// 早退終了時刻←input.早退終了時刻
		TimeWithDayAttr end = leaveEndClock;
		// 早退開始時刻←退勤時刻
		TimeWithDayAttr start = leave;
		if (leave.lessThanOrEqualTo(withinWorkTimeFrame.getTimeSheet().getStart())){
			// 早退開始時刻←就業時間内時間枠の開始時刻
			start = withinWorkTimeFrame.getTimeSheet().getStart();
		}
		// 丸め設定を保持
		TimeRoundingSetting roundingSet = otherEmTimezoneLateEarlySet.getRoundingSetByDedAtr(deductionAtr.isDeduction());
		// 遅刻早退時間帯を作成
		LateLeaveEarlyTimeSheet result = new LateLeaveEarlyTimeSheet(
				new TimeSpanForDailyCalc(start, end),
				new TimeRoundingSetting(roundingSet.getRoundingTime(), roundingSet.getRounding()));
		// 控除時間帯の登録
		result.registDeductionList(ActualWorkTimeSheetAtrForLate.LeaveEarly,
				deductTimeSheet, integrationOfWorkTime.getCommonSetting());
		// 遅刻早退時間帯を返す
		return Optional.of(result);
	}
	
	/**
	 * 早退計上時間の計算
	 * @return
	 */
	public TimeWithCalculation calcForRecordTime(
			boolean leaveEarly //日別実績の計算区分.遅刻早退の自動計算設定.早退
			) {
		//早退時間の計算
		AttendanceTime calcforRecordTime = AttendanceTime.ZERO;
		if(this.forRecordTimeSheet.isPresent()) {
			calcforRecordTime = this.forRecordTimeSheet.get().calcTotalTime();
		}
		//インターバル免除時間を控除する
		
		//早退計上時間の作成
		TimeWithCalculation leaveEarlyTime = leaveEarly?TimeWithCalculation.sameTime(calcforRecordTime):TimeWithCalculation.createTimeWithCalculation(new AttendanceTime(0),calcforRecordTime);	
		return leaveEarlyTime;
	}
	
	/**
	 * 早退控除時間の計算
	 * @return
	 */
	public TimeWithCalculation calcDedctionTime(
			boolean leaveEarly, //日別実績の計算区分.遅刻早退の自動計算設定.早退
			NotUseAtr notUseAtr //控除区分
			) {
		TimeWithCalculation leaveEarlyDeductionTime = TimeWithCalculation.sameTime(new AttendanceTime(0));
		if(notUseAtr==NotUseAtr.USE) {//控除する場合
			AttendanceTime calcDeductionTime = this.forDeducationTimeSheet.isPresent()?this.forDeducationTimeSheet.get().calcTotalTime():new AttendanceTime(0);
			leaveEarlyDeductionTime =  leaveEarly?TimeWithCalculation.sameTime(calcDeductionTime):TimeWithCalculation.createTimeWithCalculation(new AttendanceTime(0),calcDeductionTime);
		}
		return leaveEarlyDeductionTime;
	}
	
	/**
	 * 自身が持つ短時間勤務時間帯(控除)を収集
	 * @return　短時間勤務時間帯
	 */
	public List<TimeSheetOfDeductionItem> getShortTimeSheet(){
		List<TimeSheetOfDeductionItem> returnList = new ArrayList<>();
		if(this.getForRecordTimeSheet() != null && this.getForRecordTimeSheet().isPresent()) {
			returnList.addAll(this.getForRecordTimeSheet().get().collectShortTimeSheet());
		}
		return returnList;
	}
	
	
	
//	/**
//	 * 早退時間の計算
//	 * @param leaveEarlyTimeSpan
//	 * @param deductionTimeSheet
//	 * @return　早退時間
//	 */
//	public static int getLeaveEarlyTime(TimeSpanForDailyCalc leaveEarlyTimeSpan,DeductionTimeSheet deductionTimeSheet) {
//		//早退時間を計算
//		int leaveEarlyTime = leaveEarlyTimeSpan.lengthAsMinutes();
//		//控除時間の計算
//		
//		//遅刻時間から控除時間を控除する
//		//leaveEarlyTime -= deductionTime;
//		
//		//丸め処理（未作成）	
//		
//		return leaveEarlyTime;
//	}
//	
//	public static TimeSpanForDailyCalc getCorrectedLeaveEarlyTimeSheet(TimeSpanForDailyCalc leaveEarlyTimeSheet,int leaveEarlyTime,DeductionTimeSheet deductionTimeSheet) {
//		//終了から丸め後の早退時間分を減算した時刻を求める
//		leaveEarlyTimeSheet.getEnd().backByMinutes(leaveEarlyTime);
//		//全ての控除時間帯を取得しソート（未作成）	
//		//控除時間帯分ループ（仮作成）
//		for(TimeSheetOfDeductionItem deduTimeSheet : deductionTimeSheet.getForDeductionTimeZoneList()) {
//			//計算範囲内の時間帯を取得（早退時間帯と控除時間帯の重複している時間帯を取得）
//			//控除時間の計算
//			//控除時間分、終了時刻を後ろにズラす		
//		}
//	}
//	
//	public int getLeaveEarlyDeductionTime() {
//		return this.forDeducationTimeSheet.map(ts -> ts.lengthAsMinutes()).orElse(0);
//	}
//	
//	public boolean isLeaveEarly() {
//		return this.forDeducationTimeSheet.isPresent();
//	}
//	
//	public TimeSpanWithRounding deductFrom(TimeSpanWithRounding source) {
//		
//		if (!this.isLeaveEarly()) {
//			return source;
//		}
//
//		//早退時間帯の終了時刻を開始時刻にする
//		return source.newTimeSpan(//↓間違っている遅刻用のままなので早退ように変えろ
//				source.shiftOnlyStart(this.forDeducationTimeSheet.get().getEnd()));
//	}
//	
//	
//	/**
//	 * 流動勤務の場合の早退控除時間の計算
//	 * @return
//	 */
//	public LeaveEarlyTimeSheet leaveEarlyTimeCalcForFluid(
//			WithinWorkTimeFrame withinWorkTimeFrame,
//			TimeWithDayAttr leaveworkTime,
//			WorkTimeCommonSet workTimeCommonSet,
//			LeaveEarlyDecisionClock leaveEarlyDecisionClock,
//			DeductionTimeSheet deductionTimeSheet) {
//		
//		if(leaveEarlyDecisionClock.isLeaveEarly(leaveworkTime)) {
//			
//			return withinWorkTimeFrame.getTimeSheet().getDuplicatedWith(leaveEarlyRangeForCalc)
//					.map(initialLeaveEarlyTimeSheet -> {
//						val revisedLeaveEarlyTimeSheet = reviseLeaveEarlyTimeSheet(initialLeaveEarlyTimeSheet, deductionTimeSheet);
//						return LeaveEarlyTimeSheet.createAsLeaveEarly(revisedLeaveEarlyTimeSheet);
//					})
//					.orElse(LeaveEarlyTimeSheet.createAsNotLeaveEarly());			
//		}
//		return LeaveEarlyTimeSheet.createAsNotLeaveEarly();
//	}
//	
//	
//	/**
//	 * 早退時間帯作成（流動勤務）
//	 * @return
//	 */
//	public TimeSpanForDailyCalc reviceLeaveEarlyTimeSheetForFluid(
//			TimeSpanForDailyCalc leaveEarlyTimeSheet,/*??*/
//			DeductionTimeSheet deductionTimeSheet) {
//		
//		//早退時間を計算
//		int leaveEarlyTime = getLeaveEarlyTimeForFluid();
//		//早退時間帯を再度補正
//		leaveEarlyTimeSheet = getCorrectedLeaveEarlyTimeSheet(leaveEarlyTimeSheet, leaveEarlyTime, deductionTimeSheet);
//		//丸め設定を保持（未作成）
//		
//		return leaveEarlyTimeSheet;
//	}
//	
//	/**
//	 * 
//	 * @return
//	 */
//	public int getLeaveEarlyTimeForFluid(
//			TimeSpanForDailyCalc leaveEarlyTimeSpan,
//			DeductionTimeSheet deductionTimeSheet) {
//		//早退時間を計算
//		int leaveEarlyTime = leaveEarlyTimeSpan.lengthAsMinutes();
//		
//		//遅刻時間の取得（未作成）	
//		
//		//控除時間の計算（未作成）	
//		
//		//早退時間から控除時間を控除する（未作成）	
//		//leaveEarlyTime -= deductionTime;
//
//		//丸め処理（未作成）		
//		
//		return leaveEarlyTime;
//		
//	}
//	
	
//	/**
//	 * 早退時間帯作成(流動勤務)
//	 * @param deductionAtr 控除区分
//	 * @param timeLeavingWork 出退勤
//	 * @param predetermineTimeSet 所定時間設定(計算用クラス)
//	 * @param commonSetting 就業時間帯の共通設定
//	 * @param flowRestTime 流動勤務の休憩時間帯
//	 * @param timeSheetOfDeductionItems 控除項目の時間帯
//	 */
//	public void createLeaveEaryTimeSheetForFlow(
//			DeductionAtr deductionAtr,
//			TimeLeavingWork timeLeavingWork,
//			PredetermineTimeSetForCalc predetermineTimeSet,
//			WorkTimezoneCommonSet commonSetting,
//			FlowWorkRestTimezone flowRestTime,
//			List<TimeSheetOfDeductionItem> timeSheetOfDeductionItems) {
//		
//		//早退時間帯の作成
//		this.setDecitionTimeSheet(
//				deductionAtr,
//				getLeaveEarlyTimeSheetForFlow(deductionAtr, predetermineTimeSet, timeLeavingWork, commonSetting));
//		
//		// 遅刻早退用控除時間帯を取得する　（補正する時間（＝休憩）を除いた時間帯リスト）
//		List<TimeSheetOfDeductionItem> itemsForLateEarly = new ArrayList<>(timeSheetOfDeductionItems);
//		itemsForLateEarly.removeIf(t -> t.getDeductionAtr().isBreak());
//		
//		// 遅刻早退時間帯を補正する時間帯を取得する
//		// 控除時間帯を取得（早退時間補正に必要な控除時間帯のみ）
//		List<TimeSheetOfDeductionItem> deductionTimeItems =
//				TimeSheetOfDeductionItem.getTimeSheetForAdjustFlow(flowRestTime, timeSheetOfDeductionItems);
//		
//		//早退時間の計算
//		//早退時間帯を再度補正
//		getDecitionTimeSheet(deductionAtr).get().setDeductionTimeSheet(timeSheetOfDeductionItems);
//		this.setDecitionTimeSheet(
//				deductionAtr,
//				Optional.of(getDecitionTimeSheet(deductionAtr).get().collectionAgainOfEarly(
//						getDecitionTimeSheet(deductionAtr).get(), deductionTimeItems, itemsForLateEarly)));
//	}
	
//	/**
//	 * 早退時間帯の作成(流動勤務)_退勤時刻～所定時間帯の終了時刻を遅刻時間帯にする
//	 * @param deductionAtr 控除区分
//	 * @param predetermineTimeSet 所定時間設定(計算用クラス)
//	 * @param timeLeavingWork 出退勤
//	 * @param commonSetting 就業時間帯の共通設定
//	 * @return　遅刻早退時間帯
//	 */
//	private Optional<LateLeaveEarlyTimeSheet> getLeaveEarlyTimeSheetForFlow(
//			DeductionAtr deductionAtr,
//			PredetermineTimeSetForCalc predetermineTimeSet,
//			TimeLeavingWork timeLeavingWork,
//			WorkTimezoneCommonSet commonSetting) {
//		
//		//退勤時刻
//		Optional<TimeWithDayAttr> start = timeLeavingWork.getLeaveTime();
//		if (!start.isPresent()) {
//			return Optional.empty();
//		}
//		//所定時間帯の終了時刻
//		if(!predetermineTimeSet.getTimeSheet(timeLeavingWork.getWorkNo()).isPresent()) {
//			return Optional.empty(); 
//		}
//		TimeWithDayAttr end = predetermineTimeSet.getTimeSheet(timeLeavingWork.getWorkNo()).get().getEnd();
//		
//		//丸め設定　控除の場合、控除時間丸め設定を参照。　計上の場合、時間丸め設定を参照
//		TimeRoundingSetting rounding = commonSetting.getLateEarlySet()
//				.getOtherEmTimezoneLateEarlySet(LateEarlyAtr.EARLY)
//				.getRoundingSetByDedAtr(deductionAtr.isDeduction());
//		
//		//早退を取り消したフラグをセット　まだ実装しなくていい
//		
//		//早退開始時刻←退勤時刻、早退終了時刻←所定時間帯の終了時刻
//		return Optional.of(new LateLeaveEarlyTimeSheet(new TimeSpanForDailyCalc(start.get(), end), rounding));
//	}
	
	 /**
	 * 早退時間の休暇時間相殺
	 * @param deductionAtr 控除 or 計上
	 * @param companyholidayPriorityOrder 時間休暇相殺優先順位
	 * @param timeVacationUseTime 日別実績の時間休暇使用時間
	 */
	public void setOffsetTime(
			DeductionAtr deductionAtr,
			CompanyHolidayPriorityOrder companyholidayPriorityOrder,
			TimevacationUseTimeOfDaily timeVacationUseTime) {
		if(!this.getDecitionTimeSheet(deductionAtr).isPresent()) {
			return;
		}
		this.OffsetTime = Optional.of(this.getDecitionTimeSheet(deductionAtr).get().offsetProcess(
				companyholidayPriorityOrder,
				timeVacationUseTime,
				NotUseAtr.NOT_USE));
	}
	
	/**
	 * 重複する時間帯で作り直す
	 * @param timeSpan 時間帯
	 * @param deductionTimeSheet 控除時間帯
	 * @param commonSet 就業時間帯の共通設定
	 * @return 早退時間帯 
	 */
	public Optional<LeaveEarlyTimeSheet> recreateWithDuplicate(TimeSpanForDailyCalc timeSpan, DeductionTimeSheet deductionTimeSheet, WorkTimezoneCommonSet commonSet) {
		//計上用時間帯を変更する
		Optional<LateLeaveEarlyTimeSheet> record = this.forRecordTimeSheet.flatMap(
				r -> r.recreateWithDuplicate(timeSpan, ActualWorkTimeSheetAtrForLate.LeaveEarly, deductionTimeSheet, commonSet));
		//控除用時間帯を変更する
		Optional<LateLeaveEarlyTimeSheet> deducation = this.forDeducationTimeSheet.flatMap(
				d -> d.recreateWithDuplicate(timeSpan, ActualWorkTimeSheetAtrForLate.LeaveEarly, deductionTimeSheet, commonSet));
		if(!record.isPresent() && !deducation.isPresent()) {
			return Optional.empty();
		}
		return Optional.of(new LeaveEarlyTimeSheet(
				record,
				deducation,
				this.workNo,
				Optional.empty()));//相殺時間を削除する
	}
}