package nts.uk.ctx.at.record.dom.workrecord.erroralarm.primitivevalue;

import java.math.BigDecimal;

import nts.arc.primitive.DecimalPrimitiveValue;
import nts.arc.primitive.constraint.DecimalMantissaMaxLength;
import nts.arc.primitive.constraint.DecimalRange;

/**
 * チェック上限日数
 *
 */
@DecimalRange(min = "0.0", max = "99.9")
@DecimalMantissaMaxLength(1)
public class CheckUpperLimitDay  extends DecimalPrimitiveValue<CheckUpperLimitDay> {

	private static final long serialVersionUID = 1L;

	/**
	 * Instantiates a new value.
	 *
	 * @param rawValue the raw value
	 */	
	public CheckUpperLimitDay(BigDecimal rawValue) {
		super(rawValue);
	}
	
}