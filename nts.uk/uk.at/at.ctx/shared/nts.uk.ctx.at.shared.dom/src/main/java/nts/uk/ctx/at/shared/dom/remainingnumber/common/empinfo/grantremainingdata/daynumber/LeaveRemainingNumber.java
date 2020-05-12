package nts.uk.ctx.at.shared.dom.remainingnumber.common.empinfo.grantremainingdata.daynumber;

import java.util.Optional;

import lombok.Getter;
import lombok.Setter;

/**
 * 休暇残数
 * @author masaaki_jinno
 *
 */
@Getter
@Setter
public class LeaveRemainingNumber {

	/**
	 * 日数
	 */
	protected LeaveRemainingDayNumber days;

	/**
	 * 時間
	 */
	protected Optional<LeaveRemainingTime> minutes;

	/**
	 * コンストラクタ
	 * @param days
	 * @param minutes
	 */
	protected LeaveRemainingNumber(double days, Integer minutes) {
		this.days = new LeaveRemainingDayNumber(days);
		this.minutes = minutes != null ? Optional.of(new LeaveRemainingTime(minutes)) : Optional.empty();
	}
	
	/**
	 * コンストラクタ
	 * @param days
	 * @param minutes
	 * @return
	 */
	public static LeaveRemainingNumber createFromJavaType(double days, Integer minutes) {
		return new LeaveRemainingNumber(days, minutes);
	}
	
	/**
	 * 残数を加算
	 * @param aLeaveRemainingNumber
	 */
	public void add(LeaveRemainingNumber aLeaveRemainingNumber){
		days = new LeaveRemainingDayNumber( days.v() + aLeaveRemainingNumber.getDays().v() );
		if ( aLeaveRemainingNumber.getMinutes().isPresent() ){
			if ( this.getMinutes().isPresent() ){
				int total = this.getMinutes().get().v()
						+ aLeaveRemainingNumber.getMinutes().get().v();
				this.setMinutes(Optional.of(new LeaveRemainingTime(total)));
			}
			else
			{
				this.setMinutes(Optional.of(new LeaveRemainingTime(
						aLeaveRemainingNumber.getMinutes().get().v())));
			}
		}
	}

}
