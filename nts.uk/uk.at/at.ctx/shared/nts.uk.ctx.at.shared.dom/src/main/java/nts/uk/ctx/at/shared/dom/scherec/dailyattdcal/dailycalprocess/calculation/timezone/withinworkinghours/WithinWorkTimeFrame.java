package nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailycalprocess.calculation.timezone.withinworkinghours;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import lombok.Getter;
import lombok.Setter;
import nts.arc.error.BusinessException;
import nts.arc.error.RawErrorMessage;
import nts.uk.ctx.at.shared.dom.PremiumAtr;
import nts.uk.ctx.at.shared.dom.common.time.AttendanceTime;
import nts.uk.ctx.at.shared.dom.common.timerounding.Rounding;
import nts.uk.ctx.at.shared.dom.common.timerounding.TimeRoundingSetting;
import nts.uk.ctx.at.shared.dom.common.timerounding.Unit;
import nts.uk.ctx.at.shared.dom.scherec.addsettingofworktime.AddSetting;
import nts.uk.ctx.at.shared.dom.scherec.addsettingofworktime.CalcurationByActualTimeAtr;
import nts.uk.ctx.at.shared.dom.scherec.addsettingofworktime.HolidayAddtionSet;
import nts.uk.ctx.at.shared.dom.scherec.addsettingofworktime.HolidayCalcMethodSet;
import nts.uk.ctx.at.shared.dom.scherec.addsettingofworktime.WorkDeformedLaborAdditionSet;
import nts.uk.ctx.at.shared.dom.scherec.addsettingofworktime.WorkFlexAdditionSet;
import nts.uk.ctx.at.shared.dom.scherec.addsettingofworktime.WorkRegularAdditionSet;
import nts.uk.ctx.at.shared.dom.scherec.addsettingofworktime.WorkTimeCalcMethodDetailOfHoliday;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.autocalsetting.ActualWorkTimeSheetAtr;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.autocalsetting.AutoCalcOfLeaveEarlySetting;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.attendancetime.TimeLeavingWork;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.breakouting.ConditionAtr;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.dailyattendancework.IntegrationOfDaily;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailycalprocess.calculation.ActualWorkingTimeSheet;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailycalprocess.calculation.DeductionOffSetTime;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailycalprocess.calculation.DeductionTimeSheet;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailycalprocess.calculation.ManagePerCompanySet;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailycalprocess.calculation.TimeSpanForDailyCalc;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailycalprocess.calculation.other.ManagePerPersonDailySet;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailycalprocess.calculation.timezone.CalculationTimeSheet;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailycalprocess.calculation.timezone.deductiontime.DeductionAtr;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailycalprocess.calculation.timezone.deductiontime.TimeSheetOfDeductionItem;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailycalprocess.calculation.timezone.someitems.BonusPayTimeSheetForCalc;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.midnighttimezone.MidNightTimeSheetForCalc;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.ortherpackage.classfunction.PredetermineTimeSetForCalc;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.ortherpackage.classfunction.SpecBonusPayTimeSheetForCalc;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.ortherpackage.classfunction.TimevacationUseTimeOfDaily;
import nts.uk.ctx.at.shared.dom.vacation.setting.addsettingofworktime.HolidayAdditionAtr;
import nts.uk.ctx.at.shared.dom.workingcondition.WorkingSystem;
import nts.uk.ctx.at.shared.dom.worktime.IntegrationOfWorkTime;
import nts.uk.ctx.at.shared.dom.worktime.common.EmTimeFrameNo;
import nts.uk.ctx.at.shared.dom.worktime.common.EmTimeZoneSet;
import nts.uk.ctx.at.shared.dom.worktime.common.LateEarlyAtr;
import nts.uk.ctx.at.shared.dom.worktime.common.OtherEmTimezoneLateEarlySet;
import nts.uk.ctx.at.shared.dom.worktime.common.TimeZoneRounding;
import nts.uk.ctx.at.shared.dom.worktime.common.WorkTimezoneCommonSet;
import nts.uk.ctx.at.shared.dom.worktime.flexset.CoreTimeSetting;
import nts.uk.ctx.at.shared.dom.worktime.flexset.TimeSheet;
import nts.uk.ctx.at.shared.dom.worktime.flowset.FlowWorkRestTimezone;
import nts.uk.ctx.at.shared.dom.worktime.predset.TimezoneUse;
import nts.uk.ctx.at.shared.dom.worktype.WorkType;
import nts.uk.shr.com.enumcommon.NotUseAtr;
import nts.uk.shr.com.time.TimeWithDayAttr;

/**
 * 就業時間内時間枠
 * @author keisuke_hoshina
 *
 */
@Getter
public class WithinWorkTimeFrame extends ActualWorkingTimeSheet {

	//就業時間枠No
	private final EmTimeFrameNo workingHoursTimeNo;
	
	/** 遅刻早退控除前時間帯　※時間帯作成では使用しない */
	private TimeSpanForDailyCalc beforeLateEarlyTimeSheet;
	
	//遅刻時間帯・・・deductByLateLeaveEarlyを呼ぶまでは値が無い
	private Optional<LateTimeSheet> lateTimeSheet;
	//早退時間帯・・・deductByLateLeaveEarlyを呼ぶまでは値が無い
	private Optional<LeaveEarlyTimeSheet> leaveEarlyTimeSheet;
	//計算用所定内割増時間
	@Setter
	private Optional<WithinPremiumTimeSheetForCalc> premiumTimeSheetInPredetermined;
	//遅刻休暇使用残時間
	@Setter
	private Optional<TimevacationUseTimeOfDaily> lateVacationUseTime;
	//早退休暇使用残時間
	@Setter
	private Optional<TimevacationUseTimeOfDaily> leaveEarlyVacationUseTime;
	
	/**
	 * constructor
	 * @param workingHoursTimeNo
	 * @param timeSheet
	 * @param calculationTimeSheet
	 */
	public WithinWorkTimeFrame(
			EmTimeFrameNo workingHoursTimeNo,
			TimeSpanForDailyCalc timeSheet,
			TimeSpanForDailyCalc beforeLateEarlyTimeSheet,
			TimeRoundingSetting rounding,
			List<TimeSheetOfDeductionItem> recorddeductionTimeSheets,
			List<TimeSheetOfDeductionItem> deductionTimeSheets,
			List<BonusPayTimeSheetForCalc> bonusPayTimeSheet,
			Optional<MidNightTimeSheetForCalc> midNighttimeSheet,
			List<SpecBonusPayTimeSheetForCalc> specifiedBonusPayTimeSheet,
			Optional<LateTimeSheet> lateTimeSheet,
			Optional<LeaveEarlyTimeSheet> leaveEarlyTimeSheet) {
		
		super(timeSheet, rounding, recorddeductionTimeSheets,deductionTimeSheets,bonusPayTimeSheet,specifiedBonusPayTimeSheet,midNighttimeSheet);
		this.workingHoursTimeNo = workingHoursTimeNo;
		this.beforeLateEarlyTimeSheet = beforeLateEarlyTimeSheet;
		this.lateTimeSheet = lateTimeSheet;
		this.leaveEarlyTimeSheet = leaveEarlyTimeSheet;
		this.premiumTimeSheetInPredetermined = Optional.empty();
		this.lateVacationUseTime = Optional.empty();
		this.leaveEarlyVacationUseTime = Optional.empty();
	}

	
	
	/**
	 * 空で作成する
	 * @return就業時間内時間枠
	 */
	public static WithinWorkTimeFrame createEmpty() {
		return new WithinWorkTimeFrame(
				new EmTimeFrameNo(1),
				new TimeSpanForDailyCalc(new TimeWithDayAttr(0), new TimeWithDayAttr(0)),
				new TimeSpanForDailyCalc(new TimeWithDayAttr(0), new TimeWithDayAttr(0)),
				new TimeRoundingSetting(Unit.ROUNDING_TIME_1MIN, Rounding.ROUNDING_DOWN),
				Collections.emptyList(),
				Collections.emptyList(),
				Collections.emptyList(),
				Optional.empty(),
				Collections.emptyList(),
				Optional.empty(),
				Optional.empty());
	}
	
	
	public TimeSpanForDailyCalc getTimeSheet() {
		return this.timeSheet;
	}
	
	
	

	/**
	 * 遅刻時間帯の作成
	 * @param timeLeavingWork 出退勤
	 * @param otherEmTimezoneLateEarlySet 就業時間帯の遅刻・早退別設定
	 * @param lateDesClock 遅刻判断時刻
	 * @param duplicateTimeSheet 就業時間内時間枠
	 * @param deductionTimeSheet 控除時間帯
	 * @param breakTimeFromMaster 控除項目の時間帯(List) ※就業時間帯マスタから取得した休憩時間帯（大塚用）
	 * @param workNo 勤務No
	 * @param predetermineTime 所定時間帯
	 * @param workType 勤務種類
	 * @param predetermineTimeForSet 計算用就業時間設定
	 * @param integrationOfWorkTime 統合就業時間帯
	 * @return 遅刻時間帯
	 */
	public static LateTimeSheet createLateTimeSheet(
			TimeLeavingWork timeLeavingWork,
			OtherEmTimezoneLateEarlySet otherEmTimezoneLateEarlySet,
			Optional<LateDecisionClock> lateDesClock,
			WithinWorkTimeFrame duplicateTimeSheet,
			DeductionTimeSheet deductionTimeSheet,
			List<TimeSheetOfDeductionItem> breakTimeFromMaster,
			int workNo,
			Optional<TimezoneUse> predetermineTime,
			WorkType workType,
			PredetermineTimeSetForCalc predetermineTimeForSet,
			IntegrationOfWorkTime integrationOfWorkTime) {

		if(integrationOfWorkTime.getWorkTimeSetting().getWorkTimeDivision().isFlex() && !integrationOfWorkTime.getFlexWorkSetting().get().getCoreTimeSetting().isUseTimeSheet())
			return LateTimeSheet.createAsNotLate();
		
		//遅刻時間帯の作成
		LateTimeSheet latetimesheet = LateTimeSheet.createLateTimeSheet(
				lateDesClock,
				timeLeavingWork,
				otherEmTimezoneLateEarlySet,
				duplicateTimeSheet,
				deductionTimeSheet,
				breakTimeFromMaster,
				predetermineTime,
				workNo,
				workType,
				predetermineTimeForSet,
				integrationOfWorkTime);
	
		return latetimesheet;
	}
	

	/**
	 * 早退時間帯の作成
	 * @param timeLeavingWork 出退勤
	 * @param otherEmTimezoneLateEarlySet 就業時間帯の遅刻・早退別設定
	 * @param leaveEarlyDesClock 早退判断時刻
	 * @param duplicateTimeSheet 就業時間内時間枠
	 * @param deductionTimeSheet 控除時間帯
	 * @param breakTimeFromMaster 控除項目の時間帯(List) ※就業時間帯マスタから取得した休憩時間帯（大塚用）
	 * @param workNo 勤務No
	 * @param predetermineTimeSet 所定時間帯
	 * @param workType 勤務種類
	 * @param predetermineTimeForSet 計算用就業時間設定
	 * @param integrationOfWorkTime 統合就業時間帯
	 * @return
	 */
	public static LeaveEarlyTimeSheet createLeaveEarlyTimeSheet(
			TimeLeavingWork timeLeavingWork,
			OtherEmTimezoneLateEarlySet otherEmTimezoneLateEarlySet,
			Optional<LeaveEarlyDecisionClock> leaveEarlyDesClock,
			WithinWorkTimeFrame duplicateTimeSheet,
			DeductionTimeSheet deductionTimeSheet,
			List<TimeSheetOfDeductionItem> breakTimeFromMaster,
			int workNo,
			Optional<TimezoneUse> predetermineTimeSet,
			WorkType workType,
			PredetermineTimeSetForCalc predetermineTimeForSet,
			IntegrationOfWorkTime integrationOfWorkTime) {
		
		//早退時間帯の作成
		LeaveEarlyTimeSheet leaveEarlytimesheet = LeaveEarlyTimeSheet.createLeaveEarlyTimeSheet(
				leaveEarlyDesClock,
				timeLeavingWork,
				otherEmTimezoneLateEarlySet,
				duplicateTimeSheet,
				deductionTimeSheet,
				breakTimeFromMaster,
				predetermineTimeSet,
				workNo,
				workType,
				predetermineTimeForSet,
				integrationOfWorkTime);
	
		return leaveEarlytimesheet;
	}


//
//	@Override
//	public LateTimeSheet getLateTimeSheet() {
//		return this.lateTimeSheet.get();
//	}
//
//	@Override
//	public LeaveEarlyTimeSheet getLeaveEarlyTimeSheet() {
//		return this.leaveEarlyTimeSheet.get();
//	}
	
	/**
	 * 実働時間を求め、就業時間を計算する
	 * アルゴリズム：時間帯単位の計算処理
	 * @param holidayAdditionAtr 休暇加算区分
	 * @param timevacationUseTimeOfDaily 休暇使用合計残時間未割当
	 * @param autoCalcOfLeaveEarlySetting 遅刻早退の自動計算設定
	 * @param addSetting 加算設定
	 * @param holidayAddtionSet 休暇加算時間設定
	 * @param holidayCalcMethodSet 休暇の計算方法の設定
	 * @param premiumAtr 割増区分
	 * @param commonSetting 就業時間帯の共通設定
	 * @param lateEarlyMinusAtr 遅刻早退控除する
	 * @return 就業時間
	 */
	public AttendanceTime calcActualWorkTimeAndWorkTime(
			HolidayAdditionAtr holidayAdditionAtr,
			AttendanceTime timevacationUseTimeOfDaily,
			AutoCalcOfLeaveEarlySetting autoCalcOfLeaveEarlySetting,
			AddSetting addSetting,
			HolidayAddtionSet holidayAddtionSet,
			HolidayCalcMethodSet holidayCalcMethodSet,
			PremiumAtr premiumAtr,
			Optional<WorkTimezoneCommonSet> commonSetting,
			NotUseAtr lateEarlyMinusAtr) {
		
		//就業時間の計算
		AttendanceTime actualTime = calcActualTime(holidayCalcMethodSet,premiumAtr);

		AttendanceTime workTime = calcWorkTime(actualTime);
		/*就業時間算出ロジックをここに*/
		
		//控除時間、時間休暇で相殺した時間を計算  控除時間帯が存在する前提で動いていたため、控除時間帯が無かったらオールゼロで修正
		DeductionOffSetTime timeVacationOffSetTime = new DeductionOffSetTime(new AttendanceTime(0),new AttendanceTime(0),new AttendanceTime(0),new AttendanceTime(0));

		//遅刻、早退時間を就業時間から控除
		AttendanceTime lateEarlyDiductionTime = this.getLateEarlyDiductionTime(
				autoCalcOfLeaveEarlySetting,
				holidayCalcMethodSet,
				premiumAtr,
				commonSetting,
				lateEarlyMinusAtr);
		workTime = new AttendanceTime(workTime.valueAsMinutes() - lateEarlyDiductionTime.valueAsMinutes());
		
		//時間休暇使用の残時間を計算 
		if(holidayAdditionAtr.isHolidayAddition()) {
			//就業時間に加算する時間休暇を就業時間へ加算     
			workTime = new AttendanceTime(workTime.valueAsMinutes() + calcTimeVacationAddTime(holidayAddtionSet,
																							addSetting.getCalculationByActualTimeAtr(PremiumAtr.RegularWork),
																							timeVacationOffSetTime).valueAsMinutes());
		}
		//丸め処理
		TimeRoundingSetting rounding = this.getRounding();
		workTime = new AttendanceTime(rounding.round(workTime.valueAsMinutes()));
						
		return workTime;
	}
	
	/**
	 * 遅刻、早退時間を就業時間から控除
	 * @param autoCalcOfLeaveEarlySetting 遅刻早退の自動計算設定
	 * @param holidayCalcMethodSet 休暇の計算方法の設定
	 * @param premiumAtr 割増区分
	 * @param commonSetting 就業時間帯の共通設定
	 * @param lateEarlyMinusAtr 遅刻早退を強制的に控除する true:強制的に控除する  false:強制的に控除しない（設定を参照する）
	 * @return 遅刻早退控除時間
	 */
	private AttendanceTime getLateEarlyDiductionTime(
			AutoCalcOfLeaveEarlySetting autoCalcOfLeaveEarlySetting,
			HolidayCalcMethodSet holidayCalcMethodSet,
			PremiumAtr premiumAtr,
			Optional<WorkTimezoneCommonSet> commonSetting,
			NotUseAtr lateEarlyMinusAtr) {
		
		if(!this.isDeductLateLeaveEarly(premiumAtr, holidayCalcMethodSet, commonSetting) && lateEarlyMinusAtr.equals(NotUseAtr.NOT_USE))
			//設定を参照して「控除しない」となった場合、かつ、「強制的に控除する＝しない」の場合は0
			return AttendanceTime.ZERO;
		
		//遅刻控除時間を計算
		int lateDeductTime = 0;
		if(this.lateTimeSheet.isPresent() && this.lateTimeSheet.get().getForDeducationTimeSheet().isPresent()) {
			
			//遅刻時間帯と自身が重複している時間
			Optional<TimeSpanForDailyCalc> lateDupulicateTime
					= this.lateTimeSheet.get().getForDeducationTimeSheet().get().getTimeSheet().getDuplicatedWith(this.beforeLateEarlyTimeSheet);
			
			if(lateDupulicateTime.isPresent()) {
				//就業時間内時間枠の時間帯と重複している遅刻時間帯のみを控除する
				this.lateTimeSheet.get().getForDeducationTimeSheet().get().shiftTimeSheet(lateDupulicateTime.get());
				lateDeductTime = this.lateTimeSheet.get().calcDedctionTime(autoCalcOfLeaveEarlySetting.isLate(), NotUseAtr.USE).getTime().valueAsMinutes();
			}
		}
		
		//早退控除時間を計算
		int leaveEarlyDeductTime = 0;
		if(this.leaveEarlyTimeSheet.isPresent() && this.leaveEarlyTimeSheet.get().getForDeducationTimeSheet().isPresent()) {
			
			//早退時間帯と自身が重複している時間
			Optional<TimeSpanForDailyCalc> leaveEarlyDupulicateTime 
					= this.leaveEarlyTimeSheet.get().getForDeducationTimeSheet().get().getTimeSheet().getDuplicatedWith(this.timeSheet);
			
			if(leaveEarlyDupulicateTime.isPresent()) {
				//就業時間内時間枠の時間帯と重複している早退時間帯のみを控除する
				this.leaveEarlyTimeSheet.get().getForDeducationTimeSheet().get().shiftTimeSheet(leaveEarlyDupulicateTime.get());
				leaveEarlyDeductTime = this.leaveEarlyTimeSheet.get().calcDedctionTime(autoCalcOfLeaveEarlySetting.isLeaveEarly(), NotUseAtr.USE).getTime().valueAsMinutes();
			}
		}
		int lateLeaveEarlySubtraction = lateDeductTime + leaveEarlyDeductTime;
		return new AttendanceTime(lateLeaveEarlySubtraction);
	}
	
	/**
	 * 就業時間を計算する
	 * @param actualTime
	 * @return　就業時間
	 */
	public AttendanceTime calcWorkTime(AttendanceTime actualTime) {
		return actualTime;
	}
	
	/**
	 * 実働時間を計算する
	 * @return　実働時間
	 */
	public AttendanceTime calcActualTime(HolidayCalcMethodSet holidayCalcMethodSet,PremiumAtr premiumAtr) {
//		return new AttendanceTime(((CalculationTimeSheet)this).getCalcrange().lengthAsMinutes());	
//		TimeZoneRounding a = ((CalculationTimeSheet)this).getTimeSheet();
//		TimeSpanForDailyCalc b = a.timeSpan();
//		AttendanceTime result = ((CalculationTimeSheet)this).calcTotalTime();/*.getTimeSheet().timeSpan().lengthAsMinutes());*/		
		//開始～終了の間の時間を計算する
		AttendanceTime result = new AttendanceTime(this.beforeLateEarlyTimeSheet.getTimeSpan().lengthAsMinutes());
		//控除時間を控除する
		result =  result.minusMinutes(this.calcDeductionTime(holidayCalcMethodSet, premiumAtr).valueAsMinutes());
		//丸め処理
		result = new AttendanceTime(this.rounding.round(result.valueAsMinutes()));
		//溢れ時間を加算する
		result.addMinutes(this.timeVacationOverflowTime.orElse(AttendanceTime.ZERO).valueAsMinutes());
		return result;
	}
	
	//控除時間の計算
	public AttendanceTime calcDeductionTime(HolidayCalcMethodSet holidayCalcMethodSet,PremiumAtr premiumAtr) {
		AttendanceTime result = new AttendanceTime(0);
		//休憩
		result = result.addMinutes(((CalculationTimeSheet)this).calcDedTimeByAtr(DeductionAtr.Deduction,ConditionAtr.BREAK).valueAsMinutes());
		//外出(私用)
		result = result.addMinutes(((CalculationTimeSheet)this).calcDedTimeByAtr(DeductionAtr.Deduction,ConditionAtr.PrivateGoOut).valueAsMinutes());
		//外出(公用)
		result = result.addMinutes(((CalculationTimeSheet)this).calcDedTimeByAtr(DeductionAtr.Deduction,ConditionAtr.PublicGoOut).valueAsMinutes());
		//短時間
		AttendanceTime shortTime = new AttendanceTime(0);
		//介護
		AttendanceTime careTime = new AttendanceTime(0);
		//短時間勤務を控除するか判断
		if(premiumAtr.isRegularWork() && !holidayCalcMethodSet.getWorkTimeCalcMethodOfHoliday().isCalculateIncludCareTime()) {
			shortTime = ((CalculationTimeSheet)this).calcDedTimeByAtr(DeductionAtr.Deduction,ConditionAtr.Child);
			careTime = ((CalculationTimeSheet)this).calcDedTimeByAtr(DeductionAtr.Deduction,ConditionAtr.Care);
		}
		if(premiumAtr.isPremium() && !holidayCalcMethodSet.getPremiumCalcMethodOfHoliday().isCalculateIncludCareTime()) {
			shortTime = ((CalculationTimeSheet)this).calcDedTimeByAtr(DeductionAtr.Deduction,ConditionAtr.Child);
			careTime = ((CalculationTimeSheet)this).calcDedTimeByAtr(DeductionAtr.Deduction,ConditionAtr.Care);
		}
		result = result.addMinutes(shortTime.valueAsMinutes());
		result = result.addMinutes(careTime.valueAsMinutes());
		return result;
	}
	
	//＊＊＊＊＊＊＊＊＊＊＊＊＊＊＊＊＊＊＊＊＊＊＊＊＊＊＊＊＊＊＊＊＊＊＊＊＊＊＊＊＊＊＊＊＊＊＊＊＊＊＊↓高須
	
//	/**
//	 * 計算範囲を判断（流動）　　流動時の就業時間内時間枠
//	 */
//	public void createWithinWorkTimeFrameForFluid(
//			AttendanceLeavingWork attendanceLeavingWork,
//			DailyWork dailyWork,
//			PredetermineTimeSetForCalc predetermineTimeSetForCalc) {
//		TimeSpanForDailyCalc timeSheet = new TimeSpanForDailyCalc(
//				attendanceLeavingWork.getAttendance().getEngrave().getTimesOfDay(),
//				attendanceLeavingWork.getLeaveWork().getEngrave().getTimesOfDay());
//		this.correctTimeSheet(dailyWork, timeSheet, predetermineTimeSetForCalc);
//	}
//	
//	/**
//	 * 勤務の単位を基に時間帯の開始、終了を補正
//	 * @param dailyWork 1日の勤務
//	 */
//	public WithinWorkTimeFrame correctTimeSheet(
//			DailyWork dailyWork,
//			TimeSpanForDailyCalc timeSheet,
//			PredetermineTimeSetForCalc predetermineTimeSetForCalc) {
//		
//		//丸め設定を作成
//		Finally<TimeRoundingSetting> rounding = Finally.of(new TimeRoundingSetting(Unit.ROUNDING_TIME_1MIN,Rounding.ROUNDING_DOWN));
//		
//		if (dailyWork.getAttendanceHolidayAttr().isHalfDayWorking()) {
//			TimeSpanForDailyCalc timeSheetForRounding = this.getHalfDayWorkingTimeSheetOf(dailyWork.getAttendanceHolidayAttr(),timeSheet,predetermineTimeSetForCalc);
//			TimeSpanWithRounding calcRange = new TimeSpanWithRounding(timeSheetForRounding.getStart(),timeSheetForRounding.getEnd(),rounding);
//			CalculationTimeSheet t = new CalculationTimeSheet(calcRange,timeSheetForRounding,Optional.empty());
//			return new WithinWorkTimeFrame(workingHoursTimeNo,timeSheet,t);
//		}
//	}
	
	
	/**
	 * 時間休暇からの加算時間を取得
	 * @author ken_takasu
	 * 
	 * @return
	 */
	public AttendanceTime calcTimeVacationAddTime(HolidayAddtionSet holidayAddtionSet,
												  CalcurationByActualTimeAtr calculationByActualTimeAtr,
												  DeductionOffSetTime timeVacationOffSetTime
												  ) {
		AttendanceTime addTime = new AttendanceTime(0);
		//実働のみで計算するかチェックする
		if(calculationByActualTimeAtr.isCalclationByActualTime()) {
			//年休分を加算するかチェックする
			if(holidayAddtionSet.getAdditionVacationSet().getAnnualHoliday()==NotUseAtr.USE) {
				addTime = new AttendanceTime(addTime.valueAsMinutes() + timeVacationOffSetTime.getAnnualLeave().valueAsMinutes());
			}
			//積立年休分を含めて求めるかチェックする
			if(holidayAddtionSet.getAdditionVacationSet().getYearlyReserved()==NotUseAtr.USE) {
				addTime = new AttendanceTime(addTime.valueAsMinutes() + timeVacationOffSetTime.getSixtyHourHoliday().valueAsMinutes());
			}
			//特別休暇分を含めて求めるかチェックする
			if(holidayAddtionSet.getAdditionVacationSet().getSpecialHoliday()==NotUseAtr.USE) {
				addTime = new AttendanceTime(addTime.valueAsMinutes() + timeVacationOffSetTime.getSpecialHoliday().valueAsMinutes());
			}
		}
		return addTime;
	}
	
	/**
	 * 時間休暇相殺時間を修行時間に含めるか判断する
	 * @author ken_takasu
	 * @param workingSystem
	 * @param addSettingOfRegularWork
	 * @param addSettingOfIrregularWork
	 * @param addSettingOfFlexWork
	 * @return
	 */
	public CalcurationByActualTimeAtr getCalculationByActualTimeAtr(WorkingSystem workingSystem,
																	WorkRegularAdditionSet addSettingOfRegularWork,
																	WorkDeformedLaborAdditionSet addSettingOfIrregularWork, 
																	WorkFlexAdditionSet addSettingOfFlexWork) {
		
		switch (workingSystem) {
		case REGULAR_WORK:
			if(addSettingOfRegularWork == null){
				return CalcurationByActualTimeAtr.CALCULATION_OTHER_THAN_ACTUAL_TIME;
			}
			return addSettingOfRegularWork.getVacationCalcMethodSet().getWorkTimeCalcMethodOfHoliday().getCalculateActualOperation();
			
		case FLEX_TIME_WORK:
			if(addSettingOfFlexWork == null) {
				return CalcurationByActualTimeAtr.CALCULATION_OTHER_THAN_ACTUAL_TIME;
			}
			return addSettingOfFlexWork.getVacationCalcMethodSet().getPremiumCalcMethodOfHoliday().getCalculateActualOperation();

		case VARIABLE_WORKING_TIME_WORK:
			if(addSettingOfIrregularWork == null) {
				return CalcurationByActualTimeAtr.CALCULATION_OTHER_THAN_ACTUAL_TIME;
			}
			return addSettingOfIrregularWork.getVacationCalcMethodSet().getWorkTimeCalcMethodOfHoliday().getCalculateActualOperation();

		case EXCLUDED_WORKING_CALCULATE:
			return CalcurationByActualTimeAtr.CALCULATION_BY_ACTUAL_TIME;
		default:
			throw new BusinessException(new RawErrorMessage("不正な労働制です"));
		}
	}


	/**
	 * 就業時間内時間枠毎の処理
	 * @param companyCommonSetting 会社別設定管理
	 * @param personDailySetting 社員設定管理
	 * @param todayWorkType 勤務種類
	 * @param integrationOfWorkTime 統合就業時間帯
	 * @param integrationOfDaily 日別実績(Work)
	 * @param timeLeavingWork 出退勤
	 * @param duplicateTimeSheet 就業時間内時間枠
	 * @param deductionTimeSheet 控除時間帯
	 * @param breakTimeFromMaster 控除項目の時間帯(List) ※就業時間帯マスタから取得した休憩時間帯（大塚用）
	 * @param predetermineTimeSetForCalc 計算用所定時間設定
	 * @param lateDesClock 遅刻判断時刻
	 * @param leaveEarlyDesClock 早退判断時刻
	 * @param isLastIndex ループの最後である
	 * @return 就業時間内時間枠
	 */
	public static WithinWorkTimeFrame createWithinWorkTimeFrame(
			ManagePerCompanySet companyCommonSetting,
			ManagePerPersonDailySet personDailySetting,
			WorkType todayWorkType,
			IntegrationOfWorkTime integrationOfWorkTime,
			IntegrationOfDaily integrationOfDaily,
			TimeLeavingWork timeLeavingWork,
			WithinWorkTimeFrame duplicateTimeSheet,
			DeductionTimeSheet deductionTimeSheet,
			List<TimeSheetOfDeductionItem> breakTimeFromMaster,
			PredetermineTimeSetForCalc predetermineTimeSetForCalc,
			Optional<LateDecisionClock> lateDesClock,
			Optional<LeaveEarlyDecisionClock> leaveEarlyDesClock,
			boolean isLastIndex) {
		
		boolean isDeductLate = true;
		boolean isDeductLeaveEarly = true;
		
		TimeWithDayAttr startOclock = duplicateTimeSheet.getTimeSheet().getStart();
		TimeWithDayAttr endOclock = duplicateTimeSheet.getTimeSheet().getEnd();
		
		EmTimeZoneSet dupTimeSheet = new EmTimeZoneSet(
				duplicateTimeSheet.getWorkingHoursTimeNo(),
				new TimeZoneRounding(duplicateTimeSheet.getTimeSheet().getStart(),
				duplicateTimeSheet.getTimeSheet().getEnd(),
				duplicateTimeSheet.getRounding()));

		List<TimeSheetOfDeductionItem> addBreakListInLateEarly = new ArrayList<>();
		
		//遅刻時間帯の作成
		LateTimeSheet lateTimeSheet = createLateTimeSheet(
				timeLeavingWork,
				integrationOfWorkTime.getCommonSetting().getLateEarlySet().getOtherEmTimezoneLateEarlySet(LateEarlyAtr.LATE),
				lateDesClock,
				duplicateTimeSheet,
				deductionTimeSheet,
				breakTimeFromMaster,
				timeLeavingWork.getWorkNo().v(),
				predetermineTimeSetForCalc.getTimeSheets(todayWorkType.getDailyWork().decisionNeedPredTime(), timeLeavingWork.getWorkNo().v()),
				todayWorkType,
				predetermineTimeSetForCalc,
				integrationOfWorkTime);
		
		//遅刻時間を計算する
		AttendanceTime lateDeductTime = lateTimeSheet.getForDeducationTimeSheet().isPresent()?lateTimeSheet.getForDeducationTimeSheet().get().calcTotalTime():new AttendanceTime(0);
		
		if(lateDesClock.isPresent()) {//←のifはフレックスの最低勤務時間利用の場合に下記処理を走らせたくない為追加
			Optional<WorkTimeCalcMethodDetailOfHoliday> holidayCalcMethodSet = personDailySetting.getAddSetting().getVacationCalcMethodSet().getWorkTimeCalcMethodOfHoliday().getAdvancedSet();
			if(holidayCalcMethodSet.isPresent()) {
				
				//就業時間内時間帯から控除するか判断し控除する
				isDeductLate = holidayCalcMethodSet.get().decisionLateDeductSetting(
						lateDeductTime,
						integrationOfWorkTime.getCommonSetting().getLateEarlySet().getOtherEmTimezoneLateEarlySet(LateEarlyAtr.LATE).getGraceTimeSet(),
						Optional.of(integrationOfWorkTime.getCommonSetting()));
				
				//控除後の出勤時刻を取得（控除しない場合は、所定の開始時刻をセットする)
				startOclock = getDeductedStartOclockByLate(
						companyCommonSetting,
						personDailySetting,
						todayWorkType,
						integrationOfWorkTime,
						timeLeavingWork,
						duplicateTimeSheet,
						deductionTimeSheet,
						breakTimeFromMaster,
						predetermineTimeSetForCalc,
						isLastIndex,
						isDeductLate,
						lateTimeSheet);

				//遅刻早退時間帯が持っている休憩を就業時間枠時間帯へ入れる
				if(lateTimeSheet.getForDeducationTimeSheet() != null && lateTimeSheet.getForDeducationTimeSheet().isPresent()) {
					addBreakListInLateEarly.addAll(lateTimeSheet.getForDeducationTimeSheet().get().getDeductionTimeSheet().stream().filter(tc -> tc.getDeductionAtr().isBreak()).collect(Collectors.toList()));
				}
			}
		}
		//早退時間帯の作成
		LeaveEarlyTimeSheet LeaveEarlyTimeSheet = createLeaveEarlyTimeSheet(
				timeLeavingWork,
				integrationOfWorkTime.getCommonSetting().getLateEarlySet().getOtherEmTimezoneLateEarlySet(LateEarlyAtr.EARLY),
				leaveEarlyDesClock,
				duplicateTimeSheet,
				deductionTimeSheet,
				breakTimeFromMaster,
				timeLeavingWork.getWorkNo().v(),
				predetermineTimeSetForCalc.getTimeSheets(todayWorkType.getDailyWork().decisionNeedPredTime(), timeLeavingWork.getWorkNo().v()),
				todayWorkType,
				predetermineTimeSetForCalc,
				integrationOfWorkTime);
		
		//早退時間を計算する
		AttendanceTime LeaveEarlyDeductTime = LeaveEarlyTimeSheet.getForDeducationTimeSheet().isPresent()?LeaveEarlyTimeSheet.getForDeducationTimeSheet().get().calcTotalTime():new AttendanceTime(0);
		
		if(leaveEarlyDesClock.isPresent()) {//←のifはフレックスの最低勤務時間利用の場合に下記処理を走らせたくない為追加
			Optional<WorkTimeCalcMethodDetailOfHoliday> holidayCalcMethodSet = personDailySetting.getAddSetting().getVacationCalcMethodSet().getWorkTimeCalcMethodOfHoliday().getAdvancedSet();
			if(holidayCalcMethodSet.isPresent()) {
				
				//就業時間内時間帯から控除するか判断
				isDeductLeaveEarly = (holidayCalcMethodSet.get().decisionLateDeductSetting(LeaveEarlyDeductTime,
					integrationOfWorkTime.getCommonSetting().getLateEarlySet().getOtherEmTimezoneLateEarlySet(LateEarlyAtr.EARLY).getGraceTimeSet(),
					Optional.of(integrationOfWorkTime.getCommonSetting())));
				
				//控除後の退勤時刻を取得（控除しない場合は、所定の終了時刻をセットする)
				endOclock = getDeductedEndOclockByEarly(
						companyCommonSetting,
						personDailySetting,
						todayWorkType,
						integrationOfWorkTime,
						timeLeavingWork,
						duplicateTimeSheet,
						deductionTimeSheet,
						breakTimeFromMaster,
						predetermineTimeSetForCalc,
						isLastIndex,
						isDeductLeaveEarly,
						LeaveEarlyTimeSheet);

				//遅刻早退時間帯が持っている休憩を就業時間枠時間帯へ入れる
				if(LeaveEarlyTimeSheet.getForDeducationTimeSheet() != null && LeaveEarlyTimeSheet.getForDeducationTimeSheet().isPresent()) {
					addBreakListInLateEarly.addAll(LeaveEarlyTimeSheet.getForDeducationTimeSheet().get().getDeductionTimeSheet().stream().filter(tc -> tc.getDeductionAtr().isBreak()).collect(Collectors.toList()));
				}
			}
		}
		
		//控除後の出勤と退勤時刻から時間帯を作成する。
		dupTimeSheet = new EmTimeZoneSet(
				new EmTimeFrameNo(timeLeavingWork.getWorkNo().v()), 
				new TimeZoneRounding(startOclock, endOclock, duplicateTimeSheet.getRounding()));
		
		//控除時間帯
		List<TimeSheetOfDeductionItem> dedTimeSheet = Collections.emptyList();
		dedTimeSheet = deductionTimeSheet.getDupliRangeTimeSheet(duplicateTimeSheet.getBeforeLateEarlyTimeSheet(), DeductionAtr.Deduction);
		dedTimeSheet.forEach(tc ->{
			tc.changeReverceRounding(tc.getRounding(), ActualWorkTimeSheetAtr.WithinWorkTime, DeductionAtr.Deduction, Optional.of(integrationOfWorkTime.getCommonSetting()));
		});

		//計上用時間帯
		List<TimeSheetOfDeductionItem> recordTimeSheet = Collections.emptyList(); 

		recordTimeSheet = deductionTimeSheet.getDupliRangeTimeSheet(new TimeSpanForDailyCalc(dupTimeSheet.getTimezone().getTimeSpan()), DeductionAtr.Appropriate);
		recordTimeSheet.forEach(tc ->{
			tc.changeReverceRounding(tc.getRounding(), ActualWorkTimeSheetAtr.WithinWorkTime, DeductionAtr.Appropriate, Optional.of(integrationOfWorkTime.getCommonSetting()));
		});
		
		/*
		 * 就業時間内時間帯に持たせる休憩時間帯を作成
		 * 加給、深夜に休憩時刻を持たせたい。
		 * 遅刻早退の時間帯の休憩は遅刻を控除しない場合、加給や深夜にもその休憩時間帯を含める。
		 */
		for(TimeSheetOfDeductionItem td : addBreakListInLateEarly) {
			if(td != null && (!isDeductLeaveEarly || !isDeductLate)) {
				dedTimeSheet.addAll(addBreakListInLateEarly);
			}
		}

		/*加給*/
		List<BonusPayTimeSheetForCalc> bonusPayTimeSheet = getBonusPayTimeSheetIncludeDedTimeSheet(personDailySetting.getBonusPaySetting(), new TimeSpanForDailyCalc(dupTimeSheet.getTimezone().getTimeSpan()), dedTimeSheet, recordTimeSheet);
		/*特定日*/
		List<SpecBonusPayTimeSheetForCalc> specifiedBonusPayTimeSheet = getSpecBonusPayTimeSheetIncludeDedTimeSheet(personDailySetting.getBonusPaySetting(), new TimeSpanForDailyCalc(dupTimeSheet.getTimezone().getTimeSpan()), dedTimeSheet, recordTimeSheet,integrationOfDaily.getSpecDateAttr());
		/*深夜*/
		Optional<MidNightTimeSheetForCalc> duplicatemidNightTimeSheet = getMidNightTimeSheetIncludeDedTimeSheet(companyCommonSetting.getMidNightTimeSheet(), new TimeSpanForDailyCalc(dupTimeSheet.getTimezone().getTimeSpan()), dedTimeSheet, recordTimeSheet,Optional.of(integrationOfWorkTime.getCommonSetting()));
		
		return new WithinWorkTimeFrame(
				duplicateTimeSheet.getWorkingHoursTimeNo(),
				new TimeSpanForDailyCalc(dupTimeSheet.getTimezone().getTimeSpan()),
				duplicateTimeSheet.getBeforeLateEarlyTimeSheet(),
				duplicateTimeSheet.getRounding(),
				recordTimeSheet,
				dedTimeSheet,
				bonusPayTimeSheet,
				duplicatemidNightTimeSheet,
				specifiedBonusPayTimeSheet,
				Optional.of(lateTimeSheet),
				Optional.of(LeaveEarlyTimeSheet));
	}
	
	/**
	 * 控除後の出勤時刻を取得する
	 * @param companyCommonSetting 会社別設定管理
	 * @param personDailySetting 社員設定管理
	 * @param todayWorkType 勤務種類
	 * @param integrationOfWorkTime 統合就業時間帯
	 * @param timeLeavingWork 出退勤
	 * @param duplicateTimeSheet 就業時間内時間枠
	 * @param deductionTimeSheet 控除時間帯
	 * @param breakTimeFromMaster 控除項目の時間帯(List) ※就業時間帯マスタから取得した休憩時間帯（大塚用）
	 * @param predetermineTimeSetForCalc 計算用所定時間設定
	 * @param isLastIndex ループの最後である
	 * @param isDeductLeaveLate 控除する
	 * @param lateTimeSheet 遅刻時間帯
	 * @return 控除後の出勤時刻
	 */
	private static TimeWithDayAttr getDeductedStartOclockByLate(
			ManagePerCompanySet companyCommonSetting,
			ManagePerPersonDailySet personDailySetting,
			WorkType todayWorkType,
			IntegrationOfWorkTime integrationOfWorkTime,
			TimeLeavingWork timeLeavingWork,
			WithinWorkTimeFrame duplicateTimeSheet,
			DeductionTimeSheet deductionTimeSheet,
			List<TimeSheetOfDeductionItem> breakTimeFromMaster,
			PredetermineTimeSetForCalc predetermineTimeSetForCalc,
			boolean isLastIndex,
			boolean isDeductLeaveLate,
			LateTimeSheet lateTimeSheet) {
		
		TimeWithDayAttr startTime = duplicateTimeSheet.getTimeSheet().getStart();
			
		//フレックスの場合はコアタイムを使って遅刻早退の控除を行う。
		//出勤 < コアタイム.開始　→　出勤
		//出勤 > コアタイム.開始　→　コアタイム.開始　を開始時刻とする。
		if(integrationOfWorkTime.getWorkTimeSetting().getWorkTimeDivision().isFlex()) {
			CoreTimeSetting coreTimeSetting = integrationOfWorkTime.getFlexWorkSetting().get().getCoreTimeSetting();
			if(coreTimeSetting.isUseTimeSheet()) {
				startTime = getDecisionCoreTimeSheet(predetermineTimeSetForCalc,coreTimeSetting,todayWorkType).getStartTime();
				if(startTime.greaterThan(duplicateTimeSheet.getTimeSheet().getStart())) {
					startTime = duplicateTimeSheet.getTimeSheet().getStart();
				}
			}
		}

		//就業時間帯枠=1の場合は、
		boolean isFirstIndex = duplicateTimeSheet.getWorkingHoursTimeNo().v().equals(1);
		if(lateTimeSheet.getForDeducationTimeSheet().isPresent() && isFirstIndex) {
			EmTimeZoneSet emDuplicateTimeSheet = new EmTimeZoneSet(
					new EmTimeFrameNo(timeLeavingWork.getWorkNo().v()), 
					new TimeZoneRounding(duplicateTimeSheet.getTimeSheet().getStart(), duplicateTimeSheet.getTimeSheet().getEnd(), duplicateTimeSheet.getRounding()));
			Optional<TimeWithDayAttr> correctStartOclock = deductProcessForLate(lateTimeSheet,breakTimeFromMaster,deductionTimeSheet,true,emDuplicateTimeSheet);
			startTime = isDeductLeaveLate && correctStartOclock.isPresent()
					?correctStartOclock.get()
					:lateTimeSheet.getForDeducationTimeSheet().get().getTimeSheet().getStart();
		}
		return startTime;
	}
	
	/**
	 * 控除後の退勤時刻を取得する
	 * @param companyCommonSetting 会社別設定管理
	 * @param personDailySetting 社員設定管理
	 * @param todayWorkType 勤務種類
	 * @param integrationOfWorkTime 統合就業時間帯
	 * @param timeLeavingWork 出退勤
	 * @param duplicateTimeSheet 就業時間内時間枠
	 * @param deductionTimeSheet 控除時間帯
	 * @param breakTimeFromMaster 控除項目の時間帯(List) ※就業時間帯マスタから取得した休憩時間帯（大塚用）
	 * @param predetermineTimeSetForCalc 計算用所定時間設定
	 * @param isLastIndex ループの最後である
	 * @param isDeductLeaveLate 控除する
	 * @param earlyTimeSheet 早退時間帯
	 * @return 控除後の退勤時刻
	 */
	private static TimeWithDayAttr getDeductedEndOclockByEarly(
			ManagePerCompanySet companyCommonSetting,
			ManagePerPersonDailySet personDailySetting,
			WorkType todayWorkType,
			IntegrationOfWorkTime integrationOfWorkTime,
			TimeLeavingWork timeLeavingWork,
			WithinWorkTimeFrame duplicateTimeSheet,
			DeductionTimeSheet deductionTimeSheet,
			List<TimeSheetOfDeductionItem> breakTimeFromMaster,
			PredetermineTimeSetForCalc predetermineTimeSetForCalc,
			boolean isLastIndex,
			boolean isDeductLeaveEarly,
			LeaveEarlyTimeSheet earlyTimeSheet) {
	
		//控除が無ければそのままの終了時刻を使う。
		TimeWithDayAttr endTime = duplicateTimeSheet.getTimeSheet().getEnd();
		
		//フレックスの場合はコアタイム
		if(integrationOfWorkTime.getWorkTimeSetting().getWorkTimeDivision().isFlex()) {
			CoreTimeSetting coreTimeSetting = integrationOfWorkTime.getFlexWorkSetting().get().getCoreTimeSetting();
			if(coreTimeSetting.isUseTimeSheet()) {
				endTime = getDecisionCoreTimeSheet(predetermineTimeSetForCalc,coreTimeSetting,todayWorkType).getEndTime();
				if(endTime.lessThan(duplicateTimeSheet.getTimeSheet().getEnd())) {
					endTime = duplicateTimeSheet.getTimeSheet().getEnd();
				}
			}
		}
		
		//早退時間帯がそもそも存在しない　→　引数dupTimeSheetの終了をそのまま（現状維持)　控除しない場合　→　leaveearlyのend する場合　→ leaveEarlyのstart
		if(earlyTimeSheet.getForDeducationTimeSheet().isPresent() && isLastIndex) {
			 EmTimeZoneSet emDuplicateTimeSheet = new EmTimeZoneSet(
					 new EmTimeFrameNo(timeLeavingWork.getWorkNo().v()), 
					 new TimeZoneRounding(duplicateTimeSheet.getTimeSheet().getStart(), duplicateTimeSheet.getTimeSheet().getEnd(), duplicateTimeSheet.getRounding()));
			
			Optional<TimeWithDayAttr> correctEndOclock = deductProcessForEarly(earlyTimeSheet, breakTimeFromMaster, deductionTimeSheet, true, emDuplicateTimeSheet);
			endTime = isDeductLeaveEarly && correctEndOclock.isPresent() 
					?correctEndOclock.get()
					:earlyTimeSheet.getForDeducationTimeSheet().get().getTimeSheet().getEnd();
		 }
		 return endTime;
	}
	
	
	private static Optional<TimeWithDayAttr> deductProcessForLate(LateTimeSheet lTSheet,List<TimeSheetOfDeductionItem> breakTimeList, DeductionTimeSheet deductionTimeSheet,
									  						boolean isOOtsuka, EmTimeZoneSet originDupTimeSheet) {
		if(lTSheet == null) return Optional.empty();
		List<TimeSheetOfDeductionItem> dedList = new ArrayList<>();
		TimeWithDayAttr startOclock = lTSheet.getForDeducationTimeSheet().get().getTimeSheet().getEnd();
		if(isOOtsuka) {
			dedList = breakTimeList;
		}
		else {
			dedList = deductionTimeSheet.getForDeductionTimeZoneList();
		}
		for(TimeSheetOfDeductionItem tod : dedList) {
			if(tod.contains(startOclock)) {
				if(tod.contains(originDupTimeSheet.getTimezone().getStart())) {
					startOclock = originDupTimeSheet.getTimezone().getStart();
				}
				else if(originDupTimeSheet.getTimezone().getStart().lessThan(tod.getTimeSheet().getStart())) {
					startOclock = tod.getTimeSheet().getStart();
				}
				else if(originDupTimeSheet.getTimezone().getStart().greaterThan(tod.getTimeSheet().getStart())) {
					startOclock = tod.getTimeSheet().getEnd();
				}
//				else {
//					startOclock = tod.getTimeSheet().getEnd();	
//				}
			}
		}
		return Optional.of(startOclock);
	}

	
	private static Optional<TimeWithDayAttr> deductProcessForEarly(LeaveEarlyTimeSheet lSheet,List<TimeSheetOfDeductionItem> breakTimeList, DeductionTimeSheet deductionTimeSheet,
																   boolean isOOtsuka, EmTimeZoneSet originDupTimeSheet) {
		if(lSheet == null) return Optional.empty();
		List<TimeSheetOfDeductionItem> dedList = new ArrayList<>();
		TimeWithDayAttr endOclock = lSheet.getForDeducationTimeSheet().get().getTimeSheet().getStart();
		if(isOOtsuka) {
			dedList = breakTimeList;
		}
		else {
			dedList = deductionTimeSheet.getForDeductionTimeZoneList();
		}
		for(TimeSheetOfDeductionItem tod : dedList) {
			if(tod.contains(endOclock)) {
				if(tod.contains(originDupTimeSheet.getTimezone().getEnd())) {
					endOclock = originDupTimeSheet.getTimezone().getEnd();
				}
				else if(originDupTimeSheet.getTimezone().getStart().greaterThan(tod.getTimeSheet().getStart())) {
					endOclock = tod.getTimeSheet().getStart();
				}
				else if(originDupTimeSheet.getTimezone().getStart().lessThan(tod.getTimeSheet().getStart())) {
					endOclock = tod.getTimeSheet().getEnd();
				}
//				else {
//					endOclock = tod.getTimeSheet().getStart();	
//				}
			}
		}
		return Optional.of(endOclock);
	}


	/**
	 * 遅刻早退を控除するかどうか判断
	 * @param lateEarlyMinusAtr 
	 * @return
	 */
	public boolean isDeductLateLeaveEarly(PremiumAtr premiumAtr,HolidayCalcMethodSet holidayCalcMethodSet,Optional<WorkTimezoneCommonSet> commonSetting) {
		//就業の休暇の就業時間計算方法詳細．遅刻・早退を控除する
		if(premiumAtr.isRegularWork() && holidayCalcMethodSet.getWorkTimeCalcMethodOfHoliday().isDeductLateLeaveEarly(commonSetting))
			return true;
		
		//割増の休暇の就業時間計算方法詳細．遅刻・早退を控除する
		if(premiumAtr.isPremium() && holidayCalcMethodSet.getPremiumCalcMethodOfHoliday().isDeductLateLeaveEarly(commonSetting))
			return true;
		
		return false;
	}
	
	/**
	 * 大塚モード使用時専用の遅刻、早退削除処理
	 */
	public void cleanLateLeaveEarlyTimeForOOtsuka() {
		lateTimeSheet = Optional.empty();
		leaveEarlyTimeSheet = Optional.empty();
	}
	
	
	/**
	 * コアタイム時間帯を午前終了、午後開始で補正したコアタイム時間帯の取得
	 * コアありフレの場合に就業時間内時間帯から遅刻早退を控除しない場合用
	 * @param predetermineTimeForSet
	 * @return
	 */
	public static TimeSheet getDecisionCoreTimeSheet(PredetermineTimeSetForCalc predetermineTimeForSet,CoreTimeSetting coreTimeSetting,WorkType workType) {
		
		TimeSheet result = coreTimeSetting.getCoreTimeSheet();
		
		switch (workType.getAttendanceHolidayAttr()) {
		case MORNING:
			TimeWithDayAttr end = result.getEndTime();
			if(predetermineTimeForSet.getAMEndTime().lessThan(end.valueAsMinutes())) {
				end = predetermineTimeForSet.getAMEndTime();
			}
			result = new TimeSheet(result.getStartTime(), end);
			return result;
		case AFTERNOON:
			TimeWithDayAttr start = result.getStartTime();
			if(predetermineTimeForSet.getPMStartTime().greaterThan(start.valueAsMinutes())) {
				start = predetermineTimeForSet.getPMStartTime();
			}
			result = new TimeSheet(start,result.getEndTime());
			return result;
		case FULL_TIME:
		case HOLIDAY:
			return result;
		default:
			throw new RuntimeException("unknown attr:" + workType.getAttendanceHolidayAttr());
		}
			
	}
	
	/**
	 * 自身が持つ短時間勤務時間帯(控除)を収集
	 * @return　短時間勤務時間帯
	 */
	public List<TimeSheetOfDeductionItem> collectShortTimeSheetInFrame(){
		List<TimeSheetOfDeductionItem> returnList = new ArrayList<>(); 
		//自身が持つ短時間時間帯を保持
		returnList.addAll(this.collectShortTimeSheet());
		//遅刻時間帯の短時間時間帯を保持
		if(this.getLateTimeSheet() != null && this.getLateTimeSheet().isPresent())
			returnList.addAll(this.getLateTimeSheet().get().getShortTimeSheet());
		//早退時間帯の短時間時間帯を保持
		if(this.getLeaveEarlyTimeSheet() != null && this.getLeaveEarlyTimeSheet().isPresent())
			returnList.addAll(this.getLeaveEarlyTimeSheet().get().getShortTimeSheet());
		return returnList;
	}
	
	 /**
	 * 遅刻時間帯の作成（流動）
	 * @param timeLeavingWork 出退勤
	 * @param predetermineTimeSet 所定時間設定(計算用クラス)
	 * @param deductionTimeSheet 控除時間帯
	 * @param lateDecisionClock 遅刻判断時刻
	 * @param commonSetting 就業時間帯の共通設定
	 * @param flowRestTime 流動勤務の休憩時間帯
	 * @return 遅刻時間帯
	 */
	public Optional<LateTimeSheet> createLateTimeSheet(
			TimeLeavingWork timeLeavingWork,
			PredetermineTimeSetForCalc predetermineTimeSet,
			List<TimeSheetOfDeductionItem> forDeductionTimeZones,
			Optional<LateDecisionClock> lateDecisionClock,
			WorkTimezoneCommonSet commonSetting,
			FlowWorkRestTimezone flowRestTime){
			
		if(!lateDecisionClock.isPresent()) return Optional.empty();
		
		//出勤時刻 > 遅刻判断時刻 の場合
		if(timeLeavingWork.getTimespan().getSpan().getStart().greaterThan(lateDecisionClock.get().getLateDecisionClock().getDayTime())) {
			
			LateTimeSheet creatingLateTimeSheet = new LateTimeSheet(Optional.empty(), Optional.empty(), timeLeavingWork.getWorkNo().v(), Optional.empty());
			
			//遅刻控除時間帯の作成
			creatingLateTimeSheet.createLateTimeSheetForFlow(
					DeductionAtr.Deduction,
					timeLeavingWork,
					predetermineTimeSet,
					commonSetting,
					flowRestTime,
					forDeductionTimeZones);
			
			//遅刻時間帯の作成
			creatingLateTimeSheet.createLateTimeSheetForFlow(
					DeductionAtr.Appropriate,
					timeLeavingWork,
					predetermineTimeSet,
					commonSetting,
					flowRestTime,
					forDeductionTimeZones);
			
			this.lateTimeSheet = Optional.of(creatingLateTimeSheet);
			
			return Optional.of(creatingLateTimeSheet);
		}
		return Optional.empty();
	}
	
	 /**
	 * 早退時間帯の作成（流動）
	 * @param timeLeavingWork 出退勤
	 * @param predetermineTimeSet 所定時間設定(計算用クラス)
	 * @param forDeductionTimeZones 控除項目の時間帯
	 * @param leaveEarlyDecisionClock 早退判断時刻
	 * @param commonSetting 就業時間帯の共通設定
	 * @param flowRestTime 流動勤務の休憩時間帯
	 * @return 早退時間帯
	 */
	public Optional<LeaveEarlyTimeSheet> createLeaveEarlyTimeSheet(
			TimeLeavingWork timeLeavingWork,
			PredetermineTimeSetForCalc predetermineTimeSet,
			List<TimeSheetOfDeductionItem> forDeductionTimeZones,
			Optional<LeaveEarlyDecisionClock> leaveEarlyDecisionClock,
			WorkTimezoneCommonSet commonSetting,
			FlowWorkRestTimezone flowRestTime){
			
		if(!leaveEarlyDecisionClock.isPresent()) return Optional.empty();
		
		//早退判断時刻 > 退勤時刻 の場合
		if(leaveEarlyDecisionClock.get().getLeaveEarlyDecisionClock().greaterThan(timeLeavingWork.getTimespan().getSpan().getEnd().getDayTime())) {
			
			LeaveEarlyTimeSheet creatingLeaveEarlyTimeSheet = new LeaveEarlyTimeSheet(Optional.empty(), Optional.empty(), timeLeavingWork.getWorkNo().v(), Optional.empty());
			
			//早退控除時間帯の作成
			creatingLeaveEarlyTimeSheet.createLeaveEaryTimeSheetForFlow(
					DeductionAtr.Deduction,
					timeLeavingWork,
					predetermineTimeSet,
					commonSetting,
					flowRestTime,
					forDeductionTimeZones);
			
			//早退時間帯の作成
			creatingLeaveEarlyTimeSheet.createLeaveEaryTimeSheetForFlow(
					DeductionAtr.Appropriate,
					timeLeavingWork,
					predetermineTimeSet,
					commonSetting,
					flowRestTime,
					forDeductionTimeZones);
			
			this.leaveEarlyTimeSheet = Optional.of(creatingLeaveEarlyTimeSheet);
			
			return Optional.of(creatingLeaveEarlyTimeSheet);
		}
		return Optional.empty();
	}
	
	/**
	 * 遅刻早退控除前時間帯を作成する
	 * @param lateDecisionClocks 遅刻判断時刻
	 * @param leaveEarlyDecisionClocks 早退判断時刻
	 */
	public void createBeforeLateEarlyTimeSheet(
			LateDecisionClock lateDecisionClocks,
			LeaveEarlyDecisionClock leaveEarlyDecisionClocks) {
		this.beforeLateEarlyTimeSheet = this.timeSheet;
		if(this.timeSheet.getStart().greaterThan(lateDecisionClocks.getLateDecisionClock())){
			this.beforeLateEarlyTimeSheet = this.beforeLateEarlyTimeSheet.shiftOnlyStart(lateDecisionClocks.getLateDecisionClock());
		}
		if(this.timeSheet.getEnd().lessThan(leaveEarlyDecisionClocks.getLeaveEarlyDecisionClock())){
			this.beforeLateEarlyTimeSheet = this.beforeLateEarlyTimeSheet.shiftOnlyEnd(leaveEarlyDecisionClocks.getLeaveEarlyDecisionClock());
		}
	}
}
