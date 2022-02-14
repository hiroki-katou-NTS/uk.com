package nts.uk.ctx.at.shared.dom.remainingnumber.reserveleave.empinfo.grantremainingdata;

import lombok.Getter;
import nts.arc.time.YearMonth;
import nts.uk.ctx.at.shared.dom.workrule.closure.ClosureId;
import nts.uk.shr.com.time.calendar.date.ClosureDate;

/**
 *
 * @author HungTT - 積立年休付与残数履歴データ
 *
 */

@Getter
public class ReserveLeaveGrantRemainHistoryData extends ReserveLeaveGrantRemainingData {

	// 年月
	private YearMonth yearMonth;

	// 締めID
	private ClosureId closureId;

	// 締め日
	private ClosureDate closureDate;

	public ReserveLeaveGrantRemainHistoryData(ReserveLeaveGrantRemainingData data, YearMonth yearMonth,
			ClosureId closureId, ClosureDate closureDate) {
		super(data.getLeaveID(), data.getEmployeeId(), data.getGrantDate(), data.getDeadline(),
				data.getExpirationStatus(), data.getRegisterType(), data.getDetails());
		this.yearMonth = yearMonth;
		this.closureId = closureId;
		this.closureDate = closureDate;
	}

}
