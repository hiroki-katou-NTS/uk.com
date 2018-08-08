package nts.uk.screen.at.app.ktgwidget.find.dto;

import java.util.List;


import lombok.Value;
import nts.uk.ctx.at.shared.app.query.workrule.closure.ClosureResultModel;

@Value

public class OvertimeHoursDto {
	
	int closureID;
	
	List<ClosureResultModel> listclosureID;
	
	private OvertimeHours overtimeHours;

	public OvertimeHoursDto(int closureID,List<ClosureResultModel> listclosureID, OvertimeHours overtimeHours) {
		super();
		this.closureID = closureID;
		this.listclosureID = listclosureID;
		this.overtimeHours = overtimeHours;
	}

	
	
}
