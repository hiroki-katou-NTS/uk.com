package nts.uk.ctx.at.record.app.command.dailyperform.erroralarm;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.arc.layer.app.command.CommandHandlerContext;
import nts.uk.ctx.at.record.dom.workrecord.erroralarm.EmployeeDailyPerErrorRepository;
import nts.uk.ctx.at.shared.app.util.attendanceitem.CommandFacade;

@Stateless
public class EmployeeDailyPerErrorCommandUpdateHandler extends CommandFacade<EmployeeDailyPerErrorCommand> {

	@Inject
	private EmployeeDailyPerErrorRepository repo;

	@Override
	protected void handle(CommandHandlerContext<EmployeeDailyPerErrorCommand> context) {
//		repo.update(context.getCommand().getData());
	}

}
