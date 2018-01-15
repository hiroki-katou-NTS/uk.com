package nts.uk.ctx.at.schedule.app.command.shift.specificdayset.company;

import java.util.List;

import lombok.Value;

@Value
public class UpdateCompanySpecificDateCommand {

	private String specificDate;

	private List<Integer> specificDateItemNo;
	
	private boolean isUpdate;
}
