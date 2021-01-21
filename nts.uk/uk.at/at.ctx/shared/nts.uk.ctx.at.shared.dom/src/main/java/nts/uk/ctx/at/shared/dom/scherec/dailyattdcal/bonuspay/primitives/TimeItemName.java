/**
 * 10:26:40 AM Jun 6, 2017
 */
package nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.bonuspay.primitives;

import nts.arc.primitive.StringPrimitiveValue;
import nts.arc.primitive.constraint.StringMaxLength;

/**
 * @author hungnm
 *
 */
// 勤怠項目名称
@StringMaxLength(12)
public class TimeItemName extends StringPrimitiveValue<TimeItemName> {
	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;

	/**
	 * Instantiates a new time item name
	 *
	 * @param rawValue
	 *            the raw value
	 */
	public TimeItemName(String rawValue) {
		super(rawValue);
	}
}
