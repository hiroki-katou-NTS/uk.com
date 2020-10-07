package nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailycalprocess.calculation.other;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import lombok.Getter;
import lombok.val;
import nts.gul.util.value.Finally;
import nts.uk.ctx.at.shared.dom.common.time.AttendanceTime;
import nts.uk.ctx.at.shared.dom.common.timerounding.TimeRoundingSetting;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.autocalsetting.ActualWorkTimeSheetAtr;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.autocalsetting.AutoCalRestTimeSetting;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.autocalsetting.AutoCalSetting;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.bonuspay.setting.BonusPaySetting;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.attendancetime.TimeLeavingWork;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.common.TimeDivergenceWithCalculation;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.holidayworktime.HolidayWorkFrameTime;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.holidayworktime.HolidayWorkFrameTimeSheet;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.paytime.SpecificDateAttrOfDailyAttd;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailycalprocess.calculation.ActualWorkingTimeSheet;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailycalprocess.calculation.DeductionTimeSheet;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailycalprocess.calculation.TimeSpanForDailyCalc;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailycalprocess.calculation.timezone.deductiontime.DeductionAtr;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailycalprocess.calculation.timezone.deductiontime.TimeSheetOfDeductionItem;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailycalprocess.calculation.timezone.someitems.BonusPayTimeSheetForCalc;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.midnighttimezone.MidNightTimeSheet;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.midnighttimezone.MidNightTimeSheetForCalc;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.ortherpackage.classfunction.SpecBonusPayTimeSheetForCalc;
import nts.uk.ctx.at.shared.dom.workrule.outsideworktime.holidaywork.HolidayWorkFrameNo;
import nts.uk.ctx.at.shared.dom.workrule.outsideworktime.holidaywork.StaturoryAtrOfHolidayWork;
import nts.uk.ctx.at.shared.dom.worktime.common.BreakFrameNo;
import nts.uk.ctx.at.shared.dom.worktime.common.EmTimezoneNo;
import nts.uk.ctx.at.shared.dom.worktime.common.HDWorkTimeSheetSetting;
import nts.uk.ctx.at.shared.dom.worktime.common.WorkTimezoneCommonSet;
import nts.uk.ctx.at.shared.dom.worktime.flowset.FlowWorkHolidayTimeZone;
import nts.uk.ctx.at.shared.dom.worktime.flowset.FlowWorkSetting;
import nts.uk.ctx.at.shared.dom.worktype.WorkType;
import nts.uk.shr.com.time.TimeWithDayAttr;

/**
 * 計算用休出枠時間帯
 * @author keisuke_hoshina
 *
 */
@Getter
public class HolidayWorkFrameTimeSheetForCalc extends ActualWorkingTimeSheet{
	
	//休出枠時間帯No
	private HolidayWorkFrameTime frameTime;
	
	//拘束時間として扱う
	private boolean TreatAsTimeSpentAtWork;
	
	//休出枠時間
	private EmTimezoneNo HolidayWorkTimeSheetNo; 
	
	//法定区分
	private Finally<StaturoryAtrOfHolidayWork> statutoryAtr;
	
	/**
	 * constructor
	 * @param timeSheet 時間帯(丸め付き)
	 * @param calculationTimeSheet 計算範囲
	 * @param deductionTimeSheets 控除項目の時間帯
	 * @param bonusPayTimeSheet 加給時間帯
	 * @param midNighttimeSheet 
	 * @param frameTime
	 * @param treatAsTimeSpentAtWork
	 * @param holidayWorkTimeSheetNo
	 */
	public HolidayWorkFrameTimeSheetForCalc(TimeSpanForDailyCalc timeSheet, TimeRoundingSetting rounding,
			List<TimeSheetOfDeductionItem> recorddeductionTimeSheets,
			List<TimeSheetOfDeductionItem> deductionTimeSheets, List<BonusPayTimeSheetForCalc> bonusPayTimeSheet,
			List<SpecBonusPayTimeSheetForCalc> specifiedBonusPayTimeSheet, Optional<MidNightTimeSheetForCalc> midNighttimeSheet,
			HolidayWorkFrameTime frameTime, boolean treatAsTimeSpentAtWork, EmTimezoneNo holidayWorkTimeSheetNo,
			Finally<StaturoryAtrOfHolidayWork> statutoryAtr) {
		super(timeSheet, rounding, recorddeductionTimeSheets, deductionTimeSheets, bonusPayTimeSheet, specifiedBonusPayTimeSheet,
				midNighttimeSheet);
		this.frameTime = frameTime;
		TreatAsTimeSpentAtWork = treatAsTimeSpentAtWork;
		HolidayWorkTimeSheetNo = holidayWorkTimeSheetNo;
		this.statutoryAtr = statutoryAtr;
	}
	

	/**
	 * constructor
	 * @param start 開始時刻
	 * @param end 終了時刻
	 * @param rounding 時間丸め設定
	 * @param deductionTimeSheets 控除項目の時間帯(List)
	 * @param frameTime 休出枠時間
	 * @param holidayWorkTimeSheetNo 就業時間帯NO
	 * @param statutoryAtr 休日出勤の法定区分
	 */
	public HolidayWorkFrameTimeSheetForCalc(
			TimeWithDayAttr start,
			TimeWithDayAttr end,
			TimeRoundingSetting rounding,
			List<TimeSheetOfDeductionItem> deductionTimeSheets,
			HolidayWorkFrameTime frameTime,
			EmTimezoneNo holidayWorkTimeSheetNo,
			Finally<StaturoryAtrOfHolidayWork> statutoryAtr) {
		super(new TimeSpanForDailyCalc(start, end),
				rounding,
				deductionTimeSheets,
				deductionTimeSheets,
				new ArrayList<>(),
				new ArrayList<>(),
				Optional.empty());
		this.frameTime = frameTime;
		this.TreatAsTimeSpentAtWork = false;
		this.HolidayWorkTimeSheetNo = holidayWorkTimeSheetNo;
		this.statutoryAtr = statutoryAtr;
	}
	
	/**
	 * 計算用休出枠時間帯リストの作成
	 * @param integrationOfDaily 
	 * @return
	 */
	public static List<HolidayWorkFrameTimeSheetForCalc> createHolidayTimeWorkFrame(TimeLeavingWork attendanceLeave,List<HDWorkTimeSheetSetting> holidayWorkTimeList,WorkType todayWorkType
																					,Optional<BonusPaySetting> bonuspaySetting,MidNightTimeSheet midNightTimeSheet,DeductionTimeSheet deductionTimeSheet
																					,Optional<WorkTimezoneCommonSet> commonSetting,Optional<SpecificDateAttrOfDailyAttd> specificDateAttrSheets) {
		List<HolidayWorkFrameTimeSheetForCalc> returnList = new ArrayList<>();
		for(HDWorkTimeSheetSetting holidayWorkTime:holidayWorkTimeList) {
			val duplicateTimeSpan = holidayWorkTime.getTimezone().timeSpan().getDuplicatedWith(attendanceLeave.getTimespan()); 
			if(duplicateTimeSpan.isPresent()) {
				returnList.add(createHolidayTimeWorkFrameTimeSheet(new TimeSpanForDailyCalc(duplicateTimeSpan.get()),holidayWorkTime,todayWorkType,bonuspaySetting,midNightTimeSheet,deductionTimeSheet,commonSetting, specificDateAttrSheets));
			}
		}
		return returnList;
	}
	
	/**
	 * 計算用休出枠時間帯から休出枠時間帯へ変換する
	 * @return　休出枠時間帯
	 */
	public HolidayWorkFrameTimeSheet changeNotWorkFrameTimeSheet() {
		return new HolidayWorkFrameTimeSheet(this.getFrameTime().getHolidayFrameNo(),this.timeSheet.getTimeSpan());
	}
	
	/**
	 * 計算用休出枠ｔ時間帯(WORK)の作成
	 * @param timeSpan 就業時間帯の休出和時間帯と打刻時間の重複時間帯
	 * @param integrationOfDaily 
	 * @return
	 */
	public static HolidayWorkFrameTimeSheetForCalc createHolidayTimeWorkFrameTimeSheet(TimeSpanForDailyCalc timeSpan,HDWorkTimeSheetSetting holidayWorkFrameTimeSheet,WorkType today
																					  ,Optional<BonusPaySetting> bonuspaySetting,MidNightTimeSheet midNightTimeSheet,DeductionTimeSheet deductionTimeSheet
																					  ,Optional<WorkTimezoneCommonSet> commonSetting,Optional<SpecificDateAttrOfDailyAttd> specificDateAttrSheets) {

		//時間帯跨いだ控除時間帯分割
		List<TimeSheetOfDeductionItem> dedTimeSheet = deductionTimeSheet.getDupliRangeTimeSheet(timeSpan, DeductionAtr.Deduction);
		dedTimeSheet.forEach(tc ->{
			tc.changeReverceRounding(tc.getRounding(), ActualWorkTimeSheetAtr.HolidayWork, DeductionAtr.Deduction, commonSetting);
		});
		List<TimeSheetOfDeductionItem> recordTimeSheet = deductionTimeSheet.getDupliRangeTimeSheet(timeSpan, DeductionAtr.Appropriate);
		recordTimeSheet.forEach(tc ->{
			tc.changeReverceRounding(tc.getRounding(), ActualWorkTimeSheetAtr.HolidayWork, DeductionAtr.Appropriate, commonSetting);
		});
		//控除時間帯を保持させる(継承先に)
		//控除の丸め
		//休出枠No
		BreakFrameNo breakFrameNo = holidayWorkFrameTimeSheet.decisionBreakFrameNoByHolidayAtr(today.getWorkTypeSetList().get(0).getHolidayAtr());
		/*加給*/
		val duplibonusPayTimeSheet = getBonusPayTimeSheetIncludeDedTimeSheet(bonuspaySetting, timeSpan, recordTimeSheet, recordTimeSheet);					 
		/*特定日*/
		val duplispecifiedBonusPayTimeSheet = getSpecBonusPayTimeSheetIncludeDedTimeSheet(bonuspaySetting, timeSpan, recordTimeSheet, recordTimeSheet, specificDateAttrSheets);
		/*深夜*/
		val duplicatemidNightTimeSheet = getMidNightTimeSheetIncludeDedTimeSheet(midNightTimeSheet, timeSpan, recordTimeSheet, recordTimeSheet,commonSetting);
		
		
		HolidayWorkFrameTime holidayTimeFrame = new HolidayWorkFrameTime(new HolidayWorkFrameNo(breakFrameNo.v().intValue()),
																			Finally.of(TimeDivergenceWithCalculation.sameTime(new AttendanceTime(0))),
																			Finally.of(TimeDivergenceWithCalculation.sameTime(new AttendanceTime(0))),
																			Finally.of(new AttendanceTime(0)));
		
		return new HolidayWorkFrameTimeSheetForCalc(timeSpan,
													holidayWorkFrameTimeSheet.getTimezone().getRounding(),
													dedTimeSheet.stream().map(tc ->tc.createWithExcessAtr()).collect(Collectors.toList()),
													recordTimeSheet.stream().map(tc ->tc.createWithExcessAtr()).collect(Collectors.toList()),
													duplibonusPayTimeSheet,
													duplispecifiedBonusPayTimeSheet,
													duplicatemidNightTimeSheet,
													holidayTimeFrame,
													false,
													new EmTimezoneNo(holidayWorkFrameTimeSheet.getWorkTimeNo()),
													Finally.of(StaturoryAtrOfHolidayWork.deicisionAtrByHolidayAtr(today.getWorkTypeSetList().get(0).getHolidayAtr())));
	}
	/**
	 * 残業時間帯時間枠に残業時間を埋める
	 * @param autoCalcSet 時間外の自動計算区分
	 * @return 残業時間枠時間帯クラス
	 */
	public HolidayWorkFrameTime calcOverTimeWorkTime(AutoCalRestTimeSetting autoCalcSet) {
		AttendanceTime holidayWorkTime;
		if(autoCalcSet.getLateNightTime().getCalAtr().isCalculateEmbossing()) {
			holidayWorkTime = new AttendanceTime(0);
		}
		else {
			holidayWorkTime = this.calcTotalTime();
		}
		return  new HolidayWorkFrameTime(this.frameTime.getHolidayFrameNo()
				,this.frameTime.getTransferTime()
				,Finally.of(TimeDivergenceWithCalculation.sameTime(holidayWorkTime))
				,this.frameTime.getBeforeApplicationTime());
	}
	
	/**
	 * 計算処理
	 * 休出時間の計算
	 * @param forceCalcTime 強制時間区分
	 * @param autoCalcSet 
	 */
	public AttendanceTime correctCalculationTime(AutoCalSetting autoCalcSet,DeductionAtr dedAtr) {
		return this.calcTotalTime();
	}

	
//	/**
//	 *　指定条件の控除項目だけの控除時間
//	 * @param forcsList
//	 * @param atr
//	 * @return
//	 */
//	public AttendanceTime forcs(List<TimeSheetOfDeductionItem> forcsList,ConditionAtr atr,DeductionAtr dedAtr){
//		AttendanceTime dedTotalTime = new AttendanceTime(0);
//		val loopList = this.getDedTimeSheetByAtr(dedAtr, atr);
//		for(TimeSheetOfDeductionItem deduTimeSheet: loopList) {
//			if(deduTimeSheet.checkIncludeCalculation(atr)) {
//				dedTotalTime = dedTotalTime.addMinutes(deduTimeSheet.calcTotalTime().valueAsMinutes());
//			}
//		}
//		return dedTotalTime;
//	}
	
	//＊＊＊＊＊＊＊＊＊＊＊＊＊＊＊＊＊＊＊＊＊＊＊＊＊＊＊＊＊＊＊＊＊＊＊＊＊＊＊＊＊＊＊＊＊＊＊＊＊＊＊
	
	/**
	 * 控除時間を考慮して終了時刻を求める(流動勤務時に使用)
	 * @return
	 */
	public HolidayWorkFrameTimeSheet collectHolidayWorkFrameTimeSheet(
			FlowWorkHolidayTimeZone fluWorkHolidayTimeSheet,
			WorkType workType,
			DeductionTimeSheet deductionTimeSheet,/*事前処理で作成した控除時間帯で良い？*/
			TimeSpanForDailyCalc collectCalcRange,/*計算範囲*/
			AttendanceTime previousElapsedTime/*前回の経過時間*/
			) {
		//今回の処理の経過時間
		AttendanceTime elapsedTime = fluWorkHolidayTimeSheet.getFlowTimeSetting().getElapsedTime();
		//休出枠の時間を計算する (今回処理する経過時間-前回の経過時間)
//		AttendanceTime holidayWorkFrameTime =  new AttendanceTime(elapsedTime.valueAsMinutes()-previousElapsedTime.valueAsMinutes());
		//休出枠時間から終了時刻を計算する
		TimeWithDayAttr endClock = collectCalcRange.getStart().backByMinutes(elapsedTime.valueAsMinutes());
		//休出枠時間帯　（一時的に作成）
		TimeSpanForDailyCalc holidayWorkFrameTimeSheet = new TimeSpanForDailyCalc(collectCalcRange.getStart(),endClock);
		//控除時間帯分ループ
		for(TimeSheetOfDeductionItem timeSheetOfDeductionItem : deductionTimeSheet.getForDeductionTimeZoneList()) {
			TimeSpanForDailyCalc duplicateTime = holidayWorkFrameTimeSheet.getDuplicatedWith(timeSheetOfDeductionItem.getTimeSheet()).orElse(null);
			if(duplicateTime!=null) {//重複している場合の処理
				//控除項目の時間帯に法定内区分をセット
				//TODO: createBreakTimeSheetAsFixed
//				timeSheetOfDeductionItem = timeSheetOfDeductionItem.createBreakTimeSheetAsFixed(
//						timeSheetOfDeductionItem.getTimeSheet().getSpan(),
//						timeSheetOfDeductionItem.getGoOutReason(),
//						timeSheetOfDeductionItem.getBreakAtr(),
//						timeSheetOfDeductionItem.getDeductionAtr(),
//						WithinStatutoryAtr.WithinStatutory);
				//控除時間分、終了時刻を遅くする
//				TimeSpanForDailyCalc collectTimeSheet = this.timeSheet.timeSpan().shiftEndBack(duplicateTime.lengthAsMinutes());
//				TimeZoneRounding newTimeSheet = this.timeSheet;
				// ここはベトナムへ連絡後コメントアウトを外すnewTimeSheet.newTimeSpan(collectTimeSheet);
			}	
		}
		
		//休出枠NOを判断
		
		//控除時間帯に丸め設定を付与

		//加給時間帯を作成
		
		//深夜時間帯を作成
		
		//TODO: return HolidayWorkFrameTimeSheet
//		HolidayWorkFrameTimeSheet holidayWorkFrameTimeSheet2 =  new HolidayWorkFrameTimeSheet();
		return null;
	}

	/**
	 * 休出枠時間帯の作成
	 * @param todayWorkType 当日の勤務種類
	 * @param flowWorkSetting 流動勤務設定
	 * @param timeSheetOfDeductionItems 控除項目の時間帯(List)
	 * @param holidayStartEnd 休出開始終了時刻
	 * @param bonusPaySetting 加給設定
	 * @param specDateAttr 日別実績の特定日区分
	 * @param midNightTimeSheet 深夜時間帯
	 * @param processingTimezone 流動休出時間帯
	 * @return 計算用休出枠時間帯
	 */
	public static HolidayWorkFrameTimeSheetForCalc createAsFlow(
			WorkType todayWorkType,
			FlowWorkSetting flowWorkSetting,
			List<TimeSheetOfDeductionItem> timeSheetOfDeductionItems,
			TimeSpanForDailyCalc holidayStartEnd,
			Optional<BonusPaySetting> bonusPaySetting,
			Optional<SpecificDateAttrOfDailyAttd> specDateAttr,
			MidNightTimeSheet midNightTimeSheet,
			FlowWorkHolidayTimeZone processingTimezone) {
		
		//終了時刻の計算
		TimeWithDayAttr endTime = HolidayWorkFrameTimeSheetForCalc.calcEndTimeForFlow(
				processingTimezone,
				flowWorkSetting.getOffdayWorkTimezone().getLstWorkTimezone(),
				timeSheetOfDeductionItems,
				holidayStartEnd);
		
		//休出枠時間を作成
		HolidayWorkFrameTime frame = new HolidayWorkFrameTime(
				new HolidayWorkFrameNo(processingTimezone.getBreakFrameNoToHolidayAtr(todayWorkType.getHolidayAtr().get()).v().intValue()),
				Finally.of(TimeDivergenceWithCalculation.sameTime(new AttendanceTime(0))),
				Finally.of(TimeDivergenceWithCalculation.sameTime(new AttendanceTime(0))),
				Finally.of(new AttendanceTime(0)));
		
		//休出枠時間帯を作成
		HolidayWorkFrameTimeSheetForCalc holidayWorkFrameTimeSheet = new HolidayWorkFrameTimeSheetForCalc(
				holidayStartEnd.getStart(),
				endTime,
				processingTimezone.getFlowTimeSetting().getRounding(),
				timeSheetOfDeductionItems,
				frame,
				new EmTimezoneNo(processingTimezone.getWorktimeNo()),
				Finally.of(StaturoryAtrOfHolidayWork.deicisionAtrByHolidayAtr(todayWorkType.getHolidayAtr().get())));
		
		//計上、控除時間帯を補正
		holidayWorkFrameTimeSheet.trimRecordedAndDeductionToSelfRange();
		//控除時間帯に丸め設定を付与
		holidayWorkFrameTimeSheet.grantRoundingToDeductionTimeSheet(ActualWorkTimeSheetAtr.OverTimeWork, flowWorkSetting.getCommonSetting());
		//加給時間帯を作成
		holidayWorkFrameTimeSheet.createBonusPayTimeSheet(bonusPaySetting, specDateAttr);
		//深夜時間帯を作成
		holidayWorkFrameTimeSheet.createMidNightTimeSheet(midNightTimeSheet, Optional.of(flowWorkSetting.getCommonSetting()));
		
		return holidayWorkFrameTimeSheet;
	}
	
	/**
	 * 終了時刻の計算（流動）
	 * @param processingHolidayTimeZone 処理中の流動休出時間帯
	 * @param holidayTimezones 流動休出時間帯(List)
	 * @param timeSheetOfDeductionItems 控除項目の時間帯(List)
	 * @param holidayStartEnd 休出開始時刻
	 * @param leaveStampTime 退勤時刻
	 * @return 終了時刻
	 */
	private static TimeWithDayAttr calcEndTimeForFlow(
			FlowWorkHolidayTimeZone processingHolidayTimeZone,
			List<FlowWorkHolidayTimeZone> holidayTimezones,
			List<TimeSheetOfDeductionItem> timeSheetOfDeductionItems,
			TimeSpanForDailyCalc holidayStartEnd) {
		
		Optional<FlowWorkHolidayTimeZone> plusOneHolidayTimezone = holidayTimezones.stream()
				.filter(timezone -> timezone.getWorktimeNo().equals(processingHolidayTimeZone.getWorktimeNo()+1))
				.findFirst();
		
		TimeWithDayAttr endTime = TimeWithDayAttr.THE_PRESENT_DAY_0000;
		
		if(plusOneHolidayTimezone.isPresent()) {
			//休出枠の時間を計算する
			AttendanceTime holidayFrameTime = plusOneHolidayTimezone.get().getFlowTimeSetting().getElapsedTime().minusMinutes(
					processingHolidayTimeZone.getFlowTimeSetting().getElapsedTime().valueAsMinutes());
			
			//休出枠時間から終了時刻を計算する
			endTime = holidayStartEnd.getStart().forwardByMinutes(holidayFrameTime.valueAsMinutes());
			
			TimeSpanForDailyCalc timeSpan = new TimeSpanForDailyCalc(holidayStartEnd.getStart(), endTime);
			
			//控除時間分、終了時刻をズラす
			endTime = timeSpan.forwardByDeductionTime(timeSheetOfDeductionItems);
			
			//間にinput.退勤時刻があるか確認、input.退勤時刻に置き換え
			if(timeSpan.contains(holidayStartEnd.getEnd())) endTime = holidayStartEnd.getEnd();
		}
		else {
			endTime = holidayStartEnd.getEnd();
		}
		return endTime;
	}
}
