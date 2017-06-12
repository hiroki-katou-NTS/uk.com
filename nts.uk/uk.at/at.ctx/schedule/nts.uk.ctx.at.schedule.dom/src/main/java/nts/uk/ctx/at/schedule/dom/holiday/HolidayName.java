/**
 * 4:19:48 PM Jun 12, 2017
 */
package nts.uk.ctx.at.schedule.dom.holiday;

import nts.arc.primitive.StringPrimitiveValue;
import nts.arc.primitive.constraint.CharType;
import nts.arc.primitive.constraint.StringCharType;
import nts.arc.primitive.constraint.StringMaxLength;

/**
 * @author hungnm
 *
 */
@StringCharType(CharType.ANY_HALF_WIDTH)
@StringMaxLength(40)
public class HolidayName extends StringPrimitiveValue<HolidayName>  {
	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;

	/**
	 * Instantiates a new holiday name
	 *
	 * @param rawValue
	 *            the raw value
	 */
	public HolidayName(String rawValue) {
		super(rawValue);
	}
}
