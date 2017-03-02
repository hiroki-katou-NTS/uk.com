package nts.uk.ctx.basic.app.find.organization.positionhistory;

import lombok.Data;
import nts.arc.time.GeneralDate;
@Data
public class JobHisDto {

	private String historyID;
	private String companyCode;
	private GeneralDate startDate;
	private GeneralDate endDate;
	

}
