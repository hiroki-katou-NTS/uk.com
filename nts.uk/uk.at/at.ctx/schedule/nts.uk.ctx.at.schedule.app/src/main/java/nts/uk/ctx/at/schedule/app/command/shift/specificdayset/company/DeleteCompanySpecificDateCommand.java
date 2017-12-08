package nts.uk.ctx.at.schedule.app.command.shift.specificdayset.company;

import lombok.Data;

@Data
public class DeleteCompanySpecificDateCommand {
	private String startDate;
	
	private String endDate;
}
