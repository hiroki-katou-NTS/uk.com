package nts.uk.ctx.hr.develop.dom.humanresourcedevevent;

import nts.arc.primitive.constraint.CharType;
import nts.arc.primitive.constraint.StringCharType;
import nts.arc.primitive.constraint.StringMaxLength;
import nts.uk.shr.com.primitive.CodePrimitiveValue;

/**
 * プログラム名
 *
 */
@StringCharType(CharType.ALPHA_NUMERIC)
@StringMaxLength(50)
public class ProgramName extends CodePrimitiveValue<ProgramName>{
	/**
	 * serialVersionUID
	 */
	private static final long serialVersionUID = 1L;

	public ProgramName(String rawValue) {
		super(rawValue);
	}
}
