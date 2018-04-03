package nts.uk.ctx.at.record.dom.monthly.vacation.reserveleave;

import lombok.Getter;
import nts.uk.ctx.at.record.dom.remainingnumber.reserveleave.empinfo.grantremainingdata.daynumber.ReserveLeaveRemainingDayNumber;
import nts.uk.ctx.at.record.dom.remainingnumber.reserveleave.empinfo.grantremainingdata.daynumber.ReserveLeaveUsedNumber;

/**
 * 積立年休
 * @author shuichu_ishida
 */
@Getter
public class ReserveLeave {

	/** 残数 */
	private ReserveLeaveRemainingDayNumber remainingNumber;
	/** 使用数 */
	private ReserveLeaveUsedNumber usedNumber;
	
	/**
	 * コンストラクタ
	 */
	public ReserveLeave(){
		
		this.remainingNumber = new ReserveLeaveRemainingDayNumber(0.0);
		this.usedNumber = new ReserveLeaveUsedNumber(0.0, null);
	}
	
	/**
	 * ファクトリー
	 * @param remainingNumber 残数
	 * @param usedNumber 使用数
	 * @return 積立年休
	 */
	public static ReserveLeave of(
			ReserveLeaveRemainingDayNumber remainingNumber,
			ReserveLeaveUsedNumber usedNumber){
		
		ReserveLeave domain = new ReserveLeave();
		domain.remainingNumber = remainingNumber;
		domain.usedNumber = usedNumber;
		return domain;
	}
}
