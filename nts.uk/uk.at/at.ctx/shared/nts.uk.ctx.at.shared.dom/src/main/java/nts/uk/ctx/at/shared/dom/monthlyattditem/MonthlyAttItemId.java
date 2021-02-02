package nts.uk.ctx.at.shared.dom.monthlyattditem;

import java.math.BigDecimal;

import nts.arc.primitive.DecimalPrimitiveValue;
import nts.arc.primitive.constraint.DecimalMantissaMaxLength;

/**
 * 勤怠項目ID
 * 
 * @author LienPTK
 */
@DecimalMantissaMaxLength(4)
public class MonthlyAttItemId extends DecimalPrimitiveValue<MonthlyAttItemId> {

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;

	/**
	 * Instantiates a new monthly att item id.
	 *
	 * @param rawValue the raw value
	 */
	public MonthlyAttItemId(BigDecimal rawValue) {
		super(rawValue);
	}

}