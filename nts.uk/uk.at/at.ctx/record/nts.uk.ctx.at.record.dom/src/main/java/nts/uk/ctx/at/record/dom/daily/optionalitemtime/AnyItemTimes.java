package nts.uk.ctx.at.record.dom.daily.optionalitemtime;

import java.math.BigDecimal;

import nts.arc.primitive.DecimalPrimitiveValue;

public class AnyItemTimes extends DecimalPrimitiveValue<AnyItemTimes>{

	private static final long serialVersionUID = 1L;
	
	public AnyItemTimes(BigDecimal rawValue) {
		super(rawValue);
	}
}