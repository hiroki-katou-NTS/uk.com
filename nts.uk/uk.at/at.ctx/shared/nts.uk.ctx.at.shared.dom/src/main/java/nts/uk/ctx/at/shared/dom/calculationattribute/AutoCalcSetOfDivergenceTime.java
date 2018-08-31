package nts.uk.ctx.at.shared.dom.calculationattribute;

import lombok.Getter;
import nts.arc.layer.dom.DomainObject;
import nts.uk.ctx.at.shared.dom.calculationattribute.enums.DivergenceTimeAttr;

/** 乖離時間の自動計算設定 */
@Getter
public class AutoCalcSetOfDivergenceTime extends DomainObject {

	/** 乖離: boolean */
	private DivergenceTimeAttr divergenceTime;

	/**
	 * Instantiates a new auto calc set of divergence time.
	 *
	 * @param atr the atr
	 */
	public AutoCalcSetOfDivergenceTime(DivergenceTimeAttr atr) {
		super();
		this.divergenceTime = atr;
	}
}
