package nts.uk.ctx.at.function.dom.alarm.checkcondition.agree36;

import java.math.BigDecimal;

import nts.arc.primitive.DecimalPrimitiveValue;
import nts.arc.primitive.constraint.DecimalRange;
/**
 * ３６協定１ヶ月時間
 * @author yennth
 *
 */
@DecimalRange(min="44640", max="44640")
public class OverTime extends DecimalPrimitiveValue<OverTime>{

	public OverTime(BigDecimal rawValue) {
		super(rawValue);
	}

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

}
