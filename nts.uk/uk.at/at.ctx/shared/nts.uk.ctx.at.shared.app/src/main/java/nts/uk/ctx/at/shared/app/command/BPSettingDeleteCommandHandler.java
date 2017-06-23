package nts.uk.ctx.at.shared.app.command;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.arc.layer.app.command.CommandHandler;
import nts.arc.layer.app.command.CommandHandlerContext;
import nts.uk.ctx.at.shared.dom.bonuspay.repository.BPSettingRepository;

@Stateless
public class BPSettingDeleteCommandHandler extends CommandHandler<BPSettingDeleteCommand> {
	@Inject
	private BPSettingRepository bpSettingRepository;

	@Override
	protected void handle(CommandHandlerContext<BPSettingDeleteCommand> context) {
		BPSettingDeleteCommand bpSettingDeleteCommand = context.getCommand();
		this.bpSettingRepository.removeBonusPaySetting(bpSettingDeleteCommand.companyId, bpSettingDeleteCommand.code);
	}

}
