package nts.uk.ctx.at.shared.app.command;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.arc.layer.app.command.CommandHandler;
import nts.arc.layer.app.command.CommandHandlerContext;
import nts.uk.ctx.at.shared.dom.bonuspay.repository.PSBonusPaySettingRepository;
import nts.uk.ctx.at.shared.dom.bonuspay.setting.PersonalBonusPaySetting;

@Stateless
public class PSBonusPaySettingDeleteCommandHandler extends CommandHandler<PSBonusPaySettingDeleteCommand> {
	@Inject
	private PSBonusPaySettingRepository psBonusPaySettingRepository;

	@Override
	protected void handle(CommandHandlerContext<PSBonusPaySettingDeleteCommand> context) {
		PSBonusPaySettingDeleteCommand psBonusPaySettingDeleteCommand = context.getCommand();
		this.psBonusPaySettingRepository
				.removePBPSetting(this.toPersonalBonusPaySettingDomain(psBonusPaySettingDeleteCommand));
	}

	private PersonalBonusPaySetting toPersonalBonusPaySettingDomain(
			PSBonusPaySettingDeleteCommand psBonusPaySettingDeleteCommand) {
		return PersonalBonusPaySetting.createFromJavaType(psBonusPaySettingDeleteCommand.getEmployeeId().toString(),
				psBonusPaySettingDeleteCommand.getBonusPaySettingCode().toString());
	}

}
