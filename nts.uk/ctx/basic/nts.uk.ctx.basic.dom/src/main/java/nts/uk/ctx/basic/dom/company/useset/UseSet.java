package nts.uk.ctx.basic.dom.company.useset;

import lombok.AllArgsConstructor;
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
@StringMaxLength(1)
@StringCharType(CharType.NUMERIC)
public class UseSet extends IntegerPrimitiveValue<UseSet>{
	
	/**serialVersionUID	 */
	private static final long serialVersionUID = 1L;
	/**
	 * constructors
	 * @param rawValue
	 */
	public UseSet(Integer rawValue) {
		super(rawValue);
		// TODO Auto-generated constructor stub
	}

	

}
