package nts.uk.ctx.at.shared.dom.remainingnumber.common.empinfo.grantremainingdata.daynumber;

import nts.arc.primitive.TimeDurationPrimitiveValue;

/**
 * 休暇未消化時間  
 * @author masaaki_jinno
 *
 */
public class LeaveUndigestTime extends TimeDurationPrimitiveValue<LeaveUndigestTime>{

	private static final long serialVersionUID = 1689575162579361974L;

	public LeaveUndigestTime(int timeAsMinutes) {
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
	 * 休暇未消化時間を加算
	 * @param aLeaveUndigestTime
	 */
	public LeaveUndigestTime add(LeaveUndigestTime aLeaveUndigestTime){
		
		// 加算
		return new LeaveUndigestTime( v() + aLeaveUndigestTime.v() );
	}
}
