package nts.uk.ctx.pr.report.dom.payment.comparing.masterpage;

import nts.arc.primitive.constraint.CharType;
import nts.arc.primitive.constraint.StringCharType;
import nts.arc.primitive.constraint.StringMaxLength;
import nts.uk.shr.com.primitive.CodePrimitiveValue;

@StringMaxLength(10)
@StringCharType(CharType.ALPHA_NUMERIC)
public class DepartmentCode extends CodePrimitiveValue<DepartmentCode> {

	private static final long serialVersionUID = 1L;

	public DepartmentCode(String rawValue) {
		super(rawValue);
	}

}
