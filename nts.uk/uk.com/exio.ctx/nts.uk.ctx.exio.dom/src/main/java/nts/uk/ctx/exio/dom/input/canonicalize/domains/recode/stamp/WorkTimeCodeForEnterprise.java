package nts.uk.ctx.exio.dom.input.canonicalize.domains.recode.stamp;

import nts.arc.primitive.constraint.CharType;
import nts.arc.primitive.constraint.StringCharType;
import nts.arc.primitive.constraint.StringMaxLength;
import nts.uk.shr.com.primitive.CodePrimitiveValue;
import nts.uk.shr.com.primitive.ZeroPaddedCode;

import java.io.Serializable;

/**
 * 就業時間帯コード(Enterprise)
 * Enterprise仕様のデータ型
 * Enterpriseでは、EMPTYを"000"として保持しているため
 *
 */
@StringMaxLength(3)
@StringCharType(CharType.ALPHA_NUMERIC)
@ZeroPaddedCode
public class WorkTimeCodeForEnterprise  extends CodePrimitiveValue<WorkTimeCodeForEnterprise> implements Serializable {

	private static final long serialVersionUID = 1L;

	public WorkTimeCodeForEnterprise(String rawValue) {
		super(rawValue);
	}

	public boolean isEmpty(){
		// "000"の場合はempty扱い
		return this.equals("000");
	}
}
