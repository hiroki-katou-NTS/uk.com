package nts.uk.ctx.pereg.dom.person.info.selectionitem;

import nts.arc.primitive.StringPrimitiveValue;
import nts.arc.primitive.constraint.StringMaxLength;

@StringMaxLength(value = 50)
public class EnumName extends StringPrimitiveValue<EnumName>{

	private static final long serialVersionUID = 1L;

	public EnumName(String rawValue) {
		super(rawValue);
	}

}
