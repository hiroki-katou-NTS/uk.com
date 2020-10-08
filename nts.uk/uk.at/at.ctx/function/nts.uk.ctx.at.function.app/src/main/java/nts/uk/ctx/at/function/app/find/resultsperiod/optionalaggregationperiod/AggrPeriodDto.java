package nts.uk.ctx.at.function.app.find.resultsperiod.optionalaggregationperiod;

import lombok.Builder;
import lombok.Data;
import nts.arc.time.GeneralDate;

/**
 * The Class AggrPeriodDto.
 * 任意集計期間
 */
@Data
@Builder
public class AggrPeriodDto {

	/**  会社ID. */
	private String companyId;

	/**  任意集計枠コード. */
	private String aggrFrameCode;

	/**  任意集計名称. */
	private String optionalAggrName;

	/**  対象期間. */
	private GeneralDate startDate;

	/**  対象期間. */
	private GeneralDate endDate;

}
