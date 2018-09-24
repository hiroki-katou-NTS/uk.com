package nts.uk.ctx.pereg.app.command.person.setting.selectionitem.selectionitem.remove;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.transaction.Transactional;

import nts.arc.error.BusinessException;
import nts.arc.layer.app.command.CommandHandler;
import nts.arc.layer.app.command.CommandHandlerContext;
import nts.uk.ctx.pereg.app.find.person.info.item.PerInfoItemDefFinder;
import nts.uk.ctx.pereg.dom.person.setting.selectionitem.history.SelectionHistoryRepository;
import nts.uk.ctx.pereg.dom.person.setting.selectionitem.selection.SelectionRepository;
import nts.uk.ctx.pereg.dom.person.setting.selectionitem.selectionitem.IPerInfoSelectionItemRepository;
import nts.uk.ctx.pereg.dom.person.setting.selectionitem.selectionorder.SelectionItemOrderRepository;

@Stateless
@Transactional
public class RemoveSelectionItemCommandHandler extends CommandHandler<RemoveSelectionItemCommand> {
	@Inject
	private IPerInfoSelectionItemRepository perInfoSelectionItemRepo;

	@Inject
	private SelectionHistoryRepository historySelectionRepository;

	@Inject
	private SelectionRepository selectionRepo;

	@Inject
	private SelectionItemOrderRepository selectionOrderRepo;
	
	@Inject
	private PerInfoItemDefFinder itemDefinitionFinder;

	@Override
	protected void handle(CommandHandlerContext<RemoveSelectionItemCommand> context) {
		RemoveSelectionItemCommand command = context.getCommand();
		String selectionItemId = command.getSelectionItemId();

		// check
		//checkSelectionItemId(selectionItemId);

		// remove selections
		selectionRepo.removeInSelectionItemId(selectionItemId);

		// remove selection orders
		selectionOrderRepo.removeInSelectionItemId(selectionItemId);
		
		// remove histories
		historySelectionRepository.removeAllOfSelectionItem(selectionItemId);
		
		// remove selection item
		this.perInfoSelectionItemRepo.remove(selectionItemId);
	}
	
	public boolean checkSelectionItemId(String id) {
		 return itemDefinitionFinder.checkExistedSelectionItemId(id);
	}
}
