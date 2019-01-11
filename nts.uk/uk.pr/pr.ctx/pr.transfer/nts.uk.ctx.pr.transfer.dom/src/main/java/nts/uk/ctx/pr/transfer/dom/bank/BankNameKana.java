package nts.uk.ctx.pr.transfer.dom.bank;

import nts.arc.primitive.StringPrimitiveValue;
import nts.arc.primitive.constraint.CharType;
import nts.arc.primitive.constraint.StringCharType;
import nts.arc.primitive.constraint.StringMaxLength;

/**
 * 
 * @author HungTT - 銀行カナ名
 *
 */
@StringMaxLength(15)
@StringCharType(CharType.ANY_HALF_WIDTH)
public class BankNameKana extends StringPrimitiveValue<BankNameKana> {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public BankNameKana(String rawValue) {
		super(rawValue);
		// TODO Auto-generated constructor stub
	}

}
