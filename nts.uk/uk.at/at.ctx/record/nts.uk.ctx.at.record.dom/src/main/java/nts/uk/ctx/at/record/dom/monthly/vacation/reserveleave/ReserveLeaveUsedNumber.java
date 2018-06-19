package nts.uk.ctx.at.record.dom.monthly.vacation.reserveleave;

import java.util.Optional;

import lombok.Getter;
import nts.uk.ctx.at.shared.dom.remainingnumber.reserveleave.empinfo.grantremainingdata.daynumber.ReserveLeaveUsedDayNumber;

/**
 * 積立年休使用数
 * @author shuichu_ishida
 */
@Getter
public class ReserveLeaveUsedNumber {

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
}
