package nts.uk.ctx.sys.assist.dom.storage;

import nts.arc.primitive.StringPrimitiveValue;
import nts.arc.primitive.constraint.StringMaxLength;

@StringMaxLength(2000)
public class ErrorContent extends StringPrimitiveValue<ErrorContent> {
	
	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;
	
	/**
	 * Instantiates a new error content.
	 *
	 * @param rawValue the raw value
	 */
	public ErrorContent(String rawValue) {
		super(rawValue);
	}
}
