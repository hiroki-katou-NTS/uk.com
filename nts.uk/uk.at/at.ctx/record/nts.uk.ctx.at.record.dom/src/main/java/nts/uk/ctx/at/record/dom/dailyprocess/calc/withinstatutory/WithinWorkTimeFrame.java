package nts.uk.ctx.at.record.dom.dailyprocess.calc.withinstatutory;

import java.util.List;
import java.util.Optional;

import lombok.val;
import nts.gul.util.value.Finally;
import nts.uk.ctx.at.record.dom.dailyprocess.calc.CalculationTimeSheet;
import nts.uk.ctx.at.record.dom.dailyprocess.calc.DeductionTimeSheet;
import nts.uk.ctx.at.record.dom.dailyprocess.calc.LateLeaveEarlyManagementTimeFrame;
import nts.uk.ctx.at.record.dom.dailyprocess.calc.LateTimeSheet;
import nts.uk.ctx.at.record.dom.dailyprocess.calc.LeaveEarlyTimeSheet;
import nts.uk.ctx.at.shared.dom.common.time.TimeSpanForCalc;
import nts.uk.ctx.at.shared.dom.vacation.setting.addsettingofworktime.WorkTimeCalcMethodDetailOfHoliday;
import nts.uk.ctx.at.shared.dom.worktime.WorkTime;
import nts.uk.ctx.at.shared.dom.worktime.CommonSetting.lateleaveearly.GraceTimeSetting;
import nts.uk.ctx.at.shared.dom.worktime.fixedworkset.WorkTimeCommonSet;
import nts.uk.ctx.at.shared.dom.worktime.fixedworkset.timespan.TimeSpanWithRounding;
import nts.uk.shr.com.time.TimeWithDayAttr;

/**
 * 就業時間内時間枠
 * @author keisuke_hoshina
 *
 */
public class WithinWorkTimeFrame extends CalculationTimeSheet implements LateLeaveEarlyManagementTimeFrame {

	private final int workingHoursTimeNo;
	
	private final Optional<TimeSpanForCalc> premiumTimeSheetInPredetermined;
	
	//遅刻時間帯・・・deductByLateLeaveEarlyを呼ぶまでは値が無い
	private Finally<LateTimeSheet> lateTimeSheet = Finally.empty();
	//早退時間帯・・・deductByLateLeaveEarlyを呼ぶまでは値が無い
	private Finally<LeaveEarlyTimeSheet> leaveEarlyTimeSheet = Finally.empty();
	
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
			TimeSpanForCalc calculationTimeSheet) {
		
		super(timeSheet, calculationTimeSheet);
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
<<<<<<< HEAD
	public int calcActualWorkTimeAndWorkTime(HolidayAdditionAtr holidayAdditionAtr,DeductionTimeSheet dedTimeSheet) {
		int actualTime = calcWorkTime(); 
		actualTime = actualTime - dedTimeSheet.calcDeductionAllTimeSheet(DeductionAtr.Deduction, ((CalculationTimeSheet)this).getTimeSheet());
		int workTime = calcActualTime(actualTime);
		/*就業時間算出ロジックをここに*/
=======
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

		//遅刻時間を計算する
		this.lateTimeSheet.set(LateTimeSheet.lateTimeCalc(
				this,
				workTime.getLateTimeCalcRange(goWorkTime, workNo),
				workTimeCommonSet,
				withinWorkTimeSheet.getlateDecisionClock(workNo),
				deductionTimeSheet));
		
		if (!this.lateTimeSheet.get().isLate()) {
			return;
		}
		
		int lateDeductTime = this.lateTimeSheet.get().getLateDeductionTime();
		
		//就業時間内時間帯から控除するか判断し控除する				
		if(workTimeCalcMethodDetailOfHoliday.deductsFromWithinWorkTimeSheet(lateDeductTime, graceTimeSetting)) {
			//遅刻時間帯の終了時刻を開始時刻にする
			this.timeSheet = this.lateTimeSheet.get().deductForm(this.timeSheet);
		}
		
		this.deductByLate(graceTimeSetting, workTimeCalcMethodDetailOfHoliday);

		
		//早退時間を計算する		
		this.leaveEarlyTimeSheet.set(LeaveEarlyTimeSheet.leaveEarlyTimeCalc(
				this, 
				workTime.getleaveEarlyTimeCalcRange(leaveWorkTime, workNo), 
				workTimeCommonSet, 
				withinWorkTimeSheet.getleaveEarlyDecisionClock(workNo), 
				deductionTimeSheet));
		
		if (!this.leaveEarlyTimeSheet.get().isLeaveEarly()) {
			return;
		}
		
		int leaveEarlyDeductTime = this.leaveEarlyTimeSheet.get().getLeaveEarlyDeductionTime();
		
		//就業時間内時間帯から控除するか判断し控除する				
		if(workTimeCalcMethodDetailOfHoliday.deductsFromWithinWorkTimeSheet(leaveEarlyDeductTime, graceTimeSetting)) {
			//早退時間帯の終了時刻を開始時刻にする
			this.timeSheet = this.lateTimeSheet.get().deductForm(this.timeSheet);
		}
		
		this.deductByLeaveEarly(graceTimeSetting, workTimeCalcMethodDetailOfHoliday);	
>>>>>>> ecbaab4d216f774ac42e77cba6dd2c7cb9b42598
		
	}

	/**
	 *  就業時間内時間帯から遅刻分を控除
	 * @param graceTimeSetting
	 * @param workTimeCalcMethodDetailOfHoliday
	 */
	private void deductByLate(GraceTimeSetting graceTimeSetting,
			WorkTimeCalcMethodDetailOfHoliday workTimeCalcMethodDetailOfHoliday) {
		//遅刻時間を計算する
		int lateDeductTime = this.lateTimeSheet.get().getForDeducationTimeSheet().get().lengthAsMinutes();
		
		//就業時間内時間帯から控除するか判断し控除する				
		if(workTimeCalcMethodDetailOfHoliday.deductsFromWithinWorkTimeSheet(lateDeductTime, graceTimeSetting)) {
			//遅刻時間帯の終了時刻を開始時刻にする
			this.timeSheet = this.timeSheet.newTimeSpan(
					this.timeSheet.shiftOnlyStart(this.lateTimeSheet.get().getForDeducationTimeSheet().get().getEnd()));
		}
	}
	
	/**
	 * 就業時間内時間帯から早退分を控除
	 * @param graceTimeSetting
	 * @param workTimeCalcMethodDetailOfHoliday
	 */
	private void deductByLeaveEarly(GraceTimeSetting graceTimeSetting,
			WorkTimeCalcMethodDetailOfHoliday workTimeCalcMethodDetailOfHoliday) {
		//早退時間を計算する
		int leaveEarlyDeductTime = this.leaveEarlyTimeSheet.get().getForDeducationTimeSheet().get().lengthAsMinutes();
		
		//就業時間内時間帯から控除するか判断し控除する				
		if(workTimeCalcMethodDetailOfHoliday.deductsFromWithinWorkTimeSheet(leaveEarlyDeductTime, graceTimeSetting)) {
			//早退時間帯の開始時刻を終了時刻にする
			this.timeSheet = this.timeSheet.newTimeSpan(
					this.timeSheet.shiftOnlyEnd(this.leaveEarlyTimeSheet.get().getForDeducationTimeSheet().get().getStart()));
		}
	}
	

	@Override
	public LateTimeSheet getLateTimeSheet() {
		return this.lateTimeSheet.get();
	}

	@Override
	public LeaveEarlyTimeSheet getLeaveEarlyTimeSheet() {
		return this.leaveEarlyTimeSheet.get();
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

		//遅刻時間を計算する
		this.lateTimeSheet.set(LateTimeSheet.lateTimeCalc(
				this,
				workTime.getLateTimeCalcRange(goWorkTime, workNo),
				workTimeCommonSet,
				withinWorkTimeSheet.getlateDecisionClock(workNo),
				deductionTimeSheet));
		
		if (!this.lateTimeSheet.get().isLate()) {
			return;
		}
		
		int lateDeductTime = this.lateTimeSheet.get().getLateDeductionTime();
		
		//就業時間内時間帯から控除するか判断し控除する				
		if(workTimeCalcMethodDetailOfHoliday.deductsFromWithinWorkTimeSheet(lateDeductTime, graceTimeSetting)) {
			//遅刻時間帯の終了時刻を開始時刻にする
			this.timeSheet = this.lateTimeSheet.get().deductForm(this.timeSheet);
		}
		
		this.deductByLate(graceTimeSetting, workTimeCalcMethodDetailOfHoliday);

		
		//早退時間を計算する		
		this.leaveEarlyTimeSheet.set(LeaveEarlyTimeSheet.leaveEarlyTimeCalc(
				this, 
				workTime.getleaveEarlyTimeCalcRange(leaveWorkTime, workNo), 
				workTimeCommonSet, 
				withinWorkTimeSheet.getleaveEarlyDecisionClock(workNo), 
				deductionTimeSheet));
		
		if (!this.leaveEarlyTimeSheet.get().isLeaveEarly()) {
			return;
		}
		
		int leaveEarlyDeductTime = this.leaveEarlyTimeSheet.get().getLeaveEarlyDeductionTime();
		
		//就業時間内時間帯から控除するか判断し控除する				
		if(workTimeCalcMethodDetailOfHoliday.deductsFromWithinWorkTimeSheet(leaveEarlyDeductTime, graceTimeSetting)) {
			//早退時間帯の終了時刻を開始時刻にする
			this.timeSheet = this.lateTimeSheet.get().deductForm(this.timeSheet);
		}
		
		this.deductByLeaveEarly(graceTimeSetting, workTimeCalcMethodDetailOfHoliday);	
		
	}

	/**
	 *  就業時間内時間帯から遅刻分を控除
	 * @param graceTimeSetting
	 * @param workTimeCalcMethodDetailOfHoliday
	 */
	private void deductByLate(GraceTimeSetting graceTimeSetting,
			WorkTimeCalcMethodDetailOfHoliday workTimeCalcMethodDetailOfHoliday) {
		//遅刻時間を計算する
		int lateDeductTime = this.lateTimeSheet.get().getForDeducationTimeSheet().get().lengthAsMinutes();
		
		//就業時間内時間帯から控除するか判断し控除する				
		if(workTimeCalcMethodDetailOfHoliday.deductsFromWithinWorkTimeSheet(lateDeductTime, graceTimeSetting)) {
			//遅刻時間帯の終了時刻を開始時刻にする
			this.timeSheet = this.timeSheet.newTimeSpan(
					this.timeSheet.shiftOnlyStart(this.lateTimeSheet.get().getForDeducationTimeSheet().get().getEnd()));
		}
	}
	
	/**
	 * 就業時間内時間帯から早退分を控除
	 * @param graceTimeSetting
	 * @param workTimeCalcMethodDetailOfHoliday
	 */
	private void deductByLeaveEarly(GraceTimeSetting graceTimeSetting,
			WorkTimeCalcMethodDetailOfHoliday workTimeCalcMethodDetailOfHoliday) {
		//早退時間を計算する
		int leaveEarlyDeductTime = this.leaveEarlyTimeSheet.get().getForDeducationTimeSheet().get().lengthAsMinutes();
		
		//就業時間内時間帯から控除するか判断し控除する				
		if(workTimeCalcMethodDetailOfHoliday.deductsFromWithinWorkTimeSheet(leaveEarlyDeductTime, graceTimeSetting)) {
			//早退時間帯の開始時刻を終了時刻にする
			this.timeSheet = this.timeSheet.newTimeSpan(
					this.timeSheet.shiftOnlyEnd(this.leaveEarlyTimeSheet.get().getForDeducationTimeSheet().get().getStart()));
		}
	}
	

	@Override
	public LateTimeSheet getLateTimeSheet() {
		return this.lateTimeSheet.get();
	}

	@Override
	public LeaveEarlyTimeSheet getLeaveEarlyTimeSheet() {
		return this.leaveEarlyTimeSheet.get();
	}

}
