package nts.uk.ctx.at.request.app.command.application.appabsence;

import java.util.stream.Collectors;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.transaction.Transactional;

import nts.arc.layer.app.command.CommandHandlerContext;
import nts.arc.layer.app.command.CommandHandlerWithResult;
import nts.uk.ctx.at.request.dom.application.Application;
import nts.uk.ctx.at.request.dom.application.appabsence.ApplyForLeave;
import nts.uk.ctx.at.request.dom.application.appabsence.service.AbsenceServiceProcess;
import nts.uk.ctx.at.request.dom.application.common.service.other.output.ProcessResult;

@Stateless
@Transactional
public class UpdateAppAbsenceMobileCommandHandler extends CommandHandlerWithResult<UpdateAppAbsenceMobileCommand, ProcessResult> {
	
	@Inject 
    private AbsenceServiceProcess absenceServiceProcess;
	
	
	@Override
	protected ProcessResult handle(CommandHandlerContext<UpdateAppAbsenceMobileCommand> context) {
		UpdateAppAbsenceMobileCommand command = context.getCommand();
		Application application = command.getApplication().toDomain(command.getAppDispInfoStartupOutput().getAppDetailScreenInfo().getApplication());
		ApplyForLeave applyForLeave = command.getApplyForLeave().toDomain();
		applyForLeave.setApplication(application);
		
		return absenceServiceProcess.updateApplyForLeave(
	            applyForLeave, 
	            command.getHolidayAppDates(), 
	            command.getLeaveComDayOffManaDto().stream().map(x -> x.toDomain()).collect(Collectors.toList()), 
	            command.getPayoutSubofHDManagementDto().stream().map(x -> x.toDomain()).collect(Collectors.toList()), 
	            command.getAppDispInfoStartupOutput().toDomain());
	}

}
