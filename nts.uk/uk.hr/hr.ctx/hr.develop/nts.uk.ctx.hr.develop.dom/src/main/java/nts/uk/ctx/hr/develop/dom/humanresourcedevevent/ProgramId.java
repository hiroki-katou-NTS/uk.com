package nts.uk.ctx.hr.develop.dom.humanresourcedevevent;

import nts.arc.primitive.constraint.CharType;
import nts.arc.primitive.constraint.StringCharType;
import nts.arc.primitive.constraint.StringMaxLength;
import nts.uk.shr.com.primitive.CodePrimitiveValue;

/**
 * プログラムID
 *
 */
@StringCharType(CharType.ALPHA_NUMERIC)
@StringMaxLength(10)
public class ProgramId  extends CodePrimitiveValue<ProgramId>{
	/**
	 * serialVersionUID
	 */
	private static final long serialVersionUID = 1L;

	public ProgramId(String rawValue) {
		super(rawValue);
	}
}
