package nts.uk.ctx.at.record.dom.monthly.vacation.specialholiday.monthremaindata;

import nts.arc.primitive.IntegerPrimitiveValue;
import nts.arc.primitive.constraint.IntegerRange;
@IntegerRange(min=0, max = 99)
public class UseNumber extends IntegerPrimitiveValue<UseNumber> {

	public UseNumber(Integer rawValue) {
		super(rawValue);
		// TODO Auto-generated constructor stub
	}

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Override
	protected Integer reviseRawValue(Integer rawValue) {
		if (rawValue == null) return super.reviseRawValue(rawValue);
		if (rawValue > 99) rawValue = 99;
		if (rawValue < 0) rawValue = 0;
		return super.reviseRawValue(rawValue);
	}
}
