package nts.uk.ctx.at.shared.app.command;

import java.util.Optional;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.arc.layer.app.command.CommandHandler;
import nts.arc.layer.app.command.CommandHandlerContext;
import nts.uk.ctx.at.shared.dom.bonuspay.repository.WTBonusPaySettingRepository;
import nts.uk.ctx.at.shared.dom.bonuspay.setting.WorkingTimesheetBonusPaySetting;
import nts.uk.shr.com.context.AppContexts;

@Stateless
public class WTBonusPaySettingCommandHandler extends CommandHandler<WTBonusPaySettingCommand> {
	@Inject
	private WTBonusPaySettingRepository repo;

	@Override
	protected void handle(CommandHandlerContext<WTBonusPaySettingCommand> context) {
		WTBonusPaySettingCommand command = context.getCommand();
		WorkingTimesheetBonusPaySetting domain = toDomain(command);
		switch (command.getAction()) {
		case 1:

			Optional<WorkingTimesheetBonusPaySetting> update = repo.getWTBPSetting(domain.getCompanyId().v(),
					domain.getWorkingTimesheetCode());

			if (update.isPresent()) {
				repo.updateWTBPSetting(domain);
			} else {
				repo.addWTBPSetting(domain);
			}
			break;
		case 0:
			this.repo.removeWTBPSetting(domain);
			break;
		}
	}

	private WorkingTimesheetBonusPaySetting toDomain(WTBonusPaySettingCommand command) {
		String companyId = AppContexts.user().companyId();
		return WorkingTimesheetBonusPaySetting.createFromJavaType(companyId, command.getWorkingTimesheetCode(),
				command.getBonusPaySettingCode());
	}

}