package nts.uk.ctx.at.shared.app.command;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.arc.layer.app.command.CommandHandler;
import nts.arc.layer.app.command.CommandHandlerContext;
import nts.uk.ctx.at.shared.dom.bonuspay.repository.PSBonusPaySettingRepository;
import nts.uk.ctx.at.shared.dom.bonuspay.setting.PersonalBonusPaySetting;

@Stateless
public class PSBonusPaySettingAddCommandHandler extends CommandHandler<PSBonusPaySettingAddCommand> {
	@Inject
	private PSBonusPaySettingRepository psBonusPaySettingRepository;

	@Override
	protected void handle(CommandHandlerContext<PSBonusPaySettingAddCommand> context) {
		PSBonusPaySettingAddCommand psBonusPaySettingAddCommand = context.getCommand();
		this.psBonusPaySettingRepository
				.addPBPSetting(this.toPersonalBonusPaySettingDomain(psBonusPaySettingAddCommand));

	}

	private PersonalBonusPaySetting toPersonalBonusPaySettingDomain(
			PSBonusPaySettingAddCommand psBonusPaySettingAddCommand) {
		return PersonalBonusPaySetting.createFromJavaType(psBonusPaySettingAddCommand.getEmployeeId().toString(),
				psBonusPaySettingAddCommand.getBonusPaySettingCode().toString());
	}
}
