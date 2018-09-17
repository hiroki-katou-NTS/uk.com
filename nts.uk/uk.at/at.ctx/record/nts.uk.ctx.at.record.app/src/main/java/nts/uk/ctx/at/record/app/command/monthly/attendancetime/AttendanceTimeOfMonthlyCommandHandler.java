package nts.uk.ctx.at.record.app.command.monthly.attendancetime;

import java.util.Optional;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.arc.layer.app.command.CommandHandlerContext;
import nts.uk.ctx.at.record.dom.monthly.AttendanceTimeOfMonthlyRepository;
import nts.uk.ctx.at.shared.app.util.attendanceitem.CommandFacade;

@Stateless
public class AttendanceTimeOfMonthlyCommandHandler extends CommandFacade<AttendanceTimeOfMonthlyCommand> {

	@Inject
	private AttendanceTimeOfMonthlyRepository repo;

	@Override
	protected void handle(CommandHandlerContext<AttendanceTimeOfMonthlyCommand> context) {
		if(context.getCommand().getData().isHaveData()) {
			repo.persistAndUpdate(context.getCommand().toDomain(), Optional.empty());
		}
		
	}
}
