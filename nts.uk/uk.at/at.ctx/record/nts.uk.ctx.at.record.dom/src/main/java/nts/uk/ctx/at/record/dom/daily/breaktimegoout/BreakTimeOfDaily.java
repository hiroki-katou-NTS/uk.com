package nts.uk.ctx.at.record.dom.daily.breaktimegoout;

import lombok.Getter;
import nts.uk.ctx.at.record.dom.daily.DeductionTotalTime;
import nts.uk.ctx.at.record.dom.dailyprocess.calc.CalculationRangeOfOneDay;
import nts.uk.ctx.at.record.dom.dailyprocess.calc.DeductionAtr;
import nts.uk.ctx.at.record.dom.dailyprocess.calc.DeductionTimeSheet;

/**
 * 日別実績の休憩時間
 * @author keisuke_hoshina
 *
 */
@Getter
public class BreakTimeOfDaily {
	private DeductionTotalTime toRecordTotalTime;
	private DeductionTotalTime deductionTotalTime;
	
	/**
	 * Constructor
	 * @param record 計上用
	 * @param deduction 控除用
	 */
	private BreakTimeOfDaily(DeductionTotalTime record,DeductionTotalTime deduction) {
		this.toRecordTotalTime = record;
		this.deductionTotalTime = deduction;
	}
	
	/**
	 * 控除、計上用両方を受け取った時間に入れ替える
	 * @return 日別実績の休憩時間
	 */
	public static BreakTimeOfDaily sameTotalTime(DeductionTotalTime deductionTime) {
		return new BreakTimeOfDaily(deductionTime,deductionTime);
	}
	
	/**
	 * 日別実績の休憩時間
	 * @param oneDay 1日の計算範囲
	 * @return 日別実績の休憩時間
	 */
	public static BreakTimeOfDaily calcTotalBreakTime(CalculationRangeOfOneDay oneDay) {
		return new BreakTimeOfDaily(oneDay.getTemporaryDeductionTimeSheet().get().getTotalBreakTime(DeductionAtr.Deduction),
									oneDay.getTemporaryDeductionTimeSheet().get().getTotalBreakTime(DeductionAtr.Appropriate));
	}
	
}
