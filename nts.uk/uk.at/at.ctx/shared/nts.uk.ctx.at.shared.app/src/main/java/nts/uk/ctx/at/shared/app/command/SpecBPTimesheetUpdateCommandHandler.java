package nts.uk.ctx.at.shared.app.command;

import java.util.List;
import java.util.stream.Collectors;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.arc.layer.app.command.CommandHandler;
import nts.arc.layer.app.command.CommandHandlerContext;
import nts.uk.ctx.at.shared.dom.bonuspay.primitives.BonusPaySettingCode;
import nts.uk.ctx.at.shared.dom.bonuspay.repository.SpecBPTimesheetRepository;
import nts.uk.ctx.at.shared.dom.bonuspay.setting.SpecBonusPayTimesheet;
@Stateless
public class SpecBPTimesheetUpdateCommandHandler  extends CommandHandler<List<SpecBPTimesheetUpdateCommand>> {
	@Inject
	private SpecBPTimesheetRepository specBPTimesheetRepository;
	@Override
	protected void handle(CommandHandlerContext<List<SpecBPTimesheetUpdateCommand>> context) {
	 List<SpecBPTimesheetUpdateCommand> lstSpecBPTimesheetUpdateCommand = context.getCommand();
		 specBPTimesheetRepository.updateListTimesheet(lstSpecBPTimesheetUpdateCommand.get(0).companyId,
				new BonusPaySettingCode(lstSpecBPTimesheetUpdateCommand.get(0).bonusPaySettingCode),
					lstSpecBPTimesheetUpdateCommand.stream().map(c -> toSpecBonusPayTimesheetDomain(c)).collect(Collectors.toList()));
	}

	private SpecBonusPayTimesheet toSpecBonusPayTimesheetDomain(
			SpecBPTimesheetUpdateCommand specBPTimesheetUpdateCommand) {
		return SpecBonusPayTimesheet.createFromJavaType(specBPTimesheetUpdateCommand.timeSheetNO,
				specBPTimesheetUpdateCommand.useAtr, specBPTimesheetUpdateCommand.timeItemId,
				Long.valueOf(specBPTimesheetUpdateCommand.startTime), Long.valueOf(specBPTimesheetUpdateCommand.endTime),
				specBPTimesheetUpdateCommand.roundingTimeAtr, specBPTimesheetUpdateCommand.roundingAtr,
				specBPTimesheetUpdateCommand.specialDateItemNO);

	}

}
