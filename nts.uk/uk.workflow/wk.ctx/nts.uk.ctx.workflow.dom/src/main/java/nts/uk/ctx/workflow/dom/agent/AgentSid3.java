package nts.uk.ctx.workflow.dom.agent;

import nts.arc.primitive.StringPrimitiveValue;
import nts.arc.primitive.constraint.StringMaxLength;

/**
 * The Class AgentSid3.
 */
@StringMaxLength(36)
public class AgentSid3 extends StringPrimitiveValue<EmployeeId> {

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;

	/**
	 * Instantiates a new agent sid
	 *
	 * @param rawValue the raw value
	 */
	public AgentSid3(String rawValue) {
		super(rawValue);
	}

}