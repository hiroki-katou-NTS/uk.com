/**
 * 
 */
package nts.uk.ctx.at.record.dom.workrecord.authormanage;

import java.math.BigDecimal;

import nts.arc.primitive.DecimalPrimitiveValue;
import nts.arc.primitive.constraint.DecimalMaxValue;

/**
 * @author danpv
 *
 */
@DecimalMaxValue("999")
public class DailyPerformanceFunctionNo extends DecimalPrimitiveValue<DailyPerformanceFunctionNo>{

	private static final long serialVersionUID = 1L;
	
	/**
	 * @param rawValue
	 */
	public DailyPerformanceFunctionNo(BigDecimal rawValue) {
		super(rawValue);
	}

	

}
