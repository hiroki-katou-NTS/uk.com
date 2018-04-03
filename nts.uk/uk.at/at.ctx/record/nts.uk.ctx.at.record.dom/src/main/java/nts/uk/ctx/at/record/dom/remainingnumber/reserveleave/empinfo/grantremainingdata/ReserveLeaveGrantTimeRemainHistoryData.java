package nts.uk.ctx.at.record.dom.remainingnumber.reserveleave.empinfo.grantremainingdata;

import lombok.Getter;
import nts.arc.time.GeneralDate;

/**
 * 
 * @author HungTT - 積立年休付付与時点残数履歴データ
 *
 */

@Getter
public class ReserveLeaveGrantTimeRemainHistoryData {

	// 付与処理日
	private GeneralDate grantDate;

	public ReserveLeaveGrantTimeRemainHistoryData(GeneralDate grantDate) {
		super();
		this.grantDate = grantDate;
	}
	
}
