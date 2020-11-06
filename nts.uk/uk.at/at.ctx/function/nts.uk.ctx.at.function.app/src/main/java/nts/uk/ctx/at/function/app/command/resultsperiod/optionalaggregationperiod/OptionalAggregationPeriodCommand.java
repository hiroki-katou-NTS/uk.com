package nts.uk.ctx.at.function.app.command.resultsperiod.optionalaggregationperiod;

import lombok.Getter;
import lombok.NoArgsConstructor;
import nts.arc.time.GeneralDate;
import nts.uk.ctx.at.record.dom.resultsperiod.optionalaggregationperiod.AnyAggrPeriod;

/**
 * The Class OptionalAggregationPeriodCommand.
 */
@Getter
@NoArgsConstructor
public class OptionalAggregationPeriodCommand implements AnyAggrPeriod.MementoGetter {

	/** 任意集計枠コード */
	private String aggrFrameCode;

	/** 任意集計名称 */
	private String optionalAggrName;

	/** 対象期間 */
	private GeneralDate startDate;

	/** 対象期間 */
	private GeneralDate endDate;

	/**
	 * Gets company id.
	 *
	 * @return the company id
	 */
	@Override
	public String getCompanyId() {
		return null;
	}
}
