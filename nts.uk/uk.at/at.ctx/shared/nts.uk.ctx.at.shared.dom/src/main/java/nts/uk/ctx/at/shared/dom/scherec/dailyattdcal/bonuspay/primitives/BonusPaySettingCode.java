/**
 * 10:27:53 AM Jun 6, 2017
 */
package nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.bonuspay.primitives;

import nts.arc.primitive.constraint.CharType;
import nts.arc.primitive.constraint.StringCharType;
import nts.arc.primitive.constraint.StringMaxLength;
import nts.uk.shr.com.primitive.CodePrimitiveValue;

/**
 * @author hungnm
 */
// 加給設定コード
@StringCharType(CharType.ALPHA_NUMERIC)
@StringMaxLength(3)
public class BonusPaySettingCode extends CodePrimitiveValue<BonusPaySettingCode> {

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;

	/**
	 * Instantiates a new bonus pay setting code
	 *
	 * @param rawValue
	 *            the raw value
	 */
	public BonusPaySettingCode(String rawValue) {
		super(rawValue);
	}
}
