package nts.uk.ctx.basic.dom.company;

import nts.arc.primitive.StringPrimitiveValue;
import nts.arc.primitive.constraint.StringMaxLength;
/**
 * 
 * @author lanlt
 *
 */
@StringMaxLength(20)
public class FaxNo extends StringPrimitiveValue<FaxNo>{
	/**serialVersionUID	 */
	private static final long serialVersionUID = 1L;
	/**
	 * contructor
	 * @param rawValue raw value
	 *  */
	public FaxNo(String rawValue) {
		super(rawValue);
		// TODO Auto-generated constructor stub
	}
}
