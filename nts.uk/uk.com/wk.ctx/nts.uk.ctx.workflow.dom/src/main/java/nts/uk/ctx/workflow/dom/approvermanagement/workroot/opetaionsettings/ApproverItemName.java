package nts.uk.ctx.workflow.dom.approvermanagement.workroot.opetaionsettings;

import nts.arc.primitive.StringPrimitiveValue;
import nts.arc.primitive.constraint.StringMaxLength;

/**
 * 自分の承認者項目名称	
 */
@StringMaxLength(24)
public class ApproverItemName extends StringPrimitiveValue<ApproverItemName> {

	private static final long serialVersionUID = 2824293763897916189L;

	public ApproverItemName(String rawValue) {
		super(rawValue);
	}
}
