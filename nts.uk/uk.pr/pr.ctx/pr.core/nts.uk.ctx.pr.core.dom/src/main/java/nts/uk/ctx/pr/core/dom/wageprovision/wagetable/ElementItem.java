package nts.uk.ctx.pr.core.dom.wageprovision.wagetable;

import java.math.BigDecimal;
import java.util.Optional;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import nts.arc.layer.dom.DomainObject;

/**
 * 要素項目
 */
@AllArgsConstructor
@Getter
@EqualsAndHashCode(callSuper=false)
public class ElementItem extends DomainObject {

	/**
	 * 要素項目(マスタ)
	 */
	private Optional<MasterElementItem> masterElementItem;

	/**
	 * 要素項目(数値)
	 */
	private Optional<NumericElementItem> numericElementItem;

	public ElementItem(String masterCode, Long frameNumber, BigDecimal frameLowerLimit, BigDecimal frameUpperLimit) {
		this.masterElementItem = masterCode == null ? Optional.empty() : Optional.of(new MasterElementItem(masterCode));
		if (frameNumber == null && frameLowerLimit == null && frameUpperLimit == null)
			this.numericElementItem = Optional.empty();
		else
			this.numericElementItem = Optional
					.of(new NumericElementItem(frameNumber, frameLowerLimit, frameUpperLimit));
	}

}
