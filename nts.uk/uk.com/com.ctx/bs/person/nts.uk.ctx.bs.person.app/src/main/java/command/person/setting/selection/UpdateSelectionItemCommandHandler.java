package command.person.setting.selection;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.arc.layer.app.command.CommandHandler;
import nts.arc.layer.app.command.CommandHandlerContext;
import nts.uk.ctx.bs.person.dom.person.setting.selection.IPerInfoSelectionItemRepository;
import nts.uk.ctx.bs.person.dom.person.setting.selection.PerInfoSelectionItem;
import nts.uk.shr.com.context.AppContexts;

@Stateless
public class UpdateSelectionItemCommandHandler extends CommandHandler<UpdateSelectionItemCommand> {
	@Inject
	private IPerInfoSelectionItemRepository perInfoSelectionItemRepo;

	@Override
	protected void handle(CommandHandlerContext<UpdateSelectionItemCommand> context) {
		UpdateSelectionItemCommand command = context.getCommand();
		PerInfoSelectionItem domain = PerInfoSelectionItem.createFromJavaType(command.getSelectionItemId(),
				command.getSelectionItemName(), command.getMemo(),
				command.isSelectionItemClassification() == true ? 1 : 0, AppContexts.user().contractCode(),
				command.getIntegrationCode(), command.getFormatSelection().getSelectionCode(),
				command.getFormatSelection().isSelectionCodeCharacter() == true ? 1 : 0,
				command.getFormatSelection().getSelectionName(),
				command.getFormatSelection().getSelectionExternalCode());
		this.perInfoSelectionItemRepo.update(domain);
	}

}
