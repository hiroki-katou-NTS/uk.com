/**
 * 10:28:16 AM Jun 6, 2017
 */
package nts.uk.ctx.at.shared.dom.bonuspay.primitives;

import nts.arc.primitive.StringPrimitiveValue;
import nts.arc.primitive.constraint.CharType;
import nts.arc.primitive.constraint.StringCharType;
import nts.arc.primitive.constraint.StringMaxLength;

/**
 * @author hungnm
 *
 */
//加給設定名称
@StringCharType(CharType.ANY_HALF_WIDTH)
@StringMaxLength(20)
public class BonusPaySettingName extends StringPrimitiveValue<BonusPaySettingName> {

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;

	/**
	 * Instantiates a new bonus pay setting name
	 *
	 * @param rawValue
	 *            the raw value
	 */
	public BonusPaySettingName(String rawValue) {
		super(rawValue);
	}
}
