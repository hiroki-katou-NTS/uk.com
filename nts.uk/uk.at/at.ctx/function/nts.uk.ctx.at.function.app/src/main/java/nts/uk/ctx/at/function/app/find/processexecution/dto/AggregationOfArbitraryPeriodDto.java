package nts.uk.ctx.at.function.app.find.processexecution.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import nts.uk.ctx.at.function.dom.processexecution.AggrFrameCode;
import nts.uk.ctx.at.function.dom.processexecution.AggregationAnyPeriod;

/**
 * The class Aggregation of arbitrary period dto.<br>
 * Dto 任意期間の集計
 *
 * @author nws-minhnb
 */
@Data
@AllArgsConstructor
public class AggregationOfArbitraryPeriodDto {

	/**
	 * 使用区分
	 **/
	private int classificationOfUse;

	/**
	 * コード
	 **/
	private String aggrFrameCode;

	/**
	 * No args constructor.
	 */
	private AggregationOfArbitraryPeriodDto() {
	}

	/**
	 * Create from domain.
	 *
	 * @param domain the domain
	 * @return the Aggregation of arbitrary period dto
	 */
	public static AggregationOfArbitraryPeriodDto createFromDomain(AggregationAnyPeriod domain) {
		if (domain == null) {
			return null;
		}
		AggregationOfArbitraryPeriodDto dto = new AggregationOfArbitraryPeriodDto();
		dto.classificationOfUse = domain.getAggAnyPeriodAttr().value;
		dto.aggrFrameCode = domain.getAggrFrameCode().map(AggrFrameCode::v).orElse(null);
		return dto;
	}

}
