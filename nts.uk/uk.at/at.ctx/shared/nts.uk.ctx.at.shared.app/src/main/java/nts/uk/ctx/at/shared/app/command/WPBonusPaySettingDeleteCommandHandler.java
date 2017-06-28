package nts.uk.ctx.at.shared.app.command;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.arc.layer.app.command.CommandHandler;
import nts.arc.layer.app.command.CommandHandlerContext;
import nts.uk.ctx.at.shared.dom.bonuspay.repository.WPBonusPaySettingRepository;
import nts.uk.ctx.at.shared.dom.bonuspay.setting.WorkplaceBonusPaySetting;

@Stateless
public class WPBonusPaySettingDeleteCommandHandler extends CommandHandler<WPBonusPaySettingDeleteCommand> {
	@Inject
	private WPBonusPaySettingRepository wpBonusPaySettingRepository;

	@Override
	protected void handle(CommandHandlerContext<WPBonusPaySettingDeleteCommand> context) {
		WPBonusPaySettingDeleteCommand wpBonusPaySettingDeleteCommand = context.getCommand();
		this.wpBonusPaySettingRepository
				.removeWPBPSetting(this.toWPBonusPaySettingDomain(wpBonusPaySettingDeleteCommand));
	}

	private WorkplaceBonusPaySetting toWPBonusPaySettingDomain(
			WPBonusPaySettingDeleteCommand wpBonusPaySettingDeleteCommand) {
		return WorkplaceBonusPaySetting.createFromJavaType(wpBonusPaySettingDeleteCommand.getWorkplaceId().toString(),
				wpBonusPaySettingDeleteCommand.getBonusPaySettingCode().toString());

	}

}
