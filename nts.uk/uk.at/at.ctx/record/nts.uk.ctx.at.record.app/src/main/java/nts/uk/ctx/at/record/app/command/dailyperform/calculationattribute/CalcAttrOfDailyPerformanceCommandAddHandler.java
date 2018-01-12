package nts.uk.ctx.at.record.app.command.dailyperform.calculationattribute;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.arc.layer.app.command.CommandHandlerContext;
import nts.uk.ctx.at.record.dom.calculationattribute.repo.CalAttrOfDailyPerformanceRepository;
import nts.uk.ctx.at.shared.app.util.attendanceitem.CommandFacade;

@Stateless
public class CalcAttrOfDailyPerformanceCommandAddHandler extends CommandFacade<CalcAttrOfDailyPerformanceCommand> {

	@Inject
	private CalAttrOfDailyPerformanceRepository repo;

	@Override
	protected void handle(CommandHandlerContext<CalcAttrOfDailyPerformanceCommand> context) {
		CalcAttrOfDailyPerformanceCommand command = context.getCommand();
		repo.add(command.toDomain());
	}

	
}
