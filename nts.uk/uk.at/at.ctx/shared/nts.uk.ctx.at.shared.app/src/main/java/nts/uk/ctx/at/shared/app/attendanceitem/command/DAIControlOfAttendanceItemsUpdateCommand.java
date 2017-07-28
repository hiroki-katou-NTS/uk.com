package nts.uk.ctx.at.shared.app.attendanceitem.command;

import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.arc.layer.app.command.CommandHandler;
import nts.arc.layer.app.command.CommandHandlerContext;
import nts.uk.ctx.at.shared.dom.attendanceitem.DisplayAndInputControlOfAttendanceItems;
import nts.uk.ctx.at.shared.dom.attendanceitem.repository.DAIControlOfAttendanceItemsRepository;

@Stateless
public class DAIControlOfAttendanceItemsUpdateCommand extends CommandHandler<List<DAIControlOfAttendanceItemsCommand>> {
	@Inject
	private DAIControlOfAttendanceItemsRepository dIControlOfAttendanceItemsRepository;

	@Override
	protected void handle(CommandHandlerContext<List<DAIControlOfAttendanceItemsCommand>> context) {
		List<DAIControlOfAttendanceItemsCommand> lstDAIControlOfAttendanceItemsCommand = context.getCommand();
		this.dIControlOfAttendanceItemsRepository
				.updateListControlOfAttendanceItem(lstDAIControlOfAttendanceItemsCommand.stream()
						.map(c -> toDAIControlOfAttendanceItemsDomain(c)).collect(Collectors.toList()));
	}

	private DisplayAndInputControlOfAttendanceItems toDAIControlOfAttendanceItemsDomain(
			DAIControlOfAttendanceItemsCommand dAIControlOfAttendanceItemsCommand) {
		return DisplayAndInputControlOfAttendanceItems.createFromJavaType(
				new BigDecimal(dAIControlOfAttendanceItemsCommand.attendanceItemId),
				dAIControlOfAttendanceItemsCommand.businessTypeCode, 
				dAIControlOfAttendanceItemsCommand.youCanChangeIt,
				dAIControlOfAttendanceItemsCommand.canBeChangedByOthers, dAIControlOfAttendanceItemsCommand.use);
	}

}
