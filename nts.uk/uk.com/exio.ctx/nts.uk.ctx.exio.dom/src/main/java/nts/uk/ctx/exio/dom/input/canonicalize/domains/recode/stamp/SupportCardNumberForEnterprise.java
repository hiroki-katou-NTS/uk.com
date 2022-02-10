package nts.uk.ctx.exio.dom.input.canonicalize.domains.recode.stamp;


import nts.arc.primitive.StringPrimitiveValue;
import nts.arc.primitive.constraint.CharType;
import nts.arc.primitive.constraint.StringCharType;
import nts.arc.primitive.constraint.StringMaxLength;

/**
 * 応援カード番号(Enterprise)
 * Enterprise仕様のデータ型
 * Enterpriseでは、EMPTYを"      "(半角スペース6桁)として保持しているため
 *
 */
@StringMaxLength(6)
@StringCharType(CharType.NUMERIC)
public class SupportCardNumberForEnterprise extends StringPrimitiveValue<SupportCardNumberForEnterprise> {

	public SupportCardNumberForEnterprise(String rawValue) {
		super(rawValue);
	}

	private static final long serialVersionUID = 1L;

	@Override
	public void validate() {
		// 半角スペース6桁の場合はempty扱いのため検証不要
		if(this.equals("      ")) {
			return;
		}
		super.validate();
	}

	public boolean isEmpty(){
		// 半角スペース6桁の場合はempty扱い
		return this.equals("      ");
	}
}
