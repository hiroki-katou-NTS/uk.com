package nts.uk.ctx.at.record.dom.remainingnumber.specialleave.empinfo.grantremainingdata;

import lombok.Getter;
import nts.uk.ctx.at.record.dom.remainingnumber.specialleave.empinfo.grantremainingdata.daynumber.SpecialLeaveGrantNumber;
import nts.uk.ctx.at.record.dom.remainingnumber.specialleave.empinfo.grantremainingdata.daynumber.SpecialLeaveRemainingNumber;
import nts.uk.ctx.at.record.dom.remainingnumber.specialleave.empinfo.grantremainingdata.daynumber.SpecialLeaveUsedNumber;

@Getter
public class SpecialLeaveNumberInfo {
	
	private SpecialLeaveGrantNumber grantNumber;
	
	private SpecialLeaveUsedNumber usedNumber;
	
	private SpecialLeaveRemainingNumber remainingNumber;

}
