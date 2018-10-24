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

	@Override
	protected Double reviseRawValue(Double rawValue) {
		if (rawValue == null) return super.reviseRawValue(rawValue);
		if (rawValue > 999.5) rawValue = 999.5;
		if (rawValue < 0.0) rawValue = 0.0;
		return super.reviseRawValue(rawValue);
	}
}
