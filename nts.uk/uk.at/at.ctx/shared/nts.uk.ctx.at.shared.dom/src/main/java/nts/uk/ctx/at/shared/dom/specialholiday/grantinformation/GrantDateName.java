package nts.uk.ctx.at.shared.dom.specialholiday.grantinformation;

import nts.arc.primitive.PrimitiveValue;
import nts.arc.primitive.StringPrimitiveValue;
import nts.arc.primitive.constraint.StringMaxLength;

@StringMaxLength(20)
public class GrantDateName extends StringPrimitiveValue<PrimitiveValue<String>> {
	/** serialVersionUID */
	private static final long serialVersionUID = 1L;
	
	public GrantDateName(String rawValue) {
		super(rawValue);
	}
}
