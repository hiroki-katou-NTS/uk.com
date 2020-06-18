package nts.uk.ctx.at.shared.dom.remainingnumber.common.empinfo.grantremainingdata.daynumber;

import nts.arc.primitive.HalfIntegerPrimitiveValue;
import nts.arc.primitive.constraint.HalfIntegerRange;

/**
 * 休暇未消化日数
 * @author masaaki_jinno
 *
 */
//public class LeaveUndigestDayNumber {
@HalfIntegerRange(min = -999.5 , max = 999.5)
public class LeaveUndigestDayNumber extends HalfIntegerPrimitiveValue<LeaveRemainingDayNumber>{

	private static final long serialVersionUID = 4447237140240165037L;

	public LeaveUndigestDayNumber(Double rawValue) {
		super(rawValue);
	}

	@Override
	protected Double reviseRawValue(Double rawValue) {
		if (rawValue == null) return super.reviseRawValue(rawValue);
		if (rawValue > 999.5) rawValue = 999.5;
		if (rawValue < -999.5) rawValue = -999.5;
		return super.reviseRawValue(rawValue);
	}

	/**
	 * 休暇未消化日数を加算
	 * @param aLeaveRemainingDayNumber
	 */
	public LeaveUndigestDayNumber add(LeaveUndigestDayNumber aLeaveUndigestDayNumber){
		
		// 加算
		return new LeaveUndigestDayNumber( v() + aLeaveUndigestDayNumber.v() );
	}
}
