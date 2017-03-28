package nts.uk.ctx.basic.dom.company.address;

import nts.arc.primitive.StringPrimitiveValue;
import nts.arc.primitive.constraint.StringMaxLength;
/**
 * 
 * @author lanlt
 *
 */
@StringMaxLength(40)
public class Address2 extends StringPrimitiveValue<Address2> {
	/**serialVersionUID	 */
	private static final long serialVersionUID = 1L;
	
   /**
    * contructors
    * @param rawValue raw value
    */
	public Address2(String rawValue) {
		super(rawValue);
		// TODO Auto-generated constructor stub
	}
}
