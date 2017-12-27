package nts.uk.ctx.at.shared.dom.calculation.holiday.roundingmonth;

import nts.arc.primitive.PrimitiveValue;
import nts.arc.primitive.StringPrimitiveValue;
import nts.arc.primitive.constraint.StringMaxLength;

/**
 * 
 * @author phongtq
 * 勤怠項目ID
 */

@StringMaxLength(36)
public class TimeItemId extends StringPrimitiveValue<PrimitiveValue<String>> {
	/** serialVersionUID */
	private static final long serialVersionUID = 1L;

	public TimeItemId(String rawValue) {
		super(rawValue);
	}
}