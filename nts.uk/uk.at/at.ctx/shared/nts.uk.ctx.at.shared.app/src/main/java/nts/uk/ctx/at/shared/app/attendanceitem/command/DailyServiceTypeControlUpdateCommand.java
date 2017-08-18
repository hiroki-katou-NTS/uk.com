package nts.uk.ctx.at.shared.app.attendanceitem.command;

import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.arc.layer.app.command.CommandHandler;
import nts.arc.layer.app.command.CommandHandlerContext;
import nts.uk.ctx.at.shared.dom.attendanceitem.DailyServiceTypeControl;
import nts.uk.ctx.at.shared.dom.attendanceitem.repository.DailyServiceTypeControlRepository;

@Stateless
public class DailyServiceTypeControlUpdateCommand extends CommandHandler<List<DailyServiceTypeControlCommand>> {
	@Inject
	private DailyServiceTypeControlRepository dailyServiceTypeControlRepository;

	@Override
	protected void handle(CommandHandlerContext<List<DailyServiceTypeControlCommand>> context) {
		List<DailyServiceTypeControlCommand> lstDailyServiceTypeControlCommand = context.getCommand();
		this.dailyServiceTypeControlRepository
				.updateListDailyServiceTypeControl(lstDailyServiceTypeControlCommand.stream()
						.map(c -> toDailyServiceTypeControlDomain(c)).collect(Collectors.toList()));
	}

	private DailyServiceTypeControl toDailyServiceTypeControlDomain(
			DailyServiceTypeControlCommand dailyServiceTypeControlCommand) {
		return DailyServiceTypeControl.createFromJavaType(
				new BigDecimal(dailyServiceTypeControlCommand.attendanceItemId),
				dailyServiceTypeControlCommand.businessTypeCode, 
				dailyServiceTypeControlCommand.youCanChangeIt,
				dailyServiceTypeControlCommand.canBeChangedByOthers, dailyServiceTypeControlCommand.use);
	}

}
