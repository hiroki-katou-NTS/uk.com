package nts.uk.ctx.at.shared.app.command.bonuspay;

import java.util.Optional;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.arc.layer.app.command.CommandHandler;
import nts.arc.layer.app.command.CommandHandlerContext;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.bonuspay.repository.PSBonusPaySettingRepository;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.bonuspay.setting.PersonalBonusPaySetting;

@Stateless
public class PSBonusPaySettingCommandHandler extends CommandHandler<PSBonusPaySettingCommand> {
	@Inject
	private PSBonusPaySettingRepository repo;

	@Override
	protected void handle(CommandHandlerContext<PSBonusPaySettingCommand> context) {
		PSBonusPaySettingCommand command = context.getCommand();
		switch (command.getAction()) {
		case 0:
			Optional<PersonalBonusPaySetting> update = repo.getPersonalBonusPaySetting(command.getEmployeeId());

			if (update.isPresent()) {
				repo.updatePBPSetting(toDomain(command));
			} else {
				repo.addPBPSetting(toDomain(command));
			}
			break;
		case 1:
			repo.removePBPSetting(toDomain(command));
			break;
		}
	}

	private PersonalBonusPaySetting toDomain(PSBonusPaySettingCommand command) {
		return PersonalBonusPaySetting.createFromJavaType(command.getEmployeeId(), command.getBonusPaySettingCode());
	}
}
