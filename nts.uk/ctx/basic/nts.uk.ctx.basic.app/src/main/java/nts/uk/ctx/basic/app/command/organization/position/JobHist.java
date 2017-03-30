package nts.uk.ctx.basic.app.command.organization.position;

import lombok.Getter;
import lombok.Setter;
import nts.arc.time.GeneralDate;

@Getter
@Setter
public class JobHist {
	
	private String companyCode;
	
	private GeneralDate startDate;
	
	private GeneralDate endDate;
	
	private String historyId;
}
