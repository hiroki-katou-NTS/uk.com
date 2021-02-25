package nts.uk.ctx.sys.assist.dom.storage;

import nts.arc.primitive.StringPrimitiveValue;
import nts.arc.primitive.constraint.StringMaxLength;

@StringMaxLength(2000)
public class ProcessingContent extends StringPrimitiveValue<ProcessingContent> {
	
	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;
	
	/**
	 * Instantiates a new processing content.
	 *
	 * @param rawValue the raw value
	 */
	public ProcessingContent(String rawValue) {
		super(rawValue);
	}
}
