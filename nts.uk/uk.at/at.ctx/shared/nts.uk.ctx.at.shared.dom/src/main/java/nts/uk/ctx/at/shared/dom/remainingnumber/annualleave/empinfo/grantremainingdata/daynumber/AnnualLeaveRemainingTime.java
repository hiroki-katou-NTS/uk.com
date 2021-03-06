package nts.uk.ctx.at.shared.dom.remainingnumber.annualleave.empinfo.grantremainingdata.daynumber;

import nts.arc.primitive.constraint.TimeRange;
import nts.uk.ctx.at.shared.dom.remainingnumber.common.empinfo.grantremainingdata.daynumber.LeaveRemainingTime;

@TimeRange(min = "-999:59", max="999:59")

/**
 * 年休残時間 
 * @author masaaki_jinno
 *
 */
public class AnnualLeaveRemainingTime extends LeaveRemainingTime{

	private static final long serialVersionUID = -3402887695920983416L;

	public AnnualLeaveRemainingTime(int timeAsMinutes) {
		super(timeAsMinutes);
	}

//	@Override
//	protected Integer reviseRawValue(Integer rawValue) {
//		if (rawValue == null) return super.reviseRawValue(0);
//		if (rawValue > 999 * 60 + 59) rawValue = 999 * 60 + 59;
//		if (rawValue < -(999 * 60 + 59)) rawValue = -(999 * 60 + 59);
//		return super.reviseRawValue(rawValue);
//	}
	
	/**
	 * Convert to format time 
	 * @return <String> format time HH:MM
	 */
	public String getTimeWithFormat(){
		return this.hour() + ":" + (this.minute() < 10 ? "0" + this.minute() : this.minute());
	}
}
