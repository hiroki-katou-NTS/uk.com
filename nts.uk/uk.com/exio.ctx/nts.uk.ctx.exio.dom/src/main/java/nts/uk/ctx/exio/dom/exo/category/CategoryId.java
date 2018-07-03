package nts.uk.ctx.exio.dom.exo.category;

import nts.arc.primitive.StringPrimitiveValue;
import nts.arc.primitive.constraint.StringMaxLength;

@StringMaxLength(36)
public class CategoryId extends StringPrimitiveValue<CategoryId> {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public CategoryId(String rawValue) {
		super(rawValue);
	}

}
