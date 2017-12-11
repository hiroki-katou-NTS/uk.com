package nts.uk.ctx.pereg.dom.person.info.selectionitem;

import nts.arc.primitive.StringPrimitiveValue;
import nts.arc.primitive.constraint.StringMaxLength;

@StringMaxLength(value = 50)
public class TypeCode extends StringPrimitiveValue<TypeCode>{

	private static final long serialVersionUID = 1L;

	public TypeCode(String rawValue) {
		super(rawValue);
	}

}
