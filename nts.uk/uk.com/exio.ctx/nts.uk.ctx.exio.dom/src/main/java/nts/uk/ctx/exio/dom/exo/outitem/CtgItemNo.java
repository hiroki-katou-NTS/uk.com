package nts.uk.ctx.exio.dom.exo.outitem;

import nts.arc.primitive.StringPrimitiveValue;
import nts.arc.primitive.constraint.StringMaxLength;

/*
 * カテゴリ項目NO
 */
@StringMaxLength(4)
public class CtgItemNo extends StringPrimitiveValue<CtgItemNo> {
	private static final long serialVersionUID = 1L;

	public CtgItemNo(String rawValue) {
		super(rawValue);
	}
}
