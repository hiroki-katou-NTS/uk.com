package nts.uk.ctx.at.function.dom.annualworkschedule.primitivevalue;

import nts.arc.primitive.StringPrimitiveValue;
import nts.arc.primitive.constraint.StringMaxLength;

@StringMaxLength(12)
public class ItemOutTblBookHeadingName extends StringPrimitiveValue<ItemOutTblBookHeadingName> {

	public ItemOutTblBookHeadingName(String rawValue) {
		super(rawValue);
	}
}
