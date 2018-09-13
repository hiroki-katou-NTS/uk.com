package nts.uk.ctx.pereg.app.command.person.setting.selectionitem.history.add;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.arc.layer.app.command.CommandHandlerContext;
import nts.arc.layer.app.command.CommandHandlerWithResult;
import nts.arc.time.GeneralDate;
import nts.gul.text.IdentifierUtil;
import nts.uk.ctx.pereg.dom.person.setting.selectionitem.history.SelectionHistory;
import nts.uk.ctx.pereg.dom.person.setting.selectionitem.history.SelectionHistoryRepository;
import nts.uk.ctx.pereg.dom.person.setting.selectionitem.selection.Selection;
import nts.uk.ctx.pereg.dom.person.setting.selectionitem.selection.SelectionRepository;
import nts.uk.ctx.pereg.dom.person.setting.selectionitem.selectionorder.SelectionItemOrder;
import nts.uk.ctx.pereg.dom.person.setting.selectionitem.selectionorder.SelectionItemOrderRepository;
import nts.uk.shr.com.context.AppContexts;
import nts.uk.shr.com.history.DateHistoryItem;
import nts.uk.shr.com.time.calendar.period.DatePeriod;

@Stateless
public class AddSelectionHistoryCommandHandler extends CommandHandlerWithResult<AddSelectionHistoryCommand, String> {

	@Inject
	private SelectionHistoryRepository selectionHistoryRepo;

	@Inject
	private SelectionRepository selectionRepo;

	@Inject
	private SelectionItemOrderRepository selectionOrderRepo;

	@Override
	protected String handle(CommandHandlerContext<AddSelectionHistoryCommand> context) {
		AddSelectionHistoryCommand command = context.getCommand();
		String selectItemID = command.getSelectionItemId();
		GeneralDate startDate = command.getStartDate();

		String companyId =  AppContexts.user().companyId();

		Optional<SelectionHistory> domainHistOpt = this.selectionHistoryRepo.get(selectItemID, companyId);
		
		SelectionHistory domainHist;
		if (domainHistOpt.isPresent()) {
			domainHist = domainHistOpt.get();
		} else {
			domainHist = SelectionHistory.createNewHistorySelection(companyId, selectItemID);
		}

		// add new history
		String newHistoryId = IdentifierUtil.randomUniqueId();
		DatePeriod period = new DatePeriod(startDate, GeneralDate.max());
		DateHistoryItem dateHistoryItem = new DateHistoryItem(newHistoryId, period);
		
		domainHist.add(dateHistoryItem);
		
		this.selectionHistoryRepo.add(domainHist);

		// copy selection and selection-order
		copyHistory(command.getSelectingHistId(), newHistoryId);

		return newHistoryId;
	}

	private boolean isGroupManager() {
		String groupManageRoleId = AppContexts.user().roles().forGroupCompaniesAdmin();
		if (groupManageRoleId != null) {
			return true;
		}
		return false;
	}


	private void copyHistory(String sourceHistId, String newHistoryId) {
		List<Selection> sourceSelectionList = this.selectionRepo.getAllSelectByHistId(sourceHistId);
		Map<String, SelectionItemOrder> sourceSelectionOrderMap = this.selectionOrderRepo
				.getAllOrderSelectionByHistId(sourceHistId).stream()
				.collect(Collectors.toMap(order -> order.getSelectionID(), order -> order));

		for (Selection sourceSelection : sourceSelectionList) {
			String newSelectionId = IdentifierUtil.randomUniqueId();
			// copy selections
			Selection newSelection = Selection.createFromSelection(newSelectionId, newHistoryId,
					sourceSelection.getSelectionCD().v(), sourceSelection.getSelectionName().v(),
					sourceSelection.getExternalCD().v(), sourceSelection.getMemoSelection().v());
			this.selectionRepo.add(newSelection);

			// copy selection orders
			SelectionItemOrder sourceSelectionOrder = sourceSelectionOrderMap.get(sourceSelection.getSelectionID());
			SelectionItemOrder newSelectionOrder = SelectionItemOrder.selectionItemOrder(newSelectionId, newHistoryId,
					sourceSelectionOrder.getDisporder().v(), sourceSelectionOrder.getInitSelection().value);
			this.selectionOrderRepo.add(newSelectionOrder);
		}
	}

}
