package nts.uk.ctx.at.function.dom.annualworkschedule.primitivevalue;

import nts.arc.primitive.constraint.CharType;
import nts.arc.primitive.constraint.StringCharType;
import nts.arc.primitive.constraint.StringMaxLength;
import nts.uk.shr.com.primitive.CodePrimitiveValue;

@StringMaxLength(2)
@StringCharType(CharType.NUMERIC)
public class ItemOutTblBookCode extends CodePrimitiveValue<ItemOutTblBookCode>{

	public ItemOutTblBookCode(String rawValue) {
		super(rawValue);
	}
}
