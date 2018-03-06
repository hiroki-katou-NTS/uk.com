package nts.uk.ctx.exio.dom.exi.dataformat;

import nts.arc.primitive.PrimitiveValue;
import nts.arc.primitive.StringPrimitiveValue;
import nts.arc.primitive.constraint.StringMaxLength;

/**
 * 固定値の値
 */
@StringMaxLength(30)
public class ValueOfFixed extends StringPrimitiveValue<PrimitiveValue<String>>{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * Contructor
	 * 
	 * @param rawValue
	 */
	public ValueOfFixed(String rawValue) {
		super(rawValue);
		// TODO Auto-generated constructor stub
	}
}
