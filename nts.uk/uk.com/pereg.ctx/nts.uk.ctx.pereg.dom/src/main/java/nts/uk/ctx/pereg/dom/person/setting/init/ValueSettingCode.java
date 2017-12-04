/**
 * 
 */
package nts.uk.ctx.pereg.dom.person.setting.init;

import nts.arc.primitive.StringPrimitiveValue;
import nts.arc.primitive.constraint.StringMaxLength;
import nts.uk.shr.com.primitive.ZeroPaddedCode;
import nts.arc.primitive.constraint.StringCharType;
import nts.arc.primitive.constraint.CharType;
/**
 * 個人情報初期値設定コード
 * @author lanlt
 *
 */
@StringCharType(CharType.ALPHA_NUMERIC)
@StringMaxLength(4)
@ZeroPaddedCode
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
