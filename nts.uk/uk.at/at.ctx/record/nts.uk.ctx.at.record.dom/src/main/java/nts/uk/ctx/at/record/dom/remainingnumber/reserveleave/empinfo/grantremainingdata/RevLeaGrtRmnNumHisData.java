package nts.uk.ctx.at.record.dom.remainingnumber.reserveleave.empinfo.grantremainingdata;

import lombok.Getter;
import nts.arc.time.GeneralDate;
import nts.arc.time.YearMonth;

@Getter
public class RevLeaGrtRmnNumHisData extends ReserveLeaveGrantRemainingData{

	private YearMonth yearMonth;
	
	private String closeId;
	
	private GeneralDate closeDate;
	
}
