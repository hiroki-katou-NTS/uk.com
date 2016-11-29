package nts.uk.ctx.pr.proto.dom.paymentdata;

import nts.arc.primitive.StringPrimitiveValue;
import nts.arc.primitive.constraint.StringMaxLength;

@StringMaxLength(2)
public class CommuNotaxLimitCode extends StringPrimitiveValue<CommuNotaxLimitCode> {

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
	public CommuNotaxLimitCode(String rawValue) {
		super(rawValue);
	}	

}
