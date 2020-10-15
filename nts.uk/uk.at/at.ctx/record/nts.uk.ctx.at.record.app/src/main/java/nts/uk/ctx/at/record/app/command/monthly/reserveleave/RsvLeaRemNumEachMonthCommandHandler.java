package nts.uk.ctx.at.record.app.command.monthly.reserveleave;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.arc.layer.app.command.CommandHandlerContext;
import nts.uk.ctx.at.shared.app.util.attendanceitem.CommandFacade;
import nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.monthly.vacation.reserveleave.RsvLeaRemNumEachMonthRepository;

@Stateless
public class RsvLeaRemNumEachMonthCommandHandler extends CommandFacade<RsvLeaRemNumEachMonthCommand> {

	@Inject
	private RsvLeaRemNumEachMonthRepository repo;

	@Override
	protected void handle(CommandHandlerContext<RsvLeaRemNumEachMonthCommand> context) {
		if(context.getCommand().getData().isHaveData()) {
			repo.persistAndUpdate(context.getCommand().toDomain());
		}
		
	}
}
