/**
 * 
 */
package nts.uk.ctx.at.auth.dom.wplmanagementauthority;

import java.math.BigDecimal;

import nts.arc.primitive.DecimalPrimitiveValue;
import nts.arc.primitive.IntegerPrimitiveValue;
import nts.arc.primitive.constraint.DecimalMaxValue;
import nts.arc.primitive.constraint.IntegerMaxValue;

/**
 * 
 * @author tutk
 *
 */
@IntegerMaxValue(999)
public class DailyPerformanceFunctionNo extends IntegerPrimitiveValue<DailyPerformanceFunctionNo>{

	private static final long serialVersionUID = 1L;
	
	/**
	 * @param rawValue
	 */
	public DailyPerformanceFunctionNo(int rawValue) {
		super(rawValue);
	}

	

}
