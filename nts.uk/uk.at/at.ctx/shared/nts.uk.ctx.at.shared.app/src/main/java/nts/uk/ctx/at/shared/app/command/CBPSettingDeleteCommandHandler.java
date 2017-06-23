package nts.uk.ctx.at.shared.app.command;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.arc.layer.app.command.CommandHandler;
import nts.arc.layer.app.command.CommandHandlerContext;
import nts.uk.ctx.at.shared.dom.bonuspay.repository.CPBonusPaySettingRepository;
import nts.uk.ctx.at.shared.dom.bonuspay.setting.CompanyBonusPaySetting;

@Stateless
public class CBPSettingDeleteCommandHandler extends CommandHandler<CBPSettingDeleteCommand> {
	@Inject
	private CPBonusPaySettingRepository cpBonusPaySettingRepository;
	@Override
	protected void handle(CommandHandlerContext<CBPSettingDeleteCommand> context) {
		CBPSettingDeleteCommand cBPSettingDeleteCommand = context.getCommand();
		this.cpBonusPaySettingRepository.removeSetting(this.toCompanyBonusPaySettingDomain(cBPSettingDeleteCommand));
	}

	private CompanyBonusPaySetting toCompanyBonusPaySettingDomain(CBPSettingDeleteCommand cBPSettingDeleteCommand) {
		return CompanyBonusPaySetting.createFromJavaType(cBPSettingDeleteCommand.companyId,
				cBPSettingDeleteCommand.bonusPaySettingCode);
	}

}
