package nts.uk.ctx.at.function.app.command.resultsperiod.optionalaggregationperiod;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.Setter;
import nts.arc.time.GeneralDate;
import nts.uk.ctx.at.record.dom.resultsperiod.optionalaggregationperiod.primitivevalue.AggrFrameCode;
import nts.uk.ctx.at.record.dom.resultsperiod.optionalaggregationperiod.primitivevalue.OptionalAggrName;

/**
 * The Class OptionalAggregationPeriodCommand.
 */
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class OptionalAggregationPeriodCommand {

	/** 会社ID */
	private String companyId;

	/** 任意集計枠コード */
	private String aggrFrameCode;

	/** 任意集計名称 */
	private String optionalAggrName;

	/** 対象期間 */
	private String startDate;

	/** 対象期間 */
	private String endDate;

	public String getCompanyId() {
		return companyId;
	}
	public AggrFrameCode getAggrFrameCode() {
		return new AggrFrameCode(aggrFrameCode);
	}
	public OptionalAggrName getOptionalAggrName() {
		return new OptionalAggrName(optionalAggrName);
	}
	public GeneralDate getStartDate() {
		return GeneralDate.fromString(startDate, "yyyy/MM/dd");
	}
	public GeneralDate getEndDate() {
		return GeneralDate.fromString(endDate, "yyyy/MM/dd");
	}

}
