package nts.uk.ctx.at.function.dom.processexecution;

import nts.arc.primitive.constraint.CharType;
import nts.arc.primitive.constraint.StringCharType;
import nts.arc.primitive.constraint.StringMaxLength;
import nts.uk.shr.com.primitive.CodePrimitiveValue;

/**
 *  任意集計枠コード
 */
@StringMaxLength(3)
@StringCharType(CharType.ALPHA_NUMERIC)
public class AggrFrameCode extends CodePrimitiveValue<AggrFrameCode> {

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;

	/**
	 * コンストラクタ.
	 *
	 * @param code コード
	 */
	public AggrFrameCode(String code) {
		super(code);
	}
}
