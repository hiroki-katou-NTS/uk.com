package nts.uk.ctx.at.shared.dom.remainingnumber.reserveleave.export.param;

import lombok.Getter;
import nts.arc.time.GeneralDate;
import nts.uk.ctx.at.shared.dom.remainingnumber.reserveleave.empinfo.grantremainingdata.daynumber.ReserveLeaveGrantDayNumber;

/**
 * 次回積立年休付与
 * @author shuichu_ishida
 */
@Getter
public class NextReserveLeaveGrant {

	/** 付与年月日 */
	private GeneralDate grantYmd;
	/** 付与日数 */
	private ReserveLeaveGrantDayNumber grantDays;
	/** 使用期限日 */
	private GeneralDate deadline;
	
	/**
	 * コンストラクタ
	 */
	public NextReserveLeaveGrant(){
		
		this.grantYmd = GeneralDate.today();
		this.grantDays = new ReserveLeaveGrantDayNumber(0.0);
		this.deadline = GeneralDate.max();
	}
	
	/**
	 * ファクトリー
	 * @param grantYmd 付与年月日
	 * @param grantDays 付与日数
	 * @param deadline 使用期限日
	 * @return 次回積立年休付与
	 */
	public static NextReserveLeaveGrant of(
			GeneralDate grantYmd, ReserveLeaveGrantDayNumber grantDays, GeneralDate deadline){
		
		NextReserveLeaveGrant domain = new NextReserveLeaveGrant();
		domain.grantYmd = grantYmd;
		domain.grantDays = grantDays;
		domain.deadline = deadline;
		return domain;
	}
}
