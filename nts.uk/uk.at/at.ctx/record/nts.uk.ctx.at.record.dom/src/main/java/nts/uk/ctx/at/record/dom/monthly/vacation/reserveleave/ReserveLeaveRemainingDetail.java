package nts.uk.ctx.at.record.dom.monthly.vacation.reserveleave;

import lombok.Getter;
import lombok.Setter;
import nts.arc.time.GeneralDate;
import nts.uk.ctx.at.record.dom.remainingnumber.reserveleave.empinfo.grantremainingdata.daynumber.ReserveLeaveRemainingDayNumber;

/**
 * 積立年休残明細
 * @author shuichu_ishida
 */
@Getter
@Setter
public class ReserveLeaveRemainingDetail {

	/** 日数 */
	private ReserveLeaveRemainingDayNumber days;
	/** 付与日 */
	private GeneralDate grantDate;
	
	/**
	 * コンストラクタ
	 */
	public ReserveLeaveRemainingDetail(){
		
		this.days = new ReserveLeaveRemainingDayNumber(0.0);
		this.grantDate = GeneralDate.max();
	}
	
	/**
	 * ファクトリー
	 * @param days 日数
	 * @param grantDate 付与日
	 * @return 積立年休残明細
	 */
	public static ReserveLeaveRemainingDetail of(
			ReserveLeaveRemainingDayNumber days,
			GeneralDate grantDate){
		
		ReserveLeaveRemainingDetail domain = new ReserveLeaveRemainingDetail();
		domain.days = days;
		domain.grantDate = grantDate;
		return domain;
	}
}
