package nts.uk.ctx.pereg.dom.person.info.category;

import nts.arc.primitive.constraint.CharType;
import nts.arc.primitive.constraint.StringCharType;
import nts.arc.primitive.constraint.StringMaxLength;
import nts.uk.shr.com.primitive.CodePrimitiveValue;

@StringMaxLength(value = 7)
@StringCharType(value = CharType.ALPHA_NUMERIC)
public class CategoryCode extends CodePrimitiveValue<CategoryCode> {

	private static final long serialVersionUID = 1L;

	public CategoryCode(String rawValue) {
		super(rawValue);
	}
}
