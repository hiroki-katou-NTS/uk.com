package nts.uk.ctx.at.record.dom.dailyprocess.calc.withinstatutory;

import java.util.List;
import java.util.Optional;

import nts.uk.ctx.at.record.dom.dailyprocess.calc.CalculationTimeSheet;
import nts.uk.ctx.at.record.dom.dailyprocess.calc.DeductionAtr;
import nts.uk.ctx.at.record.dom.dailyprocess.calc.DeductionTimeSheet;
import nts.uk.ctx.at.shared.dom.common.time.AttendanceTime;
import nts.uk.ctx.at.shared.dom.common.time.TimeSpanForCalc;
import nts.uk.ctx.at.shared.dom.vacation.setting.addsettingofworktime.HolidayAdditionAtr;
import nts.uk.ctx.at.shared.dom.worktime.fixedworkset.timespan.TimeSpanWithRounding;
import nts.uk.shr.com.time.TimeWithDayAttr;

/**
 * 就業時間内時間枠
 * @author keisuke_hoshina
 *
 */
public class WithinWorkTimeFrame extends CalculationTimeSheet {

	private final int workingHoursTimeNo;
	
	private final Optional<TimeSpanForCalc> premiumTimeSheetInPredetermined;
	
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
	
	/**
	 * 実働時間を求め、就業時間を計算する
	 * @return 就業時間
	 */
	public int calcActualWorkTimeAndWorkTime(HolidayAdditionAtr holidayAdditionAtr,DeductionTimeSheet dedTimeSheet) {
		int actualTime = calcWorkTime(); 
		actualTime = actualTime - dedTimeSheet.calcDeductionAllTimeSheet(DeductionAtr.Deduction, ((CalculationTimeSheet)this).getTimeSheet());
		int workTime = calcActualTime(actualTime);
		/*就業時間算出ロジックをここに*/
		
		return workTime;
	}
	
	/**
	 * 実働時間の算出
	 * @return
	 */
	public int calcActualTime(int actualTime) {
		int workTime = 0;
		return workTime;
	}
	
	/**
	 * 就業時間を計算する 
	 * @return
	 */
	public int calcWorkTime() {
		return ((CalculationTimeSheet)this).getTimeSheet().lengthAsMinutes();
	}
}
