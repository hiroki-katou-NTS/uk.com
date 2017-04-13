package nts.uk.ctx.pr.core.dom.rule.employment.unitprice.personal;

import nts.arc.primitive.constraint.CharType;
import nts.arc.primitive.constraint.StringCharType;
import nts.arc.primitive.constraint.StringMaxLength;
import nts.arc.primitive.constraint.StringRegEx;
import nts.uk.shr.com.primitive.CodePrimitiveValue;

@StringMaxLength(2)
@StringCharType(CharType.NUMERIC)
@StringRegEx("^[1-9][0-9]?$")
public class PersonalUnitPriceCode extends CodePrimitiveValue<PersonalUnitPriceCode>{

	private static final long serialVersionUID = 1L;
	/**
	 * 
	 * @param rawValue
	 */
	public PersonalUnitPriceCode(String rawValue) {
		super(rawValue);
	}
}
