package nts.uk.ctx.at.record.app.find.remainingnumber.annualleave.nexttime;

import lombok.Getter;
import nts.arc.time.GeneralDate;

@Getter
public class NextTimeEventParam {
	
	private String employeeId;
	
	private GeneralDate standardDate;
	
	private String grantTable;
	
}
