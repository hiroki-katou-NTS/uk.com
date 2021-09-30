package nts.uk.ctx.at.shared.dom.remainingnumber.common;

import nts.arc.primitive.HalfIntegerPrimitiveValue;
import nts.arc.primitive.constraint.HalfIntegerRange;

/**
 * 
 * @author hayata_maekawa
 *	日別休暇使用日数
 */


@HalfIntegerRange(min = 0, max = 1.0)
public class DayOfVacationUse extends HalfIntegerPrimitiveValue<DayOfVacationUse> {

	/**
	 * 日数
	 */
	private static final long serialVersionUID = 1L;
	
	public DayOfVacationUse(Double rawValue) {
		super(rawValue);
		
	}

}
