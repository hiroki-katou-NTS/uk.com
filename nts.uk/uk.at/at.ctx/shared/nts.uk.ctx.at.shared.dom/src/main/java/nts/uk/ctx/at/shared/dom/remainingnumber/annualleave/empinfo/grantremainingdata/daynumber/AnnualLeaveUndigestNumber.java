package nts.uk.ctx.at.shared.dom.remainingnumber.annualleave.empinfo.grantremainingdata.daynumber;

import java.util.Optional;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import nts.uk.ctx.at.shared.dom.remainingnumber.common.empinfo.grantremainingdata.daynumber.LeaveUndigestNumber;

/**
 * 年休未消化数
 * @author masaaki_jinno
 *
 */
@Getter
@Setter
@NoArgsConstructor
public class AnnualLeaveUndigestNumber extends LeaveUndigestNumber {

//	/**
//	 * 日数
//	 */
//	private AnnualLeaveUndigestDayNumber days;
//
//	/**
//	 * 時間
//	 */
//	private Optional<AnnualLeaveUndigestTime> minutes;

	private AnnualLeaveUndigestNumber(double days, Integer minutes) {
		// super(days, minutes);
		this.days = new AnnualLeaveUndigestDayNumber(days);
		this.minutes = minutes != null ? Optional.of(new AnnualLeaveUndigestTime(minutes)) : Optional.empty();
	}
	
	public static AnnualLeaveUndigestNumber createFromJavaType(double days, Integer minutes) {
		return new AnnualLeaveUndigestNumber(days, minutes);
	}
	
	@Override
	public AnnualLeaveUndigestNumber clone() {
		AnnualLeaveUndigestNumber cloned;
		try {
			int int_minutes = 0;
			if ( this.minutes.isPresent() ){
				int_minutes = minutes.get().v();
			}
			
			cloned = AnnualLeaveUndigestNumber.createFromJavaType(
					days.v(), int_minutes);
		}
		catch (Exception e){
			throw new RuntimeException("AnnualLeaveUndigestNumber clone error.");
		}
		return cloned;
	}
	
}
