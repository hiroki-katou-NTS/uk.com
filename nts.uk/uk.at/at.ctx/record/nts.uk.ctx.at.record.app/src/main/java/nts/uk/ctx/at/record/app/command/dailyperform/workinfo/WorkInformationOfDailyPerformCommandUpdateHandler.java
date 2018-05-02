package nts.uk.ctx.at.record.app.command.dailyperform.workinfo;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.arc.layer.app.command.CommandHandlerContext;
import nts.uk.ctx.at.record.dom.workinformation.WorkInfoOfDailyPerformance;
import nts.uk.ctx.at.record.dom.workinformation.repository.WorkInformationRepository;
import nts.uk.ctx.at.shared.app.util.attendanceitem.CommandFacade;

@Stateless
public class WorkInformationOfDailyPerformCommandUpdateHandler extends CommandFacade<WorkInformationOfDailyPerformCommand> {

	@Inject
	private WorkInformationRepository repo;

	@Override
	protected void handle(CommandHandlerContext<WorkInformationOfDailyPerformCommand> context) {
		WorkInfoOfDailyPerformance domain = context.getCommand().getData();
		repo.updateByKey(domain);
		domain.workInfoChanged();
	}

}
