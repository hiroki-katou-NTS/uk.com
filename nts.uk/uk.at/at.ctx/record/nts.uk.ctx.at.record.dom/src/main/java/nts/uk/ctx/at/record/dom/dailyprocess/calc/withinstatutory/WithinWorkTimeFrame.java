package nts.uk.ctx.at.record.dom.dailyprocess.calc.withinstatutory;

import java.util.List;
import java.util.Optional;

import lombok.val;
import lombok.Getter;
import nts.gul.util.value.Finally;
import nts.uk.ctx.at.record.dom.MidNightTimeSheet;
import nts.uk.ctx.at.record.dom.daily.LateTimeOfDaily;
import nts.uk.ctx.at.record.dom.daily.LeaveEarlyTimeOfDaily;
import nts.uk.ctx.at.record.dom.daily.TimeWithCalculation;
import nts.uk.ctx.at.record.dom.daily.TimevacationUseTimeOfDaily;
import nts.uk.ctx.at.record.dom.dailyprocess.calc.CalculationTimeSheet;
import nts.uk.ctx.at.record.dom.dailyprocess.calc.DeductionAtr;
import nts.uk.ctx.at.record.dom.dailyprocess.calc.DeductionOffSetTime;
import nts.uk.ctx.at.record.dom.dailyprocess.calc.DeductionTimeSheet;
import nts.uk.ctx.at.record.dom.dailyprocess.calc.LateTimeSheet;
import nts.uk.ctx.at.record.dom.dailyprocess.calc.LeaveEarlyTimeSheet;
import nts.uk.ctx.at.record.dom.dailyprocess.calc.PredetermineTimeSetForCalc;
import nts.uk.ctx.at.record.dom.dailyprocess.calc.TimeSheetOfDeductionItem;
import nts.uk.ctx.at.shared.dom.bonuspay.setting.BonusPayTimesheet;
import nts.uk.ctx.at.shared.dom.bonuspay.setting.SpecBonusPayTimesheet;
import nts.uk.ctx.at.shared.dom.common.time.AttendanceTime;
import nts.uk.ctx.at.shared.dom.common.time.TimeSpanForCalc;
import nts.uk.ctx.at.shared.dom.employment.statutory.worktime.employment.WorkingSystem;
import nts.uk.ctx.at.shared.dom.vacation.setting.addsettingofworktime.AddSettingOfFlexWork;
import nts.uk.ctx.at.shared.dom.vacation.setting.addsettingofworktime.AddSettingOfIrregularWork;
import nts.uk.ctx.at.shared.dom.vacation.setting.addsettingofworktime.AddSettingOfRegularWork;
import nts.uk.ctx.at.shared.dom.vacation.setting.addsettingofworktime.CalculationByActualTimeAtr;
import nts.uk.ctx.at.shared.dom.vacation.setting.addsettingofworktime.HolidayAdditionAtr;
import nts.uk.ctx.at.shared.dom.vacation.setting.addsettingofworktime.StatutoryDivision;
import nts.uk.ctx.at.shared.dom.vacation.setting.addsettingofworktime.WorkTimeCalcMethodDetailOfHoliday;
import nts.uk.ctx.at.shared.dom.workrule.addsettingofworktime.NotUseAtr;
import nts.uk.ctx.at.shared.dom.workrule.addsettingofworktime.VacationAddTimeSet;
import nts.uk.ctx.at.shared.dom.worktime.WorkTime;
import nts.uk.ctx.at.shared.dom.worktime.CommomSetting.lateleaveearlysetting.GraceTimeSetting;
import nts.uk.ctx.at.shared.dom.worktime.fixedworkset.WorkTimeCommonSet;
import nts.uk.ctx.at.shared.dom.worktime.fixedworkset.timespan.TimeSpanWithRounding;
import nts.uk.ctx.at.shared.dom.worktime.fluidworkset.Rounding;
import nts.uk.ctx.at.shared.dom.worktime.fluidworkset.TimeRoundingSetting;
import nts.uk.ctx.at.shared.dom.worktime.fluidworkset.Unit;
import nts.uk.ctx.at.shared.dom.worktype.AttendanceHolidayAttr;
import nts.uk.ctx.at.shared.dom.worktype.DailyWork;
import nts.uk.shr.com.time.TimeWithDayAttr;

/**
 * 就業時間内時間枠
 * @author keisuke_hoshina
 *
 */
public class WithinWorkTimeFrame extends CalculationTimeSheet{// implements LateLeaveEarlyManagementTimeFrame {

	@Getter
	private final int workingHoursTimeNo;
	
	private final Optional<TimeSpanForCalc> premiumTimeSheetInPredetermined;
	
//	//遅刻時間帯・・・deductByLateLeaveEarlyを呼ぶまでは値が無い
//	private Finally<LateTimeSheet> lateTimeSheet = Finally.empty();
//	//早退時間帯・・・deductByLateLeaveEarlyを呼ぶまでは値が無い
//	private Finally<LeaveEarlyTimeSheet> leaveEarlyTimeSheet = Finally.empty();
	
	public TimeSpanForCalc getPremiumTimeSheetInPredetermined() {
		return this.premiumTimeSheetInPredetermined.get();
	}
	
	/**
	 * constructor
	 * @param workingHoursTimeNo
	 * @param timeSheet
	 * @param calculationTimeSheet
	 */
	public WithinWorkTimeFrame(
			int workingHoursTimeNo,
			TimeSpanWithRounding timeSheet,
			TimeSpanForCalc calculationTimeSheet,
			List<TimeSheetOfDeductionItem> deductionTimeSheets,
			List<BonusPayTimesheet> bonusPayTimeSheet,
			Optional<MidNightTimeSheet> midNighttimeSheet,
			List<SpecBonusPayTimesheet> specifiedBonusPayTimeSheet) {
		
		super(timeSheet, calculationTimeSheet,deductionTimeSheets,bonusPayTimeSheet,specifiedBonusPayTimeSheet,midNighttimeSheet);
		this.workingHoursTimeNo = workingHoursTimeNo;
		this.premiumTimeSheetInPredetermined = Optional.empty();
	}
	

	
	public TimeSpanWithRounding getTimeSheet() {
		return this.timeSheet;
	}

	
	/**
	 * 時間帯から遅刻早退時間を控除
	 * @param workTime
	 * @param goWorkTime
	 * @param workNo
	 * @param workTimeCommonSet
	 * @param withinWorkTimeSheet
	 * @param deductionTimeSheet
	 * @param graceTimeSetting
	 * @param workTimeCalcMethodDetailOfHoliday
	 */
	public void deductByLateLeaveEarly(
			WorkTime workTime,
			TimeWithDayAttr goWorkTime,
			TimeWithDayAttr leaveWorkTime,
			int workNo,
			WorkTimeCommonSet workTimeCommonSet,
			WithinWorkTimeSheet withinWorkTimeSheet,
			DeductionTimeSheet deductionTimeSheet,
			GraceTimeSetting graceTimeSetting,
			WorkTimeCalcMethodDetailOfHoliday workTimeCalcMethodDetailOfHoliday) {
	
//		//遅刻時間を計算する
//		this.lateTimeSheet.set(LateTimeSheet.lateTimeCalc(
//				this,
//				workTime.getLateTimeCalcRange(goWorkTime, workNo),
//				workTimeCommonSet,
//				withinWorkTimeSheet.getlateDecisionClock(workNo),
//				deductionTimeSheet));
//		
//		//遅刻していない場合and猶予時間を加算する場合はここで処理を抜ける
//		if (!this.lateTimeSheet.get().isLate()) {
//			return;
//		}
//		
//		int lateDeductTime = this.lateTimeSheet.get().getLateDeductionTime();
//		
//		//就業時間内時間帯から控除するか判断し控除する				
//		if(workTimeCalcMethodDetailOfHoliday.deductsFromWithinWorkTimeSheet(lateDeductTime, graceTimeSetting)) {
//			//遅刻時間帯の終了時刻を開始時刻にする
//			this.timeSheet = this.lateTimeSheet.get().deductForm(this.timeSheet);
//		}
//		
//		this.deductByLate(graceTimeSetting, workTimeCalcMethodDetailOfHoliday);
//
//		
//		//早退時間を計算する		
//		this.leaveEarlyTimeSheet.set(LeaveEarlyTimeSheet.leaveEarlyTimeCalc(
//				this, 
//				workTime.getleaveEarlyTimeCalcRange(leaveWorkTime, workNo), 
//				workTimeCommonSet, 
//				withinWorkTimeSheet.getleaveEarlyDecisionClock(workNo), 
//				deductionTimeSheet));
//		
//		if (!this.leaveEarlyTimeSheet.get().isLeaveEarly()) {
//			return;
//		}
//		
//		int leaveEarlyDeductTime = this.leaveEarlyTimeSheet.get().getLeaveEarlyDeductionTime();
//		
//		//就業時間内時間帯から控除するか判断し控除する				
//		if(workTimeCalcMethodDetailOfHoliday.deductsFromWithinWorkTimeSheet(leaveEarlyDeductTime, graceTimeSetting)) {
//			//早退時間帯の終了時刻を開始時刻にする
//			this.timeSheet = this.lateTimeSheet.get().deductForm(this.timeSheet);
//		}
//		
//		this.deductByLeaveEarly(graceTimeSetting, workTimeCalcMethodDetailOfHoliday);	
//		
	}
//
//	/**
//	 *  就業時間内時間帯から遅刻分を控除
//	 * @param graceTimeSetting
//	 * @param workTimeCalcMethodDetailOfHoliday
//	 */
//	private void deductByLate(GraceTimeSetting graceTimeSetting,
//			WorkTimeCalcMethodDetailOfHoliday workTimeCalcMethodDetailOfHoliday) {
//		//遅刻時間を計算する
//		int lateDeductTime = this.lateTimeSheet.get().getForDeducationTimeSheet().get().lengthAsMinutes();
//		
//		//就業時間内時間帯から控除するか判断し控除する				
//		if(workTimeCalcMethodDetailOfHoliday.deductsFromWithinWorkTimeSheet(lateDeductTime, graceTimeSetting)) {
//			//遅刻時間帯の終了時刻を開始時刻にする
//			this.timeSheet = this.timeSheet.newTimeSpan(
//					this.timeSheet.shiftOnlyStart(this.lateTimeSheet.get().getForDeducationTimeSheet().get().getEnd()));
//		}
//	}
//	
//	/**
//	 * 就業時間内時間帯から早退分を控除
//	 * @param graceTimeSetting
//	 * @param workTimeCalcMethodDetailOfHoliday
//	 */
//	private void deductByLeaveEarly(GraceTimeSetting graceTimeSetting,
//			WorkTimeCalcMethodDetailOfHoliday workTimeCalcMethodDetailOfHoliday) {
//		//早退時間を計算する
//		int leaveEarlyDeductTime = this.leaveEarlyTimeSheet.get().getForDeducationTimeSheet().get().lengthAsMinutes();
//		
//		//就業時間内時間帯から控除するか判断し控除する				
//		if(workTimeCalcMethodDetailOfHoliday.deductsFromWithinWorkTimeSheet(leaveEarlyDeductTime, graceTimeSetting)) {
//			//早退時間帯の開始時刻を終了時刻にする
//			this.timeSheet = this.timeSheet.newTimeSpan(
//					this.timeSheet.shiftOnlyEnd(this.leaveEarlyTimeSheet.get().getForDeducationTimeSheet().get().getStart()));
//		}
//	}
//	
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
	 * @param holidayAdditionAtr
	 * @param dedTimeSheet
	 * @return
	 */
	public AttendanceTime calcActualWorkTimeAndWorkTime(HolidayAdditionAtr holidayAdditionAtr,
														DeductionTimeSheet dedTimeSheet,
														TimevacationUseTimeOfDaily timevacationUseTimeOfDaily,
														WorkingSystem workingSystem,
														AddSettingOfRegularWork addSettingOfRegularWork,
														AddSettingOfIrregularWork addSettingOfIrregularWork, 
														AddSettingOfFlexWork addSettingOfFlexWork,
														LateTimeSheet lateTimeSheet,
														LeaveEarlyTimeSheet leaveEarlyTimeSheet,
														LateTimeOfDaily lateTimeOfDaily,
														LeaveEarlyTimeOfDaily leaveEarlyTimeOfDaily,
														VacationAddTimeSet vacationAddTimeSet,
														boolean late,  //日別実績の計算区分.遅刻早退の自動計算設定.遅刻
														boolean leaveEarly  //日別実績の計算区分.遅刻早退の自動計算設定.早退
														) {
		  AttendanceTime actualTime = calcActualTime(); 
		  actualTime = actualTime.minusMinutes(dedTimeSheet.calcDeductionAllTimeSheet(DeductionAtr.Deduction, ((CalculationTimeSheet)this).getTimeSheet()).valueAsMinutes());
		  AttendanceTime workTime = calcWorkTime(actualTime);
		/*就業時間算出ロジックをここに*/
		
		//控除時間の内、時間休暇で相殺した時間を計算
		DeductionOffSetTime timeVacationOffSetTime = dedTimeSheet.calcTotalDeductionOffSetTime(lateTimeOfDaily,lateTimeSheet,leaveEarlyTimeOfDaily,leaveEarlyTimeSheet);
		//時間休暇使用の残時間を計算 
		timevacationUseTimeOfDaily.subtractionDeductionOffSetTime(timeVacationOffSetTime);
		//遅刻、早退時間を就業時間から控除 
		workTime = new AttendanceTime(workTime.valueAsMinutes() + calcLateLeaveEarlyDeductionTimeForPremiun(workingSystem,
																											addSettingOfRegularWork,
																											addSettingOfIrregularWork,
																											addSettingOfFlexWork,
																											lateTimeSheet,
																											leaveEarlyTimeSheet,
																											late,
																											leaveEarly).valueAsMinutes());		
		//就業時間に加算する時間休暇を就業時間へ加算     
		workTime = new AttendanceTime(workTime.valueAsMinutes() + calcTimeVacationAddTime(vacationAddTimeSet,
																						  getCalculationByActualTimeAtr(workingSystem,
																													  	addSettingOfRegularWork,
																													  	addSettingOfIrregularWork,
																													  	addSettingOfFlexWork),
																						  timeVacationOffSetTime).valueAsMinutes());
		
		return workTime;
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
	 public AttendanceTime calcActualTime() {
	  return new AttendanceTime(((CalculationTimeSheet)this).getTimeSheet().lengthAsMinutes());
	 }
	
	/**
	 * 計算範囲を判断（流動）　　流動時の就業時間内時間枠
	 * @author ken_takasu
	 * 
	 */
	public void createWithinWorkTimeFrameForFluid(
			AttendanceLeavingWork attendanceLeavingWork,
			DailyWork dailyWork,
			PredetermineTimeSetForCalc predetermineTimeSetForCalc) {
		TimeSpanForCalc timeSheet = new TimeSpanForCalc(
				attendanceLeavingWork.getAttendance().getEngrave().getTimesOfDay(),
				attendanceLeavingWork.getLeaveWork().getEngrave().getTimesOfDay());
		this.correctTimeSheet(dailyWork, timeSheet, predetermineTimeSetForCalc);
	}
	
	/**
	 * 勤務の単位を基に時間帯の開始、終了を補正
	 * @author ken_takasu
	 * @param dailyWork 1日の勤務
	 */
	public WithinWorkTimeFrame correctTimeSheet(
			DailyWork dailyWork,
			TimeSpanForCalc timeSheet,
			PredetermineTimeSetForCalc predetermineTimeSetForCalc) {
		
		//丸め設定を作成
		Finally<TimeRoundingSetting> rounding = Finally.of(new TimeRoundingSetting(Unit.ROUNDING_TIME_1MIN,Rounding.ROUNDING_DOWN));
		
		if (dailyWork.getAttendanceHolidayAttr().isHalfDayWorking()) {
			TimeSpanForCalc timeSheetForRounding = this.getHalfDayWorkingTimeSheetOf(dailyWork.getAttendanceHolidayAttr(),timeSheet,predetermineTimeSetForCalc);
			TimeSpanWithRounding calcRange = new TimeSpanWithRounding(timeSheetForRounding.getStart(),timeSheetForRounding.getEnd(),rounding);
			CalculationTimeSheet t = new CalculationTimeSheet(calcRange,timeSheetForRounding,Optional.empty());
			return new WithinWorkTimeFrame(workingHoursTimeNo,timeSheet,t);
		}
	}

	/**
	 * 午前出勤、午後出勤の判定
	 * @author ken_takasu
	 * @param attr
	 * @return
	 */
	private TimeSpanForCalc getHalfDayWorkingTimeSheetOf(
			AttendanceHolidayAttr attr,
			TimeSpanForCalc timeSheet,
			PredetermineTimeSetForCalc predetermineTimeSetForCalc) {
		switch (attr) {
		case MORNING:
			return new TimeSpanForCalc(timeSheet.getStart(), predetermineTimeSetForCalc.getAMEndTime());
		case AFTERNOON:
			return new TimeSpanForCalc(predetermineTimeSetForCalc.getPMStartTime(),timeSheet.getEnd());
		default:
			throw new RuntimeException("半日専用のメソッドです: " + attr);
		}
	}

	
	/**
	 * 「遅刻早退を控除しない」を取得する
	 * @author ken_takasu
	 * @param workingSystem
	 * @return
	 */
	private NotUseAtr getNotDeductLateLeaveEarly(WorkingSystem workingSystem,
												  //StatutoryDivision statutoryDivision,
													AddSettingOfRegularWork addSettingOfRegularWork,
													AddSettingOfIrregularWork addSettingOfIrregularWork, 
													AddSettingOfFlexWork addSettingOfFlexWork) {
		switch (workingSystem) {
		case RegularWork:
			return addSettingOfRegularWork.getHolidayCalcMethodSet().getWorkTimeCalcMethodOfHoliday().getDetailSet().getDeductLateLeaveEarly();

		case FlexTimeWork:
			return addSettingOfFlexWork.getHolidayCalcMethodSet().getWorkTimeCalcMethodOfHoliday().getDetailSet().getDeductLateLeaveEarly();

		case VariableWorkingTimeWork:
			return addSettingOfIrregularWork.getHolidayCalcMethodSet().getWorkTimeCalcMethodOfHoliday().getDetailSet().getDeductLateLeaveEarly();

		case ExcludedWorkingCalculate:
			throw new RuntimeException("不正な労働制です");
		default:
			throw new RuntimeException("不正な労働制です");
		}
	}
	
	/**
	 * 割増用遅刻早退控除時間の計算
	 * @author ken_takasu
	 * @return
	 */
	public AttendanceTime calcLateLeaveEarlyDeductionTimeForPremiun(WorkingSystem workingSystem,
			  													  //StatutoryDivision statutoryDivision,
																	AddSettingOfRegularWork addSettingOfRegularWork,
																	AddSettingOfIrregularWork addSettingOfIrregularWork, 
																	AddSettingOfFlexWork addSettingOfFlexWork,
																	LateTimeSheet lateTimeSheet,
																	LeaveEarlyTimeSheet leaveEarlyTimeSheet,
																	boolean late,  //日別実績の計算区分.遅刻早退の自動計算設定.遅刻
																	boolean leaveEarly  //日別実績の計算区分.遅刻早退の自動計算設定.早退
																	) {
		AttendanceTime addTime = new AttendanceTime(0);
		//就業時間帯の設定を取得
		if(getNotDeductLateLeaveEarly(workingSystem, addSettingOfRegularWork, addSettingOfIrregularWork, addSettingOfFlexWork).isDonot()) {
			//遅刻控除時間を計算
			TimeWithCalculation lateDeductionTime = lateTimeSheet.calcLateForDeductionTime(NotUseAtr.To, late);
			addTime = new AttendanceTime(lateDeductionTime.getCalcTime().valueAsMinutes());
			//早退控除時間を計算
			TimeWithCalculation leaveEarlyDeductionTime = leaveEarlyTimeSheet.calcLeaveEarlyForDeductionTime(NotUseAtr.To, leaveEarly);
			addTime = new AttendanceTime(addTime.valueAsMinutes() + leaveEarlyDeductionTime.getCalcTime().valueAsMinutes());
		}
		return addTime;
	}
	
	
	/**
	 * 時間休暇からの加算時間を取得
	 * @author ken_takasu
	 * 
	 * @return
	 */
	public AttendanceTime calcTimeVacationAddTime(VacationAddTimeSet vacationAddTimeSet,
												  CalculationByActualTimeAtr calculationByActualTimeAtr,
												  DeductionOffSetTime timeVacationOffSetTime
												  ) {
		AttendanceTime addTime = new AttendanceTime(0);
		//実働のみで計算するかチェックする
		if(calculationByActualTimeAtr.isCalculationOtherThanActualTime()) {
			//年休分を加算するかチェックする
			if(vacationAddTimeSet.getAddVacationSet().getAnnualLeave().isUse()) {
				addTime = new AttendanceTime(addTime.valueAsMinutes() + timeVacationOffSetTime.getAnnualLeave().valueAsMinutes());
			}
			//積立年休分を含めて求めるかチェックする
			if(vacationAddTimeSet.getAddVacationSet().getＲetentionYearly().isUse()) {
				addTime = new AttendanceTime(addTime.valueAsMinutes() + timeVacationOffSetTime.getRetentionYearly().valueAsMinutes());
			}
			//特別休暇分を含めて求めるかチェックする
			if(vacationAddTimeSet.getAddVacationSet().getSpecialHoliday().isUse()) {
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
	public CalculationByActualTimeAtr getCalculationByActualTimeAtr(WorkingSystem workingSystem,
																	AddSettingOfRegularWork addSettingOfRegularWork,
																	AddSettingOfIrregularWork addSettingOfIrregularWork, 
																	AddSettingOfFlexWork addSettingOfFlexWork) {
		switch (workingSystem) {
		case RegularWork:
			return addSettingOfRegularWork.getHolidayCalcMethodSet().getWorkTimeCalcMethodOfHoliday().getCalculationByActualTime();

		case FlexTimeWork:
			return addSettingOfFlexWork.getHolidayCalcMethodSet().getPremiumCalcMethodOfHoliday().getCalculationByActualTime();

		case VariableWorkingTimeWork:
			return addSettingOfIrregularWork.getHolidayCalcMethodSet().getWorkTimeCalcMethodOfHoliday().getCalculationByActualTime();

		case ExcludedWorkingCalculate:
			return CalculationByActualTimeAtr.CalculationByActualTime;
		default:
			throw new RuntimeException("不正な労働制です");
		}
	}
	
	
			
}
