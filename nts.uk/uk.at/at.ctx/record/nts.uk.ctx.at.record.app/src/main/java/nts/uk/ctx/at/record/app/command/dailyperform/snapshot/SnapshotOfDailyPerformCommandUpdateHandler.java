package nts.uk.ctx.at.record.app.command.dailyperform.snapshot;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.arc.layer.app.command.CommandHandlerContext;
import nts.uk.ctx.at.record.dom.daily.DailyRecordAdUpService;
import nts.uk.ctx.at.shared.app.util.attendanceitem.CommandFacade;

@Stateless
public class SnapshotOfDailyPerformCommandUpdateHandler extends CommandFacade<SnapshotOfDailyPerformCommand> {

	@Inject
	private DailyRecordAdUpService adUpRepo;

	@Override
	protected void handle(CommandHandlerContext<SnapshotOfDailyPerformCommand> context) {
		context.getCommand().getData().ifPresent(d -> {
			adUpRepo.adUpSnapshot(context.getCommand().getEmployeeId(), context.getCommand().getWorkDate(), d);
		});
	}

}
