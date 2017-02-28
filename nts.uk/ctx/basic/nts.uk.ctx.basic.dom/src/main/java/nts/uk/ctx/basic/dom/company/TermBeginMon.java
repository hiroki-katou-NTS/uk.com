package nts.uk.ctx.basic.dom.company;

import nts.arc.primitive.IntegerPrimitiveValue;
import nts.arc.primitive.StringPrimitiveValue;
import nts.arc.primitive.constraint.CharType;
import nts.arc.primitive.constraint.StringCharType;
import nts.arc.primitive.constraint.StringMaxLength;
/**
 * 
 * @author lanlt
 *
 */
@StringMaxLength(2)
@StringCharType(CharType.NUMERIC)
public class TermBeginMon extends IntegerPrimitiveValue<TermBeginMon>{
	/**serialVersionUID*/
	private static final long serialVersionUID = 1L;
	/**
	 * @param rawValue
	 */
	public TermBeginMon(int rawValue) {
		super(rawValue);
		// TODO Auto-generated constructor stub
	}



}
