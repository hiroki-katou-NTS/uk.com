package nts.uk.ctx.sys.portal.dom.flowmenu;

import nts.arc.primitive.StringPrimitiveValue;
import nts.arc.primitive.constraint.StringMaxLength;

@StringMaxLength(20)
public class DisplayName extends StringPrimitiveValue<DisplayName> {

	private static final long serialVersionUID = 1L;

	public DisplayName(String rawValue) {
		super(rawValue);
	}
}
