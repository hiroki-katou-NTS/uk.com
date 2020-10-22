package nts.uk.ctx.at.function.dom.adapter.resultsperiod.optionalaggregationperiod;

import lombok.AllArgsConstructor;
import lombok.Data;
import nts.arc.time.calendar.period.DatePeriod;

/**
 * 任意集計期間
 *
 * @author phongtq
 */
@Data
@AllArgsConstructor
public class AnyAggrPeriodImport {

	/** 会社ID */
	private String companyId;

	/** 任意集計枠コード */
	private String aggrFrameCode;

	/** 任意集計名称 */
	private String optionalAggrName;

	/** 対象期間 */
	private DatePeriod period;

	/**
	 * No args constructor.
	 */
	private AnyAggrPeriodImport() {
	}

}
