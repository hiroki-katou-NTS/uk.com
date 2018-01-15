/**
 * 
 */
package nts.uk.ctx.pereg.dom.person.info.order;

import nts.arc.primitive.IntegerPrimitiveValue;
import nts.arc.primitive.constraint.IntegerRange;

@IntegerRange(max = 9999, min = 1)
public class DispOrder extends IntegerPrimitiveValue<DispOrder> {

	private static final long serialVersionUID = 1L;

	public DispOrder(Integer rawValue) {
		super(rawValue);
	}

}
