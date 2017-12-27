package nts.uk.ctx.at.shared.app.command.bonuspay;

import java.util.Optional;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.arc.layer.app.command.CommandHandler;
import nts.arc.layer.app.command.CommandHandlerContext;
import nts.uk.ctx.at.shared.dom.bonuspay.repository.WPBonusPaySettingRepository;
import nts.uk.ctx.at.shared.dom.bonuspay.setting.WorkplaceBonusPaySetting;
import nts.uk.ctx.at.shared.dom.common.WorkplaceId;

@Stateless
public class WPBonusPaySettingCommandHandler extends CommandHandler<WPBonusPaySettingCommand> {
	@Inject
	private WPBonusPaySettingRepository repo;

	@Override
	protected void handle(CommandHandlerContext<WPBonusPaySettingCommand> context) {
		WPBonusPaySettingCommand command = context.getCommand();
		switch (command.getAction()) {
		case 0:
			Optional<WorkplaceBonusPaySetting> domain = repo.getWPBPSetting(new WorkplaceId(command.getWorkplaceId()));
			if (domain.isPresent()) {
				repo.updateWPBPSetting(toDomain(command));
			} else {
				this.repo.addWPBPSetting(toDomain(command));
			}
			break;
		case 1:
			this.repo.removeWPBPSetting(toDomain(command));
			break;
		}
	}

	private WorkplaceBonusPaySetting toDomain(WPBonusPaySettingCommand command) {
		return WorkplaceBonusPaySetting.createFromJavaType(command.getWorkplaceId(), command.getBonusPaySettingCode());
	}

}
