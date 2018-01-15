package nts.uk.ctx.at.shared.dom.specialholiday.grantdate;

import nts.arc.primitive.PrimitiveValue;
import nts.arc.primitive.StringPrimitiveValue;
import nts.arc.primitive.constraint.StringMaxLength;

@StringMaxLength(20)
public class PersonalGrantDateName extends StringPrimitiveValue<PrimitiveValue<String>> {
	/** serialVersionUID */
	private static final long serialVersionUID = 1L;
	
	public PersonalGrantDateName(String rawValue) {
		super(rawValue);
	} 

}
