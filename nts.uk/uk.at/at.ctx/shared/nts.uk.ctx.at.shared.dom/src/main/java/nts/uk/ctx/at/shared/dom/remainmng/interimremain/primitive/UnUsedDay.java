package nts.uk.ctx.at.shared.dom.remainmng.interimremain.primitive;

import nts.arc.primitive.HalfIntegerPrimitiveValue;
import nts.arc.primitive.constraint.HalfIntegerRange;
/**
 * 未使用日数
 * @author do_dt
 *
 */
@HalfIntegerRange(min=0d, max = 1d)
public class UnUsedDay extends HalfIntegerPrimitiveValue<UnUsedDay>{

	public UnUsedDay(Double rawValue) {
		super(rawValue);
		// TODO Auto-generated constructor stub
	}

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

}
