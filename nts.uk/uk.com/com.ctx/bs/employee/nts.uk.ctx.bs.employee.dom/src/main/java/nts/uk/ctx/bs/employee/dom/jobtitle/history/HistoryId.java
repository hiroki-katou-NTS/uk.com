package nts.uk.ctx.bs.employee.dom.jobtitle.history;

import nts.arc.primitive.StringPrimitiveValue;
import nts.arc.primitive.constraint.StringMaxLength;

/**
 * The Class JobTitleHistoryId.
 */
@StringMaxLength(36)
public class HistoryId extends StringPrimitiveValue<HistoryId> {
	
	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;

	/**
	 * Instantiates a new job title history id.
	 *
	 * @param rawValue the raw value
	 */
	public HistoryId(String rawValue) {
		super(rawValue);
	}
}