package nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailycalprocess.calculation.timezone;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import lombok.Getter;
import lombok.Setter;
import lombok.val;
import nts.gul.util.value.Finally;
import nts.uk.ctx.at.shared.dom.PremiumAtr;
import nts.uk.ctx.at.shared.dom.common.time.AttendanceTime;
import nts.uk.ctx.at.shared.dom.common.time.AttendanceTimeOfExistMinus;
import nts.uk.ctx.at.shared.dom.common.timerounding.Rounding;
import nts.uk.ctx.at.shared.dom.common.timerounding.TimeRoundingSetting;
import nts.uk.ctx.at.shared.dom.common.timerounding.Unit;
import nts.uk.ctx.at.shared.dom.scherec.addsettingofworktime.HolidayCalcMethodSet;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.autocalsetting.BonusPayAutoCalcSet;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.attendancetime.TimeLeavingOfDailyAttd;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.attendancetime.TimeLeavingWork;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.attendancetime.WorkTimes;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.breakouting.ConditionAtr;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.calcategory.CalAttrOfDailyAttd;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.common.TimeWithCalculation;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.dailyattendancework.IntegrationOfDaily;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.paytime.BonusPayTime;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.temporarytime.WorkNo;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.workinfomation.ScheduleTimeSheet;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.workinfomation.WorkInfoOfDailyAttendance;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.workschedule.WorkScheduleTimeOfDaily;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailycalprocess.calculation.DeductionTimeSheet;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailycalprocess.calculation.ManagePerCompanySet;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailycalprocess.calculation.PreviousAndNextDaily;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailycalprocess.calculation.SchedulePerformance;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailycalprocess.calculation.TimeSpanForDailyCalc;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailycalprocess.calculation.other.FlexWithinWorkTimeSheet;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailycalprocess.calculation.other.HolidayWorkFrameTimeSheetForCalc;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailycalprocess.calculation.other.ManagePerPersonDailySet;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailycalprocess.calculation.other.OutsideWorkTimeSheet;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailycalprocess.calculation.other.TimeSheetRoundingAtr;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailycalprocess.calculation.timezone.deductiontime.DeductionAtr;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailycalprocess.calculation.timezone.deductiontime.DeductionClassification;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailycalprocess.calculation.timezone.deductiontime.TimeSheetOfDeductionItem;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailycalprocess.calculation.timezone.deductiontime.WorkingBreakTimeAtr;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailycalprocess.calculation.timezone.outsideworktime.OverTimeFrameTimeSheetForCalc;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailycalprocess.calculation.timezone.withinworkinghours.WithinWorkTimeFrame;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailycalprocess.calculation.timezone.withinworkinghours.WithinWorkTimeSheet;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.ortherpackage.classfunction.PredetermineTimeSetForCalc;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.ortherpackage.enums.BonusPayAtr;
import nts.uk.ctx.at.shared.dom.workrule.outsideworktime.AutoCalRaisingSalarySetting;
import nts.uk.ctx.at.shared.dom.workrule.outsideworktime.StatutoryAtr;
import nts.uk.ctx.at.shared.dom.worktime.IntegrationOfWorkTime;
import nts.uk.ctx.at.shared.dom.worktime.common.EmTimeFrameNo;
import nts.uk.ctx.at.shared.dom.worktime.common.WorkTimezoneCommonSet;
import nts.uk.ctx.at.shared.dom.worktime.flowset.FixedChangeAtr;
import nts.uk.ctx.at.shared.dom.worktime.flowset.FlowOTSet;
import nts.uk.ctx.at.shared.dom.worktime.flowset.FlowWorkSetting;
import nts.uk.ctx.at.shared.dom.worktime.flowset.PrePlanWorkTimeCalcMethod;
import nts.uk.ctx.at.shared.dom.worktype.AttendanceDayAttr;
import nts.uk.ctx.at.shared.dom.worktype.WorkType;
import nts.uk.shr.com.time.TimeWithDayAttr;

/**
 * 1日の計算範囲
 * 
 * @author keisuke_hoshina
 *
 */
@Getter
public class CalculationRangeOfOneDay {

	//1日の範囲
	private TimeSpanForDailyCalc oneDayOfRange;

	//勤務情報
	private WorkInfoOfDailyAttendance workInformationOfDaily;
	
	@Setter
	//出退勤
	private TimeLeavingOfDailyAttd attendanceLeavingWork;

	//所定時間設定
	private PredetermineTimeSetForCalc predetermineTimeSetForCalc;
	
	/*----------------Optional-----------------------*/
	//インターバル制度管理
	
	//非勤務時間帯
	private Optional<NonWorkingTimeSheet> nonWorkingTimeSheet;
	/*----------------------Finally------------------------*/
	//加給時間
	
	//就業時間内時間帯
	private Finally<WithinWorkTimeSheet> withinWorkingTimeSheet = Finally.empty();

	@Setter
	//就業時間外時間帯
	private Finally<OutsideWorkTimeSheet> outsideWorkTimeSheet = Finally.empty();

	//出勤前時間
	private Finally<AttendanceTime> beforeAttendance = Finally.of(new AttendanceTime(0)); 
	
	//退勤後時間
	private Finally<AttendanceTime> afterLeaving = Finally.of(new AttendanceTime(0));



	public CalculationRangeOfOneDay(Finally<WithinWorkTimeSheet> withinWorkingTimeSheet,
			Finally<OutsideWorkTimeSheet> outsideWorkTimeSheet, TimeSpanForDailyCalc oneDayOfRange,
			TimeLeavingOfDailyAttd attendanceLeavingWork, PredetermineTimeSetForCalc predetermineTimeSetForCalc,
			WorkInfoOfDailyAttendance workInformationofDaily,
			Optional<NonWorkingTimeSheet> nonWorkingTimeSheet) {
		this.withinWorkingTimeSheet = withinWorkingTimeSheet;
		this.outsideWorkTimeSheet = outsideWorkTimeSheet;
		this.oneDayOfRange = oneDayOfRange;
		this.attendanceLeavingWork = attendanceLeavingWork;
		this.predetermineTimeSetForCalc = predetermineTimeSetForCalc;
		this.workInformationOfDaily = workInformationofDaily;
		this.nonWorkingTimeSheet = nonWorkingTimeSheet;
	}
	
	
	public static CalculationRangeOfOneDay createEmpty(IntegrationOfDaily integrationOfDaily) {
		return new CalculationRangeOfOneDay(
				Finally.of(
						new FlexWithinWorkTimeSheet(
								Arrays.asList(
										new WithinWorkTimeFrame(
												new EmTimeFrameNo(5),
												new TimeSpanForDailyCalc(new TimeWithDayAttr(0), new TimeWithDayAttr(0)),
												new TimeSpanForDailyCalc(new TimeWithDayAttr(0), new TimeWithDayAttr(0)),
												new TimeRoundingSetting(Unit.ROUNDING_TIME_1MIN, Rounding.ROUNDING_DOWN),
												Collections.emptyList(),
												Collections.emptyList(),
												Collections.emptyList(),
												Optional.empty(),
												Collections.emptyList(),
												Optional.empty(),
												Optional.empty()
										)
								),
								Collections.emptyList(),
								Optional.empty()
						)
				),
				Finally.of(new OutsideWorkTimeSheet(Optional.empty(),Optional.empty())),
				null,
				integrationOfDaily.getAttendanceLeave().orElse(null),
				null,
				integrationOfDaily.getWorkInformation(),
				Optional.empty()
		);
	}
	
	
	/**
	 * 就業時間帯の作成
	 * アルゴリズム：固定勤務の時間帯作成
	 * @param companyCommonSetting 会社別設定管理
	 * @param personDailySetting 社員設定管理
	 * @param todayWorkType 当日の勤務種類
	 * @param integrationOfWorkTime 統合就業時間帯
	 * @param integrationOfDaily 日別実績(Work)
	 * @param previousAndNextDaily 前日と翌日の勤務
	 */
	public void createWithinWorkTimeSheet(
			ManagePerCompanySet companyCommonSetting,
			ManagePerPersonDailySet personDailySetting,
			WorkType todayWorkType,
			IntegrationOfWorkTime integrationOfWorkTime,
			IntegrationOfDaily integrationOfDaily,
			PreviousAndNextDaily previousAndNextDaily) {
		
		/* 固定控除時間帯の作成 */
		DeductionTimeSheet deductionTimeSheet = DeductionTimeSheet.createTimeSheetForFixBreakTime(
				todayWorkType,
				integrationOfWorkTime,
				integrationOfDaily,
				this.oneDayOfRange,
				this.attendanceLeavingWork,
				this.predetermineTimeSetForCalc);
		
		this.theDayOfWorkTimesLoop(
				companyCommonSetting,
				personDailySetting,
				todayWorkType,
				integrationOfWorkTime,
				integrationOfDaily,
				deductionTimeSheet,
				previousAndNextDaily);
	}



	/**
	 * 時間帯作成(勤務回数分ループ) 就業時間内・外作成処理
	 * アルゴリズム：時間帯の作成
	 * @param companyCommonSetting 会社別設定管理
	 * @param personDailySetting 社員設定管理
	 * @param personalInfo 勤務種類
	 * @param todayWorkType 統合就業時間帯
	 * @param integrationOfWorkTime 日別実績(Work)
	 * @param integrationOfDaily 日別計算用時間帯
	 * @param deductionTimeSheet 日別実績の出退勤
	 * @param previousAndNextDaily 所定時間設定(計算用クラス)
	 */
	public void theDayOfWorkTimesLoop(
			ManagePerCompanySet companyCommonSetting,
			ManagePerPersonDailySet personDailySetting,
			WorkType todayWorkType,
			IntegrationOfWorkTime integrationOfWorkTime,
			IntegrationOfDaily integrationOfDaily,
			DeductionTimeSheet deductionTimeSheet,
			PreviousAndNextDaily previousAndNextDaily) {
		
		if (personDailySetting.getPersonInfo().getLaborSystem().isExcludedWorkingCalculate()) {
			/* 計算対象外 */
			return;
		}
		
		/*attendanceLeavingWork は、ジャスト遅刻早退の補正処理を行った結果をCalculationRangeOneDayが持っているので、
		 * intergrationOfDailyではなく、rangeOneDayが持っている出退勤を使う事。
		 * */
		for (int workNumber = 1; workNumber <= attendanceLeavingWork.getTimeLeavingWorks().size(); workNumber++) {
			
			/* 就業時間内時間帯作成 */
			//打刻はある前提で動く
			val createWithinWorkTimeSheet = WithinWorkTimeSheet.createAsFixed(
					companyCommonSetting,
					personDailySetting,
					todayWorkType,
					integrationOfWorkTime,
					integrationOfDaily,
					deductionTimeSheet,
					this.predetermineTimeSetForCalc,
					this.attendanceLeavingWork.getAttendanceLeavingWork(new WorkNo(workNumber)).get());
			if(this.withinWorkingTimeSheet.isPresent()) {
				this.withinWorkingTimeSheet.get().getWithinWorkTimeFrame().addAll(createWithinWorkTimeSheet.getWithinWorkTimeFrame());
			}
			else {
				this.withinWorkingTimeSheet.set(createWithinWorkTimeSheet);
			}
			/* 就業時間外時間帯作成 */
			//打刻はある前提で動く
			val createOutSideWorkTimeSheet = OutsideWorkTimeSheet.createOutsideWorkTimeSheet(
					companyCommonSetting,
					personDailySetting,
					todayWorkType,
					integrationOfWorkTime,
					integrationOfDaily,
					deductionTimeSheet,
					this.predetermineTimeSetForCalc,
					this.attendanceLeavingWork.getAttendanceLeavingWork(new WorkNo(workNumber)).get(),
					previousAndNextDaily,
					createWithinWorkTimeSheet);
			if(!outsideWorkTimeSheet.isPresent()) {
				//outsideWorkTimeSheet.set(createOutSideWorkTimeSheet);
				this.outsideWorkTimeSheet = Finally.of(createOutSideWorkTimeSheet);
			}
			else {
				//残業
				if(outsideWorkTimeSheet.get().getOverTimeWorkSheet().isPresent()) {
					List<OverTimeFrameTimeSheetForCalc> addOverList = createOutSideWorkTimeSheet.getOverTimeWorkSheet().isPresent()? createOutSideWorkTimeSheet.getOverTimeWorkSheet().get().getFrameTimeSheets():Collections.emptyList();
					outsideWorkTimeSheet.get().getOverTimeWorkSheet().get().getFrameTimeSheets().addAll(addOverList);
				}
				else {
					this.outsideWorkTimeSheet = Finally.of(new OutsideWorkTimeSheet(createOutSideWorkTimeSheet.getOverTimeWorkSheet(),this.outsideWorkTimeSheet.get().getHolidayWorkTimeSheet()));
				}
				//休�?
				if(outsideWorkTimeSheet.get().getHolidayWorkTimeSheet().isPresent()) {
					List<HolidayWorkFrameTimeSheetForCalc> addHolList = createOutSideWorkTimeSheet.getHolidayWorkTimeSheet().isPresent()? createOutSideWorkTimeSheet.getHolidayWorkTimeSheet().get().getWorkHolidayTime():Collections.emptyList();
					outsideWorkTimeSheet.get().getHolidayWorkTimeSheet().get().getWorkHolidayTime().addAll(addHolList);
				}
				else {
					this.outsideWorkTimeSheet = Finally.of(new OutsideWorkTimeSheet(this.outsideWorkTimeSheet.get().getOverTimeWorkSheet(),createOutSideWorkTimeSheet.getHolidayWorkTimeSheet()));
				}
			}
		}
		
		List<OverTimeFrameTimeSheetForCalc> paramList = new ArrayList<>();
		if(!this.withinWorkingTimeSheet.isPresent()) {
			this.withinWorkingTimeSheet = Finally.of(new WithinWorkTimeSheet(Arrays.asList(new WithinWorkTimeFrame(new EmTimeFrameNo(5), 
																									new TimeSpanForDailyCalc(new TimeWithDayAttr(0), new TimeWithDayAttr(0)), 
																									new TimeSpanForDailyCalc(new TimeWithDayAttr(0), new TimeWithDayAttr(0)), 
																									new TimeRoundingSetting(Unit.ROUNDING_TIME_1MIN, Rounding.ROUNDING_DOWN), 
																									Collections.emptyList(), 
																									Collections.emptyList(), 
																									Collections.emptyList(), 
																									Optional.empty(), 
																									Collections.emptyList(), 
																									Optional.empty(), 
																									Optional.empty())),
																			Collections.emptyList(),
																			Optional.empty(),
																			Optional.empty()));
		}
		if(this.outsideWorkTimeSheet.isPresent()
			&& this.outsideWorkTimeSheet.get().getOverTimeWorkSheet().isPresent()) {
			paramList = this.outsideWorkTimeSheet.get().getOverTimeWorkSheet().get().getFrameTimeSheets();
		}
	}

	/**
	 * 加給時間を計算する(就内・残業・休出時間帯)
	 * アルゴリズム：加給時間の計算
	 * @param raisingAutoCalcSet 加給の自動計算設定
	 * @param bonusPayAutoCalcSet 加給自動計算設定
	 * @param calcAtrOfDaily 日別実績の計算区分
	 * @param bonusPayAtr 加給区分
	 * @return 加給時間(List)
	 */
	public List<BonusPayTime> calcBonusPayTime(
			AutoCalRaisingSalarySetting raisingAutoCalcSet,
			BonusPayAutoCalcSet bonusPayAutoCalcSet,
			CalAttrOfDailyAttd calcAtrOfDaily,
			BonusPayAtr bonusPayAtr) {
		
		List<BonusPayTime> overTimeBonusPay = new ArrayList<>();
		List<BonusPayTime> holidayWorkBonusPay = new ArrayList<>();
		List<BonusPayTime> withinBonusPay = new ArrayList<>();
		if(this.withinWorkingTimeSheet != null && withinWorkingTimeSheet.isPresent())
			withinBonusPay = withinWorkingTimeSheet.get().calcBonusPayTimeInWithinWorkTime(raisingAutoCalcSet,bonusPayAutoCalcSet, bonusPayAtr,calcAtrOfDaily);
		
		if(this.outsideWorkTimeSheet != null && this.outsideWorkTimeSheet.isPresent())
		{
			if(this.outsideWorkTimeSheet.get().getOverTimeWorkSheet().isPresent()) { 
				overTimeBonusPay = outsideWorkTimeSheet.get().getOverTimeWorkSheet().get().calcBonusPayTimeInOverWorkTime(raisingAutoCalcSet, bonusPayAutoCalcSet, bonusPayAtr, calcAtrOfDaily);
			}
			
			if(this.outsideWorkTimeSheet.get().getHolidayWorkTimeSheet().isPresent()) {
				holidayWorkBonusPay = outsideWorkTimeSheet.get().getHolidayWorkTimeSheet().get().calcBonusPayTimeInHolidayWorkTime(raisingAutoCalcSet, bonusPayAutoCalcSet, bonusPayAtr, calcAtrOfDaily);
			}
		}
		return calcBonusPayTime(withinBonusPay,overTimeBonusPay,holidayWorkBonusPay);
	}
	
	/**
	 * 特定加給時間を計算する(就内・残業・休出時間帯)
	 * アルゴリズム：加給時間の計算
	 * @param raisingAutoCalcSet 加給の自動計算設定
	 * @param bonusPayAutoCalcSet 加給自動計算設定
	 * @param calcAtrOfDaily 日別実績の計算区分
	 * @param bonusPayAtr 加給区分
	 * @return 特定加給時間(List)
	 */
	public List<BonusPayTime> calcSpecBonusPayTime(
			AutoCalRaisingSalarySetting raisingAutoCalcSet,
			BonusPayAutoCalcSet bonusPayAutoCalcSet,
			CalAttrOfDailyAttd calcAtrOfDaily,
			BonusPayAtr bonusPayAtr){
		
		List<BonusPayTime> overTimeBonusPay = new ArrayList<>();
		List<BonusPayTime> holidayWorkBonusPay = new ArrayList<>();
		List<BonusPayTime> withinBonusPay = new ArrayList<>();
		
		if(withinWorkingTimeSheet.isPresent())
			 withinBonusPay = withinWorkingTimeSheet.get().calcSpecifiedBonusPayTimeInWithinWorkTime(raisingAutoCalcSet,bonusPayAutoCalcSet, bonusPayAtr,calcAtrOfDaily);

		if(outsideWorkTimeSheet.isPresent())
		{
			if(outsideWorkTimeSheet.get().getOverTimeWorkSheet().isPresent()) { 
				overTimeBonusPay = outsideWorkTimeSheet.get().getOverTimeWorkSheet().get().calcSpecBonusPayTimeInOverWorkTime(raisingAutoCalcSet, bonusPayAutoCalcSet, bonusPayAtr, calcAtrOfDaily);
			}
			
			if(outsideWorkTimeSheet.get().getHolidayWorkTimeSheet().isPresent()) {
				holidayWorkBonusPay = outsideWorkTimeSheet.get().getHolidayWorkTimeSheet().get().calcSpecBonusPayTimeInHolidayWorkTime(raisingAutoCalcSet, bonusPayAutoCalcSet, bonusPayAtr, calcAtrOfDaily);
			}
		}
		return calcBonusPayTime(withinBonusPay,overTimeBonusPay,holidayWorkBonusPay);
	}
	
	/**
	 * 就・残�?休�?�?給時間を合計す�?
	 * @param withinBonusPay
	 * @param overTimeBonusPay
	 * @param holidayWorkBonusPay
	 * @return�?合計後�?�?算時�?(Noでユニ�?ク)
	 */
	private List<BonusPayTime> calcBonusPayTime(List<BonusPayTime> withinBonusPay ,
								   List<BonusPayTime> overTimeBonusPay ,
								   List<BonusPayTime> holidayWorkBonusPay){
		List<BonusPayTime> returnList = new ArrayList<>();
		
		overTimeBonusPay.addAll(holidayWorkBonusPay);
		val excessPayTime = overTimeBonusPay;
		for(int bonusPayNo = 1 ; bonusPayNo <= 10 ; bonusPayNo++) {
			returnList.add(addWithinAndExcessBonusTime(sumBonusPayTime(getByBonusPayNo(withinBonusPay,bonusPayNo),bonusPayNo),
													   sumBonusPayTime(getByBonusPayNo(excessPayTime,bonusPayNo),bonusPayNo)
													   ,bonusPayNo));
		}
		return returnList;
	}
	
	/**
	 * 受け取っ�?2つの�?給時間が持つ時間を合�?
	 * @param within
	 * @param excess
	 * @param bonusPayNo
	 * @return
	 */
	private BonusPayTime addWithinAndExcessBonusTime(BonusPayTime within,BonusPayTime excess,int bonusPayNo) {
		return new BonusPayTime(bonusPayNo,
								within.getBonusPayTime().addMinutes(excess.getBonusPayTime().valueAsMinutes()),
								within.getWithinBonusPay().addMinutes(excess.getWithinBonusPay().getTime(), 
																	  excess.getWithinBonusPay().getCalcTime()),
								within.getExcessBonusPayTime().addMinutes(excess.getExcessBonusPayTime().getTime(), 
										  								  excess.getExcessBonusPayTime().getCalcTime())
								);
	}
	
	/**
	 * �?��間�?合計を算�?
	 * @param bonusPayList�?�?給時間のリス�?
	 * @param bonusPayNo�?�?給時間?�??
	 * @return�?合計時間�?�?給時間
	 */
	private BonusPayTime sumBonusPayTime(List<BonusPayTime> bonusPayList, int bonusPayNo) {
		AttendanceTime bonusPayTime =  new AttendanceTime(bonusPayList.stream().map(tc -> tc.getBonusPayTime().valueAsMinutes()).collect(Collectors.summingInt(tc -> tc)));
		AttendanceTime withinTime = new AttendanceTime(bonusPayList.stream().map(tc -> tc.getWithinBonusPay().getTime().valueAsMinutes()).collect(Collectors.summingInt(tc -> tc)));
		AttendanceTime withinCalcTime = new AttendanceTime(bonusPayList.stream().map(tc -> tc.getWithinBonusPay().getCalcTime().valueAsMinutes()).collect(Collectors.summingInt(tc -> tc)));
		AttendanceTime excessTime = new AttendanceTime(bonusPayList.stream().map(tc -> tc.getExcessBonusPayTime().getTime().valueAsMinutes()).collect(Collectors.summingInt(tc -> tc)));
		AttendanceTime excessCalcTime = new AttendanceTime(bonusPayList.stream().map(tc -> tc.getExcessBonusPayTime().getCalcTime().valueAsMinutes()).collect(Collectors.summingInt(tc -> tc)));
		
		return new BonusPayTime(bonusPayNo,
								bonusPayTime,
								TimeWithCalculation.createTimeWithCalculation(withinTime, withinCalcTime),
								TimeWithCalculation.createTimeWithCalculation(excessTime, excessCalcTime));
	}
	
	/**
	 * 受け取った加給時間?�?�を持つ�?給時間を取�?
	 * @param bonusPayTime �?給時間
	 * @param bonusPayNo�?�?給時間?�??
	 * @return�?�?給時間リス�?
	 */
	private List<BonusPayTime> getByBonusPayNo(List<BonusPayTime> bonusPayTime,int bonusPayNo){
		return bonusPayTime.stream().filter(tc -> tc.getBonusPayTimeItemNo() == bonusPayNo).collect(Collectors.toList());
	}
	
	
	/**
	 * 控除時間を取得
	 * @param dedClassification 条件
	 * @param dedAtr 控除区分
	 * @param statutoryAtrs 法定内区分
	 * @param pertimesheet 丸め区分(時間帯で丸めるかの区分)
	 * @param premiumAtr 割増区分
	 * @param holidayCalcMethodSet 休暇の計算方法の設定
	 * @param commonSetting 就業時間帯の共通設定
	 * @return 控除時間
	 */
	public TimeWithCalculation calcWithinTotalTime(
			ConditionAtr dedClassification,
			DeductionAtr dedAtr,
			StatutoryAtr statutoryAtr,
			TimeSheetRoundingAtr pertimesheet,
			PremiumAtr premiumAtr,
			HolidayCalcMethodSet holidayCalcMethodSet,
			Optional<WorkTimezoneCommonSet> commonSetting) {
		if(statutoryAtr.isStatutory()) {
			if(this.withinWorkingTimeSheet.isPresent()) {
				return TimeWithCalculation.sameTime(this.withinWorkingTimeSheet.get().calculationAllFrameDeductionTime(dedAtr, dedClassification,premiumAtr,holidayCalcMethodSet,commonSetting));
			}
		}
		else if(statutoryAtr.isExcess()) {
			if(this.getOutsideWorkTimeSheet().isPresent()) {
				AttendanceTime overTime = this.getOutsideWorkTimeSheet().get().caluclationAllOverTimeFrameTime(dedAtr, dedClassification);
				AttendanceTime holidaytime = this.getOutsideWorkTimeSheet().get().caluclationAllHolidayFrameTime(dedAtr, dedClassification);
				return TimeWithCalculation.sameTime(overTime.addMinutes(holidaytime.valueAsMinutes()));
			}
		}
		return TimeWithCalculation.sameTime(new AttendanceTime(0));
	}

	/**
	 * フレックス勤務の時間帯作成
	 * @param companyCommonSetting 会社別設定管理
	 * @param personDailySetting  社員設定管理
	 * @param todayWorkType 勤務種類
	 * @param integrationOfWorkTime 統合就業時間帯
	 * @param integrationOfDaily 日別実績(Work)
	 * @param previousAndNextDaily 前日と翌日の勤務
	 */
	public void createTimeSheetAsFlex(
			ManagePerCompanySet companyCommonSetting,
			ManagePerPersonDailySet personDailySetting,
			WorkType todayWorkType,
			IntegrationOfWorkTime integrationOfWorkTime,
			IntegrationOfDaily integrationOfDaily,
			PreviousAndNextDaily previousAndNextDaily){
	
		//控除時間帯の作成
		val deductionTimeSheet = provisionalDeterminationOfDeductionTimeSheet(
				todayWorkType,
				integrationOfWorkTime,
				integrationOfDaily,
				this.oneDayOfRange,
				this.attendanceLeavingWork,
				this.predetermineTimeSetForCalc);
		
		theDayOfWorkTimesLoop(
				companyCommonSetting,
				personDailySetting,
				todayWorkType,
				integrationOfWorkTime,
				integrationOfDaily,
				deductionTimeSheet,
				previousAndNextDaily);
		
		/*コアタイムのセット*/
		if(this.withinWorkingTimeSheet.isPresent())
			this.withinWorkingTimeSheet = Finally.of(
					withinWorkingTimeSheet.get().createWithinFlexTimeSheet(integrationOfWorkTime.getFlexWorkSetting().get().getCoreTimeSetting(), deductionTimeSheet));
	}

	/**
	 * 控除時間帯の仮確定
	 * アルゴリズム：流動休憩用の時間帯作成
	 * @param todayWorkType 勤務種類
	 * @param integrationOfWorkTime 統合就業時間帯
	 * @param integrationOfDaily 日別実績(Work)
	 * @param oneDayOfRange 日別計算用時間帯
	 * @param attendanceLeaveWork 日別実績の出退勤
	 * @param predetermineTimeSetForCalc 所定時間設定(計算用クラス)
	 * @return 控除時間帯
	 */
	public static DeductionTimeSheet provisionalDeterminationOfDeductionTimeSheet(
			WorkType todayWorkType,
			IntegrationOfWorkTime integrationOfWorkTime,
			IntegrationOfDaily integrationOfDaily,
			TimeSpanForDailyCalc oneDayOfRange,
			TimeLeavingOfDailyAttd attendanceLeaveWork,
			PredetermineTimeSetForCalc predetermineTimeSetForCalc) {
		//控除用
		val dedTimeSheet = DeductionTimeSheet.provisionalDecisionOfDeductionTimeSheet(
				DeductionAtr.Deduction,
				todayWorkType,
				integrationOfWorkTime,
				integrationOfDaily,
				oneDayOfRange,
				attendanceLeaveWork,
				predetermineTimeSetForCalc);
		//計上用
		val recordTimeSheet = DeductionTimeSheet.provisionalDecisionOfDeductionTimeSheet(
				DeductionAtr.Appropriate,
				todayWorkType,
				integrationOfWorkTime,
				integrationOfDaily,
				oneDayOfRange,
				attendanceLeaveWork,
				predetermineTimeSetForCalc);
	
		return new DeductionTimeSheet(
				dedTimeSheet,
				recordTimeSheet,
				integrationOfDaily.getBreakTime(),
				integrationOfDaily.getOutingTime(),
				integrationOfDaily.getShortTime().isPresent()
						? integrationOfDaily.getShortTime().get().getShortWorkingTimeSheets()
						: Collections.emptyList()); 
	}
	
	 /**
	  * 大塚モード使用時専用の��?��、早��?削除処��?
	  * 大塚モード使用時専用の�?��、早�?削除処�?
	  * 大塚モード使用時専用の遅刻、早退削除処理
	  */
	 public void cleanLateLeaveEarlyTimeForOOtsuka() {
		 if(this.getWithinWorkingTimeSheet() != null
			&& this.getWithinWorkingTimeSheet().isPresent()){
			 this.withinWorkingTimeSheet.get().cleanLateLeaveEarlyTimeForOOtsuka();
		 }
	 }
	 
	 public void clearLeavingTime() {
		 this.attendanceLeavingWork = new TimeLeavingOfDailyAttd(Collections.emptyList(),new WorkTimes(0));
	 }
	 
	
	/**
	 * 流動勤務の時間帯作成
	 * @param companyCommonSetting 会社別設定管理
	 * @param personDailySetting 社員設定管理
	 * @param todayWorkType 当日の勤務種類
	 * @param integrationOfWorkTime 統合就業時間帯
	 * @param integrationOfDaily 日別実績(Work)
	 * @param previousAndNextDaily 前日と翌日の勤務
	 * @param schedulePerformance 予定実績
	 */
	public void createFlowWork(
			ManagePerCompanySet companyCommonSetting,
			ManagePerPersonDailySet personDailySetting,
			WorkType todayWorkType,
			IntegrationOfWorkTime integrationOfWorkTime,
			IntegrationOfDaily integrationOfDaily,
			PreviousAndNextDaily previousAndNextDaily,
			Optional<SchedulePerformance> schedulePerformance) {
		
		if (todayWorkType.getDecisionAttendanceHolidayAttr()) {
			//1日休日は就内就外を空で作成する
			this.withinWorkingTimeSheet.set(WithinWorkTimeSheet.createEmpty());
			this.outsideWorkTimeSheet.set(OutsideWorkTimeSheet.createEmpty());
			return;
		}
		
		//出退勤を取得
		List<TimeLeavingWork> timeLeavingWorks = integrationOfDaily.getAttendanceLeave().get().getTimeLeavingWorks();
		
		//空の就業時間内時間帯を作成。これを遅刻早退と就内の処理で編集していく。
		WithinWorkTimeSheet creatingWithinWorkTimeSheet = new WithinWorkTimeSheet(new ArrayList<>(), new ArrayList<>(), Optional.empty(), Optional.empty());
		
		//事前に遅刻早退、控除時間帯を取得する
		DeductionTimeSheet timeSheetOfDeductionItems = this.prePocessForFlow(//設計上は控除項目の時間帯を返すが、就内で呼ぶ共通処理で必要な為、控除時間帯を返している。
				personDailySetting,
				todayWorkType,
				integrationOfWorkTime,
				integrationOfDaily,
				timeLeavingWorks,
				creatingWithinWorkTimeSheet);
		
		if(todayWorkType.isWeekDayAttendance()) {
			
			//所定時間設定をコピーして計算用の所定時間設定を作成する → 既に持っている
			
			//所定時間帯、残業開始を補正する
			this.fluctuationPredeterminedForFlow(
					companyCommonSetting,
					personDailySetting,
					todayWorkType,
					integrationOfDaily,
					integrationOfWorkTime.getFlowWorkSetting().get(),
					schedulePerformance);
			
			//流動勤務(就内、平日)
			this.withinWorkingTimeSheet.set(WithinWorkTimeSheet.createAsFlow(
					companyCommonSetting,
					personDailySetting,
					todayWorkType,
					integrationOfWorkTime,
					integrationOfDaily,
					this.predetermineTimeSetForCalc,
					timeSheetOfDeductionItems,
					creatingWithinWorkTimeSheet));
			
			if(this.withinWorkingTimeSheet.get().getWithinWorkTimeFrame().isEmpty())
				return;
			
			//流動勤務(平日・就外）
			this.outsideWorkTimeSheet.set(
					OutsideWorkTimeSheet.createOverTimeAsFlow(
							companyCommonSetting,
							personDailySetting,
							todayWorkType,
							integrationOfWorkTime,
							integrationOfDaily,
							this.predetermineTimeSetForCalc,
							timeSheetOfDeductionItems.getForDeductionTimeZoneList(),
							this.withinWorkingTimeSheet.get(),
							previousAndNextDaily));
		} else {
			//休出の場合でも就業時間内時間帯を作成する必要がある為、空で作成
			this.withinWorkingTimeSheet = Finally.of(WithinWorkTimeSheet.createEmpty());
			
			if(!creatingWithinWorkTimeSheet.getStartEndToWithinWorkTimeFrame().isPresent())
				return;
				
			//流動勤務(休日出勤)
			 this.outsideWorkTimeSheet.set(
					OutsideWorkTimeSheet.createHolidayAsFlow(
							companyCommonSetting,
							personDailySetting,
							todayWorkType,
							integrationOfWorkTime,
							integrationOfDaily,
							timeSheetOfDeductionItems.getForDeductionTimeZoneList(),
							creatingWithinWorkTimeSheet.getStartEndToWithinWorkTimeFrame().get(),
							this.oneDayOfRange,
							previousAndNextDaily));
		}
	}
	
	
	/**
	 * 事前処理
	 * @param personDailySetting 社員設定管理
	 * @param todayWorkType 勤務種類
	 * @param integrationOfWorkTime 統合就業時間帯
	 * @param integrationOfDaily 日別実績(Work)
	 * @param timeLeavingForFlowWork 流動勤務用出退勤
	 * @param creatingWithinWorkTimeSheet 就業時間内時間帯
	 * @return 控除時間帯
	 */
	public DeductionTimeSheet prePocessForFlow(
			ManagePerPersonDailySet personDailySetting,
			WorkType todayWorkType,
			IntegrationOfWorkTime integrationOfWorkTime,
			IntegrationOfDaily integrationOfDaily,
			List<TimeLeavingWork> timeLeavingForFlowWork,
			WithinWorkTimeSheet creatingWithinWorkTimeSheet){
		
		//1回目と2回目の間の時間帯を作成  遅刻丸めによる時刻補正を行うよりも前に時間帯を作成する必要がある為、ここで作成。
		Optional<TimeSheetOfDeductionItem> betweenWorkTimeSheets = this.createBetweenWork(
				timeLeavingForFlowWork,
				integrationOfWorkTime.getFlowWorkSetting().get());
		
		//間の休憩を非勤務時間帯へ
		if(betweenWorkTimeSheets.isPresent()) {
			if(betweenWorkTimeSheets.get().getWorkingBreakAtr().isWorking() && betweenWorkTimeSheets.get().getDeductionAtr().isBreak()) {
				List<TimeSpanForDailyCalc> whithinBreakTimeSheet = new ArrayList<TimeSpanForDailyCalc>();
				whithinBreakTimeSheet.add(betweenWorkTimeSheets.get().getTimeSheet());
				this.nonWorkingTimeSheet = Optional.of(new NonWorkingTimeSheet(whithinBreakTimeSheet, Collections.emptyList()));
			}
		};
		
		//控除時間帯の取得
		DeductionTimeSheet deductionTimeSheetCalcBefore = provisionalDeterminationOfDeductionTimeSheet(
				todayWorkType,
				integrationOfWorkTime,
				integrationOfDaily,
				this.oneDayOfRange,
				this.attendanceLeavingWork,
				this.predetermineTimeSetForCalc);
		
		//遅刻時間帯の計算
		List<TimeLeavingWork> calcLateTimeLeavingWorksWorks = new ArrayList<>(timeLeavingForFlowWork);
		timeLeavingForFlowWork.clear();
		for(TimeLeavingWork timeLeavingWork : calcLateTimeLeavingWorksWorks) {
			timeLeavingForFlowWork.add(
					this.calcLateTimeSheet(
							todayWorkType,
							integrationOfWorkTime,
							integrationOfDaily,
							deductionTimeSheetCalcBefore.getForDeductionTimeZoneList(),
							personDailySetting.getAddSetting().getVacationCalcMethodSet(),
							timeLeavingWork,
							creatingWithinWorkTimeSheet));
		}
		
		//控除時間帯の取得
		DeductionTimeSheet deductionTimeSheetCalcAfter = provisionalDeterminationOfDeductionTimeSheet(
				todayWorkType,
				integrationOfWorkTime,
				integrationOfDaily,
				this.oneDayOfRange,
				new TimeLeavingOfDailyAttd(
						timeLeavingForFlowWork, 
						new WorkTimes(timeLeavingForFlowWork.size())),
				this.predetermineTimeSetForCalc);
		
		if(betweenWorkTimeSheets.isPresent()) {
			deductionTimeSheetCalcAfter.getForDeductionTimeZoneList().add(betweenWorkTimeSheets.get());
		}
		
		//早退時間帯の計算
		List<TimeLeavingWork> calcLeaveEarlyTimeLeavingWorks = new ArrayList<>(timeLeavingForFlowWork);
		timeLeavingForFlowWork.clear();
		
		for(TimeLeavingWork timeLeavingWork : calcLeaveEarlyTimeLeavingWorks) {
			timeLeavingForFlowWork.add(
					this.calcLeaveEarlyTimeSheet(
							todayWorkType,
							integrationOfWorkTime,
							deductionTimeSheetCalcBefore.getForDeductionTimeZoneList(),
							personDailySetting.getAddSetting().getVacationCalcMethodSet(),
							timeLeavingWork,
							creatingWithinWorkTimeSheet));
		}
		
		return deductionTimeSheetCalcAfter;
	}
	
	/**
	* 遅刻時間帯の計算
	 * @param todayWorkType 勤務種類
	 * @param integrationOfWorkTime 統合就業時間帯
	 * @param integrationOfDaily 日別実績(Work)
	 * @param forDeductionTimeZones 控除項目の時間帯
	 * @param holidayCalcMethodSet 休暇の計算方法の設定
	 * @param timeLeavingWork 出退勤
	 * @param creatingWithinWorkTimeSheet 就業時間内時間帯
	 * @return 出退勤
	 */
	private TimeLeavingWork calcLateTimeSheet(
			WorkType todayWorkType,
			IntegrationOfWorkTime integrationOfWorkTime,
			IntegrationOfDaily integrationOfDaily,
			List<TimeSheetOfDeductionItem> forDeductionTimeZones,
			HolidayCalcMethodSet holidayCalcMethodSet,
			TimeLeavingWork timeLeavingWork,
			WithinWorkTimeSheet creatingWithinWorkTimeSheet){
		
		//所定時間帯を取得する
		PredetermineTimeSetForCalc predetermineTimeSet = this.getPredetermineTimeSheetForFlow(timeLeavingWork.getWorkNo(), todayWorkType);
		
		//計算範囲を判断する
		creatingWithinWorkTimeSheet.getWithinWorkTimeFrame().add(this.createWithinWorkTimeFrameIncludingCalculationRange(
				todayWorkType,
				integrationOfDaily,
				integrationOfWorkTime.getFlowWorkSetting().get(),
				timeLeavingWork,
				predetermineTimeSet));

		//遅刻時間帯を計算
		timeLeavingWork = creatingWithinWorkTimeSheet.calcLateTimeDeduction(
				todayWorkType,
				integrationOfWorkTime,
				forDeductionTimeZones,
				holidayCalcMethodSet,
				timeLeavingWork,
				predetermineTimeSet);
		
		if(!timeLeavingWork.getAttendanceStampTimeWithDay().isPresent())
			return timeLeavingWork;
		
		//時間帯.出勤←流動勤務用出退勤.出勤
		creatingWithinWorkTimeSheet.getWithinWorkTimeFrame().get(timeLeavingWork.getWorkNo().v() - 1).shiftStart(
				timeLeavingWork.getAttendanceStampTimeWithDay().get());
		
		return timeLeavingWork;
	}
	
	/**
	 * 早退時間帯の計算
	 * @param todayWorkType 勤務種類
	 * @param integrationOfWorkTime 統合就業時間帯
	 * @param forDeductionTimeZones 控除項目の時間帯
	 * @param holidayCalcMethodSet 休暇の計算方法の設定
	 * @param timeLeavingWork 出退勤
	 * @param creatingWithinWorkTimeSheet 就業時間内時間帯
	 * @return  出退勤
	 */
	private TimeLeavingWork calcLeaveEarlyTimeSheet(
			WorkType todayWorkType,
			IntegrationOfWorkTime integrationOfWorkTime,
			List<TimeSheetOfDeductionItem> forDeductionTimeZones,
			HolidayCalcMethodSet holidayCalcMethodSet,
			TimeLeavingWork timeLeavingWork,
			WithinWorkTimeSheet creatingWithinWorkTimeSheet){
		
		//所定時間帯を取得する
		PredetermineTimeSetForCalc predetermineTimeSet = getPredetermineTimeSheetForFlow(timeLeavingWork.getWorkNo(), todayWorkType);
		
		//早退時間帯を計算
		timeLeavingWork = creatingWithinWorkTimeSheet.calcLeaveEarlyTimeDeduction(
				todayWorkType,
				integrationOfWorkTime,
				forDeductionTimeZones,
				holidayCalcMethodSet,
				timeLeavingWork,
				predetermineTimeSet);
		
		if(!timeLeavingWork.getleaveStampTimeWithDay().isPresent())
			return timeLeavingWork;
		
		//時間帯.退勤←流動勤務用出退勤.退勤
		creatingWithinWorkTimeSheet.getWithinWorkTimeFrame().get(timeLeavingWork.getWorkNo().v() - 1).shiftEnd(
				timeLeavingWork.getleaveStampTimeWithDay().get());
		
		return timeLeavingWork;
	}
	
	/**
	* 所定時間帯を取得する(流動)_自身の計算用所定時間設定を補正した新しいインスタンスを作成して返す
	* @param workNo 勤務NO
	* @param workType 勤務種類
	* @return PredetermineTimeSetForCalc 所定時間設定(計算用クラス)
	*/
	private PredetermineTimeSetForCalc getPredetermineTimeSheetForFlow(
			WorkNo workNo,
			WorkType workType){
		
		PredetermineTimeSetForCalc copiedPredetermineTimeSetForCalc = this.predetermineTimeSetForCalc.clone();
	
		//予定と実績が同じ勤務の場合
		if(this.workInformationOfDaily.isMatchWorkInfomation()){
			
			//予定勤務から参照
			Optional<ScheduleTimeSheet> scheduleTimeSheet = this.workInformationOfDaily.getScheduleTimeSheet(workNo);
			
			if(scheduleTimeSheet.isPresent()) {
				copiedPredetermineTimeSetForCalc.getTimeSheets().get(workNo.v()).updateStartTime(scheduleTimeSheet.get().getAttendance());
				copiedPredetermineTimeSetForCalc.getTimeSheets().get(workNo.v()).updateEndTime(scheduleTimeSheet.get().getLeaveWork());
			}
		}
		//午前勤務、午後勤務の場合に時間帯を補正する
		copiedPredetermineTimeSetForCalc.correctPredetermineTimeSheet(workType.getDailyWork(),workNo.v());
		return copiedPredetermineTimeSetForCalc;
	}
	
	/**
	 * 計算範囲を判断（流動）
	 * @param todayWorkType 勤務種類
	 * @param integrationOfDaily 日別実績(Work)
	 * @param flowWorkSetting 流動勤務設定
	 * @param timeLeavingWork 出退勤
	 * @param predetermineTimeSet 計算用所定時間設定
	 * @return 就業時間内時間枠
	 */
	private WithinWorkTimeFrame createWithinWorkTimeFrameIncludingCalculationRange(
			WorkType todayWorkType,
			IntegrationOfDaily integrationOfDaily,
			FlowWorkSetting flowWorkSetting,
			TimeLeavingWork timeLeavingWork,
			PredetermineTimeSetForCalc predetermineTimeSet) {
		
		//出退勤の計算範囲を計算用クラスとして作成
		TimeSpanForDailyCalc calcRange = new TimeSpanForDailyCalc(timeLeavingWork.getTimeZone().getStart(), timeLeavingWork.getTimeZone().getEnd());
		
		if(todayWorkType.getAttendanceHolidayAttr().isMorning()) {
			//午前終了の時刻までの時間帯に補正する
			calcRange = calcRange.shiftOnlyEnd(predetermineTimeSet.getAMEndTime());
		}
		
		if(todayWorkType.getAttendanceHolidayAttr().isAfternoon()) {
			//午後開始の時刻からの時間帯に補正する
			calcRange = calcRange.shiftOnlyStart(predetermineTimeSet.getPMStartTime());
		}
		
		if(isCalculateFromScheduleStartTime(calcRange.getStart(), integrationOfDaily, flowWorkSetting)) {
			//予定勤務時間帯．出勤時刻までの時間帯に補正する
			calcRange = calcRange.shiftOnlyStart(integrationOfDaily.getWorkInformation().getScheduleTimeSheet(new WorkNo(1)).get().getAttendance());
		}
		
		//就業時間内時間枠作成
		return new WithinWorkTimeFrame(
				new EmTimeFrameNo(timeLeavingWork.getWorkNo().v()),
				calcRange,
				calcRange,
				new TimeRoundingSetting(Unit.ROUNDING_TIME_1MIN, Rounding.ROUNDING_DOWN),
				new ArrayList<>(),
				new ArrayList<>(),
				new ArrayList<>(),
				Optional.empty(),
				new ArrayList<>(),
				Optional.empty(),
				Optional.empty());
	}
	
	/**
	 * 予定開始時刻から計算するかチェック
	 * @param startTime 開始時刻
	 * @param integrationOfDaily 日別実績(Work)
	 * @param flowWorkSetting 流動勤務設定
	 * @return true：計算する　false：計算しない
	 */
	private boolean isCalculateFromScheduleStartTime(
			TimeWithDayAttr startTime,
			IntegrationOfDaily integrationOfDaily,
			FlowWorkSetting flowWorkSetting) {
		
		//出勤時刻から計算する
		if(flowWorkSetting.getFlowSetting().getCalculateSetting().getCalcStartTimeSet() == PrePlanWorkTimeCalcMethod.CALC_FROM_WORK_TIME) return false;
		
		//一致しない 
		if(!integrationOfDaily.getWorkInformation().isMatchWorkInfomation()) return false;
		
		//計算開始時刻>=予定勤務時間帯．出勤時刻
		if(!integrationOfDaily.getWorkInformation().getScheduleTimeSheet(new WorkNo(1)).isPresent()) return false;
		if(startTime.greaterThanOrEqualTo(integrationOfDaily.getWorkInformation().getScheduleTimeSheet(new WorkNo(1)).get().getAttendance())) return false;
		
		//計算する
		return true;
	}
	
	/**
	 * 1回目と2回目の間の時間帯を作成
	 * @param timeLeavingForFlowWork 出退勤
	 * @param workType 勤務種類
	 * @param flowWorkSetting 流動勤務設定
	 * @return Optional<TimeSheetOfDeductionItem>  控除項目の時間帯
	 */
	private Optional<TimeSheetOfDeductionItem> createBetweenWork(
			List<TimeLeavingWork> timeLeavingForFlowWork,
			FlowWorkSetting flowWorkSetting) {
		
		//1回目の退勤と2回目の出勤がない
		if(!this.existsFirstEndAndSecondStart(timeLeavingForFlowWork)) return Optional.empty();
		
		//時間帯を作成
		TimeSpanForDailyCalc betweenWorkTimeSheet = createBetweenWorkTimeSheet(timeLeavingForFlowWork, flowWorkSetting);
		
		//控除時間帯を作成
		Optional<TimeSheetOfDeductionItem> deductionTimeBetweenWork = Optional.of(TimeSheetOfDeductionItem.createTimeSheetOfDeductionItemAsFixed(
				betweenWorkTimeSheet, 
				new TimeRoundingSetting(Unit.ROUNDING_TIME_1MIN, Rounding.ROUNDING_DOWN),
				Collections.emptyList(),
				Collections.emptyList(),
				WorkingBreakTimeAtr.WORKING,
				Finally.empty(),
				Finally.empty(),
				Optional.empty(),
				//休憩として扱う場合、就業時間から控除し休憩時間に計上する(控除種別.休憩)　休憩として扱わない場合、就業時間から控除するが休憩時間には計上しない(控除種別.計上なし)
				flowWorkSetting.getRestSetting().getFlowRestSetting().isUsePluralWorkRestTime() ? DeductionClassification.BREAK : DeductionClassification.NON_RECORD,
				Optional.empty()));
		
		return deductionTimeBetweenWork;
	}
	
	/**
	 * 出退勤に時刻が埋まっているかどうかを確認する(1回目の退勤と2回目の出勤が埋まっているか)
	 * @param timeLeavingWorks 出退勤（List)
	 * @return true：埋まっている false：埋まっていない
	 */
	private boolean existsFirstEndAndSecondStart(List<TimeLeavingWork> timeLeavingWorks) {
		if(timeLeavingWorks.size() < 2) return false;
		
		//1回目の退勤
		Optional<TimeLeavingWork> first = timeLeavingWorks.stream()
				.filter(a -> a.getWorkNo().equals(new WorkNo(1)))
				.findFirst();
		if(!first.isPresent()) return false;
		if(!first.get().getLeaveStamp().isPresent()) return false;
		
		//2回目の出勤
		Optional<TimeLeavingWork> second = timeLeavingWorks.stream()
				.filter(a -> a.getWorkNo().equals(new WorkNo(2)))
				.findFirst();
		if(!second.isPresent()) return false;
		if(!second.get().getAttendanceStamp().isPresent()) return false;

		return true;
	}
	
	/**
	 * 時間帯を判断（流動_勤務間）
	 * @param timeLeavingWorks 出退勤
	 * @param flowWorkSetting 流動勤務設定
	 * @return 勤務間の時間帯
	 */
	private TimeSpanForDailyCalc createBetweenWorkTimeSheet(
			List<TimeLeavingWork> timeLeavingWorks,
			FlowWorkSetting flowWorkSetting) {
		
		TimeSpanForDailyCalc betweenWorkTimeSheet = new TimeSpanForDailyCalc(TimeWithDayAttr.THE_PRESENT_DAY_0000, TimeWithDayAttr.THE_PRESENT_DAY_0000);
		
		//1回目の勤務の退勤>=所定
		if(timeLeavingWorks.get(0).getTimespan().getEnd().greaterThanOrEqualTo(this.predetermineTimeSetForCalc.getTimeSheets().get(0).getEnd().v())) {
			betweenWorkTimeSheet.shiftOnlyStart(timeLeavingWorks.get(0).getTimespan().getEnd());
		}
		else {
			betweenWorkTimeSheet.shiftOnlyStart(this.predetermineTimeSetForCalc.getTimeSheets().get(0).getEnd());
		}
		
		//2回目の勤務の出勤>=所定
		if(timeLeavingWorks.get(1).getTimespan().getStart().greaterThanOrEqualTo(this.predetermineTimeSetForCalc.getTimeSheets().get(1).getStart().v())) {
			 betweenWorkTimeSheet.shiftOnlyStart(this.predetermineTimeSetForCalc.getTimeSheets().get(1).getStart());
		}
		else {
			//予定開始時刻から計算する
			if(flowWorkSetting.getFlowSetting().getCalculateSetting().getCalcStartTimeSet().equals(PrePlanWorkTimeCalcMethod.CALC_FROM_PLAN_START_TIME)) {
				betweenWorkTimeSheet.shiftOnlyStart(timeLeavingWorks.get(1).getTimespan().getStart());
			}
			//出勤時刻から計算する
			if(flowWorkSetting.getFlowSetting().getCalculateSetting().getCalcStartTimeSet().equals(PrePlanWorkTimeCalcMethod.CALC_FROM_WORK_TIME)) {
				betweenWorkTimeSheet.shiftOnlyStart(this.predetermineTimeSetForCalc.getTimeSheets().get(1).getStart());
			}
		}
		return betweenWorkTimeSheet;
	}

	/**
	 * 流動勤務所定変動
	 * @param companyCommonSetting 会社別設定管理
	 * @param personDailySetting 社員設定管理
	 * @param todayWorkType 勤務種類
	 * @param integrationOfDaily 日別実績(Work)（実績）
	 * @param flowWorkSetting 流動勤務設定
	 * @param schedulePerformance 予定実績
	 */
	private void fluctuationPredeterminedForFlow(
			ManagePerCompanySet companyCommonSetting,
			ManagePerPersonDailySet personDailySetting,
			WorkType todayWorkType,
			IntegrationOfDaily integrationOfDaily,
			FlowWorkSetting flowWorkSetting,
			Optional<SchedulePerformance> schedulePerformance) {
		
		//変動させるかチェックする
		if(!isFluctuationPredeterminedForFlow(todayWorkType, integrationOfDaily, flowWorkSetting.getFlowSetting().getOvertimeSetting(), schedulePerformance))
			return;
		
		//変動させる時間を求める
		AttendanceTimeOfExistMinus fluctuationTime = getFluctuationPredeterminedForFlow(
				companyCommonSetting,
				personDailySetting,
				todayWorkType,
				integrationOfDaily,
				schedulePerformance.get(),
				flowWorkSetting.getFlowSetting().getOvertimeSetting());
		
		if(!fluctuationTime.equals(AttendanceTimeOfExistMinus.ZERO)) {
			//所定時間帯を変動させる
			this.predetermineTimeSetForCalc.fluctuationPredeterminedTimeSheetToSchedule(
					schedulePerformance.get().getCalculationRangeOfOneDay().getAttendanceLeavingWork().getTimeLeavingWorks());
			
			//所定時間を変動させる
			this.predetermineTimeSetForCalc.getAdditionSet().fluctuationPredeterminedTimeForFlow(fluctuationTime);
			
			//残業時間帯を変動させる
			flowWorkSetting.getHalfDayWorkTimezone().getWorkTimeZone().fluctuationElapsedTimeInLstOTTimezone(fluctuationTime);
		}
	}
	
	/**
	 * 変動させる時間を求める
	 * @param companyCommonSetting 会社別設定管理
	 * @param personDailySetting 社員設定管理
	 * @param todayWorkType 勤務種類
	 * @param integrationOfDaily 日別実績(Work)（実績）
	 * @param schedulePerformance 予定実績
	 * @param FlowOTSet 流動残業設定
	 * @return changeTime 変動させる時間
	 */
	private AttendanceTimeOfExistMinus getFluctuationPredeterminedForFlow(
			ManagePerCompanySet companyCommonSetting,
			ManagePerPersonDailySet personDailySetting,
			WorkType todayWorkType,
			IntegrationOfDaily integrationOfDaily,
			SchedulePerformance schedulePerformance,
			FlowOTSet flowOTSet) {
		
		//予定の所定時間を計算する
		AttendanceTime schedulePredetermineTime = WorkScheduleTimeOfDaily.calcPredeterminedFromTime(
				companyCommonSetting,
				personDailySetting,
				integrationOfDaily,
				schedulePerformance,
				flowOTSet);
		
		//実績の所定時間を計算する
		AttendanceTime recordPredetermineTime = this.predetermineTimeSetForCalc.getpredetermineTime(todayWorkType.getDailyWork());
		
		//予定の所定時間 - 実績の所定時間
		AttendanceTimeOfExistMinus fluctuationTime = new AttendanceTimeOfExistMinus(schedulePredetermineTime.v()).minusMinutes(recordPredetermineTime.v());
		
		//所定変動区分が「後にズラす」
		if(flowOTSet.getFixedChangeAtr() == FixedChangeAtr.AFTER_SHIFT && fluctuationTime.isNegative()) {
			fluctuationTime = AttendanceTimeOfExistMinus.ZERO;
		}
		return fluctuationTime;
	}
	
	/**
	 * 変動させるかチェックする
	 * @param todayWorkType 勤務種類
	 * @param integrationOfDaily 日別実績(Work)（実績）
	 * @param FlowOTSet 流動残業設定
	 * @param manageReGetClassOfSchedule 時間帯作成、時間計算で再取得が必要になっているクラスたちの管理クラス(予定）
	 * @return true:変動させる false:変動させない
	 */
	private boolean isFluctuationPredeterminedForFlow(
			WorkType todayWorkType,
			IntegrationOfDaily integrationOfDaily,
			FlowOTSet flowOTSet,
			Optional<SchedulePerformance> schedulePerformance) {
		
		if(!schedulePerformance.isPresent() //予定を渡している場合（予定の計算時には変動させない為）
			|| !integrationOfDaily.getWorkInformation().isMatchWorkInfomation() //勤務実績と勤務予定の勤務情報が一致しない
			|| todayWorkType.chechAttendanceDay().equals(AttendanceDayAttr.HOLIDAY_WORK) //勤務実績の勤務種類が休出
			|| flowOTSet.getFixedChangeAtr().equals(FixedChangeAtr.NOT_CHANGE)) { //所定変動区分が「変動しない」
			return false;
		}
		return true;
	}
}
