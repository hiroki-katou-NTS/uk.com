package nts.uk.ctx.at.record.app.command.monthly.childcare;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.arc.layer.app.command.CommandHandlerContext;
import nts.uk.ctx.at.shared.app.util.attendanceitem.CommandFacade;
import nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.monthly.information.childnursing.MonChildHdRemainRepository;

@Stateless
public class MonthChildCareRemainCommandHandler extends CommandFacade<MonthChildCareRemainCommand> {

	@Inject
	private MonChildHdRemainRepository repo;

	@Override
	protected void handle(CommandHandlerContext<MonthChildCareRemainCommand> context) {
		if(context.getCommand().getData().isHaveData()) {
			repo.persistAndUpdate(context.getCommand().toDomain());
		}
		
	}
}
