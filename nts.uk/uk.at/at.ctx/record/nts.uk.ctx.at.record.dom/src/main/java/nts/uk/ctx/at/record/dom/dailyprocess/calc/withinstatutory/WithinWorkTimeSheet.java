package nts.uk.ctx.at.record.dom.dailyprocess.calc.withinstatutory;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import lombok.AllArgsConstructor;
import lombok.val;
import nts.uk.ctx.at.record.dom.MidNightTimeSheet;
import nts.uk.ctx.at.record.dom.bonuspay.autocalc.BonusPayAutoCalcSet;
import nts.uk.ctx.at.record.dom.calculationattribute.CalAttrOfDailyPerformance;
import nts.uk.ctx.at.record.dom.daily.TimeWithCalculation;
import nts.uk.ctx.at.record.dom.daily.TimevacationUseTimeOfDaily;
import nts.uk.ctx.at.record.dom.daily.bonuspaytime.BonusPayTime;
import nts.uk.ctx.at.record.dom.daily.midnight.WithinStatutoryMidNightTime;
import nts.uk.ctx.at.record.dom.dailyprocess.calc.ActualWorkTimeSheetAtr;
import nts.uk.ctx.at.record.dom.dailyprocess.calc.BonusPayAtr;
import nts.uk.ctx.at.record.dom.dailyprocess.calc.DeductionOffSetTime;
import nts.uk.ctx.at.record.dom.dailyprocess.calc.DeductionTimeSheet;
import nts.uk.ctx.at.record.dom.dailyprocess.calc.LateLeaveEarlyManagementTimeSheet;
import nts.uk.ctx.at.record.dom.dailyprocess.calc.PredetermineTimeSetForCalc;
import nts.uk.ctx.at.shared.dom.bonuspay.setting.BonusPaySetting;
import nts.uk.ctx.at.shared.dom.bonuspay.setting.BonusPayTimesheet;
import nts.uk.ctx.at.shared.dom.bonuspay.setting.SpecBonusPayTimesheet;
import nts.uk.ctx.at.shared.dom.common.time.AttendanceTime;
import nts.uk.ctx.at.shared.dom.common.time.TimeSpanForCalc;
import nts.uk.ctx.at.shared.dom.ot.autocalsetting.AutoCalAtrOvertime;
import nts.uk.ctx.at.shared.dom.vacation.setting.addsettingofworktime.CalculationByActualTimeAtr;
import nts.uk.ctx.at.shared.dom.vacation.setting.addsettingofworktime.HolidayAdditionAtr;
import nts.uk.ctx.at.shared.dom.worktime.common.AmPmAtr;
import nts.uk.ctx.at.shared.dom.worktime.common.EmTimeZoneSet;
import nts.uk.ctx.at.shared.dom.worktime.common.WorkTimezoneCommonSet;
import nts.uk.ctx.at.shared.dom.worktime.fixedset.FixedWorkSetting;
import nts.uk.ctx.at.shared.dom.worktime.predset.PredetemineTimeSetting;
import nts.uk.ctx.at.shared.dom.worktype.AttendanceHolidayAttr;
import nts.uk.ctx.at.shared.dom.worktype.WorkType;
import nts.uk.shr.com.time.TimeWithDayAttr;

/**
 * 就業時間内時間帯
 * @author keisuke_hoshina
 *
 */
@AllArgsConstructor
public class WithinWorkTimeSheet implements LateLeaveEarlyManagementTimeSheet{

	//必要になったら追加
	//private WorkingHours
	//private RaisingSalaryTime
	//private Optional<> flexTimeSheet;
	private final List<WithinWorkTimeFrame> withinWorkTimeFrame;
//	private final List<LeaveEarlyDecisionClock> leaveEarlyDecisionClock;
//	private final List<LateDecisionClock> lateDecisionClock;
//	private List<LateTimeOfDaily> lateTimeOfDaily;
//	private final FlexWithinWorkTimeSheet flexTimeSheet;
	
	
	
	/**
	 * 就業時間内時間帯
	 * 
	 * @param workType 勤務種類クラス
	 * @param predetermineTimeSet 所定時間設定クラス
	 * @param fixedWorkSetting 固定勤務設定クラス
	 * @param workTimeCommonSet 就業時間帯の共通設定
	 * @param deductionTimeSheet 控除時間帯
	 * @param bonusPaySetting 加給設定
	 * @return 就業時間内時間帯
	 */
	public static WithinWorkTimeSheet createAsFixed(WorkType workType,
													PredetemineTimeSetting predetermineTimeSet,
													FixedWorkSetting fixedWorkSetting,
													WorkTimezoneCommonSet workTimeCommonSet,
													DeductionTimeSheet deductionTimeSheet,
													BonusPaySetting bonusPaySetting) {
		
		List<WithinWorkTimeFrame> timeFrames = new ArrayList<>();
		if(workType.isWeekDayAttendance()) {
			timeFrames = isWeekDayProcess(workType,predetermineTimeSet,fixedWorkSetting,workTimeCommonSet
									 							,deductionTimeSheet,bonusPaySetting);
		}
		return new WithinWorkTimeSheet(timeFrames);
	}

	/**
	 * 就業時間内時間帯の作成
	 * @param workType　勤務種類クラス
	 * @param predetermineTimeSet 所定時間設定クラス
	 * @param fixedWorkSetting  固定勤務設定クラス
	 * @return 就業時間内時間帯クラス
	 */
	private static List<WithinWorkTimeFrame> isWeekDayProcess(
			WorkType workType,
			PredetemineTimeSetting predetermineTimeSet,
			FixedWorkSetting fixedWorkSetting,
			WorkTimezoneCommonSet workTimeCommonSet,
			DeductionTimeSheet deductionTimeSheet,
			BonusPaySetting bonusPaySetting
			) {
		

		PredetermineTimeSetForCalc predetermineTimeForSet = PredetermineTimeSetForCalc.convertFromAggregatePremiumTime(predetermineTimeSet);

//		//遅刻猶予時間の取得
//		val lateGraceTime = workTimeCommonSet.getLateSetting().getGraceTimeSetting();//引数でworkTimeCommonSet毎渡すように修正予定
//		//早退猶予時間の取得
//		val leaveEarlyGraceTime = workTimeCommonSet.getLeaveEarlySetting().getGraceTimeSetting();
						
		val timeFrames = new ArrayList<WithinWorkTimeFrame>();
		WithinWorkTimeFrame timeFrame;
		val workingHourSet = createWorkingHourSet(workType, predetermineTimeForSet , fixedWorkSetting);
		
		for (int frameNo = 0; frameNo < workingHourSet.toArray().length; frameNo++) {
			List<BonusPayTimesheet> bonusPayTimeSheet = new ArrayList<>();
			List<SpecBonusPayTimesheet> specifiedBonusPayTimeSheet = new ArrayList<>();
			Optional<MidNightTimeSheet> midNightTimeSheet;
			for(EmTimeZoneSet duplicateTimeSheet :workingHourSet) {
				//DeductionTimeSheet deductionTimeSheet = /*控除時間を分割する*/
				timeFrame = new WithinWorkTimeFrame(frameNo, duplicateTimeSheet.getTimezone(),duplicateTimeSheet.getTimezone().timeSpan(),deductionTimeSheet.getForDeductionTimeZoneList(),Collections.emptyList(),Optional.empty(),Collections.emptyList());
				/*加給*/
				bonusPayTimeSheet = bonusPaySetting.createDuplicationBonusPayTimeSheet(duplicateTimeSheet.getTimezone().timeSpan());
				specifiedBonusPayTimeSheet = bonusPaySetting.createDuplicationSpecifyBonusPay(duplicateTimeSheet.getTimezone().timeSpan());
				/*深夜*/
				midNightTimeSheet = timeFrame.createMidNightTimeSheet();
				
				timeFrames.add(new WithinWorkTimeFrame(timeFrame.getWorkingHoursTimeNo(),timeFrame.getTimeSheet(),timeFrame.getCalcrange(),timeFrame.getDeductionTimeSheet(),bonusPayTimeSheet,midNightTimeSheet,specifiedBonusPayTimeSheet));
			}
		}
		
		/*所定内割増時間の時間帯作成*/
		
		return timeFrames;
//				,
//				LeaveEarlyDecisionClock.createListOfAllWorks(predetermineTimeSet, deductionTimeSheet, leaveEarlyGraceTime),
//				LateDecisionClock.createListOfAllWorks(predetermineTimeSet, deductionTimeSheet, lateGraceTime));
	}
	
	/**
	 * 遅刻・早退時間を控除する
	 */
	public void deductLateAndLeaveEarly() {
		
	}
	
	/**
	 * 指定した枠番の就業時間内時間枠を返す
	 * @param frameNo
	 * @return
	 */
	public WithinWorkTimeFrame getFrameAt(int frameNo) {
		return this.withinWorkTimeFrame.get(frameNo);
	}

	/**
	 *  所定時間と重複している時間帯の判定
	 * @param workType　勤務種類クラス
	 * @param predetermineTimeSet 所定時間設定クラス
	 * @param fixedWorkSetting 固定勤務設定クラス
	 * @return 所定時間と重複している時間帯
	 */
	public static List<EmTimeZoneSet> createWorkingHourSet(WorkType workType, PredetermineTimeSetForCalc predetermineTimeSet,
			FixedWorkSetting fixedWorkSetting) {
		
		val attendanceHolidayAttr = workType.getAttendanceHolidayAttr();
		val emTimeZoneSet = getWorkingHourSetByAmPmClass(fixedWorkSetting, attendanceHolidayAttr);
		return extractBetween(
				emTimeZoneSet,
				new TimeWithDayAttr(predetermineTimeSet.getStartOneDayTime().valueAsMinutes()),
				new TimeWithDayAttr(predetermineTimeSet.getStartOneDayTime().valueAsMinutes() + predetermineTimeSet.getOneDayRange().valueAsMinutes()));
	}

	/**
	 * 所定時間帯と重複している就業時間帯設定時間を取り出す。
	 * @param start 開始時刻
	 * @param end　終了時刻
	 */
	private static List<EmTimeZoneSet> extractBetween(List<EmTimeZoneSet> timeZoneList,TimeWithDayAttr start,TimeWithDayAttr end){
		List<EmTimeZoneSet> returnList = new ArrayList<>();
		timeZoneList.forEach(source ->{ source.getTimezone().timeSpan().getDuplicatedWith(new TimeSpanForCalc(start, end)).ifPresent(duplicated -> {
											returnList.add(source.newSpanWith(duplicated.getStart(), duplicated.getEnd()));
										});
									});
		return returnList;
	}
	/**
	 * 平日出勤の出勤時間帯を取得
	 * @param fixedWorkSetting 固定勤務設定クラス
	 * @param attendanceHolidayAttr 出勤休日区分
	 * @return 出勤時間帯
	 */
	private static List<EmTimeZoneSet> getWorkingHourSetByAmPmClass(
			FixedWorkSetting fixedWorkSetting,
			AttendanceHolidayAttr attendanceHolidayAttr) {
		
		switch (attendanceHolidayAttr) {
		case FULL_TIME:
		case HOLIDAY:
			return fixedWorkSetting.getLstHalfDayWorkTimezone().stream().filter(tc -> tc.getDayAtr().equals(AmPmAtr.ONE_DAY)).collect(Collectors.toList()).get(0).getWorkTimezone().getLstWorkingTimezone();
		case MORNING:
			return fixedWorkSetting.getLstHalfDayWorkTimezone().stream().filter(tc -> tc.getDayAtr().equals(AmPmAtr.AM)).collect(Collectors.toList()).get(0).getWorkTimezone().getLstWorkingTimezone();
		case AFTERNOON:
			return fixedWorkSetting.getLstHalfDayWorkTimezone().stream().filter(tc -> tc.getDayAtr().equals(AmPmAtr.PM)).collect(Collectors.toList()).get(0).getWorkTimezone().getLstWorkingTimezone();
		default:
			throw new RuntimeException("unknown attendanceHolidayAttr" + attendanceHolidayAttr);
		}
	}
	
//	/**
//	 * 引数のNoと一致する遅刻判断時刻を取得する
//	 * @param workNo
//	 * @return　遅刻判断時刻
//	 */
//	public LateDecisionClock getlateDecisionClock(int workNo) {
//		List<LateDecisionClock> clockList = this.lateDecisionClock.stream().filter(tc -> tc.getWorkNo()==workNo).collect(Collectors.toList());
//		if(clockList.size()>1) {
//			throw new RuntimeException("Exist duplicate workNo : " + workNo);
//		}
//		return clockList.get(0);
//	}
//	
//	/**
//	 * 引数のNoと一致する早退判断時刻を取得する
//	 * @param workNo
//	 * @return　早退判断時刻
//	 */
//	public LeaveEarlyDecisionClock getleaveEarlyDecisionClock(int workNo) {
//		List<LeaveEarlyDecisionClock> clockList = this.leaveEarlyDecisionClock.stream().filter(tc -> tc.getWorkNo()==workNo).collect(Collectors.toList());
//		if(clockList.size()>1) {
//			throw new RuntimeException("Exist duplicate workNo : " + workNo);
//		}
//		return clockList.get(0);
//	}
	
//	/**
//	 * コアタイムのセット
//	 * @param coreTimeSetting コアタイム時間設定
//	 */
//	public WithinWorkTimeSheet createWithinFlexTimeSheet(CoreTimeSetting coreTimeSetting) {
//		List<FlexWithinWorkTimeSheet> duplicateCoreTimeList = new ArrayList<>();
//		for(WithinWorkTimeFrame workTimeFrame : this.withinWorkTimeFrame) {
//			Optional<TimeSpanForCalc> duplicateSpan = workTimeFrame.getCalcrange().getDuplicatedWith(coreTimeSetting.getCoreTime().getSpan()); 
//			if(duplicateSpan.isPresent()) {
//				duplicateCoreTimeList.add(new FlexWithinWorkTimeSheet(duplicateSpan.get().getSpan()));
//			}
//		}
//		TimeSpanForCalc coreTime = new TimeSpanForCalc(new TimeWithDayAttr(),new TimeWithDayAttr())
//		/*フレックス時間帯に入れる*/
//		return new WithinWorkTimeSheet(this.withinWorkTimeFrame,this.leaveEarlyDecisionClock,this.lateDecisionClock,new FlexWithinWorkTimeSheet());
//	}
	
	
	/**
	 * 就業時間(法定内用)の計算
	 * @param calcActualTime 実働のみで計算する
	 * @param dedTimeSheet　控除時間帯
	 * @return 就業時間の計算結果
	 */
	public AttendanceTime calcWorkTimeForStatutory(CalculationByActualTimeAtr calcActualTime,DeductionTimeSheet dedTimeSheet) {
		return calcWorkTime(HolidayAdditionAtr.HolidayAddition.convertFromCalcByActualTimeToHolidayAdditionAtr(calcActualTime),dedTimeSheet);
	}
	
	
	/**
	 * 就業時間の計算(控除時間差し引いた後) →　ループ処理　
	 * @return 就業時間
	 */
	public AttendanceTime calcWorkTime(HolidayAdditionAtr holidayAdditionAtr,DeductionTimeSheet dedTimeSheet) {
		
		AttendanceTime workTime = calcWorkTimeBeforeDeductPremium(holidayAdditionAtr ,dedTimeSheet);
		if(holidayAdditionAtr.isHolidayAddition()) {
			/*休暇加算時間を計算*/
			/*休暇加算時間を加算*/
//			if(/*時間休暇加算時間*/) {
//			}
		}
		return workTime;
	}
	
	/**
	 * 就業時間内時間枠の全枠分の就業時間を算出する
	 * (所定内割増時間を差し引く前)
	 * @return 就業時間
	 */
	public AttendanceTime calcWorkTimeBeforeDeductPremium(HolidayAdditionAtr holidayAdditionAtr,DeductionTimeSheet dedTimeSheet) {
		AttendanceTime workTime = new AttendanceTime(0);
		for(WithinWorkTimeFrame copyItem: withinWorkTimeFrame) {
			workTime.addMinutes(copyItem.calcActualWorkTimeAndWorkTime(holidayAdditionAtr,dedTimeSheet).valueAsMinutes());
		}
		return workTime;
	}
	
	
//	/**
//	 * 日別計算の遅刻早退時間の計算
//	 * @return
//	 */
//	public int calcLateLeaveEarlyinWithinWorkTime() {
//		for(WithinWorkTimeFrame workTimeFrame : withinWorkTimeFrame) {
//			workTimeFrame.correctTimeSheet(dailyWork, timeSheet, predetermineTimeSetForCalc);
//		}
//	}
	
//	//＊＊＊＊＊＊＊＊＊＊＊＊＊＊＊＊＊＊＊＊＊＊＊＊＊＊＊＊＊＊＊＊＊＊＊＊＊＊＊＊＊＊＊＊＊＊＊＊＊＊＊
//	
//	//就業時間内時間帯クラスを作成　　（流動勤務）
//	public WithinWorkTimeSheet createAsFluidWork(
//			PredetermineTimeSetForCalc predetermineTimeSetForCalc,
//			WorkType workType,
//			WorkInfoOfDailyPerformance workInformationOfDaily,
//			FluidWorkSetting fluidWorkSetting,
//			DeductionTimeSheet deductionTimeSheet) {
//		//開始時刻を取得
//		TimeWithDayAttr startClock = getStartClock();
//		//所定時間帯、残業開始を補正
//		cllectPredetermineTimeAndOverWorkTimeStart();
//		//残業開始となる経過時間を取得
//		AttendanceTime elapsedTime = fluidWorkSetting.getWeekdayWorkTime().getWorkTimeSheet().getMatchWorkNoOverTimeWorkSheet(1).getFluidWorkTimeSetting().getElapsedTime();
//		//経過時間から終了時刻を計算
//		TimeWithDayAttr endClock = startClock.backByMinutes(elapsedTime.valueAsMinutes());
//		//就業時間帯の作成（一時的に作成）
//		TimeSpanForCalc workTimeSheet = new TimeSpanForCalc(startClock,endClock);
//		//控除時間帯を取得 (控除時間帯分ループ）
//		for(TimeSheetOfDeductionItem timeSheetOfDeductionItem : deductionTimeSheet.getForDeductionTimeZoneList()) {
//			//就業時間帯に重複する控除時間を計算
//			TimeSpanForCalc duplicateTime = workTimeSheet.getDuplicatedWith(timeSheetOfDeductionItem.getTimeSheet().getSpan()).orElse(null);
//			//就業時間帯と控除時間帯が重複しているかチェック
//			if(duplicateTime!=null) {
//				//控除項目の時間帯に法定内区分をセット
//				timeSheetOfDeductionItem = new TimeSheetOfDeductionItem(
//						timeSheetOfDeductionItem.getTimeSheet().getSpan(),
//						timeSheetOfDeductionItem.getGoOutReason(),
//						timeSheetOfDeductionItem.getBreakAtr(),
//						timeSheetOfDeductionItem.getDeductionAtr(),
//						WithinStatutoryAtr.WithinStatutory);
//				//控除時間分、終了時刻をズラす
//				endClock.backByMinutes(duplicateTime.lengthAsMinutes());
//				//休暇加算するかチェックしてズラす
//				
//			}		
//		}
//		//就業時間内時間帯クラスを作成
//		
//		
//		
//	}
//	
//	/**
//	 * 開始時刻を取得　　（流動勤務（平日・就内））
//	 * @return
//	 */
//	public TimeWithDayAttr getStartClock() {
//		
//	}
//	
//	
//	//所定時間帯、残業開始を補正
//	public void cllectPredetermineTimeAndOverWorkTimeStart(
//			PredetermineTimeSetForCalc predetermineTimeSetForCalc,
//			WorkType workType,
//			WorkInfoOfDailyPerformance workInformationOfDaily) {
//		//所定時間帯を取得
//		predetermineTimeSetForCalc.correctPredetermineTimeSheet(workType.getDailyWork());
//		//予定所定時間が変更された場合に所定時間を変更するかチェック
//		//勤務予定と勤務実績の勤怠情報を比較
//		//勤務種類が休日出勤でないかチェック
//		if(
//				!workInformationOfDaily.isMatchWorkInfomation()||
//				workType.getDailyWork().isHolidayWork()
//				) {
//			return;
//		}
//		//就業時間帯の所定時間と予定時間を比較
//			
//		//計算用所定時間設定を所定終了ずらす時間分ズラす
//		
//		//流動勤務時間帯設定の残業時間帯を所定終了ずらす時間分ズラす
//		
//	}
//	
//	
//	/**
//	 * 遅刻時間の計算　（遅刻時間帯の作成）
//	 * 呼び出す時に勤務No分ループする前提で記載
//	 * @return 日別実績の遅刻時間
//	 */
//	public LateTimeOfDaily calcLateTime(
//			boolean clacification,/*遅刻早退の自動計算設定.遅刻　←　どこが持ってるか不明*/
//			boolean deducttionClacification,/*控除設定　←　何を参照すればよいのか不明*/
//			int workNo) {
//		
//		//勤務Noに一致する遅刻時間をListで取得する
//		List<LateTimeSheet> lateTimeSheetList = getMatchWorkNoLateTimeSheetList(workNo).orElse(null);
//		
//		LateTimeSheet lateTimeSheet;
//		//遅刻時間帯を１つの時間帯にする。
//		if(lateTimeSheetList!=null) {
//			//ここの処理で保科君が考えてくれた処理を組み込む
//			lateTimeSheet = createBondLateTimeSheet(workNo,lateTimeSheetList);
//		}
//
//		//遅刻計上時間の計算  ←　1つのメソッドとして出すこと
//		int calcTime = lateTimeSheet.getForRecordTimeSheet().get().calcTotalTime();
//		TimeWithCalculation lateTime = calcClacificationjudge(clacification, calcTime);
//		
//		//遅刻控除時間の計算 ←　1つのメソッドとして出すこと
//		TimeWithCalculation lateDeductionTime;
//		if(deducttionClacification) {//控除する場合
//			int calcTime2 = lateTimeSheet.getForDeducationTimeSheet().get().calcTotalTime();
//			lateDeductionTime =  calcClacificationjudge(clacification, calcTime2);
//		}else {//控除しない場合
//			lateDeductionTime = TimeWithCalculation.sameTime(new AttendanceTime(0));
//		}
//		
//		//相殺時間の計算
//		
//		//計上用時間帯から相殺時間を控除する
//		
//		LateTimeOfDaily lateTimeOfDaily = new LateTimeOfDaily();
//		return lateTimeOfDaily;
//	}
//	
//	/***
//	 * 勤務Noに一致する遅刻時間をListで取得する
//	 * @return
//	 */
//	public Optional<List<LateTimeSheet>> getMatchWorkNoLateTimeSheetList(int workNo){
//		//<<interface>>遅刻早退管理時間帯が持っているはずの遅刻時間帯<List>
//		List<LateTimeSheet> oldlateTimeSheetList;
//		//遅刻時間帯を１つの時間帯にする。
//		List<LateTimeSheet> lateTimeSheetList = oldlateTimeSheetList.stream().filter(ts -> ts.getWorkNo()==workNo).collect(Collectors.toList());
//		if(lateTimeSheetList==null) {
//			return Optional.empty();
//		}
//		return Optional.of(lateTimeSheetList);
//	}
//	
//	/**
//	 * 遅刻時間帯を１つの時間帯にする。
//	 * @param workNo
//	 * @return
//	 */
//	public LateTimeSheet createBondLateTimeSheet(
//			int workNo,
//			List<LateTimeSheet> lateTimeSheetList) {
//		//計上用時間帯のみのリストを作成
//		List<TimeSpanForCalc> forRecordTimeSheetList = 
//				lateTimeSheetList.stream().map(ts -> ts.getForRecordTimeSheet().get()).collect(Collectors.toList());
//		//1つの時間帯に結合
//		TimeSpanForCalc forRecordTimeSheet = bondTimeSpan(forRecordTimeSheetList);
//		
//		//控除用時間帯のみのリストを作成
//		List<TimeSpanForCalc> forDeductionTimeSheetList = 
//				lateTimeSheetList.stream().map(ts -> ts.getForDeducationTimeSheet().get()).collect(Collectors.toList());
//		//1つの時間帯に結合
//		TimeSpanForCalc forDeductionTimeSheet = bondTimeSpan(forRecordTimeSheetList);
//		
//		return LateTimeSheet.createAsLate(
//				forRecordTimeSheet,
//				forDeductionTimeSheet,
//				workNo,
//				Optional.empty(),
//				Optional.empty());
//	}
//	
	/**
	 * 渡した時間帯(List)を1つの時間帯に結合する
	 * @param list
	 * @return
	 */
	public TimeSpanForCalc bondTimeSpan(List<TimeSpanForCalc> list) {
		TimeWithDayAttr start = list.stream().map(ts -> ts.getStart()).min(Comparator.naturalOrder()).get();
		TimeWithDayAttr end =  list.stream().map(ts -> ts.getEnd()).max(Comparator.naturalOrder()).get();
		TimeSpanForCalc bondTimeSpan = new TimeSpanForCalc(start, end);
		return bondTimeSpan;
	}


	/**
	 * 指定された計算区分を基に計算付き時間帯を作成する
	 * @return
	 */
	public TimeWithCalculation calcClacificationjudge(boolean clacification , int calcTime) {
		if(clacification) {
			return TimeWithCalculation.sameTime(new AttendanceTime(calcTime));
		}else {
			return TimeWithCalculation.createTimeWithCalculation(new AttendanceTime(0),new AttendanceTime(calcTime));
		}
	}
	
//	//遅刻早退時間帯（List）を1つの遅刻早退時間帯に結合する
//	public LateLeaveEarlyTimeSheet connect(List<LateLeaveEarlyTimeSheet> timeSheetList){
//		 return new LeaveEarlyTimeSheet(createMinStartMaxEndSpanForTimeSheet(timeSheetList),
//		 								createMinStartMaxEndSpanForCalcRange(timeSheetList),
//		 								extract(List<LeaveEarlyTimeSheet> timeSheetList),
//		 								Optional.empty(),
//		 								Optional.empty(),
//		 								Optional.empty());
//	}
//	//遅刻早退時間帯（List）の時間帯（丸め付）を1つの時間帯（丸め付）にする
//	public TimeSpanWithRounding createMinStartMaxEndSpanForTimeSheet(List<LateLeaveEarlyTimeSheet> timeSheetList){
//		TimeWithDayAttr start = timeSheetList.stream().map(ts -> ts.getTimeSheet().getStart()).min(Comparator.naturalOrder()).get();
//		TimeWithDayAttr end =  timeSheetList.stream().map(ts -> ts.getTimeSheet().getEnd()).max(Comparator.naturalOrder()).get();
//		return new TimeSpanWithRounding(start, end, rounding);//丸めはどうする？
//	}
//	//遅刻早退時間帯（List）の計算範囲を1つの計算範囲にする
//	public TimeSpanForCalc createMinStartMaxEndSpanForCalcRange(List<LateLeaveEarlyTimeSheet> timeSheetList){
//		TimeWithDayAttr start = timeSheetList.stream().map(ts -> ts.getCalcrange().getStart()).min(Comparator.naturalOrder()).get();
//		TimeWithDayAttr end =  timeSheetList.stream().map(ts -> ts.getCalcrange().getEnd()).max(Comparator.naturalOrder()).get();
//		return new TimeSpanForCalc(start,end);
//	}
//	//遅刻早退時間帯（List）の控除項目の時間帯（List）を1つの控除項目の時間帯（List）にする
//	public List<TimeSheetOfDeductionItem> extract(List<LateLeaveEarlyTimeSheet> timeSheetList){
//		return timeSheetList.stream().map(tc -> tc.getDeductionTimeSheets()).collect(Collectors.toList());
//	}
	
	
//	/**
//	 * 遅刻時間の休暇時間相殺
//	 * @return
//	 */
//	public DeductionOffSetTime calcDeductionOffSetTime(
//			TimevacationUseTimeOfDaily TimeVacationAdditionRemainingTime,//時間休暇使用残時間を取得する
//			LateTimeSheet lateTimeSheet,
//			DeductionAtr deductionAtr) {
//		TimeSpanForCalc calcRange;
//		//計算範囲の取得
//		if(deductionAtr.isDeduction()) {//パラメータが控除の場合
//			calcRange = lateTimeSheet.getForDeducationTimeSheet().get().getTimeSheet().getSpan();
//		}else {//パラメータが計上の場合
//			calcRange = lateTimeSheet.getForRecordTimeSheet().get().getTimeSheet().getSpan();
//		}
//		//遅刻時間を求める
//		int lateRemainingTime = calcRange.lengthAsMinutes();
//		//時間休暇相殺を利用して相殺した各時間を求める
//		DeductionOffSetTime deductionOffSetTime = createDeductionOffSetTime(lateRemainingTime,TimeVacationAdditionRemainingTime);
//		
//		return 	deductionOffSetTime;
//	}
	
	
	/**
	 * 時間休暇相殺を利用して相殺した各時間を求める  （一時的に作成）
	 * @return
	 */
	public DeductionOffSetTime createDeductionOffSetTime(int lateRemainingTime,TimevacationUseTimeOfDaily TimeVacationAdditionRemainingTime) {
		
		AttendanceTime timeAnnualLeaveUseTime = calcOffSetTime(lateRemainingTime,TimeVacationAdditionRemainingTime.getTimeAnnualLeaveUseTime());
		lateRemainingTime -= timeAnnualLeaveUseTime.valueAsMinutes();

		AttendanceTime timeCompensatoryLeaveUseTime = new AttendanceTime(0);
		AttendanceTime sixtyHourExcessHolidayUseTime = new AttendanceTime(0);
		AttendanceTime timeSpecialHolidayUseTime = new AttendanceTime(0);
		
		if(lateRemainingTime > 0) {
			timeCompensatoryLeaveUseTime = calcOffSetTime(lateRemainingTime,TimeVacationAdditionRemainingTime.getTimeCompensatoryLeaveUseTime());
			lateRemainingTime -= timeCompensatoryLeaveUseTime.valueAsMinutes();
		}
		
		if(lateRemainingTime > 0) {
			sixtyHourExcessHolidayUseTime = calcOffSetTime(lateRemainingTime,TimeVacationAdditionRemainingTime.getSixtyHourExcessHolidayUseTime());
			lateRemainingTime -= sixtyHourExcessHolidayUseTime.valueAsMinutes();
		}
		
		if(lateRemainingTime > 0) {
			timeSpecialHolidayUseTime = calcOffSetTime(lateRemainingTime,TimeVacationAdditionRemainingTime.getTimeSpecialHolidayUseTime());
			lateRemainingTime -= timeSpecialHolidayUseTime.valueAsMinutes();
		}
				
		return new DeductionOffSetTime(
				timeAnnualLeaveUseTime,
				timeCompensatoryLeaveUseTime,
				sixtyHourExcessHolidayUseTime,
				timeSpecialHolidayUseTime);
	}

	
	/**
	 * 
	 * @param lateRemainingTime 遅刻残数
	 * @param timeVacationUseTime　時間休暇使用時間
	 * @return
	 */
	public AttendanceTime calcOffSetTime(int lateRemainingTime,AttendanceTime timeVacationUseTime) {
		int offSetTime;
		//相殺する時間を計算（比較）する
		if(timeVacationUseTime.lessThanOrEqualTo(lateRemainingTime)) {
			offSetTime = timeVacationUseTime.valueAsMinutes();
		}else {
			offSetTime = lateRemainingTime;
		}
		return new AttendanceTime(offSetTime);
	}

		
	//＊＊＊＊＊＊＊＊＊＊＊＊＊＊＊＊＊＊＊＊＊＊＊＊＊＊＊＊＊＊＊＊＊＊＊＊＊＊＊＊＊＊＊＊＊＊＊＊＊＊＊
	
	/**
	 * 就業時間内時間帯に入っている加給時間の計算
	 */
	public List<BonusPayTime> calcBonusPayTimeInWithinWorkTime(BonusPayAutoCalcSet bonusPayAutoCalcSet,BonusPayAtr bonusPayAtr,CalAttrOfDailyPerformance calcAtrOfDaily) {
		List<BonusPayTime> bonusPayList = new ArrayList<>();
		for(WithinWorkTimeFrame timeFrame : withinWorkTimeFrame) {
			bonusPayList.addAll(timeFrame.calcBonusPay(ActualWorkTimeSheetAtr.WithinWorkTime,bonusPayAutoCalcSet, calcAtrOfDaily));
		}
		return bonusPayList;
	}
	/**
	 * 就業時間内時間帯に入っている特定加給時間の計算
	 */
	public List<BonusPayTime> calcSpecifiedBonusPayTimeInWithinWorkTime(BonusPayAutoCalcSet bonusPayAutoCalcSet,BonusPayAtr bonusPayAtr,CalAttrOfDailyPerformance calcAtrOfDaily) {
		List<BonusPayTime> bonusPayList = new ArrayList<>();
		for(WithinWorkTimeFrame timeFrame : withinWorkTimeFrame) {
			bonusPayList.addAll(timeFrame.calcBonusPay(ActualWorkTimeSheetAtr.WithinWorkTime,bonusPayAutoCalcSet, calcAtrOfDaily));
		}
		return bonusPayList;
	}
	/**
	 * 法定内深夜時間の計算
	 * @return　法定内深夜時間
	 */
	public WithinStatutoryMidNightTime calcMidNightTime(AutoCalAtrOvertime autoCalcSet) {
		int totalMidNightTime = 0;
		totalMidNightTime = withinWorkTimeFrame.stream()
											   .filter(tg -> tg.getMidNightTimeSheet().isPresent())
											   .map(ts -> ts.getMidNightTimeSheet().get().calcMidNight(autoCalcSet))
											   .collect(Collectors.summingInt(tc -> tc));
		return new WithinStatutoryMidNightTime(TimeWithCalculation.sameTime(new AttendanceTime(totalMidNightTime)));
	}
}
