package nts.uk.ctx.at.schedule.app.command.shift.specificdayset.workplace;

import lombok.Data;

@Data
public class DeleteWorkplaceSpecificDateCommand {
	private String workPlaceId;
	
	private String yearMonth;
}
