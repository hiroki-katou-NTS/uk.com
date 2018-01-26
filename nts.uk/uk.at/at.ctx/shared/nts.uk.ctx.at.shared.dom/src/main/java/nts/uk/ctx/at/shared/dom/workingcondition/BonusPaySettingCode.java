package nts.uk.ctx.at.shared.dom.workingcondition;

import nts.arc.primitive.StringPrimitiveValue;
import nts.arc.primitive.constraint.CharType;
import nts.arc.primitive.constraint.StringCharType;
import nts.arc.primitive.constraint.StringMaxLength;
import nts.uk.shr.com.primitive.ZeroPaddedCode;

/**
 * The Class BonusPaySettingCode.
 */
// 加給設定コード
@StringCharType(CharType.ALPHA_NUMERIC)
@StringMaxLength(3)
@ZeroPaddedCode
public class BonusPaySettingCode extends StringPrimitiveValue<BonusPaySettingCode>{

	/**
	 * Instantiates a new bonus pay setting code.
	 *
	 * @param rawValue the raw value
	 */
	public BonusPaySettingCode(String rawValue) {
		super(rawValue);
	}

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 6880012464907935240L;

}
