package nts.uk.ctx.at.record.app.find.resultsperiod.optionalaggregationperiod;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import nts.arc.time.GeneralDate;
import nts.uk.ctx.at.record.dom.resultsperiod.optionalaggregationperiod.OptionalAggrPeriod;

@NoArgsConstructor
@AllArgsConstructor
@Data
/**
 * 
 * @author phongtq
 *
 */
public class OptionalAggrPeriodDto {

	private String companyId;
	
	/** 任意集計枠コード */
	private String aggrFrameCode;

	/** 任意集計名称 */
	private String optionalAggrName;

	/** 対象期間 */
	private GeneralDate startDate;

	/** 対象期間 */
	private GeneralDate endDate;
	
	/** */

	public static OptionalAggrPeriodDto fromDomain(OptionalAggrPeriod domain){
		return new OptionalAggrPeriodDto(
				domain.getCompanyId(),
				domain.getAggrFrameCode().v(),
				domain.getOptionalAggrName().v(),
				domain.getStartDate(),
				domain.getEndDate()
				);
	}
}
