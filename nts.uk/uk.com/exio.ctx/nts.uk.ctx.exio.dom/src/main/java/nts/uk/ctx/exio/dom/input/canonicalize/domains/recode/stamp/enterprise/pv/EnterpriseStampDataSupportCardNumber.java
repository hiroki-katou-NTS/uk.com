package nts.uk.ctx.exio.dom.input.canonicalize.domains.recode.stamp.enterprise.pv;


import nts.arc.primitive.StringPrimitiveValue;
import nts.arc.primitive.constraint.CharType;
import nts.arc.primitive.constraint.StringCharType;
import nts.arc.primitive.constraint.StringMaxLength;

/**
 * E版打刻データ応援カード番号
 * Enterprise仕様のデータ型
 * Enterpriseでは、EMPTYを"      "(半角スペース6桁)として保持しているため
 */
@SuppressWarnings("serial")
@StringMaxLength(6)
@StringCharType(CharType.NUMERIC)
public class EnterpriseStampDataSupportCardNumber extends StringPrimitiveValue<EnterpriseStampDataSupportCardNumber> {

	public EnterpriseStampDataSupportCardNumber(String rawValue) {
		super(rawValue);
	}

	@Override
	public void validate() {
		// emptyの場合は検証不要
		if(this.isEmpty()) {
			return;
		}
		super.validate();
	}

	public boolean isEmpty(){
		return v().equals("      ");
	}
}
