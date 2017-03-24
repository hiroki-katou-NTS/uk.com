package nts.uk.ctx.basic.dom.company.address;

import nts.arc.primitive.StringPrimitiveValue;
import nts.arc.primitive.constraint.StringMaxLength;
/**
 * 
 * @author lanlt
 *
 */
@StringMaxLength(120)
public class AddressKana1 extends StringPrimitiveValue<AddressKana1>{

	/**serialVersionUID*/
	private static final long serialVersionUID = 1L;
	/**
	 * contructors
	 * @param rawValue
	 */
	public AddressKana1(String rawValue) {
		super(rawValue);
		// TODO Auto-generated constructor stub
	}


}
