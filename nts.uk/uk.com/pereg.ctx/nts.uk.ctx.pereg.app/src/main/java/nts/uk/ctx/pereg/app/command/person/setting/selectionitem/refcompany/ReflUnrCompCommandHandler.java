package nts.uk.ctx.pereg.app.command.person.setting.selectionitem.refcompany;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.arc.layer.app.command.CommandHandlerContext;
import nts.arc.layer.app.command.CommandHandlerWithResult;
import nts.gul.text.IdentifierUtil;
import nts.uk.ctx.pereg.app.command.person.setting.selectionitem.domainservice.SelectionHistoryService;
import nts.uk.ctx.pereg.dom.company.ICompanyRepo;
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
public class ReflUnrCompCommandHandler extends CommandHandlerWithResult<ReflUnrCompCommand, String> {

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
	protected String handle(CommandHandlerContext<ReflUnrCompCommand> context) {
		ReflUnrCompCommand command = context.getCommand();
		String newHistId = IdentifierUtil.randomUniqueId();
		String selectionItemId = command.getSelectionItemId();

		String zeroCompanyId = AppContexts.user().zeroCompanyIdInContract();

		SelectionHistory zeroCompanyHistory = this.selectionHistoryRepo.get(selectionItemId, zeroCompanyId).get();

		List<String> historyIdOfZeroCompany = zeroCompanyHistory.getDateHistoryItems().stream().map(x -> x.identifier())
				.collect(Collectors.toList());

		Map<String, List<Selection>> histIdSelectionMap = selectionRepo.getByHistIdList(historyIdOfZeroCompany);
		Map<String, List<SelectionItemOrder>> histIdSelectionOrderMap = selectOrderRepo
				.getByHistIdList(historyIdOfZeroCompany);

		List<String> companyIdList = companyRepo.acquireAllCompany();

		companyIdList.forEach(companyId -> {
			// delete
			selectionHistService.removeHistoryOfCompany(selectionItemId, companyId);
			
			// insert
			insertHistoryList(zeroCompanyHistory, companyId, histIdSelectionMap, histIdSelectionOrderMap);

		});

		return newHistId;
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
		SelectionHistory selectionHistoryOfCompany = SelectionHistory
				.createFullHistorySelection(zeroCompanyHistory.getSelectionItemId(), companyId, dateHistoryItems);
		selectionHistoryRepo.add(selectionHistoryOfCompany);

		// selection
		List<Selection> selectionsOfCompany = createNewSelections(zeroCompanyHistory, histIdSelectionMap,
				histIdSelectionOrderMap, zeroHistIdCompanyHistIdMap);
		selectionRepo.addAll(selectionsOfCompany);

		// selection order
		List<SelectionItemOrder> selectionOrderOfCompany = createNewSelectionOrders(zeroCompanyHistory,
				histIdSelectionMap, histIdSelectionOrderMap, zeroHistIdCompanyHistIdMap);
		selectOrderRepo.addAll(selectionOrderOfCompany);
	}

	private List<Selection> createNewSelections(SelectionHistory zeroCompanyHistory,
			Map<String, List<Selection>> histIdSelectionMap,
			Map<String, List<SelectionItemOrder>> histIdSelectionOrderMap,
			Map<String, String> zeroHistIdCompanyHistIdMap) {
		
		List<Selection> selectionsOfCompany = new ArrayList<>();
		zeroCompanyHistory.getDateHistoryItems().forEach(histItem -> {
			String zeroHistId = histItem.identifier();
			String companyHistory = zeroHistIdCompanyHistIdMap.get(zeroHistId);
			List<Selection> zeroSelections = histIdSelectionMap.get(histItem.identifier());
			if (zeroSelections != null) {
				List<Selection> companySelections = zeroSelections.stream()
						.map(x -> x.cloneNewSelection(companyHistory)).collect(Collectors.toList());
				selectionsOfCompany.addAll(companySelections);
			}
		});

		return selectionsOfCompany;
	}
	
	private List<SelectionItemOrder> createNewSelectionOrders(SelectionHistory zeroCompanyHistory,
			Map<String, List<Selection>> histIdSelectionMap,
			Map<String, List<SelectionItemOrder>> histIdSelectionOrderMap,
			Map<String, String> zeroHistIdCompanyHistIdMap) {
		List<SelectionItemOrder> selectionOrdersOfCompany = new ArrayList<>();
		zeroCompanyHistory.getDateHistoryItems().forEach(histItem -> {
			String zeroHistId = histItem.identifier();
			String companyHistory = zeroHistIdCompanyHistIdMap.get(zeroHistId);

			List<SelectionItemOrder> zeroSelectionOrders = histIdSelectionOrderMap.get(zeroHistId);
			if (zeroSelectionOrders != null) {
				List<SelectionItemOrder> companySelectionOrders = zeroSelectionOrders.stream()
						.map(x -> x.cloneNewSelectionItemOrder(companyHistory)).collect(Collectors.toList());
				selectionOrdersOfCompany.addAll(companySelectionOrders);
			}

		});
		return selectionOrdersOfCompany;
	}

}
