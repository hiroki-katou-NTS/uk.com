package nts.uk.ctx.sys.assist.dom.deletedata;

import nts.arc.primitive.StringPrimitiveValue;
import nts.arc.primitive.constraint.StringMaxLength;

@StringMaxLength(30)
public class DelName extends StringPrimitiveValue<DelName> {
	
	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;
	
	/**
	 * Instantiates a new deletion name.
	 *
	 * @param rawValue the raw value
	 */
	public DelName(String rawValue) {
		super(rawValue);
	}
}
