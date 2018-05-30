package nts.uk.ctx.at.record.pub.remainnumber.reserveleave;

import lombok.AllArgsConstructor;
import lombok.Getter;
import nts.arc.time.YearMonth;
import nts.uk.ctx.at.record.dom.remainingnumber.reserveleave.empinfo.grantremainingdata.daynumber.ReserveLeaveRemainingDayNumber;
import nts.uk.ctx.at.record.dom.remainingnumber.reserveleave.empinfo.grantremainingdata.daynumber.ReserveLeaveUsedDayNumber;

/**
 * 積立年休利用当月状況
 * @author shuichu_ishida
 */
@Getter
@AllArgsConstructor
public class RsvLeaUsedCurrentMonExport {

	/** 年月 */
	private YearMonth yearMonth;
	/** 月度使用数 */
	private ReserveLeaveUsedDayNumber usedNumber;
	/** 月度残日数 */
	private ReserveLeaveRemainingDayNumber remainNumber;

	public RsvLeaUsedCurrentMonExport(YearMonth yearMonth){
		
		this.yearMonth = yearMonth;
		this.usedNumber = new ReserveLeaveUsedDayNumber(0.0);
		this.remainNumber = new ReserveLeaveRemainingDayNumber(0.0);
	}
}
