package nts.uk.ctx.pereg.app.command.person.setting.selectionitem.selection;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.arc.error.BusinessException;
import nts.arc.layer.app.command.CommandHandlerContext;
import nts.arc.layer.app.command.CommandHandlerWithResult;
import nts.arc.time.GeneralDate;
import nts.gul.text.IdentifierUtil;
import nts.uk.ctx.pereg.dom.person.setting.selectionitem.history.PerInfoHistorySelection;
import nts.uk.ctx.pereg.dom.person.setting.selectionitem.history.PerInfoHistorySelectionRepository;
import nts.uk.ctx.pereg.dom.person.setting.selectionitem.selection.Selection;
import nts.uk.ctx.pereg.dom.person.setting.selectionitem.selection.SelectionRepository;
import nts.uk.ctx.pereg.dom.person.setting.selectionitem.selectionorder.SelectionItemOrder;
import nts.uk.ctx.pereg.dom.person.setting.selectionitem.selectionorder.SelectionItemOrderRepository;
import nts.uk.shr.com.context.AppContexts;
import nts.uk.shr.com.time.calendar.period.DatePeriod;

@Stateless
public class AddSelectionHistoryCommandHandler extends CommandHandlerWithResult<AddSelectionHistoryCommand, String> {

	@Inject
	private PerInfoHistorySelectionRepository historySelectionRepository;

	@Inject
	private SelectionRepository selectionRepo;

	@Inject
	private SelectionItemOrderRepository selectionOrderRepo;

	@Override
	protected String handle(CommandHandlerContext<AddSelectionHistoryCommand> context) {
		AddSelectionHistoryCommand command = context.getCommand();
		String selectItemID = command.getSelectionItemId();
		GeneralDate startDate = command.getStartDate();

		String companyId = isGroupManager() ? AppContexts.user().zeroCompanyIdInContract()
				: AppContexts.user().companyId();

		List<PerInfoHistorySelection> historyList = this.historySelectionRepository
				.getAllBySelecItemIdAndCompanyId(selectItemID, companyId);

		// validate
		validateStartDate(historyList, startDate);

		// add new history
		String newHistoryId = IdentifierUtil.randomUniqueId();
		DatePeriod period = new DatePeriod(startDate, GeneralDate.max());
		PerInfoHistorySelection domainHist = PerInfoHistorySelection.createHistorySelection(newHistoryId, selectItemID,
				companyId, period);
		this.historySelectionRepository.add(domainHist);

		// update the previous history
		updateBeforeHistory(historyList, startDate);

		// copy selection and selection-order
		copyHistory(command.getSelectingHistId(), newHistoryId);

		return newHistoryId;
	}

	private void validateStartDate(List<PerInfoHistorySelection> lstHist, GeneralDate startDate) {
		if (!lstHist.isEmpty()) {
			PerInfoHistorySelection histLast = lstHist.get(0);
			if (startDate.beforeOrEquals(histLast.getPeriod().start())) {
				throw new BusinessException("Msg_102");
			}
		}
	}

	private boolean isGroupManager() {
		String groupManageRoleId = AppContexts.user().roles().forGroupCompaniesAdmin();
		if (groupManageRoleId != null) {
			return true;
		}
		return false;
	}

	private void updateBeforeHistory(List<PerInfoHistorySelection> historyList, GeneralDate startDate) {
		// if last history isPresent (not first time create)
		if (!historyList.isEmpty()) {
			PerInfoHistorySelection lastHist = historyList.get(0);
			// set end date lastHist = startDate of newHist -1
			DatePeriod lastHistPeriod = new DatePeriod(lastHist.getPeriod().start(), startDate.addDays(-1));
			lastHist.setPeriod(lastHistPeriod);

			this.historySelectionRepository.update(lastHist);

		}
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
