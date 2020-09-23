package nts.uk.ctx.at.record.dom.employmentinfoterminal.nrlremote;

import nts.arc.primitive.StringPrimitiveValue;
import nts.arc.primitive.constraint.StringMaxLength;

/**
 * @author ThanhNX
 *
 *         設定値
 */
@StringMaxLength(50)
public class SettingValue extends StringPrimitiveValue<SettingValue> {

	private static final long serialVersionUID = 1L;

	public SettingValue(String rawValue) {
		super(rawValue);
	}

}
