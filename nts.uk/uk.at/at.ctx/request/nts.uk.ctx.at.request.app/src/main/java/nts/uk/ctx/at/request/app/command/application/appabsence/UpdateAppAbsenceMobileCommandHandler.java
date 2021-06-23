package nts.uk.ctx.at.request.app.command.application.appabsence;

import java.util.Optional;
import java.util.stream.Collectors;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.transaction.Transactional;

import nts.arc.layer.app.command.CommandHandlerContext;
import nts.arc.layer.app.command.CommandHandlerWithResult;
import nts.uk.ctx.at.request.dom.application.Application;
import nts.uk.ctx.at.request.dom.application.appabsence.ApplyForLeave;
import nts.uk.ctx.at.request.dom.application.appabsence.HolidayAppType;
import nts.uk.ctx.at.request.dom.application.appabsence.apptimedigest.TimeDigestApplication;
import nts.uk.ctx.at.request.dom.application.appabsence.service.AbsenceServiceProcess;
import nts.uk.ctx.at.request.dom.application.common.service.other.output.ProcessResult;
import nts.uk.ctx.at.shared.dom.common.time.AttendanceTime;

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
		
		if (applyForLeave.getVacationInfo().getHolidayApplicationType() == HolidayAppType.DIGESTION_TIME) {
			if (!applyForLeave.getReflectFreeTimeApp().getTimeDegestion().isPresent()) {
				applyForLeave.getReflectFreeTimeApp().setTimeDegestion(Optional.of(new TimeDigestApplication(
						new AttendanceTime(0), 
						new AttendanceTime(0), 
						new AttendanceTime(0), 
						new AttendanceTime(0), 
						new AttendanceTime(0), 
						new AttendanceTime(0),
			            Optional.empty()
						)));
				
			}
		}
		return absenceServiceProcess.updateApplyForLeave(
	            applyForLeave, 
	            command.getHolidayAppDates(), 
	            command.getLeaveComDayOffManaDto().stream().map(x -> x.toDomain()).collect(Collectors.toList()), 
	            command.getPayoutSubofHDManagementDto().stream().map(x -> x.toDomain()).collect(Collectors.toList()), 
	            command.getAppDispInfoStartupOutput().toDomain());
	}

}
