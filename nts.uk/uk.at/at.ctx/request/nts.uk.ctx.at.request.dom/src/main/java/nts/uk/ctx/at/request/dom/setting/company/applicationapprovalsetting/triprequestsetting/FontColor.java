package nts.uk.ctx.at.request.dom.setting.company.applicationapprovalsetting.triprequestsetting;

import nts.arc.primitive.StringPrimitiveValue;
import nts.arc.primitive.constraint.StringMaxLength;

@StringMaxLength(7)
public class FontColor extends StringPrimitiveValue<FontColor>{

	public FontColor(String rawValue) {
		super(rawValue);
	}

	/**
	 * serialVersionUID
	 */
	private static final long serialVersionUID = 1L;

}
