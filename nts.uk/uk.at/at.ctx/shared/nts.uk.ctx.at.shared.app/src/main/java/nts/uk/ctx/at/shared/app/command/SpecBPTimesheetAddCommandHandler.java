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
public class SpecBPTimesheetAddCommandHandler extends CommandHandler<List<SpecBPTimesheetAddCommand>> {
	@Inject
	private SpecBPTimesheetRepository specBPTimesheetRepository;

	@Override
	protected void handle(CommandHandlerContext<List<SpecBPTimesheetAddCommand>> context) {
	 List<SpecBPTimesheetAddCommand> lstSpecBPTimesheetAddCommand = context.getCommand();
	 specBPTimesheetRepository.addListTimesheet(lstSpecBPTimesheetAddCommand.get(0).companyId,
			new BonusPaySettingCode(lstSpecBPTimesheetAddCommand.get(0).bonusPaySettingCode),
				lstSpecBPTimesheetAddCommand.stream().map(c -> toSpecBonusPayTimesheetDomain(c)).collect(Collectors.toList()));
	}

	private SpecBonusPayTimesheet toSpecBonusPayTimesheetDomain(SpecBPTimesheetAddCommand specBPTimesheetAddCommand) {
		return SpecBonusPayTimesheet.createFromJavaType(specBPTimesheetAddCommand.timeSheetNO,
				specBPTimesheetAddCommand.useAtr, specBPTimesheetAddCommand.timeItemId,
				Long.valueOf(specBPTimesheetAddCommand.startTime), Long.valueOf(specBPTimesheetAddCommand.endTime),
				specBPTimesheetAddCommand.roundingTimeAtr, specBPTimesheetAddCommand.roundingAtr,
				specBPTimesheetAddCommand.specialDateItemNO);

	}

}
