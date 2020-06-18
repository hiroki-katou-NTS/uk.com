package nts.uk.ctx.at.record.dom.daily.withinworktime;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import lombok.Getter;
import lombok.Setter;
import lombok.val;
import nts.arc.time.GeneralDate;
import nts.gul.util.value.Finally;
import nts.uk.ctx.at.record.dom.daily.TimeDivergenceWithCalculation;
import nts.uk.ctx.at.record.dom.daily.TimevacationUseTimeOfDaily;
import nts.uk.ctx.at.record.dom.daily.calcset.CalcMethodOfNoWorkingDayForCalc;
import nts.uk.ctx.at.record.dom.daily.midnight.WithinStatutoryMidNightTime;
import nts.uk.ctx.at.record.dom.dailyprocess.calc.AttendanceItemDictionaryForCalc;
import nts.uk.ctx.at.record.dom.dailyprocess.calc.DeductionTimeSheet;
import nts.uk.ctx.at.record.dom.dailyprocess.calc.FlexWithinWorkTimeSheet;
import nts.uk.ctx.at.record.dom.dailyprocess.calc.IntegrationOfDaily;
import nts.uk.ctx.at.record.dom.dailyprocess.calc.ManagePerPersonDailySet;
import nts.uk.ctx.at.record.dom.dailyprocess.calc.ManageReGetClass;
import nts.uk.ctx.at.record.dom.dailyprocess.calc.PredetermineTimeSetForCalc;
import nts.uk.ctx.at.record.dom.dailyprocess.calc.VacationClass;
import nts.uk.ctx.at.record.dom.dailyprocess.calc.withinstatutory.WithinWorkTimeSheet;
import nts.uk.ctx.at.record.dom.raborstandardact.flex.SettingOfFlexWork;
import nts.uk.ctx.at.record.dom.workrecord.erroralarm.EmployeeDailyPerError;
import nts.uk.ctx.at.record.dom.workrecord.erroralarm.primitivevalue.ErrorAlarmWorkRecordCode;
import nts.uk.ctx.at.shared.dom.PremiumAtr;
import nts.uk.ctx.at.shared.dom.common.time.AttendanceTime;
import nts.uk.ctx.at.shared.dom.ot.autocalsetting.AutoCalAtrOvertime;
import nts.uk.ctx.at.shared.dom.ot.autocalsetting.AutoCalcOfLeaveEarlySetting;
import nts.uk.ctx.at.shared.dom.ot.autocalsetting.TimeLimitUpperLimitSetting;
import nts.uk.ctx.at.shared.dom.scherec.addsettingofworktime.AddSetting;
import nts.uk.ctx.at.shared.dom.scherec.addsettingofworktime.CalcurationByActualTimeAtr;
import nts.uk.ctx.at.shared.dom.scherec.addsettingofworktime.DeductLeaveEarly;
import nts.uk.ctx.at.shared.dom.scherec.addsettingofworktime.HolidayAddtionSet;
import nts.uk.ctx.at.shared.dom.scherec.addsettingofworktime.HolidayCalcMethodSet;
import nts.uk.ctx.at.shared.dom.scherec.addsettingofworktime.PremiumHolidayCalcMethod;
import nts.uk.ctx.at.shared.dom.scherec.addsettingofworktime.WorkDeformedLaborAdditionSet;
import nts.uk.ctx.at.shared.dom.scherec.addsettingofworktime.WorkFlexAdditionSet;
import nts.uk.ctx.at.shared.dom.scherec.addsettingofworktime.WorkRegularAdditionSet;
import nts.uk.ctx.at.shared.dom.scherec.addsettingofworktime.WorkTimeHolidayCalcMethod;
import nts.uk.ctx.at.shared.dom.statutory.worktime.sharedNew.DailyUnit;
import nts.uk.ctx.at.shared.dom.vacation.setting.addsettingofworktime.HolidayAdditionAtr;
import nts.uk.ctx.at.shared.dom.vacation.setting.addsettingofworktime.StatutoryDivision;
import nts.uk.ctx.at.shared.dom.workingcondition.WorkingConditionItem;
import nts.uk.ctx.at.shared.dom.workingcondition.WorkingSystem;
import nts.uk.ctx.at.shared.dom.worktime.IntegrationOfWorkTime;
import nts.uk.ctx.at.shared.dom.worktime.common.WorkTimeCode;
import nts.uk.ctx.at.shared.dom.worktime.common.WorkTimezoneCommonSet;
import nts.uk.ctx.at.shared.dom.worktime.flexset.CoreTimeSetting;
import nts.uk.ctx.at.shared.dom.worktime.worktimeset.WorkTimeDailyAtr;
import nts.uk.ctx.at.shared.dom.worktype.WorkType;
import nts.uk.shr.com.context.AppContexts;
import nts.uk.shr.com.enumcommon.NotUseAtr;

/**
 * 日別実績の所定内時間
 * @author keisuke_hoshina
 *
 */
@Getter
public class WithinStatutoryTimeOfDaily {
	//就業時間
	@Setter
	private AttendanceTime workTime;
	//実働就業時間
	@Setter
	private AttendanceTime actualWorkTime = new AttendanceTime(0);
	//所定内割増時間
	private AttendanceTime withinPrescribedPremiumTime = new AttendanceTime(0);
	//所定内深夜時間
	@Setter
	private WithinStatutoryMidNightTime withinStatutoryMidNightTime = new WithinStatutoryMidNightTime(TimeDivergenceWithCalculation.sameTime(new AttendanceTime(0)));
	//休暇加算時間
	private AttendanceTime vacationAddTime = new AttendanceTime(0);  
	
	/**
	 * Constructor
	 * @param workTime 就業時間
	 * @param actualTime 
	 */
	private WithinStatutoryTimeOfDaily(AttendanceTime workTime,AttendanceTime actualTime, AttendanceTime premiumTime, WithinStatutoryMidNightTime midNightTime) {
		this.workTime = workTime;
		this.actualWorkTime = actualTime;
		this.withinPrescribedPremiumTime = premiumTime;
		this.withinStatutoryMidNightTime = midNightTime;
	}
	
	/**
	 * 全メンバを算出するために計算指示を出す
	 * アルゴリズム：日別実績の所定内時間
	 * @param recordReget 実績
	 * @param vacationClass 休暇クラス
	 * @param workType 勤務種類
	 * @param calcMethod フレックス勤務の非勤務日の場合の計算方法
	 * @param flexCalcMethod フレックス勤務の設定
	 * @param workTimeDailyAtr 勤務形態区分
	 * @param workTimeCode 就業時間帯コード
	 * @param conditionItem 労働条件項目
	 * @param predetermineTimeSetByPersonInfo 計算用所定時間設定（個人）
	 * @return 日別実績の所定内時間
	 */
	public static WithinStatutoryTimeOfDaily calcStatutoryTime(
			ManageReGetClass recordReget,
			VacationClass vacationClass,
			WorkType workType,
			CalcMethodOfNoWorkingDayForCalc calcMethod,
			Optional<SettingOfFlexWork> flexCalcMethod,
			Optional<WorkTimeDailyAtr> workTimeDailyAtr,
			Optional<WorkTimeCode> workTimeCode,
			WorkingConditionItem conditionItem,
			Optional<PredetermineTimeSetForCalc> predetermineTimeSetByPersonInfo) {
		
		AttendanceTime workTime = new AttendanceTime(0);
		AttendanceTime actualTime = new AttendanceTime(0);
		AttendanceTime withinpremiumTime = new AttendanceTime(0);
		
		//ここに計算できる状態でやってきているかチェックをする
		if(recordReget.getCalculationRangeOfOneDay().getWithinWorkingTimeSheet().isPresent() && recordReget.getCalculationRangeOfOneDay().getPredetermineTimeSetForCalc() != null) {
			//所定内割増時間の計算
			val predTime = recordReget.getCalculationRangeOfOneDay().getPredetermineTimeSetForCalc().getpredetermineTime(workType.getDailyWork());
			withinpremiumTime = predTime.greaterThan(recordReget.getDailyUnit().getDailyTime().valueAsMinutes())
										? new AttendanceTime(recordReget.getCalculationRangeOfOneDay().getWithinWorkingTimeSheet().get().getWithinWorkTimeFrame().stream()
												.filter(tc -> tc.getPremiumTimeSheetInPredetermined().isPresent())
												.map(tc -> tc.getPremiumTimeSheetInPredetermined().get().getWithinPremiumtimeSheet().lengthAsMinutes())
												.collect(Collectors.summingInt(tc -> tc)))
										: new AttendanceTime(0);
		}
		
		//事前フレックス
		AttendanceTime preFlexTime = AttendanceTime.ZERO;
		if(recordReget.getIntegrationOfDaily().getAttendanceTimeOfDailyPerformance().get().getActualWorkingTimeOfDaily().getTotalWorkingTime().getExcessOfStatutoryTimeOfDaily().getOverTimeWork().isPresent()
				&& recordReget.getIntegrationOfDaily().getAttendanceTimeOfDailyPerformance().get().getActualWorkingTimeOfDaily().getTotalWorkingTime().getExcessOfStatutoryTimeOfDaily().getOverTimeWork().get().getFlexTime() != null) {
			preFlexTime = recordReget.getIntegrationOfDaily().getAttendanceTimeOfDailyPerformance().get().getActualWorkingTimeOfDaily().getTotalWorkingTime().getExcessOfStatutoryTimeOfDaily().getOverTimeWork().get().getFlexTime().getBeforeApplicationTime();
		}
		
		//就業時間の計算
		workTime = calcWithinStatutoryTime(
				recordReget.getCalculationRangeOfOneDay().getWithinWorkingTimeSheet().get(),
				vacationClass,
				workType,
				recordReget.getIntegrationOfDaily().getCalAttr().getLeaveEarlySetting(),
				recordReget.getAddSetting(),
				recordReget.getHolidayAddtionSet().get(),
				recordReget.getHolidayCalcMethodSet(),
				calcMethod,
				flexCalcMethod,
				workTimeDailyAtr,
				workTimeCode,preFlexTime,
				recordReget.getCoreTimeSetting(),
				recordReget.getCalculationRangeOfOneDay().getPredetermineTimeSetForCalc(),
				recordReget.getCalculationRangeOfOneDay().getWithinWorkingTimeSheet().get().getTimeVacationAdditionRemainingTime(),
				recordReget.getDailyUnit(),
				recordReget.getWorkTimezoneCommonSet(),
				conditionItem,
				predetermineTimeSetByPersonInfo,
				Optional.empty(),
				NotUseAtr.NOT_USE);
		
		//実働就業時間の計算
		actualTime =  calcActualWorkTime(
				recordReget.getCalculationRangeOfOneDay().getWithinWorkingTimeSheet().get(),
				vacationClass,
				workType,
				AutoCalcOfLeaveEarlySetting.createAllTrue(),
				recordReget.getAddSetting(),
				recordReget.getHolidayAddtionSet().get(),
				recordReget.getHolidayCalcMethodSet(),
				calcMethod,
				flexCalcMethod,
				workTimeDailyAtr,
				workTimeCode,preFlexTime,
				recordReget.getCoreTimeSetting(),
				recordReget.getCalculationRangeOfOneDay().getPredetermineTimeSetForCalc(),
				recordReget.getCalculationRangeOfOneDay().getWithinWorkingTimeSheet().get().getTimeVacationAdditionRemainingTime(),
				recordReget.getDailyUnit(),
				recordReget.getWorkTimezoneCommonSet(),
				conditionItem,
				predetermineTimeSetByPersonInfo,
				Optional.of(new DeductLeaveEarly(0, 1)),
				NotUseAtr.USE);

		actualTime = actualTime.minusMinutes(withinpremiumTime.valueAsMinutes());
			
		//所定内深夜時間の計算
		WithinStatutoryMidNightTime midNightTime = WithinStatutoryMidNightTime.calcPredetermineMidNightTime(recordReget.getCalculationRangeOfOneDay());

		return new WithinStatutoryTimeOfDaily(workTime,actualTime,withinpremiumTime,midNightTime);
	}
	


	/**
	 * 日別実績の法定内時間の計算
	 * アルゴリズム：就業時間（法定内用）の計算
	 * @param withinTimeSheet 就業時間内時間帯
	 * @param vacationClass 休暇クラス
	 * @param workType 勤務種類
	 * @param autoCalcOfLeaveEarlySetting 遅刻早退の自動計算設定
	 * @param addSetting 加算設定
	 * @param holidayAddtionSet 休暇加算時間設定
	 * @param holidayCalcMethodSet 休暇の計算方法の設定
	 * @param calcMethod フレックス勤務の非勤務日の場合の計算方法
	 * @param flexCalcMethod フレックス勤務の設定
	 * @param workTimeDailyAtr 就業時間帯からとってきた勤務区分
	 * @param workTimeCode 就業時間帯コード
	 * @param preFlexTime 事前フレ
	 * @param coreTimeSetting コアタイム時間帯設定
	 * @param predetermineTimeSetForCalc 計算用所定時間設定
	 * @param timeVacationAdditionRemainingTime 休暇使用合計残時間未割当
	 * @param dailyUnit 法定労働時間
	 * @param commonSetting 就業時間帯の共通設定
	 * @param conditionItem 労働条件項目
	 * @param predetermineTimeSetByPersonInfo 計算用所定時間設定
	 * @param deductLeaveEarly 遅刻早退を控除する
	 * @param lateEarlyMinusAtr 遅刻早退を控除する
	 * @return 就業時間（法定内用）
	 */
	public static AttendanceTime calcWithinStatutoryTime(
			WithinWorkTimeSheet withinTimeSheet,
			VacationClass vacationClass,
			WorkType workType,
			AutoCalcOfLeaveEarlySetting autoCalcOfLeaveEarlySetting,
			AddSetting addSetting,
			HolidayAddtionSet holidayAddtionSet,
			HolidayCalcMethodSet holidayCalcMethodSet,
			CalcMethodOfNoWorkingDayForCalc calcMethod, 
			Optional<SettingOfFlexWork> flexCalcMethod,
			Optional<WorkTimeDailyAtr> workTimeDailyAtr,//就業時間帯からとってきた勤務区分
			Optional<WorkTimeCode> workTimeCode,
			AttendanceTime preFlexTime,
			Optional<CoreTimeSetting> coreTimeSetting,
			PredetermineTimeSetForCalc predetermineTimeSetForCalc,
			Finally<AttendanceTime> timeVacationAdditionRemainingTime,
			DailyUnit dailyUnit,
			Optional<WorkTimezoneCommonSet> commonSetting,
			WorkingConditionItem conditionItem,
			Optional<PredetermineTimeSetForCalc> predetermineTimeSetByPersonInfo,
			Optional<DeductLeaveEarly> deductLeaveEarly,
			NotUseAtr lateEarlyMinusAtr) {
		
		if(conditionItem.getLaborSystem().isFlexTimeWork()
			&& (!workTimeDailyAtr.isPresent() || workTimeDailyAtr.get().isFlex())) {
			FlexWithinWorkTimeSheet changedFlexTimeSheet = (FlexWithinWorkTimeSheet)withinTimeSheet;
			Optional<WorkTimezoneCommonSet> leaveLatesetForWorkTime = commonSetting.isPresent() && commonSetting.get().getLateEarlySet().getCommonSet().isDelFromEmTime() && coreTimeSetting.isPresent() && coreTimeSetting.get().isUseTimeSheet()
																	?Optional.of(commonSetting.get().reverceTimeZoneLateEarlySet())
																	:commonSetting;
			
			//就業時間を計算
			return changedFlexTimeSheet.calcWorkTime(
					PremiumAtr.RegularWork,
					addSetting.getCalculationByActualTimeAtr(PremiumAtr.RegularWork),
					vacationClass,
					timeVacationAdditionRemainingTime.get(),
					StatutoryDivision.Nomal,
					workType,
					predetermineTimeSetForCalc,
					workTimeCode,
					autoCalcOfLeaveEarlySetting,
					addSetting,
					holidayAddtionSet,
					holidayCalcMethodSet,
					calcMethod,
					AutoCalAtrOvertime.CALCULATEMBOSS,
					flexCalcMethod.get(),
					preFlexTime,
					coreTimeSetting,
					dailyUnit,
					leaveLatesetForWorkTime,
					TimeLimitUpperLimitSetting.NOUPPERLIMIT,
					conditionItem,
					predetermineTimeSetByPersonInfo,
					lateEarlyMinusAtr);
		} else {
			Optional<WorkTimezoneCommonSet> leaveLatesetForWorkTime = commonSetting.isPresent() && commonSetting.get().getLateEarlySet().getCommonSet().isDelFromEmTime()
																	 ?Optional.of(commonSetting.get().reverceTimeZoneLateEarlySet())
																	 :commonSetting;
			//就業時間を計算
			return withinTimeSheet.calcWorkTime(
					PremiumAtr.RegularWork,
					vacationClass,
					timeVacationAdditionRemainingTime.get(),
					workType,
					predetermineTimeSetForCalc,
					workTimeCode,
					autoCalcOfLeaveEarlySetting,
					addSetting,
					holidayAddtionSet,
					holidayCalcMethodSet,
					dailyUnit,
					leaveLatesetForWorkTime,
					conditionItem,
					predetermineTimeSetByPersonInfo,coreTimeSetting,
					HolidayAdditionAtr.HolidayAddition.convertFromCalcByActualTimeToHolidayAdditionAtr(addSetting.getCalculationByActualTimeAtr(PremiumAtr.RegularWork)),
					lateEarlyMinusAtr).getWorkTime();
		}
	}
	
	/**
	 * 受け取った引数で日別実績の法定内時間を作成する
	 * @author ken_takasu
	 * @param workTime
	 * @param workTimeIncludeVacationTime
	 * @param withinPrescribedPremiumTime
	 * @param withinStatutoryMidNightTime
	 * @param vacationAddTime
	 * @return
	 */
	public static WithinStatutoryTimeOfDaily createWithinStatutoryTimeOfDaily(AttendanceTime workTime,
																	   AttendanceTime actualWorkTime,
																	   AttendanceTime withinPrescribedPremiumTime,
																	   WithinStatutoryMidNightTime withinStatutoryMidNightTime,
																	   AttendanceTime vacationAddTime) {
		WithinStatutoryTimeOfDaily withinStatutoryTimeOfDaily = new WithinStatutoryTimeOfDaily(workTime,actualWorkTime,withinPrescribedPremiumTime,withinStatutoryMidNightTime);
		withinStatutoryTimeOfDaily.actualWorkTime = actualWorkTime;
		withinStatutoryTimeOfDaily.withinPrescribedPremiumTime = withinPrescribedPremiumTime;
		withinStatutoryTimeOfDaily.withinStatutoryMidNightTime = withinStatutoryMidNightTime;
		withinStatutoryTimeOfDaily.vacationAddTime = vacationAddTime;
		return withinStatutoryTimeOfDaily;
	}
	
	public List<EmployeeDailyPerError> checkWithinMidNightExcess(String employeeId,
			                                               		 GeneralDate targetDate,
																 String searchWord,
																 AttendanceItemDictionaryForCalc attendanceItemDictionary,
																 ErrorAlarmWorkRecordCode errorCode) {
		List<EmployeeDailyPerError> returnErrorItem = new ArrayList<>();
		if(this.getWithinStatutoryMidNightTime().isOverLimitDivergenceTime()) {
			val itemId = attendanceItemDictionary.findId(searchWord);
			if(itemId.isPresent())
				returnErrorItem.add(new EmployeeDailyPerError(AppContexts.user().companyCode(), employeeId, targetDate, errorCode, itemId.get()));
					
		}
		return returnErrorItem;
	}
	
	/**
	 * 就業時間から休憩未使用時間を減算(大塚モード専用処理)
	 * @param unUseBreakTime 休憩未取得時間
	 */
	public void workTimeMinusUnUseBreakTimeForOotsuka(AttendanceTime unUseBreakTime) {
		this.workTime = this.workTime.minusMinutes(unUseBreakTime.valueAsMinutes());
		this.actualWorkTime = this.actualWorkTime.minusMinutes(unUseBreakTime.valueAsMinutes());
	}
	
	/**
	 * 乖離時間のみ再計算
	 * @return
	 */
	public WithinStatutoryTimeOfDaily calcDiverGenceTime() {
		return new WithinStatutoryTimeOfDaily(this.workTime,
											  this.actualWorkTime,
											  this.withinPrescribedPremiumTime,
											  this.withinStatutoryMidNightTime!=null?this.withinStatutoryMidNightTime.calcDiverGenceTime():this.withinStatutoryMidNightTime);
	}
	
	/**
	 * 深夜時間の上限時間調整処理
	 * @param upperTime 上限時間
	 */
	public void controlMidTimeUpper(AttendanceTime upperTime) {
		this.withinStatutoryMidNightTime.controlUpperTime(upperTime);
	}
	
	
	/**
	 * 実働就業時間の計算
	 * @param dailyUnit 
	 * @param leaveEarlyTime 
	 * @param lateTime 
	 * @param withinpremiumTime 
	 */
	public static AttendanceTime calcActualWorkTime(WithinWorkTimeSheet withinTimeSheet,
			   												   VacationClass vacationClass,
			   												   WorkType workType,
			   												   AutoCalcOfLeaveEarlySetting autoCalcOfLeaveEarlySetting,
			   												   AddSetting addSetting,
			   												   HolidayAddtionSet holidayAddtionSet,
			   												   HolidayCalcMethodSet holidayCalcMethodSet,
			   												   CalcMethodOfNoWorkingDayForCalc calcMethod, 
			   												   Optional<SettingOfFlexWork> flexCalcMethod,
			   												   Optional<WorkTimeDailyAtr> workTimeDailyAtr,//就業時間帯からとってきた勤務区分
			   												   Optional<WorkTimeCode> workTimeCode,
			   												   AttendanceTime preFlexTime,Optional<CoreTimeSetting> coreTimeSetting,
			   												   PredetermineTimeSetForCalc predetermineTimeSetForCalc,
			   												   Finally<AttendanceTime> timeVacationAdditionRemainingTime, DailyUnit dailyUnit,
			   												   Optional<WorkTimezoneCommonSet> commonSetting,
			   												   WorkingConditionItem conditionItem,
			   												Optional<PredetermineTimeSetForCalc> predetermineTimeSetByPersonInfo,
			   												Optional<DeductLeaveEarly> deductLeaveEarly,
			   												NotUseAtr lateEarlyMinusAtr
			   												   ) {
		if(conditionItem.getLaborSystem().isFlexTimeWork() 
//		if(true
			&& (!workTimeDailyAtr.isPresent() || workTimeDailyAtr.get().isFlex())) {
			FlexWithinWorkTimeSheet changedFlexTimeSheet = (FlexWithinWorkTimeSheet)withinTimeSheet;
			DeductLeaveEarly leaveLateset = new DeductLeaveEarly(1,1);
			if(deductLeaveEarly.isPresent()) {
				leaveLateset = deductLeaveEarly.get();
			}
			else {
				if(addSetting.getVacationCalcMethodSet().getWorkTimeCalcMethodOfHoliday().getAdvancedSet().isPresent()) {
					leaveLateset = addSetting.getVacationCalcMethodSet().getWorkTimeCalcMethodOfHoliday().getAdvancedSet().get().getNotDeductLateLeaveEarly();
				}
			}
			Optional<WorkTimezoneCommonSet> leaveLatesetForWorkTime = commonSetting.isPresent() && commonSetting.get().getLateEarlySet().getCommonSet().isDelFromEmTime() && coreTimeSetting.isPresent() && coreTimeSetting.get().isUseTimeSheet()
																	  ?Optional.of(commonSetting.get().reverceTimeZoneLateEarlySet())
																	  :commonSetting;
			
			return changedFlexTimeSheet.calcActualWorkTime(PremiumAtr.RegularWork,
															 addSetting.getCalculationByActualTimeAtr(PremiumAtr.RegularWork),
						  									 vacationClass,
						  									 timeVacationAdditionRemainingTime.get(),
						  									 StatutoryDivision.Nomal,workType,
						  									 predetermineTimeSetForCalc,
						  									 workTimeCode,
						  									 autoCalcOfLeaveEarlySetting,
						  									 addSetting,
						  									 holidayAddtionSet,
						  									 holidayCalcMethodSet,
						  									 calcMethod,
						  									 AutoCalAtrOvertime.CALCULATEMBOSS,
						  									 flexCalcMethod.get(),
						  									 preFlexTime,
						  									coreTimeSetting,
						  									dailyUnit,
						  									leaveLatesetForWorkTime,
						  									TimeLimitUpperLimitSetting.NOUPPERLIMIT,
						  									conditionItem,
						  									predetermineTimeSetByPersonInfo,
						  									leaveLateset,
						  									lateEarlyMinusAtr
					   );
		}
		else {
			DeductLeaveEarly leaveLateset = new DeductLeaveEarly(1,1);
			if(deductLeaveEarly.isPresent()) {
				leaveLateset = deductLeaveEarly.get();
			}
			else {
				if(addSetting.getVacationCalcMethodSet().getWorkTimeCalcMethodOfHoliday().getAdvancedSet().isPresent()) {//ichioka見直し　ここはフレだった
					leaveLateset = addSetting.getVacationCalcMethodSet().getWorkTimeCalcMethodOfHoliday().getAdvancedSet().get().getNotDeductLateLeaveEarly();//ichioka　ここは通常。あってるか？
				}
			}
//			Optional<WorkTimezoneCommonSet> leaveLatesetForWorkTime = commonSetting.isPresent() && commonSetting.get().getLateEarlySet().getCommonSet().isDelFromEmTime()
//																	?Optional.of(commonSetting.get().reverceTimeZoneLateEarlySet())
//																	:commonSetting;
			Optional<WorkTimezoneCommonSet> leaveLatesetForWorkTime = commonSetting;
			return withinTimeSheet.calcWorkTime(PremiumAtr.RegularWork,
						  														  vacationClass,
						  														  timeVacationAdditionRemainingTime.get(),
						  														  workType,
						  														  predetermineTimeSetForCalc,
						  														  workTimeCode,
						  														  autoCalcOfLeaveEarlySetting,
						  														  addSetting.createCalculationByActualTime(),
						  														  holidayAddtionSet,
						  														  holidayCalcMethodSet,
						  														  dailyUnit,
						  														  leaveLatesetForWorkTime,
						  														  conditionItem,
						  														  predetermineTimeSetByPersonInfo,coreTimeSetting,
						  														  //HolidayAdditionAtr.HolidayAddition.convertFromCalcByActualTimeToHolidayAdditionAtr(regularAddSetting.getVacationCalcMethodSet().getWorkTimeCalcMethodOfHoliday().getCalculateActualOperation()),
						  														  //休暇加算するか(就業時間計算時)はここを見るようにしている
						  														  HolidayAdditionAtr.HolidayNotAddition,
						  														  lateEarlyMinusAtr
						  														  
												).getWorkTime();
		}
	}
	
	public static WithinStatutoryTimeOfDaily defaultValue(){
		return new WithinStatutoryTimeOfDaily(AttendanceTime.ZERO, AttendanceTime.ZERO, AttendanceTime.ZERO, 
				new WithinStatutoryMidNightTime(TimeDivergenceWithCalculation.defaultValue()));
	}
	
	/**
	 * 所定内休憩未取得時間を計算
	 * @param unUseBreakTime 休憩未取得時間
	 * @param withinBreakTime 就業時間帯に設定されている所定内の休憩時間
	 * @param attendanceTime 
	 * @return 休憩未取得時間
	 */
	public AttendanceTime calcUnUseWithinBreakTime(AttendanceTime unUseBreakTime, AttendanceTime predTime, AttendanceTime withinBreakTime) {
		//所定内時間
		AttendanceTime withinPredTime = predTime.addMinutes(withinBreakTime.valueAsMinutes()).minusMinutes(this.getActualWorkTime().valueAsMinutes());
		if(withinPredTime.greaterThan(unUseBreakTime.valueAsMinutes())) {
			return unUseBreakTime;
		}
		else {
			return withinPredTime;
		}
	}
}