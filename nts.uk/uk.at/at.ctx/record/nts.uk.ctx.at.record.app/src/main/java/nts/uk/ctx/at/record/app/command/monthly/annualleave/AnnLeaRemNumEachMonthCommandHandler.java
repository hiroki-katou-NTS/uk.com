package nts.uk.ctx.at.record.app.command.monthly.annualleave;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.arc.layer.app.command.CommandHandlerContext;
import nts.uk.ctx.at.record.dom.monthly.vacation.annualleave.AnnLeaRemNumEachMonthRepository;
import nts.uk.ctx.at.shared.app.util.attendanceitem.CommandFacade;

@Stateless
public class AnnLeaRemNumEachMonthCommandHandler extends CommandFacade<AnnLeaRemNumEachMonthCommand> {

	@Inject
	private AnnLeaRemNumEachMonthRepository repo;

	@Override
	protected void handle(CommandHandlerContext<AnnLeaRemNumEachMonthCommand> context) {
		if(context.getCommand().getData().isHaveData()) {
			repo.persistAndUpdate(context.getCommand().toDomain());
		}
		
	}
}
