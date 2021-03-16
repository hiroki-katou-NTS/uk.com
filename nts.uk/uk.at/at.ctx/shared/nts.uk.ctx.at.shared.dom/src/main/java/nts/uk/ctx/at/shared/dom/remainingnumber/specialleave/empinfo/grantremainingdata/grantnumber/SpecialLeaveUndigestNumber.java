package nts.uk.ctx.at.shared.dom.remainingnumber.specialleave.empinfo.grantremainingdata.grantnumber;

import java.util.Optional;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import nts.uk.ctx.at.shared.dom.remainingnumber.common.empinfo.grantremainingdata.daynumber.LeaveUndigestNumber;

/**
 * 特休未消化数
 * @author masaaki_jinno
 *
 */
@Getter
@Setter
@NoArgsConstructor
public class SpecialLeaveUndigestNumber extends LeaveUndigestNumber {

//	/**
//	 * 日数
//	 */
//	private SpecialLeaveUndigestDayNumber days;
//
//	/**
//	 * 時間
//	 */
//	private Optional<SpecialLeaveUndigestTime> minutes;

	private SpecialLeaveUndigestNumber(double days, Integer minutes) {
		// super(days, minutes);
		this.days = new SpecialLeaveUndigestDayNumber(days);
		this.minutes = minutes != null ? Optional.of(new SpecialLeaveUndigestTime(minutes)) : Optional.empty();
	}
	
	public static SpecialLeaveUndigestNumber createFromJavaType(double days, Integer minutes) {
		return new SpecialLeaveUndigestNumber(days, minutes);
	}
	
	@Override
	public SpecialLeaveUndigestNumber clone() {
		SpecialLeaveUndigestNumber cloned;
		try {
			int int_minutes = 0;
			if ( this.minutes.isPresent() ){
				int_minutes = minutes.get().v();
			}
			
			cloned = SpecialLeaveUndigestNumber.createFromJavaType(
					days.v(), int_minutes);
		}
		catch (Exception e){
			throw new RuntimeException("SpecialLeaveUndigestNumber clone error.");
		}
		return cloned;
	}
	
}
