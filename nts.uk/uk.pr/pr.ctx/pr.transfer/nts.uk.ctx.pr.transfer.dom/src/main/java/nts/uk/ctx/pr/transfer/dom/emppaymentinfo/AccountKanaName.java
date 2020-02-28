package nts.uk.ctx.pr.transfer.dom.emppaymentinfo;

import nts.arc.primitive.StringPrimitiveValue;
import nts.arc.primitive.constraint.CharType;
import nts.arc.primitive.constraint.StringCharType;
import nts.arc.primitive.constraint.StringMaxLength;

/**
 * 
 * @author HungTT - 口座カナ名
 *
 */
@StringMaxLength(30)
@StringCharType(CharType.KANA)
public class AccountKanaName extends StringPrimitiveValue<AccountKanaName> {
	/**
	 * serialVersionUID
	 */
	private static final long serialVersionUID = 1L;

	public AccountKanaName(String arg0) {
		super(arg0);
	}
}
