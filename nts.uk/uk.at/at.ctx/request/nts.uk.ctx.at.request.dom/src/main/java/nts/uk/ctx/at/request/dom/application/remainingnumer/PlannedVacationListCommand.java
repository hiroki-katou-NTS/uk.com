package nts.uk.ctx.at.request.dom.application.remainingnumer;

import lombok.Data;

@Data
public class PlannedVacationListCommand {
	private int maxNumberDays;
	private String workTypeCode;
	private String workTypeName;
	
}

