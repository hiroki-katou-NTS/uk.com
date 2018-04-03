package nts.uk.ctx.at.record.dom.remainingnumber.reserveleave.empinfo.grantremainingdata;

import lombok.Getter;
import nts.arc.enums.EnumAdaptor;
import nts.arc.time.YearMonth;
import nts.uk.ctx.at.shared.dom.workrule.closure.ClosureDate;
import nts.uk.ctx.at.shared.dom.workrule.closure.ClosureId;

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

	public ReserveLeaveGrantRemainHistoryData(YearMonth yearMonth, int closureId, ClosureDate closureDate) {
		super();
		this.yearMonth = yearMonth;
		this.closureId = EnumAdaptor.valueOf(closureId, ClosureId.class);
		this.closureDate = closureDate;
	}

}
