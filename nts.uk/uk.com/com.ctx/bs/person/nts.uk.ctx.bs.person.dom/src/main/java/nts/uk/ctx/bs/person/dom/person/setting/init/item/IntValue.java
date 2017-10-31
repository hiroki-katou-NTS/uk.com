/**
 * 
 */
package nts.uk.ctx.bs.person.dom.person.setting.init.item;

import java.math.BigDecimal;

import nts.arc.primitive.DecimalPrimitiveValue;
import nts.arc.primitive.constraint.DecimalRange;

/**
 * IntValue
 * @author lanlt
 *
 */
@DecimalRange(max = "30", min = "0")
public class IntValue extends DecimalPrimitiveValue<IntValue>{

	public IntValue(BigDecimal rawValue) {
		super(rawValue);
		// TODO Auto-generated constructor stub
	}

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

}
