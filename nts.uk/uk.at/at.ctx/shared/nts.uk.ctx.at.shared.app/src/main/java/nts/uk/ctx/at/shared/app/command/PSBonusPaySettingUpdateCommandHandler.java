package nts.uk.ctx.at.shared.app.command;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.arc.layer.app.command.CommandHandler;
import nts.arc.layer.app.command.CommandHandlerContext;
import nts.uk.ctx.at.shared.dom.bonuspay.repository.PSBonusPaySettingRepository;
import nts.uk.ctx.at.shared.dom.bonuspay.setting.PersonalBonusPaySetting;

@Stateless
public class PSBonusPaySettingUpdateCommandHandler extends CommandHandler<PSBonusPaySettingUpdateCommand> {
	@Inject
	private PSBonusPaySettingRepository psBonusPaySettingRepository;

	@Override
	protected void handle(CommandHandlerContext<PSBonusPaySettingUpdateCommand> context) {
		PSBonusPaySettingUpdateCommand psBonusPaySettingUpdateCommand = context.getCommand();
		this.psBonusPaySettingRepository.updatePBPSetting(this.toPersonalBonusPaySettingDomain(psBonusPaySettingUpdateCommand));
	}

	private PersonalBonusPaySetting toPersonalBonusPaySettingDomain(
			PSBonusPaySettingUpdateCommand psBonusPaySettingUpdateCommand) {
		return PersonalBonusPaySetting.createFromJavaType(psBonusPaySettingUpdateCommand.getEmployeeId().toString(),
				psBonusPaySettingUpdateCommand.getBonusPaySettingCode().toString());
	}

}
