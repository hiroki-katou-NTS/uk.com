package nts.uk.ctx.workflow.dom.agent;

import nts.arc.primitive.StringPrimitiveValue;
import nts.arc.primitive.constraint.StringMaxLength;

/**
 * The Class EmployeeId.
 */
@StringMaxLength(36)
public class EmployeeId extends StringPrimitiveValue<EmployeeId> {

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;

	/**
	 * Instantiates a new employee id.
	 *
	 * @param rawValue the raw value
	 */
	public EmployeeId(String rawValue) {
		super(rawValue);
	}

}
