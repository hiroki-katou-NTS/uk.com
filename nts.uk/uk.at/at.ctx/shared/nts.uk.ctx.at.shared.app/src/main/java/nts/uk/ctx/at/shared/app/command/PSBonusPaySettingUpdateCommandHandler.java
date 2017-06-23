package nts.uk.ctx.at.shared.app.command;

import java.util.List;
import java.util.stream.Collectors;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.arc.layer.app.command.CommandHandler;
import nts.arc.layer.app.command.CommandHandlerContext;
import nts.uk.ctx.at.shared.dom.bonuspay.repository.PSBonusPaySettingRepository;
import nts.uk.ctx.at.shared.dom.bonuspay.setting.PersonalBonusPaySetting;

@Stateless
public class PSBonusPaySettingUpdateCommandHandler extends CommandHandler<List<PSBonusPaySettingUpdateCommand>> {
	@Inject
	private PSBonusPaySettingRepository psBonusPaySettingRepository;

	@Override
	protected void handle(CommandHandlerContext<List<PSBonusPaySettingUpdateCommand>> context) {
		List<PSBonusPaySettingUpdateCommand> lstPSBonusPaySettingUpdateCommand = context.getCommand();
		this.psBonusPaySettingRepository.updateListSetting(lstPSBonusPaySettingUpdateCommand.stream()
				.map(c -> toPersonalBonusPaySettingDomain(c)).collect(Collectors.toList()));
	}

	private PersonalBonusPaySetting toPersonalBonusPaySettingDomain(
			PSBonusPaySettingUpdateCommand psBonusPaySettingUpdateCommand) {
		return PersonalBonusPaySetting.createFromJavaType(psBonusPaySettingUpdateCommand.getEmployeeId().toString(),
				psBonusPaySettingUpdateCommand.getBonusPaySettingCode().toString());
	}

}
