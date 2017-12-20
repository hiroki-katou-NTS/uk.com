package nts.uk.ctx.pereg.dom.person.info.category;

import nts.arc.primitive.StringPrimitiveValue;
import nts.arc.primitive.constraint.StringMaxLength;
@StringMaxLength(value = 30)
//@StringRegEx("[a-zA-Z0-9\\u3040-\\u309F\\u30A0-\\u30FF-\\uFF61-\\uFF9F-\\u3040-\\u309F\\u30A0-\\u30FF-\\u3040-\\u309F-\\u4E00-\\u9FBF]+$")
public class CategoryName extends StringPrimitiveValue<CategoryName> {

	private static final long serialVersionUID = 1L;

	public CategoryName(String rawValue) {
		super(rawValue);
	}

}
