package nts.uk.ctx.at.request.app.command.application.remainingnumber.checkfunc;

import lombok.Data;

@Data
public class PlannedVacationListCommand {
	private int maxNumberDays;
	private String workTypeCode;
	private String workTypeName;
	
}

