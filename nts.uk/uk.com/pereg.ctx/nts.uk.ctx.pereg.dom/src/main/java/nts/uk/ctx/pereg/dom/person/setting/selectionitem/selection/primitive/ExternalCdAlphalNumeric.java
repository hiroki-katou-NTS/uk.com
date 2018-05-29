package nts.uk.ctx.pereg.dom.person.setting.selectionitem.selection.primitive;

import nts.arc.primitive.constraint.CharType;
import nts.arc.primitive.constraint.StringCharType;
import nts.arc.primitive.constraint.StringMaxLength;

@StringMaxLength(10)
@StringCharType(CharType.ALPHA_NUMERIC)
public class ExternalCdAlphalNumeric extends ExternalCD {
	private static final long serialVersionUID = 1L;

	public ExternalCdAlphalNumeric(String rawValue) {
		super(rawValue);
	}

}
