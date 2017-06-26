package nts.uk.ctx.at.shared.app.command;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.arc.layer.app.command.CommandHandler;
import nts.arc.layer.app.command.CommandHandlerContext;
import nts.uk.ctx.at.shared.dom.bonuspay.repository.CPBonusPaySettingRepository;
import nts.uk.ctx.at.shared.dom.bonuspay.setting.CompanyBonusPaySetting;

@Stateless
public class CBPSettingAddCommandHandler extends CommandHandler<CBPSettingAddCommand> {
	@Inject
	private CPBonusPaySettingRepository cpBonusPaySettingRepository;

	@Override
	protected void handle(CommandHandlerContext<CBPSettingAddCommand> context) {
		CBPSettingAddCommand cBPSettingAddCommand = context.getCommand();
		this.cpBonusPaySettingRepository.addSetting(this.toCompanyBonusPaySettingDomain(cBPSettingAddCommand));
	}

	private CompanyBonusPaySetting toCompanyBonusPaySettingDomain(CBPSettingAddCommand cBPSettingAddCommand) {
		return CompanyBonusPaySetting.createFromJavaType(cBPSettingAddCommand.companyId,
				cBPSettingAddCommand.bonusPaySettingCode);
	}

}
