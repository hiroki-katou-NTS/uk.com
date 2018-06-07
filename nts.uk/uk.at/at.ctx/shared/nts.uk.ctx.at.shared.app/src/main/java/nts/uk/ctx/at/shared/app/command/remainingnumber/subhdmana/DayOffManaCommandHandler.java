package nts.uk.ctx.at.shared.app.command.remainingnumber.subhdmana;

import java.util.List;
import java.util.stream.Collectors;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.arc.layer.app.command.CommandHandlerContext;
import nts.arc.layer.app.command.CommandHandlerWithResult;
import nts.uk.ctx.at.shared.dom.remainingnumber.subhdmana.DayOffManagementData;
import nts.uk.ctx.at.shared.dom.remainingnumber.subhdmana.DaysOffMana;
import nts.uk.ctx.at.shared.dom.remainingnumber.subhdmana.service.DayOffManagementService;

@Stateless
public class DayOffManaCommandHandler extends CommandHandlerWithResult<DayOffManaCommand,List<String>> {
	
	@Inject
	private DayOffManagementService dayOffManaService;
	
	@Override
	protected List<String> handle(CommandHandlerContext<DayOffManaCommand> context) {

		DayOffManaCommand dayOffManaCommand = context.getCommand();

		DayOffManagementData dayOffManagementData = new DayOffManagementData(
				dayOffManaCommand.getComDayOffManaDtos().stream()
						.map(item -> new DaysOffMana(item.getComDayOffID(), item.getDayOff(), item.getRemainDays()))
						.collect(Collectors.toList()),
				dayOffManaCommand.getEmployeeId(), dayOffManaCommand.getLeaveId(),
				dayOffManaCommand.getNumberDayParam());
		 
		return dayOffManaService.updateDayOff(dayOffManagementData);
	}

}
