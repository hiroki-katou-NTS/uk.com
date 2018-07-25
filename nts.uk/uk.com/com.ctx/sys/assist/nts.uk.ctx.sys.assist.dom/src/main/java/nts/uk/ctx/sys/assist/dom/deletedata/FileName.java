package nts.uk.ctx.sys.assist.dom.deletedata;

import nts.arc.primitive.StringPrimitiveValue;
import nts.arc.primitive.constraint.StringMaxLength;

@StringMaxLength(1000)
public class FileName extends StringPrimitiveValue<FileName> {
	
	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;
	
	/**
	 * Instantiates a new file name.
	 *
	 * @param rawValue the raw value
	 */
	public FileName(String rawValue) {
		super(rawValue);
	}
}
