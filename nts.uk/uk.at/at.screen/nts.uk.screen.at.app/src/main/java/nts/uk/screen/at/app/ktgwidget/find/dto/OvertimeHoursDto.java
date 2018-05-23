package nts.uk.screen.at.app.ktgwidget.find.dto;

import java.util.List;


import lombok.Value;
import nts.uk.ctx.at.shared.app.query.workrule.closure.ClosureResultModel;

@Value

public class OvertimeHoursDto {
	
	List<ClosureResultModel> listclosureID;
	
	private OvertimeHours overtimeHours;

	public OvertimeHoursDto(List<ClosureResultModel> listclosureID, OvertimeHours overtimeHours) {
		super();
		this.listclosureID = listclosureID;
		this.overtimeHours = overtimeHours;
	}

	
	
}
