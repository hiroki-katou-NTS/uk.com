package nts.uk.ctx.at.shared.app.command;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.arc.layer.app.command.CommandHandler;
import nts.arc.layer.app.command.CommandHandlerContext;
import nts.uk.ctx.at.shared.dom.bonuspay.repository.WTBonusPaySettingRepository;
import nts.uk.ctx.at.shared.dom.bonuspay.setting.WorkingTimesheetBonusPaySetting;

@Stateless
public class WTBonusPaySettingDeleteCommandHandler extends CommandHandler<WTBonusPaySettingDeleteCommand> {
	@Inject
	private WTBonusPaySettingRepository wtBonusPaySettingRepository;

	@Override
	protected void handle(CommandHandlerContext<WTBonusPaySettingDeleteCommand> context) {
		 WTBonusPaySettingDeleteCommand wtBonusPaySettingDeleteCommand = context.getCommand();
		this.wtBonusPaySettingRepository.removeWTBPSetting(this.toWorkingTimesheetBonusPaySettingDomain(wtBonusPaySettingDeleteCommand));
	}
	private WorkingTimesheetBonusPaySetting toWorkingTimesheetBonusPaySettingDomain(
			WTBonusPaySettingDeleteCommand wtBonusPaySettingDeleteCommand) {
		return WorkingTimesheetBonusPaySetting.createFromJavaType(
				wtBonusPaySettingDeleteCommand.getCompanyId().toString(),
				wtBonusPaySettingDeleteCommand.getWorkingTimesheetCode().toString(),
				wtBonusPaySettingDeleteCommand.getBonusPaySettingCode().toString());

	}

}
