package nts.uk.ctx.at.request.app.find.application.appabsence.dto;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import nts.uk.ctx.at.request.app.command.application.appabsence.ApplyForLeaveDto;
import nts.uk.ctx.at.request.app.command.application.common.AppDispInfoStartupCmd;

@AllArgsConstructor
@Data
public class StartMobileParam {
	
	private Integer mode;
	
	private String companyId;
	
	private String employeeIdOp;
	
	private List<String> datesOp;
	
	private AppAbsenceStartInfoDto appAbsenceStartInfoOutputOp;
	
	private ApplyForLeaveDto applyForLeaveOp;
	
	private AppDispInfoStartupCmd appDispInfoStartupOutput;
}
