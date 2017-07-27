/**
 * 
 */
package nts.uk.ctx.bs.person.dom.person.layoutitemclassification;

import java.math.BigDecimal;
import java.math.BigInteger;

import nts.arc.primitive.DecimalPrimitiveValue;
import nts.arc.primitive.constraint.DecimalMantissaMaxLength;
import nts.arc.primitive.constraint.DecimalRange;

/**
 * @author laitv
 *
 */
@DecimalRange(min= "1" , max = "9999")
public class DisPOrder extends  DecimalPrimitiveValue<DisPOrder>{

	public DisPOrder(BigDecimal rawValue) {
		super(rawValue);
	}

	
}
	