package nts.uk.ctx.at.shared.app.command.bonuspay;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.arc.layer.app.command.CommandHandler;
import nts.arc.layer.app.command.CommandHandlerContext;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.bonuspay.repository.CPBonusPaySettingRepository;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.bonuspay.setting.CompanyBonusPaySetting;
import nts.uk.shr.com.context.AppContexts;

@Stateless
public class CBPSettingCommandHandler extends CommandHandler<CBPSettingCommand> {
	@Inject
	private CPBonusPaySettingRepository  repo;

	@Override
	protected void handle(CommandHandlerContext<CBPSettingCommand> context) {
		CBPSettingCommand command = context.getCommand();

		switch (command.getAction()) {
		case 0:
			this.repo.saveSetting(toDomain(command));
			break;
		case 1:
			this.repo.removeSetting(toDomain(command));
			break;
		}
	}

	private CompanyBonusPaySetting toDomain(CBPSettingCommand command) {
		String companyId = AppContexts.user().companyId();
		return CompanyBonusPaySetting.createFromJavaType(companyId, command.getBonusPaySettingCode());
	}
}
