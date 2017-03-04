package nts.uk.ctx.basic.dom.organization.position;
/*
 * Code of JobCode
 */

import nts.arc.primitive.constraint.CharType;
import nts.arc.primitive.constraint.StringCharType;
import nts.arc.primitive.constraint.StringMaxLength;
import nts.uk.shr.com.primitive.CodePrimitiveValue;



@StringCharType(CharType.ALPHABET)
@StringMaxLength(10)
public class JobCode extends CodePrimitiveValue<JobCode>{
	
	/** serialVersionUID */
	private static final long serialVersionUID = 1L;

	public JobCode(String rawValue) {
		super(rawValue);
	}

}