package nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.monthly.vacation.reserveleave;

import lombok.Getter;
import lombok.Setter;
import nts.uk.ctx.at.shared.dom.remainingnumber.reserveleave.empinfo.grantremainingdata.daynumber.ReserveLeaveGrantDayNumber;

/**
 * 積立年休付与情報
 * @author shuichu_ishida
 */
@Getter
@Setter
public class ReserveLeaveGrant {

	/** 付与日数 */
	private ReserveLeaveGrantDayNumber grantDays;
	
	/**
	 * コンストラクタ
	 */
	public ReserveLeaveGrant(){
		
		this.grantDays = new ReserveLeaveGrantDayNumber(0.0);
	}
	
	/**
	 * ファクトリー
	 * @param grantDays 付与日数
	 * @return 積立年休付与ｊ方法
	 */
	public static ReserveLeaveGrant of(
			ReserveLeaveGrantDayNumber grantDays){
		
		ReserveLeaveGrant domain = new ReserveLeaveGrant();
		domain.grantDays = grantDays;
		return domain;
	}
}
