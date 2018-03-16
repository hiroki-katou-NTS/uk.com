package nts.uk.ctx.at.record.dom.remainingnumber.reserveleave.empinfo.grantremainingdata;

import lombok.Getter;
import nts.arc.time.GeneralDate;

@Getter
public class RevLeaGrtTmPntRmnNumHisData extends ReserveLeaveGrantRemainingData{
	
	// domain name: 積立年休付与残数データ
	
	/**
	 * 付与処理日
	 */
	private GeneralDate processGrantDate;

}
