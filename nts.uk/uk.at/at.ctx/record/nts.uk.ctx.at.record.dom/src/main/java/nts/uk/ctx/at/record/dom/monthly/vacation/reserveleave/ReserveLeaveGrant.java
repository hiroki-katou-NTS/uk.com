package nts.uk.ctx.at.record.dom.monthly.vacation.reserveleave;

import lombok.Getter;
import lombok.Setter;
import nts.uk.ctx.at.record.dom.remainingnumber.reserveleave.empinfo.grantremainingdata.daynumber.ReserveLeaveGrantDayNumber;

/**
 * 積立年休付与情報
 * @author shuichu_ishida
 */
@Getter
@Setter
public class ReserveLeaveGrant {

	/** 付与区分 */
	private boolean grantAtr;
	/** 付与日数 */
	private ReserveLeaveGrantDayNumber grantDays;
	
	/**
	 * コンストラクタ
	 */
	public ReserveLeaveGrant(){
		
		this.grantAtr = false;
		this.grantDays = new ReserveLeaveGrantDayNumber(0.0);
	}
	
	/**
	 * ファクトリー
	 * @param grantAtr 付与区分
	 * @param grantDays 付与日数
	 * @return 積立年休付与ｊ方法
	 */
	public static ReserveLeaveGrant of(
			boolean grantAtr,
			ReserveLeaveGrantDayNumber grantDays){
		
		ReserveLeaveGrant domain = new ReserveLeaveGrant();
		domain.grantAtr = grantAtr;
		domain.grantDays = grantDays;
		return domain;
	}
}
