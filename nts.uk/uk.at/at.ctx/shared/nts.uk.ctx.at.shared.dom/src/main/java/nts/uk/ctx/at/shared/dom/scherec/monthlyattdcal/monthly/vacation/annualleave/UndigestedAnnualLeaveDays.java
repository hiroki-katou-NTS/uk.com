package nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.monthly.vacation.annualleave;

import lombok.Getter;
import nts.uk.ctx.at.shared.dom.remainingnumber.annualleave.empinfo.grantremainingdata.daynumber.AnnualLeaveUsedDayNumber;

/**
 * 年休未消化日数
 * @author shuichu_ishida
 */
@Getter
public class UndigestedAnnualLeaveDays implements Cloneable {

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
	
	@Override
	protected UndigestedAnnualLeaveDays clone() {
		UndigestedAnnualLeaveDays cloned = new UndigestedAnnualLeaveDays();
		try {
			cloned.undigestedDays = new AnnualLeaveUsedDayNumber(this.undigestedDays.v());
		}
		catch (Exception e){
			throw new RuntimeException("UndigestedAnnualLeaveDays clone error.");
		}
		return cloned;
	}

	/**
	 * 日数を加算する
	 * @param days 日数
	 */
	public void addDays(double days){
		this.undigestedDays = new AnnualLeaveUsedDayNumber(this.undigestedDays.v() + days);
	}
}
