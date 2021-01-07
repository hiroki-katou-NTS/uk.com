package nts.uk.ctx.at.record.app.command.monthly.specialholiday;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.arc.enums.EnumAdaptor;
import nts.arc.layer.app.command.CommandHandlerContext;
import nts.uk.ctx.at.shared.app.util.attendanceitem.CommandFacade;
import nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.monthly.vacation.specialholiday.SpecialHolidayRemainDataRepository;
import nts.uk.ctx.at.shared.dom.workrule.closure.ClosureId;

@Stateless
public class SpecialHolidayRemainMonthlyCommandHandler extends CommandFacade<SpecialHolidayRemainMonthlyCommand> {

	@Inject
	private SpecialHolidayRemainDataRepository repo;

	@Override
	protected void handle(CommandHandlerContext<SpecialHolidayRemainMonthlyCommand> context) {
		if(context.getCommand().toDomain() != null) {
//			context.getCommand().toDomain().stream().forEach(d -> {
				repo.persistAndUpdate(context.getCommand().toDomain());
//			});
		} else {
			repo.remove(context.getCommand().getEmployeeId(), context.getCommand().getYearMonth(),
					EnumAdaptor.valueOf(context.getCommand().getClosureId(), ClosureId.class),
					context.getCommand().getClosureDate().toDomain());
		}
	}
}
