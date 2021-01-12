package nts.uk.ctx.at.request.app.find.application.appabsence.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import nts.uk.ctx.at.request.app.command.application.appabsence.ApplyForLeaveDto;
import nts.uk.ctx.at.request.app.command.application.common.ApplicationInsertCmd;
import nts.uk.ctx.at.request.app.command.application.common.ApplicationUpdateCmd;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class CheckInsertMobileParam {
	
	private String companyId;
	
	private AppAbsenceStartInfoDto appAbsenceStartInfoDto;
	
	private ApplyForLeaveDto applyForLeave;
	
	private ApplicationInsertCmd application;
	
	private ApplicationUpdateCmd applicationUpdate;
	
	private Boolean mode;
}
