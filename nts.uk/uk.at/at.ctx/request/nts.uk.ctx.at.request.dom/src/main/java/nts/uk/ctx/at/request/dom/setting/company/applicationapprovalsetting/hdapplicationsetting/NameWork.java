package nts.uk.ctx.at.request.dom.setting.company.applicationapprovalsetting.hdapplicationsetting;

import nts.arc.primitive.StringPrimitiveValue;
import nts.arc.primitive.constraint.StringMaxLength;

@StringMaxLength(12)
public class NameWork extends StringPrimitiveValue<NameWork>{

	public NameWork(String rawValue) {
		super(rawValue);
	}

	/**
	 * serialVersionUID
	 */
	private static final long serialVersionUID = 1L;
	
}
