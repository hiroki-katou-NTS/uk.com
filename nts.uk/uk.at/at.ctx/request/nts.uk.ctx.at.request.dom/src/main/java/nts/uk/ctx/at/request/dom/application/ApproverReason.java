package nts.uk.ctx.at.request.dom.application;

import nts.arc.primitive.StringPrimitiveValue;
import nts.arc.primitive.constraint.StringMaxLength;
/**
 * 承認理由
 * @author Doan Duy Hung
 *
 */
@StringMaxLength(400)
public class ApproverReason extends StringPrimitiveValue<ApproverReason> {

	private static final long serialVersionUID = 1L;

	public ApproverReason(String rawValue) {
		super(rawValue);
	}
	

}
