package nts.uk.ctx.at.request.dom.application;

import nts.arc.primitive.StringPrimitiveValue;
import nts.arc.primitive.constraint.StringMaxLength;

@StringMaxLength(400)
public class AppReason extends StringPrimitiveValue<AppReason> {

	private static final long serialVersionUID = 1L;

	public AppReason(String rawValue) {
		super(rawValue);
	}
	

}
