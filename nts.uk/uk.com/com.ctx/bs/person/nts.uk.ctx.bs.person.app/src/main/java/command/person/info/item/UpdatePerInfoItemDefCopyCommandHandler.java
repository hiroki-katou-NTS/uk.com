package command.person.info.item;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.arc.layer.app.command.CommandHandler;
import nts.arc.layer.app.command.CommandHandlerContext;
import nts.uk.ctx.bs.person.dom.person.info.item.PerInfoItemDefRepositoty;
import nts.uk.shr.com.context.AppContexts;

@Stateless
public class UpdatePerInfoItemDefCopyCommandHandler extends CommandHandler<UpdatePerInfoItemDefCopy>{
	
	@Inject
	private PerInfoItemDefRepositoty perInfoItemDefRepositoty;

	@Override
	protected void handle(CommandHandlerContext<UpdatePerInfoItemDefCopy> context) {
		UpdatePerInfoItemDefCopy command = context.getCommand();
		String companyId = AppContexts.user().companyId();	
		//delete object
		perInfoItemDefRepositoty.removePerInfoItemInCopySetting(command.getPerInfoCtgId(), companyId);
		
		// add objects
		perInfoItemDefRepositoty.updatePerInfoItemInCopySetting(command.getPerInfoCtgId(), command.getPerInfoItemDefIds());
	}

}
