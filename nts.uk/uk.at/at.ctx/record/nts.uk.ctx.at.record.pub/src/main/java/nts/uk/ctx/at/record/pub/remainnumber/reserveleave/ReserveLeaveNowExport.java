package nts.uk.ctx.at.record.pub.remainnumber.reserveleave;

import lombok.AllArgsConstructor;
import lombok.Getter;
import nts.uk.ctx.at.shared.dom.remainingnumber.reserveleave.empinfo.grantremainingdata.daynumber.ReserveLeaveGrantDayNumber;
import nts.uk.ctx.at.shared.dom.remainingnumber.reserveleave.empinfo.grantremainingdata.daynumber.ReserveLeaveRemainingDayNumber;
import nts.uk.ctx.at.shared.dom.remainingnumber.reserveleave.empinfo.grantremainingdata.daynumber.ReserveLeaveUsedDayNumber;

/**
 * 積立年休現在状況
 * @author shuichu_ishida
 */
@Getter
@AllArgsConstructor
public class ReserveLeaveNowExport {

	/** 月初残 */
	private ReserveLeaveRemainingDayNumber startMonthRemain;
	/** 付与数 */
	private ReserveLeaveGrantDayNumber grantNumber;
	/** 使用数 */
	private ReserveLeaveUsedDayNumber usedNumber;
	/** 残数 */
	private ReserveLeaveRemainingDayNumber remainNumber;
	/** 未消化数 */
	private ReserveLeaveRemainingDayNumber undigestNumber;
	
	public ReserveLeaveNowExport(){
		this.startMonthRemain = new ReserveLeaveRemainingDayNumber(0.0);
		this.grantNumber = new ReserveLeaveGrantDayNumber(0.0);
		this.usedNumber = new ReserveLeaveUsedDayNumber(0.0);
		this.remainNumber = new ReserveLeaveRemainingDayNumber(0.0);
		this.undigestNumber = new ReserveLeaveRemainingDayNumber(0.0);
	}
}
