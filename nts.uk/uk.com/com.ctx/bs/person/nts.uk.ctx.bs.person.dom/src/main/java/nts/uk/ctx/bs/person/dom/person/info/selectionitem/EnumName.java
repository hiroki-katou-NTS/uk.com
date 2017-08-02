package nts.uk.ctx.bs.person.dom.person.info.selectionitem;

import nts.arc.primitive.StringPrimitiveValue;
import nts.arc.primitive.constraint.CharType;
import nts.arc.primitive.constraint.StringCharType;
import nts.arc.primitive.constraint.StringMaxLength;

@StringCharType(value = CharType.ANY_HALF_WIDTH)
@StringMaxLength(value = 10)
public class EnumName extends StringPrimitiveValue<EnumName>{

	private static final long serialVersionUID = 1L;

	public EnumName(String rawValue) {
		super(rawValue);
	}

}
