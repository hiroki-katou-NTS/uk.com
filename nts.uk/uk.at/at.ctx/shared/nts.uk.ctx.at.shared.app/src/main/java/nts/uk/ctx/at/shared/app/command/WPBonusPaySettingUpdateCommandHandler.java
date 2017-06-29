package nts.uk.ctx.at.shared.app.command;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.arc.layer.app.command.CommandHandler;
import nts.arc.layer.app.command.CommandHandlerContext;
import nts.uk.ctx.at.shared.dom.bonuspay.repository.WPBonusPaySettingRepository;
import nts.uk.ctx.at.shared.dom.bonuspay.setting.WorkplaceBonusPaySetting;

@Stateless
public class WPBonusPaySettingUpdateCommandHandler extends CommandHandler<WPBonusPaySettingUpdateCommand> {
	@Inject
	private WPBonusPaySettingRepository wpBonusPaySettingRepository;

	@Override
	protected void handle(CommandHandlerContext<WPBonusPaySettingUpdateCommand> context) {
		WPBonusPaySettingUpdateCommand wpBonusPaySettingUpdateCommand = context.getCommand();
		this.wpBonusPaySettingRepository.updateWPBPSetting(this.toWPBonusPaySettingDomain(wpBonusPaySettingUpdateCommand));
	}

	private WorkplaceBonusPaySetting toWPBonusPaySettingDomain(
			WPBonusPaySettingUpdateCommand wpBonusPaySettingUpdateCommand) {
		return WorkplaceBonusPaySetting.createFromJavaType(wpBonusPaySettingUpdateCommand.getWorkplaceId().toString(),
				wpBonusPaySettingUpdateCommand.getBonusPaySettingCode().toString());

	}

}
