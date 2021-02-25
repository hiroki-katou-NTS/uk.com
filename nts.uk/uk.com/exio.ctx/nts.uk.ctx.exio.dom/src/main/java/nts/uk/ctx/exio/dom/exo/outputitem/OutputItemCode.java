package nts.uk.ctx.exio.dom.exo.outputitem;

import nts.arc.primitive.StringPrimitiveValue;
import nts.arc.primitive.constraint.CharType;
import nts.arc.primitive.constraint.StringCharType;
import nts.arc.primitive.constraint.StringMaxLength;
import nts.uk.shr.com.primitive.ZeroPaddedCode;

/**
 * @author son.tc
 *
 */
@StringMaxLength(4)
@StringCharType(CharType.NUMERIC)
@ZeroPaddedCode
public class OutputItemCode extends StringPrimitiveValue<OutputItemCode> {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public OutputItemCode(String rawValue) {
		super(rawValue);
	}
}
