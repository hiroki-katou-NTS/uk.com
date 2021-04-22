package nts.uk.ctx.at.record.dom.workrecord.erroralarm.primitivevalue;

import java.math.BigDecimal;

import nts.arc.primitive.DecimalPrimitiveValue;
import nts.arc.primitive.constraint.DecimalMantissaMaxLength;
import nts.arc.primitive.constraint.DecimalRange;

/**
 * 
 * チェック上限
 *
 */
@DecimalRange(min = "-999.9", max = "999.9")
@DecimalMantissaMaxLength(1)
public class CheckUpperLimit extends DecimalPrimitiveValue<CheckUpperLimit> {
	private static final long serialVersionUID = 1L;

	/**
	 * Instantiates a new value.
	 *
	 * @param rawValue the raw value
	 */	
	public CheckUpperLimit(BigDecimal rawValue) {
		super(rawValue);
	}
}
