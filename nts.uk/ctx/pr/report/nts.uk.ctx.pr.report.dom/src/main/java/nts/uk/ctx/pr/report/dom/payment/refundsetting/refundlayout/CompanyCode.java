package nts.uk.ctx.pr.report.dom.payment.refundsetting.refundlayout;

import nts.arc.primitive.constraint.CharType;
import nts.arc.primitive.constraint.StringCharType;
import nts.arc.primitive.constraint.StringMaxLength;
import nts.uk.shr.com.primitive.CodePrimitiveValue;

@StringMaxLength(4)
@StringCharType(value = CharType.NUMERIC)
public class CompanyCode extends CodePrimitiveValue<CompanyCode>  {

	private static final long serialVersionUID = 1L;

	public CompanyCode(String rawValue) {
		super(rawValue);
	}

}
