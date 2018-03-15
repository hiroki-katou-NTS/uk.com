package nts.uk.ctx.at.record.dom.remainingnumber.specialleave.empinfo.basicinfo;

import java.util.Optional;

import lombok.Getter;
import nts.arc.time.GeneralDate;

@Getter
public class SpecialLeaveGrantSetting {
	
	private GeneralDate grantStandardDate;
	
	private Optional<Integer> grantNumber;
	
	private Optional<GrantTable> grantTable;
	
}
