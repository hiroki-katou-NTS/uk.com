package nts.uk.ctx.at.record.dom.remainingnumber.specialleave.empinfo.grantremainingdata.grantnumber;

import nts.arc.primitive.IntegerPrimitiveValue;
import nts.arc.primitive.constraint.IntegerRange;

//日数 - 特別休暇付与日数
@IntegerRange(min = 0 , max = 366)
public class DayNumberOfGrant extends IntegerPrimitiveValue<DayNumberOfGrant>{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public DayNumberOfGrant(Integer rawValue) {
		super(rawValue);
	}

}
