package nts.uk.ctx.hr.develop.dom.humanresourcedevevent;

import nts.arc.primitive.constraint.CharType;
import nts.arc.primitive.constraint.StringCharType;
import nts.arc.primitive.constraint.StringMaxLength;
import nts.uk.shr.com.primitive.CodePrimitiveValue;
/**
 * 備考
 *
 */
@StringCharType(CharType.ALPHA_NUMERIC)
@StringMaxLength(100)
public class Memo extends CodePrimitiveValue<Memo>{
	/**
	 * serialVersionUID
	 */
	private static final long serialVersionUID = 1L;

	public Memo(String rawValue) {
		super(rawValue);
	}
}
