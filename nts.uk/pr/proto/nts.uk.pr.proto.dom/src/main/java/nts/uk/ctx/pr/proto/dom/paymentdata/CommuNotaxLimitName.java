package nts.uk.ctx.pr.proto.dom.paymentdata;

import nts.arc.primitive.StringPrimitiveValue;
import nts.arc.primitive.constraint.StringMaxLength;

@StringMaxLength(20)
public class CommuNotaxLimitName extends StringPrimitiveValue<CommuNotaxLimitName> {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	/**
	 * Constructs.
	 * 
	 * @param rawValue
	 *            raw value
	 */
	public CommuNotaxLimitName(String rawValue) {
		super(rawValue);
	}	

}
