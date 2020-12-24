package nts.uk.ctx.at.request.app.command.application.appabsence;

import java.util.List;

import lombok.Data;
import nts.uk.ctx.at.request.app.command.application.common.ApplicationCommand_New;
import nts.uk.ctx.at.request.app.find.application.appabsence.dto.AppAbsenceStartInfoDto;

@Data
public class UpdateAppAbsenceCommand {
	
	private String companyID;
	
	private AppAbsenceStartInfoDto appAbsenceStartInfoDto;
	
	private ApplicationCommand_New applicationCommand;
	
	private AppAbsenceCommand appAbsenceCommand;
	
	private Integer alldayHalfDay;
	
	private boolean agentAtr;
	
	private Boolean mourningAtr;
	
	public List<String> holidayDateLst;
}
