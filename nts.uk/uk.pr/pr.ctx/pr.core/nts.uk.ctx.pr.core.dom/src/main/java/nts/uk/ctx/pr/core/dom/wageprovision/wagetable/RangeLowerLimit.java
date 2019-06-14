package nts.uk.ctx.pr.core.dom.wageprovision.wagetable;

import java.math.BigDecimal;

import nts.arc.primitive.DecimalPrimitiveValue;
import nts.arc.primitive.constraint.DecimalMantissaMaxLength;
import nts.arc.primitive.constraint.DecimalRange;

/**
 * 範囲下限
 */


@DecimalRange(min = "-9999999999.00", max = "9999999999.00")
@DecimalMantissaMaxLength(2)
public class RangeLowerLimit extends DecimalPrimitiveValue<RangeLowerLimit> {

	private static final long serialVersionUID = 1L;

	public RangeLowerLimit(BigDecimal rawValue) {
		super(rawValue);
	}

}
