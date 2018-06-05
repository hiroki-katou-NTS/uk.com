package nts.uk.ctx.pereg.app.command.person.setting.selectionitem.history.remove;

import java.util.List;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.transaction.Transactional;

import nts.arc.layer.app.command.CommandHandler;
import nts.arc.layer.app.command.CommandHandlerContext;
import nts.uk.ctx.pereg.dom.person.setting.selectionitem.history.SelectionHistory;
import nts.uk.ctx.pereg.dom.person.setting.selectionitem.history.SelectionHistoryRepository;
import nts.uk.ctx.pereg.dom.person.setting.selectionitem.selection.Selection;
import nts.uk.ctx.pereg.dom.person.setting.selectionitem.selection.SelectionRepository;
import nts.uk.ctx.pereg.dom.person.setting.selectionitem.selectionorder.SelectionItemOrder;
import nts.uk.ctx.pereg.dom.person.setting.selectionitem.selectionorder.SelectionItemOrderRepository;
import nts.uk.shr.com.context.AppContexts;
import nts.uk.shr.com.history.DateHistoryItem;

/**
 * 
 * @author tuannv
 *
 */
@Stateless
@Transactional
public class RemoveHistoryCommandHandler extends CommandHandler<RemoveHistoryCommand> {

	@Inject
	private SelectionRepository selectionRepo;

	@Inject
	private SelectionItemOrderRepository selectionOrderRepo;
	
	@Inject
	private SelectionHistoryRepository selectionHistoryRepo;

	@Override
	protected void handle(CommandHandlerContext<RemoveHistoryCommand> context) {
		RemoveHistoryCommand command = context.getCommand();
		String histId = command.getHistId();
		
		SelectionHistory selectionHistory = selectionHistoryRepo
				.get(command.getSelectionItemId(), AppContexts.user().companyId()).get();
		
		DateHistoryItem removeDateHistItem = selectionHistory.getDateHistoryItems().stream()
				.filter(x -> x.identifier().equals(histId)).findFirst().get();
		// remove on domain
		selectionHistory.remove(removeDateHistItem);
		
		// remove on database
		selectionHistoryRepo.delete(selectionHistory, removeDateHistItem);
		
		// Xoa Selection:
		List<Selection> selectionList = this.selectionRepo.getAllSelectByHistId(histId);
		selectionList.stream().forEach(x -> this.selectionRepo.remove(x.getSelectionID()));

		// Xoa domain: Selection Order
		List<SelectionItemOrder> orderList = this.selectionOrderRepo.getAllOrderSelectionByHistId(histId);
		orderList.stream().forEach(x -> this.selectionOrderRepo.remove(x.getSelectionID()));

	}

}
