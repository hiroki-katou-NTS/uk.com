/**
 * 
 */
package nts.uk.ctx.pereg.dom.person.setting.init;

import nts.arc.primitive.StringPrimitiveValue;
import nts.arc.primitive.constraint.StringMaxLength;

/**
 * @author lanlt
 *
 */
@StringMaxLength(30)
public class ValueSettingName extends StringPrimitiveValue<ValueSettingName> {

	public ValueSettingName(String rawValue) {
		super(rawValue);
		// TODO Auto-generated constructor stub
	}

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

}
