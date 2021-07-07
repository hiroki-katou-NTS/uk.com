package nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailycalprocess.calculation;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import lombok.Getter;
import lombok.val;
import nts.gul.util.value.Finally;
import nts.uk.ctx.at.shared.dom.PremiumAtr;
import nts.uk.ctx.at.shared.dom.common.time.AttendanceTime;
import nts.uk.ctx.at.shared.dom.common.time.AttendanceTimeOfExistMinus;
import nts.uk.ctx.at.shared.dom.common.time.TimeSpanForCalc;
import nts.uk.ctx.at.shared.dom.scherec.addsettingofworktime.AddSetting;
import nts.uk.ctx.at.shared.dom.scherec.addsettingofworktime.CalcurationByActualTimeAtr;
import nts.uk.ctx.at.shared.dom.scherec.addsettingofworktime.DeductLeaveEarly;
import nts.uk.ctx.at.shared.dom.scherec.addsettingofworktime.HolidayAddtionSet;
import nts.uk.ctx.at.shared.dom.scherec.addsettingofworktime.HolidayCalcMethodSet;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.autocalsetting.AutoCalAtrOvertime;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.autocalsetting.AutoCalcOfLeaveEarlySetting;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.autocalsetting.TimeLimitUpperLimitSetting;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.TimevacationUseTimeOfDaily;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.attendancetime.TimeLeavingOfDailyAttd;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.breakouting.ConditionAtr;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.common.TimeDivergenceWithCalculationMinusExist;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.overtimehours.clearovertime.FlexTime;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.vacationusetime.VacationClass;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.workingstyle.flex.SettingOfFlexWork;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailycalprocess.calculation.timezone.deductiontime.DeductionAtr;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailycalprocess.calculation.timezone.deductiontime.TimeSheetOfDeductionItem;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailycalprocess.calculation.timezone.service.ActualWorkTimeSheetListService;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailycalprocess.calculation.timezone.withinworkinghours.LateDecisionClock;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailycalprocess.calculation.timezone.withinworkinghours.LeaveEarlyDecisionClock;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailycalprocess.calculation.timezone.withinworkinghours.WithinWorkTimeFrame;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailycalprocess.calculation.timezone.withinworkinghours.WithinWorkTimeSheet;
import nts.uk.ctx.at.shared.dom.scherec.statutory.worktime.week.DailyUnit;
import nts.uk.ctx.at.shared.dom.vacation.setting.addsettingofworktime.HolidayAdditionAtr;
import nts.uk.ctx.at.shared.dom.vacation.setting.addsettingofworktime.StatutoryDivision;
import nts.uk.ctx.at.shared.dom.workingcondition.WorkingConditionItem;
import nts.uk.ctx.at.shared.dom.workrule.goingout.GoingOutReason;
import nts.uk.ctx.at.shared.dom.worktime.common.WorkTimeCode;
import nts.uk.ctx.at.shared.dom.worktime.common.WorkTimezoneCommonSet;
import nts.uk.ctx.at.shared.dom.worktime.flexset.CoreTimeSetting;
import nts.uk.ctx.at.shared.dom.worktype.VacationCategory;
import nts.uk.ctx.at.shared.dom.worktype.WorkType;
import nts.uk.shr.com.enumcommon.NotUseAtr;
import nts.uk.shr.com.time.TimeWithDayAttr;

/**
 * フレックス就業時間内時間帯
 * @author keisuke_hoshina
 *
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
			Map<GoingOutReason,TimevacationUseTimeOfDaily> outingVacationUseTime,
			Finally<AttendanceTime> timeVacationAdditionRemainingTime,
			Optional<TimeSpanForDailyCalc> coreTimeSheet) {
		super(withinWorkTimeFrame, shortTimeSheets, leaveEarlyDecisionClock, lateDecisionClock, outingVacationUseTime, timeVacationAdditionRemainingTime);
		this.coreTimeSheet = coreTimeSheet;
	}	
	
	/**
	 * 代休使用時間の計算
	 * @return
	 */
	public AttendanceTime calcSubstituteHoliday(WorkType workType,
			 									Optional<PredetermineTimeSetForCalc> predetermineTimeSet,
			 									Optional<PredetermineTimeSetForCalc> predetermineTimeSetByPersonInfo,
			 									Optional<WorkTimeCode> siftCode,
			 									WorkingConditionItem  conditionItem,
			 									Optional<HolidayAddtionSet> holidayAddtionSet) {
		if(workType.getDailyWork().getOneDay().isSubstituteHoliday()
			|| workType.getDailyWork().getMorning().isSubstituteHoliday()
			|| workType.getDailyWork().getAfternoon().isSubstituteHoliday())
		{
			return VacationClass.vacationTimeOfcalcDaily(workType, 
													 VacationCategory.SubstituteHoliday,
													 predetermineTimeSet,
													 predetermineTimeSetByPersonInfo,
													 siftCode,
													 conditionItem,
													 holidayAddtionSet);
		}
		else {
			return new AttendanceTime(0);
		}
	}
	
	/**
	 * フレックス時間を計算する
	 * アルゴリズム：フレックス時間の加算
	 * @param calcMethod フレックス勤務の非勤務日の場合の計算方法
	 * @param holidayCalcMethodSet 休暇の計算方法の設定
	 * @param autoCalcAtr 時間外の自動計算区分
	 * @param workType 勤務種類
	 * @param flexCalcMethod フレックス勤務の設定
	 * @param predetermineTimeSet 計算用所定時間設定
	 * @param vacationClass 休暇クラス
	 * @param timevacationUseTimeOfDaily 休暇使用合計残時間未割当
	 * @param statutoryDivision 法定内区分
	 * @param siftCode 就業時間帯コード
	 * @param autoCalcOfLeaveEarlySetting 遅刻早退の自動計算設定
	 * @param addSetting 加算設定
	 * @param holidayAddtionSet 休暇加算時間設定
	 * @param flexLimitSetting 時間外の上限設定
	 * @param preAppTime 事前フレ
	 * @param dailyUnit 法定労働時間
	 * @param commonSetting 就業時間帯の共通設定
	 * @param conditionItem 法定内区分
	 * @param predetermineTimeSetByPersonInfo 計算用所定時間（個人）
	 * @param coreTimeSetting コアタイム時間帯設定
	 * @param lateEarlyMinusAtr 遅刻早退控除するかどうか
	 * @param deductionAtr 控除区分
	 * @param attendanceLeavingWork 日別勤怠の出退勤
	 * @return フレックス時間
	 */
	public FlexTime createWithinWorkTimeSheetAsFlex(
			CalcMethodOfNoWorkingDayForCalc calcMethod,
			HolidayCalcMethodSet holidayCalcMethodSet,
			AutoCalAtrOvertime autoCalcAtr,
			WorkType workType,
			SettingOfFlexWork flexCalcMethod,
			PredetermineTimeSetForCalc predetermineTimeSet,
			VacationClass vacationClass,
			AttendanceTime timevacationUseTimeOfDaily,
			StatutoryDivision statutoryDivision,
			Optional<WorkTimeCode> siftCode,
			AutoCalcOfLeaveEarlySetting autoCalcOfLeaveEarlySetting,
			AddSetting addSetting,
			HolidayAddtionSet holidayAddtionSet,
			TimeLimitUpperLimitSetting flexLimitSetting,
			AttendanceTime preAppTime,
			DailyUnit dailyUnit,
			Optional<WorkTimezoneCommonSet> commonSetting,
			WorkingConditionItem conditionItem,
			Optional<PredetermineTimeSetForCalc> predetermineTimeSetByPersonInfo,
			Optional<CoreTimeSetting> coreTimeSetting,
			NotUseAtr lateEarlyMinusAtr,
			Optional<DeductionAtr> deductionAtr,
			Optional<TimeLeavingOfDailyAttd> attendanceLeavingWork) {
		
		//フレックス時間の基準となる所定労働時間
		FlexTime flexTime = new FlexTime(TimeDivergenceWithCalculationMinusExist.sameTime(new AttendanceTimeOfExistMinus(0)),new AttendanceTime(0));
		
		//1日休日 or 1日休出  or 1日振休
		if(workType.getDailyWork().isOneDayHoliday() || workType.getDailyWork().isHolidayWork() || workType.getDailyWork().isPause())
			return flexTime;

		//フレックス計算しない　and 非勤務日
		if(!calcMethod.isCalclateFlexTime() && workType.getDailyWork().getAttendanceHolidayAttr().isHoliday()) 
				return flexTime;
		
		/*フレックス時間の計算*/
		CalcFlexTime calcflexTime = calcFlexTime(
				holidayCalcMethodSet,
				autoCalcAtr,
				workType,
				flexCalcMethod,
				predetermineTimeSet,
				vacationClass,
				timevacationUseTimeOfDaily,statutoryDivision,siftCode,
				autoCalcOfLeaveEarlySetting,
				addSetting,
				holidayAddtionSet,dailyUnit,commonSetting,
				flexLimitSetting, conditionItem,predetermineTimeSetByPersonInfo,coreTimeSetting,
				lateEarlyMinusAtr,
				attendanceLeavingWork);
		
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
	 * @param holidayCalcMethodSet 休暇の計算方法の設定
	 * @param autoCalcAtr 時間外の自動計算区分
	 * @param workType 勤務種類
	 * @param flexCalcMethod フレックス勤務の設定
	 * @param predetermineTimeSet 計算用所定時間設定
	 * @param vacationClass 休暇クラス
	 * @param timevacationUseTimeOfDaily 休暇使用合計残時間未割当
	 * @param statutoryDivision 法定内区分
	 * @param siftCode 就業時間帯コード
	 * @param autoCalcOfLeaveEarlySetting 遅刻早退の自動計算設定
	 * @param addSetting 加算設定
	 * @param holidayAddtionSet 休暇加算時間設定
	 * @param dailyUnit 法定労働時間
	 * @param commonSetting 就業時間帯の共通設定
	 * @param flexUpper 時間外の上限設定
	 * @param conditionItem 労働条件項目
	 * @param predetermineTimeSetByPersonInfo 計算用所定時間（個人）
	 * @param coreTimeSetting コアタイム時間帯設定
	 * @param lateEarlyMinusAtr 遅刻早退控除するかどうか
	 * @param attendanceLeavingWork 日別勤怠の出退勤
	 * @return フレックス時間
	 */
	public CalcFlexTime calcFlexTime(
			HolidayCalcMethodSet holidayCalcMethodSet,
			AutoCalAtrOvertime autoCalcAtr,
			WorkType workType,
			SettingOfFlexWork flexCalcMethod,
			PredetermineTimeSetForCalc predetermineTimeSet,
			VacationClass vacationClass,
			AttendanceTime timevacationUseTimeOfDaily,
			StatutoryDivision statutoryDivision,
			Optional<WorkTimeCode> siftCode,
			AutoCalcOfLeaveEarlySetting autoCalcOfLeaveEarlySetting,
			AddSetting addSetting,
			HolidayAddtionSet holidayAddtionSet,
			DailyUnit  dailyUnit,
			Optional<WorkTimezoneCommonSet> commonSetting,
			TimeLimitUpperLimitSetting flexUpper,//こいつは残さないとだめ,
			WorkingConditionItem conditionItem,
			Optional<PredetermineTimeSetForCalc> predetermineTimeSetByPersonInfo,
			Optional<CoreTimeSetting> coreTimeSetting,
			NotUseAtr lateEarlyMinusAtr,
			Optional<TimeLeavingOfDailyAttd> attendanceLeavingWork) {
		
		/*フレックス時間の基準となる所定労働時間の取得*/
		StatutoryWorkingTime houtei = calcStatutoryTime(workType,flexCalcMethod,predetermineTimeSet==null?Optional.empty():Optional.of(predetermineTimeSet), siftCode,conditionItem,Optional.of(holidayAddtionSet),predetermineTimeSetByPersonInfo);
		Optional<WorkTimezoneCommonSet> leaveLatesetForWorkTime = commonSetting.isPresent() && commonSetting.get().getLateEarlySet().getCommonSet().isDelFromEmTime() && coreTimeSetting.isPresent() && coreTimeSetting.get().isUseTimeSheet()
				?Optional.of(commonSetting.get().reverceTimeZoneLateEarlySet())
				:commonSetting;
				
		/*就業時間(法定内用)の計算*/
		WorkHour zitudou = super.calcWorkTime(
				PremiumAtr.RegularWork,
				vacationClass,
				timevacationUseTimeOfDaily,
				workType,
				predetermineTimeSet,
				siftCode,
				autoCalcOfLeaveEarlySetting,
				addSetting,
				holidayAddtionSet,
				holidayCalcMethodSet,
				dailyUnit,
				leaveLatesetForWorkTime,
				conditionItem,
				predetermineTimeSetByPersonInfo,
				coreTimeSetting,
				HolidayAdditionAtr.HolidayAddition.convertFromCalcByActualTimeToHolidayAdditionAtr(addSetting.getCalculationByActualTimeAtr(PremiumAtr.RegularWork)),
				lateEarlyMinusAtr,
				attendanceLeavingWork);
		
		/*就業時間(割増含む)の計算*/
		WorkHour zitudouIncludePremium = super.calcWorkTime(
				PremiumAtr.Premium,
				vacationClass,
				timevacationUseTimeOfDaily,
				workType,
				predetermineTimeSet,
				siftCode,
				autoCalcOfLeaveEarlySetting,
				addSetting,
				holidayAddtionSet,
				holidayCalcMethodSet,
				dailyUnit,
				leaveLatesetForWorkTime,
				conditionItem,
				predetermineTimeSetByPersonInfo,
				coreTimeSetting,
				HolidayAdditionAtr.HolidayAddition.convertFromCalcByActualTimeToHolidayAdditionAtr(addSetting.getCalculationByActualTimeAtr(PremiumAtr.Premium)),
				lateEarlyMinusAtr,
				attendanceLeavingWork);
		
		AttendanceTimeOfExistMinus flexTime = new AttendanceTimeOfExistMinus(0);
		AttendanceTime vacationAddTime = new AttendanceTime(0);
		
		//就業時間（割増含む）からフレックス時間を計算
		flexTime = new AttendanceTimeOfExistMinus(zitudouIncludePremium.getWorkTime().valueAsMinutes()).minusMinutes(houtei.getForWorkTimeIncludePremium().v());
		
		if(flexTime.lessThan(0)) {
			//就業時間(法定内用)からフレックス時間を計算
			AttendanceTimeOfExistMinus flexTimeIncludePremium = new AttendanceTimeOfExistMinus(zitudou.getWorkTime().valueAsMinutes()).minusMinutes(houtei.getForActualWorkTime().v());
			
			if(flexTimeIncludePremium.lessThan(0)) {
				//計算したフレックス時間を0：00を上限とする。
				flexTimeIncludePremium = (flexTimeIncludePremium.greaterThan(0))
						?new AttendanceTimeOfExistMinus(0)
						:flexTimeIncludePremium;
				
				//フレックス不足時の加算時間を計算
				int diffValue = flexTimeIncludePremium.valueAsMinutes()-flexTime.valueAsMinutes();
				flexTime = flexTimeIncludePremium;
				vacationAddTime = new AttendanceTime(diffValue);
			}
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
	 * 所定労働時間から控除(フレックス用)
	 * @return
	 */
	public StatutoryWorkingTime calcStatutoryTime(WorkType workType,SettingOfFlexWork flexCalcMethod,Optional<PredetermineTimeSetForCalc> predetermineTimeSet,
												  Optional<WorkTimeCode> siftCode, WorkingConditionItem conditionItem,Optional<HolidayAddtionSet> holidayAddtionSet,
												  Optional<PredetermineTimeSetForCalc> predetermineTimeSetByPersonInfo) {
		val predetermineTime = predetermineTimeSet.isPresent()?predetermineTimeSet.get().getAdditionSet().getPredTime().getOneDay():new AttendanceTime(0);
		StatutoryDeductionForFlex deductionTime = calcdeductTime(workType,flexCalcMethod,predetermineTimeSet,predetermineTimeSetByPersonInfo, siftCode,conditionItem,holidayAddtionSet);
		return new StatutoryWorkingTime( new AttendanceTime(predetermineTime.v() - deductionTime.getForActualWork().valueAsMinutes()) 
										,new AttendanceTime(predetermineTime.v() - deductionTime.getForPremium().valueAsMinutes()));
	}
	/**
	 * 控除する時間の計算
	 */
	public StatutoryDeductionForFlex calcdeductTime(WorkType workType,SettingOfFlexWork flexCalcMethod,
													Optional<PredetermineTimeSetForCalc> predetermineTimeSet,
													Optional<PredetermineTimeSetForCalc> predetermineTimeSetByPersonInfo,
													Optional<WorkTimeCode> siftCode,
													WorkingConditionItem conditionItem,
													Optional<HolidayAddtionSet> holidayAddtionSet){
		/*休日控除時間の計算*/
//		AttendanceTime forHolidayTime = calcHolidayDeductionTime(workType);
		/*代休控除時間の計算*/
		AttendanceTime forCompensatoryLeaveTime = calcSubstituteHoliday(workType,predetermineTimeSet,predetermineTimeSetByPersonInfo, siftCode,conditionItem,holidayAddtionSet);
		
		DeductionTime deductionTime = new DeductionTime(forCompensatoryLeaveTime,forCompensatoryLeaveTime);//(forHolidayTime,forCompensatoryLeaveTime);
		//DeductionTime deductionTime = new DeductionTime(new AttendanceTime(0),new AttendanceTime(0));//(forHolidayTime,forCompensatoryLeaveTime);
		//休日控除が算出できるようになったら、各代休控除時間に加算するように変える
		return new StatutoryDeductionForFlex(deductionTime.getForCompensatoryHolidayTime(),deductionTime.getForCompensatoryHolidayTime());
//		return new StatutoryDeductionForFlex(deductionTime.forLackCalcPredetermineDeduction(flexCalcMethod.getFlexCalcMethod())
//											,deductionTime.forPremiumCalcPredetermineDeduction(flexCalcMethod.getFlexCalcMethod()));
	}
	
	/**
	 * 就業時間の計算
	 * @param premiumAtr 割増区分
	 * @param calcActualTime 実働のみで計算するしない区分
	 * @param vacationClass 休暇クラス
	 * @param timevacationUseTimeOfDaily 休暇使用合計残時間未割当
	 * @param statutoryDivision 法定内区分
	 * @param workType 勤務種類
	 * @param predetermineTimeSet 計算用所定時間設定
	 * @param siftCode 就業時間帯コード
	 * @param autoCalcOfLeaveEarlySetting 遅刻早退の自動計算設定
	 * @param addSetting 加算設定
	 * @param holidayAddtionSet 休暇加算時間設定
	 * @param holidayCalcMethodSet 休暇の計算方法の設定
	 * @param calcMethod フレックス勤務の非勤務日の場合の計算方法
	 * @param flexAutoCalcAtr 時間外の自動計算区分
	 * @param flexCalcMethod フレックス勤務の設定
	 * @param preFlexTime 事前フレ
	 * @param coreTimeSetting コアタイム時間帯設定
	 * @param dailyUnit 法定労働時間
	 * @param commonSetting 就業時間帯の共通設定
	 * @param flexUpper 時間外の上限設定
	 * @param conditionItem 労働条件項目
	 * @param predetermineTimeSetByPersonInfo 計算用所定時間設定（個人）
	 * @param lateEarlyMinusAtr 遅刻早退控除する
	 * @param attendanceLeavingWork 日別勤怠の出退勤
	 * @return 就業時間
	 */
	public AttendanceTime calcWorkTime(
			PremiumAtr premiumAtr,
			CalcurationByActualTimeAtr calcActualTime,
			VacationClass vacationClass,
			AttendanceTime timevacationUseTimeOfDaily,
			StatutoryDivision statutoryDivision,
			WorkType workType,
			PredetermineTimeSetForCalc predetermineTimeSet,
			Optional<WorkTimeCode> siftCode,
			AutoCalcOfLeaveEarlySetting autoCalcOfLeaveEarlySetting,
			AddSetting addSetting,
			HolidayAddtionSet holidayAddtionSet,
			HolidayCalcMethodSet holidayCalcMethodSet,
			CalcMethodOfNoWorkingDayForCalc calcMethod, 
			AutoCalAtrOvertime flexAutoCalcAtr, //フレの上限時間設定
			SettingOfFlexWork flexCalcMethod,
			AttendanceTime preFlexTime,
			Optional<CoreTimeSetting> coreTimeSetting,
			DailyUnit dailyUnit,
			Optional<WorkTimezoneCommonSet> commonSetting,
			TimeLimitUpperLimitSetting flexUpper,//こいつは残さないといけない
			WorkingConditionItem conditionItem,
			Optional<PredetermineTimeSetForCalc> predetermineTimeSetByPersonInfo,
			NotUseAtr lateEarlyMinusAtr,
			Optional<TimeLeavingOfDailyAttd> attendanceLeavingWork) {
		
		//就業時間（法定内用）の計算//
		AttendanceTime withinTime = super.calcWorkTime(
				premiumAtr,
				vacationClass,
				timevacationUseTimeOfDaily,
				workType,
				predetermineTimeSet,
				siftCode,
				autoCalcOfLeaveEarlySetting,
				addSetting,
				holidayAddtionSet,
				holidayCalcMethodSet,
				dailyUnit,
				commonSetting,
				conditionItem,
				predetermineTimeSetByPersonInfo,
				coreTimeSetting,
				HolidayAdditionAtr.HolidayAddition.convertFromCalcByActualTimeToHolidayAdditionAtr(calcActualTime),
				lateEarlyMinusAtr,
				attendanceLeavingWork).getWorkTime();
		
		//フレックス時間の計算
		FlexTime flexTime = this.createWithinWorkTimeSheetAsFlex(
				calcMethod,
				addSetting.getVacationCalcMethodSet().getWorkTimeDeductFlexTime(),
				flexAutoCalcAtr,
				workType,
				flexCalcMethod,
				predetermineTimeSet,
				vacationClass,
				timevacationUseTimeOfDaily,
				statutoryDivision,
				siftCode,
				autoCalcOfLeaveEarlySetting,
				addSetting.getWorkTimeDeductFlexTime(),
				holidayAddtionSet,
				flexUpper,
				preFlexTime,
				dailyUnit,
				commonSetting,
				conditionItem,
				predetermineTimeSetByPersonInfo,
				coreTimeSetting,
				lateEarlyMinusAtr,
				Optional.of(DeductionAtr.Deduction),
				attendanceLeavingWork);
		
		AttendanceTime result = new AttendanceTime(0);
		if(flexTime.getFlexTime().getTime().greaterThan(0)) {
			result = withinTime.minusMinutes(flexTime.getFlexTime().getTime().valueAsMinutes());
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
	 * コア内外の外出時間の計算
	 * @param isWithin コア内外区分（true=コア内、false=コア外）
	 * @param dedAtr 控除区分
	 * @param roundAtr 丸め区分
	 * @return 外出時間
	 */
	public AttendanceTime calcOutingTimeInFlex(
			boolean isWithin,
			DeductionAtr dedAtr,
			TimeSheetRoundingAtr roundAtr) {
		
		// コアタイムとの重複を判断して時間帯を作成
		List<WithinWorkTimeFrame> targetFrameList = this.createSpanDuplicatedWithCoreTime(isWithin);
		// 控除時間の計算（私用外出）
		AttendanceTime privateGoOutTime = ActualWorkTimeSheetListService.calcDeductionTime(
				ConditionAtr.PrivateGoOut, dedAtr, roundAtr,
				targetFrameList.stream().map(t -> (ActualWorkingTimeSheet)t).collect(Collectors.toList()));
		// 控除時間の計算（有償外出）
		AttendanceTime compGoOutTime = ActualWorkTimeSheetListService.calcDeductionTime(
				ConditionAtr.CompesationGoOut, dedAtr, roundAtr,
				targetFrameList.stream().map(t -> (ActualWorkingTimeSheet)t).collect(Collectors.toList()));
		// 外出時間を返す
		return new AttendanceTime(privateGoOutTime.valueAsMinutes() + compGoOutTime.valueAsMinutes());
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
	
	//実働就業時間の計算
	public AttendanceTime calcActualWorkTime(PremiumAtr premiumAtr, 
									   CalcurationByActualTimeAtr calcActualTime,
									   VacationClass vacationClass,
									   AttendanceTime timevacationUseTimeOfDaily,
									   StatutoryDivision statutoryDivision,
									   WorkType workType,
									   PredetermineTimeSetForCalc predetermineTimeSet,
									   Optional<WorkTimeCode> siftCode,
									   AutoCalcOfLeaveEarlySetting autoCalcOfLeaveEarlySetting,
									   AddSetting addSetting,
									   HolidayAddtionSet holidayAddtionSet,
									   HolidayCalcMethodSet holidayCalcMethodSet,
									   CalcMethodOfNoWorkingDayForCalc calcMethod, 
									   AutoCalAtrOvertime flexAutoCalcAtr, //フレの上限時間設定
									   SettingOfFlexWork flexCalcMethod,
									   AttendanceTime preFlexTime,Optional<CoreTimeSetting> coreTimeSetting,
									   DailyUnit dailyUnit,Optional<WorkTimezoneCommonSet> commonSetting,
									   TimeLimitUpperLimitSetting flexUpper,//こいつは残さないといけない
									   WorkingConditionItem conditionItem,
									   Optional<PredetermineTimeSetForCalc> predetermineTimeSetByPersonInfo,
									   DeductLeaveEarly deductLeaveEarly,
									   NotUseAtr lateEarlyMinusAtr,
									   Optional<TimeLeavingOfDailyAttd> attendanceLeavingWork
			   ) {
		
		//実働のみ
		AttendanceTime withinTime = super.calcWorkTime(PremiumAtr.RegularWork,
													   vacationClass,
													   timevacationUseTimeOfDaily,
													   workType, 
													   predetermineTimeSet, 
													   siftCode, 
													   autoCalcOfLeaveEarlySetting,
													   addSetting.createCalculationByActualTime(),
													   holidayAddtionSet, 
													   holidayCalcMethodSet,
													   dailyUnit,commonSetting,
													   conditionItem,
													   predetermineTimeSetByPersonInfo,coreTimeSetting
													   ,HolidayAdditionAtr.HolidayAddition.convertFromCalcByActualTimeToHolidayAdditionAtr(CalcurationByActualTimeAtr.CALCULATION_BY_ACTUAL_TIME),
													   lateEarlyMinusAtr,
													   attendanceLeavingWork
													   ).getWorkTime();
		//休暇加算のマスタを見る
		FlexTime flexTime = this.createWithinWorkTimeSheetAsFlex(calcMethod, 
																 addSetting.getVacationCalcMethodSet().getWorkTimeDeductFlexTime(),
																 flexAutoCalcAtr, 
																 workType, 
																 flexCalcMethod, 
																 predetermineTimeSet, 
																 vacationClass, 
																 timevacationUseTimeOfDaily, 
																 statutoryDivision, 
																 siftCode, 
																 autoCalcOfLeaveEarlySetting,
																 addSetting.getWorkTimeDeductFlexTime(),
																 holidayAddtionSet, 
																 flexUpper,
																 preFlexTime,
																 dailyUnit,
																 commonSetting,
																 conditionItem,
																 predetermineTimeSetByPersonInfo,
																 coreTimeSetting,
																 NotUseAtr.USE,
																 Optional.of(DeductionAtr.Deduction),
																 attendanceLeavingWork);
		AttendanceTime result = new AttendanceTime(0);
		if(flexTime.getFlexTime().getTime().greaterThan(0)) {
			result = withinTime.minusMinutes(flexTime.getFlexTime().getTime().valueAsMinutes());
		}
		else {
			result = withinTime;
		}
		return result;
	}

	/**
	 * 所定内深夜時間の計算
	 * @param holidayCalcMethodSet 休暇の計算方法の設定
	 * @param autoCalcAtr 時間外の自動計算区分
	 * @param workType 勤務種類
	 * @param flexCalcMethod フレックス勤務の設定
	 * @param predetermineTimeSet 計算用所定時間設定
	 * @param vacationClass 休暇クラス
	 * @param timevacationUseTimeOfDaily 休暇使用合計残時間未割当
	 * @param statutoryDivision 法定内区分
	 * @param siftCode 就業時間帯コード
	 * @param autoCalcOfLeaveEarlySetting 遅刻早退の自動計算設定
	 * @param addSetting 加算設定
	 * @param holidayAddtionSet 休暇加算時間設定
	 * @param dailyUnit 法定労働時間
	 * @param commonSetting 就業時間帯の共通設定
	 * @param flexUpper 時間外の上限設定
	 * @param conditionItem 労働条件項目
	 * @param predetermineTimeSetByPersonInfo 計算用所定時間（個人）
	 * @param coreTimeSetting コアタイム時間帯設定
	 * @return 所定内深夜時間
	 */
	public AttendanceTime calcWithinMidnightTime(
			HolidayCalcMethodSet holidayCalcMethodSet,
			AutoCalAtrOvertime autoCalcAtr,
			WorkType workType,
			SettingOfFlexWork flexCalcMethod,
			PredetermineTimeSetForCalc predetermineTimeSet,
			VacationClass vacationClass,
			AttendanceTime timevacationUseTimeOfDaily,
			StatutoryDivision statutoryDivision,
			Optional<WorkTimeCode> siftCode,
			AutoCalcOfLeaveEarlySetting autoCalcOfLeaveEarlySetting,
			AddSetting addSetting,
			HolidayAddtionSet holidayAddtionSet,
			DailyUnit  dailyUnit,
			Optional<WorkTimezoneCommonSet> commonSetting,
			TimeLimitUpperLimitSetting flexUpper,//こいつは残さないとだめ,
			WorkingConditionItem conditionItem,
			Optional<PredetermineTimeSetForCalc> predetermineTimeSetByPersonInfo,
			Optional<CoreTimeSetting> coreTimeSetting,
			NotUseAtr lateEarlyMinusAtr,
			Optional<TimeLeavingOfDailyAttd> attendanceLeavingWork) {
		
		// フレックス深夜時間帯の作成
		FlexMidnightTimeSheet flexMidnightTimeSheet = FlexMidnightTimeSheet.create(
				this,
				holidayCalcMethodSet,
				autoCalcAtr,
				workType,
				flexCalcMethod,
				predetermineTimeSet,
				vacationClass,
				timevacationUseTimeOfDaily,
				statutoryDivision,
				siftCode,
				autoCalcOfLeaveEarlySetting,
				addSetting,
				holidayAddtionSet,
				dailyUnit,
				commonSetting,
				flexUpper,
				conditionItem,
				predetermineTimeSetByPersonInfo,
				coreTimeSetting,
				lateEarlyMinusAtr,
				attendanceLeavingWork);
		// 深夜時間を累計する　→　所定内深夜時間を返す
		return FlexMidnightTimeSheet.sumMidnightTime(flexMidnightTimeSheet.getWithin());
	}

	/**
	 * 所定外深夜時間の計算
	 * @param holidayCalcMethodSet 休暇の計算方法の設定
	 * @param autoCalcAtr 時間外の自動計算区分
	 * @param workType 勤務種類
	 * @param flexCalcMethod フレックス勤務の設定
	 * @param predetermineTimeSet 計算用所定時間設定
	 * @param vacationClass 休暇クラス
	 * @param timevacationUseTimeOfDaily 休暇使用合計残時間未割当
	 * @param statutoryDivision 法定内区分
	 * @param siftCode 就業時間帯コード
	 * @param autoCalcOfLeaveEarlySetting 遅刻早退の自動計算設定
	 * @param addSetting 加算設定
	 * @param holidayAddtionSet 休暇加算時間設定
	 * @param dailyUnit 法定労働時間
	 * @param commonSetting 就業時間帯の共通設定
	 * @param flexUpper 時間外の上限設定
	 * @param conditionItem 労働条件項目
	 * @param predetermineTimeSetByPersonInfo 計算用所定時間（個人）
	 * @param coreTimeSetting コアタイム時間帯設定
	 * @return 所定外深夜時間
	 */
	public AttendanceTime calcWithoutMidnightTime(
			HolidayCalcMethodSet holidayCalcMethodSet,
			AutoCalAtrOvertime autoCalcAtr,
			WorkType workType,
			SettingOfFlexWork flexCalcMethod,
			PredetermineTimeSetForCalc predetermineTimeSet,
			VacationClass vacationClass,
			AttendanceTime timevacationUseTimeOfDaily,
			StatutoryDivision statutoryDivision,
			Optional<WorkTimeCode> siftCode,
			AutoCalcOfLeaveEarlySetting autoCalcOfLeaveEarlySetting,
			AddSetting addSetting,
			HolidayAddtionSet holidayAddtionSet,
			DailyUnit  dailyUnit,
			Optional<WorkTimezoneCommonSet> commonSetting,
			TimeLimitUpperLimitSetting flexUpper,//こいつは残さないとだめ,
			WorkingConditionItem conditionItem,
			Optional<PredetermineTimeSetForCalc> predetermineTimeSetByPersonInfo,
			Optional<CoreTimeSetting> coreTimeSetting,
			NotUseAtr lateEarlyMinusAtr,
			Optional<TimeLeavingOfDailyAttd> attendanceLeavingWork) {
		
		// フレックス深夜時間帯の作成
		FlexMidnightTimeSheet flexMidnightTimeSheet = FlexMidnightTimeSheet.create(
				this,
				holidayCalcMethodSet,
				autoCalcAtr,
				workType,
				flexCalcMethod,
				predetermineTimeSet,
				vacationClass,
				timevacationUseTimeOfDaily,
				statutoryDivision,
				siftCode,
				autoCalcOfLeaveEarlySetting,
				addSetting,
				holidayAddtionSet,
				dailyUnit,
				commonSetting,
				flexUpper,
				conditionItem,
				predetermineTimeSetByPersonInfo,
				coreTimeSetting,
				lateEarlyMinusAtr,
				attendanceLeavingWork);
		// 深夜時間を累計する　→　所定外深夜時間を返す
		return FlexMidnightTimeSheet.sumMidnightTime(flexMidnightTimeSheet.getWithout());
	}
	
	/**
	 * 所定外開始時刻の計算
	 * @param holidayCalcMethodSet 休暇の計算方法の設定
	 * @param autoCalcAtr 時間外の自動計算区分
	 * @param workType 勤務種類
	 * @param flexCalcMethod フレックス勤務の設定
	 * @param predetermineTimeSet 計算用所定時間設定
	 * @param vacationClass 休暇クラス
	 * @param timevacationUseTimeOfDaily 休暇使用合計残時間未割当
	 * @param statutoryDivision 法定内区分
	 * @param siftCode 就業時間帯コード
	 * @param autoCalcOfLeaveEarlySetting 遅刻早退の自動計算設定
	 * @param addSetting 加算設定
	 * @param holidayAddtionSet 休暇加算時間設定
	 * @param dailyUnit 法定労働時間
	 * @param commonSetting 就業時間帯の共通設定
	 * @param flexUpper 時間外の上限設定
	 * @param conditionItem 労働条件項目
	 * @param predetermineTimeSetByPersonInfo 計算用所定時間（個人）
	 * @param coreTimeSetting コアタイム時間帯設定
	 * @return 所定外開始時刻
	 */
	public Optional<TimeWithDayAttr> calcWithoutStartTime(
			HolidayCalcMethodSet holidayCalcMethodSet,
			AutoCalAtrOvertime autoCalcAtr,
			WorkType workType,
			SettingOfFlexWork flexCalcMethod,
			PredetermineTimeSetForCalc predetermineTimeSet,
			VacationClass vacationClass,
			AttendanceTime timevacationUseTimeOfDaily,
			StatutoryDivision statutoryDivision,
			Optional<WorkTimeCode> siftCode,
			AutoCalcOfLeaveEarlySetting autoCalcOfLeaveEarlySetting,
			AddSetting addSetting,
			HolidayAddtionSet holidayAddtionSet,
			DailyUnit  dailyUnit,
			Optional<WorkTimezoneCommonSet> commonSetting,
			TimeLimitUpperLimitSetting flexUpper,//こいつは残さないとだめ,
			WorkingConditionItem conditionItem,
			Optional<PredetermineTimeSetForCalc> predetermineTimeSetByPersonInfo,
			Optional<CoreTimeSetting> coreTimeSetting,
			NotUseAtr lateEarlyMinusAtr,
			Optional<TimeLeavingOfDailyAttd> attendanceLeavingWork) {
		
		// フレックス時間の計算
		int flexMinutes = this.calcFlexTime(
				holidayCalcMethodSet,
				autoCalcAtr,
				workType,
				flexCalcMethod,
				predetermineTimeSet,
				vacationClass,
				timevacationUseTimeOfDaily,
				statutoryDivision,
				siftCode,
				autoCalcOfLeaveEarlySetting,
				addSetting,
				holidayAddtionSet,
				dailyUnit,
				commonSetting,
				flexUpper,
				conditionItem,
				predetermineTimeSetByPersonInfo,
				coreTimeSetting,
				lateEarlyMinusAtr,
				attendanceLeavingWork).getFlexTime().getTime().valueAsMinutes();
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
}
