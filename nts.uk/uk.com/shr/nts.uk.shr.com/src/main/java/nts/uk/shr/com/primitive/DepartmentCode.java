package nts.uk.shr.com.primitive;

import nts.arc.primitive.constraint.StringMaxLength;
import nts.arc.primitive.constraint.StringRegEx;

@StringMaxLength(value = 10)
@StringRegEx("[a-zA-Z0-9_-]+")
public class DepartmentCode extends CodePrimitiveValue<DepartmentCode> {

	public DepartmentCode(String rawValue) {
		super(rawValue);
	}

}
