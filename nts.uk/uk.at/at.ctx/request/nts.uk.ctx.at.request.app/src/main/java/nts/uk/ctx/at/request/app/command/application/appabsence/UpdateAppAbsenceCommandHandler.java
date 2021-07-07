package nts.uk.ctx.at.request.app.command.application.appabsence;

import java.util.stream.Collectors;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.arc.layer.app.command.CommandHandlerContext;
import nts.arc.layer.app.command.CommandHandlerWithResult;
import nts.uk.ctx.at.request.app.find.application.ApplicationDto;
import nts.uk.ctx.at.request.dom.application.Application;
import nts.uk.ctx.at.request.dom.application.appabsence.ApplyForLeave;
import nts.uk.ctx.at.request.dom.application.appabsence.service.AbsenceServiceProcess;
import nts.uk.ctx.at.request.dom.application.common.service.other.output.ProcessResult;

@Stateless
public class UpdateAppAbsenceCommandHandler extends CommandHandlerWithResult<UpdateAppAbsenceCommand, ProcessResult>{
	
	@Inject 
    private AbsenceServiceProcess absenceServiceProcess;
	
	@Override
	protected ProcessResult handle(CommandHandlerContext<UpdateAppAbsenceCommand> context) {
	    UpdateAppAbsenceCommand command = context.getCommand();
	    
	    ApplicationDto applicationDto = command.getApplication();
	    Application application = applicationDto.toDomain();
	    ApplyForLeave applyForLeave = command.getApplyForLeave().toDomain();
	    applyForLeave.setApplication(application);
	    
	    // 休暇申請（詳細）更新処理
	    return absenceServiceProcess.updateApplyForLeave(
	            applyForLeave, 
	            command.getHolidayAppDates(), 
	            command.getLeaveComDayOffMana().stream().map(x -> x.toDomain()).collect(Collectors.toList()), 
	            command.getPayoutSubofHDManagements().stream().map(x -> x.toDomain()).collect(Collectors.toList()), 
	            command.getAppDispInfoStartupOutput().toDomain());
	}

}
