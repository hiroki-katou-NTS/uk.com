package nts.uk.ctx.at.record.dom.remainingnumber.reserveleave.export.param;

import lombok.Getter;
import nts.uk.ctx.at.shared.dom.remainingnumber.reserveleave.empinfo.grantremainingdata.daynumber.ReserveLeaveGrantDayNumber;

/**
 * 積立年休付与情報
 * @author shuichu_ishida
 */
@Getter
public class ReserveLeaveGrantInfo {

	/** 付与日数 */
	private ReserveLeaveGrantDayNumber grantDays;
	
	/**
	 * コンストラクタ
	 */
	public ReserveLeaveGrantInfo(){
		
		this.grantDays = new ReserveLeaveGrantDayNumber(0.0);
	}

	/**
	 * ファクトリー
	 * @param grantDays　付与日数
	 * @return 積立年休付与情報
	 */
	public static ReserveLeaveGrantInfo of(
			ReserveLeaveGrantDayNumber grantDays){
		
		ReserveLeaveGrantInfo domain = new ReserveLeaveGrantInfo();
		domain.grantDays = grantDays;
		return domain;
	}
}
