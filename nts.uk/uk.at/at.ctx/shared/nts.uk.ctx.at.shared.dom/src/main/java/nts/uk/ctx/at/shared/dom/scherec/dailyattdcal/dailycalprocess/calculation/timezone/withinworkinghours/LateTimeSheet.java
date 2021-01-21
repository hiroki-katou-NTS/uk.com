package nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailycalprocess.calculation.timezone.withinworkinghours;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import lombok.Getter;
import nts.uk.ctx.at.shared.dom.common.time.AttendanceTime;
import nts.uk.ctx.at.shared.dom.common.timerounding.TimeRoundingSetting;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.TimevacationUseTimeOfDaily;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.attendancetime.TimeLeavingWork;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.common.TimeWithCalculation;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.latetime.LateTimeOfDaily;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailycalprocess.calculation.DeductionOffSetTime;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailycalprocess.calculation.DeductionTimeSheet;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailycalprocess.calculation.PredetermineTimeSetForCalc;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailycalprocess.calculation.TimeSpanForDailyCalc;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailycalprocess.calculation.timezone.deductiontime.DeductionAtr;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailycalprocess.calculation.timezone.deductiontime.DeductionClassification;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailycalprocess.calculation.timezone.deductiontime.TimeSheetOfDeductionItem;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailycalprocess.holidaypriorityorder.CompanyHolidayPriorityOrder;
import nts.uk.ctx.at.shared.dom.worktime.IntegrationOfWorkTime;
import nts.uk.ctx.at.shared.dom.worktime.common.OtherEmTimezoneLateEarlySet;
import nts.uk.ctx.at.shared.dom.worktime.common.WorkTimezoneCommonSet;
import nts.uk.ctx.at.shared.dom.worktime.flowset.FlowWorkRestTimezone;
import nts.uk.ctx.at.shared.dom.worktime.predset.TimezoneUse;
import nts.uk.ctx.at.shared.dom.worktype.WorkType;
import nts.uk.shr.com.enumcommon.NotUseAtr;
import nts.uk.shr.com.time.TimeWithDayAttr;

/**
 * 遅刻時間帯
 * @author keisuke_hoshina
 *
 */
@Getter
public class LateTimeSheet{
	
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
	
	public static LateTimeSheet createAsNotLate() {
		return new LateTimeSheet(Optional.empty(), Optional.empty(),1,Optional.empty());
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
	 * @param specifiedTimeSheet
	 * @param goWorkTime
	 * @param workNo
	 * @param classification
	 * @param lateDesClock
	 * @return
	 */
	public static LateTimeSheet createLateTimeSheet(
			Optional<LateDecisionClock> lateDesClock,
			TimeLeavingWork timeLeavingWork,
			OtherEmTimezoneLateEarlySet otherEmTimezoneLateEarlySet,
			WithinWorkTimeFrame duplicateTimeSheet,
			DeductionTimeSheet deductionTimeSheet,
			List<TimeSheetOfDeductionItem> breakTimeFromMaster,
			Optional<TimezoneUse> predetermineTimeSet,
			int workNo,
			WorkType workType,
			PredetermineTimeSetForCalc predetermineTimeForSet,
			IntegrationOfWorkTime integrationOfWorkTime) {
		
		//出勤時刻
		TimeWithDayAttr attendance = null;
		if(timeLeavingWork.getAttendanceStamp().isPresent()) {
			if(timeLeavingWork.getAttendanceStamp().get().getStamp().isPresent()) {
				if(timeLeavingWork.getAttendanceStamp().get().getStamp().get().getTimeDay().getTimeWithDay().isPresent()) {
					attendance =  timeLeavingWork.getAttendanceStamp().get().getStamp().get().getTimeDay().getTimeWithDay().get();
				}
			}
		}
		if(attendance != null && lateDesClock.isPresent() && predetermineTimeSet.isPresent()) {
			//出勤時刻と遅刻判断時刻を比較	
			if(lateDesClock.get().getLateDecisionClock().lessThan(attendance)) {

				//遅刻控除時間帯の作成
				Optional<LateLeaveEarlyTimeSheet> lateDeductTimeSheet = createLateLeaveEarlyTimeSheet(DeductionAtr.Deduction,
																									  timeLeavingWork,
																									  integrationOfWorkTime,
																									  predetermineTimeSet.get(),
																									  duplicateTimeSheet,
																									  deductionTimeSheet,
																									  breakTimeFromMaster,workType,predetermineTimeForSet,
																									  otherEmTimezoneLateEarlySet);
				//遅刻時間帯の作成
				Optional<LateLeaveEarlyTimeSheet> lateAppTimeSheet = createLateLeaveEarlyTimeSheet(DeductionAtr.Appropriate,
																								   timeLeavingWork,
																								   integrationOfWorkTime,
																								   predetermineTimeSet.get(),
																								   duplicateTimeSheet,
																								   deductionTimeSheet,
																								   breakTimeFromMaster,workType,predetermineTimeForSet,
																								   otherEmTimezoneLateEarlySet);
				
				LateTimeSheet lateTimeSheet = new LateTimeSheet(lateAppTimeSheet,lateDeductTimeSheet, workNo, Optional.empty());
				
				return lateTimeSheet;
			}else {
				if(!otherEmTimezoneLateEarlySet.getGraceTimeSet().isIncludeWorkingHour()){//猶予時間を加算しない場合
					//遅刻控除時間帯の作成
					Optional<LateLeaveEarlyTimeSheet> lateDeductTimeSheet = createLateLeaveEarlyTimeSheet(DeductionAtr.Deduction,
																										  timeLeavingWork,
																										  integrationOfWorkTime,
																										  predetermineTimeSet.get(),
																										  duplicateTimeSheet,
																										  deductionTimeSheet,
																										  breakTimeFromMaster,workType,predetermineTimeForSet,
																										  otherEmTimezoneLateEarlySet);
					LateTimeSheet lateTimeSheet = new LateTimeSheet(Optional.empty(),lateDeductTimeSheet, workNo, Optional.empty());
					return lateTimeSheet;
				}
			}
		}
		return LateTimeSheet.createAsNotLate();//遅刻していない
	}
	
	/**
	 * 遅刻早退時間帯作成
	 * @param goWorkTime
	 * @param integrationOfWorkTime
	 * @param deductionTimeSheet
	 * @param workNo
	 * @param deductionAtr
	 * @param otherEmTimezoneLateEarlySet 
	 * @return 控除用または計上用の遅刻早退時間帯
	 */
	private static Optional<LateLeaveEarlyTimeSheet> createLateLeaveEarlyTimeSheet(
			DeductionAtr deductionAtr,
			TimeLeavingWork timeLeavingWork
			,IntegrationOfWorkTime integrationOfWorkTime
			,TimezoneUse predetermineTimeSet
			,WithinWorkTimeFrame duplicateTimeSheet
			,DeductionTimeSheet deductionTimeSheet,List<TimeSheetOfDeductionItem> breakTimeFromMaster
			,WorkType workType,PredetermineTimeSetForCalc predetermineTimeForSet, OtherEmTimezoneLateEarlySet otherEmTimezoneLateEarlySet){

		//遅刻時間帯の作成
		Optional<LateLeaveEarlyTimeSheet> instance = createLateTimeSheetInstance(deductionAtr,
				timeLeavingWork
				,integrationOfWorkTime
				,predetermineTimeSet
				,duplicateTimeSheet
				,deductionTimeSheet,breakTimeFromMaster,workType,predetermineTimeForSet,
				otherEmTimezoneLateEarlySet);
			
		//遅刻時間帯を再度補正
		if(instance.isPresent()) {
			instance = Optional.of(instance.get().collectionAgainOfLate(instance.get(),breakTimeFromMaster));
		}
		return instance;
	}
	
	private static Optional<LateLeaveEarlyTimeSheet> createLateTimeSheetInstance(
			DeductionAtr deductionAtr,
			TimeLeavingWork timeLeavingWork,
			IntegrationOfWorkTime integrationOfWorkTime,
			TimezoneUse predetermineTimeSet,
			WithinWorkTimeFrame duplicateTimeSheet,
			DeductionTimeSheet deductionTimeSheet,
			List<TimeSheetOfDeductionItem> breakTimeFromMaster,
			WorkType workType,
			PredetermineTimeSetForCalc predetermineTimeForSet,
			OtherEmTimezoneLateEarlySet otherEmTimezoneLateEarlySet){
		//控除区分を基に丸め設定を取得しておく
		//TimeRoundingSetting timeRoundingSetting = lateLeaveEarlySettingOfWorkTime.getTimeRoundingSetting(deductionAtr);
		//出勤時刻
		TimeWithDayAttr attendance = null;
		if(timeLeavingWork.getAttendanceStamp().isPresent()) {
			if(timeLeavingWork.getAttendanceStamp().get().getStamp().isPresent()) {
				if(timeLeavingWork.getAttendanceStamp().get().getStamp().get().getTimeDay().getTimeWithDay().isPresent()) {
					attendance =  timeLeavingWork.getAttendanceStamp().get().getStamp().get().getTimeDay().getTimeWithDay().get();
				}
			}
		}
		if(attendance!=null) {
			//計算範囲の取得
			Optional<TimeSpanForDailyCalc> calcRange = LateDecisionClock.getCalcRange(predetermineTimeSet, timeLeavingWork, integrationOfWorkTime, predetermineTimeForSet,workType.getDailyWork().decisionNeedPredTime());
			if(calcRange.isPresent()) {
				//遅刻時間帯の作成
				TimeWithDayAttr start = calcRange.get().getStart();
//				TimeWithDayAttr end = calcRange.get().getStart().lessThanOrEqualTo(attendance)?attendance:calcRange.get().getStart();
				TimeWithDayAttr end = calcRange.get().getStart();
				if(calcRange.get().getEnd().greaterThan(calcRange.get().getStart())) {
					end = attendance.greaterThan(calcRange.get().getEnd())?calcRange.get().getEnd():attendance;
				}else {
					//計算範囲の開始と終了が逆転している場合（遅刻していない場合）は時間帯を作成しない
					return Optional.empty();
				}
				
				TimeRoundingSetting roundingSet = otherEmTimezoneLateEarlySet.getRoundingSetByDedAtr(deductionAtr.isDeduction());
				
				LateLeaveEarlyTimeSheet lateLeaveEarlytimeSheet = new LateLeaveEarlyTimeSheet(
										new TimeSpanForDailyCalc(start,end),
										new TimeRoundingSetting(roundingSet.getRoundingTime(),roundingSet.getRounding()));
				
				List<TimeSheetOfDeductionItem> dpCopyDed = new ArrayList<>();
				deductionTimeSheet.getForDeductionTimeZoneList().forEach(tc -> {
					dpCopyDed.add(tc);
				});
				
				List<TimeSheetOfDeductionItem> dpCopyRec = new ArrayList<>();
				deductionTimeSheet.getForRecordTimeZoneList().forEach(tc -> {
					dpCopyRec.add(tc);
				});
				
				DeductionTimeSheet reNewdeductionTimeSheet = new DeductionTimeSheet(dpCopyDed,dpCopyRec,deductionTimeSheet.getBreakTimeOfDailyList(),deductionTimeSheet.getDailyGoOutSheet(),deductionTimeSheet.getShortTimeSheets());
				//大塚モードか判断_現状は常に大塚モード
				if(true) {
					//区分が休憩の時間帯を一旦削除
					reNewdeductionTimeSheet.getForDeductionTimeZoneList().removeIf(t -> t.getDeductionAtr().isBreak());
					reNewdeductionTimeSheet.getForRecordTimeZoneList().removeIf(t -> t.getDeductionAtr().isBreak());
					for(TimeSheetOfDeductionItem dedbreakTime:breakTimeFromMaster) {	
						reNewdeductionTimeSheet.getForDeductionTimeZoneList().add(dedbreakTime);
						reNewdeductionTimeSheet.getForRecordTimeZoneList().add(dedbreakTime);
					}				
				}
				List<TimeSheetOfDeductionItem> dudctionList = reNewdeductionTimeSheet.getDupliRangeTimeSheet(new TimeSpanForDailyCalc(start,end), deductionAtr);
				lateLeaveEarlytimeSheet.setDeductionTimeSheet(dudctionList);
				return Optional.of(lateLeaveEarlytimeSheet);
			}
		}
		return Optional.empty();
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
	
	/**
	 * 遅刻時間帯作成(流動勤務)
	 * @param deductionAtr 控除区分
	 * @param timeLeavingWork 出退勤
	 * @param predetermineTimeSet 所定時間設定(計算用クラス)
	 * @param commonSetting 就業時間帯の共通設定
	 * @param flowRestTime 流動勤務の休憩時間帯
	 * @param timeSheetOfDeductionItems 控除項目の時間帯
	 */
	public void createLateTimeSheetForFlow(
			DeductionAtr deductionAtr,
			TimeLeavingWork timeLeavingWork,
			PredetermineTimeSetForCalc predetermineTimeSet,
			WorkTimezoneCommonSet commonSetting,
			FlowWorkRestTimezone flowRestTime,
			List<TimeSheetOfDeductionItem> timeSheetOfDeductionItems) {
		
		//遅刻時間帯の作成
		this.setDecitionTimeSheet(
				deductionAtr,
				this.getLateTimeSheetForFlow(deductionAtr, predetermineTimeSet, timeLeavingWork, commonSetting));
		
		//流動休憩の場合、育児だけに絞り込む
		List<TimeSheetOfDeductionItem> deductionTimeItems = filteringLateTimeSheetForFlow(flowRestTime,timeSheetOfDeductionItems);
		
		//遅刻時間の計算
		//遅刻時間帯を再度補正
		this.setDecitionTimeSheet(
				deductionAtr,
				Optional.of(this.getDecitionTimeSheet(deductionAtr).get().collectionAgainOfLate(getDecitionTimeSheet(deductionAtr).get(), deductionTimeItems)));
		
		//計上の場合　インターバル免除時間帯を控除
		if(deductionAtr.isAppropriate()) {
			//createIntervalExemptionTimeSheet();
		}
	}
	
	/**
	 * 遅刻時間帯の作成(流動勤務)_所定時間帯の開始時刻～出勤時刻を遅刻時間帯にする
	 * @param deductionAtr 控除区分
	 * @param predetermineTimeSet 所定時間設定(計算用クラス)
	 * @param timeLeavingWork 出退勤
	 * @param commonSetting 就業時間帯の共通設定
	 * @return　遅刻早退時間帯
	 */
	private Optional<LateLeaveEarlyTimeSheet> getLateTimeSheetForFlow(
			DeductionAtr deductionAtr,
			PredetermineTimeSetForCalc predetermineTimeSet,
			TimeLeavingWork timeLeavingWork,
			WorkTimezoneCommonSet commonSetting) {
		
		//所定時間帯の開始時刻
		TimeWithDayAttr start = predetermineTimeSet.getTimeSheets().get(timeLeavingWork.getWorkNo().v()-1).getStart();
		
		//出勤時刻
		Optional<TimeWithDayAttr> end = timeLeavingWork.getAttendanceStamp().get().getStamp().get().getTimeDay().getTimeWithDay();
		if (!end.isPresent()) {
			return Optional.empty();
		}
		//丸め設定　控除の場合、控除時間丸め設定を参照。　計上の場合、時間丸め設定を参照
		TimeRoundingSetting rounding = commonSetting.getLateEarlySet().getOtherClassSets().stream()
				.filter(o -> o.getLateEarlyAtr().isLATE())
				.findFirst().get().getRoundingSetByDedAtr(deductionAtr.isDeduction());
		
		//遅刻を取り消したフラグをセット　まだ実装しなくていい
		
		//遅刻開始時刻←所定時間帯の開始時刻、遅刻終了時刻←出勤時刻
		return Optional.of(new LateLeaveEarlyTimeSheet(new TimeSpanForDailyCalc(start,end.get()),rounding));
	}
	
	/**
	 * 遅刻時間帯の計算(流動勤務)_控除時間帯を絞り込む
	 * @param flowRestTime 流動勤務の休憩時間帯
	 * @param timeSheetOfDeductionItems 控除項目の時間帯
	 * @return　控除項目の時間帯
	 */
	private List<TimeSheetOfDeductionItem> filteringLateTimeSheetForFlow(
			FlowWorkRestTimezone flowRestTime,
			List<TimeSheetOfDeductionItem> timeSheetOfDeductionItems) {
		
		List<TimeSheetOfDeductionItem> deductionTimeSheets;
		
		//固定休憩
		if(flowRestTime.isFixRestTime()){
			deductionTimeSheets = timeSheetOfDeductionItems.stream()
					.filter(t -> t.getDeductionAtr() == DeductionClassification.CHILD_CARE
							|| t.getDeductionAtr() == DeductionClassification.BREAK)
					.collect(Collectors.toList());
		}
		//流動休憩
		else {
			deductionTimeSheets = timeSheetOfDeductionItems.stream()
					.filter(t -> t.getDeductionAtr() == DeductionClassification.CHILD_CARE)
					.collect(Collectors.toList());
		}
		return deductionTimeSheets;
	}
	
	
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
	public void calcLateOffsetTime(
		DeductionAtr deductionAtr,
		CompanyHolidayPriorityOrder companyholidayPriorityOrder,
		TimevacationUseTimeOfDaily timeVacationUseTime) {
		
		this.getDecitionTimeSheet(deductionAtr).get().offsetProcessInPriorityOrder(
				deductionAtr,
				companyholidayPriorityOrder,
				timeVacationUseTime,
				this.OffsetTime.isPresent()?this.OffsetTime.get():DeductionOffSetTime.createAllZero());
	}
}
