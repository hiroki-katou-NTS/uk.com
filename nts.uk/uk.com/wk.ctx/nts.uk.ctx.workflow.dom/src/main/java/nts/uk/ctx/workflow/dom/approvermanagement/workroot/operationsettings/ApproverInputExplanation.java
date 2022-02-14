package nts.uk.ctx.workflow.dom.approvermanagement.workroot.operationsettings;

import nts.arc.primitive.StringPrimitiveValue;
import nts.arc.primitive.constraint.StringMaxLength;

/**
 * 自分の承認者入力説明
 */
@StringMaxLength(1000)
public class ApproverInputExplanation extends StringPrimitiveValue<ApproverInputExplanation> {

	private static final long serialVersionUID = 1L;

	public ApproverInputExplanation(String rawValue) {
		super(rawValue);
	}
}
