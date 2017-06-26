package nts.uk.ctx.at.shared.app.command;

import java.util.List;
import java.util.stream.Collectors;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.arc.layer.app.command.CommandHandler;
import nts.arc.layer.app.command.CommandHandlerContext;
import nts.uk.ctx.at.shared.dom.bonuspay.repository.BPTimesheetRepository;
import nts.uk.ctx.at.shared.dom.bonuspay.setting.BonusPayTimesheet;

@Stateless
public class BPTimesheetAddCommandHandler extends CommandHandler<List<BPTimesheetAddCommand>> {
	@Inject
	private BPTimesheetRepository bpTimesheetRepository;

	@Override
	protected void handle(CommandHandlerContext<List<BPTimesheetAddCommand>> context) {
		List<BPTimesheetAddCommand> lstBPTimesheetAddCommand = context.getCommand();
		bpTimesheetRepository.addListTimesheet(lstBPTimesheetAddCommand.get(0).companyId,
				lstBPTimesheetAddCommand.get(0).bonusPaySettingCode,
				lstBPTimesheetAddCommand.stream().map(c -> toBonusPayTimesheetDomain(c)).collect(Collectors.toList()));
	}

	private BonusPayTimesheet toBonusPayTimesheetDomain(BPTimesheetAddCommand bpTimesheetAddCommand) {
		return BonusPayTimesheet.createFromJavaType(bpTimesheetAddCommand.timeSheetNO, bpTimesheetAddCommand.useAtr,
				bpTimesheetAddCommand.timeItemId, Long.valueOf(bpTimesheetAddCommand.startTime),
				Long.valueOf(bpTimesheetAddCommand.endTime), bpTimesheetAddCommand.roundingTimeAtr,
				bpTimesheetAddCommand.roundingAtr);
	}

}
