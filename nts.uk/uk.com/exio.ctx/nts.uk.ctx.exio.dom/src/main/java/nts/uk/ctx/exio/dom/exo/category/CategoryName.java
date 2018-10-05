package nts.uk.ctx.exio.dom.exo.category;

import nts.arc.primitive.StringPrimitiveValue;
import nts.arc.primitive.constraint.StringMaxLength;

/**
 * 外部出力カテゴリ名
 */
@StringMaxLength(20)
public class CategoryName extends StringPrimitiveValue<CategoryName> {

	private static final long serialVersionUID = 1L;

	public CategoryName(String rawValue) {
		super(rawValue);
	}

}
