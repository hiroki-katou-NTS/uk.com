package nts.uk.ctx.pr.report.dom.payment.comparing.setting;

import nts.arc.primitive.StringPrimitiveValue;
import nts.arc.primitive.constraint.CharType;
import nts.arc.primitive.constraint.StringCharType;
import nts.arc.primitive.constraint.StringMaxLength;

@StringMaxLength(7)
@StringCharType(CharType.ANY_HALF_WIDTH)
public class MinusBackColor extends StringPrimitiveValue<MinusBackColor>{

	private static final long serialVersionUID = 1L;

	public MinusBackColor(String rawValue) {
		super(rawValue);
	}

	

}
