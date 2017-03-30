package nts.uk.ctx.basic.dom.system.bank.linebank;

import nts.arc.primitive.StringPrimitiveValue;
import nts.arc.primitive.constraint.CharType;
import nts.arc.primitive.constraint.StringCharType;
import nts.arc.primitive.constraint.StringMaxLength;

@StringMaxLength(20)
@StringCharType(CharType.ALPHA_NUMERIC)
/**
 * 
 * @author sonnh1
 *
 */
public class LineBankName extends StringPrimitiveValue<LineBankName> {
	/**
	 * serialVersionUID
	 */
	private static final long serialVersionUID = 1L;

	public LineBankName(String arg0) {
		super(arg0);
	}
}
