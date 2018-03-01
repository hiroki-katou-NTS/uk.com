package nts.uk.ctx.at.request.dom.setting.request.application.comment;

import nts.arc.primitive.StringPrimitiveValue;
import nts.arc.primitive.constraint.StringMaxLength;

@StringMaxLength(7)
public class CommentFontColor extends StringPrimitiveValue<CommentFontColor> {

	/**
	 * UID
	 */
	private static final long serialVersionUID = 1L;

	public CommentFontColor(String rawValue) {
		super(rawValue);
	}

}
