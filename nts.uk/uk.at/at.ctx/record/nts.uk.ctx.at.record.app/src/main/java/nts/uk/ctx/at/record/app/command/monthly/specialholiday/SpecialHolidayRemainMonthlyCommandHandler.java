package nts.uk.ctx.at.record.app.command.monthly.specialholiday;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.arc.layer.app.command.CommandHandlerContext;
import nts.uk.ctx.at.shared.app.util.attendanceitem.CommandFacade;
import nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.monthly.vacation.specialholiday.SpecialHolidayRemainDataRepository;

@Stateless
public class SpecialHolidayRemainMonthlyCommandHandler extends CommandFacade<SpecialHolidayRemainMonthlyCommand> {

	@Inject
	private SpecialHolidayRemainDataRepository repo;

	@Override
	protected void handle(CommandHandlerContext<SpecialHolidayRemainMonthlyCommand> context) {
		if(!context.getCommand().getData().isEmpty()) {
			context.getCommand().toDomain().stream().forEach(d -> {
				repo.persistAndUpdate(d);
			});
		}
		
	}
}
