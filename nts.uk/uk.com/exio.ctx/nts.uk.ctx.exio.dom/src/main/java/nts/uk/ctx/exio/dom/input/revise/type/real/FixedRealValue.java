package nts.uk.ctx.exio.dom.input.revise.type.real;

import java.math.BigDecimal;

import nts.arc.primitive.DecimalPrimitiveValue;
import nts.arc.primitive.constraint.DecimalMantissaMaxLength;
import nts.arc.primitive.constraint.DecimalMaxValue;
import nts.arc.primitive.constraint.DecimalMinValue;

/**
 * 実数型固定値
 */
@DecimalMaxValue("9999999999.99")
@DecimalMinValue("-9999999999.99")
@DecimalMantissaMaxLength(2)
public class FixedRealValue extends DecimalPrimitiveValue<FixedRealValue>{
	
	public FixedRealValue(BigDecimal rawValue) {
		super(rawValue);
	}
}
