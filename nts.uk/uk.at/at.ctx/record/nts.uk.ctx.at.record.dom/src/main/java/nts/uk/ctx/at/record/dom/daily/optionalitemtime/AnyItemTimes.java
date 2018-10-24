package nts.uk.ctx.at.record.dom.daily.optionalitemtime;

import java.math.BigDecimal;

import nts.arc.primitive.DecimalPrimitiveValue;
import nts.arc.primitive.constraint.DecimalMantissaMaxLength;
import nts.arc.primitive.constraint.DecimalRange;

@DecimalMantissaMaxLength(2)
@DecimalRange(min = "-99.99", max = "99.99")
public class AnyItemTimes extends DecimalPrimitiveValue<AnyItemTimes>{

	private static final long serialVersionUID = 1L;
	
	public AnyItemTimes(BigDecimal rawValue) {
		super(rawValue.compareTo(BigDecimal.valueOf(99.99))>0?BigDecimal.valueOf(99.99):rawValue);
	}
}