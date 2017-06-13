/**
 * 4:19:33 PM Jun 12, 2017
 */
package nts.uk.ctx.at.schedule.dom.event;

import nts.arc.primitive.StringPrimitiveValue;
import nts.arc.primitive.constraint.CharType;
import nts.arc.primitive.constraint.StringCharType;
import nts.arc.primitive.constraint.StringMaxLength;

/**
 * @author hungnm
 *
 */
@StringCharType(CharType.ANY_HALF_WIDTH)
@StringMaxLength(20)
public class EventName extends StringPrimitiveValue<EventName> {
	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;

	/**
	 * Instantiates a new event name
	 *
	 * @param rawValue
	 *            the raw value
	 */
	public EventName(String rawValue) {
		super(rawValue);
	}
}
