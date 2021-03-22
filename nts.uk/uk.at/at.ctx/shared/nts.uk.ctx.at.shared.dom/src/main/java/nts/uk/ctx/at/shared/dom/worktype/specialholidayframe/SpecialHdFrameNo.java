package nts.uk.ctx.at.shared.dom.worktype.specialholidayframe;

import nts.arc.primitive.IntegerPrimitiveValue;
import nts.arc.primitive.constraint.IntegerRange;

//特別休暇枠No
@IntegerRange(max = 30,min = 1)
public class SpecialHdFrameNo extends IntegerPrimitiveValue<SpecialHdFrameNo> {

	private static final long serialVersionUID = 1L;

	/**
	 * コンストラクタ
	 * @param No
	 */
	public SpecialHdFrameNo(Integer No) {
		super(No);
	}
	
}