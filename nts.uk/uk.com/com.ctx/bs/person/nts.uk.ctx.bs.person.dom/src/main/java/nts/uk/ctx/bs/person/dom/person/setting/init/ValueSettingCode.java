/**
 * 
 */
package nts.uk.ctx.bs.person.dom.person.setting.init;

import nts.arc.primitive.StringPrimitiveValue;
import nts.arc.primitive.constraint.StringMaxLength;

/**
 * @author lanlt
 *
 */
@StringMaxLength(17)
public class ValueSettingCode  extends StringPrimitiveValue<ValueSettingCode>{

	public ValueSettingCode(String rawValue) {
		super(rawValue);
		// TODO Auto-generated constructor stub
	}

	/**
	 * serialVersionUID
	 */
	private static final long serialVersionUID = 1L;

}
