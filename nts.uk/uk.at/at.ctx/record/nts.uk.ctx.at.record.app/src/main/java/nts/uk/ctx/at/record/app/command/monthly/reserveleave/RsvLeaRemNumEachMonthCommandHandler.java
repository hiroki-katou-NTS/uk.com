package nts.uk.ctx.at.record.app.command.monthly.reserveleave;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.arc.layer.app.command.CommandHandlerContext;
import nts.uk.ctx.at.record.dom.monthly.vacation.reserveleave.RsvLeaRemNumEachMonthRepository;
import nts.uk.ctx.at.shared.app.util.attendanceitem.CommandFacade;

@Stateless
public class RsvLeaRemNumEachMonthCommandHandler extends CommandFacade<RsvLeaRemNumEachMonthCommand> {

	@Inject
	private RsvLeaRemNumEachMonthRepository repo;

	@Override
	protected void handle(CommandHandlerContext<RsvLeaRemNumEachMonthCommand> context) {
		if(context.getCommand().getData().isHaveData()) {
			repo.persistAndUpdate(context.getCommand().getData().toDomain());
		}
		
	}
}
