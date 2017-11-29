package nts.uk.ctx.bs.employee.dom.workplace.affiliate;

import nts.arc.primitive.constraint.CharType;
import nts.arc.primitive.constraint.StringCharType;
import nts.arc.primitive.constraint.StringMaxLength;
import nts.uk.shr.com.primitive.CodePrimitiveValue;

/**
 * 
 * 場所コード
 */
@StringCharType(CharType.ALPHA_NUMERIC)
@StringMaxLength(10)
public class LocationCode extends CodePrimitiveValue<LocationCode>{
	/**
	 * serialVersionUID
	 */
	private static final long serialVersionUID = 1L;

	public LocationCode(String rawValue) {
		super(rawValue);
	}
}
