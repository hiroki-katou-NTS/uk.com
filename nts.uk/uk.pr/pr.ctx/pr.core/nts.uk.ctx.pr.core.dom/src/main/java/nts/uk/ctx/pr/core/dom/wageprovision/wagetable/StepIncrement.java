package nts.uk.ctx.pr.core.dom.wageprovision.wagetable;

import java.math.BigDecimal;

import nts.arc.primitive.DecimalPrimitiveValue;
import nts.arc.primitive.constraint.DecimalMantissaMaxLength;
import nts.arc.primitive.constraint.DecimalRange;

/**
 * きざみ単位
 */

@DecimalRange(min = "0.01", max = "9999999999.00")
@DecimalMantissaMaxLength(2)
public class StepIncrement extends DecimalPrimitiveValue<StepIncrement> {

	private static final long serialVersionUID = 1L;

	public StepIncrement(BigDecimal rawValue) {
		super(rawValue);
	}

}
