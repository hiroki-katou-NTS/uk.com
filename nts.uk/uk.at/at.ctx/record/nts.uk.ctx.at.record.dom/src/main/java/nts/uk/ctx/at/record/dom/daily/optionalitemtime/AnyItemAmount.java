package nts.uk.ctx.at.record.dom.daily.optionalitemtime;

import java.math.BigDecimal;

import nts.arc.primitive.DecimalPrimitiveValue;

public class AnyItemAmount extends DecimalPrimitiveValue<AnyItemAmount>{

	private static final long serialVersionUID = 1L;
	
	public AnyItemAmount(BigDecimal rawValue) {
		super(rawValue);
	}
}