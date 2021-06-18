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
public class CreatAppAbsenceMobileCommandHandler extends CommandHandlerWithResult<RegisterAppAbsenceMobileCommand, ProcessResult> {
	
	@Inject 
	private AbsenceServiceProcess absenceServiceProcess;
	
	@Override
	protected ProcessResult handle(CommandHandlerContext<RegisterAppAbsenceMobileCommand> context) {
		
		RegisterAppAbsenceMobileCommand command = context.getCommand();
		ApplyForLeave applyForLeave = command.getApplyForLeave().toDomain();
		Application application = command.getApplication().toDomain();
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
		
		
		return absenceServiceProcess.registerAppAbsence(
		        applyForLeave, 
		        command.getAppDates(), 
		        command.getLeaveComDayOffMana().stream().map(x -> x.toDomain()).collect(Collectors.toList()), 
		        command.getPayoutSubofHDManagements().stream().map(x -> x.toDomain()).collect(Collectors.toList()), 
		        command.isMailServerSet(),
		        command.getApprovalRoot().stream().map(x -> x.toDomain()).collect(Collectors.toList()),
		        command.getApptypeSetting().toDomain());
	}

}
