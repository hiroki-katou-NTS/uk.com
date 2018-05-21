package nts.uk.ctx.at.record.app.command.remainingnumber.dayoffmanagement;

import java.util.stream.Collectors;

import javax.inject.Inject;

import nts.arc.layer.app.command.CommandHandler;
import nts.arc.layer.app.command.CommandHandlerContext;
import nts.uk.ctx.at.record.dom.remainingnumber.dayoffmanagement.DayOffManagementData;
import nts.uk.ctx.at.record.dom.remainingnumber.dayoffmanagement.DayOffManagementService;
import nts.uk.ctx.at.record.dom.remainingnumber.dayoffmanagement.DaysOffMana;

public class DayOffManaCommandHandler extends CommandHandler<DayOffManaCommand> {
	
	@Inject
	private DayOffManagementService dayOffManaService;
	
	@Override
	protected void handle(CommandHandlerContext<DayOffManaCommand> context) {

		DayOffManaCommand dayOffManaCommand = context.getCommand();

		DayOffManagementData dayOffManagementData = new DayOffManagementData(
				dayOffManaCommand.getDaysOffMana().stream().map(item -> {
					return new DaysOffMana(item.getCode(), item.getDate(), item.getUseNumberDay());
				}).collect(Collectors.toList()), dayOffManaCommand.getEmployeeId(), dayOffManaCommand.getLeaveId());
		 
		dayOffManaService.updateDayOff(dayOffManagementData);

	}

}
