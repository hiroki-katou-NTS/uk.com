package nts.uk.ctx.at.request.dom.setting.request.gobackdirectlycommon.primitive;

import nts.arc.primitive.StringPrimitiveValue;
import nts.arc.primitive.constraint.StringMaxLength;

@StringMaxLength(6)
public class CommentFontColor extends StringPrimitiveValue<CommentFontColor> {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public CommentFontColor(String rawValue) {
		super(rawValue);
	}

}
