package nts.uk.ctx.at.schedule.app.command.shift.specificdayset.workplace;

import java.util.List;

import lombok.Value;

@Value
public class WorkplaceSpecificDateCommand {
	
	private String workPlaceId;

	private String specificDate;

	private List<Integer> specificDateItemNo;
	
	private boolean isUpdate;
}
