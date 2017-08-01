package nts.uk.ctx.bs.person.dom.person.info.selectionitem;

import nts.arc.primitive.StringPrimitiveValue;
import nts.arc.primitive.constraint.CharType;
import nts.arc.primitive.constraint.StringCharType;
import nts.arc.primitive.constraint.StringMaxLength;

@StringCharType(value = CharType.ANY_HALF_WIDTH)
@StringMaxLength(value = 10)
public class TypeCode extends StringPrimitiveValue<TypeCode>{

	private static final long serialVersionUID = 1L;

	public TypeCode(String rawValue) {
		super(rawValue);
	}

}
