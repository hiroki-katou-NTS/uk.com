package nts.uk.ctx.pr.core.dom.wageprovision.wagetable;

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
	private Integer frameNumber;

	/**
	 * 当該枠下限
	 */
	private Integer frameLowerLimit;

	/**
	 * 当該枠上限
	 */
	private Integer frameUpperLimit;

}
