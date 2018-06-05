package nts.uk.ctx.pereg.dom.person.setting.selectionitem.domainservice;

import java.util.List;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.uk.ctx.pereg.dom.person.setting.selectionitem.history.SelectionHistory;
import nts.uk.ctx.pereg.dom.person.setting.selectionitem.history.SelectionHistoryRepository;
import nts.uk.ctx.pereg.dom.person.setting.selectionitem.selection.Selection;
import nts.uk.ctx.pereg.dom.person.setting.selectionitem.selection.SelectionRepository;
import nts.uk.ctx.pereg.dom.person.setting.selectionitem.selectionorder.SelectionItemOrder;
import nts.uk.ctx.pereg.dom.person.setting.selectionitem.selectionorder.SelectionItemOrderRepository;
import nts.uk.shr.com.history.DateHistoryItem;

@Stateless
public class SelectionHistoryService {

	@Inject
	private SelectionRepository selectionRepo;

	@Inject
	private SelectionItemOrderRepository selectionOrderRepo;

	@Inject
	private SelectionHistoryRepository selectionHistoryRepo;

	public void removeHistoryOfCompany(String selectionItemId, String companyId) {

		// History:
		SelectionHistory selectionHistory = this.selectionHistoryRepo.get(selectionItemId, companyId).get();

		selectionHistory.getDateHistoryItems().stream().forEach(x -> {
			deleteSelectionHistory(selectionItemId, companyId, x.identifier());

		});

	}

	public void deleteSelectionHistory(String selectionItemId, String companyId, String historyId) {

		SelectionHistory selectionHistory = selectionHistoryRepo.get(selectionItemId, companyId).get();

		DateHistoryItem removeDateHistItem = selectionHistory.getDateHistoryItems().stream()
				.filter(x -> x.identifier().equals(historyId)).findFirst().get();
		// remove on domain
		selectionHistory.remove(removeDateHistItem);

		// remove on database
		selectionHistoryRepo.delete(selectionHistory, removeDateHistItem);

		// Xoa Selection:
		List<Selection> selectionList = this.selectionRepo.getAllSelectByHistId(historyId);
		selectionList.stream().forEach(x -> this.selectionRepo.remove(x.getSelectionID()));

		// Xoa domain: Selection Order
		List<SelectionItemOrder> orderList = this.selectionOrderRepo.getAllOrderSelectionByHistId(historyId);
		orderList.stream().forEach(x -> this.selectionOrderRepo.remove(x.getSelectionID()));

	}

}
