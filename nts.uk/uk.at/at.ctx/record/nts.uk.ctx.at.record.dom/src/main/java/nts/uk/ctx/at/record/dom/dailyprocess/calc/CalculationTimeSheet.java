package nts.uk.ctx.at.record.dom.dailyprocess.calc;

import java.util.ArrayList;
import java.util.List;

import lombok.RequiredArgsConstructor;
import nts.uk.ctx.at.shared.dom.common.time.TimeSpanForCalc;
import nts.uk.ctx.at.shared.dom.worktime.fixedworkset.timespan.TimeSpanWithRounding;

/**
 * 計算時間帯
 * @author keisuke_hoshina
 *
 */
public abstract class CalculationTimeSheet {
	
	protected TimeSpanWithRounding timeSheet;
	protected final TimeSpanForCalc calculationTimeSheet;
	protected final List<DeductionTimeSheet> deductionTimeSheets = new ArrayList<>();
	
	public CalculationTimeSheet(TimeSpanWithRounding timeSheet, TimeSpanForCalc calculationTimeSheet) {
		this.timeSheet = timeSheet;
		this.calculationTimeSheet = calculationTimeSheet;
	}
}
