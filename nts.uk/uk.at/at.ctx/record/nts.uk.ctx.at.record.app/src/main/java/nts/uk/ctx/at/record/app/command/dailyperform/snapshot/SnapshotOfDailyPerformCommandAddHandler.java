package nts.uk.ctx.at.record.app.command.dailyperform.snapshot;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.arc.layer.app.command.CommandHandlerContext;
import nts.uk.ctx.at.record.dom.adapter.schedule.snapshot.DailySnapshotWorkAdapter;
import nts.uk.ctx.at.record.dom.adapter.schedule.snapshot.DailySnapshotWorkImport;
import nts.uk.ctx.at.shared.app.util.attendanceitem.CommandFacade;

@Stateless
public class SnapshotOfDailyPerformCommandAddHandler extends CommandFacade<SnapshotOfDailyPerformCommand> {

	@Inject
	private DailySnapshotWorkAdapter repo;

	@Override
	protected void handle(CommandHandlerContext<SnapshotOfDailyPerformCommand> context) {
		context.getCommand().getData().ifPresent(d -> {
			repo.save(DailySnapshotWorkImport.from(context.getCommand().getEmployeeId(), context.getCommand().getWorkDate(), d));
		});
	}
}
