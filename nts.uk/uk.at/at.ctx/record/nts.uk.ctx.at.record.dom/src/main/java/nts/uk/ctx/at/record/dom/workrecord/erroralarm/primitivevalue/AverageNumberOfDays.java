package nts.uk.ctx.at.record.dom.workrecord.erroralarm.primitivevalue;

import java.math.BigDecimal;

import nts.arc.primitive.DecimalPrimitiveValue;
import nts.arc.primitive.constraint.DecimalMantissaMaxLength;
import nts.arc.primitive.constraint.DecimalRange;

/**
 * PrimitiveValue: 平均日数
 *
 */
@DecimalRange(min = "0.0", max = "99.9")
@DecimalMantissaMaxLength(1)
public class AverageNumberOfDays extends DecimalPrimitiveValue<AverageNumberOfDays> {

	private static final long serialVersionUID = 1L;

	/**
	 * Instantiates a new times value.
	 *
	 * @param rawValue the raw value
	 */	
	public AverageNumberOfDays(BigDecimal rawValue) {
		super(rawValue);
	}
	
}
