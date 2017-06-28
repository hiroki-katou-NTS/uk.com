package nts.uk.ctx.at.shared.app.command;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.arc.layer.app.command.CommandHandler;
import nts.arc.layer.app.command.CommandHandlerContext;
import nts.uk.ctx.at.shared.dom.bonuspay.repository.WTBonusPaySettingRepository;
import nts.uk.ctx.at.shared.dom.bonuspay.setting.WorkingTimesheetBonusPaySetting;

@Stateless
public class WTBonusPaySettingAddCommandHandler extends CommandHandler<WTBonusPaySettingAddCommand> {
	@Inject
	private WTBonusPaySettingRepository wtBonusPaySettingRepository;

	@Override
	protected void handle(CommandHandlerContext<WTBonusPaySettingAddCommand> context) {
	 WTBonusPaySettingAddCommand wtBonusPaySettingAddCommand = context.getCommand();
		this.wtBonusPaySettingRepository.addWTBPSetting(this.toWorkingTimesheetBonusPaySettingDomain(wtBonusPaySettingAddCommand));
	}

	private WorkingTimesheetBonusPaySetting toWorkingTimesheetBonusPaySettingDomain(
			WTBonusPaySettingAddCommand wtBonusPaySettingAddCommand) {
		return WorkingTimesheetBonusPaySetting.createFromJavaType(wtBonusPaySettingAddCommand.getCompanyId().toString(),
				wtBonusPaySettingAddCommand.getWorkingTimesheetCode().toString(),
				wtBonusPaySettingAddCommand.getBonusPaySettingCode().toString());

	}

}
