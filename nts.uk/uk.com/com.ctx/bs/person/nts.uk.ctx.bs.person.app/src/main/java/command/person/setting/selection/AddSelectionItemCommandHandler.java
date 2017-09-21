package command.person.setting.selection;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.arc.layer.app.command.CommandHandlerContext;
import nts.arc.layer.app.command.CommandHandlerWithResult;
import nts.gul.text.IdentifierUtil;
import nts.uk.ctx.bs.person.dom.person.setting.selection.IPerInfoSelectionItemRepository;
import nts.uk.ctx.bs.person.dom.person.setting.selection.PerInfoSelectionItem;
import nts.uk.shr.com.context.AppContexts;

@Stateless
public class AddSelectionItemCommandHandler extends CommandHandlerWithResult<AddSelectionItemCommand,String> {

	@Inject
	private IPerInfoSelectionItemRepository perInfoSelectionItemRepo;

	@Override
	protected String handle(CommandHandlerContext<AddSelectionItemCommand> context) {
		AddSelectionItemCommand command = context.getCommand();
		String newId = IdentifierUtil.randomUniqueId();
		PerInfoSelectionItem domain = PerInfoSelectionItem.createFromJavaType(
				newId,
				command.getSelectionItemName(), 
				command.getMemo(), 
				command.getSelectionItemClassification(),
				AppContexts.user().contractCode(), 
				command.getIntegrationCode(),
				command.getFormatSelection().getSelectionCode(),
				command.getFormatSelection().getSelectionCodeCharacter(),
				command.getFormatSelection().getSelectionName(),
				command.getFormatSelection().getSelectionExternalCode());

		this.perInfoSelectionItemRepo.add(domain);
		
		return newId;

	}

}
