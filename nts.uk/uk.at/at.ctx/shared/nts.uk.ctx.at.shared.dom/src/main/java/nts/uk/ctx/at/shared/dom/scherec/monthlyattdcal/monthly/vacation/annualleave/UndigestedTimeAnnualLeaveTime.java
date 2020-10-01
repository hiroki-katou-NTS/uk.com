package nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.monthly.vacation.annualleave;

import lombok.Getter;
import nts.uk.ctx.at.shared.dom.remainingnumber.annualleave.empinfo.maxdata.UsedMinutes;

/**
 * 時間年休未消化時間
 * @author shuichu_ishida
 */
@Getter
public class UndigestedTimeAnnualLeaveTime implements Cloneable {

	/** 未消化時間 */
	private UsedMinutes undigestedTime;
	
	/**
	 * コンストラクタ
	 */
	public UndigestedTimeAnnualLeaveTime(){
		
		this.undigestedTime = new UsedMinutes(0);
	}
	
	/**
	 * ファクトリー
	 * @param undigestedTime 未消化時間
	 * @return 時間年休未消化時間
	 */
	public static UndigestedTimeAnnualLeaveTime of(UsedMinutes undigestedTime){
		
		UndigestedTimeAnnualLeaveTime domain = new UndigestedTimeAnnualLeaveTime();
		domain.undigestedTime = undigestedTime;
		return domain;
	}
	
	@Override
	protected UndigestedTimeAnnualLeaveTime clone() {
		UndigestedTimeAnnualLeaveTime cloned = new UndigestedTimeAnnualLeaveTime();
		try {
			cloned.undigestedTime = new UsedMinutes(this.undigestedTime.v());
		}
		catch (Exception e){
			throw new RuntimeException("UndigestedTimeAnnualLeaveTime clone error.");
		}
		return cloned;
	}
	
	/**
	 * 分を加算する
	 * @param minutes 分
	 */
	public void addMinutes(int minutes){
		this.undigestedTime = this.undigestedTime.addMinutes(minutes);
	}
}
