package nts.uk.ctx.basic.app.find.organization.position;


import lombok.Data;
import nts.arc.time.GeneralDate;

@Data
public class JobHistDto {

	private String historyId;
	private String companyCode;
	private GeneralDate startDate;
	private GeneralDate endDate;
	

}
