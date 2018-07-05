package nts.uk.ctx.exio.dom.exo.outputitem;

import nts.arc.primitive.StringPrimitiveValue;
import nts.arc.primitive.constraint.StringMaxLength;

/*
 * カテゴリ項目NO
 */
@StringMaxLength(4)
public class CategoryItemNo extends StringPrimitiveValue<CategoryItemNo> {
	private static final long serialVersionUID = 1L;

	public CategoryItemNo(String rawValue) {
		super(rawValue);
	}
}
