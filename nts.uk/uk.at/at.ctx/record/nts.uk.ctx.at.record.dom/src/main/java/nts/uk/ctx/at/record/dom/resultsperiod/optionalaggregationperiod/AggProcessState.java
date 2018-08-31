package nts.uk.ctx.at.record.dom.resultsperiod.optionalaggregationperiod;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public enum AggProcessState {
	/* 中断 */
	INTERRUPTION(0),

	/* 正常終了 */
	SUCCESS(1);

	public final int value;

}
