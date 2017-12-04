package nts.uk.ctx.pereg.dom.person.info.item;

import nts.arc.primitive.constraint.CharType;
import nts.arc.primitive.constraint.StringCharType;
import nts.arc.primitive.constraint.StringMaxLength;
import nts.uk.shr.com.primitive.CodePrimitiveValue;

@StringMaxLength(value = 7)
@StringCharType(value = CharType.ALPHA_NUMERIC)
public class ItemCode extends CodePrimitiveValue<ItemCode> {

	private static final long serialVersionUID = 1L;

	public ItemCode(String rawValue) {
		super(rawValue);
	}
	
}
