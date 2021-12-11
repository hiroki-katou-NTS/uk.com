package nts.uk.ctx.at.shared.dom.remainingnumber.reserveleave.empinfo.grantremainingdata;

import lombok.Getter;
import nts.arc.time.GeneralDate;

/**
 *
 * @author HungTT - 積立年休付付与時点残数履歴データ
 *
 */

@Getter
public class ReserveLeaveGrantTimeRemainHistoryData extends ReserveLeaveGrantRemainingData {

	// 付与処理日
	private GeneralDate grantProcessDate;

	public ReserveLeaveGrantTimeRemainHistoryData(ReserveLeaveGrantRemainingData data, GeneralDate grantProcessDate) {
		super(data.getLeaveID(), data.getEmployeeId(), data.getGrantDate(), data.getDeadline(),
				data.getExpirationStatus(), data.getRegisterType(), data.getDetails());
		this.grantProcessDate = grantProcessDate;
	}

}
