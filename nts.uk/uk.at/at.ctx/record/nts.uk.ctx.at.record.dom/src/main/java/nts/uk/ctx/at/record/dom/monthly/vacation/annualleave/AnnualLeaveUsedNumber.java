package nts.uk.ctx.at.record.dom.monthly.vacation.annualleave;

import java.util.Optional;

import lombok.Getter;

/**
 * 年休使用数
 * @author shuichu_ishida
 */
@Getter
public class AnnualLeaveUsedNumber {

	/** 使用日数 */
	private AnnualLeaveUsedDays usedDays;
	/** 使用時間 */
	private Optional<TimeAnnualLeaveUsedTime> usedTime;
	
	/**
	 * コンストラクタ
	 */
	public AnnualLeaveUsedNumber(){
		
		this.usedDays = new AnnualLeaveUsedDays();
		this.usedTime = Optional.empty();
	}

	/**
	 * ファクトリー
	 * @param usedDays 使用日数
	 * @param usedTime 使用時間
	 * @return 年休使用数
	 */
	public static AnnualLeaveUsedNumber of(
			AnnualLeaveUsedDays usedDays,
			Optional<TimeAnnualLeaveUsedTime> usedTime){
		
		AnnualLeaveUsedNumber domain = new AnnualLeaveUsedNumber();
		domain.usedDays = usedDays;
		domain.usedTime = usedTime;
		return domain;
	}
}
