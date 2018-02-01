package nts.uk.ctx.at.request.dom.setting.company.applicationapprovalsetting.triprequestsetting;

import nts.arc.primitive.StringPrimitiveValue;
import nts.arc.primitive.constraint.StringMaxLength;

@StringMaxLength(50)
public class Comment extends StringPrimitiveValue<Comment>{

	public Comment(String rawValue) {
		super(rawValue);
	}

	/**
	 * serialVersionUID
	 */
	private static final long serialVersionUID = 1L;

}
