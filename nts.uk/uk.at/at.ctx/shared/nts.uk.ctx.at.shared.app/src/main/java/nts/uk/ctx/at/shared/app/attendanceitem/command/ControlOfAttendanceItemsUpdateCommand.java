package nts.uk.ctx.at.shared.app.attendanceitem.command;

import java.math.BigDecimal;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.arc.layer.app.command.CommandHandler;
import nts.arc.layer.app.command.CommandHandlerContext;
import nts.uk.ctx.at.shared.dom.attendanceitem.ControlOfAttendanceItems;
import nts.uk.ctx.at.shared.dom.attendanceitem.repository.ControlOfAttendanceItemsRepository;

@Stateless
public class ControlOfAttendanceItemsUpdateCommand extends CommandHandler<ControlOfAttendanceItemsCommand> {
	@Inject
	private ControlOfAttendanceItemsRepository controlOfAttendanceItemsRepository;

	@Override
	protected void handle(CommandHandlerContext<ControlOfAttendanceItemsCommand> context) {

		ControlOfAttendanceItemsCommand controlOfAttendanceItemsCommand = context.getCommand();
		this.controlOfAttendanceItemsRepository
				.updateControlOfAttendanceItem(this.toControlOfAttendanceItemsDomain(controlOfAttendanceItemsCommand));
	}

	private ControlOfAttendanceItems toControlOfAttendanceItemsDomain(
			ControlOfAttendanceItemsCommand controlOfAttendanceItemsCommand) {
		return ControlOfAttendanceItems.createFromJavaType(  new BigDecimal(controlOfAttendanceItemsCommand.attandanceTimeId) ,
				controlOfAttendanceItemsCommand.inputUnitOfTimeItem,
				controlOfAttendanceItemsCommand.headerBackgroundColorOfDailyPerformance,controlOfAttendanceItemsCommand.nameLineFeedPosition);
	}

}
