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

}
