package nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailycalprocess.calculation.timezone.outsideworktime;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import lombok.Getter;
import lombok.val;
import nts.gul.util.value.Finally;
import nts.uk.ctx.at.shared.dom.PremiumAtr;
import nts.uk.ctx.at.shared.dom.common.time.AttendanceTime;
import nts.uk.ctx.at.shared.dom.common.time.TimeSpanForCalc;
import nts.uk.ctx.at.shared.dom.common.timerounding.Rounding;
import nts.uk.ctx.at.shared.dom.common.timerounding.TimeRoundingSetting;
import nts.uk.ctx.at.shared.dom.common.timerounding.Unit;
import nts.uk.ctx.at.shared.dom.scherec.addsettingofworktime.CalcurationByActualTimeAtr;
import nts.uk.ctx.at.shared.dom.scherec.addsettingofworktime.DeductLeaveEarly;
import nts.uk.ctx.at.shared.dom.scherec.addsettingofworktime.HolidayCalcMethodSet;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.autocalsetting.ActualWorkTimeSheetAtr;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.autocalsetting.AutoCalOvertimeSetting;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.bonuspay.setting.BonusPaySetting;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.WithinStatutoryTimeOfDaily;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.attendancetime.TimeLeavingWork;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.common.TimeDivergenceWithCalculation;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.common.TimeWithCalculation;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.dailyattendancework.IntegrationOfDaily;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.paytime.SpecificDateAttrOfDailyAttd;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.vacationusetime.VacationClass;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.workingstyle.flex.FlexCalcMethod;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.workingstyle.flex.FlexCalcMethodOfEachPremiumHalfWork;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.workingstyle.flex.FlexCalcMethodOfHalfWork;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.workingstyle.flex.SettingOfFlexWork;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailycalprocess.calculation.ActualWorkingTimeSheet;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailycalprocess.calculation.CalcMethodOfNoWorkingDayForCalc;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailycalprocess.calculation.DeductionTimeSheet;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailycalprocess.calculation.ManagePerCompanySet;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailycalprocess.calculation.ManagePerPersonDailySet;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailycalprocess.calculation.PredetermineTimeSetForCalc;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailycalprocess.calculation.SpecBonusPayTimeSheetForCalc;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailycalprocess.calculation.TimeSpanForDailyCalc;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailycalprocess.calculation.timezone.deductiontime.DeductionAtr;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailycalprocess.calculation.timezone.deductiontime.TimeSheetOfDeductionItem;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailycalprocess.calculation.timezone.someitems.BonusPayTimeSheetForCalc;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailycalprocess.calculation.timezone.withinworkinghours.WithinWorkTimeSheet;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.midnighttimezone.MidNightTimeSheet;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.midnighttimezone.MidNightTimeSheetForCalc;
import nts.uk.ctx.at.shared.dom.vacation.setting.addsettingofworktime.HolidayAdditionAtr;
import nts.uk.ctx.at.shared.dom.workrule.outsideworktime.StatutoryAtr;
import nts.uk.ctx.at.shared.dom.workrule.outsideworktime.overtime.overtimeframe.OverTimeFrameNo;
import nts.uk.ctx.at.shared.dom.worktime.IntegrationOfWorkTime;
import nts.uk.ctx.at.shared.dom.worktime.common.EmTimezoneNo;
import nts.uk.ctx.at.shared.dom.worktime.common.HolidayCalculation;
import nts.uk.ctx.at.shared.dom.worktime.common.OverTimeOfTimeZoneSet;
import nts.uk.ctx.at.shared.dom.worktime.common.SettlementOrder;
import nts.uk.ctx.at.shared.dom.worktime.common.WorkTimezoneCommonSet;
import nts.uk.ctx.at.shared.dom.worktime.flowset.FlowOTTimezone;
import nts.uk.ctx.at.shared.dom.worktime.flowset.FlowWorkSetting;
import nts.uk.ctx.at.shared.dom.worktime.flowset.FlowWorkTimezoneSetting;
import nts.uk.ctx.at.shared.dom.worktype.WorkType;
import nts.uk.ctx.at.shared.dom.worktype.WorkTypeClassification;
import nts.uk.shr.com.enumcommon.NotUseAtr;
import nts.uk.shr.com.time.TimeWithDayAttr;

/**
 * 残業枠時間帯(WORK)
 * @author keisuke_hoshina
 *
 */
@Getter
public class OverTimeFrameTimeSheetForCalc extends ActualWorkingTimeSheet {
	
	//枠時間
	private OverTimeFrameTime frameTime;
	
	//法定内区分
	private  StatutoryAtr withinStatutryAtr;
	
	//早出区分
	private boolean goEarly;
	
	//残業時間帯No
	private EmTimezoneNo overTimeWorkSheetNo;
	
	//拘束時間として扱う
	private boolean asTreatBindTime;
	
	//精算順序
	private Optional<SettlementOrder> payOrder;

	//調整時間
	private Optional<AttendanceTime> adjustTime;

	
	
	/**
	 * Constrcotr
	 * @param timeSheet
	 * @param calcrange
	 * @param deductionTimeSheets
	 * @param bonusPayTimeSheet
	 * @param specifiedBonusPayTimeSheet
	 * @param midNighttimeSheet
	 * @param frameTime
	 * @param withinStatutryAtr
	 * @param goEarly
	 * @param overTimeWorkSheetNo
	 * @param asTreatBindTime
	 * @param adjustTime
	 */
	public OverTimeFrameTimeSheetForCalc(TimeSpanForDailyCalc timeSheet, TimeRoundingSetting rounding,
			List<TimeSheetOfDeductionItem> recorddeductionTimeSheets,
			List<TimeSheetOfDeductionItem> deductionTimeSheets, List<BonusPayTimeSheetForCalc> bonusPayTimeSheet,
			List<SpecBonusPayTimeSheetForCalc> specifiedBonusPayTimeSheet,
			Optional<MidNightTimeSheetForCalc> midNighttimeSheet, OverTimeFrameTime frameTime,
			StatutoryAtr withinStatutryAtr, boolean goEarly, EmTimezoneNo overTimeWorkSheetNo, boolean asTreatBindTime,
			Optional<SettlementOrder> payOrder, Optional<AttendanceTime> adjustTime) {
		super(timeSheet, rounding, recorddeductionTimeSheets, deductionTimeSheets, bonusPayTimeSheet,
				specifiedBonusPayTimeSheet, midNighttimeSheet);
		this.frameTime = frameTime;
		this.withinStatutryAtr = withinStatutryAtr;
		this.goEarly = goEarly;
		this.overTimeWorkSheetNo = overTimeWorkSheetNo;
		this.asTreatBindTime = asTreatBindTime;
		this.payOrder = payOrder;
		this.adjustTime = adjustTime;
	}
	
	/**
	 * constructor
	 * @param start
	 * @param end
	 * @param rounding
	 * @param timeSheetOfDeductionItems
	 * @param frame
	 * @param overTimeWorkSheetNo
	 */
	public OverTimeFrameTimeSheetForCalc(
			TimeWithDayAttr start,
			TimeWithDayAttr end,
			TimeRoundingSetting rounding,
			List<TimeSheetOfDeductionItem> timeSheetOfDeductionItems,
			OverTimeFrameTime frame,
			EmTimezoneNo overTimeWorkSheetNo) {
		
		super(new TimeSpanForDailyCalc(start, end),
				rounding,
				timeSheetOfDeductionItems,
				timeSheetOfDeductionItems,
				new ArrayList<>(),
				new ArrayList<>(),
				Optional.empty());
		this.frameTime = frame;
		this.withinStatutryAtr = StatutoryAtr.Excess;
		this.goEarly = false;
		this.overTimeWorkSheetNo = overTimeWorkSheetNo;
		this.asTreatBindTime = false;
		this.payOrder = Optional.empty();
		this.adjustTime = Optional.empty();
	}
	
	/**
	 * 計算用残業枠時間帯から残業枠時間帯へ変換する
	 * @return　残業枠時間帯
	 */
	public OverTimeFrameTimeSheet changeNotWorkFrameTimeSheet() {
		return new OverTimeFrameTimeSheet(this.timeSheet, this.frameTime.getOverWorkFrameNo());
	}


	/**
	 * 残業枠分ループし残業枠時間帯の作成
	 * アルゴリズム：固定勤務(平日・就外）
	 * @param companyCommonSetting 会社別設定管理
	 * @param personDailySetting 社員設定管理
	 * @param todayWorkType 勤務種類
	 * @param integrationOfWorkTime 統合就業時間帯
	 * @param integrationOfDaily 日別実績(Work)
	 * @param predetermineTimeSetForCalc 日別計算用時間帯
	 * @param deductionTimeSheet 控除時間帯
	 * @param timeLeavingWork 出退勤
	 * @param createdWithinWorkTimeSheet 就業時間内時間帯
	 * @return 残業枠時間帯(WORK)(List)
	 */
	public static List<OverTimeFrameTimeSheetForCalc> createOverWorkFrame(
			ManagePerCompanySet companyCommonSetting,
			ManagePerPersonDailySet personDailySetting,
			WorkType todayWorkType,
			IntegrationOfWorkTime integrationOfWorkTime,
			IntegrationOfDaily integrationOfDaily,
			PredetermineTimeSetForCalc predetermineTimeSetForCalc,
			DeductionTimeSheet deductionTimeSheet,
			TimeLeavingWork timeLeavingWork,
			WithinWorkTimeSheet createdWithinWorkTimeSheet) {
		List<OverTimeFrameTimeSheetForCalc> createTimeSheet = new ArrayList<>();
		
		for(OverTimeOfTimeZoneSet overTimeHourSet:integrationOfWorkTime.getOverTimeOfTimeZoneSetList(todayWorkType)) {
			
			Optional<TimeSpanForCalc> calcrange = overTimeHourSet.getTimezone().getDuplicatedWith(timeLeavingWork.getTimespan());
			if(calcrange.isPresent()) {
				createTimeSheet.add(OverTimeFrameTimeSheetForCalc.createOverWorkFramTimeSheet(
						overTimeHourSet,
						new TimeSpanForDailyCalc(calcrange.get()),
						personDailySetting.getBonusPaySetting(),
						companyCommonSetting.getMidNightTimeSheet(),
						deductionTimeSheet,
						Optional.of(integrationOfWorkTime.getCommonSetting()),
						integrationOfDaily.getSpecDateAttr()));
			}
		}
		///*変形残業　振替*/
		List<OverTimeFrameTimeSheetForCalc> afterVariableWork = new ArrayList<>();
		afterVariableWork = dicisionCalcVariableWork(
				companyCommonSetting,
				personDailySetting,
				todayWorkType,
				integrationOfWorkTime,
				integrationOfDaily,
				predetermineTimeSetForCalc,
				createTimeSheet);
		afterVariableWork = afterVariableWork.stream()
				.sorted((first,second) -> first.getTimeSheet().getStart().compareTo(second.getTimeSheet().getStart()))
				.collect(Collectors.toList());
		///*法定内残業　振替*/
		List<OverTimeFrameTimeSheetForCalc> afterCalcStatutoryOverTimeWork = new ArrayList<>();
		afterCalcStatutoryOverTimeWork = diciaionCalcStatutory(
				companyCommonSetting,
				personDailySetting,
				todayWorkType,
				integrationOfWorkTime,
				integrationOfDaily,
				predetermineTimeSetForCalc,
				afterVariableWork,
				createdWithinWorkTimeSheet);
		
		/*return*/
		return afterCalcStatutoryOverTimeWork;
	}

	/**
	 * 残業枠時間帯の作成
	 * @param overTimeHourSet 残業時間の時間帯設定
	 * @param timeSpan 計算範囲
	 * @param integrationOfDaily 
	 * @return
	 */
	public static OverTimeFrameTimeSheetForCalc createOverWorkFramTimeSheet(OverTimeOfTimeZoneSet overTimeHourSet,TimeSpanForDailyCalc timeSpan,
																			Optional<BonusPaySetting> bonuspaySetting,MidNightTimeSheet midNightTimeSheet,
																			DeductionTimeSheet deductionTimeSheet,Optional<WorkTimezoneCommonSet> commonSetting,
																			Optional<SpecificDateAttrOfDailyAttd> specificDateAttrSheets) {
		
		List<TimeSheetOfDeductionItem> dedTimeSheet = deductionTimeSheet.getDupliRangeTimeSheet(timeSpan, DeductionAtr.Deduction);
		dedTimeSheet.forEach(tc ->{
			tc.changeReverceRounding(tc.getRounding(), ActualWorkTimeSheetAtr.OverTimeWork, DeductionAtr.Deduction, commonSetting);
		});
		List<TimeSheetOfDeductionItem> recordTimeSheet = deductionTimeSheet.getDupliRangeTimeSheet(timeSpan, DeductionAtr.Appropriate);
		recordTimeSheet.forEach(tc ->{
			tc.changeReverceRounding(tc.getRounding(), ActualWorkTimeSheetAtr.OverTimeWork, DeductionAtr.Appropriate, commonSetting);
		});
		/*加給*/
		val duplibonusPayTimeSheet = getBonusPayTimeSheetIncludeDedTimeSheet(bonuspaySetting, timeSpan, recordTimeSheet, recordTimeSheet);
		/*特定日*/
		val duplispecifiedBonusPayTimeSheet = getSpecBonusPayTimeSheetIncludeDedTimeSheet(bonuspaySetting, timeSpan, recordTimeSheet, recordTimeSheet, specificDateAttrSheets);
		/*深夜*/
		val duplicatemidNightTimeSheet = getMidNightTimeSheetIncludeDedTimeSheet(midNightTimeSheet, timeSpan, recordTimeSheet, recordTimeSheet,commonSetting);
		
		
		OverTimeFrameTime overTimeFrameTime = new OverTimeFrameTime(new OverTimeFrameNo(overTimeHourSet.getOtFrameNo().v()),
				    												TimeDivergenceWithCalculation.sameTime(new AttendanceTime(0)),
				    												TimeDivergenceWithCalculation.sameTime(new AttendanceTime(0)),
				    												new AttendanceTime(0),
				    												new AttendanceTime(0));

		
		
		return new OverTimeFrameTimeSheetForCalc(timeSpan,
											  	overTimeHourSet.getTimezone().getRounding(),
											  	recordTimeSheet.stream().map(tc ->tc.createWithExcessAtr()).collect(Collectors.toList()),
											  	dedTimeSheet.stream().map(tc ->tc.createWithExcessAtr()).collect(Collectors.toList()),
											  	duplibonusPayTimeSheet,
											  	duplispecifiedBonusPayTimeSheet,
											  	duplicatemidNightTimeSheet,
											  	overTimeFrameTime,
											  	StatutoryAtr.Excess,
											  	overTimeHourSet.isEarlyOTUse(),
											  	overTimeHourSet.getWorkTimezoneNo(),
											  	false,
											  	Optional.of(overTimeHourSet.getSettlementOrder()),
											  	Optional.empty()
											  	);
	}

	
	/**
	 * 変形労働を計算するか判定
	 * @param companyCommonSetting 会社別設定管理
	 * @param personDailySetting 社員設定管理
	 * @param todayWorkType 勤務種類
	 * @param integrationOfWorkTime 統合就業時間帯
	 * @param integrationOfDaily 日別実績(Work)
	 * @param predetermineTimeSetForCalc 計算用所定時間設定
	 * @param createTimeSheet 残業枠時間帯(WORK)(List)
	 * @return 残業枠時間帯(WORK)(List)
	 */
	public static List<OverTimeFrameTimeSheetForCalc> dicisionCalcVariableWork(
			ManagePerCompanySet companyCommonSetting,
			ManagePerPersonDailySet personDailySetting,
			WorkType todayWorkType,
			IntegrationOfWorkTime integrationOfWorkTime,
			IntegrationOfDaily integrationOfDaily,
			PredetermineTimeSetForCalc predetermineTimeSetForCalc,
			List<OverTimeFrameTimeSheetForCalc> createTimeSheet) {
		//変形労働か
		if(personDailySetting.getPersonInfo().getLaborSystem().isVariableWorkingTimeWork()) {
			//変形基準内残業計算するか
			if(companyCommonSetting.getDeformLaborOT().isLegalOtCalc()) {
				//変形できる時間計算
				AttendanceTime ableRangeTime =
						new AttendanceTime(personDailySetting.getDailyUnit().getDailyTime().valueAsMinutes()
								- predetermineTimeSetForCalc.getAdditionSet().getPredTime().getPredetermineWorkTime());
				if(ableRangeTime.greaterThan(0))
					return reclassified(
							ableRangeTime,
							createTimeSheet,
							integrationOfDaily.getCalAttr().getOvertimeSetting(),
							integrationOfWorkTime.getLegalOverTimeFrameNoMap(todayWorkType),
							personDailySetting.getAddSetting().getVacationCalcMethodSet());
			}
		}
		return createTimeSheet;
	}
	

	/**
	 * 法定内残業時間の計算をするか判定
	 * @param companyCommonSetting 会社別設定管理
	 * @param personDailySetting 社員設定管理
	 * @param todayWorkType 勤務種類
	 * @param integrationOfWorkTime 統合就業時間帯
	 * @param integrationOfDaily 日別実績(Work)
	 * @param predetermineTimeSetForCalc 計算用所定時間設定
	 * @param overTimeWorkFrameTimeSheetList 計算用所定時間設定（個人平日時）
	 * @param createdWithinWorkTimeSheet 就業時間内時間帯
	 * @return 残業枠時間帯(WORK)(List)
	 */
	public static List<OverTimeFrameTimeSheetForCalc> diciaionCalcStatutory(
			ManagePerCompanySet companyCommonSetting,
			ManagePerPersonDailySet personDailySetting,
			WorkType todayWorkType,
			IntegrationOfWorkTime integrationOfWorkTime,
			IntegrationOfDaily integrationOfDaily,
			PredetermineTimeSetForCalc predetermineTimeSetForCalc,
			List<OverTimeFrameTimeSheetForCalc> overTimeWorkFrameTimeSheetList,
			WithinWorkTimeSheet createdWithinWorkTimeSheet) {
		if(integrationOfWorkTime.isLegalInternalTime()) {
			/*振替処理   法定内基準時間を計算する*/
			AttendanceTime workTime = new AttendanceTime(0);
			if(createdWithinWorkTimeSheet != null){
				boolean isOOtsukaMode = true;
				//大塚納品ではこちらを通るようにする(実働と法定労働から振り替えられる時間を算出)
				if(isOOtsukaMode) {
					workTime = WithinStatutoryTimeOfDaily.calcActualWorkTime(
							createdWithinWorkTimeSheet,
							VacationClass.createAllZero(),
							todayWorkType,
							integrationOfDaily.getCalAttr().getLeaveEarlySetting(),
							personDailySetting.getAddSetting(),
							companyCommonSetting.getHolidayAdditionPerCompany().get(),
							personDailySetting.getAddSetting().getVacationCalcMethodSet(),
							CalcMethodOfNoWorkingDayForCalc.isCalculateFlexTime,
							Optional.of(new SettingOfFlexWork(new FlexCalcMethodOfHalfWork(
									new FlexCalcMethodOfEachPremiumHalfWork(FlexCalcMethod.OneDay, FlexCalcMethod.OneDay),
									new FlexCalcMethodOfEachPremiumHalfWork(FlexCalcMethod.OneDay, FlexCalcMethod.OneDay)))),
							Optional.of(integrationOfWorkTime.getWorkTimeSetting().getWorkTimeDivision().getWorkTimeDailyAtr()),
							Optional.of(integrationOfWorkTime.getCode()),
							new AttendanceTime(2880), //事前フレ
							integrationOfWorkTime.getCoreTimeSetting(),
							predetermineTimeSetForCalc,
							Finally.of(AttendanceTime.ZERO),
							personDailySetting.getDailyUnit(),
							Optional.of(integrationOfWorkTime.getCommonSetting()),
							personDailySetting.getPersonInfo(),
							Optional.of(personDailySetting.getPredetermineTimeSetByPersonWeekDay()),
							Optional.of(new DeductLeaveEarly(0, 1)),
							NotUseAtr.NOT_USE);
				}
				//製品版では就業時間を求めて使うようにする
				else {
					//時間帯の計算で既に遅刻早退が控除された状態となっている為、常に控除しないで渡す。
					Optional<WorkTimezoneCommonSet> leaveLatesetForWorkTime = integrationOfWorkTime.getCommonSetting().getLateEarlySet().getCommonSet().isDelFromEmTime()
							?Optional.of(integrationOfWorkTime.getCommonSetting().reverceTimeZoneLateEarlySet())
							:Optional.of(integrationOfWorkTime.getCommonSetting());
					workTime = createdWithinWorkTimeSheet.calcWorkTime(
							PremiumAtr.RegularWork,
							VacationClass.createAllZero(),
							AttendanceTime.ZERO,
							todayWorkType,
							predetermineTimeSetForCalc,
							Optional.of(integrationOfWorkTime.getCode()),
							integrationOfDaily.getCalAttr().getLeaveEarlySetting(),
							personDailySetting.getAddSetting(),
							companyCommonSetting.getHolidayAdditionPerCompany().get(),
							personDailySetting.getAddSetting().getVacationCalcMethodSet(),
							personDailySetting.getDailyUnit(),
							leaveLatesetForWorkTime,
							personDailySetting.getPersonInfo(),
							Optional.of(personDailySetting.getPredetermineTimeSetByPersonWeekDay()),
							integrationOfWorkTime.getCoreTimeSetting(),
							HolidayAdditionAtr.HolidayAddition.convertFromCalcByActualTimeToHolidayAdditionAtr(CalcurationByActualTimeAtr.CALCULATION_BY_ACTUAL_TIME),
							NotUseAtr.NOT_USE
							).getWorkTime();
				}
			}
		
			AttendanceTime ableRangeTime = new AttendanceTime(personDailySetting.getDailyUnit().getDailyTime().valueAsMinutes() - workTime.valueAsMinutes());
			
			HolidayCalculation holidayCalculation = integrationOfWorkTime.getCommonSetting().getHolidayCalculation();
			if(ableRangeTime.greaterThan(0) && integrationOfDaily.getCalAttr().getOvertimeSetting().getLegalOtTime().getCalAtr().isCalculateEmbossing())
			{
				if(!todayWorkType.getDailyWork().decisionMatchWorkType(WorkTypeClassification.SpecialHoliday).isFullTime() || holidayCalculation.getIsCalculate().isNotUse()) {
					List<OverTimeFrameTimeSheetForCalc> result = reclassified(ableRangeTime,overTimeWorkFrameTimeSheetList.stream()
							.filter(tc -> tc.getPayOrder().isPresent())
							.sorted((first,second) -> first.getPayOrder().get().compareTo(second.getPayOrder().isPresent()
									?second.getPayOrder().get()
									:new SettlementOrder(99)))
							.collect(Collectors.toList()),
					integrationOfDaily.getCalAttr().getOvertimeSetting(),
					integrationOfWorkTime.getLegalOverTimeFrameNoMap(todayWorkType),
					personDailySetting.getAddSetting().getVacationCalcMethodSet());
					
					return result;
				}
			}
		}
		return overTimeWorkFrameTimeSheetList;
	}


	/**
	 * 振替処理
	 * @param ableRangeTime 振替できる時間
	 * @param overTimeWorkFrameTimeSheetList　残業時間枠時間帯クラス
	 * @param autoCalculationSet　時間外の自動計算設定
	 */
	public static List<OverTimeFrameTimeSheetForCalc> reclassified(AttendanceTime ableRangeTime,List<OverTimeFrameTimeSheetForCalc> overTimeWorkFrameTimeSheetList,
			AutoCalOvertimeSetting autoCalculationSet,Map<EmTimezoneNo, OverTimeFrameNo> statutoryOverFrames,HolidayCalcMethodSet holidayCalcMethodSet) {
		boolean forceAtr = true;
		AttendanceTime overTime = new AttendanceTime(0);
		AttendanceTime transTime = new AttendanceTime(0);
		
		for(int number = 0; number < overTimeWorkFrameTimeSheetList.size(); number++) {
			overTime = overTimeWorkFrameTimeSheetList.get(number).correctCalculationTime(Optional.of(forceAtr),autoCalculationSet,DeductionAtr.Deduction).getTime();
			
			if(ableRangeTime.greaterThan(overTime)) {
				transTime = overTime;
			}
			else {
				transTime = ableRangeTime;
			}
			
			if(transTime.lessThanOrEqualTo(0))
				continue;
			
			//振替時間を調整時間に入れる
			if(overTimeWorkFrameTimeSheetList.get(number).adjustTime.isPresent()) {
				overTimeWorkFrameTimeSheetList.get(number).adjustTime = Optional.of(overTimeWorkFrameTimeSheetList.get(number).adjustTime.get().addMinutes(transTime.valueAsMinutes()));
			}
			else {
				overTimeWorkFrameTimeSheetList.get(number).adjustTime = Optional.of(transTime);
			}

			//終了時間の判断
			TimeWithDayAttr endTime = reCreateSiteiTimeFromStartTime(transTime,overTimeWorkFrameTimeSheetList.get(number));
			
			/*ここで分割*/
			overTimeWorkFrameTimeSheetList = correctTimeSpan(overTimeWorkFrameTimeSheetList.get(number).splitTimeSpan(endTime,statutoryOverFrames),overTimeWorkFrameTimeSheetList,number);
			
			ableRangeTime = ableRangeTime.minusMinutes(transTime.valueAsMinutes()) ;
		}
		return overTimeWorkFrameTimeSheetList;
	}
	
	
	/**
	 * 開始から指定時間経過後の終了時刻を取得
	 * @param transTime
	 * @return
	 */
	public static TimeWithDayAttr reCreateSiteiTimeFromStartTime(AttendanceTime transTime,OverTimeFrameTimeSheetForCalc overTimeWork) {
		return overTimeWork.reCreateTreatAsSiteiTimeEnd(transTime,overTimeWork).orElse(overTimeWork.timeSheet).getEnd();
	}
	
	/**
	 * 分割後の残業時間枠時間帯を受け取り
	 * @param insertList　補正した時間帯
	 * @param originList　補正する前の時間帯
	 * @return　
	 */
	public static List<OverTimeFrameTimeSheetForCalc> correctTimeSpan(List<OverTimeFrameTimeSheetForCalc> insertList,List<OverTimeFrameTimeSheetForCalc> originList,int nowNumber){
		originList.remove(nowNumber);
		originList.addAll(insertList);
		List<OverTimeFrameTimeSheetForCalc> returnList = new ArrayList<>();
		returnList = originList;		
		
		boolean nextLoopFlag = true;
		while(nextLoopFlag) {
			for(int index = 0 ; index <= originList.size() ; index++) {
				if(index == originList.size() - 1)
				{
					nextLoopFlag = false;
					break;
				}
				//精算順序の比較
				if(returnList.get(index).getPayOrder().get().greaterThan(returnList.get(index + 1).getPayOrder().get())) {
					OverTimeFrameTimeSheetForCalc pary = returnList.get(index);
					returnList.set(index, returnList.get(index + 1));
					returnList.set(index + 1, pary);
					break;
				}
				//枠の比較(精算順序同じケースの整頓)
				else if(returnList.get(index).getPayOrder().get().equals(returnList.get(index + 1).getPayOrder().get())) {
					if(returnList.get(index).getTimeSheet().getStart().greaterThan(returnList.get(index + 1).getTimeSheet().getStart()) ) {
						OverTimeFrameTimeSheetForCalc pary = returnList.get(index);
						returnList.set(index, returnList.get(index + 1));
						returnList.set(index + 1, pary);
						break;
					}
				}

			}
		}
		
		return originList;
	}
	
	
    /**
     * 時間帯の分割
     * @param baseTime 
     * @param statutoryOverFrames Map<就業時間帯No, 法定内残業枠No>
     * @return
     */
    public List<OverTimeFrameTimeSheetForCalc> splitTimeSpan(TimeWithDayAttr baseTime, Map<EmTimezoneNo, OverTimeFrameNo> statutoryOverFrames){
        List<OverTimeFrameTimeSheetForCalc> returnList = new ArrayList<>();

        val statutoryOverFrameNo = Optional.ofNullable(statutoryOverFrames.get(this.getOverTimeWorkSheetNo()));
		
        if(this.timeSheet.getEnd().equals(baseTime)) {
            returnList.add(new OverTimeFrameTimeSheetForCalc(this.timeSheet
                    										,this.rounding
                    										,this.recordedTimeSheet
                    										,this.deductionTimeSheet
                    										,this.bonusPayTimeSheet
                    										,this.specBonusPayTimesheet
                    										,this.midNightTimeSheet
                    										,this.getFrameTime().changeFrameNo(statutoryOverFrameNo.isPresent()?statutoryOverFrameNo.get().v():this.getFrameTime().getOverWorkFrameNo().v())
                    										,StatutoryAtr.Statutory
                    										,this.goEarly
                    										,this.getOverTimeWorkSheetNo()
                    										,this.asTreatBindTime
                    										,this.payOrder
                    										,this.getAdjustTime()));
        }
        //分割
        else {
        	/*法内へ振り替える側*/
        	//計上の控除時間帯
        	val beforeRec = this.getRecordedTimeSheet().stream()
        										.filter(tc -> tc.createDuplicateRange(new TimeSpanForDailyCalc(this.timeSheet.getStart(), baseTime)).isPresent())
        										.map(tc -> tc.createDuplicateRange(new TimeSpanForDailyCalc(this.timeSheet.getStart(), baseTime)).get())
        										.map(tc -> tc.reCreateOwn(baseTime, true))
        										.collect(Collectors.toList());
        	//控除の控除時間帯
        	val beforeDed = this.getDeductionTimeSheet().stream()
												.filter(tc -> tc.createDuplicateRange(new TimeSpanForDailyCalc(this.timeSheet.getStart(), baseTime)).isPresent())
												.map(tc -> tc.createDuplicateRange(new TimeSpanForDailyCalc(this.timeSheet.getStart(), baseTime)).get())
												.map(tc -> tc.reCreateOwn(baseTime, true))
												.collect(Collectors.toList());
        	val beforeBp = this.getBonusPayTimeSheet().stream()
												.filter(tc -> tc.createDuplicateRange(new TimeSpanForDailyCalc(this.timeSheet.getStart(), baseTime)).isPresent())
												.map(tc -> tc.createDuplicateRange(new TimeSpanForDailyCalc(this.timeSheet.getStart(), baseTime)).get())
												.collect(Collectors.toList());
        	val beforeSpecBp = this.getSpecBonusPayTimesheet().stream()
												.filter(tc -> tc.createDuplicateRange(new TimeSpanForDailyCalc(this.timeSheet.getStart(), baseTime)).isPresent())
												.map(tc -> tc.createDuplicateRange(new TimeSpanForDailyCalc(this.timeSheet.getStart(), baseTime)).get())
												.collect(Collectors.toList());
        	//深夜時間帯
        	Optional<MidNightTimeSheetForCalc> beforeMid = Optional.empty();
        	if(this.getMidNightTimeSheet().isPresent()) {
        		beforeMid = this.getMidNightTimeSheet().get().getDuplicateRangeTimeSheet(new TimeSpanForDailyCalc(this.timeSheet.getStart(), baseTime));
        		if(beforeMid.isPresent()) {
        			beforeMid = beforeMid.get().reCreateOwn(baseTime, true);
        		}
        	}
        	
        													
        	
            returnList.add(new OverTimeFrameTimeSheetForCalc(this.timeSheet
                                                         ,new TimeRoundingSetting(Unit.ROUNDING_TIME_1MIN, Rounding.ROUNDING_DOWN)
                                                         ,beforeRec
                                                         ,beforeDed
                                                         ,beforeBp
                                                         ,beforeSpecBp
                                                         ,beforeMid
                                                         ,this.getFrameTime().changeFrameNo(statutoryOverFrameNo.isPresent()?statutoryOverFrameNo.get().v():this.getFrameTime().getOverWorkFrameNo().v())
                                                         ,StatutoryAtr.Statutory
                                                         ,this.goEarly
                                                         ,this.getOverTimeWorkSheetNo()
                                                         ,this.asTreatBindTime
                                                         ,this.payOrder
                                                         ,Optional.of(new AttendanceTime(0))));
            
            /*法外側*/
        	//計上の控除時間帯
        	val afterRec = this.getRecordedTimeSheet().stream()
        										.filter(tc -> tc.createDuplicateRange(new TimeSpanForDailyCalc(baseTime, this.timeSheet.getEnd())).isPresent())
        										.map(tc -> tc.createDuplicateRange(new TimeSpanForDailyCalc(baseTime, this.timeSheet.getEnd())).get())
        										.map(tc -> tc.reCreateOwn(baseTime, false))
        										.collect(Collectors.toList());
        	//控除の控除時間帯
        	val afterDed = this.getDeductionTimeSheet().stream()
												.filter(tc -> tc.createDuplicateRange(new TimeSpanForDailyCalc(baseTime, this.timeSheet.getEnd())).isPresent())
												.map(tc -> tc.createDuplicateRange(new TimeSpanForDailyCalc(baseTime, this.timeSheet.getEnd())).get())
												.map(tc -> tc.reCreateOwn(baseTime, false))
												.collect(Collectors.toList());
        	val afterBp = this.getBonusPayTimeSheet().stream()
												.filter(tc -> tc.createDuplicateRange(new TimeSpanForDailyCalc(baseTime, this.timeSheet.getEnd())).isPresent())
												.map(tc -> tc.createDuplicateRange(new TimeSpanForDailyCalc( baseTime, this.timeSheet.getEnd())).get())
												.collect(Collectors.toList());
        	val afterSpecBp = this.getSpecBonusPayTimesheet().stream()
												.filter(tc -> tc.createDuplicateRange(new TimeSpanForDailyCalc(baseTime, this.timeSheet.getEnd())).isPresent())
												.map(tc -> tc.createDuplicateRange(new TimeSpanForDailyCalc( baseTime, this.timeSheet.getEnd())).get())
												.collect(Collectors.toList());
        	//深夜時間帯
        	Optional<MidNightTimeSheetForCalc> afterMid = Optional.empty();
        	if(this.getMidNightTimeSheet().isPresent()) {
        		afterMid = this.getMidNightTimeSheet().get().getDuplicateRangeTimeSheet(new TimeSpanForDailyCalc(baseTime, this.timeSheet.getEnd()));
        		if(afterMid.isPresent()) {
        			afterMid = afterMid.get().reCreateOwn(baseTime, false);
        		}
        	}												
            
            returnList.add(new OverTimeFrameTimeSheetForCalc(new TimeSpanForDailyCalc(baseTime, this.timeSheet.getEnd())
                                                          ,this.rounding
                                                         ,afterRec
                                                         ,afterDed
                                                         ,afterBp
                                                         ,afterSpecBp
                                                         ,afterMid
                                                         ,this.getFrameTime().changeFrameNo(this.getFrameTime().getOverWorkFrameNo().v())
                                                         ,StatutoryAtr.Excess
                                                         ,this.goEarly
                                                         ,this.getOverTimeWorkSheetNo()
                                                         ,this.asTreatBindTime
                                                         ,this.payOrder
                                                         ,this.getAdjustTime()));
        }
        return returnList;
    }

	
	/**
	 * 計算処理
	 * 残業時間の計算
	 * @param forceCalcTime 強制時間区分
	 * @param autoCalcSet 残業時間の自動計算設定
	 */
	public TimeWithCalculation correctCalculationTime(
			Optional<Boolean> forceCalcTime,
			AutoCalOvertimeSetting autoCalcSet,
			DeductionAtr dedAtr) {
		
		//「打刻から計算する」
		boolean isCalculateEmboss = false;
		
		if(forceCalcTime.orElse(false)) {
			//強制的に「打刻から計算する」をセット
			isCalculateEmboss = true;
		}
		else {
			//計算区分の判断
			isCalculateEmboss = this.isCalculateEmboss(autoCalcSet);
		}
		//残業時間の計算
		AttendanceTime overTime = overTimeCalculationByAdjustTime();
		
		if(isCalculateEmboss) return TimeWithCalculation.sameTime(overTime);
		
		return TimeWithCalculation.createTimeWithCalculation(AttendanceTime.ZERO, overTime);
	}
	
	/**
	 * 打刻区分の判断処理
	 * @param autoCalcSet 残業時間の自動計算設定
	 * @return 打刻から計算する  true:打刻から計算する false:それ以外
	 */
	public boolean isCalculateEmboss(AutoCalOvertimeSetting autoCalcSet) {
		return autoCalcSet.decisionCalcAtr(this.withinStatutryAtr, this.goEarly);
	}
	
//	/**
//	 * 調整時間を考慮した時間の計算
//	 * @param autoCalcSet 時間外の自動計算区分
//	 * @return 残業時間枠時間帯クラス
//	 */
//	public AttendanceTime overTimeCalculationByAdjustTime(DeductionAtr dedAtr) {
//		//調整時間を加算
//		this.calcrange = new TimeSpanForDailyCalc(this.calcrange.getStart(), this.calcrange.getEnd().forwardByMinutes(this.adjustTime.orElse(new AttendanceTime(0)).valueAsMinutes()));
//		this.timeSheet = new TimeZoneRounding(this.getTimeSheet().getStart(), this.getTimeSheet().getEnd().forwardByMinutes(this.adjustTime.orElse(new AttendanceTime(0)).valueAsMinutes()), this.getTimeSheet().getRounding());
//		AttendanceTime time = this.calcTotalTime(dedAtr);
//		//調整時間を減算(元に戻す)
//		this.calcrange = new TimeSpanForDailyCalc(this.calcrange.getStart(), this.calcrange.getEnd().backByMinutes(this.adjustTime.orElse(new AttendanceTime(0)).valueAsMinutes()));
//		this.timeSheet = new TimeZoneRounding(this.getTimeSheet().getStart(), this.getTimeSheet().getEnd().backByMinutes(this.adjustTime.orElse(new AttendanceTime(0)).valueAsMinutes()), this.getTimeSheet().getRounding());
//		time = time.minusMinutes(this.adjustTime.orElse(new AttendanceTime(0)).valueAsMinutes()) ;
//		return time;
//		
//	}
	
	/**
	 * 調整時間を考慮した時間の計算
	 * @param autoCalcSet 時間外の自動計算区分
	 * @return 残業時間枠時間帯クラス
	 */
	public AttendanceTime overTimeCalculationByAdjustTime() {
		//調整時間を加算
		this.timeSheet = new TimeSpanForDailyCalc(this.timeSheet.getStart(), this.timeSheet.getEnd().forwardByMinutes(this.adjustTime.orElse(new AttendanceTime(0)).valueAsMinutes()));
		AttendanceTime time = this.calcTotalTime();
		//調整時間を減算(元に戻す)
		this.timeSheet = new TimeSpanForDailyCalc(this.timeSheet.getStart(), this.timeSheet.getEnd().backByMinutes(this.adjustTime.orElse(new AttendanceTime(0)).valueAsMinutes()));
		time = time.minusMinutes(this.adjustTime.orElse(new AttendanceTime(0)).valueAsMinutes()) ;
		return time;
		
	}
	
	/**
	 * 残業時間の作成（流動）
	 * @param flowWorkSetting 流動勤務設定
	 * @param processingFlowOTTimezone 処理中の流動残業時間帯
	 * @param timeSheetOfDeductionItems 控除項目の時間帯(List)
	 * @param overTimeStartTime 残業開始時刻
	 * @param leaveStampTime 退勤時刻
	 * @param goOutSet 就業時間帯の外出設定
	 * @param bonusPaySetting 加給設定
	 * @param specDateAttr 日別勤怠の特定日区分
	 * @param midNightTimeSheet 深夜時間帯
	 * @return 残業枠時間帯(WORK)
	 */
	public static OverTimeFrameTimeSheetForCalc createAsFlow(
			FlowWorkSetting flowWorkSetting,
			FlowOTTimezone processingFlowOTTimezone,
			List<TimeSheetOfDeductionItem> timeSheetOfDeductionItems,
			TimeWithDayAttr overTimeStartTime,
			TimeWithDayAttr leaveStampTime,
			Optional<BonusPaySetting> bonusPaySetting,
			Optional<SpecificDateAttrOfDailyAttd> specDateAttr,
			MidNightTimeSheet midNightTimeSheet) {
		
		//終了時刻の計算
		TimeWithDayAttr endTime = OverTimeFrameTimeSheetForCalc.calcEndTimeForFlow(
				processingFlowOTTimezone,
				flowWorkSetting.getHalfDayWorkTimezone().getWorkTimeZone().getLstOTTimezone(),
				timeSheetOfDeductionItems,
				overTimeStartTime,
				leaveStampTime);
		
		//残業枠時間を作成
		OverTimeFrameTime frame = new OverTimeFrameTime(
				new OverTimeFrameNo(processingFlowOTTimezone.getOTFrameNo().v().intValue()),
				TimeDivergenceWithCalculation.defaultValue(),
				TimeDivergenceWithCalculation.defaultValue(),
				AttendanceTime.ZERO,
				AttendanceTime.ZERO);
		
		//時間帯を作成
		OverTimeFrameTimeSheetForCalc overTimeFrame = new OverTimeFrameTimeSheetForCalc(
				overTimeStartTime,
				endTime,
				processingFlowOTTimezone.getFlowTimeSetting().getRounding(),
				timeSheetOfDeductionItems,
				frame,
				new EmTimezoneNo(processingFlowOTTimezone.getWorktimeNo()));
		
		//計上、控除時間帯を補正
		overTimeFrame.trimRecordedAndDeductionToSelfRange();
		//控除時間帯に丸め設定を付与
		overTimeFrame.grantRoundingToDeductionTimeSheet(ActualWorkTimeSheetAtr.OverTimeWork, flowWorkSetting.getCommonSetting());
		//加給時間帯を作成
		overTimeFrame.createBonusPayTimeSheet(bonusPaySetting, specDateAttr);
		//深夜時間帯を作成
		overTimeFrame.createMidNightTimeSheet(midNightTimeSheet, Optional.of(flowWorkSetting.getCommonSetting()));
		
		return overTimeFrame;
	}
	
	/**
	 * 終了時刻の計算（流動）
	 * @param processingFlowOTTimezone 処理中の流動残業時間帯
	 * @param lstOTTimezone 流動残業時間帯(List)
	 * @param timeSheetOfDeductionItems 控除項目の時間帯(List)
	 * @param overTimeStartTime 残業開始時刻
	 * @param leaveStampTime 退勤時刻
	 * @return 終了時刻
	 */
	private static TimeWithDayAttr calcEndTimeForFlow(
			FlowOTTimezone processingFlowOTTimezone,
			List<FlowOTTimezone> lstOTTimezone,
			List<TimeSheetOfDeductionItem> timeSheetOfDeductionItems,
			TimeWithDayAttr overTimeStartTime,
			TimeWithDayAttr leaveStampTime) {
		
		Optional<FlowOTTimezone> plusOneFlowOTTimezone = lstOTTimezone.stream()
				.filter(timezone -> timezone.getWorktimeNo().equals(processingFlowOTTimezone.getWorktimeNo()+1))
				.findFirst();
		
		TimeWithDayAttr endTime = TimeWithDayAttr.THE_PRESENT_DAY_0000;
		
		if(plusOneFlowOTTimezone.isPresent()) {
			//残業枠の時間を計算する
			AttendanceTime overTimeFrameTime = plusOneFlowOTTimezone.get().getFlowTimeSetting().getElapsedTime().minusMinutes(
					processingFlowOTTimezone.getFlowTimeSetting().getElapsedTime().valueAsMinutes());
			
			//残業枠時間から終了時刻を計算する
			endTime = overTimeStartTime.forwardByMinutes(overTimeFrameTime.valueAsMinutes());
			
			TimeSpanForDailyCalc timeSpan = new TimeSpanForDailyCalc(overTimeStartTime, endTime);
			
			//控除時間分、終了時刻をズラす
			endTime = timeSpan.forwardByDeductionTime(timeSheetOfDeductionItems);
			
			//間にinput.退勤時刻があるか確認、input.退勤時刻に置き換え
			if(timeSpan.contains(leaveStampTime)) endTime = leaveStampTime;
		}
		else {
			endTime = leaveStampTime;
		}
		return endTime;
	}
	
	/**
	 * 残業枠時間帯への割当
	 * @param remainingTime 未割り当て時間
	 * @param flowWorkTimezoneSetting 流動勤務時間帯設定
	 * @param autoCalcSet 残業時間の自動計算設定
	 */
	public AttendanceTime allocateToOverTimeFrame(
			AttendanceTime remainingTime,
			FlowWorkTimezoneSetting flowWorkTimezoneSetting,
			AutoCalOvertimeSetting autoCalcSet) {
		
		//割り当て可能な時間を計算
		AttendanceTime allocateTime = this.calcAllocationTime(remainingTime, flowWorkTimezoneSetting, autoCalcSet);
		
		//割当時間をセット
		this.setTimeVacationOverflowTime(Optional.of(allocateTime));
		
		//未割り当て時間を減算
		return remainingTime.minusMinutes(allocateTime.valueAsMinutes());
	}
	
	/**
	 * 割当が可能な時間を計算
	 * @param remainingTime 未割り当て時間
	 * @param flowWorkTimezoneSetting 流動勤務時間帯設定
	 * @param autoCalcSet 残業時間の自動計算設定
	 * @return 割り当て可能な時間
	 */
	public AttendanceTime calcAllocationTime(
			AttendanceTime remainingTime,
			FlowWorkTimezoneSetting flowWorkTimezoneSetting,
			AutoCalOvertimeSetting autoCalcSet) {
		
		//計算処理
		AttendanceTime overTime = this.correctCalculationTime(
				Optional.of(true),
				autoCalcSet,
				DeductionAtr.Deduction).getTime();
		
		if(!flowWorkTimezoneSetting.getMatchWorkNoOverTimeWorkSheet(this.overTimeWorkSheetNo.v()+1).isPresent()) return overTime;
		
		//割当が可能な時間を計算
		AttendanceTime overTimeLimit =
				flowWorkTimezoneSetting.getMatchWorkNoOverTimeWorkSheet(this.overTimeWorkSheetNo.v()+1).get()
				.getFlowTimeSetting().getElapsedTime().minusMinutes(
						flowWorkTimezoneSetting.getMatchWorkNoOverTimeWorkSheet(this.overTimeWorkSheetNo.v()).get()
						.getFlowTimeSetting().getElapsedTime().valueAsMinutes());
		
		return overTimeLimit.lessThan(overTime) ? overTimeLimit : overTime;
	}
	
	/**
	 * 残業枠を作成する（空で作成する）
	 * @param calcrange 計算範囲
	 * @param flowOTTimezone 流動残業時間帯
	 * @return 残業枠時間帯(WORK)
	 */
	public static OverTimeFrameTimeSheetForCalc createEmpty(TimeSpanForDailyCalc calcrange, FlowOTTimezone flowOTTimezone) {
		return new OverTimeFrameTimeSheetForCalc(
				calcrange,
				new TimeRoundingSetting(Unit.ROUNDING_TIME_1MIN, Rounding.ROUNDING_DOWN),
				Collections.emptyList(),
				Collections.emptyList(),
				Collections.emptyList(),
				Collections.emptyList(),
				Optional.empty(),
				new OverTimeFrameTime(
						new OverTimeFrameNo(flowOTTimezone.getOTFrameNo().v().intValue()),
						TimeDivergenceWithCalculation.defaultValue(),
						TimeDivergenceWithCalculation.defaultValue(),
						AttendanceTime.ZERO,
						AttendanceTime.ZERO),
				StatutoryAtr.Excess,
				false,
				new EmTimezoneNo(flowOTTimezone.getWorktimeNo()),
				false,
				Optional.empty(),
				Optional.empty());
	}
	
	/**
	 * 残業枠時間帯への割当
	 * @param lastFrame 残業枠時間帯への割当
	 * @param timeVacationAdditionRemainingTime
	 * @param flowWorkTimezoneSetting
	 * @return 未割り当て時間
	 */
	public AttendanceTime allocateOverflowTimeVacation(
			boolean lastFrame,	
			AttendanceTime timeVacationAdditionRemainingTime,
			FlowWorkTimezoneSetting flowWorkTimezoneSetting) {
		
		//割り当てる時間
		AttendanceTime allocateTime = AttendanceTime.ZERO;
		
		if(lastFrame) {
			allocateTime = timeVacationAdditionRemainingTime;
		}
		else{
			//割当可能な時間を計算
			AttendanceTime limitTime =
				flowWorkTimezoneSetting.getMatchWorkNoOverTimeWorkSheet(this.overTimeWorkSheetNo.v()+1).get()
				.getFlowTimeSetting().getElapsedTime().minusMinutes(
						flowWorkTimezoneSetting.getMatchWorkNoOverTimeWorkSheet(this.overTimeWorkSheetNo.v()).get()
						.getFlowTimeSetting().getElapsedTime().valueAsMinutes());
		
			allocateTime = limitTime.lessThan(timeVacationAdditionRemainingTime) ? limitTime : timeVacationAdditionRemainingTime;
		}
		
		this.setTimeVacationOverflowTime(Optional.of(allocateTime));
		return timeVacationAdditionRemainingTime.minusMinutes(allocateTime.valueAsMinutes());
	}
	
}
