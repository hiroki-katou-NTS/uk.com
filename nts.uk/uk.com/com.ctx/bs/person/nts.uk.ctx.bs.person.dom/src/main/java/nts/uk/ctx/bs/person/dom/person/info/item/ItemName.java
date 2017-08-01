package nts.uk.ctx.bs.person.dom.person.info.item;

import nts.arc.primitive.StringPrimitiveValue;
import nts.arc.primitive.constraint.CharType;
import nts.arc.primitive.constraint.StringCharType;
import nts.arc.primitive.constraint.StringMaxLength;

@StringMaxLength(value = 30)
@StringCharType(value = CharType.ANY_HALF_WIDTH)
public class ItemName extends StringPrimitiveValue<ItemName>{

	private static final long serialVersionUID = 1L;

	public ItemName(String rawValue) {
		super(rawValue);
	}

}
