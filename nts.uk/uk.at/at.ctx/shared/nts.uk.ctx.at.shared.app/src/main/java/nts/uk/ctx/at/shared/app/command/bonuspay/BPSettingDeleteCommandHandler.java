package nts.uk.ctx.at.shared.app.command.bonuspay;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.arc.layer.app.command.CommandHandler;
import nts.arc.layer.app.command.CommandHandlerContext;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.bonuspay.services.BonusPaySettingService;
import nts.uk.shr.com.context.AppContexts;

@Stateless
public class BPSettingDeleteCommandHandler extends CommandHandler<BPSettingDeleteCommand> {
	@Inject
	private BonusPaySettingService bonusPaySettingService;

	@Override
	protected void handle(CommandHandlerContext<BPSettingDeleteCommand> context) {
		String companyId = AppContexts.user().companyId();
		BPSettingDeleteCommand bpSettingDeleteCommand = context.getCommand();
		this.bonusPaySettingService.deleteBonusPaySetting(companyId,
				bpSettingDeleteCommand.code);

	}

}
