/**
 * 
 */
package nts.uk.ctx.bs.person.dom.person.layoutitemclassification.definition;

import java.math.BigDecimal;

import nts.arc.primitive.DecimalPrimitiveValue;
import nts.arc.primitive.constraint.DecimalRange;

/**
 * @author laitv
 *
 */
@DecimalRange(min= "0" , max= "9999")
public class LayoutDisPOrder extends DecimalPrimitiveValue<LayoutDisPOrder>{

	public LayoutDisPOrder(BigDecimal rawValue) {
		super(rawValue);
	}

}
