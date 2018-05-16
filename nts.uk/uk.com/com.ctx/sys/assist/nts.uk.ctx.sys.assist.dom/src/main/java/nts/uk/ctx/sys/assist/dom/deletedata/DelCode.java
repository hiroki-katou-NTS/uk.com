package nts.uk.ctx.sys.assist.dom.deletedata;

import nts.arc.primitive.StringPrimitiveValue;
import nts.arc.primitive.constraint.StringMaxLength;

@StringMaxLength(3)
public class DelCode extends StringPrimitiveValue<DelCode> {
	
	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;
	
	/**
	 * Instantiates a new deletion code.
	 *
	 * @param rawValue the raw value
	 */
	public DelCode(String rawValue) {
		super(rawValue);
	}
}
