package nts.uk.ctx.basic.dom.company.organization.workplace;

import nts.arc.primitive.StringPrimitiveValue;
import nts.arc.primitive.constraint.StringMaxLength;

@StringMaxLength(120)
public class WorkplaceName extends StringPrimitiveValue<WorkplaceName> {
	
	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;
	
	/**
	 * Instantiates a new employee code.
	 *
	 * @param rawValue the raw value
	 */
	public WorkplaceName(String rawValue) {
		super(rawValue);
	}


}