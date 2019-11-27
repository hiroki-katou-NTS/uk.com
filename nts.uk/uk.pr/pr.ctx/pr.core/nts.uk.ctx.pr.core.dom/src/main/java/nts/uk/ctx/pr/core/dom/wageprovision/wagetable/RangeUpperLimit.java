package nts.uk.ctx.pr.core.dom.wageprovision.wagetable;

import java.math.BigDecimal;

import nts.arc.primitive.DecimalPrimitiveValue;
import nts.arc.primitive.constraint.DecimalMantissaMaxLength;
import nts.arc.primitive.constraint.DecimalRange;

/**
 * 範囲上限
 */

@DecimalRange(min = "-9999999999.00", max = "9999999999.00")
@DecimalMantissaMaxLength(2)
public class RangeUpperLimit extends DecimalPrimitiveValue<RangeUpperLimit> {

	private static final long serialVersionUID = 1L;

	public RangeUpperLimit(BigDecimal rawValue) {
		super(rawValue);
	}
	
}
