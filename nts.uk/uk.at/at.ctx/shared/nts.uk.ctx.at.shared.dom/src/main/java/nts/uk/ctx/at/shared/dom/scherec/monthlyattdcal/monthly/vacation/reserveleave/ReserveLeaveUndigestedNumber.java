package nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.monthly.vacation.reserveleave;

import lombok.Getter;
import nts.uk.ctx.at.shared.dom.remainingnumber.reserveleave.empinfo.grantremainingdata.daynumber.ReserveLeaveRemainingDayNumber;

/**
 * 積立年休未消化数
 * @author shuichu_ishida
 */
@Getter
public class ReserveLeaveUndigestedNumber implements Cloneable {

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
	
	@Override
	public ReserveLeaveUndigestedNumber clone() {
		ReserveLeaveUndigestedNumber cloned = new ReserveLeaveUndigestedNumber();
		try {
			cloned.undigestedDays = new ReserveLeaveRemainingDayNumber(this.undigestedDays.v());
		}
		catch (Exception e){
			throw new RuntimeException("ReserveLeaveUndigestedNumber clone error.");
		}
		return cloned;
	}

	/**
	 * 日数を加算する
	 * @param days 日数
	 */
	public void addDays(double days){
		this.undigestedDays = new ReserveLeaveRemainingDayNumber(this.undigestedDays.v() + days);
	}
}
