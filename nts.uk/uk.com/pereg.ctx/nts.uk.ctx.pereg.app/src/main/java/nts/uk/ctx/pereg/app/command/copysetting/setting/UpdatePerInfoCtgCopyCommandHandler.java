package nts.uk.ctx.pereg.app.command.copysetting.setting;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.arc.layer.app.command.CommandHandler;
import nts.arc.layer.app.command.CommandHandlerContext;
import nts.uk.ctx.pereg.dom.copysetting.setting.EmpCopySettingRepository;
import nts.uk.shr.com.context.AppContexts;

@Stateless
public class UpdatePerInfoCtgCopyCommandHandler extends CommandHandler<UpdatePerInfoCtgCopyCommand> {

	@Inject
	private EmpCopySettingRepository empCopyRepo;

	@Override
	protected void handle(CommandHandlerContext<UpdatePerInfoCtgCopyCommand> context) {
		String companyId = AppContexts.user().companyId();
		this.empCopyRepo.updatePerInfoCtgInCopySetting(context.getCommand().getPerInfoCtgId(), companyId);
	}

}
