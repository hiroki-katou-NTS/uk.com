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
public class WPBonusPaySettingUpdateCommandHandler extends CommandHandler<List<WPBonusPaySettingUpdateCommand>> {
	@Inject
	private WPBonusPaySettingRepository wpBonusPaySettingRepository;

	@Override
	protected void handle(CommandHandlerContext<List<WPBonusPaySettingUpdateCommand>> context) {
		List<WPBonusPaySettingUpdateCommand> lstWPBonusPaySettingUpdateCommand = context.getCommand();
		this.wpBonusPaySettingRepository.addListSetting(lstWPBonusPaySettingUpdateCommand.stream()
				.map(c -> toWPBonusPaySettingDomain(c)).collect(Collectors.toList()));
	}

	private WorkplaceBonusPaySetting toWPBonusPaySettingDomain(
			WPBonusPaySettingUpdateCommand wpBonusPaySettingUpdateCommand) {
		return WorkplaceBonusPaySetting.createFromJavaType(wpBonusPaySettingUpdateCommand.getWorkplaceId().toString(),
				wpBonusPaySettingUpdateCommand.getBonusPaySettingCode().toString());

	}

}
