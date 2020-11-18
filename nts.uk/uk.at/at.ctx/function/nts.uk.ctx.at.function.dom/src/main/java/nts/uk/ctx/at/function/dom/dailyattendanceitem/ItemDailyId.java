package nts.uk.ctx.at.function.dom.dailyattendanceitem;

import java.math.BigDecimal;

import nts.arc.primitive.DecimalPrimitiveValue;
import nts.arc.primitive.constraint.DecimalMantissaMaxLength;

/**
 * 勤怠項目ID
 *
 * @author phith
 */
@DecimalMantissaMaxLength(4)
public class ItemDailyId extends DecimalPrimitiveValue<ItemDailyId> {

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;

	/**
	 * Instantiates a new item daily id.
	 *
	 * @param rawValue the raw value
	 */
	public ItemDailyId(BigDecimal rawValue) {
		super(rawValue);
	}

}
