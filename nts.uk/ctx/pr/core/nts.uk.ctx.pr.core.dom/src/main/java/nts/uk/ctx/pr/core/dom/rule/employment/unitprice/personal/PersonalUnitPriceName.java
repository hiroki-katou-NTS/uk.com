package nts.uk.ctx.pr.core.dom.rule.employment.unitprice.personal;

import nts.arc.primitive.StringPrimitiveValue;
import nts.arc.primitive.constraint.StringMaxLength;

@StringMaxLength(20)
public class PersonalUnitPriceName extends StringPrimitiveValue<PersonalUnitPriceName>{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public PersonalUnitPriceName(String rawValue) {
		super(rawValue);
		// TODO Auto-generated constructor stub
	}

}
