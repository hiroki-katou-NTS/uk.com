package nts.uk.ctx.at.shared.app.command;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.arc.layer.app.command.CommandHandler;
import nts.arc.layer.app.command.CommandHandlerContext;
import nts.uk.ctx.at.shared.dom.bonuspay.repository.CPBonusPaySettingRepository;
import nts.uk.ctx.at.shared.dom.bonuspay.setting.CompanyBonusPaySetting;

@Stateless
public class CBPSettingUpdateCommandHandler extends CommandHandler<CBPSettingUpdateCommand> {
	@Inject
	private CPBonusPaySettingRepository cpBonusPaySettingRepository;

	@Override
	protected void handle(CommandHandlerContext<CBPSettingUpdateCommand> context) {
		CBPSettingUpdateCommand cBPSettingUpdateCommand = context.getCommand();
		this.cpBonusPaySettingRepository.updateSetting(this.toCompanyBonusPaySettingDomain(cBPSettingUpdateCommand));
	}

	private CompanyBonusPaySetting toCompanyBonusPaySettingDomain(CBPSettingUpdateCommand cBPSettingUpdateCommand) {
		return CompanyBonusPaySetting.createFromJavaType(cBPSettingUpdateCommand.companyId,
				cBPSettingUpdateCommand.bonusPaySettingCode);
	}

}
