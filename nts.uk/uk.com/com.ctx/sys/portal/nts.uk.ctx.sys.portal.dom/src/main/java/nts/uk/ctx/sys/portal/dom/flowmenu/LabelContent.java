package nts.uk.ctx.sys.portal.dom.flowmenu;

import nts.arc.primitive.StringPrimitiveValue;
import nts.arc.primitive.constraint.StringMaxLength;

/**
 * ラベル内容
 */
@StringMaxLength(100)
public class LabelContent extends StringPrimitiveValue<LabelContent> {

	private static final long serialVersionUID = 1L;

	public LabelContent(String rawValue) {
		super(rawValue);
	}
}
