package nts.uk.ctx.sys.portal.dom.flowmenu;

import nts.arc.primitive.IntegerPrimitiveValue;
import nts.arc.primitive.constraint.IntegerRange;

/**
 * 横縦サイズ
 */
@IntegerRange(min = 1, max = 999)
public class HorizontalAndVerticalSize extends IntegerPrimitiveValue<HorizontalAndVerticalSize> {

	private static final long serialVersionUID = 1L;

	public HorizontalAndVerticalSize(Integer rawValue) {
		super(rawValue);
	}
}
