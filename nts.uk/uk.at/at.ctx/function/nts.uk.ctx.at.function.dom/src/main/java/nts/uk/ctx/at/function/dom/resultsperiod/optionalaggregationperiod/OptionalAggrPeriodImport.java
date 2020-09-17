package nts.uk.ctx.at.function.dom.resultsperiod.optionalaggregationperiod;

import lombok.Builder;
import lombok.Data;
import nts.arc.time.GeneralDate;

/**
 * 任意集計期間
 * 
 * @author phongtq
 *
 */
@Data
@Builder
public class OptionalAggrPeriodImport {

	/** 会社ID */
	private String companyId;

	/** 任意集計枠コード */
	private String aggrFrameCode;

	/** 任意集計名称 */
	private String optionalAggrName;

	/** 対象期間 */
	private GeneralDate startDate;

	/** 対象期間 */
	private GeneralDate endDate;

}
