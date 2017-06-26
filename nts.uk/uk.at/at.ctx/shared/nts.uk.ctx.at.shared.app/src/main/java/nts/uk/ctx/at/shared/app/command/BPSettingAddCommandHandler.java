package nts.uk.ctx.at.shared.app.command;

import java.util.List;
import java.util.stream.Collectors;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.arc.layer.app.command.CommandHandler;
import nts.arc.layer.app.command.CommandHandlerContext;
import nts.uk.ctx.at.shared.dom.bonuspay.services.BonusPaySettingService;
import nts.uk.ctx.at.shared.dom.bonuspay.setting.BonusPaySetting;
import nts.uk.ctx.at.shared.dom.bonuspay.setting.BonusPayTimesheet;
import nts.uk.ctx.at.shared.dom.bonuspay.setting.SpecBonusPayTimesheet;

@Stateless
public class BPSettingAddCommandHandler extends CommandHandler<BPSettingAddCommand> {
	@Inject
	private BonusPaySettingService bonusPaySettingService;

	@Override
	protected void handle(CommandHandlerContext<BPSettingAddCommand> context) {
		BPSettingAddCommand bpSettingAddCommand = context.getCommand();
		bonusPaySettingService.addBonusPaySetting(this.ToBonusPaySettingDomain(bpSettingAddCommand));
	}

	private BonusPaySetting ToBonusPaySettingDomain(BPSettingAddCommand bpSettingAddCommand) {

		BonusPaySetting bonusPaySetting = BonusPaySetting.createFromJavaType(bpSettingAddCommand.getCompanyId(),
				bpSettingAddCommand.getCode(), bpSettingAddCommand.getName());
		List<BPTimesheetAddCommand> lstBPTimesheetAddCommand = bpSettingAddCommand.getLstBonusPayTimesheet();
		List<BonusPayTimesheet> lstBonusPayTimesheet = lstBPTimesheetAddCommand.stream()
				.map(c -> toBonusPayTimesheetDomain(c)).collect(Collectors.toList());
		List<SpecBPTimesheetAddCommand> lstSpecBPTimesheetAddCommand = bpSettingAddCommand
				.getLstSpecBonusPayTimesheet();
		List<SpecBonusPayTimesheet> lstSpecBonusPayTimesheet = lstSpecBPTimesheetAddCommand.stream()
				.map(c -> toSpecBonusPayTimesheetDomain(c)).collect(Collectors.toList());
		bonusPaySetting.setListTimesheet(lstBonusPayTimesheet);
		bonusPaySetting.setListSpecialTimesheet(lstSpecBonusPayTimesheet);
		return bonusPaySetting;

	}

	private BonusPayTimesheet toBonusPayTimesheetDomain(BPTimesheetAddCommand bpTimesheetAddCommand) {
		return BonusPayTimesheet.createFromJavaType(bpTimesheetAddCommand.timeSheetNO, bpTimesheetAddCommand.useAtr,
				bpTimesheetAddCommand.timeItemId, Long.valueOf(bpTimesheetAddCommand.startTime),
				Long.valueOf(bpTimesheetAddCommand.endTime), bpTimesheetAddCommand.roundingTimeAtr,
				bpTimesheetAddCommand.roundingAtr);
	}

	private SpecBonusPayTimesheet toSpecBonusPayTimesheetDomain(SpecBPTimesheetAddCommand specBPTimesheetAddCommand) {
		return SpecBonusPayTimesheet.createFromJavaType(specBPTimesheetAddCommand.timeSheetNO,
				specBPTimesheetAddCommand.useAtr, specBPTimesheetAddCommand.timeItemId,
				Long.valueOf(specBPTimesheetAddCommand.startTime), Long.valueOf(specBPTimesheetAddCommand.endTime),
				specBPTimesheetAddCommand.roundingTimeAtr, specBPTimesheetAddCommand.roundingAtr,
				specBPTimesheetAddCommand.specialDateItemNO);

	}

}
