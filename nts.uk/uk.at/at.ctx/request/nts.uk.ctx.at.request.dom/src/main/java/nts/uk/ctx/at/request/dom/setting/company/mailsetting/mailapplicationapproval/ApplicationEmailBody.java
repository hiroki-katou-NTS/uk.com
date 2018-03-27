package nts.uk.ctx.at.request.dom.setting.company.mailsetting.mailapplicationapproval;

import nts.arc.primitive.PrimitiveValue;
import nts.arc.primitive.StringPrimitiveValue;
import nts.arc.primitive.constraint.StringMaxLength;
@StringMaxLength(1000)
public class ApplicationEmailBody extends StringPrimitiveValue<PrimitiveValue<String>>{
	private static final long serialVersionUID = 1L;

	public ApplicationEmailBody(String rawValue) {
		super(rawValue);
	}
}
