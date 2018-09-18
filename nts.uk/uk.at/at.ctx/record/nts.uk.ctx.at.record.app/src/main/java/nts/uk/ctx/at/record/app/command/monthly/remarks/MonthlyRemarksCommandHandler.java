package nts.uk.ctx.at.record.app.command.monthly.remarks;

import javax.ejb.Stateless;

import nts.arc.layer.app.command.CommandHandlerContext;
import nts.uk.ctx.at.shared.app.util.attendanceitem.CommandFacade;

@Stateless
public class MonthlyRemarksCommandHandler extends CommandFacade<MonthlyRemarksCommand> {

	/**TODO: inject repo*/
//	@Inject
//	private AnnLeaRemNumEachMonthRepository repo;

	@Override
	protected void handle(CommandHandlerContext<MonthlyRemarksCommand> context) {
		if(!context.getCommand().getData().isEmpty()) {
			context.getCommand().toDomain().stream().forEach(d -> {
//				repo.persistAndUpdate(d);
			});
		}
		
	}
}
