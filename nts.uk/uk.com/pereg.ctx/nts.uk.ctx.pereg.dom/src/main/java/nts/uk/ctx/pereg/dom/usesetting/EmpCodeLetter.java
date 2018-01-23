package nts.uk.ctx.pereg.dom.usesetting;

import nts.arc.primitive.StringPrimitiveValue;
import nts.arc.primitive.constraint.StringMaxLength;

@StringMaxLength(11)
public class EmpCodeLetter extends StringPrimitiveValue<EmpCodeLetter> {
	private static final long serialVersionUID = 1L;

	public EmpCodeLetter(String rawValue) {
		super(rawValue);
	}
}
