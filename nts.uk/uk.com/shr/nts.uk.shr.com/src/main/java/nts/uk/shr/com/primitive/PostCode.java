package nts.uk.shr.com.primitive;

import nts.arc.primitive.StringPrimitiveValue;
import nts.arc.primitive.constraint.StringRegEx;

@StringRegEx("\\d{7}|(\\d{3}-\\d{4})")
public class PostCode extends StringPrimitiveValue<PostCode>{

	public PostCode(String rawValue) {
		super(rawValue);
	}

}
