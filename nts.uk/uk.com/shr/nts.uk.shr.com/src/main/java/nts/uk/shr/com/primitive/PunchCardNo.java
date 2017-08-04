package nts.uk.shr.com.primitive;

import nts.arc.primitive.constraint.CharType;
import nts.arc.primitive.constraint.StringCharType;
import nts.arc.primitive.constraint.StringMaxLength;
import nts.arc.primitive.constraint.StringRegEx;

@StringMaxLength(20)
@StringRegEx("^[a-zA-Z0-9\"#\\$%&\\(~\\|\\{\\}\\[\\]@:`\\*\\+\\?;\\/_\\-><\\)]{1,20}$")
public class PunchCardNo extends CodePrimitiveValue<PunchCardNo> {

	public PunchCardNo(String rawValue) {
		super(rawValue);
	}

}
