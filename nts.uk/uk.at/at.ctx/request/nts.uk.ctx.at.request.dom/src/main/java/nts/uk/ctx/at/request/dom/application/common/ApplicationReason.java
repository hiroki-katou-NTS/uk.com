package nts.uk.ctx.at.request.dom.application.common;

import nts.arc.primitive.StringPrimitiveValue;
import nts.arc.primitive.constraint.StringMaxLength;

@StringMaxLength(400)
public class ApplicationReason extends StringPrimitiveValue<ApplicationReason> {

	private static final long serialVersionUID = 1L;

	public ApplicationReason(String rawValue) {
		super(rawValue);
	}
	

}
