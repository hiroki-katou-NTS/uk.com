package nts.uk.ctx.at.request.dom.application;

import nts.arc.primitive.StringPrimitiveValue;
import nts.arc.primitive.constraint.StringMaxLength;

@StringMaxLength(200)
public class ApproverReason extends StringPrimitiveValue<ApproverReason> {

	private static final long serialVersionUID = 1L;

	public ApproverReason(String rawValue) {
		super(rawValue);
	}
	

}
