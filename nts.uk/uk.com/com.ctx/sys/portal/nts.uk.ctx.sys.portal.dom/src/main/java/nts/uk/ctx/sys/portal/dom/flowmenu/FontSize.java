package nts.uk.ctx.sys.portal.dom.flowmenu;

import nts.arc.primitive.IntegerPrimitiveValue;
import nts.arc.primitive.constraint.IntegerRange;

/**
 * 文字のサイズ
 */
@IntegerRange(min = 1, max = 99)
public class FontSize extends IntegerPrimitiveValue<FontSize> {

	private static final long serialVersionUID = 1L;

	public FontSize(Integer rawValue) {
		super(rawValue);
	}
}
