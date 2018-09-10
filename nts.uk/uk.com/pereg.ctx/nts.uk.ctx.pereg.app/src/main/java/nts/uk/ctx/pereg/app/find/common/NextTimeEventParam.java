package nts.uk.ctx.pereg.app.find.common;

import lombok.Getter;
import nts.arc.time.GeneralDate;

@Getter
public class NextTimeEventParam {
	
	private String employeeId;
	
	private String standardDate;
	
	private String grantTable;
	
	private GeneralDate entryDate;
	
	private GeneralDate retireDate;
	
}
