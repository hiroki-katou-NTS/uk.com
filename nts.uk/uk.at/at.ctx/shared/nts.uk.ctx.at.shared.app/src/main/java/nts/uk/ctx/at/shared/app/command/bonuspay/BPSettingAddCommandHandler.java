package nts.uk.ctx.at.shared.app.command.bonuspay;

import java.util.List;
import java.util.stream.Collectors;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.arc.error.BusinessException;
import nts.arc.layer.app.command.CommandHandler;
import nts.arc.layer.app.command.CommandHandlerContext;
import nts.uk.ctx.at.shared.dom.bonuspay.primitives.BonusPaySettingCode;
import nts.uk.ctx.at.shared.dom.bonuspay.services.BonusPaySettingService;
import nts.uk.ctx.at.shared.dom.bonuspay.setting.BonusPaySetting;
import nts.uk.ctx.at.shared.dom.bonuspay.setting.BonusPayTimesheet;
import nts.uk.ctx.at.shared.dom.bonuspay.setting.SpecBonusPayTimesheet;
import nts.uk.shr.com.context.AppContexts;

@Stateless
public class BPSettingAddCommandHandler extends CommandHandler<BPSettingAddCommand> {
	@Inject
	private BonusPaySettingService bonusPaySettingService;

	@Override
	protected void handle(CommandHandlerContext<BPSettingAddCommand> context) {
		String companyId = AppContexts.user().companyId();
		BPSettingAddCommand bpSettingAddCommand = context.getCommand();
		if(bonusPaySettingService.isExisted(companyId, new BonusPaySettingCode(bpSettingAddCommand.getCode()))) {
			throw new BusinessException("Msg_3");
		}
		bonusPaySettingService.addBonusPaySetting(this.ToBonusPaySettingDomain(bpSettingAddCommand,companyId));
	}

	private BonusPaySetting ToBonusPaySettingDomain(BPSettingAddCommand bpSettingAddCommand,String companyId) {
		List<BPTimesheetAddCommand> lstBPTimesheetAddCommand = bpSettingAddCommand.getLstBonusPayTimesheet();
		List<BonusPayTimesheet> lstBonusPayTimesheet = lstBPTimesheetAddCommand.stream()
				.map(c -> toBonusPayTimesheetDomain(c)).collect(Collectors.toList());
		List<SpecBPTimesheetAddCommand> lstSpecBPTimesheetAddCommand = bpSettingAddCommand
				.getLstSpecBonusPayTimesheet();
		List<SpecBonusPayTimesheet> lstSpecBonusPayTimesheet = lstSpecBPTimesheetAddCommand.stream()
				.map(c -> toSpecBonusPayTimesheetDomain(c)).collect(Collectors.toList());
		return BonusPaySetting.createFromJavaType(companyId, bpSettingAddCommand.getCode(),
				bpSettingAddCommand.getName(), lstBonusPayTimesheet, lstSpecBonusPayTimesheet);
		// bonusPaySetting.setListTimesheet(lstBonusPayTimesheet);
		// bonusPaySetting.setListSpecialTimesheet(lstSpecBonusPayTimesheet);

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
