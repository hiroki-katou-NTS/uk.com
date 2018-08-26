/**
 * 5:44:01 PM Nov 9, 2017
 */
package nts.uk.ctx.at.record.dom.workrecord.erroralarm.primitivevalue;

import java.math.BigDecimal;

import nts.arc.primitive.DecimalPrimitiveValue;
import nts.arc.primitive.constraint.DecimalMantissaMaxLength;
import nts.arc.primitive.constraint.DecimalMaxValue;
import nts.arc.primitive.constraint.DecimalMinValue;

/**
 * @author hungnm
 *
 */
//チェック条件値日数
@DecimalMaxValue("99999999.5")
@DecimalMinValue("-99999999.5")
@DecimalMantissaMaxLength(1)
public class CheckedTimesValueDay  extends DecimalPrimitiveValue<CheckedTimesValueDay> {

	private static final long serialVersionUID = 1L;

	/**
	 * Instantiates a new times value.
	 *
	 * @param rawValue the raw value
	 */	
	public CheckedTimesValueDay(BigDecimal rawValue) {
		super(rawValue);
	}
	
}
