package nts.uk.ctx.at.shared.dom.remainingnumber.specialleave.empinfo.grantremainingdata.grantnumber;

import nts.arc.primitive.HalfIntegerPrimitiveValue;
import nts.arc.primitive.constraint.HalfIntegerRange;

//Type - 特別休暇残数用付与日数
@HalfIntegerRange(min = 0, max = 999.5)
public class DayNumberOfGrant extends HalfIntegerPrimitiveValue<DayNumberOfGrant>{

	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	public DayNumberOfGrant(Double rawValue) {
		super(rawValue);
		// TODO Auto-generated constructor stub
	}

	

	
}
