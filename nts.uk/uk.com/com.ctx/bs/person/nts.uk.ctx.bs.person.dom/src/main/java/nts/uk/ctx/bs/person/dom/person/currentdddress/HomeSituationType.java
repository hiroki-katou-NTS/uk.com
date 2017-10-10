package nts.uk.ctx.bs.person.dom.person.currentdddress;

import nts.arc.primitive.StringPrimitiveValue;
import nts.arc.primitive.constraint.StringMaxLength;

@StringMaxLength(30)
public class HomeSituationType extends StringPrimitiveValue<HomeSituationType>{

	public HomeSituationType(String rawValue) {
		super(rawValue);
		// TODO Auto-generated constructor stub
	}

}
