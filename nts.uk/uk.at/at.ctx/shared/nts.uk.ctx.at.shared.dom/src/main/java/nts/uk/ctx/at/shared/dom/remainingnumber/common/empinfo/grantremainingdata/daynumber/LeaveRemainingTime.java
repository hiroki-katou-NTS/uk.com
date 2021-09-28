package nts.uk.ctx.at.shared.dom.remainingnumber.common.empinfo.grantremainingdata.daynumber;

import nts.arc.primitive.TimeDurationPrimitiveValue;
import nts.arc.primitive.constraint.TimeRange;

@TimeRange(min = "-999:59", max="999:59")

/**
 * 月別休暇残時間    
 * @author masaaki_jinno
 *
 */
public class LeaveRemainingTime extends TimeDurationPrimitiveValue<LeaveRemainingTime>{

	//private static final long serialVersionUID = -3402887695920983416L;

	private static final long serialVersionUID = 8079704274349021171L;

	public LeaveRemainingTime(int timeAsMinutes) {
		super(timeAsMinutes);
	}

	@Override
	protected Integer reviseRawValue(Integer rawValue) {
		if (rawValue == null) return super.reviseRawValue(0);
		if (rawValue > 999 * 60 + 59) rawValue = 999 * 60 + 59;
		if (rawValue < -(999 * 60 + 59)) rawValue = -(999 * 60 + 59);
		return super.reviseRawValue(rawValue);
	}
	
	/**
	 * 休暇残数（時間）を加算
	 * @param aLeaveRemainingTime
	 */
	public LeaveRemainingTime add(LeaveRemainingTime aLeaveRemainingTime){
		
		// 時間加算
		return new LeaveRemainingTime( v() + aLeaveRemainingTime.v() );
	}
}
