package nts.uk.ctx.at.request.app.command.application.appabsence;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import nts.uk.ctx.at.request.app.find.application.ApplicationDto;
import nts.uk.ctx.at.request.app.find.application.appabsence.dto.AppAbsenceStartInfoDto;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CreatAppAbsenceCommand {
	
	private AppAbsenceStartInfoDto appAbsenceStartInfoDto;
	
	private ApplyForLeaveDto applyForLeave;
	
	private boolean agentAtr;
	
}
