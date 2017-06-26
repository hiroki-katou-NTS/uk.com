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
public class PSBonusPaySettingAddCommandHandler extends CommandHandler<List<PSBonusPaySettingAddCommand>> {
	@Inject
	private PSBonusPaySettingRepository psBonusPaySettingRepository;

	@Override
	protected void handle(CommandHandlerContext<List<PSBonusPaySettingAddCommand>> context) {
		List<PSBonusPaySettingAddCommand> lstPSBonusPaySettingAddCommand = context.getCommand();
		this.psBonusPaySettingRepository.addListSetting(lstPSBonusPaySettingAddCommand.stream()
				.map(c -> toPersonalBonusPaySettingDomain(c)).collect(Collectors.toList()));

	}

	private PersonalBonusPaySetting toPersonalBonusPaySettingDomain(
			PSBonusPaySettingAddCommand psBonusPaySettingAddCommand) {
		return PersonalBonusPaySetting.createFromJavaType(psBonusPaySettingAddCommand.getEmployeeId().toString(),
				psBonusPaySettingAddCommand.getBonusPaySettingCode().toString());
	}
}
