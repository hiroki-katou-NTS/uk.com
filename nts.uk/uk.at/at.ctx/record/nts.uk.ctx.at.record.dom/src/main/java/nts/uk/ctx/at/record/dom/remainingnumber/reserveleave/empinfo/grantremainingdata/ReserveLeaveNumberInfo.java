package nts.uk.ctx.at.record.dom.remainingnumber.reserveleave.empinfo.grantremainingdata;

import lombok.Getter;
import nts.uk.ctx.at.record.dom.remainingnumber.reserveleave.empinfo.grantremainingdata.daynumber.ReserveLeaveGrantNumber;
import nts.uk.ctx.at.record.dom.remainingnumber.reserveleave.empinfo.grantremainingdata.daynumber.ReserveLeaveRemainingNumber;
import nts.uk.ctx.at.record.dom.remainingnumber.reserveleave.empinfo.grantremainingdata.daynumber.ReserveLeaveUsedNumber;

@Getter
public class ReserveLeaveNumberInfo {
	
	private ReserveLeaveGrantNumber grantNumber;
	
	private ReserveLeaveUsedNumber usedNumber;
	
	private ReserveLeaveRemainingNumber remainingNumber;

}
