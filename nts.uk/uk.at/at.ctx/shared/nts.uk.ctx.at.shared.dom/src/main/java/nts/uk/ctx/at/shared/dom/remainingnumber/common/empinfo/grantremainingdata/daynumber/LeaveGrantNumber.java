package nts.uk.ctx.at.shared.dom.remainingnumber.common.empinfo.grantremainingdata.daynumber;

import java.util.Optional;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import nts.arc.time.GeneralDate;

/**
 * 休暇付与数  
 * @author masaaki_jinno
 *
 */
@Getter
@Setter
@NoArgsConstructor
public class LeaveGrantNumber {

	/**
	 * 日数
	 */
	protected LeaveGrantDayNumber days;

	/**
	 * 時間
	 */
	protected Optional<LeaveGrantTime> minutes;

	@Override
	public LeaveGrantNumber clone() {
		LeaveGrantNumber cloned = new LeaveGrantNumber();
		try {
			cloned.days = new LeaveGrantDayNumber(days.v());
			if ( minutes.isPresent() ){
				cloned.minutes = Optional.of(new LeaveGrantTime(minutes.get().v()));
			}
		}
		catch (Exception e){
			throw new RuntimeException("LeaveGrantRemainingData clone error.");
		}
		return cloned;
	}
	
	protected LeaveGrantNumber(double days, Integer minutes) {
		this.days = new LeaveGrantDayNumber(days);
		this.minutes = minutes != null ? Optional.of(new LeaveGrantTime(minutes)) : Optional.empty();
	}

	public static LeaveGrantNumber createFromJavaType(double days, Integer minutes) {
		return new LeaveGrantNumber(days, minutes);
	}

}
