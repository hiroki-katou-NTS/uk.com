package nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.monthly.vacation.annualleave;

import java.util.Optional;

import lombok.Getter;
import lombok.Setter;

/**
 * 年休未消化数
 * @author shuichu_ishida
 */
@Getter
public class AnnualLeaveUndigestedNumber implements Cloneable {

	/** 未消化日数 */
	private UndigestedAnnualLeaveDays undigestedDays;
	/** 未消化時間 */
	@Setter
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
	
	@Override
	public AnnualLeaveUndigestedNumber clone() {
		AnnualLeaveUndigestedNumber cloned = new AnnualLeaveUndigestedNumber();
		try {
			cloned.undigestedDays = this.undigestedDays.clone();
			if (this.undigestedTime.isPresent()){
				cloned.undigestedTime = Optional.of(this.undigestedTime.get().clone());
			}
		}
		catch (Exception e){
			throw new RuntimeException("AnnualLeaveUndigestedNumber clone error.");
		}
		return cloned;
	}
}
