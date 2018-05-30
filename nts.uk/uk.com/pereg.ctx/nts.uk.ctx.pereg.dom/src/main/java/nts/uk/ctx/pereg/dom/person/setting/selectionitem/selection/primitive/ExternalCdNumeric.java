package nts.uk.ctx.pereg.dom.person.setting.selectionitem.selection.primitive;

import nts.arc.primitive.constraint.CharType;
import nts.arc.primitive.constraint.StringCharType;
import nts.arc.primitive.constraint.StringMaxLength;

@StringMaxLength(10)
@StringCharType(CharType.NUMERIC)
public class ExternalCdNumeric extends ExternalCD {

	private static final long serialVersionUID = 1L;

	public ExternalCdNumeric(String rawValue) {
		super(rawValue);
		// TODO Auto-generated constructor stub
	}

}
