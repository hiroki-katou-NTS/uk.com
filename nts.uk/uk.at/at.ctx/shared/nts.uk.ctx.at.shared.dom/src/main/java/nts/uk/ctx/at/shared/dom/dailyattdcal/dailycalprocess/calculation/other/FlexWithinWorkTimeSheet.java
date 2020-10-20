package nts.uk.ctx.at.shared.dom.dailyattdcal.dailycalprocess.calculation.other;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import lombok.Getter;
import lombok.val;
import nts.uk.ctx.at.shared.dom.PremiumAtr;
import nts.uk.ctx.at.shared.dom.common.time.AttendanceTime;
import nts.uk.ctx.at.shared.dom.common.time.AttendanceTimeOfExistMinus;
import nts.uk.ctx.at.shared.dom.ot.autocalsetting.AutoCalAtrOvertime;
import nts.uk.ctx.at.shared.dom.ot.autocalsetting.AutoCalcOfLeaveEarlySetting;
import nts.uk.ctx.at.shared.dom.ot.autocalsetting.TimeLimitUpperLimitSetting;
import nts.uk.ctx.at.shared.dom.scherec.addsettingofworktime.AddSetting;
import nts.uk.ctx.at.shared.dom.scherec.addsettingofworktime.CalcurationByActualTimeAtr;
import nts.uk.ctx.at.shared.dom.scherec.addsettingofworktime.DeductLeaveEarly;
import nts.uk.ctx.at.shared.dom.scherec.addsettingofworktime.HolidayAddtionSet;
import nts.uk.ctx.at.shared.dom.scherec.addsettingofworktime.HolidayCalcMethodSet;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.common.TimeDivergenceWithCalculationMinusExist;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.overtimehours.clearovertime.FlexTime;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.vacationusetime.VacationClass;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.workingstyle.flex.SettingOfFlexWork;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailycalprocess.calculation.TimeSpanForDailyCalc;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailycalprocess.calculation.timezone.deductiontime.TimeSheetOfDeductionItem;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailycalprocess.calculation.timezone.withinworkinghours.LateDecisionClock;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailycalprocess.calculation.timezone.withinworkinghours.LeaveEarlyDecisionClock;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailycalprocess.calculation.timezone.withinworkinghours.WithinWorkTimeFrame;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailycalprocess.calculation.timezone.withinworkinghours.WithinWorkTimeSheet;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.ortherpackage.classfunction.PredetermineTimeSetForCalc;
import nts.uk.ctx.at.shared.dom.scherec.statutory.worktime.week.DailyUnit;
import nts.uk.ctx.at.shared.dom.vacation.setting.addsettingofworktime.HolidayAdditionAtr;
import nts.uk.ctx.at.shared.dom.vacation.setting.addsettingofworktime.StatutoryDivision;
import nts.uk.ctx.at.shared.dom.workingcondition.WorkingConditionItem;
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

	public FlexWithinWorkTimeSheet(List<WithinWorkTimeFrame> withinWorkTimeFrame,List<TimeSheetOfDeductionItem> shortTimeSheets,Optional<TimeSpanForDailyCalc> coreTimeSheet) {
		super(withinWorkTimeFrame,shortTimeSheets,Optional.of(new LateDecisionClock(new TimeWithDayAttr(0), 1)),Optional.of(new LeaveEarlyDecisionClock(new TimeWithDayAttr(0), 1)));
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
	 * @param coreTimeSetting
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
			NotUseAtr lateEarlyMinusAtr) {
		
		//フレックス時間の基準となる所定労働時間
		FlexTime flexTime = new FlexTime(TimeDivergenceWithCalculationMinusExist.sameTime(new AttendanceTimeOfExistMinus(0)),new AttendanceTime(0));
		
		//1日休日 or 1日休出  or 1日振休
		if(workType.getDailyWork().isOneDayHoliday() || workType.getDailyWork().isHolidayWork() || workType.getDailyWork().isPause())
			return flexTime;
		//フレックス計算しない　and 非勤務日
		
		if(!calcMethod.isCalclateFlexTime() && workType.getDailyWork().getAttendanceHolidayAttr().isHoliday()) 
				return flexTime; 
		/*フレックス時間の計算*/
		AttendanceTimeOfExistMinus calcflexTime = calcFlexTime(
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
				lateEarlyMinusAtr).getFlexTime();
		/*事前申請を上限とする制御*/
		AttendanceTimeOfExistMinus afterLimitFlexTime = decisionLimit(flexLimitSetting,calcflexTime,preAppTime);
		
		return new FlexTime(TimeDivergenceWithCalculationMinusExist.sameTime(afterLimitFlexTime),new AttendanceTime(0));
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
			NotUseAtr lateEarlyMinusAtr) {
		
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
				lateEarlyMinusAtr);
		
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
				lateEarlyMinusAtr);
		
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
		
		if((!autoCalcAtr.isCalculateEmbossing()) && flexTime.greaterThan(0)) {
			flexTime = new AttendanceTimeOfExistMinus(0);
		}
		
		return new CalcFlexTime(flexTime,vacationAddTime);
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
			NotUseAtr lateEarlyMinusAtr) {
		
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
				lateEarlyMinusAtr).getWorkTime();
		
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
				lateEarlyMinusAtr);
		
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
	 * コアタイム内外を分けて計算
	 * @param isWithin
	 * @return
	 */
	public AttendanceTime calcOutingTimeInFlex(boolean isWithin) {
		val a = this.getCoreTimeSheet();
		AttendanceTime returnValue = new AttendanceTime(0);
		if(a.isPresent()) {
			for(WithinWorkTimeFrame b : this.getWithinWorkTimeFrame()) {
				if(isWithin) {
					//コア内外出時間の計算
					val dupRange = a.get().getDuplicatedWith(b.getTimeSheet());
					if(dupRange.isPresent()) {
						returnValue = new AttendanceTime(b.getDeductionTimeSheet().stream()
								.map(tc -> tc.replaceTimeSpan(dupRange))
								.filter(tc -> tc.getGoOutReason().isPresent())
								.filter(tc -> tc.getGoOutReason().get().isPrivate()
										 || tc.getGoOutReason().get().isCompensation())
								.map(tc -> tc.calcTotalTime().valueAsMinutes())
								.collect(Collectors.summingInt(tc -> tc)));
					}
				}
				else {
//					//コア外外出時間の計算
					val dupRangeList = a.get().getNotDuplicationWith(b.getTimeSheet());
					for(TimeSpanForDailyCalc newSpan : dupRangeList) {
						returnValue = new AttendanceTime(b.getDeductionTimeSheet().stream()
								.map(tc -> tc.replaceTimeSpan(Optional.of(newSpan)))
								.filter(tc -> tc.getGoOutReason().isPresent())
								.filter(tc -> tc.getGoOutReason().get().isPrivate()
										|| tc.getGoOutReason().get().isCompensation())
								.map(tc -> tc.calcTotalTime().valueAsMinutes())
								.collect(Collectors.summingInt(tc -> tc)));
					}
				}
			}
		}
		return returnValue;

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
									   NotUseAtr lateEarlyMinusAtr
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
													   lateEarlyMinusAtr
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
																 NotUseAtr.USE);
		AttendanceTime result = new AttendanceTime(0);
		if(flexTime.getFlexTime().getTime().greaterThan(0)) {
			result = withinTime.minusMinutes(flexTime.getFlexTime().getTime().valueAsMinutes());
		}
		else {
			result = withinTime;
		}
		return result;
	}

	
	
}
