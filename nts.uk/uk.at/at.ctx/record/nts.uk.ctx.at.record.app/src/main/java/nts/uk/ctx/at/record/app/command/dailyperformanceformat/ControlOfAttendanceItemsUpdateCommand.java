package nts.uk.ctx.at.record.app.command.dailyperformanceformat;

import java.math.BigDecimal;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.arc.layer.app.command.CommandHandler;
import nts.arc.layer.app.command.CommandHandlerContext;
import nts.uk.ctx.at.shared.dom.scherec.dailyattendanceitem.ControlOfAttendanceItems;
import nts.uk.ctx.at.shared.dom.scherec.dailyattendanceitem.repository.ControlOfAttendanceItemsRepository;

@Stateless
public class ControlOfAttendanceItemsUpdateCommand extends CommandHandler<ControlOfAttendanceItemsCommand> {
	@Inject
	private ControlOfAttendanceItemsRepository controlOfAttendanceItemsRepository;


	private ControlOfAttendanceItems toControlOfAttendanceItemsDomain(
			ControlOfAttendanceItemsCommand controlOfAttendanceItemsCommand) {
		return ControlOfAttendanceItems.createFromJavaType(  new BigDecimal(controlOfAttendanceItemsCommand.attandanceTimeId) ,
				controlOfAttendanceItemsCommand.inputUnitOfTimeItem,
				controlOfAttendanceItemsCommand.headerBackgroundColorOfDailyPerformance,controlOfAttendanceItemsCommand.nameLineFeedPosition);
	}
	
	
	

	@Override
	protected void handle(CommandHandlerContext<ControlOfAttendanceItemsCommand> context) {

		ControlOfAttendanceItemsCommand controlOfAttendanceItemsCommand = context.getCommand();
		this.controlOfAttendanceItemsRepository
				.updateControlOfAttendanceItem(this.toControlOfAttendanceItemsDomain(controlOfAttendanceItemsCommand));
	}

}
