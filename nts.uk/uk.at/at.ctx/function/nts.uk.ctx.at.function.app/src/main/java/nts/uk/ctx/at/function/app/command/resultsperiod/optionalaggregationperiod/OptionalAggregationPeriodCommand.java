package nts.uk.ctx.at.function.app.command.resultsperiod.optionalaggregationperiod;

import lombok.Setter;
import nts.arc.time.GeneralDate;
import nts.uk.ctx.at.record.dom.resultsperiod.optionalaggregationperiod.primitivevalue.AggrFrameCode;
import nts.uk.ctx.at.record.dom.resultsperiod.optionalaggregationperiod.primitivevalue.OptionalAggrName;

/**
 * The Class OptionalAggregationPeriodCommand.
 */
@Setter
public class OptionalAggregationPeriodCommand {

	/** 任意集計枠コード */
	private String aggrFrameCode;

	/** 任意集計名称 */
	private String optionalAggrName;

	/** 対象期間 */
	private GeneralDate startDate;

	/** 対象期間 */
	private GeneralDate endDate;

	public AggrFrameCode getAggrFrameCode() {
		return new AggrFrameCode(aggrFrameCode);
	}
	public OptionalAggrName getOptionalAggrName() {
		return new OptionalAggrName(optionalAggrName);
	}
	public GeneralDate getStartDate() {
		return this.startDate;
	}
	public GeneralDate getEndDate() {
		return this.endDate;
	}

}
