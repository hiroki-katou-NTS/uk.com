package nts.uk.ctx.pereg.app.command.person.setting.selectionitem.refcompany;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.arc.layer.app.command.CommandHandler;
import nts.arc.layer.app.command.CommandHandlerContext;
import nts.gul.text.IdentifierUtil;
import nts.uk.ctx.pereg.dom.company.ICompanyRepo;
import nts.uk.ctx.pereg.dom.person.setting.selectionitem.domainservice.SelectionHistoryService;
import nts.uk.ctx.pereg.dom.person.setting.selectionitem.history.SelectionHistory;
import nts.uk.ctx.pereg.dom.person.setting.selectionitem.history.SelectionHistoryRepository;
import nts.uk.ctx.pereg.dom.person.setting.selectionitem.selection.Selection;
import nts.uk.ctx.pereg.dom.person.setting.selectionitem.selection.SelectionRepository;
import nts.uk.ctx.pereg.dom.person.setting.selectionitem.selectionorder.SelectionItemOrder;
import nts.uk.ctx.pereg.dom.person.setting.selectionitem.selectionorder.SelectionItemOrderRepository;
import nts.uk.shr.com.context.AppContexts;
import nts.uk.shr.com.history.DateHistoryItem;
import nts.uk.shr.com.time.calendar.period.DatePeriod;

/**
 * 
 * @author tuannv
 *
 */
@Stateless
public class ReflUnrCompCommandHandler extends CommandHandler<ReflUnrCompCommand> {

	@Inject
	private SelectionHistoryRepository selectionHistoryRepo;

	@Inject
	private SelectionRepository selectionRepo;

	@Inject
	private SelectionItemOrderRepository selectOrderRepo;

	@Inject
	private ICompanyRepo companyRepo;

	@Inject
	private SelectionHistoryService selectionHistService;

	@Override
	protected void handle(CommandHandlerContext<ReflUnrCompCommand> context) {
		ReflUnrCompCommand command = context.getCommand();
		String selectionItemId = command.getSelectionItemId();

		String loginCompanyId = AppContexts.user().companyId();

		SelectionHistory loginCompanyHistory = this.selectionHistoryRepo.get(selectionItemId, loginCompanyId).get();

		List<String> loginHistoryIds = loginCompanyHistory.getDateHistoryItems().stream().map(x -> x.identifier())
				.collect(Collectors.toList());

		Map<String, List<Selection>> histIdSelectionMap = selectionRepo.getByHistIdList(loginHistoryIds).stream()
				.collect(Collectors.groupingBy(Selection::getHistId));

		Map<String, List<SelectionItemOrder>> histIdSelectionOrderMap = selectOrderRepo.getByHistIdList(loginHistoryIds)
				.stream().collect(Collectors.groupingBy(SelectionItemOrder::getHistId));

		List<String> companyIdList = companyRepo.acquireAllCompany();

		// remove
		selectionHistService.removeHistoryOfCompanies(selectionItemId, companyIdList);
		
		companyIdList.forEach(companyId -> {
			// insert
			insertHistoryList(loginCompanyHistory, companyId, histIdSelectionMap, histIdSelectionOrderMap);
		});

	}

	private void insertHistoryList(SelectionHistory zeroCompanyHistory, String companyId,
			Map<String, List<Selection>> histIdSelectionMap,
			Map<String, List<SelectionItemOrder>> histIdSelectionOrderMap) {

		Map<String, String> zeroHistIdCompanyHistIdMap = new HashMap<>();
		List<DateHistoryItem> dateHistoryItems = new ArrayList<>();

		zeroCompanyHistory.getDateHistoryItems().forEach(histItem -> {
			String newHistId = IdentifierUtil.randomUniqueId();
			DatePeriod copyPeriod = new DatePeriod(histItem.start(), histItem.end());
			dateHistoryItems.add(new DateHistoryItem(newHistId, copyPeriod));
			zeroHistIdCompanyHistIdMap.put(histItem.identifier(), newHistId);
		});

		// history
		SelectionHistory selectionHistoryOfCompany = SelectionHistory.createFullHistorySelection(companyId,
				zeroCompanyHistory.getSelectionItemId(), dateHistoryItems);
		selectionHistoryRepo.addAllDomain(selectionHistoryOfCompany);

		// selection and selection-order
		SelectionAndOrder newSelectionAndOrder = createNewSelectionsAndSelectionOrders(zeroCompanyHistory,
				histIdSelectionMap, histIdSelectionOrderMap, zeroHistIdCompanyHistIdMap);

		selectionRepo.addAll(newSelectionAndOrder.getSelectionsOfCompany());
		selectOrderRepo.addAll(newSelectionAndOrder.getSelectionOrderOfCompany());

	}

	private SelectionAndOrder createNewSelectionsAndSelectionOrders(SelectionHistory zeroCompanyHistory,
			Map<String, List<Selection>> histIdSelectionMap,
			Map<String, List<SelectionItemOrder>> histIdSelectionOrderMap,
			Map<String, String> zeroHistIdCompanyHistIdMap) {

		List<Selection> selectionsOfCompany = new ArrayList<>();
		List<SelectionItemOrder> selectionOrderOfCompany = new ArrayList<>();

		zeroCompanyHistory.getDateHistoryItems().forEach(histItem -> {

			String zeroHistId = histItem.identifier();
			String companyHistory = zeroHistIdCompanyHistIdMap.get(zeroHistId);

			List<Selection> zeroSelections = histIdSelectionMap.get(zeroHistId);
			List<SelectionItemOrder> zeroSelectionOrders = histIdSelectionOrderMap.get(zeroHistId);

			// new selection and new selection-order of a history
			SelectionAndOrder selectionAndOrders = cloneNewToCompany(zeroHistId, companyHistory, zeroSelections,
					zeroSelectionOrders);

			selectionsOfCompany.addAll(selectionAndOrders.getSelectionsOfCompany());
			selectionOrderOfCompany.addAll(selectionAndOrders.getSelectionOrderOfCompany());
		});

		return new SelectionAndOrder(selectionsOfCompany, selectionOrderOfCompany);

	}

	private SelectionAndOrder cloneNewToCompany(String zeroHistId, String companyHistory,
			List<Selection> zeroSelections, List<SelectionItemOrder> zeroSelectionOrders) {

		if (zeroSelections == null || zeroSelectionOrders == null) {
			return new SelectionAndOrder();
		}

		List<Selection> companySelections = new ArrayList<>();
		List<SelectionItemOrder> companySelectionOrders = new ArrayList<>();

		Map<String, SelectionItemOrder> zeroSelectionOrdersMap = zeroSelectionOrders.stream()
				.collect(Collectors.toMap(x -> x.getSelectionID(), x -> x));

		zeroSelections.forEach(selection -> {
			SelectionItemOrder zeroSelectionOrder = zeroSelectionOrdersMap.get(selection.getSelectionID());

			Selection newSelection = selection.cloneNewSelection(companyHistory);
			SelectionItemOrder newSelectionOrder = zeroSelectionOrder
					.cloneNewSelectionItemOrder(newSelection.getSelectionID(), companyHistory);

			companySelections.add(newSelection);
			companySelectionOrders.add(newSelectionOrder);
		});

		return new SelectionAndOrder(companySelections, companySelectionOrders);
	}

}
