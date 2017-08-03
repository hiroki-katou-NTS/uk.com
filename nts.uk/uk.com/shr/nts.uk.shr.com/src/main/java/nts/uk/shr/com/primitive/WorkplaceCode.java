package nts.uk.shr.com.primitive;

import nts.arc.primitive.constraint.CharType;
import nts.arc.primitive.constraint.StringCharType;
import nts.arc.primitive.constraint.StringMaxLength;
import nts.arc.primitive.constraint.StringRegEx;

@StringMaxLength(value = 10)
@StringRegEx("^[a-zA-Z0-9_-]{1,10}$")
public class WorkplaceCode extends CodePrimitiveValue<WorkplaceCode> {

	public WorkplaceCode(String rawValue) {
		super(rawValue);
	}

}
