package nts.uk.ctx.at.record.app.command.monthly.dayoff;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.arc.layer.app.command.CommandHandlerContext;
import nts.uk.ctx.at.shared.app.util.attendanceitem.CommandFacade;
import nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.monthly.vacation.dayoff.MonthlyDayoffRemainDataRepository;

@Stateless
public class DayOffRemainMonthlyCommandHandler extends CommandFacade<DayOffRemainMonthlyCommand> {

	@Inject
	private MonthlyDayoffRemainDataRepository repo;

	@Override
	protected void handle(CommandHandlerContext<DayOffRemainMonthlyCommand> context) {
		if(context.getCommand().getData().isHaveData()) {
			repo.persistAndUpdate(context.getCommand().toDomain());
		}
		
	}
}
