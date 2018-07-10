package nts.uk.ctx.at.shared.dom.remainingnumber.interimremain.primitive;

import nts.arc.primitive.HalfIntegerPrimitiveValue;
import nts.arc.primitive.constraint.HalfIntegerRange;
/**
 * 必要日数
 * @author do_dt
 *
 */
@HalfIntegerRange(min=0d, max = 1d)
public class UnOffsetDay extends HalfIntegerPrimitiveValue<UnOffsetDay>{

	public UnOffsetDay(Double rawValue) {
		super(rawValue);
		// TODO Auto-generated constructor stub
	}

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

}
