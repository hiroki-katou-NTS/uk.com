package nts.uk.ctx.sys.assist.dom.deletedata;

import nts.arc.primitive.StringPrimitiveValue;
import nts.arc.primitive.constraint.StringMaxLength;

@StringMaxLength(1000)
public class BusinessName extends StringPrimitiveValue<BusinessName> {
	
	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;
	
	/**
	 * Instantiates a new business name.
	 *
	 * @param rawValue the raw value
	 */
	public BusinessName(String rawValue) {
		super(rawValue);
	}
}
