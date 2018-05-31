package nts.uk.ctx.pereg.app.command.person.setting.selectionitem;

import java.util.Optional;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.arc.error.BusinessException;
import nts.arc.error.RawErrorMessage;
import nts.arc.layer.app.command.CommandHandler;
import nts.arc.layer.app.command.CommandHandlerContext;
import nts.uk.ctx.pereg.dom.person.setting.selectionitem.selectionitem.IPerInfoSelectionItemRepository;
import nts.uk.ctx.pereg.dom.person.setting.selectionitem.selectionitem.PerInfoSelectionItem;
import nts.uk.shr.com.context.AppContexts;

@Stateless
public class UpdateSelectionItemCommandHandler extends CommandHandler<UpdateSelectionItemCommand> {
	@Inject
	private IPerInfoSelectionItemRepository perInfoSelectionItemRepo;

	@Override
	protected void handle(CommandHandlerContext<UpdateSelectionItemCommand> context) {
		UpdateSelectionItemCommand command = context.getCommand();
	
		validateSelectionItemName(command.getSelectionItemName(), command.getSelectionItemId());
		
		Optional<PerInfoSelectionItem> updateObject = this.perInfoSelectionItemRepo
				.getSelectionItemBySelectionItemId(command.getSelectionItemId());
		
		if (!updateObject.isPresent()) {
			throw new RuntimeException("The selection item is not exist!");
		}

		// Update
		updateObject.get().updateDomain(command.getSelectionItemName(), command.getIntegrationCode(), command.getMemo());

		this.perInfoSelectionItemRepo.update(updateObject.get());
	}
	
	private void validateSelectionItemName(String name, String selectionItemId) {
		Optional<PerInfoSelectionItem> optCheckExistByName = this.perInfoSelectionItemRepo
				.getSelectionItemByName(AppContexts.user().contractCode(), name, selectionItemId);

		if (optCheckExistByName.isPresent()) {
			throw new BusinessException(new RawErrorMessage("Msg_513"));
		}
	}

}
