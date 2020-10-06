package nts.uk.ctx.at.shared.app.command.bonuspay;

import javax.ejb.Stateless;
import javax.inject.Inject;
import nts.arc.layer.app.command.CommandHandler;
import nts.arc.layer.app.command.CommandHandlerContext;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.bonuspay.services.BPUnitUseSettingService;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.bonuspay.setting.BPUnitUseSetting;
import nts.uk.shr.com.context.AppContexts;

@Stateless
public class BPUnitUseSettingUpdateCommandHandler extends CommandHandler<BPUnitUseSettingUpdateCommand> {
	@Inject
	private BPUnitUseSettingService bpUnitUseSettingDomainService;

	@Override
	protected void handle(CommandHandlerContext<BPUnitUseSettingUpdateCommand> context) {
		String companyID = AppContexts.user().companyId();
		BPUnitUseSettingUpdateCommand bpUnitUseSettingUpdateCommand = context.getCommand();
		bpUnitUseSettingDomainService.updateSetting(BPUnitUseSetting.createFromJavaType(companyID,
				bpUnitUseSettingUpdateCommand.getWorkplaceUseAtr(), bpUnitUseSettingUpdateCommand.getPersonalUseAtr(),
				bpUnitUseSettingUpdateCommand.getWorkingTimesheetUseAtr()));
	}
}
