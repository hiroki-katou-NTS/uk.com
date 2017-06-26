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
public class BPTimesheetUpdateCommandHandler  extends CommandHandler<List<BPTimesheetUpdateCommand>> {
	@Inject
	private BPTimesheetRepository bpTimesheetRepository;
	@Override
	protected void handle(CommandHandlerContext<List<BPTimesheetUpdateCommand>> context) {
		 List<BPTimesheetUpdateCommand> lstBPTimesheetUpdateCommand = context.getCommand();
		bpTimesheetRepository.updateListTimesheet(lstBPTimesheetUpdateCommand.get(0).companyId,
				lstBPTimesheetUpdateCommand.get(0).bonusPaySettingCode,
				lstBPTimesheetUpdateCommand.stream().map(c -> toBonusPayTimesheetDomain(c)).collect(Collectors.toList()));
	}
	
	private BonusPayTimesheet toBonusPayTimesheetDomain(BPTimesheetUpdateCommand bpTimesheetUpdateCommand) {
		return BonusPayTimesheet.createFromJavaType(bpTimesheetUpdateCommand.timeSheetNO, bpTimesheetUpdateCommand.useAtr,
				bpTimesheetUpdateCommand.timeItemId, Long.valueOf(bpTimesheetUpdateCommand.startTime),
				Long.valueOf(bpTimesheetUpdateCommand.endTime), bpTimesheetUpdateCommand.roundingTimeAtr,
				bpTimesheetUpdateCommand.roundingAtr);
	}

}
