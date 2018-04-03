package nts.uk.ctx.at.record.dom.monthly.vacation.annualleave;

import java.util.Optional;

import lombok.Getter;

/**
 * 年休未消化数
 * @author shuichu_ishida
 */
@Getter
public class AnnualLeaveUndigestedNumber {

	/** 未消化日数 */
	private UndigestedAnnualLeaveDays undigestedDays;
	/** 未消化時間 */
	private Optional<UndigestedTimeAnnualLeaveTime> undigestedTime;
	
	/**
	 * コンストラクタ
	 */
	public AnnualLeaveUndigestedNumber(){
		
		this.undigestedDays = new UndigestedAnnualLeaveDays();
		this.undigestedTime = Optional.empty();
	}
	
	/**
	 * ファクトリー
	 * @param undigestedDays 未消化日数
	 * @param undigestedTime 未消化時間
	 * @return 年休未消化数
	 */
	public static AnnualLeaveUndigestedNumber of(
			UndigestedAnnualLeaveDays undigestedDays,
			Optional<UndigestedTimeAnnualLeaveTime> undigestedTime){
		
		AnnualLeaveUndigestedNumber domain = new AnnualLeaveUndigestedNumber();
		domain.undigestedDays = undigestedDays;
		domain.undigestedTime = undigestedTime;
		return domain;
	}
}
