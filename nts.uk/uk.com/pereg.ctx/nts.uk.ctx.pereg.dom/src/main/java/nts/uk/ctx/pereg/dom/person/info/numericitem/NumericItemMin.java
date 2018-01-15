package nts.uk.ctx.pereg.dom.person.info.numericitem;

import java.math.BigDecimal;

import nts.arc.primitive.DecimalPrimitiveValue;
import nts.arc.primitive.constraint.DecimalMantissaMaxLength;
import nts.arc.primitive.constraint.DecimalRange;

@DecimalMantissaMaxLength(value = 38)
@DecimalRange(max = "999999999999999999999999999999.99999999", min = "-999999999999999999999999999999.99999999")
public class NumericItemMin extends DecimalPrimitiveValue<NumericItemMin> {

	private static final long serialVersionUID = 1L;

	public NumericItemMin(BigDecimal rawValue) {
		super(rawValue);
	}

}
