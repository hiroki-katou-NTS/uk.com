package nts.uk.ctx.pr.transfer.dom.desbank;

import nts.arc.primitive.constraint.CharType;
import nts.arc.primitive.constraint.StringCharType;
import nts.arc.primitive.constraint.StringMaxLength;
import nts.uk.shr.com.primitive.CodePrimitiveValue;

/**
 * 
 * @author HungTT - 委託者コード
 *
 */

@StringMaxLength(10)
@StringCharType(CharType.NUMERIC)
public class EntrustorCode extends CodePrimitiveValue<EntrustorCode> {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public EntrustorCode(String rawValue) {
		super(rawValue);
	}

}
