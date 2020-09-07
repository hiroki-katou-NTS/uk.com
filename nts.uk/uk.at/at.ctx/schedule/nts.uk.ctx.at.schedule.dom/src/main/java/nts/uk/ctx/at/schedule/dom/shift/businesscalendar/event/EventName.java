/**
 * 4:19:33 PM Jun 12, 2017
 */
package nts.uk.ctx.at.schedule.dom.shift.businesscalendar.event;

import nts.arc.primitive.StringPrimitiveValue;
import nts.arc.primitive.constraint.StringMaxLength;

/**
 * 行事名称
 * @author hungnm
 *
 */
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
