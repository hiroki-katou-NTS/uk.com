package nts.uk.ctx.exio.dom.input.setting;

import nts.arc.primitive.constraint.CharType;
import nts.arc.primitive.constraint.StringCharType;
import nts.arc.primitive.constraint.StringMaxLength;
import nts.uk.shr.com.primitive.CodePrimitiveValue;
import nts.uk.shr.com.primitive.ZeroPaddedCode;

/**
 * 
 * 受入設定コード
 *
 */
@SuppressWarnings("serial")
@StringMaxLength(3)
@StringCharType(CharType.ALPHA_NUMERIC)
@ZeroPaddedCode
public class ExternalImportCode extends CodePrimitiveValue<ExternalImportCode> {

	public ExternalImportCode(String rawValue) {
		super(rawValue);
	}
}
