package nts.uk.ctx.at.record.dom.remainingnumber.annualleave.empinfo.grantremainingdata;

import lombok.Getter;
import nts.uk.ctx.at.record.dom.remainingnumber.annualleave.empinfo.grantremainingdata.daynumber.AnnualLeaveGrantNumber;
import nts.uk.ctx.at.record.dom.remainingnumber.annualleave.empinfo.grantremainingdata.daynumber.AnnualLeaveRemainingNumber;
import nts.uk.ctx.at.record.dom.remainingnumber.annualleave.empinfo.grantremainingdata.daynumber.AnnualLeaveUsedNumber;

@Getter
public class AnnualLeaveNumberInfo {
	
	private AnnualLeaveGrantNumber grantNumber;
	
	private AnnualLeaveUsedNumber usedNumber;
	
	private AnnualLeaveRemainingNumber remainingNumber;
	
	private float usedPercent;

}
