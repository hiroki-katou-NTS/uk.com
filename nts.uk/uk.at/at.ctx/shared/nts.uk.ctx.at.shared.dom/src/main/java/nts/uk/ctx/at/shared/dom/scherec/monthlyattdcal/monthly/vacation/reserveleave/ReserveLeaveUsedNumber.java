package nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.monthly.vacation.reserveleave;

import java.util.Optional;

import lombok.Getter;
import lombok.Setter;
import nts.uk.ctx.at.shared.dom.remainingnumber.reserveleave.empinfo.grantremainingdata.daynumber.ReserveLeaveUsedDayNumber;

/**
 * 積立年休使用数
 * @author shuichu_ishida
 */
@Getter
@Setter
public class ReserveLeaveUsedNumber implements Cloneable {

	/** 使用日数 */
	private ReserveLeaveUsedDayNumber usedDays;
	/** 使用日数付与前 */
	private ReserveLeaveUsedDayNumber usedDaysBeforeGrant;
	/** 使用日数付与後 */
	private Optional<ReserveLeaveUsedDayNumber> usedDaysAfterGrant;
	
	/**
	 * コンストラクタ
	 */
	public ReserveLeaveUsedNumber(){
		
		this.usedDays = new ReserveLeaveUsedDayNumber(0.0);
		this.usedDaysBeforeGrant = new ReserveLeaveUsedDayNumber(0.0);
		this.usedDaysAfterGrant = Optional.empty();
	}

	/**
	 * ファクトリー
	 * @param usedDays 使用日数
	 * @param usedDaysBeforeGrant 使用日数付与前
	 * @param usedDaysAfterGrant 使用日数付与後
	 * @return 積立年休使用数
	 */
	public static ReserveLeaveUsedNumber of(
			ReserveLeaveUsedDayNumber usedDays,
			ReserveLeaveUsedDayNumber usedDaysBeforeGrant,
			Optional<ReserveLeaveUsedDayNumber> usedDaysAfterGrant){
		
		ReserveLeaveUsedNumber domain = new ReserveLeaveUsedNumber();
		domain.usedDays = usedDays;
		domain.usedDaysBeforeGrant = usedDaysBeforeGrant;
		domain.usedDaysAfterGrant = usedDaysAfterGrant;
		return domain;
	}
	
	@Override
	public ReserveLeaveUsedNumber clone() {
		ReserveLeaveUsedNumber cloned = new ReserveLeaveUsedNumber();
		try {
			cloned.usedDays = new ReserveLeaveUsedDayNumber(this.usedDays.v());
			cloned.usedDaysBeforeGrant = new ReserveLeaveUsedDayNumber(this.usedDaysBeforeGrant.v());
			if (this.usedDaysAfterGrant.isPresent()){
				cloned.usedDaysAfterGrant = Optional.of(
						new ReserveLeaveUsedDayNumber(this.usedDaysAfterGrant.get().v()));
			}
		}
		catch (Exception e){
			throw new RuntimeException("ReserveLeaveUsedNumber clone error.");
		}
		return cloned;
	}
	
	/**
	 * 日数を使用日数に加算する
	 * @param days 日数
	 */
	public void addUsedDays(double days){
		this.usedDays = new ReserveLeaveUsedDayNumber(this.usedDays.v() + days);
	}
	
	/**
	 * 日数を使用日数付与前に加算する
	 * @param days 日数
	 */
	public void addUsedDaysBeforeGrant(double days){
		this.usedDaysBeforeGrant = new ReserveLeaveUsedDayNumber(this.usedDaysBeforeGrant.v() + days);
	}
	
	/**
	 * 日数を使用日数付与後に加算する
	 * @param days 日数
	 */
	public void addUsedDaysAfterGrant(double days){
		if (!this.usedDaysAfterGrant.isPresent()){
			this.usedDaysAfterGrant = Optional.of(new ReserveLeaveUsedDayNumber(0.0));
		}
		this.usedDaysAfterGrant = Optional.of(new ReserveLeaveUsedDayNumber(
				this.usedDaysAfterGrant.get().v() + days));
	}
}
