package nts.uk.ctx.at.record.app.command.dailyperform.attendanceleavinggate;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.arc.layer.app.command.CommandHandlerContext;
import nts.uk.ctx.at.record.dom.daily.attendanceleavinggate.repo.AttendanceLeavingGateOfDailyRepo;
import nts.uk.ctx.at.shared.app.util.attendanceitem.CommandFacade;

@Stateless
public class AttendanceLeavingGateOfDailyCommandAddHandler extends CommandFacade<AttendanceLeavingGateOfDailyCommand> {

	@Inject
	private AttendanceLeavingGateOfDailyRepo repo;

	@Override
	protected void handle(CommandHandlerContext<AttendanceLeavingGateOfDailyCommand> context) {
		if(context.getCommand().getData().isPresent()){
			repo.add(context.getCommand().toDomain().get());
		}
	}

}
