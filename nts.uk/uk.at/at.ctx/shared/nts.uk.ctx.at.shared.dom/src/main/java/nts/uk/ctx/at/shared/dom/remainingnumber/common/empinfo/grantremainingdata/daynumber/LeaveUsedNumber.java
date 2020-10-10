package nts.uk.ctx.at.shared.dom.remainingnumber.common.empinfo.grantremainingdata.daynumber;

import java.util.Optional;

import lombok.Getter;
import lombok.Setter;
import nts.arc.time.GeneralDate;
import nts.uk.ctx.at.shared.dom.remainingnumber.common.empinfo.grantremainingdata.LeaveGrantRemainingData;
import nts.uk.ctx.at.shared.dom.remainingnumber.specialleave.empinfo.grantremainingdata.grantnumber.SpecialLeaveGrantNumber;
import nts.uk.ctx.at.shared.dom.remainingnumber.specialleave.empinfo.grantremainingdata.usenumber.SpecialLeaveOverNumber;
import nts.uk.ctx.at.shared.dom.remainingnumber.specialleave.empinfo.grantremainingdata.usenumber.SpecialLeaveUsedNumber;
import nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.monthly.vacation.specialholiday.SpecialLeaveRemainingNumber;

/**
 * 休暇使用数  
 * @author masaaki_jinno
 *
 */
@Getter
@Setter
public class LeaveUsedNumber{

	/**
	 * 日数
	 */
	protected LeaveUsedDayNumber days;

	/**
	 * 時間
	 */
	protected Optional<LeaveUsedTime> minutes;

	/**
	 * 積み崩し日数
	 */
	protected Optional<LeaveUsedDayNumber> stowageDays;
	
	/**
	 * 上限超過消滅日数
	 */
	public Optional<LeaveOverNumber> leaveOverLimitNumber;
	
	
	/**
	 * 日数、時間ともに０のときはTrue,それ以外はfalseを返す
	 * @return
	 */
	public boolean isZero(){
		if ( days.v() != 0.0 ){
			return false;
		}
		if ( !minutes.isPresent() ){
			return true;
		}
		if ( minutes.get().v() == 0 ){
			return true;
		} else {
			return false;
		}
	}
	
	/**
	 * 日数＞０または時間＞０のときはTrue,それ以外はfalseを返す
	 * @return
	 */
	public boolean isLargerThanZero(){
		if ( days.v() > 0.0 ){
			return true;
		}
		if ( !minutes.isPresent() ){
			return false;
		}
		if ( minutes.get().v() > 0 ){
			return true;
		} else {
			return false;
		}
	}
	
	/**
	 * コンストラクタ
	 */
	public LeaveUsedNumber(){
		days = new LeaveUsedDayNumber(0.0);
		minutes = Optional.empty();
		stowageDays = Optional.empty();
		leaveOverLimitNumber = Optional.empty();
	}

	/**
	 * コンストラクタ
	 * @param days
	 * @param minutes
	 * @param stowageDays
	 */
	public LeaveUsedNumber(double days, Integer minutes, Double stowageDays) {
		this.days = new LeaveUsedDayNumber(days);
		this.minutes = minutes != null ? Optional.of(new LeaveUsedTime(minutes)) : Optional.empty();
		this.stowageDays = stowageDays != null ? Optional.of(new LeaveUsedDayNumber(stowageDays))
				: Optional.empty();
	}

	public static LeaveUsedNumber createFromJavaType(double days, Integer minutes, Double stowageDays) {
		return new LeaveUsedNumber(days, minutes, stowageDays);
	}
	
	/**
	 * 使用数を加算
	 * @param aLeaveRemainingNumber
	 */
	public void add(LeaveUsedNumber leaveUsedNumber){
		
		// 日付加算
		days = new LeaveUsedDayNumber(this.getDays().v() + leaveUsedNumber.getDays().v());
		
		// 時間加算
		if ( leaveUsedNumber.getMinutes().isPresent() ){
			if ( this.getMinutes().isPresent() ){
				this.setMinutes(
					Optional.of(new LeaveUsedTime(
							this.getMinutes().get().v() + 
							leaveUsedNumber.getMinutes().get().v())));
			}
			else
			{
				this.setMinutes(
					Optional.of(new LeaveUsedTime(
						leaveUsedNumber.getMinutes().get().v())));
			}
		}
	}

	@Override
	public LeaveUsedNumber clone() {
		LeaveUsedNumber cloned = new LeaveUsedNumber();
		try {
			cloned.days = new LeaveUsedDayNumber(days.v());
			cloned.minutes = minutes.map(c -> new LeaveUsedTime(c.v()));
			cloned.stowageDays = stowageDays.map(c -> new LeaveUsedDayNumber(c.v()));
			cloned.leaveOverLimitNumber = leaveOverLimitNumber.map(c -> c.clone());
		}
		catch (Exception e){
			throw new RuntimeException("LeaveUsedNumber clone error.");
		}
		return cloned;
	}
	
}
