package nts.uk.ctx.at.shared.dom.remainingnumber.common.empinfo.grantremainingdata.daynumber;

import java.util.Optional;

import lombok.Getter;
import lombok.Setter;

/**
 * 休暇未消化数 
 * @author masaaki_jinno
 *
 */
//public class LeaveUndigestNumber {
@Getter
@Setter
public class LeaveUndigestNumber {

	/**
	 * 日数
	 */
	protected LeaveUndigestDayNumber days;

	/**
	 * 時間
	 */
	protected Optional<LeaveUndigestTime> minutes;

	/**
	 * コンストラクタ
	 * @param days
	 * @param minutes
	 */
	public LeaveUndigestNumber() {
		this.days = new LeaveUndigestDayNumber(0.0);
		this.minutes = Optional.of(new LeaveUndigestTime(0));
	}
	
	/**
	 * コンストラクタ
	 * @param days
	 * @param minutes
	 */
	public LeaveUndigestNumber(double days, Integer minutes) {
		this.days = new LeaveUndigestDayNumber(days);
		this.minutes = minutes != null ? Optional.of(new LeaveUndigestTime(minutes)) : Optional.empty();
	}
	
	/**
	 * コンストラクタ
	 * @param days
	 * @param minutes
	 * @return
	 */
	public static LeaveUndigestNumber createFromJavaType(double days, Integer minutes) {
		return new LeaveUndigestNumber(days, minutes);
	}
	
	/**
	 * 残数を加算
	 * @param aLeaveUndigestNumber
	 */
	public void add(LeaveUndigestNumber aLeaveUndigestNumber){
		// 日付加算
		days = days.add(getDays());
		
		// 時間加算
		if ( aLeaveUndigestNumber.getMinutes().isPresent() ){
			if ( this.getMinutes().isPresent() ){
				this.setMinutes(Optional.of(
					this.getMinutes().get().add(
							aLeaveUndigestNumber.getMinutes().get())));
			}
			else
			{
				this.setMinutes(Optional.of(new LeaveUndigestTime(
						aLeaveUndigestNumber.getMinutes().get().v())));
			}
		}
	}

}
