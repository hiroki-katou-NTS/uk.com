package nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailycalprocess.calculation.other;

import java.math.BigDecimal;

import nts.arc.primitive.DecimalPrimitiveValue;
import nts.arc.primitive.constraint.DecimalMaxValue;
import nts.arc.primitive.constraint.DecimalMinValue;

@DecimalMaxValue("1.0")
@DecimalMinValue("0")
public class SubHolOccurrenceDayNumber extends DecimalPrimitiveValue<SubHolOccurrenceDayNumber>{
	
	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;

	public SubHolOccurrenceDayNumber(BigDecimal rawValue) {
		super(rawValue);
	}

}
