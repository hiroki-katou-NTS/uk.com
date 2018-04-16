package nts.uk.ctx.at.record.dom.monthly.vacation.reserveleave;

import lombok.Getter;
import nts.uk.ctx.at.record.dom.remainingnumber.reserveleave.empinfo.grantremainingdata.daynumber.ReserveLeaveRemainingDayNumber;
import nts.uk.ctx.at.record.dom.remainingnumber.reserveleave.empinfo.grantremainingdata.daynumber.ReserveLeaveUsedNumber;

/**
 * 実積立年休
 * @author shuichu_ishida
 */
@Getter
public class RealReserveLeave {

	/** 残数 */
	private ReserveLeaveRemainingDayNumber remainingNumber;
	/** 使用数 */
	private ReserveLeaveUsedNumber usedNumber;
	
	/**
	 * コンストラクタ
	 */
	public RealReserveLeave(){
		
		this.remainingNumber = new ReserveLeaveRemainingDayNumber(0.0);
		this.usedNumber = new ReserveLeaveUsedNumber(0.0, null);
	}
	
	/**
	 * ファクトリー
	 * @param remainingNumber 残数
	 * @param usedNumber 使用数
	 * @return 実積立年休
	 */
	public static RealReserveLeave of(
			ReserveLeaveRemainingDayNumber remainingNumber,
			ReserveLeaveUsedNumber usedNumber){
		
		RealReserveLeave domain = new RealReserveLeave();
		domain.remainingNumber = remainingNumber;
		domain.usedNumber = usedNumber;
		return domain;
	}
}
