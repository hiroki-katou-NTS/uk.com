package nts.uk.ctx.at.record.app.command.monthly.anyitem;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.arc.layer.app.command.CommandHandlerContext;
import nts.uk.ctx.at.shared.app.util.attendanceitem.CommandFacade;
import nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.monthly.anyitem.AnyItemOfMonthlyRepository;

@Stateless
public class AnyItemOfMonthlyCommandHandler extends CommandFacade<AnyItemOfMonthlyCommand> {

	@Inject
	private AnyItemOfMonthlyRepository repo;

	@Override
	protected void handle(CommandHandlerContext<AnyItemOfMonthlyCommand> context) {
		if(context.getCommand().getData().isHaveData()) {
			context.getCommand().toDomain().forEach(d -> {
				repo.persistAndUpdate(d);
			});
		}
		
	}
}
