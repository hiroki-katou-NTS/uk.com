package nts.uk.ctx.exio.dom.input.canonicalize.domains.recode.stamp;

import nts.arc.primitive.StringPrimitiveValue;
import nts.arc.primitive.constraint.CharType;
import nts.arc.primitive.constraint.StringCharType;
import nts.arc.primitive.constraint.StringMaxLength;
import nts.uk.shr.com.primitive.ZeroPaddedCode;

/**
 * 勤務場所コード(Enterprise)
 * Enterprise仕様のデータ型
 * Enterpriseでは、EMPTYを"0000"として保持しているため
 *
 */
@StringMaxLength(4)
@StringCharType(CharType.ALPHA_NUMERIC)
@ZeroPaddedCode
public class WorkLocationCDForEnterprise extends StringPrimitiveValue<WorkLocationCDForEnterprise> {

	private static final long serialVersionUID = 1L;

	public WorkLocationCDForEnterprise(String rawValue) { super(rawValue); }

	public boolean isEmpty(){
		// "000"の場合はempty扱い
		return this.equals("0000");
	}
}
