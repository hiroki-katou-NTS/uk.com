package nts.uk.ctx.pereg.dom.person.setting.init.item;

import nts.arc.primitive.StringPrimitiveValue;
import nts.arc.primitive.constraint.StringMaxLength;
import nts.uk.ctx.pereg.dom.person.setting.init.ValueSettingName;

/**
 * StringValue
 * @author lanlt
 *
 */
@StringMaxLength(1000)
public class StringValue extends StringPrimitiveValue<ValueSettingName>{

	public StringValue(String rawValue) {
		super(rawValue);
		// TODO Auto-generated constructor stub
	}

	/**
	 * serialVersionUID
	 */
	private static final long serialVersionUID = 1L;

}
