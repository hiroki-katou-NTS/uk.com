package nts.uk.ctx.workflow.dom.approvermanagement.workroot.operationsettings;

import nts.arc.primitive.StringPrimitiveValue;
import nts.arc.primitive.constraint.StringMaxLength;

/**
 * 自分の承認者入力注意
 */
@StringMaxLength(400)
public class ApproverInputCareful extends StringPrimitiveValue<ApproverInputCareful> {

	private static final long serialVersionUID = 1L;

	public ApproverInputCareful(String rawValue) {
		super(rawValue);
	}
}
