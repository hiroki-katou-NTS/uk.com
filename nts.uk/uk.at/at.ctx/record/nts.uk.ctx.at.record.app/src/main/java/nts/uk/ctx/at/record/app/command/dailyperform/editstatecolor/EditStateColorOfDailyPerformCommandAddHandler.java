package nts.uk.ctx.at.record.app.command.dailyperform.editstatecolor;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.arc.layer.app.command.CommandHandlerContext;
import nts.uk.ctx.at.record.dom.editstate.repository.EditStateOfDailyPerformanceRepository;
import nts.uk.ctx.at.shared.app.util.attendanceitem.CommandFacade;
@Stateless
public class EditStateColorOfDailyPerformCommandAddHandler extends CommandFacade<EditStateColorOfDailyPerformCommand> {

	@Inject
	private EditStateOfDailyPerformanceRepository repo;
	
	@Override
	protected void handle(CommandHandlerContext<EditStateColorOfDailyPerformCommand> context) {
		EditStateColorOfDailyPerformCommand command = context.getCommand();
		if(!command.getData().isEmpty()){
			repo.addAndUpdate(command.getData());
		}
	}

}
