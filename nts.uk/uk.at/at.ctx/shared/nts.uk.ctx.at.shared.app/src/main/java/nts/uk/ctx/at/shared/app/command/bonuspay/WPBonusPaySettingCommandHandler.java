package nts.uk.ctx.at.shared.app.command.bonuspay;

import java.util.Optional;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.arc.layer.app.command.CommandHandler;
import nts.arc.layer.app.command.CommandHandlerContext;
import nts.uk.ctx.at.shared.dom.common.WorkplaceId;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.bonuspay.repository.WPBonusPaySettingRepository;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.bonuspay.setting.WorkplaceBonusPaySetting;
import nts.uk.shr.com.context.AppContexts;

@Stateless
public class WPBonusPaySettingCommandHandler extends CommandHandler<WPBonusPaySettingCommand> {
	@Inject
	private WPBonusPaySettingRepository repo;

	@Override
	protected void handle(CommandHandlerContext<WPBonusPaySettingCommand> context) {
		WPBonusPaySettingCommand command = context.getCommand();
		String companyId = AppContexts.user().companyId();
		switch (command.getAction()) {
		case 0:
			Optional<WorkplaceBonusPaySetting> domain = repo.getWPBPSetting(companyId, new WorkplaceId(command.getWorkplaceId()));
			if (domain.isPresent()) {
				repo.updateWPBPSetting(toDomain(command, companyId));
			} else {
				this.repo.addWPBPSetting(toDomain(command, companyId));
			}
			break;
		case 1:
			this.repo.removeWPBPSetting(toDomain(command, companyId));
			break;
		}
	}

	private WorkplaceBonusPaySetting toDomain(WPBonusPaySettingCommand command, String companyId) {
		return WorkplaceBonusPaySetting.createFromJavaType(companyId, command.getWorkplaceId(), command.getBonusPaySettingCode());
	}

}
