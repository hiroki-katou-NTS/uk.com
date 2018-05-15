package nts.uk.ctx.sys.assist.dom.deletedata;

import nts.arc.primitive.StringPrimitiveValue;
import nts.arc.primitive.constraint.StringMaxLength;

@StringMaxLength(40)
public class SupplementExplanation extends StringPrimitiveValue<SupplementExplanation> {
	
	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;
	
	/**
	 * Instantiates a new supplement explanation.
	 *
	 * @param rawValue the raw value
	 */
	public SupplementExplanation(String rawValue) {
		super(rawValue);
	}
}
