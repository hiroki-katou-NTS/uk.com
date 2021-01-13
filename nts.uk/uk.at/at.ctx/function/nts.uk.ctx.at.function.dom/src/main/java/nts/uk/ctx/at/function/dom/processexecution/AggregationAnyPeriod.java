package nts.uk.ctx.at.function.dom.processexecution;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import nts.arc.enums.EnumAdaptor;
import nts.arc.layer.dom.DomainObject;
import nts.uk.shr.com.enumcommon.NotUseAtr;

import java.util.Optional;

/**
 * The class Aggregation any period.<br>
 * Domain 任意期間の集計
 *
 * @author ngatt-nws
 */
@Data
@Builder
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class AggregationAnyPeriod extends DomainObject {

	/**
	 * The Aggregation any period attribute.<br>
	 * 使用区分
	 **/
	private NotUseAtr aggAnyPeriodAttr;

	/**
	 * The Aggregation frame code.<br>
	 * コード
	 **/
	private Optional<AggrFrameCode> aggrFrameCode;

	/**
	 * Instantiates a new <code>AggregationAnyPeriod</code>.
	 *
	 * @param aggAnyPeriodAttr the aggregation any period attribute
	 * @param aggrFrameCode    the aggregation frame code
	 */
	public AggregationAnyPeriod(int aggAnyPeriodAttr, String aggrFrameCode) {
		this.aggAnyPeriodAttr = EnumAdaptor.valueOf(aggAnyPeriodAttr, NotUseAtr.class);
		this.aggrFrameCode = Optional.ofNullable(aggrFrameCode).map(AggrFrameCode::new);
	}

}
