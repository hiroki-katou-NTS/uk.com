package nts.uk.ctx.basic.app.find.organization.position;

import java.time.format.DateTimeFormatter;

import lombok.Data;
import lombok.Value;
import nts.arc.time.GeneralDate;
import nts.uk.ctx.basic.dom.organization.position.JobHistory;

@Data
public class JobHistDto {

	private String historyId;
	private String companyCode;
	private GeneralDate startDate;
	private GeneralDate endDate;
	

}
