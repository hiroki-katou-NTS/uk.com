package nts.uk.ctx.at.record.app.command.dailyperform.attendancetime;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.arc.layer.app.command.CommandHandlerContext;
import nts.uk.ctx.at.record.dom.actualworkinghours.repository.AttendanceTimeRepository;
import nts.uk.ctx.at.record.dom.dailyprocess.calc.CalculateDailyRecordService;
import nts.uk.ctx.at.record.dom.dailyprocess.calc.IntegrationOfDaily;
import nts.uk.ctx.at.shared.app.util.attendanceitem.CommandFacade;
import nts.uk.shr.com.context.AppContexts;

@Stateless
public class AttendanceTimeOfDailyPerformCommandUpdateHandler
		extends CommandFacade<AttendanceTimeOfDailyPerformCommand> {

	@Inject
	private AttendanceTimeRepository repo;

	@Inject
	private CalculateDailyRecordService calcService;

	@Override
	protected void handle(CommandHandlerContext<AttendanceTimeOfDailyPerformCommand> context) {
		AttendanceTimeOfDailyPerformCommand command = context.getCommand();
		if (command.getData().isPresent()) {
//			repo.update(command.toDomain());
			repo.update(calcService.calculate(
							AppContexts.user().companyId(), 
							command.getAffiliationInfo().getData().getWorkplaceID(),
							command.getAffiliationInfo().getData().getEmploymentCode(), 
							command.getEmployeeId(),
							command.getWorkDate(),
							new IntegrationOfDaily(
									command.getWorkInfo().toDomain(),
									command.getTimeLeaving().toDomain(), 
									command.toDomain()))
					.getAttendanceTimeOfDailyPerformance());
		}
	}

}
