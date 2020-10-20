package nts.uk.ctx.at.shared.app.command.bonuspay;

import java.util.List;
import java.util.stream.Collectors;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.arc.layer.app.command.CommandHandler;
import nts.arc.layer.app.command.CommandHandlerContext;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.bonuspay.primitives.BonusPaySettingCode;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.bonuspay.repository.BPTimesheetRepository;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.bonuspay.setting.BonusPayTimesheet;
import nts.uk.shr.com.context.AppContexts;

@Stateless
public class BPTimesheetDeleteCommandHandler extends CommandHandler<List<BPTimesheetDeleteCommand>>{
	@Inject
	private BPTimesheetRepository bpTimesheetRepository;

	@Override
	protected void handle(CommandHandlerContext<List<BPTimesheetDeleteCommand>> context) {
		String companyId = AppContexts.user().companyId();
		List<BPTimesheetDeleteCommand> lstBPTimesheetDeleteCommand = context.getCommand();
		bpTimesheetRepository.removeListTimesheet(companyId,
			new BonusPaySettingCode(lstBPTimesheetDeleteCommand.get(0).bonusPaySettingCode),
				lstBPTimesheetDeleteCommand.stream().map(c -> toBonusPayTimesheetDomain(c)).collect(Collectors.toList()));
	}
	
	private BonusPayTimesheet toBonusPayTimesheetDomain(BPTimesheetDeleteCommand bpTimesheetDeleteCommand) {
		return BonusPayTimesheet.createFromJavaType(bpTimesheetDeleteCommand.timeSheetNO, bpTimesheetDeleteCommand.useAtr,
				bpTimesheetDeleteCommand.timeItemId, bpTimesheetDeleteCommand.startTime,
				bpTimesheetDeleteCommand.endTime, bpTimesheetDeleteCommand.roundingTimeAtr,
				bpTimesheetDeleteCommand.roundingAtr);
	}
}
