package nts.uk.ctx.at.record.dom.monthly.vacation.absenceleave.monthremaindata;

import nts.arc.primitive.HalfIntegerPrimitiveValue;
import nts.arc.primitive.constraint.HalfIntegerRange;
/**
 * 振休発生日数
 * @author do_dt
 *
 */
@HalfIntegerRange(min = 0, max = 999.5)
public class RemainDataDaysMonth extends HalfIntegerPrimitiveValue<RemainDataDaysMonth> {

	public RemainDataDaysMonth(Double rawValue) {
		super(rawValue);
		// TODO Auto-generated constructor stub
	}

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

}
