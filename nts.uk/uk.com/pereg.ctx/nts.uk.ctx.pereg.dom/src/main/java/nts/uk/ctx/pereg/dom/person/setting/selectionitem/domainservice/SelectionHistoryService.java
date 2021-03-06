package nts.uk.ctx.pereg.dom.person.setting.selectionitem.domainservice;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

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

	public void removeHistoryOfCompanies(String selectionItemId, List<String> companyIds) {

		List<SelectionHistory> selectionHistories = this.selectionHistoryRepo.getList(selectionItemId, companyIds);

		List<String> historyIds = new ArrayList<>();
		selectionHistories.forEach(selectionHist -> {
			List<String> historyIdsOfCompany = selectionHist.getDateHistoryItems().stream().map(x -> x.identifier())
					.collect(Collectors.toList());
			historyIds.addAll(historyIdsOfCompany);

		});
		
		// remove SelecitonHistory
		selectionHistoryRepo.removeAllHistoryIds(historyIds);

		List<String> removeSelectionIdList = selectionRepo.getByHistIdList(historyIds).stream()
				.map(x -> x.getSelectionID()).collect(Collectors.toList());
		// remove Selection
		selectionRepo.removeAll(removeSelectionIdList);
		
		// remove SelectionOrder
		selectionOrderRepo.removeAll(removeSelectionIdList);

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
