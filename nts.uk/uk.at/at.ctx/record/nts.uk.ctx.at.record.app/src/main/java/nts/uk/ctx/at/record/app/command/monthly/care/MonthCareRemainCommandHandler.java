package nts.uk.ctx.at.record.app.command.monthly.care;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.arc.layer.app.command.CommandHandlerContext;
import nts.uk.ctx.at.record.dom.monthly.information.care.MonCareHdRemainRepository;
import nts.uk.ctx.at.shared.app.util.attendanceitem.CommandFacade;

@Stateless
public class MonthCareRemainCommandHandler extends CommandFacade<MonthCareRemainCommand> {

	@Inject
	private MonCareHdRemainRepository repo;

	@Override
	protected void handle(CommandHandlerContext<MonthCareRemainCommand> context) {
		if(context.getCommand().getData().isHaveData()) {
			repo.persistAndUpdate(context.getCommand().toDomain());
		}
		
	}
}
