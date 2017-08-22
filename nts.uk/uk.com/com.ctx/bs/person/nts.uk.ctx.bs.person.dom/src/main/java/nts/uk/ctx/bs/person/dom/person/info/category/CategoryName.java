package nts.uk.ctx.bs.person.dom.person.info.category;

import nts.arc.primitive.StringPrimitiveValue;
import nts.arc.primitive.constraint.StringMaxLength;

@StringMaxLength(value = 30)
public class CategoryName extends StringPrimitiveValue<CategoryName>{

	private static final long serialVersionUID = 1L;

	public CategoryName(String rawValue) {
		super(rawValue);
	}

}
