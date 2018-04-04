package nts.uk.ctx.at.record.dom.monthly.vacation.annualleave;

import lombok.Getter;
import nts.uk.ctx.at.record.dom.remainingnumber.annualleave.empinfo.grantremainingdata.daynumber.AnnualLeaveUsedDayNumber;

/**
 * 年休未消化日数
 * @author shuichu_ishida
 */
@Getter
public class UndigestedAnnualLeaveDays {

	/** 未消化日数 */
	private AnnualLeaveUsedDayNumber undigestedDays;
	
	/**
	 * コンストラクタ
	 */
	public UndigestedAnnualLeaveDays(){
		
		this.undigestedDays = new AnnualLeaveUsedDayNumber(0.0);
	}
	
	/**
	 * ファクトリー
	 * @param undigestedDays 未消化日数
	 * @return 年休未消化日数
	 */
	public static UndigestedAnnualLeaveDays of(AnnualLeaveUsedDayNumber undigestedDays){
		
		UndigestedAnnualLeaveDays domain = new UndigestedAnnualLeaveDays();
		domain.undigestedDays = undigestedDays;
		return domain;
	}
}
