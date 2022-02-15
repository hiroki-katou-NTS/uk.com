package nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailycalprocess.calculation;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import lombok.Getter;
import nts.arc.time.GeneralDate;
import nts.uk.ctx.at.shared.dom.PremiumAtr;
import nts.uk.ctx.at.shared.dom.common.time.AttendanceTime;
import nts.uk.ctx.at.shared.dom.common.time.AttendanceTimeOfExistMinus;
import nts.uk.ctx.at.shared.dom.common.time.BreakDownTimeDay;
import nts.uk.ctx.at.shared.dom.common.time.TimeSpanForCalc;
import nts.uk.ctx.at.shared.dom.schedule.basicschedule.WorkStyle;
import nts.uk.ctx.at.shared.dom.scherec.addsettingofworktime.AddSetting;
import nts.uk.ctx.at.shared.dom.scherec.addsettingofworktime.HolidayAddtionSet;
import nts.uk.ctx.at.shared.dom.scherec.addsettingofworktime.AddSettingOfWorkingTime;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.autocalsetting.AutoCalAtrOvertime;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.autocalsetting.AutoCalcOfLeaveEarlySetting;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.autocalsetting.TimeLimitUpperLimitSetting;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.breakouting.ConditionAtr;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.common.TimeDivergenceWithCalculationMinusExist;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.common.TimeWithCalculation;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.dailyattendancework.IntegrationOfDaily;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.overtimehours.clearovertime.FlexTime;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.vacationusetime.VacationClass;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.workinfomation.WorkInfoOfDailyAttendance;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.workingstyle.flex.SettingOfFlexWork;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.worklabor.flex.CalcFlexOfNoWorkingDay;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.worklabor.flex.CalcMethodOfNoWorkingDay;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.worklabor.flex.FlexCalcMethodOfCompLeave;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.worklabor.flex.FlexCalcMethodOfHalfHoliday;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailycalprocess.calculation.timezone.deductiontime.DeductionAtr;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailycalprocess.calculation.timezone.deductiontime.DeductionClassification;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailycalprocess.calculation.timezone.deductiontime.TimeSheetOfDeductionItem;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailycalprocess.calculation.timezone.service.ActualWorkTimeSheetListService;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailycalprocess.calculation.timezone.withinworkinghours.LateDecisionClock;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailycalprocess.calculation.timezone.withinworkinghours.LeaveEarlyDecisionClock;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailycalprocess.calculation.timezone.withinworkinghours.WithinWorkTimeFrame;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailycalprocess.calculation.timezone.withinworkinghours.WithinWorkTimeSheet;
import nts.uk.ctx.at.shared.dom.scherec.statutory.worktime.week.DailyUnit;
import nts.uk.ctx.at.shared.dom.workingcondition.WorkingConditionItem;
import nts.uk.ctx.at.shared.dom.worktime.IntegrationOfWorkTime;
import nts.uk.ctx.at.shared.dom.worktime.common.WorkTimezoneCommonSet;
import nts.uk.ctx.at.shared.dom.worktime.common.WorkTimezoneLateEarlySet;
import nts.uk.ctx.at.shared.dom.worktime.flexset.CoreTimeSetting;
import nts.uk.ctx.at.shared.dom.worktime.common.WorkTimezoneGoOutSet;
import nts.uk.ctx.at.shared.dom.worktime.flexset.FlexWorkSetting;
import nts.uk.ctx.at.shared.dom.worktime.flexset.TimeSheet;
import nts.uk.ctx.at.shared.dom.worktype.VacationCategory;
import nts.uk.ctx.at.shared.dom.worktype.WorkType;
import nts.uk.ctx.at.shared.dom.worktype.WorkTypeClassification;
import nts.uk.shr.com.enumcommon.NotUseAtr;
import nts.uk.shr.com.time.TimeWithDayAttr;

/**
 * フレックス就業時間内時間帯
 * @author keisuke_hoshina
 */
@Getter
public class FlexWithinWorkTimeSheet extends WithinWorkTimeSheet{
	/*コアタイム*/
	private Optional<TimeSpanForDailyCalc> coreTimeSheet;

	public FlexWithinWorkTimeSheet(
			List<WithinWorkTimeFrame> withinWorkTimeFrame,
			List<TimeSheetOfDeductionItem> shortTimeSheets,
			List<LeaveEarlyDecisionClock> leaveEarlyDecisionClock,
			List<LateDecisionClock> lateDecisionClock,
			Optional<TimeSpanForDailyCalc> coreTimeSheet) {
		super(withinWorkTimeFrame, shortTimeSheets, leaveEarlyDecisionClock, lateDecisionClock);
		this.coreTimeSheet = coreTimeSheet;
	}
	
	public static FlexWithinWorkTimeSheet createNew(
			List<WithinWorkTimeFrame> withinWorkTimeFrame,
			List<TimeSheetOfDeductionItem> shortTimeSheets,
			List<LeaveEarlyDecisionClock> leaveEarlyDecisionClock,
			List<LateDecisionClock> lateDecisionClock,
			Optional<TimeSpanForDailyCalc> coreTimeSheet,
			TimeVacationWork timeVacationUseTime,
			Optional<AttendanceTime> limitActualWorkTime) {
		
		FlexWithinWorkTimeSheet myclass = new FlexWithinWorkTimeSheet(
				withinWorkTimeFrame, shortTimeSheets, leaveEarlyDecisionClock, lateDecisionClock, coreTimeSheet);
		myclass.timeVacationUseTime = timeVacationUseTime;
		myclass.limitActualWorkTime = limitActualWorkTime;
		return myclass;
	}
	
	public static interface Require extends VacationClass.Require {}
	
	/**
	 * フレックス時間を計算する
	 * アルゴリズム：フレックス時間の計算
	 * @param personCommonSetting 個人設定管理
	 * @param integrationOfDaily 日別実績(WORK)
	 * @param integrationOfWorkTime 統合就業時間帯
	 * @param autoCalcAtr 時間外の自動計算区分
	 * @param workType 勤務種類
	 * @param settingOfFlex フレックス勤務の設定
	 * @param predetermineTimeSet 計算用所定時間設定
	 * @param autoCalcOfLeaveEarlySetting 遅刻早退の自動計算設定
	 * @param addSetting 加算設定
	 * @param holidayAddtionSet 休暇加算時間設定
	 * @param flexLimitSetting 時間外の上限設定
	 * @param preAppTime 事前フレ
	 * @param dailyUnit 法定労働時間
	 * @param commonSetting 就業時間帯の共通設定
	 * @param lateEarlyMinusAtr 強制的に遅刻早退控除する
	 * @param deductionAtr 控除区分
	 * @return フレックス時間
	 */
	public FlexTime createWithinWorkTimeSheetAsFlex(
			ManagePerPersonDailySet personCommonSetting,
			IntegrationOfDaily integrationOfDaily,
			Optional<IntegrationOfWorkTime> integrationOfWorkTime,
			AutoCalAtrOvertime autoCalcAtr,
			WorkType workType,
			SettingOfFlexWork settingOfFlex,
			PredetermineTimeSetForCalc predetermineTimeSet,
			AutoCalcOfLeaveEarlySetting autoCalcOfLeaveEarlySetting,
			AddSetting addSetting,
			HolidayAddtionSet holidayAddtionSet,
			TimeLimitUpperLimitSetting flexLimitSetting,
			AttendanceTime preAppTime,
			DailyUnit dailyUnit,
			Optional<WorkTimezoneCommonSet> commonSetting,
			NotUseAtr lateEarlyMinusAtr,
			Optional<DeductionAtr> deductionAtr) {
		
		//フレックス時間の基準となる所定労働時間
		FlexTime flexTime = new FlexTime(TimeDivergenceWithCalculationMinusExist.sameTime(new AttendanceTimeOfExistMinus(0)),new AttendanceTime(0));
		
		//1日休日 or 1日休出  or 1日振休
		if (workType.isHoliday() || workType.isHolidayWork() || workType.isPause()) return flexTime;

		//フレックス計算しない and 非勤務日（欠勤は、勤務日として計算する）
		CalcFlexOfNoWorkingDay noWorkingDaySet = settingOfFlex.getFlexSet().getCalcNoWorkingDay();
		if (noWorkingDaySet.getSetting() == CalcMethodOfNoWorkingDay.IS_NOT_CALC_FLEX_TIME &&
			(workType.checkWorkDay() == WorkStyle.ONE_DAY_REST && !workType.getDailyWork().isAbsence())) return flexTime;
		
		/*フレックス時間の計算*/
		CalcFlexTime calcflexTime = this.calcFlexTime(
				personCommonSetting,
				integrationOfDaily,
				integrationOfWorkTime,
				autoCalcAtr,
				workType,
				settingOfFlex,
				predetermineTimeSet,
				autoCalcOfLeaveEarlySetting,
				addSetting,
				holidayAddtionSet,
				dailyUnit,
				commonSetting,
				flexLimitSetting,
				lateEarlyMinusAtr);
		
		AttendanceTimeOfExistMinus afterLimitFlexTime = calcflexTime.getFlexTime().getTime();
		boolean isDecisionLimit = false;
		if (deductionAtr.isPresent()){
			if (deductionAtr.get() == DeductionAtr.Appropriate) isDecisionLimit = true;
		}
		else{
			isDecisionLimit = true;
		}
		if (isDecisionLimit){
			// 事前申請を上限とする制御
			afterLimitFlexTime = decisionLimit(flexLimitSetting, calcflexTime.getFlexTime().getTime(), preAppTime);
		}
		
		return new FlexTime(
				TimeDivergenceWithCalculationMinusExist.createTimeWithCalculation(
						afterLimitFlexTime, calcflexTime.getFlexTime().getCalcTime()),
				new AttendanceTime(0));
	}
	
	/**
	 * 事前申請上限制所処理
	 * @param flexLimitSetting フレックス超過時間の自動計算設定.自動計算設定.時間外の上限設定
	 * @param flexTime 
	 * @param preAppTime 
	 * @return
	 */
	private AttendanceTimeOfExistMinus decisionLimit(TimeLimitUpperLimitSetting flexLimitSetting, AttendanceTimeOfExistMinus flexTime, AttendanceTime preAppTime) {
		switch(flexLimitSetting) {
			//事前申請を上限にする
			case LIMITNUMBERAPPLICATION:
				//指示時間を上限にする ←はEAで修正されたら適切な処理入れる
			case INDICATEDYIMEUPPERLIMIT:	
				//上限制御をやりつつ、値を返す
				return (preAppTime.greaterThan(flexTime.v()))
						?flexTime
						:new AttendanceTimeOfExistMinus(preAppTime.v());
			
			//上限なし	
			case NOUPPERLIMIT:
				return flexTime;
		default:
			throw new RuntimeException("unknown flex Limit setting:" + flexLimitSetting);
		}
	}

	/**
	 * フレックス時間の計算
	 * @param personCommonSetting 個人設定管理
	 * @param integrationOfDaily 日別実績(WORK)
	 * @param integrationOfWorkTime 統合就業時間帯
	 * @param autoCalcAtr 時間外の自動計算区分
	 * @param workType 勤務種類
	 * @param settingOfFlex フレックス勤務の設定
	 * @param predetermineTimeSet 所定時間設定（会社用）
	 * @param vacationClass 休暇クラス
	 * @param autoCalcOfLeaveEarlySetting 遅刻早退の自動計算設定
	 * @param addSetting 加算設定
	 * @param holidayAddtionSet 休暇加算時間設定
	 * @param dailyUnit 法定労働時間
	 * @param commonSetting 就業時間帯の共通設定
	 * @param flexUpper 時間外の上限設定
	 * @param conditionItem 労働条件項目
	 * @param predetermineTimeSetByPersonInfo 所定時間設定（個人平日時）
	 * @param lateEarlyMinusAtr 強制的に遅刻早退控除する
	 * @return フレックス時間
	 */
	public CalcFlexTime calcFlexTime(
			ManagePerPersonDailySet personCommonSetting,
			IntegrationOfDaily integrationOfDaily,
			Optional<IntegrationOfWorkTime> integrationOfWorkTime,
			AutoCalAtrOvertime autoCalcAtr,
			WorkType workType,
			SettingOfFlexWork settingOfFlex,
			PredetermineTimeSetForCalc predetermineTimeSet,
			AutoCalcOfLeaveEarlySetting autoCalcOfLeaveEarlySetting,
			AddSetting addSetting,
			HolidayAddtionSet holidayAddtionSet,
			DailyUnit dailyUnit,
			Optional<WorkTimezoneCommonSet> commonSetting,
			TimeLimitUpperLimitSetting flexUpper,//こいつは残さないとだめ,
			NotUseAtr lateEarlyMinusAtr) {

		/*フレックス時間の基準となる所定労働時間の取得*/
		StatutoryWorkingTime houtei = this.calcStatutoryTime(
				personCommonSetting.getRequire(),
				integrationOfDaily.getYmd(),
				integrationOfDaily.getWorkInformation(),
				workType,
				settingOfFlex,
				Optional.of(predetermineTimeSet),
				personCommonSetting.getPersonInfo(),
				Optional.of(holidayAddtionSet),
				addSetting);
				
		/*就業時間(法定内用)の計算*/
		WorkHour zitudou = super.calcWorkTime(
				personCommonSetting,
				integrationOfDaily,
				integrationOfWorkTime,
				PremiumAtr.RegularWork,
				workType,
				predetermineTimeSet,
				autoCalcOfLeaveEarlySetting,
				addSetting,
				holidayAddtionSet,
				Optional.of(settingOfFlex),
				dailyUnit,
				commonSetting,
				lateEarlyMinusAtr);
		
		/*就業時間(割増含む)の計算*/
		WorkHour zitudouIncludePremium = super.calcWorkTime(
				personCommonSetting,
				integrationOfDaily,
				integrationOfWorkTime,
				PremiumAtr.Premium,
				workType,
				predetermineTimeSet,
				autoCalcOfLeaveEarlySetting,
				addSetting,
				holidayAddtionSet,
				Optional.of(settingOfFlex),
				dailyUnit,
				commonSetting,
				lateEarlyMinusAtr);
		
		AttendanceTimeOfExistMinus flexTime = new AttendanceTimeOfExistMinus(0);
		AttendanceTime vacationAddTime = new AttendanceTime(0);
		
		//就業時間（割増含む）からフレックス時間を計算
		flexTime = new AttendanceTimeOfExistMinus(zitudouIncludePremium.getWorkTime().valueAsMinutes()).minusMinutes(houtei.getForWorkTimeIncludePremium().v());
		
		if(flexTime.lessThan(0)) {
			//就業時間(法定内用)からフレックス時間を計算
			AttendanceTimeOfExistMinus flexTimeIncludePremium = new AttendanceTimeOfExistMinus(zitudou.getWorkTime().valueAsMinutes()).minusMinutes(houtei.getForActualWorkTime().v());
			
			//計算したフレックス時間を0：00を上限とする。
			flexTimeIncludePremium = (flexTimeIncludePremium.greaterThan(0))
					?new AttendanceTimeOfExistMinus(0)
					:flexTimeIncludePremium;
			
			//フレックス不足時の加算時間を計算
			int diffValue = flexTimeIncludePremium.valueAsMinutes()-flexTime.valueAsMinutes();
			flexTime = flexTimeIncludePremium;
			vacationAddTime = new AttendanceTime(diffValue);
		}
		// フレックス時間を作成
		CalcFlexTime result = new CalcFlexTime(
				TimeDivergenceWithCalculationMinusExist.createTimeWithCalculation(flexTime, flexTime), vacationAddTime);
		// 計算区分の取得
		if((!autoCalcAtr.isCalculateEmbossing()) && flexTime.greaterThan(0)) {
			// フレックス時間．時間　←　0
			result.getFlexTime().replaceTimeAndCalcDiv(new AttendanceTimeOfExistMinus(0));
		}
		// フレックス時間を返す
		return result;
	}
	
	/**
	 * フレックス計算用の法定労働時間を取得
	 * @param require Require
	 * @param baseDate 基準日
	 * @param workInfo 日別勤怠の勤務情報
	 * @param workType 勤務種類
	 * @param settingOfFlex フレックス勤務の設定
	 * @param predetermineTimeSet 計算用所定時間設定
	 * @param conditionItem 労働条件項目
	 * @param holidayAddtionSet 休暇加算時間設定
	 * @param addSetting 加算設定
	 * @return 所定労働時間
	 */
	private StatutoryWorkingTime calcStatutoryTime(
			Require require,
			GeneralDate baseDate,
			WorkInfoOfDailyAttendance workInfo,
			WorkType workType,
			SettingOfFlexWork settingOfFlex,
			Optional<PredetermineTimeSetForCalc> predetermineTimeSet,
			WorkingConditionItem conditionItem,
			Optional<HolidayAddtionSet> holidayAddtionSet,
			AddSetting addSetting) {
		
		// 所定時間を取得
		AttendanceTime predetermineTime = predetermineTimeSet.isPresent() ?
				predetermineTimeSet.get().getAdditionSet().getPredTime().getOneDay() : new AttendanceTime(0);
		// 所定労働時間から控除する時間を計算
		StatutoryDeductionForFlex deductionTime = this.calcDeductTime(require,
				baseDate, workInfo, workType, settingOfFlex, predetermineTimeSet, conditionItem, addSetting);
		// 所定時間を計算する
		int actualMinutes = predetermineTime.valueAsMinutes() - deductionTime.getForActualWork().valueAsMinutes();
		int premiumMinutes = predetermineTime.valueAsMinutes() - deductionTime.getForPremium().valueAsMinutes();
		// 所定労働時間を返す
		return new StatutoryWorkingTime(new AttendanceTime(actualMinutes), new AttendanceTime(premiumMinutes));
	}
	
	/**
	 * 控除する時間を計算
	 * @param require Require
	 * @param baseDate 基準日
	 * @param workInfo 日別勤怠の勤務情報
	 * @param workType 勤務種類
	 * @param settingOfFlex フレックス勤務の設定
	 * @param predetermineTimeSet 計算用所定時間設定
	 * @param conditionItem 労働条件項目
	 * @param addSetting 加算設定
	 * @return 所定労働控除時間
	 */
	private StatutoryDeductionForFlex calcDeductTime(
			Require require,
			GeneralDate baseDate,
			WorkInfoOfDailyAttendance workInfo,
			WorkType workType,
			SettingOfFlexWork settingOfFlex,
			Optional<PredetermineTimeSetForCalc> predetermineTimeSet,
			WorkingConditionItem conditionItem,
			AddSetting addSetting) {
		
		StatutoryDeductionForFlex result = new StatutoryDeductionForFlex();
		
		// 休日控除時間の計算
		result.add(this.calcDeductHoliday(require, WorkTypeClassification.Holiday,
				baseDate, workInfo, workType, settingOfFlex, predetermineTimeSet, conditionItem));
		// 休日控除時間の計算（振休）
		result.add(this.calcDeductHoliday(require, WorkTypeClassification.Pause,
				baseDate, workInfo, workType, settingOfFlex, predetermineTimeSet, conditionItem));
		// 代休控除時間の計算
		result.add(this.calcDeductCompLeave(require,
				baseDate, workInfo, workType, settingOfFlex, predetermineTimeSet, conditionItem));
		// 欠勤控除時間の計算
		result.add(this.calcDeductAbsence(require,
				baseDate, workInfo, workType, predetermineTimeSet, conditionItem, addSetting));
		// 所定労働控除時間を返す
		return result;
	}

	/**
	 * 休日控除時間の計算
	 * @param require Require
	 * @param workTypeClass 勤務種類の分類
	 * @param baseDate 基準日
	 * @param workInfo 日別勤怠の勤務情報
	 * @param workType 勤務種類
	 * @param settingOfFlex フレックス勤務の設定
	 * @param predetermineTimeSet 計算用所定時間設定
	 * @param conditionItem 労働条件項目
	 * @return 休日控除時間
	 */
	private DeductTimeEachSet calcDeductHoliday(
			Require require,
			WorkTypeClassification workTypeClass,
			GeneralDate baseDate,
			WorkInfoOfDailyAttendance workInfo,
			WorkType workType,
			SettingOfFlexWork settingOfFlex,
			Optional<PredetermineTimeSetForCalc> predetermineTimeSet,
			WorkingConditionItem conditionItem) {
		
		// 所定時間の内訳を取得
		BreakDownTimeDay breakDown = VacationClass.getBreakDownOfPredTime(
				require,
				conditionItem.getEmployeeId(),
				baseDate,
				workInfo,
				workTypeClass.convertVacationCategory(),
				predetermineTimeSet,
				Optional.empty());
		// フレックス勤務時の半日休日の計算方法
		FlexCalcMethodOfHalfHoliday calcMethod = settingOfFlex.getFlexSet().getHalfHoliday();
		// 所定控除時間の計算
		AttendanceTime forActualWork = calcMethod.calcDeductPredeterminedTime(workType, workTypeClass, false, breakDown);
		AttendanceTime forPremium = calcMethod.calcDeductPredeterminedTime(workType, workTypeClass, true, breakDown);
		// 設定別控除時間を返す
		return new DeductTimeEachSet(forActualWork, forPremium);
	}
	
	/**
	 * 代休控除時間の計算
	 * @param require Require
	 * @param baseDate 基準日
	 * @param workInfo 日別勤怠の勤務情報
	 * @param workType 勤務種類
	 * @param settingOfFlex フレックス勤務の設定
	 * @param predetermineTimeSet 計算用所定時間設定
	 * @param conditionItem 労働条件項目
	 * @return 代休控除時間
	 */
	private DeductTimeEachSet calcDeductCompLeave(
			Require require,
			GeneralDate baseDate,
			WorkInfoOfDailyAttendance workInfo,
			WorkType workType,
			SettingOfFlexWork settingOfFlex,
			Optional<PredetermineTimeSetForCalc> predetermineTimeSet,
			WorkingConditionItem conditionItem) {

		// 日単位の代休控除時間の計算
		DeductTimeEachSet deductTime = this.calcDeductCompLeaveOfDay(
				require, baseDate, workInfo, workType, settingOfFlex, predetermineTimeSet, conditionItem);
		// 時間単位の代休控除時間の計算
		deductTime.add(this.calcDeductCompLeaveOfTime(settingOfFlex));
		// 設定別控除時間を返す
		return deductTime;
	}
	
	/**
	 * 日単位の代休控除時間の計算
	 * @param require Require
	 * @param baseDate 基準日
	 * @param workInfo 日別勤怠の勤務情報
	 * @param workType 勤務種類
	 * @param settingOfFlex フレックス勤務の設定
	 * @param predetermineTimeSet 計算用所定時間設定
	 * @param conditionItem 労働条件項目
	 * @return 代休控除時間
	 */
	private DeductTimeEachSet calcDeductCompLeaveOfDay(
			Require require,
			GeneralDate baseDate,
			WorkInfoOfDailyAttendance workInfo,
			WorkType workType,
			SettingOfFlexWork settingOfFlex,
			Optional<PredetermineTimeSetForCalc> predetermineTimeSet,
			WorkingConditionItem conditionItem) {
		
		// 所定時間の内訳を取得
		BreakDownTimeDay breakDown = VacationClass.getBreakDownOfPredTime(
				require,
				conditionItem.getEmployeeId(),
				baseDate,
				workInfo,
				VacationCategory.SubstituteHoliday,
				predetermineTimeSet,
				Optional.empty());
		// フレックス勤務時の代休取得時の計算方法
		FlexCalcMethodOfCompLeave calcMethod = settingOfFlex.getFlexSet().getCompLeave();
		// 日単位の所定控除時間の計算
		AttendanceTime forActualWork = calcMethod.calcDeductPredTimeOfDay(
				workType, false, breakDown, settingOfFlex.getBasicSet());
		AttendanceTime forPremium = calcMethod.calcDeductPredTimeOfDay(
				workType, true, breakDown, settingOfFlex.getBasicSet());
		// 設定別控除時間を返す
		return new DeductTimeEachSet(forActualWork, forPremium);
	}

	/**
	 * 時間単位の代休控除時間の計算
	 * @param settingOfFlex フレックス勤務の設定
	 * @return 代休控除時間
	 */
	private AttendanceTime calcDeductCompLeaveOfTime(SettingOfFlexWork settingOfFlex){
		
		// 代休相殺時間の計算
		AttendanceTime offsetTime = this.calcCompLeaveOffsetTime();
		// 代休使用時間の計算
		AttendanceTime useTime = this.calcCompLeaveUseTime();
		// フレックス勤務時の代休取得時の計算方法
		FlexCalcMethodOfCompLeave calcMethod = settingOfFlex.getFlexSet().getCompLeave();
		// 時間単位の所定控除時間の計算
		AttendanceTime deductTime = calcMethod.calcDeductPredTimeOfTime(
				offsetTime, useTime, settingOfFlex.getBasicSet());
		// 所定控除時間を返す
		return deductTime;
	}
	
	/**
	 * 欠勤控除時間を計算する
	 * @param workType 勤務種類
	 * @param predetermineTimeSet 所定時間設定
	 * @param predetermineTimeSetByPersonInfo 所定時間設定（個人）
	 * @param siftCode 就業時間帯コード
	 * @param conditionItem 労働条件項目
	 * @param addSetting 加算設定
	 * @return 欠勤控除時間
	 */
	private AttendanceTime calcDeductAbsence(
			Require require,
			GeneralDate baseDate,
			WorkInfoOfDailyAttendance workInfo,
			WorkType workType,
			Optional<PredetermineTimeSetForCalc> predetermineTimeSet,
			WorkingConditionItem  conditionItem,
			AddSetting addSetting) {
		
		// 欠勤をマイナスせず所定から控除する＝「しない」
		if (!addSetting.getAddSetOfWorkingTime().getAddSetOfWorkTime().isMinusAbsenceTime()){
			return AttendanceTime.ZERO;
		}
		// 日数単位の休暇時間計算
		return VacationClass.vacationTimeOfCalcDaily(
				require,
				conditionItem.getEmployeeId(),
				baseDate,
				workInfo,
				workType,
				VacationCategory.Absence,
				predetermineTimeSet,
				Optional.empty());
	}
	
	/**
	 * 就業時間の計算
	 * @param personCommonSetting 個人設定管理
	 * @param integrationOfDaily 日別実績(WORK)
	 * @param integrationOfWorkTime 統合就業時間帯
	 * @param premiumAtr 割増区分
	 * @param workType 勤務種類
	 * @param predetermineTimeSet 計算用所定時間設定
	 * @param autoCalcOfLeaveEarlySetting 遅刻早退の自動計算設定
	 * @param addSetting 加算設定
	 * @param holidayAddtionSet 休暇加算時間設定
	 * @param flexAutoCalcAtr 時間外の自動計算区分
	 * @param settingOfFlex フレックス勤務の設定
	 * @param preFlexTime 事前フレ
	 * @param dailyUnit 法定労働時間
	 * @param commonSetting 就業時間帯の共通設定
	 * @param flexUpper 時間外の上限設定
	 * @param lateEarlyMinusAtr 強制的に遅刻早退控除する
	 * @return 就業時間
	 */
	public AttendanceTime calcWorkTime(
			ManagePerPersonDailySet personCommonSetting,
			IntegrationOfDaily integrationOfDaily,
			Optional<IntegrationOfWorkTime> integrationOfWorkTime,
			PremiumAtr premiumAtr,
			WorkType workType,
			PredetermineTimeSetForCalc predetermineTimeSet,
			AutoCalcOfLeaveEarlySetting autoCalcOfLeaveEarlySetting,
			AddSetting addSetting,
			HolidayAddtionSet holidayAddtionSet,
			AutoCalAtrOvertime flexAutoCalcAtr, //フレの上限時間設定（時間外の自動計算区分）
			SettingOfFlexWork settingOfFlex,
			AttendanceTime preFlexTime,
			DailyUnit dailyUnit,
			Optional<WorkTimezoneCommonSet> commonSetting,
			TimeLimitUpperLimitSetting flexUpper,//こいつは残さないといけない
			NotUseAtr lateEarlyMinusAtr) {
		
		//就業時間（法定内用）の計算//
		AttendanceTime withinTime = super.calcWorkTime(
				personCommonSetting,
				integrationOfDaily,
				integrationOfWorkTime,
				premiumAtr,
				workType,
				predetermineTimeSet,
				autoCalcOfLeaveEarlySetting,
				addSetting,
				holidayAddtionSet,
				Optional.of(settingOfFlex),
				dailyUnit,
				commonSetting,
				lateEarlyMinusAtr).getWorkTime();
		
		//フレックス時間の計算
		FlexTime flexTime = this.createWithinWorkTimeSheetAsFlex(
				personCommonSetting,
				integrationOfDaily,
				integrationOfWorkTime,
				flexAutoCalcAtr,
				workType,
				settingOfFlex,
				predetermineTimeSet,
				autoCalcOfLeaveEarlySetting,
				addSetting.getWorkTimeDeductFlexTime(),
				holidayAddtionSet,
				flexUpper,
				preFlexTime,
				dailyUnit,
				commonSetting,
				lateEarlyMinusAtr,
				Optional.of(DeductionAtr.Deduction));
		
		AttendanceTime result = new AttendanceTime(0);
		if(flexTime.getFlexTime().getCalcTime().greaterThan(0)) {
			result = withinTime.minusMinutes(flexTime.getFlexTime().getCalcTime().valueAsMinutes());
		}
		else {
			result = withinTime;
		}
		
//		if(holidayCalcMethodSet.getWorkTimeCalcMethodOfHoliday().getAdvancedSet().isPresent()&&coreTimeSetting.isPresent()&&!coreTimeSetting.get().isUseTimeSheet()) {
//			//遅刻時間を就業時間から控除しない場合かつ最低勤務時間よりも就業時間が小さい場合の処理
//			if(!holidayCalcMethodSet.getWorkTimeCalcMethodOfHoliday().getAdvancedSet().get().isDeductLateLeaveEarly(commonSetting)) {
//				if(result.lessThan(coreTimeSetting.get().getMinWorkTime())) {
//					result = coreTimeSetting.get().getMinWorkTime();
//				}
//			}
//		}
		return result;
	}
	
	/**
	 * コア無しフレックスで遅刻した場合の時間補正
	 * @param personCommonSetting 社員設定管理
	 * @param integrationOfDaily 日別実績(WORK)
	 * @param integrationOfWorkTime 統合就業時間帯
	 * @param premiumAtr 割増区分
	 * @param workType 勤務種類
	 * @param predetermineTimeSet 計算用所定時間設定
	 * @param autoCalcOfLeaveEarlySetting 遅刻早退の自動計算設定
	 * @param addSetting 加算設定
	 * @param holidayAddtionSet 休暇加算時間設定
	 * @param dailyUnit 法定労働時間
	 * @param lateEarlyMinusAtr 強制的に遅刻早退控除する
	 * @param settingOfFlex フレックス勤務の設定
	 * @param workTime 就業時間
	 * @return 補正後の就業時間
	 */
	public AttendanceTime correctTimeForNoCoreFlexLate(
			ManagePerPersonDailySet personCommonSetting,
			IntegrationOfDaily integrationOfDaily,
			Optional<IntegrationOfWorkTime> integrationOfWorkTime,
			PremiumAtr premiumAtr,
			WorkType workType,
			PredetermineTimeSetForCalc predetermineTimeSet,
			AutoCalcOfLeaveEarlySetting autoCalcOfLeaveEarlySetting,
			AddSetting addSetting,
			HolidayAddtionSet holidayAddtionSet,
			DailyUnit dailyUnit,
			NotUseAtr lateEarlyMinusAtr,
			Optional<SettingOfFlexWork> settingOfFlex,
			AttendanceTime workTime) {
		
		// 就業時間帯の共通設定の確認
		Optional<WorkTimezoneCommonSet> commonSetting = Optional.empty();
		Optional<CoreTimeSetting> coreTimeSetting = Optional.empty();
		if (integrationOfWorkTime.isPresent()){
			commonSetting = Optional.of(integrationOfWorkTime.get().getCommonSetting());
			coreTimeSetting = integrationOfWorkTime.get().getCoreTimeSettingForCalc(Optional.of(workType));
		}
		// 労働時間の加算設定を取得する
		AddSettingOfWorkingTime addSetOfWorkTime = addSetting.getAddSetOfWorkingTime();
		// 遅刻早退を就業時間に含めるか判断する
		Optional<WorkTimezoneLateEarlySet> lateEarlySet = Optional.empty();
		if (commonSetting.isPresent()) lateEarlySet = Optional.of(commonSetting.get().getLateEarlySet());
		if (addSetOfWorkTime.isIncludeLateEarlyInWorkTime(PremiumAtr.RegularWork, lateEarlySet)){
			// コアタイム無しの遅刻時間計算
			TimeWithCalculation lateTime = this.calcLateTimeOfNoCoreTime(
					personCommonSetting,
					integrationOfDaily,
					integrationOfWorkTime,
					DeductionAtr.Deduction,
					premiumAtr,
					workType,
					predetermineTimeSet,
					autoCalcOfLeaveEarlySetting,
					addSetting,
					holidayAddtionSet,
					dailyUnit,
					lateEarlyMinusAtr,
					settingOfFlex);
			if (lateTime.getCalcTime().valueAsMinutes() > 0){
				// 就業時間←最低勤務時間
				workTime = new AttendanceTime(coreTimeSetting.get().getMinWorkTime().valueAsMinutes());
			}
		}
		// 就業時間を返す
		return workTime;
	}
	
	/**
	 * コアタイム無しの遅刻時間計算
	 * @param personCommonSetting 社員設定管理
	 * @param integrationOfDaily 日別実績(WORK)
	 * @param integrationOfWorkTime 統合就業時間帯
	 * @param deductionAtr 控除区分
	 * @param premiumAtr 割増区分
	 * @param workType 勤務種類
	 * @param predetermineTimeSet 計算用所定時間設定
	 * @param autoCalcOfLeaveEarlySetting 遅刻早退の自動計算設定
	 * @param addSetting 加算設定
	 * @param holidayAddtionSet 休暇加算時間設定
	 * @param dailyUnit 法定労働時間
	 * @param lateEarlyMinusAtr 強制的に遅刻早退控除する
	 * @param settingOfFlex フレックス勤務の設定
	 * @return コア無しフレックス遅刻時間
	 */
	public TimeWithCalculation calcLateTimeOfNoCoreTime(
			ManagePerPersonDailySet personCommonSetting,
			IntegrationOfDaily integrationOfDaily,
			Optional<IntegrationOfWorkTime> integrationOfWorkTime,
			DeductionAtr deductionAtr,
			PremiumAtr premiumAtr,
			WorkType workType,
			PredetermineTimeSetForCalc predetermineTimeSet,
			AutoCalcOfLeaveEarlySetting autoCalcOfLeaveEarlySetting,
			AddSetting addSetting,
			HolidayAddtionSet holidayAddtionSet,
			DailyUnit dailyUnit,
			NotUseAtr lateEarlyMinusAtr,
			Optional<SettingOfFlexWork> settingOfFlex) {
		
		AttendanceTime lateTime = AttendanceTime.ZERO;
		
		// コアタイム時間帯設定の確認
		Optional<CoreTimeSetting> coreTimeSetting = Optional.empty();
		if (integrationOfWorkTime.isPresent()){
			coreTimeSetting = integrationOfWorkTime.get().getCoreTimeSettingForCalc(Optional.of(workType));
		}
		// コアあり/なしの判断
		if (coreTimeSetting.isPresent() && coreTimeSetting.get().isUseTimeSheet()){
			// 使用する
		}
		else {
			// 使用しない
			// 遅刻時間の計算
			lateTime = this.calcLateTime(
					personCommonSetting,
					integrationOfDaily,
					integrationOfWorkTime,
					deductionAtr,
					premiumAtr,
					workType,
					predetermineTimeSet,
					autoCalcOfLeaveEarlySetting,
					addSetting,
					holidayAddtionSet,
					dailyUnit,
					lateEarlyMinusAtr,
					settingOfFlex);
		}
		// 計算区分を取得　～　遅刻時間とする　～　コア無しフレックス遅刻時間を返す
		if (autoCalcOfLeaveEarlySetting.isLate()) {
			return TimeWithCalculation.sameTime(lateTime);
		}
		return TimeWithCalculation.createTimeWithCalculation(AttendanceTime.ZERO, lateTime);
	}

	/**
	 * 相殺用遅刻時間の計算
	 * （コア無しフレックス専用処理） 
	 * (相殺休暇使用時間の計算専用)
	 * @param personCommonSetting 社員設定管理
	 * @param integrationOfDaily 日別実績(WORK)
	 * @param integrationOfWorkTime 統合就業時間帯
	 * @param premiumAtr 割増区分
	 * @param workType 勤務種類
	 * @param predetermineTimeSet 計算用所定時間設定
	 * @param autoCalcOfLeaveEarlySetting 遅刻早退の自動計算設定
	 * @param addSetting 加算設定
	 * @param holidayAddtionSet 休暇加算時間設定
	 * @param dailyUnit 法定労働時間
	 * @param lateEarlyMinusAtr 強制的に遅刻早退控除する
	 * @param settingOfFlex フレックス勤務の設定
	 * @return 遅刻時間
	 */
	public AttendanceTime calcLateTimeForOffset(
			ManagePerPersonDailySet personCommonSetting,
			IntegrationOfDaily integrationOfDaily,
			Optional<IntegrationOfWorkTime> integrationOfWorkTime,
			PremiumAtr premiumAtr,
			WorkType workType,
			PredetermineTimeSetForCalc predetermineTimeSet,
			AutoCalcOfLeaveEarlySetting autoCalcOfLeaveEarlySetting,
			AddSetting addSetting,
			HolidayAddtionSet holidayAddtionSet,
			DailyUnit dailyUnit,
			NotUseAtr lateEarlyMinusAtr,
			Optional<SettingOfFlexWork> settingOfFlex) {
		
		// 就業時間帯の共通設定の確認
		Optional<CoreTimeSetting> coreTimeSettingForCalc = Optional.empty();
		if (integrationOfWorkTime.isPresent()){
			coreTimeSettingForCalc = integrationOfWorkTime.get().getCoreTimeSettingForCalc(Optional.of(workType));
		}
		
		// コア無し遅刻時間計算用の就業時間の計算
		AttendanceTime noCore = this.calcWorkTimeForCalcNoCoreLateTime(
				personCommonSetting, integrationOfDaily, integrationOfWorkTime, premiumAtr,
				workType, predetermineTimeSet, autoCalcOfLeaveEarlySetting, addSetting,
				holidayAddtionSet, dailyUnit, lateEarlyMinusAtr, settingOfFlex);
		// 遅刻時間の計算　（最低勤務時間　－　就業時間）
		AttendanceTime result = coreTimeSettingForCalc.get().getMinWorkTime().minusMinutes(noCore.valueAsMinutes());
		// マイナスの場合は0
		if (result.valueAsMinutes() < 0) return AttendanceTime.ZERO;
		// 遅刻時間を返す
		return result;
	}
	
	/**
	 * 遅刻時間の計算
	 * （コア無しフレックス専用処理）
	 * @param personCommonSetting 社員設定管理
	 * @param integrationOfDaily 日別実績(WORK)
	 * @param integrationOfWorkTime 統合就業時間帯
	 * @param deductionAtr 控除区分
	 * @param premiumAtr 割増区分
	 * @param workType 勤務種類
	 * @param predetermineTimeSet 計算用所定時間設定
	 * @param autoCalcOfLeaveEarlySetting 遅刻早退の自動計算設定
	 * @param addSetting 加算設定
	 * @param holidayAddtionSet 休暇加算時間設定
	 * @param dailyUnit 法定労働時間
	 * @param lateEarlyMinusAtr 強制的に遅刻早退控除する
	 * @param settingOfFlex フレックス勤務の設定
	 * @return 遅刻時間
	 */
	private AttendanceTime calcLateTime(
			ManagePerPersonDailySet personCommonSetting,
			IntegrationOfDaily integrationOfDaily,
			Optional<IntegrationOfWorkTime> integrationOfWorkTime,
			DeductionAtr deductionAtr,
			PremiumAtr premiumAtr,
			WorkType workType,
			PredetermineTimeSetForCalc predetermineTimeSet,
			AutoCalcOfLeaveEarlySetting autoCalcOfLeaveEarlySetting,
			AddSetting addSetting,
			HolidayAddtionSet holidayAddtionSet,
			DailyUnit dailyUnit,
			NotUseAtr lateEarlyMinusAtr,
			Optional<SettingOfFlexWork> settingOfFlex) {
		
		// 就業時間帯の共通設定の確認
		Optional<WorkTimezoneCommonSet> commonSetting = Optional.empty();
		Optional<CoreTimeSetting> coreTimeSettingForCalc = Optional.empty();
		if (integrationOfWorkTime.isPresent()){
			commonSetting = Optional.of(integrationOfWorkTime.get().getCommonSetting());
			coreTimeSettingForCalc = integrationOfWorkTime.get().getCoreTimeSettingForCalc(Optional.of(workType));
		}
		// 労働時間の加算設定を確認する
		AddSettingOfWorkingTime addSetOfWorkTime = addSetting.getAddSetOfWorkingTime();
		// 遅刻早退を就業時間に含めるか判断する
		Optional<WorkTimezoneLateEarlySet> lateEarlySet = Optional.empty();
		if (commonSetting.isPresent()) lateEarlySet = Optional.of(commonSetting.get().getLateEarlySet());
		boolean isIncludeLate = addSetOfWorkTime.isIncludeLateEarlyInWorkTime(premiumAtr, lateEarlySet);
		// 控除区分="控除"　かつ　控除しない（含める）
		if (deductionAtr.isDeduction() && isIncludeLate) return AttendanceTime.ZERO;
		// コア無し遅刻時間計算用の就業時間の計算
		AttendanceTime noCore = this.calcWorkTimeForCalcNoCoreLateTime(
				personCommonSetting,
				integrationOfDaily,
				integrationOfWorkTime,
				premiumAtr,
				workType,
				predetermineTimeSet,
				autoCalcOfLeaveEarlySetting,
				addSetting,
				holidayAddtionSet,
				dailyUnit,
				lateEarlyMinusAtr,
				settingOfFlex);
		// 遅刻時間の計算　（最低勤務時間　－　就業時間）
		AttendanceTime result = coreTimeSettingForCalc.get().getMinWorkTime().minusMinutes(noCore.valueAsMinutes());
		// マイナスの場合は0
		if (result.valueAsMinutes() < 0) return AttendanceTime.ZERO;
		// 遅刻時間を返す
		return result;
	}
	
	/**
	 * コアタイム無し遅刻時間計算用の就業時間の計算
	 * @param personCommonSetting 社員設定管理
	 * @param integrationOfDaily 日別実績(WORK)
	 * @param integrationOfWorkTime 統合就業時間帯
	 * @param premiumAtr 割増区分
	 * @param workType 勤務種類
	 * @param predetermineTimeSet 計算用所定時間設定
	 * @param autoCalcOfLeaveEarlySetting 遅刻早退の自動計算設定
	 * @param addSetting 加算設定
	 * @param holidayAddtionSet 休暇加算時間設定
	 * @param dailyUnit 法定労働時間
	 * @param lateEarlyMinusAtr 強制的に遅刻早退控除する
	 * @param settingOfFlex フレックス勤務の設定
	 * @return 就業時間
	 */
	private AttendanceTime calcWorkTimeForCalcNoCoreLateTime(
			ManagePerPersonDailySet personCommonSetting,
			IntegrationOfDaily integrationOfDaily,
			Optional<IntegrationOfWorkTime> integrationOfWorkTime,
			PremiumAtr premiumAtr,
			WorkType workType,
			PredetermineTimeSetForCalc predetermineTimeSet,
			AutoCalcOfLeaveEarlySetting autoCalcOfLeaveEarlySetting,
			AddSetting addSetting,
			HolidayAddtionSet holidayAddtionSet,
			DailyUnit dailyUnit,
			NotUseAtr lateEarlyMinusAtr,
			Optional<SettingOfFlexWork> settingOfFlex) {
		
		// 就業時間帯の共通設定の確認
		Optional<WorkTimezoneCommonSet> commonSetting = Optional.empty();
		if (integrationOfWorkTime.isPresent()){
			commonSetting = Optional.of(integrationOfWorkTime.get().getCommonSetting());
		}
		// 遅刻、早退の控除設定を「控除する」に変更する
		AddSetting changeAddSet = addSetting.createNewDeductLateEarly();
		// 就業時間（法定内用）の計算
		AttendanceTime result = this.calcWorkTime(
				personCommonSetting,
				integrationOfDaily,
				integrationOfWorkTime,
				premiumAtr,
				workType,
				predetermineTimeSet,
				autoCalcOfLeaveEarlySetting,
				changeAddSet,
				holidayAddtionSet,
				settingOfFlex,
				dailyUnit,
				commonSetting,
				lateEarlyMinusAtr).getWorkTime();
		// 就業時間を返す
		return result;
	}
	
	/**
	 * コア内外の外出時間の計算
	 * @param isWithin コア内外区分（true=コア内、false=コア外）
	 * @param conditionAtr 控除種別区分
	 * @param dedAtr 控除区分
	 * @param roundAtr 丸め区分
	 * @return 外出時間
	 */
	public AttendanceTime calcOutingTimeInFlex(
			boolean isWithin,
			ConditionAtr conditionAtr,
			DeductionAtr dedAtr,
			Optional<WorkTimezoneGoOutSet> goOutSet) {
		
		// コアタイムとの重複を判断して時間帯を作成
		List<WithinWorkTimeFrame> targetFrameList = this.createSpanDuplicatedWithCoreTime(isWithin);
		// 控除時間の計算
		AttendanceTime goOutTime = ActualWorkTimeSheetListService.calcDeductionTime(
				conditionAtr, dedAtr, goOutSet,
				targetFrameList.stream().map(t -> (ActualWorkingTimeSheet)t).collect(Collectors.toList()));
		// 外出時間を返す
		return goOutTime;
	}

	/**
	 * コアタイムとの重複を判断して時間帯を作成
	 * @param isWithin コア内外区分（true=コア内、false=コア外）
	 * @return 就業時間内時間枠List
	 */
	private List<WithinWorkTimeFrame> createSpanDuplicatedWithCoreTime(boolean isWithin){
		
		List<WithinWorkTimeFrame> results = new ArrayList<>();
		
		// コアタイム時間帯を取得
		if (!this.coreTimeSheet.isPresent()) return results;
		TimeSpanForCalc coreSpan = this.coreTimeSheet.get().getTimeSpan();
		// 就業時間内時間枠を取得
		for (WithinWorkTimeFrame workTimeFrame : this.withinWorkTimeFrame){
			// コアタイムとの重複を判断して時間帯を取得
			results.addAll(workTimeFrame.getFrameDuplicatedWithCoreTime(isWithin, coreSpan));
		}
		// 就業時間内時間枠Listを返す
		return results;
	}
	
	/**
	 * 実働就業時間の計算
	 * @param personCommonSetting 個人設定管理
	 * @param integrationOfDaily 日別実績(WORK)
	 * @param integrationOfWorkTime 統合就業時間帯
	 * @param workType 勤務種類
	 * @param predetermineTimeSet 計算用所定時間設定
	 * @param autoCalcOfLeaveEarlySetting 遅刻早退の自動計算設定
	 * @param addSetting 加算設定
	 * @param holidayAddtionSet 休暇加算時間設定
	 * @param flexAutoCalcAtr 時間外の自動計算区分
	 * @param settingOfFlex フレックス勤務の設定
	 * @param preFlexTime 事前フレックス時間
	 * @param dailyUnit 法定労働時間
	 * @param commonSetting 就業時間帯の共通設定
	 * @param flexUpper 時間外の上限設定
	 * @param lateEarlyMinusAtr 強制的に遅刻早退控除する
	 * @return 実働就業時間
	 */
	public AttendanceTime calcActualWorkTime(
			ManagePerPersonDailySet personCommonSetting,
			IntegrationOfDaily integrationOfDaily,
			Optional<IntegrationOfWorkTime> integrationOfWorkTime,
			WorkType workType,
			PredetermineTimeSetForCalc predetermineTimeSet,
			AutoCalcOfLeaveEarlySetting autoCalcOfLeaveEarlySetting,
			AddSetting addSetting,
			HolidayAddtionSet holidayAddtionSet,
			AutoCalAtrOvertime flexAutoCalcAtr, //フレの上限時間設定
			SettingOfFlexWork settingOfFlex,
			AttendanceTime preFlexTime,
			DailyUnit dailyUnit,
			Optional<WorkTimezoneCommonSet> commonSetting,
			TimeLimitUpperLimitSetting flexUpper,//こいつは残さないといけない
			NotUseAtr lateEarlyMinusAtr) {
		
		//実働のみ
		AttendanceTime withinTime = super.calcWorkTime(
				personCommonSetting,
				integrationOfDaily,
				integrationOfWorkTime,
				PremiumAtr.RegularWork,
				workType,
				predetermineTimeSet,
				autoCalcOfLeaveEarlySetting,
				addSetting.createCalculationByActualTime(),
				holidayAddtionSet,
				Optional.of(settingOfFlex),
				dailyUnit,
				commonSetting,
				lateEarlyMinusAtr).getWorkTime();
		//休暇加算のマスタを見る
		FlexTime flexTime = this.createWithinWorkTimeSheetAsFlex(
				personCommonSetting,
				integrationOfDaily,
				integrationOfWorkTime,
				flexAutoCalcAtr,
				workType,
				settingOfFlex,
				predetermineTimeSet,
				autoCalcOfLeaveEarlySetting,
				addSetting.createCalculationByActualTime(),
				holidayAddtionSet,
				flexUpper,
				preFlexTime,
				dailyUnit,
				commonSetting,
				NotUseAtr.USE,
				Optional.of(DeductionAtr.Deduction));
		// 実働就業時間
		int resultMinutes = withinTime.valueAsMinutes();
		int flexMinutes = flexTime.getFlexTime().getTime().valueAsMinutes();
		// 実働就業時間からフレックス時間を引く
		if (flexMinutes > 0) resultMinutes -= flexMinutes;
		// 実働就業時間を返す
		return new AttendanceTime(resultMinutes);
	}

	/**
	 * 所定内深夜時間の計算
	 * @param personCommonSetting 社員設定管理
	 * @param integrationOfDaily 日別実績(WORK)
	 * @param integrationOfWorkTime 統合就業時間帯
	 * @param autoCalcAtr 時間外の自動計算区分
	 * @param workType 勤務種類
	 * @param flexCalcMethod フレックス勤務の設定
	 * @param predetermineTimeSet 計算用所定時間設定
	 * @param autoCalcOfLeaveEarlySetting 遅刻早退の自動計算設定
	 * @param addSetting 加算設定
	 * @param holidayAddtionSet 休暇加算時間設定
	 * @param dailyUnit 法定労働時間
	 * @param commonSetting 就業時間帯の共通設定
	 * @param flexUpper 時間外の上限設定
	 * @param lateEarlyMinusAtr 強制的に遅刻早退控除する
	 * @return 所定内深夜時間
	 */
	public AttendanceTime calcWithinMidnightTime(
			ManagePerPersonDailySet personCommonSetting,
			IntegrationOfDaily integrationOfDaily,
			Optional<IntegrationOfWorkTime> integrationOfWorkTime,
			AutoCalAtrOvertime autoCalcAtr,
			WorkType workType,
			SettingOfFlexWork flexCalcMethod,
			PredetermineTimeSetForCalc predetermineTimeSet,
			AutoCalcOfLeaveEarlySetting autoCalcOfLeaveEarlySetting,
			AddSetting addSetting,
			HolidayAddtionSet holidayAddtionSet,
			DailyUnit dailyUnit,
			Optional<WorkTimezoneCommonSet> commonSetting,
			TimeLimitUpperLimitSetting flexUpper,//こいつは残さないとだめ,
			NotUseAtr lateEarlyMinusAtr) {
		
		// フレックス深夜時間帯の作成
		FlexMidnightTimeSheet flexMidnightTimeSheet = FlexMidnightTimeSheet.create(
				this,
				personCommonSetting,
				integrationOfDaily,
				integrationOfWorkTime,
				autoCalcAtr,
				workType,
				flexCalcMethod,
				predetermineTimeSet,
				autoCalcOfLeaveEarlySetting,
				addSetting,
				holidayAddtionSet,
				dailyUnit,
				commonSetting,
				flexUpper,
				lateEarlyMinusAtr);
		// 深夜時間を累計する　→　所定内深夜時間を返す
		return FlexMidnightTimeSheet.sumMidnightTime(flexMidnightTimeSheet.getWithin());
	}

	/**
	 * 所定外深夜時間の計算
	 * @param personCommonSetting 社員設定管理
	 * @param integrationOfDaily 日別実績(WORK)
	 * @param integrationOfWorkTime 統合就業時間帯
	 * @param autoCalcAtr 時間外の自動計算区分
	 * @param workType 勤務種類
	 * @param flexCalcMethod フレックス勤務の設定
	 * @param predetermineTimeSet 計算用所定時間設定
	 * @param timevacationUseTimeOfDaily 休暇使用合計残時間未割当
	 * @param autoCalcOfLeaveEarlySetting 遅刻早退の自動計算設定
	 * @param addSetting 加算設定
	 * @param holidayAddtionSet 休暇加算時間設定
	 * @param dailyUnit 法定労働時間
	 * @param commonSetting 就業時間帯の共通設定
	 * @param flexUpper 時間外の上限設定
	 * @param lateEarlyMinusAtr 強制的に遅刻早退控除する
	 * @return 所定外深夜時間
	 */
	public AttendanceTime calcWithoutMidnightTime(
			ManagePerPersonDailySet personCommonSetting,
			IntegrationOfDaily integrationOfDaily,
			Optional<IntegrationOfWorkTime> integrationOfWorkTime,
			AutoCalAtrOvertime autoCalcAtr,
			WorkType workType,
			SettingOfFlexWork flexCalcMethod,
			PredetermineTimeSetForCalc predetermineTimeSet,
			AutoCalcOfLeaveEarlySetting autoCalcOfLeaveEarlySetting,
			AddSetting addSetting,
			HolidayAddtionSet holidayAddtionSet,
			DailyUnit dailyUnit,
			Optional<WorkTimezoneCommonSet> commonSetting,
			TimeLimitUpperLimitSetting flexUpper,//こいつは残さないとだめ,
			NotUseAtr lateEarlyMinusAtr) {
		
		// フレックス深夜時間帯の作成
		FlexMidnightTimeSheet flexMidnightTimeSheet = FlexMidnightTimeSheet.create(
				this,
				personCommonSetting,
				integrationOfDaily,
				integrationOfWorkTime,
				autoCalcAtr,
				workType,
				flexCalcMethod,
				predetermineTimeSet,
				autoCalcOfLeaveEarlySetting,
				addSetting,
				holidayAddtionSet,
				dailyUnit,
				commonSetting,
				flexUpper,
				lateEarlyMinusAtr);
		// 深夜時間を累計する　→　所定外深夜時間を返す
		return FlexMidnightTimeSheet.sumMidnightTime(flexMidnightTimeSheet.getWithout());
	}
	
	/**
	 * 所定外開始時刻の計算
	 * @param personCommonSetting 個人設定管理
	 * @param integrationOfDaily 日別実績(WORK)
	 * @param integrationOfWorkTime 統合就業時間帯
	 * @param autoCalcAtr 時間外の自動計算区分
	 * @param workType 勤務種類
	 * @param settingOfFlex フレックス勤務の設定
	 * @param predetermineTimeSet 計算用所定時間設定
	 * @param autoCalcOfLeaveEarlySetting 遅刻早退の自動計算設定
	 * @param addSetting 加算設定
	 * @param holidayAddtionSet 休暇加算時間設定
	 * @param dailyUnit 法定労働時間
	 * @param commonSetting 就業時間帯の共通設定
	 * @param flexUpper 時間外の上限設定
	 * @param lateEarlyMinusAtr 強制的に遅刻早退控除する
	 * @return 所定外開始時刻
	 */
	public Optional<TimeWithDayAttr> calcWithoutStartTime(
			ManagePerPersonDailySet personCommonSetting,
			IntegrationOfDaily integrationOfDaily,
			Optional<IntegrationOfWorkTime> integrationOfWorkTime,
			AutoCalAtrOvertime autoCalcAtr,
			WorkType workType,
			SettingOfFlexWork settingOfFlex,
			PredetermineTimeSetForCalc predetermineTimeSet,
			AutoCalcOfLeaveEarlySetting autoCalcOfLeaveEarlySetting,
			AddSetting addSetting,
			HolidayAddtionSet holidayAddtionSet,
			DailyUnit  dailyUnit,
			Optional<WorkTimezoneCommonSet> commonSetting,
			TimeLimitUpperLimitSetting flexUpper,//こいつは残さないとだめ,
			NotUseAtr lateEarlyMinusAtr) {
		
		// フレックス時間の計算
		int flexMinutes = this.calcFlexTime(
				personCommonSetting,
				integrationOfDaily,
				integrationOfWorkTime,
				autoCalcAtr,
				workType,
				settingOfFlex,
				predetermineTimeSet,
				autoCalcOfLeaveEarlySetting,
				addSetting,
				holidayAddtionSet,
				dailyUnit,
				commonSetting,
				flexUpper,
				lateEarlyMinusAtr).getFlexTime().getCalcTime().valueAsMinutes();
		// 就業時間帯時間枠を時刻の遅い順に確認する
		if (this.getWithinWorkTimeFrame().size() == 0) return Optional.empty();
		List<WithinWorkTimeFrame> sortLateOrder = this.getWithinWorkTimeFrame().stream()
				.sorted((s1, s2) -> s2.getTimeSheet().getStart().valueAsMinutes() - s1.getTimeSheet().getStart().valueAsMinutes())
				.collect(Collectors.toList());
		// フレックス時間<=0　の時、時刻が最も遅い「就業時間内時間枠.時間帯.終了」を返す
		if (flexMinutes <= 0) return Optional.of(sortLateOrder.get(0).getTimeSheet().getEnd());
		// 時間枠を遅い順に処理
		for (WithinWorkTimeFrame timeFrame : sortLateOrder){
			// 時間枠時間の計算
			int timeFrameMinutes = timeFrame.calcTotalTime().valueAsMinutes();
			if (timeFrameMinutes == flexMinutes){
				// 「就業時間内時間枠.時間帯.開始」を返す
				return Optional.of(timeFrame.getTimeSheet().getStart());
			}
			if (timeFrameMinutes > flexMinutes){
				// 時間帯を指定時間に従って縮小
				Optional<TimeSpanForDailyCalc> within = timeFrame.contractTimeSheet(new TimeWithDayAttr(flexMinutes));
				if (!within.isPresent()) return Optional.empty();
				// 「所定内.終了」を返す
				return Optional.of(within.get().getEnd());
			}
			// フレックス時間から時間枠時間を減算する
			flexMinutes -= timeFrameMinutes;
		}
		// 最後に確認した「就業時間内時間枠.時間帯.開始」を返す
		return Optional.of(sortLateOrder.get(sortLateOrder.size()-1).getTimeSheet().getStart());
	}
	
	/**
	 * コア内の外出時間帯を控除する
	 * @param integrationOfWorkTime 統合就業時間帯
	 * @param deductionTimeSheet 控除時間帯
	 * @return 控除時間帯
	 */
	public static DeductionTimeSheet deductGoOutSheetWithinCore(
			IntegrationOfWorkTime integrationOfWorkTime,
			DeductionTimeSheet deductionTimeSheet){
		
		if (!integrationOfWorkTime.getFlexWorkSetting().isPresent()) return deductionTimeSheet;
		// フレックス勤務設定
		FlexWorkSetting flexWorkSet = integrationOfWorkTime.getFlexWorkSetting().get();
		if (flexWorkSet.isDeductGoOutWithinCoreFromWorkTime()) return deductionTimeSheet;
		TimeSheet coreTimeSetting = flexWorkSet.getCoreTimeSetting().getCoreTimeSheet();
		// コアタイム時間帯
		TimeSpanForDailyCalc coreTimeSheet = new TimeSpanForDailyCalc(
				coreTimeSetting.getStartTime(), coreTimeSetting.getEndTime());
		return deductionTimeSheet.exceptTimeSheet(
				DeductionAtr.Deduction, DeductionClassification.GO_OUT, coreTimeSheet);
	}
	
	/**
	 * 重複する時間帯で作り直す
	 * @param timeSpan 時間帯
	 * @param commonSet 就業時間帯の共通設定
	 * @return 就業時間内時間帯
	 */
	@Override
	public FlexWithinWorkTimeSheet recreateWithDuplicate(TimeSpanForDailyCalc timeSpan, Optional<WorkTimezoneCommonSet> commonSet) {
		List<WithinWorkTimeFrame> frames = this.withinWorkTimeFrame.stream()
				.filter(t -> t.getTimeSheet().checkDuplication(timeSpan).isDuplicated())
				.collect(Collectors.toList());
		
		List<WithinWorkTimeFrame> duplicate = frames.stream()
				.map(f -> f.recreateWithDuplicate(timeSpan, commonSet))
				.filter(f -> f.isPresent())
				.map(f -> f.get())
				.collect(Collectors.toList());
		
		return new FlexWithinWorkTimeSheet(
				duplicate,
				this.shortTimeSheet,
				this.leaveEarlyDecisionClock,
				this.lateDecisionClock,
				this.coreTimeSheet);
	}
}
