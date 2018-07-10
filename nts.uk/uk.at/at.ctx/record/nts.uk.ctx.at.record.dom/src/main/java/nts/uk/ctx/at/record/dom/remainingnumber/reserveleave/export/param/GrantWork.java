package nts.uk.ctx.at.record.dom.remainingnumber.reserveleave.export.param;

import lombok.Getter;
import nts.arc.time.GeneralDate;
import nts.uk.ctx.at.shared.dom.remainingnumber.reserveleave.empinfo.grantremainingdata.daynumber.ReserveLeaveGrantDayNumber;

/**
 * 積立年休付与WORK
 * @author shuichu_ishida
 */
@Getter
public class GrantWork {

	/** 付与年月日 */
	private GeneralDate grantYmd;
	/** 付与日数 */
	private ReserveLeaveGrantDayNumber grantDays;
	
	/**
	 * コンストラクタ
	 */
	public GrantWork(){
		
		this.grantYmd = GeneralDate.today();
		this.grantDays = new ReserveLeaveGrantDayNumber(0.0);
	}
	
	/**
	 * ファクトリー
	 * @param grantYmd 付与年月日
	 * @param grantDays 付与日数
	 * @return 積立年休付与WORK
	 */
	public static GrantWork of(
			GeneralDate grantYmd, ReserveLeaveGrantDayNumber grantDays){
		
		GrantWork domain = new GrantWork();
		domain.grantYmd = grantYmd;
		domain.grantDays = grantDays;
		return domain;
	}
}
