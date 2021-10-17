package nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import lombok.Getter;
import lombok.Setter;
import lombok.val;
import nts.arc.time.GeneralDate;
import nts.uk.ctx.at.shared.dom.PremiumAtr;
import nts.uk.ctx.at.shared.dom.common.amount.AttendanceAmountDaily;
import nts.uk.ctx.at.shared.dom.common.time.AttendanceTime;
import nts.uk.ctx.at.shared.dom.scherec.addsettingofworktime.AddSetting;
import nts.uk.ctx.at.shared.dom.scherec.addsettingofworktime.HolidayAddtionSet;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.autocalsetting.AutoCalAtrOvertime;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.autocalsetting.AutoCalcOfLeaveEarlySetting;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.autocalsetting.TimeLimitUpperLimitSetting;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.common.TimeDivergenceWithCalculation;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.dailyattendancework.IntegrationOfDaily;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.erroralarm.EmployeeDailyPerError;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.erroralarm.ErrorAlarmWorkRecordCode;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.workingstyle.flex.SettingOfFlexWork;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailycalprocess.calculation.AttendanceItemDictionaryForCalc;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailycalprocess.calculation.FlexWithinWorkTimeSheet;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailycalprocess.calculation.ManagePerPersonDailySet;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailycalprocess.calculation.ManageReGetClass;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailycalprocess.calculation.PredetermineTimeSetForCalc;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailycalprocess.calculation.timezone.withinworkinghours.WithinWorkTimeSheet;
import nts.uk.ctx.at.shared.dom.scherec.statutory.worktime.week.DailyUnit;
import nts.uk.ctx.at.shared.dom.workingcondition.WorkingConditionItem;
import nts.uk.ctx.at.shared.dom.worktime.IntegrationOfWorkTime;
import nts.uk.ctx.at.shared.dom.worktime.common.WorkTimezoneCommonSet;
import nts.uk.ctx.at.shared.dom.worktime.flexset.CoreTimeSetting;
import nts.uk.ctx.at.shared.dom.worktime.worktimeset.WorkTimeDailyAtr;
import nts.uk.ctx.at.shared.dom.worktime.worktimeset.WorkTimeSetting;
import nts.uk.ctx.at.shared.dom.worktype.WorkType;
import nts.uk.shr.com.context.AppContexts;
import nts.uk.shr.com.enumcommon.NotUseAtr;

/**
 * 日別実績の所定内時間
 * @author keisuke_hoshina
 */
@Getter
public class WithinStatutoryTimeOfDaily {
	/** 就業時間 */
	@Setter
	private AttendanceTime workTime;
	/** 実働就業時間 */
	@Setter
	private AttendanceTime actualWorkTime = new AttendanceTime(0);
	/** 所定内割増時間 */
	private AttendanceTime withinPrescribedPremiumTime = new AttendanceTime(0);
	/** 実働所定内割増時間 */
	private AttendanceTime actualWithinPremiumTime = new AttendanceTime(0);
	/** 所定内深夜時間 */
	@Setter
	private WithinStatutoryMidNightTime withinStatutoryMidNightTime = new WithinStatutoryMidNightTime(TimeDivergenceWithCalculation.sameTime(new AttendanceTime(0)));

	/** 就業時間金額 */
	private AttendanceAmountDaily withinWorkTimeAmount = new AttendanceAmountDaily(0);

	/**
	 * Constructor
	 * @param workTime 就業時間
	 * @param actualTime 実働就業時間
	 * @param premiumTime 所定内割増時間
	 * @param midNightTime 所定内深夜時間
	 */
	public WithinStatutoryTimeOfDaily(AttendanceTime workTime,AttendanceTime actualTime, AttendanceTime premiumTime,
			WithinStatutoryMidNightTime midNightTime) {
		this.workTime = workTime;
		this.actualWorkTime = actualTime;
		this.withinPrescribedPremiumTime = premiumTime;
		this.actualWithinPremiumTime = AttendanceTime.ZERO;
		this.withinStatutoryMidNightTime = midNightTime;
	}

	/**
	 * Constructor （このクラス内でしか使用しないコンストラクタ）
	 * 実働所定内割増時間は永続化しない、月次でも使用しない。
	 * その為、このコンストラクタを使用して外部に公開するメソッドは作成しない。
	 * @param workTime 就業時間
	 * @param actualTime 実働就業時間
	 * @param premiumTime 所定内割増時間
	 * @param actualPremiumTime 実働所定内割増時間
	 * @param midNightTime 所定内深夜時間
	 */
	private WithinStatutoryTimeOfDaily(AttendanceTime workTime,AttendanceTime actualTime, AttendanceTime premiumTime,
			AttendanceTime actualPremiumTime, WithinStatutoryMidNightTime midNightTime) {
		this.workTime = workTime;
		this.actualWorkTime = actualTime;
		this.withinPrescribedPremiumTime = premiumTime;
		this.actualWithinPremiumTime = actualPremiumTime;
		this.withinStatutoryMidNightTime = midNightTime;
	}

	/**
	 * 全メンバを算出するために計算指示を出す
	 * アルゴリズム：日別実績の所定内時間
	 * @param recordReget 再取得クラス
	 * @param settingOfFlex フレックス勤務の設定
	 * @return 日別実績の所定内時間
	 */
	public static WithinStatutoryTimeOfDaily calcStatutoryTime(
			ManageReGetClass recordReget,
			Optional<SettingOfFlexWork> settingOfFlex) {

		AttendanceTime workTime = new AttendanceTime(0);
		AttendanceTime actualTime = new AttendanceTime(0);
		AttendanceTime withinpremiumTime = new AttendanceTime(0);
		AttendanceTime actualWithinPremiumTime = new AttendanceTime(0);
		WithinStatutoryMidNightTime midNightTime = new WithinStatutoryMidNightTime(TimeDivergenceWithCalculation.defaultValue());

		// 勤務種類
		if (!recordReget.getWorkType().isPresent()){
			return new WithinStatutoryTimeOfDaily(workTime, actualTime, withinpremiumTime, actualWithinPremiumTime, midNightTime);
		}
		WorkType workType = recordReget.getWorkType().get();
		// 時間外の自動計算区分
		AutoCalAtrOvertime autoCalcAtrOvertime = recordReget.getIntegrationOfDaily()
				.getCalAttr().getFlexExcessTime().getFlexOtTime().getCalAtr();
		
		//ここに計算できる状態でやってきているかチェックをする
		if(recordReget.getCalculationRangeOfOneDay().getWithinWorkingTimeSheet().isPresent() && recordReget.getCalculationRangeOfOneDay().getPredetermineTimeSetForCalc() != null) {
			//所定内割増時間の計算
			withinpremiumTime = calcWithinPremiumTime(recordReget);
			//実働所定内割増時間の計算
			actualWithinPremiumTime = calcActualWithinPremiumTime(recordReget);
		}

		//事前フレックス
		AttendanceTime preFlexTime = AttendanceTime.ZERO;
		if(recordReget.getIntegrationOfDaily().getAttendanceTimeOfDailyPerformance().isPresent()
				&& recordReget.getIntegrationOfDaily().getAttendanceTimeOfDailyPerformance().get().getActualWorkingTimeOfDaily().getTotalWorkingTime().getExcessOfStatutoryTimeOfDaily().getOverTimeWork().isPresent()
				&& recordReget.getIntegrationOfDaily().getAttendanceTimeOfDailyPerformance().get().getActualWorkingTimeOfDaily().getTotalWorkingTime().getExcessOfStatutoryTimeOfDaily().getOverTimeWork().get().getFlexTime() != null) {
			preFlexTime = recordReget.getIntegrationOfDaily().getAttendanceTimeOfDailyPerformance().get().getActualWorkingTimeOfDaily().getTotalWorkingTime().getExcessOfStatutoryTimeOfDaily().getOverTimeWork().get().getFlexTime().getBeforeApplicationTime();
		}

		//就業時間の計算
		workTime = calcWithinStatutoryTime(
				recordReget.getCalculationRangeOfOneDay().getWithinWorkingTimeSheet().get(),
				recordReget.getPersonDailySetting(),
				recordReget.getIntegrationOfDaily(),
				recordReget.getIntegrationOfWorkTime(),
				workType,
				recordReget.getIntegrationOfDaily().getCalAttr().getLeaveEarlySetting(),
				recordReget.getAddSetting(),
				recordReget.getHolidayAddtionSet().get(),
				autoCalcAtrOvertime,
				settingOfFlex,
				preFlexTime,
				recordReget.getCalculationRangeOfOneDay().getPredetermineTimeSetForCalc(),
				recordReget.getDailyUnit(),
				recordReget.getWorkTimezoneCommonSet(),
				NotUseAtr.NOT_USE);

		//実働就業時間の計算
		actualTime =  calcActualWorkTime(
				recordReget.getCalculationRangeOfOneDay().getWithinWorkingTimeSheet().get(),
				recordReget.getPersonDailySetting(),
				recordReget.getIntegrationOfDaily(),
				recordReget.getIntegrationOfWorkTime(),
				workType,
				AutoCalcOfLeaveEarlySetting.createAllTrue(),
				recordReget.getAddSetting(),
				recordReget.getHolidayAddtionSet().get(),
				settingOfFlex,
				preFlexTime,
				recordReget.getCalculationRangeOfOneDay().getPredetermineTimeSetForCalc(),
				recordReget.getDailyUnit(),
				recordReget.getWorkTimezoneCommonSet(),
				NotUseAtr.USE);

		//所定内深夜時間の計算
		midNightTime = WithinStatutoryMidNightTime.calcPredetermineMidNightTime(recordReget, settingOfFlex);

		return new WithinStatutoryTimeOfDaily(workTime, actualTime, withinpremiumTime, actualWithinPremiumTime, midNightTime);
	}

	/**
	 * 日別実績の法定内時間の計算
	 * アルゴリズム：就業時間（法定内用）の計算
	 * @param withinTimeSheet 就業時間内時間帯
	 * @param personCommonSetting 社員設定管理
	 * @param integrationOfDaily 日別実績(WORK)
	 * @param integrationOfWorkTime 統合就業時間帯
	 * @param workType 勤務種類
	 * @param autoCalcOfLeaveEarlySetting 遅刻早退の自動計算設定
	 * @param addSetting 加算設定
	 * @param holidayAddtionSet 休暇加算時間設定
	 * @param autoCalcAtrOvertime 時間外の自動計算区分
	 * @param flexCalcMethod フレックス勤務の設定
	 * @param preFlexTime 事前フレ
	 * @param predetermineTimeSetForCalc 計算用所定時間設定
	 * @param dailyUnit 法定労働時間
	 * @param commonSetting 就業時間帯の共通設定
	 * @param lateEarlyMinusAtr 強制的に遅刻早退控除する
	 * @return 就業時間（法定内用）
	 */
	public static AttendanceTime calcWithinStatutoryTime(
			WithinWorkTimeSheet withinTimeSheet,
			ManagePerPersonDailySet personCommonSetting,
			IntegrationOfDaily integrationOfDaily,
			Optional<IntegrationOfWorkTime> integrationOfWorkTime,
			WorkType workType,
			AutoCalcOfLeaveEarlySetting autoCalcOfLeaveEarlySetting,
			AddSetting addSetting,
			HolidayAddtionSet holidayAddtionSet,
			AutoCalAtrOvertime autoCalcAtrOvertime,
			Optional<SettingOfFlexWork> flexCalcMethod,
			AttendanceTime preFlexTime,
			PredetermineTimeSetForCalc predetermineTimeSetForCalc,
			DailyUnit dailyUnit,
			Optional<WorkTimezoneCommonSet> commonSetting,
			NotUseAtr lateEarlyMinusAtr) {

		// 労働条件項目
		WorkingConditionItem conditionItem = personCommonSetting.getPersonInfo();
		// 統合就業時間帯の確認
		boolean isFlexDay = false;										// フレックス勤務日かどうか
		if (integrationOfWorkTime.isPresent()){
			WorkTimeSetting workTimeSet = integrationOfWorkTime.get().getWorkTimeSetting();	// 就業時間帯の設定
			isFlexDay = workTimeSet.getWorkTimeDivision().isFlexWorkDay(conditionItem);
		}
		if (isFlexDay){
			FlexWithinWorkTimeSheet changedFlexTimeSheet = (FlexWithinWorkTimeSheet)withinTimeSheet;

			//就業時間を計算
			return changedFlexTimeSheet.calcWorkTime(
					personCommonSetting,
					integrationOfDaily,
					integrationOfWorkTime,
					PremiumAtr.RegularWork,
					workType,
					predetermineTimeSetForCalc,
					autoCalcOfLeaveEarlySetting,
					addSetting,
					holidayAddtionSet,
					autoCalcAtrOvertime,
					flexCalcMethod.get(),
					preFlexTime,
					dailyUnit,
					commonSetting,
					TimeLimitUpperLimitSetting.NOUPPERLIMIT,
					lateEarlyMinusAtr);
		} else {
			//就業時間を計算
			return withinTimeSheet.calcWorkTime(
					personCommonSetting,
					integrationOfDaily,
					integrationOfWorkTime,
					PremiumAtr.RegularWork,
					workType,
					predetermineTimeSetForCalc,
					autoCalcOfLeaveEarlySetting,
					addSetting,
					holidayAddtionSet,
					flexCalcMethod,
					dailyUnit,
					commonSetting,
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
																	   WithinStatutoryMidNightTime withinStatutoryMidNightTime) {
		WithinStatutoryTimeOfDaily withinStatutoryTimeOfDaily = new WithinStatutoryTimeOfDaily(workTime,actualWorkTime,withinPrescribedPremiumTime,withinStatutoryMidNightTime);
		withinStatutoryTimeOfDaily.actualWorkTime = actualWorkTime;
		withinStatutoryTimeOfDaily.withinPrescribedPremiumTime = withinPrescribedPremiumTime;
		withinStatutoryTimeOfDaily.withinStatutoryMidNightTime = withinStatutoryMidNightTime;
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
											  this.actualWithinPremiumTime,
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
	 * @param withinTimeSheet 就業時間内時間帯
	 * @param personCommonSetting 個人設定管理
	 * @param integrationOfDaily 日別実績(WORK)
	 * @param integrationOfWorkTime 統合就業時間帯
	 * @param workType 勤務種類
	 * @param autoCalcOfLeaveEarlySetting 遅刻早退の自動計算設定
	 * @param addSetting 加算設定
	 * @param holidayAddtionSet 休暇加算時間設定
	 * @param settingOfFlex フレックス勤務の設定
	 * @param preFlexTime 事前フレックス時間
	 * @param predetermineTimeSet 計算用所定時間設定
	 * @param dailyUnit 法定労働時間
	 * @param commonSetting 就業時間帯の共通設定
	 * @param lateEarlyMinusAtr 強制的に遅刻早退控除する
	 * @return 実働就業時間
	 */
	public static AttendanceTime calcActualWorkTime(
			WithinWorkTimeSheet withinTimeSheet,
			ManagePerPersonDailySet personCommonSetting,
			IntegrationOfDaily integrationOfDaily,
			Optional<IntegrationOfWorkTime> integrationOfWorkTime,
			WorkType workType,
			AutoCalcOfLeaveEarlySetting autoCalcOfLeaveEarlySetting,
			AddSetting addSetting,
			HolidayAddtionSet holidayAddtionSet,
			Optional<SettingOfFlexWork> settingOfFlex,
			AttendanceTime preFlexTime,
			PredetermineTimeSetForCalc predetermineTimeSet,
			DailyUnit dailyUnit,
			Optional<WorkTimezoneCommonSet> commonSetting,
			NotUseAtr lateEarlyMinusAtr) {
		
		// 労働条件項目
		WorkingConditionItem conditionItem = personCommonSetting.getPersonInfo();
		
		// 統合就業時間帯の確認
		boolean isFlexDay = false;										// フレックス勤務日かどうか
		Optional<CoreTimeSetting> coreTimeSetting = Optional.empty();	// コアタイム時間帯設定
		if (integrationOfWorkTime.isPresent()){
			WorkTimeSetting workTimeSet = integrationOfWorkTime.get().getWorkTimeSetting();	// 就業時間帯の設定
			isFlexDay = workTimeSet.getWorkTimeDivision().isFlexWorkDay(conditionItem);
			coreTimeSetting = integrationOfWorkTime.get().getCoreTimeSetting();
		}
		if (isFlexDay) {
			FlexWithinWorkTimeSheet changedFlexTimeSheet = (FlexWithinWorkTimeSheet)withinTimeSheet;
			Optional<WorkTimezoneCommonSet> leaveLatesetForWorkTime =
					commonSetting.isPresent() &&
					commonSetting.get().getLateEarlySet().getCommonSet().isDelFromEmTime() &&
					coreTimeSetting.isPresent() &&
					coreTimeSetting.get().isUseTimeSheet() ?
							Optional.of(commonSetting.get().reverceTimeZoneLateEarlySet()) : commonSetting;

			return changedFlexTimeSheet.calcActualWorkTime(
					personCommonSetting,
					integrationOfDaily,
					integrationOfWorkTime,
					workType,
					predetermineTimeSet,
					autoCalcOfLeaveEarlySetting,
					addSetting,
					holidayAddtionSet,
					AutoCalAtrOvertime.CALCULATEMBOSS,
					settingOfFlex.get(),
					preFlexTime,
					dailyUnit,
					leaveLatesetForWorkTime,
					TimeLimitUpperLimitSetting.NOUPPERLIMIT,
					lateEarlyMinusAtr);
		}
		else {
//			Optional<WorkTimezoneCommonSet> leaveLatesetForWorkTime =
//					commonSetting.isPresent() &&
//					commonSetting.get().getLateEarlySet().getCommonSet().isDelFromEmTime() ?
//							Optional.of(commonSetting.get().reverceTimeZoneLateEarlySet()) : commonSetting;
			Optional<WorkTimezoneCommonSet> leaveLatesetForWorkTime = commonSetting;
			return withinTimeSheet.calcWorkTime(
					personCommonSetting,
					integrationOfDaily,
					integrationOfWorkTime,
					PremiumAtr.RegularWork,
					workType,
					predetermineTimeSet,
					autoCalcOfLeaveEarlySetting,
					addSetting.createCalculationByActualTime(),
					holidayAddtionSet,
					settingOfFlex,
					dailyUnit,
					leaveLatesetForWorkTime,
					lateEarlyMinusAtr).getWorkTime();
		}
	}

	public static WithinStatutoryTimeOfDaily defaultValue(){
		return new WithinStatutoryTimeOfDaily(AttendanceTime.ZERO, AttendanceTime.ZERO, AttendanceTime.ZERO, AttendanceTime.ZERO,
				new WithinStatutoryMidNightTime(TimeDivergenceWithCalculation.defaultValue()));
	}

	/**
	 * 所定内休憩未取得時間を計算
	 * @param unUseBreakTime 休憩未取得時間
	 * @param predTime 所定時間
	 * @param withinBreakTime 就業時間帯に設定されている所定内の休憩時間
	 * @return 休憩未取得時間
	 */
	public AttendanceTime calcUnUseWithinBreakTime(
			AttendanceTime unUseBreakTime, AttendanceTime predTime, AttendanceTime withinBreakTime) {
		//所定内時間
		AttendanceTime withinPredTime = predTime.addMinutes(withinBreakTime.valueAsMinutes()).minusMinutes(this.getActualWorkTime().valueAsMinutes());
		if(withinPredTime.greaterThan(unUseBreakTime.valueAsMinutes())) {
			return unUseBreakTime;
		}
		else {
			return withinPredTime;
		}
	}

	/**
	 * 所定内割増時間を計算する
	 * @param recordReget 再取得クラス
	 * @return 所定内割増時間
	 */
	public static AttendanceTime calcWithinPremiumTime(ManageReGetClass recordReget) {
		
		if(!recordReget.getCalculationRangeOfOneDay().getWithinWorkingTimeSheet().isPresent()
				|| !recordReget.getHolidayAddtionSet().isPresent()) {
			return AttendanceTime.ZERO;
		}

		// 勤務種類
		if (!recordReget.getWorkType().isPresent()) return AttendanceTime.ZERO;
		WorkType workType = recordReget.getWorkType().get();
		// 統合就業時間帯の確認
		Optional<WorkTimeDailyAtr> workTimeDailyAtr = Optional.empty();	// 勤務形態区分
		if (recordReget.getIntegrationOfWorkTime().isPresent()){
			IntegrationOfWorkTime integrationOfWorkTime = recordReget.getIntegrationOfWorkTime().get();
			WorkTimeSetting workTimeSet = integrationOfWorkTime.getWorkTimeSetting();		// 就業時間帯の設定
			workTimeDailyAtr = Optional.of(workTimeSet.getWorkTimeDivision().getWorkTimeDailyAtr());
		}
		
		if(recordReget.getPersonDailySetting().getPersonInfo().getLaborSystem().isFlexTimeWork()
				&& (!workTimeDailyAtr.isPresent() || workTimeDailyAtr.get().isFlex())) {
			//フレックスは常に0
			return AttendanceTime.ZERO;
		}

		//就業時間を計算
		return recordReget.getCalculationRangeOfOneDay().getWithinWorkingTimeSheet().get().calcWorkTime(
				recordReget.getPersonDailySetting(),
				recordReget.getIntegrationOfDaily(),
				recordReget.getIntegrationOfWorkTime(),
				PremiumAtr.RegularWork,
				workType,
				recordReget.getCalculationRangeOfOneDay().getPredetermineTimeSetForCalc(),
				recordReget.getIntegrationOfDaily().getCalAttr().getLeaveEarlySetting(),
				recordReget.getAddSetting(),
				recordReget.getHolidayAddtionSet().get(),
				Optional.empty(),
				recordReget.getDailyUnit(),
				recordReget.getWorkTimezoneCommonSet(),
				NotUseAtr.NOT_USE)	// 就業時間は設定通りに処理する為、遅刻早退を常に控除するは「しない」
				.getWithinPremiumTime();
	}

	/**
	 * 実働所定内割増を計算する
	 * @param recordReget 再取得クラス
	 * @return 実働所定内割増時間
	 */
	public static AttendanceTime calcActualWithinPremiumTime(ManageReGetClass recordReget) {
		
		if(!recordReget.getCalculationRangeOfOneDay().getWithinWorkingTimeSheet().isPresent()
				|| !recordReget.getHolidayAddtionSet().isPresent()) {
			return AttendanceTime.ZERO;
		}

		// 勤務種類
		if (!recordReget.getWorkType().isPresent()) return AttendanceTime.ZERO;
		WorkType workType = recordReget.getWorkType().get();
		// 統合就業時間帯の確認
		Optional<WorkTimeDailyAtr> workTimeDailyAtr = Optional.empty();	// 勤務形態区分
		if (recordReget.getIntegrationOfWorkTime().isPresent()){
			IntegrationOfWorkTime integrationOfWorkTime = recordReget.getIntegrationOfWorkTime().get();
			WorkTimeSetting workTimeSet = integrationOfWorkTime.getWorkTimeSetting();		// 就業時間帯の設定
			workTimeDailyAtr = Optional.of(workTimeSet.getWorkTimeDivision().getWorkTimeDailyAtr());
		}
		
		if(recordReget.getPersonDailySetting().getPersonInfo().getLaborSystem().isFlexTimeWork()
				&& (!workTimeDailyAtr.isPresent() || workTimeDailyAtr.get().isFlex())) {
			//フレックスは常に0
			return AttendanceTime.ZERO;
		}

		return recordReget.getCalculationRangeOfOneDay().getWithinWorkingTimeSheet().get().calcWorkTime(
				recordReget.getPersonDailySetting(),
				recordReget.getIntegrationOfDaily(),
				recordReget.getIntegrationOfWorkTime(),
				PremiumAtr.RegularWork,
				workType,
				recordReget.getCalculationRangeOfOneDay().getPredetermineTimeSetForCalc(),
				AutoCalcOfLeaveEarlySetting.createAllTrue(),	//遅刻早退の自動計算設定を全て「する」で渡す
				recordReget.getAddSetting().createCalculationByActualTime(),	//休暇加算はすべて「しない」で渡す
				recordReget.getHolidayAddtionSet().get(),
				Optional.empty(),
				recordReget.getDailyUnit(),
				recordReget.getWorkTimezoneCommonSet(),
				NotUseAtr.USE)		//遅刻早退は常に控除する
				.getWithinPremiumTime();
	}
}