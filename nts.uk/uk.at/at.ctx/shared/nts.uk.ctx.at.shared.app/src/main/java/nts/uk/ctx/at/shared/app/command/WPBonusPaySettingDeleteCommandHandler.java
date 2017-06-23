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
public class WPBonusPaySettingDeleteCommandHandler extends CommandHandler<List<WPBonusPaySettingDeleteCommand>> {
	@Inject
	private WPBonusPaySettingRepository wpBonusPaySettingRepository;

	@Override
	protected void handle(CommandHandlerContext<List<WPBonusPaySettingDeleteCommand>> context) {
		List<WPBonusPaySettingDeleteCommand> lstWPBonusPaySettingDeleteCommand = context.getCommand();
		this.wpBonusPaySettingRepository.removeListSetting(lstWPBonusPaySettingDeleteCommand.stream()
				.map(c -> toWPBonusPaySettingDomain(c)).collect(Collectors.toList()));
	}
	
	private WorkplaceBonusPaySetting toWPBonusPaySettingDomain(
			WPBonusPaySettingDeleteCommand wpBonusPaySettingDeleteCommand) {
		return WorkplaceBonusPaySetting.createFromJavaType(wpBonusPaySettingDeleteCommand.getWorkplaceId().toString(),
				wpBonusPaySettingDeleteCommand.getBonusPaySettingCode().toString());

	}

}
