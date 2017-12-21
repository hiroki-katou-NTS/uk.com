package nts.uk.ctx.at.record.dom.dailyprocess.calc.withinstatutory;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.val;
import nts.gul.util.value.Finally;
import nts.uk.ctx.at.record.dom.daily.LateTimeOfDaily;
import nts.uk.ctx.at.record.dom.MidNightTimeSheet;
import nts.uk.ctx.at.record.dom.calculationattribute.CalAttrOfDailyPerformance;
import nts.uk.ctx.at.record.dom.daily.TimeWithCalculation;
import nts.uk.ctx.at.record.dom.daily.TimevacationUseTimeOfDaily;
import nts.uk.ctx.at.record.dom.daily.WorkInformationOfDaily;
import nts.uk.ctx.at.record.dom.daily.bonuspaytime.BonusPayTime;
import nts.uk.ctx.at.record.dom.daily.midnight.WithinStatutoryMidNightTime;
import nts.uk.ctx.at.record.dom.dailyprocess.calc.ActualWorkTimeSheetAtr;
import nts.uk.ctx.at.record.dom.dailyprocess.calc.BonusPayAtr;
import nts.uk.ctx.at.record.dom.dailyprocess.calc.DeductionOffSetTime;
import nts.uk.ctx.at.record.dom.dailyprocess.calc.DeductionTimeSheet;
import nts.uk.ctx.at.record.dom.dailyprocess.calc.FlexWithinWorkTimeSheet;
import nts.uk.ctx.at.record.dom.dailyprocess.calc.LateLeaveEarlyManagementTimeSheet;
import nts.uk.ctx.at.record.dom.dailyprocess.calc.LateLeaveEarlyTimeSheet;
import nts.uk.ctx.at.record.dom.dailyprocess.calc.LateTimeSheet;
import nts.uk.ctx.at.record.dom.dailyprocess.calc.LateTimeSheetList;
import nts.uk.ctx.at.shared.dom.attendance.UseSetting;
import nts.uk.ctx.at.shared.dom.bonuspay.setting.BonusPaySetting;
import nts.uk.ctx.at.record.dom.dailyprocess.calc.PredetermineTimeSetForCalc;
import nts.uk.ctx.at.record.dom.dailyprocess.calc.TimeSheetOfDeductionItem;
import nts.uk.ctx.at.record.dom.dailyprocess.calc.VacationAddTime;
import nts.uk.ctx.at.record.dom.dailyprocess.calc.VacationClass;
import nts.uk.ctx.at.record.dom.worktime.primitivevalue.WorkNo;
import nts.uk.ctx.at.shared.dom.common.time.AttendanceTime;
import nts.uk.ctx.at.shared.dom.common.time.TimeSpanForCalc;
import nts.uk.ctx.at.shared.dom.vacation.setting.addsettingofworktime.CalculationByActualTimeAtr;
import nts.uk.ctx.at.shared.dom.vacation.setting.addsettingofworktime.HolidayAdditionAtr;
import nts.uk.ctx.at.shared.dom.vacation.setting.addsettingofworktime.WorkTimeCalcMethodDetailOfHoliday;
import nts.uk.ctx.at.shared.dom.workrule.outsideworktime.AutoCalculationCategoryOutsideHours;
import nts.uk.ctx.at.shared.dom.worktime.AmPmClassification;
import nts.uk.ctx.at.shared.dom.worktime.fixedworkset.FixedWorkSetting;
import nts.uk.ctx.at.shared.dom.worktime.fixedworkset.WorkTimeCommonSet;
import nts.uk.ctx.at.shared.dom.worktime.fixedworkset.WorkTimeOfTimeSheetSet;
import nts.uk.ctx.at.shared.dom.worktime.fixedworkset.WorkTimeOfTimeSheetSetList;
import nts.uk.ctx.at.shared.dom.worktime.fixedworkset.timespan.TimeSpanWithRounding;
import nts.uk.ctx.at.shared.dom.worktime.flexworkset.CoreTimeSetting;
import nts.uk.ctx.at.shared.dom.worktime.fluidworkset.FluidWorkSetting;
import nts.uk.ctx.at.shared.dom.worktimeset.WorkTimeSet;
import nts.uk.ctx.at.shared.dom.worktype.AttendanceHolidayAttr;
import nts.uk.ctx.at.shared.dom.worktype.WorkType;
import nts.uk.shr.com.time.TimeWithDayAttr;

/**
 * 就業時間内時間帯
 * @author keisuke_hoshina
 *
 */
@AllArgsConstructor
public class WithinWorkTimeSheet extends LateLeaveEarlyManagementTimeSheet{

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
													WorkTimeSet predetermineTimeSet,
													FixedWorkSetting fixedWorkSetting,
													WorkTimeCommonSet workTimeCommonSet,
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
			WorkTimeSet predetermineTimeSet,
			FixedWorkSetting fixedWorkSetting,
			WorkTimeCommonSet workTimeCommonSet,
			DeductionTimeSheet deductionTimeSheet,
			BonusPaySetting bonusPaySetting
			) {
		

		PredetermineTimeSetForCalc predetermineTimeForSet = PredetermineTimeSetForCalc.convertFromAggregatePremiumTime(predetermineTimeSet);

		//遅刻猶予時間の取得
		val lateGraceTime = workTimeCommonSet.getLeaveEarlySetting().getLateSet().getGraceTimeSetting();
		//早退猶予時間の取得
		val leaveEarlyGraceTime = workTimeCommonSet.getLeaveEarlySetting().getLeaveEarlySet().getGraceTimeSetting();
						
		val timeFrames = new ArrayList<WithinWorkTimeFrame>();
		WithinWorkTimeFrame timeFrame;
		val workingHourSet = createWorkingHourSet(workType, predetermineTimeForSet , fixedWorkSetting);
		
		for (int frameNo = 0; frameNo < workingHourSet.toArray().length; frameNo++) {
			List<BonusPayTimesheet> bonusPayTimeSheet = new ArrayList<>();
			List<SpecBonusPayTimesheet> specifiedBonusPayTimeSheet = new ArrayList<>();
			Optional<MidNightTimeSheet> midNightTimeSheet;
			for(WorkTimeOfTimeSheetSet duplicateTimeSheet :workingHourSet) {
				//DeductionTimeSheet deductionTimeSheet = /*控除時間を分割する*/
				timeFrame = new WithinWorkTimeFrame(frameNo, duplicateTimeSheet.getTimeSpan(),duplicateTimeSheet.getTimeSpan(),deductionTimeSheet.getForDeductionTimeZoneList(),Collections.emptyList(),Optional.empty(),Collections.emptyList());
				/*加給*/
				bonusPayTimeSheet = bonusPaySetting.createDuplicationBonusPayTimeSheet(duplicateTimeSheet.getTimeSpan());
				specifiedBonusPayTimeSheet = bonusPaySetting.createDuplicationSpecifyBonusPay(duplicateTimeSheet.getTimeSpan());
				/*深夜*/
				midNightTimeSheet = timeFrame.createMidNightTimeSheet();
				
				timeFrames.add(new WithinWorkTimeFrame(timeFrame.getWorkingHoursTimeNo(),timeFrame.getTimeSheet(),timeFrame.getCalcrange(),timeFrame.getDeductionTimeSheet(),bonusPayTimeSheet,midNightTimeSheet,specifiedBonusPayTimeSheet));
			}
		}
		
		/*所定内割増時間の時間帯作成*/
		
		return new WithinWorkTimeSheet(
				timeFrames
				,
				LeaveEarlyDecisionClock.createListOfAllWorks(predetermineTimeSet, deductionTimeSheet, leaveEarlyGraceTime),
				LateDecisionClock.createListOfAllWorks(predetermineTimeSet, deductionTimeSheet, lateGraceTime));
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
	public static List<WorkTimeOfTimeSheetSet> createWorkingHourSet(WorkType workType, PredetermineTimeSetForCalc predetermineTimeSet,
			FixedWorkSetting fixedWorkSetting) {
		
		val attendanceHolidayAttr = workType.getAttendanceHolidayAttr();
		return getWorkingHourSetByAmPmClass(fixedWorkSetting, attendanceHolidayAttr).extractBetween(
				new TimeWithDayAttr(predetermineTimeSet.getStartOneDayTime().valueAsMinutes()),
				new TimeWithDayAttr(predetermineTimeSet.getStartOneDayTime().valueAsMinutes() + predetermineTimeSet.getOneDayRange().valueAsMinutes()));
	}

	/**
	 * 平日出勤の出勤時間帯を取得
	 * @param fixedWorkSetting 固定勤務設定クラス
	 * @param attendanceHolidayAttr 出勤休日区分
	 * @return 出勤時間帯
	 */
	private static WorkTimeOfTimeSheetSetList getWorkingHourSetByAmPmClass(
			FixedWorkSetting fixedWorkSetting,
			AttendanceHolidayAttr attendanceHolidayAttr) {
		
		switch (attendanceHolidayAttr) {
		case FULL_TIME:
		case HOLIDAY:
			return fixedWorkSetting.getWorkingHourSet(AmPmClassification.ONE_DAY);
		case MORNING:
			return fixedWorkSetting.getWorkingHourSet(AmPmClassification.AM);
		case AFTERNOON:
			return fixedWorkSetting.getWorkingHourSet(AmPmClassification.PM);
		default:
			throw new RuntimeException("unknown attendanceHolidayAttr" + attendanceHolidayAttr);
		}
	}
	
	/**
	 * 引数のNoと一致する遅刻判断時刻を取得する
	 * @author ken_takasu 
	 * @param workNo
	 * @return　遅刻判断時刻
	 */
	public LateDecisionClock getlateDecisionClock(WorkNo workNo) {
		List<LateDecisionClock> clockList = this.lateDecisionClock.stream().filter(tc -> tc.getWorkNo()==workNo).collect(Collectors.toList());
		if(clockList.size()>1) {
			throw new RuntimeException("Exist duplicate workNo : " + workNo);
		}
		return clockList.get(0);
	}
	
	/**
	 * 引数のNoと一致する早退判断時刻を取得する
	 * @author ken_takasu
	 * @param workNo
	 * @return　早退判断時刻
	 */
	public LeaveEarlyDecisionClock getleaveEarlyDecisionClock(workNo workNo) {
		List<LeaveEarlyDecisionClock> clockList = this.leaveEarlyDecisionClock.stream().filter(tc -> tc.getWorkNo()==workNo).collect(Collectors.toList());
		if(clockList.size()>1) {
			throw new RuntimeException("Exist duplicate workNo : " + workNo);
		}
		return clockList.get(0);
	}
	
	/**
	 * コアタイムのセット
	 * @param coreTimeSetting コアタイム時間設定
	 */
	public WithinWorkTimeSheet createWithinFlexTimeSheet(CoreTimeSetting coreTimeSetting) {
		List<FlexWithinWorkTimeSheet> duplicateCoreTimeList = new ArrayList<>();
		for(WithinWorkTimeFrame workTimeFrame : this.withinWorkTimeFrame) {
			Optional<TimeSpanForCalc> duplicateSpan = workTimeFrame.getCalcrange().getDuplicatedWith(coreTimeSetting.getCoreTime().getSpan()); 
			if(duplicateSpan.isPresent()) {
				duplicateCoreTimeList.add(new FlexWithinWorkTimeSheet(duplicateSpan.get().getSpan()));
			}
		}
		TimeSpanForCalc coreTime = new TimeSpanForCalc(new TimeWithDayAttr(),new TimeWithDayAttr())
		/*フレックス時間帯に入れる*/
		return new WithinWorkTimeSheet(this.withinWorkTimeFrame,this.leaveEarlyDecisionClock,this.lateDecisionClock,new FlexWithinWorkTimeSheet());
	}
	
	
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
	public AttendanceTime calcWorkTime(CalculationByActualTimeAtr calcActualTime,DeductionTimeSheet dedTimeSheet,VacationClass vacationClass,TimevacationUseTimeOfDaily timevacationUseTimeOfDaily) {
		
		HolidayAdditionAtr holidayAddition = HolidayAdditionAtr.HolidayAddition.convertFromCalcByActualTimeToHolidayAdditionAtr(calcActualTime);
		
		AttendanceTime workTime = calcWorkTimeBeforeDeductPremium(holidayAddition ,dedTimeSheet,timevacationUseTimeOfDaily);
		
		if(holidayAddition.isHolidayAddition()) {
			//休暇加算時間を計算
			VacationAddTime vacationAddTime = vacationClass.calcVacationAddTime(statutoryDivision,
																				workingSystem, 
																				addSettingOfRegularWork, 
																				vacationAddTimeSet, 
																				workType, 
																				predetermineTimeSet, 
																				siftCode, 
																				personalCondition, 
																				addSettingOfIrregularWork, 
																				addSettingOfFlexWork);
			//休暇加算時間を加算
			workTime += vacationAddTime.calcTotaladdVacationAddTime();
			
//			if(時間休暇加算方法) {
//				workTime += timevacationUseTimeOfDaily.getTotalTimeVacationUseTime();
//			}
		}
		return workTime;
	}
	
	 /**
	  * 就業時間内時間枠の全枠分の就業時間を算出する
	  * (所定内割増時間を差し引く前)
	  * @return 就業時間
	  */
	 public AttendanceTime calcWorkTimeBeforeDeductPremium(HolidayAdditionAtr holidayAdditionAtr,DeductionTimeSheet dedTimeSheet,TimevacationUseTimeOfDaily timevacationUseTimeOfDaily) {
	  AttendanceTime workTime = new AttendanceTime(0);
	  for(WithinWorkTimeFrame copyItem: withinWorkTimeFrame) {
	   workTime.addMinutes(copyItem.calcActualWorkTimeAndWorkTime(holidayAdditionAtr,dedTimeSheet,timevacationUseTimeOfDaily).valueAsMinutes());
	  }
	  return workTime;
	 }

	 
	/**
	 * 日別計算の遅刻早退時間の計算
	 * @return
	 */
	public int calcLateLeaveEarlyinWithinWorkTime() {
		for(WithinWorkTimeFrame workTimeFrame : withinWorkTimeFrame) {
			workTimeFrame.correctTimeSheet(dailyWork, timeSheet, predetermineTimeSetForCalc);
			LateTimeSheet lateTimeSheet = LateTimeSheet.lateTimeCalc(withinWorkTimeFrame, lateRangeForCalc, workTimeCommonSet, lateDecisionClock, workNo, deductionTimeSheet);
			
		}
	}
	
//	//＊＊＊＊＊＊＊＊＊＊＊＊＊＊＊＊＊＊＊＊＊＊＊＊＊＊＊＊＊＊＊＊＊＊＊＊＊＊＊＊＊＊＊＊＊＊＊＊＊＊＊
//	
//	//就業時間内時間帯クラスを作成　　（流動勤務）
//	public WithinWorkTimeSheet createAsFluidWork(
//			PredetermineTimeSetForCalc predetermineTimeSetForCalc,
//			WorkType workType,
//			WorkInformationOfDaily workInformationOfDaily,
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
//			WorkInformationOfDaily workInformationOfDaily) {
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
	public WithinStatutoryMidNightTime calcMidNightTime(AutoCalculationCategoryOutsideHours autoCalcSet) {
		int totalMidNightTime = 0;
		totalMidNightTime = withinWorkTimeFrame.stream()
											   .filter(tg -> tg.getMidNightTimeSheet().isPresent())
											   .map(ts -> ts.getMidNightTimeSheet().get().calcMidNight(autoCalcSet))
											   .collect(Collectors.summingInt(tc -> tc));
		return new WithinStatutoryMidNightTime(TimeWithCalculation.sameTime(new AttendanceTime(totalMidNightTime)));
	}
}
