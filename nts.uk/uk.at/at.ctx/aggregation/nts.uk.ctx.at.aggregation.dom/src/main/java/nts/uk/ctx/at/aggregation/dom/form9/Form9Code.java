package nts.uk.ctx.at.aggregation.dom.form9;

import nts.arc.primitive.StringPrimitiveValue;
import nts.arc.primitive.constraint.CharType;
import nts.arc.primitive.constraint.StringCharType;
import nts.arc.primitive.constraint.StringMaxLength;
import nts.uk.shr.com.primitive.ZeroPaddedCode;

/**
 * 様式９のコード
 * UKDesign.ドメインモデル.NittsuSystem.UniversalK.就業.contexts.予実集計.様式９.様式９のコード
 * @author lan_lt
 *
 */
@StringMaxLength(10)
@StringCharType(CharType.ALPHA_NUMERIC)
@ZeroPaddedCode
public class Form9Code extends StringPrimitiveValue<Form9Code> {

	/** serialVersionUID **/
	private static final long serialVersionUID = 1L;

	public Form9Code(String rawValue) {
		super(rawValue);
	}

}
