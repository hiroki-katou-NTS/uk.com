package nts.uk.ctx.at.function.app.find.processexecution.dto;

import java.util.Optional;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import nts.arc.enums.EnumAdaptor;
import nts.uk.ctx.at.function.dom.processexecution.AggrFrameCode;
import nts.uk.ctx.at.function.dom.processexecution.AggregationAnyPeriod;
import nts.uk.shr.com.enumcommon.NotUseAtr;

/**
 * The class Aggregation of arbitrary period dto.<br>
 * Dto 任意期間の集計
 *
 * @author nws-minhnb
 */
@Data
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class AggregationAnyPeriodDto {

	/**
	 * The Aggregation any period attribute.<br>
	 * 使用区分
	 **/
	private int aggAnyPeriodAttr;

	/**
	 * The Aggregation frame code.<br>
	 * コード
	 **/
	private String aggrFrameCode;

	/**
	 * Create from domain.
	 *
	 * @param domain the domain
	 * @return the Aggregation of arbitrary period dto
	 */
	public static AggregationAnyPeriodDto createFromDomain(AggregationAnyPeriod domain) {
		if (domain == null) {
			return null;
		}
		AggregationAnyPeriodDto dto = new AggregationAnyPeriodDto();
		dto.aggAnyPeriodAttr = domain.getAggAnyPeriodAttr().value;
		dto.aggrFrameCode = domain.getAggrFrameCode().map(AggrFrameCode::v).orElse(null);
		return dto;
	}

	public AggregationAnyPeriod toDomain() {
		return AggregationAnyPeriod.builder()
				.aggAnyPeriodAttr(EnumAdaptor.valueOf(this.aggAnyPeriodAttr, NotUseAtr.class))
				.aggrFrameCode(Optional.ofNullable(this.aggrFrameCode).map(AggrFrameCode::new))
				.build();
	}
	
}
