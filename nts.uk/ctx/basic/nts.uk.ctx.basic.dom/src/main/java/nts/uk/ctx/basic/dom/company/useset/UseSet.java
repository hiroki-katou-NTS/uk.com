package nts.uk.ctx.basic.dom.company.useset;

import nts.arc.primitive.StringPrimitiveValue;
import nts.arc.primitive.constraint.CharType;
import nts.arc.primitive.constraint.StringCharType;
import nts.arc.primitive.constraint.StringMaxLength;
/**
 * 
 * @author lanlt
 *
 */
@StringMaxLength(1)
@StringCharType(CharType.NUMERIC)
public class UseSet extends StringPrimitiveValue<UseSet>{
	
	/**serialVersionUID	 */
	private static final long serialVersionUID = 1L;
	/**
	 * constructors
	 * @param rawValue
	 */
	public UseSet(String rawValue) {
		super(rawValue);
		// TODO Auto-generated constructor stub
	}

	

}
