package nts.uk.ctx.at.record.dom.remainingnumber.base;

import nts.arc.primitive.IntegerPrimitiveValue;
import nts.arc.primitive.constraint.IntegerRange;

@IntegerRange(min = 1 , max = 20)
public class SpecialVacationCD extends IntegerPrimitiveValue<SpecialVacationCD>{

	/**
	 *  特別休暇コード
	 */
	private static final long serialVersionUID = 1L;

	public SpecialVacationCD(Integer rawValue) {
		super(rawValue);
		// TODO Auto-generated constructor stub
	}

}
