package nts.uk.ctx.at.record.dom.workrecord.erroralarm.schedule.annual;

import java.math.BigDecimal;

import nts.arc.primitive.DecimalPrimitiveValue;
import nts.arc.primitive.constraint.DecimalMantissaMaxLength;
import nts.arc.primitive.constraint.DecimalRange;

/**
 * アラーム日数
 *
 */
@DecimalRange(min = "0.0", max = "999.99")
@DecimalMantissaMaxLength(2)
public class ScheAnnualCheckedTimesValueDay  extends DecimalPrimitiveValue<ScheAnnualCheckedTimesValueDay> {

	private static final long serialVersionUID = 1L;

	/**
	 * Instantiates a new times value.
	 *
	 * @param rawValue the raw value
	 */	
	public ScheAnnualCheckedTimesValueDay(BigDecimal rawValue) {
		super(rawValue);
	}
	
}