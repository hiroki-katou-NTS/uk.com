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
import nts.uk.ctx.at.shared.dom.worktime.predset.TimezoneUse;
import nts.uk.ctx.at.shared.dom.worktype.WorkType;
import nts.uk.shr.com.context.AppContexts;
import nts.uk.shr.com.enumcommon.NotUseAtr;
import nts.uk.shr.com.time.TimeWithDayAttr;

/**
 * 遅刻時間帯
 * @author keisuke_hoshina
 */
@Getter
public class LateTimeSheet {
	
	// 遅刻していない場合はempty
	//計上用時間帯
	private Optional<LateLeaveEarlyTimeSheet> forRecordTimeSheet;
	
	//控除用時間帯
	private Optional<LateLeaveEarlyTimeSheet> forDeducationTimeSheet;
	
	//勤務No
	private int workNo;
	
	//相殺時間
	private Optional<DeductionOffSetTime> OffsetTime;
	
	//コアなしフレックス遅刻時間
	private Optional<AttendanceTime> noCoreFlexLateTime = Optional.empty();
	
	public LateTimeSheet(
			Optional<LateLeaveEarlyTimeSheet> recordTimeSheet,
			Optional<LateLeaveEarlyTimeSheet> deductionTimeSheet,
			int workNo,
			Optional<DeductionOffSetTime> OffsetTime) {
		
		this.forRecordTimeSheet = recordTimeSheet;
		this.forDeducationTimeSheet = deductionTimeSheet;
		this.workNo = workNo;
		this.OffsetTime = OffsetTime;
	}
	
	public static LateTimeSheet createAsLate(LateLeaveEarlyTimeSheet recordTimeSheet,LateLeaveEarlyTimeSheet deductionTimeSheet,int workNo,Optional<LateTimeOfDaily> lateTime,Optional<DeductionOffSetTime> OffsetTime) {
		return new LateTimeSheet(Optional.of(recordTimeSheet), Optional.of(deductionTimeSheet),workNo,OffsetTime);
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
	 * 遅刻時間帯の作成
	 * @param lateDesClock 遅刻判断時刻
	 * @param timeLeavingWork 出退勤
	 * @param otherEmTimezoneLateEarlySet 就業時間帯の遅刻・早退別設定
	 * @param withinWorkTimeFrame 就業時間内時間枠
	 * @param deductionTimeSheet 控除時間帯
	 * @param predetermineTimeSet 所定時間設定
	 * @param workNo 勤務NO
	 * @param workType 勤務種類
	 * @param predetermineTimeSetForCalc 計算用所定時間設定
	 * @param integrationOfWorkTime 統合就業時間帯
	 * @param integrationOfDaily 日別実績(Work)
	 * @return 遅刻時間帯
	 */
	public static Optional<LateTimeSheet> createLateTimeSheet(
			Optional<LateDecisionClock> lateDesClock,
			TimeLeavingWork timeLeavingWork,
			OtherEmTimezoneLateEarlySet otherEmTimezoneLateEarlySet,
			WithinWorkTimeFrame withinWorkTimeFrame,
			DeductionTimeSheet deductionTimeSheet,
			Optional<TimezoneUse> predetermineTimeSet,
			int workNo,
			WorkType workType,
			PredetermineTimeSetForCalc predetermineTimeSetForCalc,
			IntegrationOfWorkTime integrationOfWorkTime,
			IntegrationOfDaily integrationOfDaily) {
		
		//出勤時刻
		Optional<TimeWithDayAttr> attendance = timeLeavingWork.getAttendanceTime();
		if(!attendance.isPresent() || !lateDesClock.isPresent() || !predetermineTimeSet.isPresent()) {
			return Optional.empty();
		}
		
		//遅刻時間帯
		Optional<LateLeaveEarlyTimeSheet> lateAppTimeSheet = Optional.empty();
		//遅刻控除時間帯
		Optional<LateLeaveEarlyTimeSheet> lateDeductTimeSheet = Optional.empty();
		
		//出勤時刻と遅刻判断時刻を比較	
		if(lateDesClock.get().getLateDecisionClock().lessThan(attendance.get())) {
			//遅刻控除時間帯の作成
			lateDeductTimeSheet = createLateLeaveEarlyTimeSheet(
					DeductionAtr.Deduction,
					timeLeavingWork,
					integrationOfWorkTime,
					integrationOfDaily,
					predetermineTimeSet.get(),
					withinWorkTimeFrame,
					deductionTimeSheet,
					workType,
					predetermineTimeSetForCalc,
					otherEmTimezoneLateEarlySet);
			//遅刻時間帯の作成
			lateAppTimeSheet = createLateLeaveEarlyTimeSheet(
					DeductionAtr.Appropriate,
					timeLeavingWork,
					integrationOfWorkTime,
					integrationOfDaily,
					predetermineTimeSet.get(),
					withinWorkTimeFrame,
					deductionTimeSheet,
					workType,
					predetermineTimeSetForCalc,
					otherEmTimezoneLateEarlySet);
		}else {
			//遅刻控除時間帯の作成
			lateDeductTimeSheet = createLateLeaveEarlyTimeSheet(
					DeductionAtr.Deduction,
					timeLeavingWork,
					integrationOfWorkTime,
					integrationOfDaily,
					predetermineTimeSet.get(),
					withinWorkTimeFrame,
					deductionTimeSheet,
					workType,
					predetermineTimeSetForCalc,
					otherEmTimezoneLateEarlySet);
		}
		if(!lateAppTimeSheet.isPresent() && !lateDeductTimeSheet.isPresent()) {
			return Optional.empty();
		}
		return Optional.of(new LateTimeSheet(lateAppTimeSheet, lateDeductTimeSheet, workNo, Optional.empty()));
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
		// 遅刻時間帯を作成する
		Optional<LateLeaveEarlyTimeSheet> instance = createLateTimeSheetInstance(
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
	 * 遅刻時間帯を作成する
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
	private static Optional<LateLeaveEarlyTimeSheet> createLateTimeSheetInstance(
			DeductionAtr deductionAtr,
			TimeLeavingWork timeLeavingWork,
			IntegrationOfWorkTime integrationOfWorkTime,
			TimezoneUse predetermineTimeSet,
			WithinWorkTimeFrame withinWorkTimeFrame,
			DeductionTimeSheet deductTimeSheet,
			WorkType workType,
			PredetermineTimeSetForCalc predetermineTimeForSet,
			OtherEmTimezoneLateEarlySet otherEmTimezoneLateEarlySet){

		// 遅刻開始時刻を取得
		Optional<TimeSpanForDailyCalc> calcRange = LateDecisionClock.getCalcRange(
				predetermineTimeSet, timeLeavingWork, integrationOfWorkTime,
				predetermineTimeForSet, workType.getDailyWork().decisionNeedPredTime());
		if (!calcRange.isPresent()) return Optional.empty();
		TimeWithDayAttr lateStartClock = calcRange.get().getStart();
		// 遅刻時間を計算する時間帯を判断
		Optional<LateLeaveEarlyTimeSheet> beforeAdjustOpt = checkTimeSheetForCalcLateTime(
				deductionAtr, timeLeavingWork, integrationOfWorkTime,
				deductTimeSheet, lateStartClock, withinWorkTimeFrame, otherEmTimezoneLateEarlySet);
		if (!beforeAdjustOpt.isPresent()) return Optional.empty();
		LateLeaveEarlyTimeSheet beforeAdjust = beforeAdjustOpt.get();
		// 遅刻時間を計算（丸め前、丸め後）
		AttendanceTime beforeRounding = beforeAdjust.calcTotalTime(NotUseAtr.USE, NotUseAtr.NOT_USE);
		AttendanceTime afterRounding = beforeAdjust.calcTotalTime();
		// 遅刻終了時刻を取得
		TimeWithDayAttr lateEndClock = beforeAdjust.getLateEndTime(beforeRounding, afterRounding, deductTimeSheet);
		// 遅刻早退時間帯を作成
		LateLeaveEarlyTimeSheet result = new LateLeaveEarlyTimeSheet(
				new TimeSpanForDailyCalc(lateStartClock, lateEndClock), beforeAdjust.getRounding());
		// 控除時間帯の登録
		result.registDeductionList(ActualWorkTimeSheetAtrForLate.Late,
				deductTimeSheet, integrationOfWorkTime.getCommonSetting());
		// 遅刻早退時間帯を返す
		return Optional.of(result);
	}

	/**
	 * 遅刻時間を計算する時間帯を判断
	 * @param deductionAtr 控除区分
	 * @param timeLeavingWork 出退勤
	 * @param integrationOfWorkTime 統合就業時間帯
	 * @param deductTimeSheet 控除時間帯
	 * @param lateStartClock 遅刻開始時刻
	 * @param withinWorkTimeFrame 就業時間内時間枠
	 * @param otherEmTimezoneLateEarlySet 就業時間帯の遅刻・早退別設定
	 * @return 遅刻早退時間帯
	 */
	private static Optional<LateLeaveEarlyTimeSheet> checkTimeSheetForCalcLateTime(
			DeductionAtr deductionAtr,
			TimeLeavingWork timeLeavingWork,
			IntegrationOfWorkTime integrationOfWorkTime,
			DeductionTimeSheet deductTimeSheet,
			TimeWithDayAttr lateStartClock,
			WithinWorkTimeFrame withinWorkTimeFrame,
			OtherEmTimezoneLateEarlySet otherEmTimezoneLateEarlySet){
		
		// 出勤時刻を確認する
		TimeWithDayAttr attendance = null;
		if (timeLeavingWork.getAttendanceStamp().isPresent()){
			TimeActualStamp attdStamp = timeLeavingWork.getAttendanceStamp().get();
			if (attdStamp.getStamp().isPresent()){
				WorkStamp stamp = attdStamp.getStamp().get();
				if (stamp.getTimeDay().getTimeWithDay().isPresent()) {
					attendance = stamp.getTimeDay().getTimeWithDay().get();
				}
			}
		}
		if (attendance == null) return Optional.empty();
		// 遅刻開始時刻←input.遅刻開始時刻
		TimeWithDayAttr start = lateStartClock;
		// 遅刻終了時刻←出勤時刻
		TimeWithDayAttr end = attendance;
		if (attendance.greaterThan(withinWorkTimeFrame.getTimeSheet().getEnd())){
			// 遅刻終了時刻←就業時間内時間枠の終了時刻
			end = withinWorkTimeFrame.getTimeSheet().getEnd();
		}
		// 丸め設定を保持
		TimeRoundingSetting roundingSet = otherEmTimezoneLateEarlySet.getRoundingSetByDedAtr(deductionAtr.isDeduction());
		// 遅刻早退時間帯を作成
		LateLeaveEarlyTimeSheet result = new LateLeaveEarlyTimeSheet(
				new TimeSpanForDailyCalc(start, end),
				new TimeRoundingSetting(roundingSet.getRoundingTime(), roundingSet.getRounding()));
		// 控除時間帯の登録
		result.registDeductionList(ActualWorkTimeSheetAtrForLate.Late,
				deductTimeSheet, integrationOfWorkTime.getCommonSetting());
		// 遅刻早退時間帯を返す
		return Optional.of(result);
	}
	
	/**
	 * 遅刻計上時間の計算
	 * @return
	 */
	public TimeWithCalculation calcForRecordTime(
			boolean late //日別実績の計算区分.遅刻早退の自動計算設定.遅刻
			) {
		//遅刻時間の計算
		AttendanceTime calcforRecordTime = AttendanceTime.ZERO;

		if(this.forRecordTimeSheet.isPresent()) {
			calcforRecordTime = this.forRecordTimeSheet.get().calcTotalTime();
		}
		
		//インターバル免除時間を控除する
		
		//遅刻計上時間の作成
		TimeWithCalculation lateTime = late?TimeWithCalculation.sameTime(calcforRecordTime):TimeWithCalculation.createTimeWithCalculation(new AttendanceTime(0),calcforRecordTime);	
		return lateTime;
	}
	
	/**
	 * 遅刻控除時間の計算
	 * @return
	 */
	public TimeWithCalculation calcDedctionTime(
			boolean late,//日別実績の計算区分.遅刻早退の自動計算設定.遅刻
			NotUseAtr notUseAtr//控除区分
			) {
		TimeWithCalculation lateDeductionTime = TimeWithCalculation.sameTime(new AttendanceTime(0));
		if(notUseAtr==NotUseAtr.USE) {//控除する場合
			AttendanceTime calcDeductionTime = this.forDeducationTimeSheet.isPresent()?this.forDeducationTimeSheet.get().calcTotalTime():new AttendanceTime(0);
			lateDeductionTime =  late?TimeWithCalculation.sameTime(calcDeductionTime):TimeWithCalculation.createTimeWithCalculation(new AttendanceTime(0),calcDeductionTime);
		}
		return lateDeductionTime;
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
//	 * 控除項目の時間帯（List）を取得する
//	 * @return
//	 */
//	public List<TimeSheetOfDeductionItem> getTimeSheetOfDeductionItem(DeductionTimeSheet deductionTimeSheet,TimeSpanForDailyCalc initiaLlateTimeSheet){
//		//遅刻時間帯に重複する控除時間帯格納用リスト
//		List<TimeSheetOfDeductionItem> duplicateTimeSheetList = new ArrayList<>();
//		//控除用時間帯リスト分ループ
//		for(TimeSheetOfDeductionItem timeSheetOfDeductionItem : deductionTimeSheet.getForDeductionTimeZoneList()) {
//			TimeSpanForDailyCalc duplicateTimeSheet = initiaLlateTimeSheet.getDuplicatedWith(timeSheetOfDeductionItem.getTimeSheet()).orElse(null);
//			if(duplicateTimeSheet != null) {
//				duplicateTimeSheetList.add(timeSheetOfDeductionItem);
//			}
//		}
//		return duplicateTimeSheetList;
//	}
//	
//	/**
//	 * 遅刻時間の計算
//	 * @param lateTimeSpan
//	 * @param deductionTimeSheet
//	 * @return　遅刻時間
//	 */
//	public int getLateTime(LateLeaveEarlyTimeSheet lateLeaveEarlyTimeSheet) {
//		//計算範囲を取得
//		TimeSpanForDailyCalc calcRange = lateLeaveEarlyTimeSheet.getCalcrange();
//		//遅刻時間を計算
//		int lateTime = calcRange.lengthAsMinutes();
//		//控除時間帯を取得	
//		List<TimeSheetOfDeductionItem> deductionTimeSheet = lateLeaveEarlyTimeSheet.getDeductionTimeSheets();
//		//控除時間の計算
//		int deductionTime = calcDeductionTime(deductionTimeSheet);
//		//遅刻時間から控除時間を控除する	
//		lateTime -= deductionTime;
//		//丸め処理
//		
//		return lateTime;
//	}
//	
//	/**
//	 * 控除時間の計算
//	 * @return
//	 */
//	public int calcDeductionTime(List<TimeSheetOfDeductionItem> deductionTimeSheet) {
//		int totalDeductionTime = 0;
//		//控除時間帯分ループ
//		for(TimeSheetOfDeductionItem timeSheetOfDeductionItem : deductionTimeSheet) {
//			//控除時間の計算
//			int deductionTime = timeSheetOfDeductionItem.getTimeSheet().lengthAsMinutes();
//			//丸め処理
//			
//			//丸め後の値をtotalDeductionTimeに加算
//			totalDeductionTime += deductionTime;
//		}
//		return totalDeductionTime;
//	}
//	
//	
//	/**
//	 * 遅刻時間帯を再度補正
//	 * @param lateTimeSheet
//	 * @param lateTime
//	 * @param deductionTimeSheet
//	 * @return 補正後の遅刻時間帯
//	 */
//	public LateLeaveEarlyTimeSheet getCorrectedLateTimeSheet(
//			LateLeaveEarlyTimeSheet lateLeaveEarlyTimeSheet,
//			int lateTime,
//			DeductionTimeSheet deductionTimeSheet) {
//		//開始から丸め後の遅刻時間分を加算した時間帯を作成
//		TimeSpanForDailyCalc lateTimeSheet = new TimeSpanForDailyCalc(
//				lateLeaveEarlyTimeSheet.getTimeSheet().getStart(),
//				lateLeaveEarlyTimeSheet.getTimeSheet().getStart().backByMinutes(lateTime));
//		//全ての控除時間帯を取得しソート
//		List<TimeSheetOfDeductionItem> deduTimeSheetList = deductionTimeSheet.getForDeductionTimeZoneList().stream()
//				.sorted(comparing(e -> e.getTimeSheet().getStart()))
//				.collect(Collectors.toList());
//		//入れ物だけ作成
//		List<TimeSheetOfDeductionItem> newdeduTimeSheetList = new ArrayList<>();
//		//控除時間帯分ループ
//		for(TimeSheetOfDeductionItem deduTimeSheet : deduTimeSheetList) {
//			//計算範囲の時間帯を作成
//			TimeSpanForDailyCalc deductionTimeSpan = deduTimeSheet.getTimeSheet().getSpan().getDuplicatedWith(lateTimeSheet).orElse(null);
//			//控除時間の計算
//			int deductionTime = 0;
//			if(deductionTimeSpan != null) {
//				newdeduTimeSheetList.add(deduTimeSheet);
//				deductionTime += deductionTimeSpan.lengthAsMinutes();
//				//丸め
//				
//			}
//			lateTimeSheet.shiftEndAhead(deductionTime);
//		}
//		LateLeaveEarlyTimeSheet collectLateLeaveEarlyTimeSheet = new LateLeaveEarlyTimeSheet(
//				lateLeaveEarlyTimeSheet.getTimeSheet().newTimeSpan(lateTimeSheet),
//				lateLeaveEarlyTimeSheet.getCalcrange(),
//				newdeduTimeSheetList,
//				lateLeaveEarlyTimeSheet.getBonusPayTimeSheet(),
//				lateLeaveEarlyTimeSheet.getMidNightTimeSheet());
//		return collectLateLeaveEarlyTimeSheet;
//	}
//	
//	public int getLateDeductionTime() {
//		return this.forDeducationTimeSheet.map(ts -> ts.lengthAsMinutes()).orElse(0);
//	}
//	
//	public boolean isLate() {
//		return this.forDeducationTimeSheet.isPresent();
//	}
//	
//	public TimeSpanWithRounding deductForm(TimeSpanWithRounding source) {
//		
//		if (!this.isLate()) {
//			return source;
//		}
//
//		//遅刻時間帯の終了時刻を開始時刻にする
//		return source.newTimeSpan(
//				source.shiftOnlyStart(this.forDeducationTimeSheet.get().getEnd()));
//	}
//	
//	
//	/**
//	 * 流動勤務の場合の遅刻控除時間の計算
//	 * @return
//	 */
//	public LateTimeSheet lateTimeCalcForFluid(
//			WithinWorkTimeFrame withinWorkTimeFrame,
//			TimeSpanForDailyCalc lateRangeForCalc,
//			WorkTimeCommonSet workTimeCommonSet,
//			LateDecisionClock lateDecisionClock,
//			DeductionTimeSheet deductionTimeSheet) {
//		
//		TimeWithDayAttr goWorkTime = lateRangeForCalc.getEnd();
//		
//		if(lateDecisionClock.isLate(goWorkTime)){
//			
//			return withinWorkTimeFrame.getTimeSheet().getDuplicatedWith(lateRangeForCalc)
//					.map(initialLateTimeSheet -> {
//						val revisedLateTimeSheet = reviceLateTimeSheetForFluid(initialLateTimeSheet, deductionTimeSheet);						
//						return LateTimeSheet.createAsLate(revisedLateTimeSheet);
//					})
//					.orElse(LateTimeSheet.createAsNotLate());
//		}
//		return LateTimeSheet.createAsNotLate();//遅刻していない場合
//	}
//	
//	/**
//	 * 遅刻時間帯作成(流動勤務)
//	 * @param goWorkTime
//	 * @param workTime
//	 * @param deductionTimeSheet
//	 * @param workNo
//	 * @param deductionAtr
//	 * @return 控除用または計上用の早退時間帯
//	 */
//	private TimeSpanForDailyCalc reviceLateTimeSheetForFluid(
//			TimeSpanForDailyCalc lateTimeSheet,/*遅刻時間帯の作成*/
//			DeductionTimeSheet deductionTimeSheet) {
//		
//		
//		//遅刻時間を計算
//		int lateTime = getLateTimeForFluid(lateTimeSheet, deductionTimeSheet);
//		//遅刻時間帯を再度補正
//		lateTimeSheet = getCorrectedLateTimeSheet(lateTimeSheet, lateTime, deductionTimeSheet);		
//		//丸め設定を保持（未作成）
//		
//		return lateTimeSheet;
//	}
//
//	/**
//	 * 遅刻時間の計算(流動勤務)
//	 * @param lateTimeSpan
//	 * @param deductionTimeSheet
//	 * @return　遅刻時間
//	 */
//	public int getLateTimeForFluid(
//			TimeSpanForDailyCalc lateTimeSpan,
//			DeductionTimeSheet deductionTimeSheet) {
//		//遅刻時間を計算
//		int lateTime = lateTimeSpan.lengthAsMinutes();
//
//		//遅刻時間の取得（未作成）	
//		
//		//控除時間の計算（未作成）	
//		
//		//遅刻時間から控除時間を控除する（未作成）	
//		//lateTime -= deductionTime;
//
//		//丸め処理（未作成）		
//		
//		return lateTime;
//	}
//	
//	
//	/**
//	 * 遅刻時間の計算　（時間帯作成後の処理で使用する）
//	 * @return
//	 */
//	public LateTimeSheet createLateTimeSheet(LateTimeSheet baseLateTimeSheet) {
//		//遅刻時間の計算
//		
//		
//		
//		
//		
//	}
//	
	
//	/**
//	 * 遅刻時間帯作成(流動勤務)
//	 * @param deductionAtr 控除区分
//	 * @param timeLeavingWork 出退勤
//	 * @param predetermineTimeSet 所定時間設定(計算用クラス)
//	 * @param commonSetting 就業時間帯の共通設定
//	 * @param flowRestTime 流動勤務の休憩時間帯
//	 * @param timeSheetOfDeductionItems 控除項目の時間帯
//	 */
//	public void createLateTimeSheetForFlow(
//			DeductionAtr deductionAtr,
//			TimeLeavingWork timeLeavingWork,
//			PredetermineTimeSetForCalc predetermineTimeSet,
//			WorkTimezoneCommonSet commonSetting,
//			FlowWorkRestTimezone flowRestTime,
//			List<TimeSheetOfDeductionItem> timeSheetOfDeductionItems) {
//		
//		//遅刻時間帯の作成
//		this.setDecitionTimeSheet(
//				deductionAtr,
//				this.getLateTimeSheetForFlow(deductionAtr, predetermineTimeSet, timeLeavingWork, commonSetting));
//		
//		// 遅刻早退用控除時間帯を取得する　（補正する時間（＝休憩）を除いた時間帯リスト）
//		List<TimeSheetOfDeductionItem> itemsForLateEarly = new ArrayList<>(timeSheetOfDeductionItems);
//		itemsForLateEarly.removeIf(t -> t.getDeductionAtr().isBreak());
//		
//		// 遅刻早退時間帯を補正する時間帯を取得する
//		// 控除時間帯を取得（遅刻時間補正に必要な控除時間帯のみ）
//		List<TimeSheetOfDeductionItem> deductionTimeItems =
//				TimeSheetOfDeductionItem.getTimeSheetForAdjustFlow(flowRestTime, timeSheetOfDeductionItems);
//		
//		//遅刻時間の計算
//		//遅刻時間帯を再度補正
//		this.setDecitionTimeSheet(
//				deductionAtr,
//				Optional.of(this.getDecitionTimeSheet(deductionAtr).get().collectionAgainOfLate(
//						getDecitionTimeSheet(deductionAtr).get(), deductionTimeItems, itemsForLateEarly)));
//		
//		//計上の場合　インターバル免除時間帯を控除
//		if(deductionAtr.isAppropriate()) {
//			//createIntervalExemptionTimeSheet();
//		}
//	}
	
//	/**
//	 * 遅刻時間帯の作成(流動勤務)_所定時間帯の開始時刻～出勤時刻を遅刻時間帯にする
//	 * @param deductionAtr 控除区分
//	 * @param predetermineTimeSet 所定時間設定(計算用クラス)
//	 * @param timeLeavingWork 出退勤
//	 * @param commonSetting 就業時間帯の共通設定
//	 * @return　遅刻早退時間帯
//	 */
//	private Optional<LateLeaveEarlyTimeSheet> getLateTimeSheetForFlow(
//			DeductionAtr deductionAtr,
//			PredetermineTimeSetForCalc predetermineTimeSet,
//			TimeLeavingWork timeLeavingWork,
//			WorkTimezoneCommonSet commonSetting) {
//		
//		//所定時間帯の開始時刻
//		if(!predetermineTimeSet.getTimeSheet(timeLeavingWork.getWorkNo()).isPresent()) {
//			return Optional.empty();
//		}
//		TimeWithDayAttr start = predetermineTimeSet.getTimeSheet(timeLeavingWork.getWorkNo()).get().getStart();
//		
//		//出勤時刻
//		Optional<TimeWithDayAttr> end = timeLeavingWork.getAttendanceTime();
//		if (!end.isPresent()) {
//			return Optional.empty();
//		}
//		//丸め設定　控除の場合、控除時間丸め設定を参照。　計上の場合、時間丸め設定を参照
//		TimeRoundingSetting rounding = commonSetting.getLateEarlySet()
//				.getOtherEmTimezoneLateEarlySet(LateEarlyAtr.LATE)
//				.getRoundingSetByDedAtr(deductionAtr.isDeduction());
//		
//		//遅刻を取り消したフラグをセット　まだ実装しなくていい
//		
//		//遅刻開始時刻←所定時間帯の開始時刻、遅刻終了時刻←出勤時刻
//		return Optional.of(new LateLeaveEarlyTimeSheet(new TimeSpanForDailyCalc(start, end.get()), rounding));
//	}
	
//	/**
//	 * 遅刻時間帯の計算(流動勤務)_控除時間帯を絞り込む
//	 * @param flowRestTime 流動勤務の休憩時間帯
//	 * @param timeSheetOfDeductionItems 控除項目の時間帯
//	 * @return　控除項目の時間帯
//	 */
//	private List<TimeSheetOfDeductionItem> filteringLateTimeSheetForFlow(
//			FlowWorkRestTimezone flowRestTime,
//			List<TimeSheetOfDeductionItem> timeSheetOfDeductionItems) {
//		
//		List<TimeSheetOfDeductionItem> deductionTimeSheets;
//		
//		//固定休憩
//		if(flowRestTime.isFixRestTime()){
//			deductionTimeSheets = timeSheetOfDeductionItems.stream()
//					.filter(t -> t.getDeductionAtr() == DeductionClassification.CHILD_CARE
//							|| t.getDeductionAtr() == DeductionClassification.BREAK)
//					.collect(Collectors.toList());
//		}
//		//流動休憩
//		else {
//			deductionTimeSheets = timeSheetOfDeductionItems.stream()
//					.filter(t -> t.getDeductionAtr() == DeductionClassification.CHILD_CARE)
//					.collect(Collectors.toList());
//		}
//		return deductionTimeSheets;
//	}
	
//	/**
//	 * インターバル免除時間帯を作成
//	 * @return
//	 */
//	public Optional<IntervalExemptionTime> createIntervalExemptionTimeSheet(WorkTimezoneCommonSet commonSetting){
//		
//		//インターバル免除時間を使用する
//		if(commonSetting.getIntervalSet().isUseIntervalExemptionTime()) {
//			
//		}
//		return;
//	}
//	
//	/**
//	 * 作成処理(計上用時間帯とインターバル時間帯の重複している時間帯をインターバル免除時間帯とする）
//	 * @return
//	 */
//	public Optional<IntervalExemptionTime> createIntervalExemptionTimeSheetDuplication(WorkTimezoneCommonSet commonSetting){
//		
//		if(commonSetting.getIntervalSet().isUseIntervalExemptionTime()) {
//			this.forRecordTimeSheet.get().calcrange.getDuplicatedWith(ManageInterval.timeSheet//未作成);
//		
//		}
//		return;
//		
//	}
//
//	/**
//	 * インターバル免除時間を控除
//	 * @return
//	 */
//	public void createIntervalExemptionTimeSheet(Optional<WorkTimezoneCommonSet> commonSetting){
//		
//		this.forRecordTimeSheet.get().calcrange;
//
//	}
	
//	//遅刻時間の休暇時間相殺
//	public void oldcalcLateOffsetTime(
//		DeductionAtr deductionAtr,
//		CompanyHolidayPriorityOrder holidayPriorityOrder,
//		TimevacationUseTimeOfDaily timeVacationUseTime) {
//		
//		this.getDecitionTimeSheet(deductionAtr).get().calcLateLeaveEarlyOffsetTime(
//				deductionAtr,
//				holidayPriorityOrder,
//				timeVacationUseTime,
//				this.OffsetTime.get(),
//				this.getDecitionTimeSheet(deductionAtr).get().calcTotalTime(NotUseAtr.NOT_USE, NotUseAtr.NOT_USE));//時間の計算
//	}
	
	/**
	 * 遅刻時間の休暇時間相殺
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
}
