package nts.uk.ctx.at.shared.app.command;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.arc.layer.app.command.CommandHandler;
import nts.arc.layer.app.command.CommandHandlerContext;
import nts.uk.ctx.at.shared.dom.bonuspay.repository.WPBonusPaySettingRepository;
import nts.uk.ctx.at.shared.dom.bonuspay.setting.WorkplaceBonusPaySetting;

@Stateless
public class WPBonusPaySettingAddCommandHandler extends CommandHandler<WPBonusPaySettingAddCommand> {
	@Inject
	private WPBonusPaySettingRepository wpBonusPaySettingRepository;

	@Override
	protected void handle(CommandHandlerContext<WPBonusPaySettingAddCommand> context) {
		WPBonusPaySettingAddCommand wpBonusPaySettingAddCommand = context.getCommand();
		this.wpBonusPaySettingRepository.addWPBPSetting(this.toWPBonusPaySettingDomain(wpBonusPaySettingAddCommand));
	}

	private WorkplaceBonusPaySetting toWPBonusPaySettingDomain(
			WPBonusPaySettingAddCommand wpBonusPaySettingAddCommand) {
		return WorkplaceBonusPaySetting.createFromJavaType(wpBonusPaySettingAddCommand.getWorkplaceId().toString(),
				wpBonusPaySettingAddCommand.getBonusPaySettingCode().toString());

	}

}
