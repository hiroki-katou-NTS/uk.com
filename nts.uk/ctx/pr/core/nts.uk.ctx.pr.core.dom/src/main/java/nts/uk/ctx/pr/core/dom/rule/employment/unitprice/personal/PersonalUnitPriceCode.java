package nts.uk.ctx.pr.core.dom.rule.employment.unitprice.personal;

import nts.arc.primitive.constraint.StringMaxLength;
import nts.uk.shr.com.primitive.CodePrimitiveValue;

@StringMaxLength(2)
public class PersonalUnitPriceCode extends CodePrimitiveValue<PersonalUnitPriceCode>{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public PersonalUnitPriceCode(String rawValue) {
		super(rawValue);
		// TODO Auto-generated constructor stub
	}

}
