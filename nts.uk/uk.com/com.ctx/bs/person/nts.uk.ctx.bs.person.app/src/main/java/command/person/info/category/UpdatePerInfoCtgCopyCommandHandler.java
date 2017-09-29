package command.person.info.category;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.arc.layer.app.command.CommandHandler;
import nts.arc.layer.app.command.CommandHandlerContext;
import nts.uk.ctx.bs.person.dom.person.info.category.PerInfoCategoryRepositoty;
import nts.uk.shr.com.context.AppContexts;

@Stateless
public class UpdatePerInfoCtgCopyCommandHandler extends CommandHandler<UpdatePerInfoCtgCopyCommand>{

	@Inject
	private PerInfoCategoryRepositoty perInfoCategoryRepositoty;
	
	@Override
	protected void handle(CommandHandlerContext<UpdatePerInfoCtgCopyCommand> context) {
		String companyId = AppContexts.user().companyId();
		perInfoCategoryRepositoty.updatePerInfoCtgInCopySetting(context.getCommand().getPerInfoCtgId(), companyId);
	}

}
