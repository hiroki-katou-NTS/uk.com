package nts.uk.ctx.pereg.dom.person.setting.selectionitem.selection;

import nts.arc.primitive.StringPrimitiveValue;
import nts.arc.primitive.constraint.CharType;
import nts.arc.primitive.constraint.StringCharType;
import nts.arc.primitive.constraint.StringMaxLength;

@StringMaxLength(10)
@StringCharType(CharType.ALPHABET)
public class ExternalCD extends StringPrimitiveValue<ExternalCD>{

	private static final long serialVersionUID = 1L;

	public ExternalCD(String rawValue) {
		super(rawValue);
	}

}
