package nts.uk.ctx.at.shared.app.command;

import java.util.List;
import java.util.stream.Collectors;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.arc.layer.app.command.CommandHandler;
import nts.arc.layer.app.command.CommandHandlerContext;
import nts.uk.ctx.at.shared.dom.bonuspay.repository.WPBonusPaySettingRepository;
import nts.uk.ctx.at.shared.dom.bonuspay.setting.WorkplaceBonusPaySetting;

@Stateless
public class WPBonusPaySettingAddCommandHandler extends CommandHandler<List<WPBonusPaySettingAddCommand>> {
	@Inject
	private WPBonusPaySettingRepository wpBonusPaySettingRepository;

	@Override
	protected void handle(CommandHandlerContext<List<WPBonusPaySettingAddCommand>> context) {
		List<WPBonusPaySettingAddCommand> lstWPBonusPaySettingAddCommand = context.getCommand();
		this.wpBonusPaySettingRepository.addListSetting(lstWPBonusPaySettingAddCommand.stream()
				.map(c -> toWPBonusPaySettingDomain(c)).collect(Collectors.toList()));
	}

	private WorkplaceBonusPaySetting toWPBonusPaySettingDomain(
			WPBonusPaySettingAddCommand wpBonusPaySettingAddCommand) {
		return WorkplaceBonusPaySetting.createFromJavaType(wpBonusPaySettingAddCommand.getWorkplaceId().toString(),
				wpBonusPaySettingAddCommand.getBonusPaySettingCode().toString());

	}

}
