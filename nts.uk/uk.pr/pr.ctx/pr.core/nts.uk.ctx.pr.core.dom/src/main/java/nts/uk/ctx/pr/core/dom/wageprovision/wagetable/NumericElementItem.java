package nts.uk.ctx.pr.core.dom.wageprovision.wagetable;

import java.math.BigDecimal;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import nts.arc.layer.dom.DomainObject;

/**
 * 要素項目（数値）
 */
@AllArgsConstructor
@Getter
@EqualsAndHashCode(callSuper=false)
public class NumericElementItem extends DomainObject {

	/**
	 * 枠番
	 */
	private Long frameNumber;

	/**
	 * 当該枠下限
	 */
	private BigDecimal frameLowerLimit;

	/**
	 * 当該枠上限
	 */
	private BigDecimal frameUpperLimit;

}
