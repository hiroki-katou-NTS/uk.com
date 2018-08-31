package nts.uk.ctx.exio.dom.qmm.itemrangeset;

import java.math.BigDecimal;

import nts.arc.primitive.DecimalPrimitiveValue;
import nts.arc.primitive.constraint.DecimalRange;

/**
 * 金額値
 */
@DecimalRange(min = "0", max = "9999999999")
public class MonetaryValue extends DecimalPrimitiveValue<MonetaryValue>{

	public MonetaryValue(BigDecimal rawValue) {
		super(rawValue);
	}

	private static final long serialVersionUID = 1L; 
}
