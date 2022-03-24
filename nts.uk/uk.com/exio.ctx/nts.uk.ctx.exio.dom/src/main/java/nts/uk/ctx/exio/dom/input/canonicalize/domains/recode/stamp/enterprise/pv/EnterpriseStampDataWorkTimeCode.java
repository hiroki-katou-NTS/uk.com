package nts.uk.ctx.exio.dom.input.canonicalize.domains.recode.stamp.enterprise.pv;

import nts.arc.primitive.constraint.CharType;
import nts.arc.primitive.constraint.StringCharType;
import nts.arc.primitive.constraint.StringMaxLength;
import nts.uk.shr.com.primitive.CodePrimitiveValue;
import nts.uk.shr.com.primitive.ZeroPaddedCode;

import java.io.Serializable;

/**
 * E版打刻データ就業時間帯コード
 * Enterprise仕様のデータ型
 * Enterpriseでは、EMPTYを"000"として保持しているため
 */
@SuppressWarnings("serial")
@StringMaxLength(3)
@StringCharType(CharType.ALPHA_NUMERIC)
@ZeroPaddedCode
public class EnterpriseStampDataWorkTimeCode extends CodePrimitiveValue<EnterpriseStampDataWorkTimeCode> implements Serializable {

	public EnterpriseStampDataWorkTimeCode(String rawValue) {
		super(rawValue);
	}

	public boolean isEmpty(){
		return v().equals("000");
	}
}
