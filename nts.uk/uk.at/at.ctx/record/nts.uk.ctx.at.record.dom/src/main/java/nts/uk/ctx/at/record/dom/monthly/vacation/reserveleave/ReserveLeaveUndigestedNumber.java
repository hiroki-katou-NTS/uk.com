package nts.uk.ctx.at.record.dom.monthly.vacation.reserveleave;

import lombok.Getter;
import nts.uk.ctx.at.shared.dom.remainingnumber.reserveleave.empinfo.grantremainingdata.daynumber.ReserveLeaveRemainingDayNumber;

/**
 * 積立年休未消化数
 * @author shuichu_ishida
 */
@Getter
public class ReserveLeaveUndigestedNumber {

	/** 未消化日数 */
	private ReserveLeaveRemainingDayNumber undigestedDays;
	
	/**
	 * コンストラクタ
	 */
	public ReserveLeaveUndigestedNumber(){
		
		this.undigestedDays = new ReserveLeaveRemainingDayNumber(0.0);
	}
	
	/**
	 * ファクトリー
	 * @param undigestedDays 未消化日数
	 * @return 積立年休未消化数
	 */
	public static ReserveLeaveUndigestedNumber of(
			ReserveLeaveRemainingDayNumber undigestedDays){
		
		ReserveLeaveUndigestedNumber domain = new ReserveLeaveUndigestedNumber();
		domain.undigestedDays = undigestedDays;
		return domain;
	}
}
