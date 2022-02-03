package nts.uk.ctx.exio.dom.input.canonicalize.domains.recode.stamp.enterprise.pv;

import nts.arc.primitive.StringPrimitiveValue;
import nts.arc.primitive.constraint.CharType;
import nts.arc.primitive.constraint.StringCharType;
import nts.arc.primitive.constraint.StringMaxLength;
import nts.uk.shr.com.primitive.ZeroPaddedCode;

/**
 * E版打刻データ場所コード
 * Enterprise仕様のデータ型
 * Enterpriseでは、EMPTYを"0000"として保持しているため
 *
 */
@StringMaxLength(4)
@StringCharType(CharType.ALPHA_NUMERIC)
@ZeroPaddedCode
public class EnterpriseStampDataLocationCode extends StringPrimitiveValue<EnterpriseStampDataLocationCode> {

	public EnterpriseStampDataLocationCode(String rawValue) { super(rawValue); }

	public boolean isEmpty(){
		return v().equals("0000");
	}
}
