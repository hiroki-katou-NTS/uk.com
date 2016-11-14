package nts.uk.ctx.pr.proto.dom.layout.detail;

import nts.arc.primitive.StringPrimitiveValue;
import nts.arc.primitive.constraint.StringMaxLengh;

@StringMaxLengh(3)
public class FormulaCode extends StringPrimitiveValue<FormulaCode> {

	public FormulaCode(String rawValue) {
		super(rawValue);
	}

	private static final long serialVersionUID = 1L;

}
