package nts.uk.ctx.at.record.dom.remainingnumber.specialleave.empinfo.basicinfo;

import java.util.Optional;

import lombok.Getter;
import nts.arc.time.GeneralDate;
import nts.uk.ctx.at.record.dom.remainingnumber.specialleave.empinfo.grantremainingdata.daynumber.SpecialLeaveGrantNumber;

@Getter
public class SpecialLeaveGrantSetting {
	
	private GeneralDate grantStandardDate;
	
	private Optional<SpecialLeaveGrantNumber> grantNumber;
	
	// field which is waiting for update
	private Optional<String> grantTable;
	
}
