package nts.uk.ctx.at.shared.dom.yearholidaygrant;

import nts.arc.primitive.IntegerPrimitiveValue;
import nts.arc.primitive.constraint.StringMaxLength;

@StringMaxLength(3)
public class GrantDays extends IntegerPrimitiveValue<GrantDays> {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public GrantDays(Integer rawValue) {
		super(rawValue);
		// TODO Auto-generated constructor stub
	}

}
