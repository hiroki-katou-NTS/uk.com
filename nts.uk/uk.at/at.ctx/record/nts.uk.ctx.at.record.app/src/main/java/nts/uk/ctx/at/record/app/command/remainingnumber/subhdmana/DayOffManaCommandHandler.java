package nts.uk.ctx.at.record.app.command.remainingnumber.subhdmana;

import java.util.stream.Collectors;

import javax.inject.Inject;

import nts.arc.layer.app.command.CommandHandler;
import nts.arc.layer.app.command.CommandHandlerContext;
import nts.uk.ctx.at.record.dom.remainingnumber.paymana.DayOffManagementData;
import nts.uk.ctx.at.record.dom.remainingnumber.paymana.DaysOffMana;
import nts.uk.ctx.at.record.dom.remainingnumber.paymana.service.DayOffManagementService;

public class DayOffManaCommandHandler extends CommandHandler<DayOffManaCommand> {
	
	@Inject
	private DayOffManagementService dayOffManaService;
	
	@Override
	protected void handle(CommandHandlerContext<DayOffManaCommand> context) {

		DayOffManaCommand dayOffManaCommand = context.getCommand();

		DayOffManagementData dayOffManagementData = new DayOffManagementData(
				dayOffManaCommand.getComDayOffManaDtos().stream().map(item -> {
					return new DaysOffMana(item.getComDayOffID(), item.getDayOff(), item.getRemainDays());
				}).collect(Collectors.toList()), dayOffManaCommand.getEmployeeId(), dayOffManaCommand.getLeaveId());
		 
		dayOffManaService.updateDayOff(dayOffManagementData);

	}

}
