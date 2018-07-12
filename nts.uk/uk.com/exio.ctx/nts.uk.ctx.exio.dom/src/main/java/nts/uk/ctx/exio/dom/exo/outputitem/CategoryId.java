package nts.uk.ctx.exio.dom.exo.outputitem;

import nts.arc.primitive.StringPrimitiveValue;
import nts.arc.primitive.constraint.StringMaxLength;

/*
 * カテゴリID
 */
@StringMaxLength(5)
public class CategoryId extends StringPrimitiveValue<CategoryId> {

	private static final long serialVersionUID = 1L;

	public CategoryId(String rawValue) {
		super(rawValue);
	}
}
