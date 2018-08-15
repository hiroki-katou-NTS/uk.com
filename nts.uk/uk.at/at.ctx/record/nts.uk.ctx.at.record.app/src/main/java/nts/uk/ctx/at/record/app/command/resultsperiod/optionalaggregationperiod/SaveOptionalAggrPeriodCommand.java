package nts.uk.ctx.at.record.app.command.resultsperiod.optionalaggregationperiod;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import nts.arc.time.GeneralDate;
import nts.uk.ctx.at.record.dom.resultsperiod.optionalaggregationperiod.OptionalAggrPeriod;

@AllArgsConstructor
@Data
@NoArgsConstructor
public class SaveOptionalAggrPeriodCommand {

	/** 任意集計枠コード */
	private String aggrFrameCode;

	/** 任意集計名称 */
	private String optionalAggrName;

	/** 対象期間 */
	private GeneralDate startDate;

	/** 対象期間 */
	private GeneralDate endDate;
	
	public OptionalAggrPeriod toDomain(String companyId) {

		return OptionalAggrPeriod.createFromJavaType(companyId, this.aggrFrameCode, this.optionalAggrName,
				this.startDate, this.endDate);
	}

}
