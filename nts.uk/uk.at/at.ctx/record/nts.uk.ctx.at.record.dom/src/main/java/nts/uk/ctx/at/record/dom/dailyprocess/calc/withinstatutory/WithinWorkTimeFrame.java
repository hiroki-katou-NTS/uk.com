package nts.uk.ctx.at.record.dom.dailyprocess.calc.withinstatutory;

import java.util.Optional;

import nts.uk.ctx.at.record.dom.dailyprocess.calc.CalculationTimeSheet;
import nts.uk.ctx.at.shared.dom.common.time.TimeSpanForCalc;
import nts.uk.ctx.at.shared.dom.worktime.fixedworkset.timespan.TimeSpanWithRounding;

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
	
	public WithinWorkTimeFrame(
			int workingHoursTimeNo,
			TimeSpanWithRounding timeSheet,
			TimeSpanForCalc calculationTimeSheet) {
		
		super(timeSheet, calculationTimeSheet);
		this.workingHoursTimeNo = workingHoursTimeNo;
		this.premiumTimeSheetInPredetermined = Optional.empty();
	}
}
