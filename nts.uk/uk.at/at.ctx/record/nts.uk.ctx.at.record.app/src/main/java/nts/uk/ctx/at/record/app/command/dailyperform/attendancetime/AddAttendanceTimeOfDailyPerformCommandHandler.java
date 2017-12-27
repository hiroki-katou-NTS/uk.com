package nts.uk.ctx.at.record.app.command.dailyperform.attendancetime;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.arc.layer.app.command.CommandHandlerContext;
import nts.arc.time.GeneralDate;
import nts.uk.ctx.at.record.app.find.dailyperform.dto.AttendanceTimeDailyPerformDto;
import nts.uk.ctx.at.record.dom.actualworkinghours.AttendanceTimeOfDailyPerformance;
import nts.uk.ctx.at.record.dom.actualworkinghours.repository.AttendanceTimeRepository;
import nts.uk.ctx.at.shared.app.util.attendanceitem.CommandFacade;
import nts.uk.ctx.at.shared.dom.common.time.AttendanceTime;

@Stateless
public class AddAttendanceTimeOfDailyPerformCommandHandler extends CommandFacade<AttendanceTimeOfDailyPerformCommand> {

	@Inject
	private AttendanceTimeRepository attendanceTimeRepo;

	@Override
	protected void handle(CommandHandlerContext<AttendanceTimeOfDailyPerformCommand> context) {
		AttendanceTimeOfDailyPerformCommand command = context.getCommand();
		attendanceTimeRepo.add(toDomain(command.getEmployeeId(), command.getWorkDate(), command.getData()));
	}

	private AttendanceTimeOfDailyPerformance toDomain(String employeeId, GeneralDate date,
			AttendanceTimeDailyPerformDto dto) {
		return new AttendanceTimeOfDailyPerformance(employeeId, date, dto.getScheduleTime().toDomain(),
				dto.getActualWorkTime().toDomain(), dto.getStayingTime().toDomain(),
				new AttendanceTime(dto.getBudgetTimeVariance()), new AttendanceTime(dto.getUnemployedTime()));
	}

}
