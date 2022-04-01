package nts.uk.ctx.at.shared.dom.remainingnumber.common.empinfo.grantremainingdata.daynumber;

import java.util.Optional;

import lombok.Getter;
import lombok.Setter;
import nts.uk.ctx.at.shared.dom.remainingnumber.annualleave.interim.TempAnnualLeaveMngs;

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
	 * ファクトリー
	 * @param days 日数
	 * @param minutes　時間
	 * @param stowageDays 積み崩し日数
	 * @param leaveOverLimitNumber 上限超過消滅日数
	 * @return LeaveUsedNumber 休暇使用数
	*/
	public static LeaveUsedNumber of(
			LeaveUsedDayNumber days,
			Optional<LeaveUsedTime> minutes,
			Optional<LeaveUsedDayNumber> stowageDays,
			Optional<LeaveOverNumber> leaveOverLimitNumber) {

		LeaveUsedNumber domain = new LeaveUsedNumber();
		domain.days = days;
		domain.minutes = minutes;
		domain.stowageDays = stowageDays;
		domain.leaveOverLimitNumber = leaveOverLimitNumber;
		return domain;
	}

	public static LeaveUsedNumber createFromJavaType(
			Double days,
			int minutes,
			Double stowageDays,
			Double leaveOverLimitNumber){
		
		LeaveUsedNumber domain = new LeaveUsedNumber();
		domain.days = new LeaveUsedDayNumber(days);
		domain.minutes = Optional.of(new LeaveUsedTime(minutes));
		domain.stowageDays = Optional.of(new LeaveUsedDayNumber(stowageDays));
		domain.leaveOverLimitNumber = Optional.of(new LeaveOverNumber(leaveOverLimitNumber));
		return domain;
	}
	
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
	 *  [3]使用しているか
	 * @return
	 */
	public boolean isLargerThanZero(){
		return isUseDay() || isUseTime();
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
	public LeaveUsedNumber(double days, Integer minutes, Double stowageDays,  Double numberOverDays, Integer timeOver) {
		this.days = new LeaveUsedDayNumber(days);
		this.minutes = minutes != null ? Optional.of(new LeaveUsedTime(minutes)) : Optional.empty();
		this.stowageDays = stowageDays != null ? Optional.of(new LeaveUsedDayNumber(stowageDays))
				: Optional.empty();
		if(numberOverDays == null && timeOver == null) {
			this.leaveOverLimitNumber = Optional.empty();
		}else {
			this.leaveOverLimitNumber = Optional.of(new LeaveOverNumber(numberOverDays, timeOver));
		}
	}


	/**
	 * コンストラクタ
	 */
	public LeaveUsedNumber(double days, Integer minutes){
		this.days = new LeaveUsedDayNumber(days);
		this.minutes = minutes != null ? Optional.of(new LeaveUsedTime(minutes)) : Optional.empty();
		this.stowageDays=Optional.empty();
		this.leaveOverLimitNumber=Optional.empty();
	}


	public static LeaveUsedNumber createFromJavaType(double days, Integer minutes, Double stowageDays, Double numberOverDays, Integer timeOver) {
		return new LeaveUsedNumber(days, minutes, stowageDays, numberOverDays, timeOver);
	}


	/**
	 * [6] 使用時間を取得
	 * @return
	 */
	public LeaveUsedTime getMinutesOrZero() {
		return this.minutes.orElse(new LeaveUsedTime(0));
	}
	
	/**
	 * [7] 積み崩し日数を取得
	 * @return
	 */
	public LeaveUsedDayNumber getStowageDaysOrZero(){
		return this.stowageDays.orElse(new LeaveUsedDayNumber(0.0));
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

		cloned.days = new LeaveUsedDayNumber(days.v());
		cloned.minutes = minutes.map(c -> new LeaveUsedTime(c.v()));
		cloned.stowageDays = stowageDays.map(c -> new LeaveUsedDayNumber(c.v()));
		cloned.leaveOverLimitNumber = leaveOverLimitNumber.map(c -> c.clone());

		return cloned;
	}

	/**
	 * [1] 日数使用しているか
	 * @return
	 */
	public boolean isUseDay() {
		return this.getDays().greaterThan(0.0);
	}
	
	
	/**
	 * 	[2] 時間使用しているか
	 * @return
	 */
	public boolean isUseTime(){
		if ( !minutes.isPresent() ){
			return false;
		}
		return minutes.get().v() > 0;
	}
	
	public LeaveUsedNumber(TempAnnualLeaveMngs tempAnnualLeaveMng) {

		this.days = (new LeaveUsedDayNumber(
				tempAnnualLeaveMng.getUsedNumber().getUsedDayNumber().map(mapper -> mapper.v()).orElse(0.0)));
		this.minutes = Optional.of(new LeaveUsedTime(
				tempAnnualLeaveMng.getUsedNumber().getUsedTime().map(mapper -> mapper.v()).orElse(0)));
		this.stowageDays=Optional.empty();
		this.leaveOverLimitNumber=Optional.empty();
	}
	
	public boolean isLimitOver(){
		return this.getLeaveOverLimitNumber().isPresent();
	}
	
	/**
	 * [4] 積み崩し日数を加算する
	 * @param days
	 * @return
	 */
	public LeaveUsedNumber addStowageDays(LeaveUsedDayNumber days){
		return LeaveUsedNumber.of(this.days, this.minutes,
				Optional.of(new LeaveUsedDayNumber(this.getStowageDaysOrZero().v() + days.v())),
				leaveOverLimitNumber);
	}
	
	/**
	 * [5]減算する
	 * @param useNumbr
	 * @return
	 */
	public LeaveUsedNumber subtract(LeaveUsedNumber useNumbr){
		LeaveUsedDayNumber days = new LeaveUsedDayNumber(this.days.v() - useNumbr.days.v());
		
		LeaveUsedTime time = new LeaveUsedTime(this.getMinutesOrZero().v() - useNumbr.getMinutesOrZero().v());
		
		return LeaveUsedNumber.of(days, Optional.of(time), this.stowageDays, this.leaveOverLimitNumber);
	}
}
