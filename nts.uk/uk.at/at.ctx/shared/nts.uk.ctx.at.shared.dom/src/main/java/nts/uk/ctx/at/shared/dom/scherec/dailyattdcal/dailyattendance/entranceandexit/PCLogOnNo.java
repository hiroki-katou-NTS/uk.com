package nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.entranceandexit;

import nts.arc.primitive.IntegerPrimitiveValue;
import nts.arc.primitive.constraint.IntegerRange;

@IntegerRange(min = 1, max = 2)
public class PCLogOnNo extends IntegerPrimitiveValue<PCLogOnNo> {

	/***/
	private static final long serialVersionUID = 1L;

	public PCLogOnNo(Integer rawValue) {
		super(rawValue);
	}

}
