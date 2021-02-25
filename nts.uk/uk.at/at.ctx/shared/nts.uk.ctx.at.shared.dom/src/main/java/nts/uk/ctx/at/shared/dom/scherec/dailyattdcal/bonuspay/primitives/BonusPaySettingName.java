/**
 * 10:28:16 AM Jun 6, 2017
 */
package nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.bonuspay.primitives;

import nts.arc.primitive.StringPrimitiveValue;
import nts.arc.primitive.constraint.StringMaxLength;

/**
 * @author hungnm
 *
 */
//加給設定名称
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
