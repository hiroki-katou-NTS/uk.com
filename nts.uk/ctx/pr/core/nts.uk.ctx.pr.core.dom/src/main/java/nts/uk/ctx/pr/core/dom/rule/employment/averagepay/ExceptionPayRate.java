package nts.uk.ctx.pr.core.dom.rule.employment.averagepay;

import java.math.BigDecimal;

import nts.arc.primitive.DecimalPrimitiveValue;
import nts.arc.primitive.IntegerPrimitiveValue;
import nts.arc.primitive.constraint.DecimalMaxValue;
import nts.arc.primitive.constraint.DecimalMinValue;
import nts.arc.primitive.constraint.IntegerMaxValue;
import nts.arc.primitive.constraint.IntegerMinValue;

@IntegerMaxValue(100)
@IntegerMinValue(0)
public class ExceptionPayRate extends IntegerPrimitiveValue<ExceptionPayRate>{
	
	public ExceptionPayRate(int value) {
		super(value);
		// TODO Auto-generated constructor stub
	}
}
