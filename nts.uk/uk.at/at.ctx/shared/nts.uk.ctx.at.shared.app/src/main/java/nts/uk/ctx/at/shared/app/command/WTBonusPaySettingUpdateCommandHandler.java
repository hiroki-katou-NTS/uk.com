package nts.uk.ctx.at.shared.app.command;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.arc.layer.app.command.CommandHandler;
import nts.arc.layer.app.command.CommandHandlerContext;
import nts.uk.ctx.at.shared.dom.bonuspay.repository.WTBonusPaySettingRepository;
import nts.uk.ctx.at.shared.dom.bonuspay.setting.WorkingTimesheetBonusPaySetting;

@Stateless
public class WTBonusPaySettingUpdateCommandHandler extends CommandHandler<WTBonusPaySettingUpdateCommand> {
	@Inject
	private WTBonusPaySettingRepository wtBonusPaySettingRepository;

	@Override
	protected void handle(CommandHandlerContext<WTBonusPaySettingUpdateCommand> context) {
		WTBonusPaySettingUpdateCommand wtBonusPaySettingUpdateCommand = context.getCommand();
		this.wtBonusPaySettingRepository
				.updateWTBPSetting(this.toWorkingTimesheetBonusPaySettingDomain(wtBonusPaySettingUpdateCommand));
	}

	private WorkingTimesheetBonusPaySetting toWorkingTimesheetBonusPaySettingDomain(
			WTBonusPaySettingUpdateCommand wtBonusPaySettingUpdateCommand) {
		return WorkingTimesheetBonusPaySetting.createFromJavaType(
				wtBonusPaySettingUpdateCommand.getCompanyId().toString(),
				wtBonusPaySettingUpdateCommand.getWorkingTimesheetCode().toString(),
				wtBonusPaySettingUpdateCommand.getBonusPaySettingCode().toString());

	}
}
