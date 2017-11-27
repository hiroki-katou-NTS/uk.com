package command.person.setting.selectionitem.selection;

import java.util.List;
import java.util.stream.Collectors;

import javax.ejb.Stateless;
import javax.inject.Inject;

import command.person.info.category.GetListCompanyOfContract;
import nts.arc.layer.app.command.CommandHandlerContext;
import nts.arc.layer.app.command.CommandHandlerWithResult;
import nts.arc.time.GeneralDate;
import nts.gul.text.IdentifierUtil;
import nts.uk.ctx.bs.person.dom.person.info.category.PersonInfoCategory;
import nts.uk.ctx.bs.person.dom.person.setting.selectionitem.PerInfoHistorySelection;
import nts.uk.ctx.bs.person.dom.person.setting.selectionitem.PerInfoHistorySelectionRepository;
import nts.uk.ctx.bs.person.dom.person.setting.selectionitem.selection.Selection;
import nts.uk.ctx.bs.person.dom.person.setting.selectionitem.selection.SelectionItemOrder;
import nts.uk.ctx.bs.person.dom.person.setting.selectionitem.selection.SelectionItemOrderRepository;
import nts.uk.ctx.bs.person.dom.person.setting.selectionitem.selection.SelectionRepository;
import nts.uk.shr.com.context.AppContexts;
import nts.uk.shr.com.time.calendar.period.DatePeriod;

/**
 * 
 * @author tuannv
 *
 */
@Stateless
public class ReflUnrCompCommandHandler extends CommandHandlerWithResult<ReflUnrCompCommand, String> {

	@Inject
	private PerInfoHistorySelectionRepository historySelectionRepository;

	@Inject
	private SelectionRepository selectionRepo;

	@Inject
	private SelectionItemOrderRepository selectionOrderRepo;

	@Override
	protected String handle(CommandHandlerContext<ReflUnrCompCommand> context) {
		ReflUnrCompCommand command = context.getCommand();
		String newHistId = IdentifierUtil.randomUniqueId();
		String selectionItemId = command.getSelectionItemId();
		List<String> companyIdList = GetListCompanyOfContract.LIST_COMPANY_OF_CONTRACT;

		// Delete data:
		for (String cid : companyIdList) {
			// History:
			List<PerInfoHistorySelection> historyList = this.historySelectionRepository
					.getAllHistoryBySelectionItemIdAndCompanyId(selectionItemId, cid);
			historyList.stream().forEach(x -> this.selectionRepo.remove(x.getSelectionItemId()));

			// Selection
			String selectionId = command.getSelectionId();
			List<Selection> selectionBySelectionId = this.selectionRepo.getAllSelectionBySelectionID(selectionId);
			selectionBySelectionId.stream().forEach(x -> this.selectionRepo.remove(x.getSelectionID()));

			// Order:
			List<SelectionItemOrder> orderBySelectionId = this.selectionOrderRepo.getAllOrderBySelectionId(selectionId);
			orderBySelectionId.stream().forEach(x -> this.selectionOrderRepo.remove(x.getSelectionID()));
		}

		String rootCompanyId = PersonInfoCategory.ROOT_COMPANY_ID;
		List<PerInfoHistorySelection> histList = this.historySelectionRepository
				.getAllHistoryByCompanyID(rootCompanyId);

		companyIdList.forEach(x -> {
			createHistoryList(histList, x);

		});

		return newHistId;
	}

	private void createHistoryList(List<PerInfoHistorySelection> histList, String comId) {
		histList.forEach(x -> {
			String oldHistId = x.getHistId();
			String newHistId = IdentifierUtil.randomUniqueId();

			// copy tat ca history cua cty: 000000000000-0000 vao cty khac:
			PerInfoHistorySelection domain = PerInfoHistorySelection.createHistorySelection(newHistId,
					x.getSelectionItemId(), comId, x.getPeriod());
			this.historySelectionRepository.add(domain);

			// get all data Selection theo histId cty: 000000000000-0000
			createSelectionList(oldHistId, newHistId);

			//create order by hist id
			//createOrderList(newSelectionID, oldHistId, newHistId);
		});

	}

	// copy data Selection cua Histroy trong cty: 000000000000-0000 vao Cty
	// khac:
	private void  createSelectionList(String oldHistId, String histId) {
		List<Selection> selectionList = this.selectionRepo.getAllSelectByHistId(oldHistId);
		List<SelectionItemOrder> orderList = this.selectionOrderRepo.getAllOrderSelectionByHistId(oldHistId);
		selectionList.forEach(x -> {
			String newSelectionID = IdentifierUtil.randomUniqueId();
			Selection domain = Selection.createFromSelection(newSelectionID, histId, x.getSelectionCD().v(),
					x.getSelectionName().v(), x.getExternalCD().v(), x.getMemoSelection().v());

			this.selectionRepo.add(domain);
			
			SelectionItemOrder orderOrg = orderList.stream()
					.filter(o -> o.getSelectionID().equals(x.getSelectionID())).collect(Collectors.toList())
					.get(0);
			SelectionItemOrder domainOrder = SelectionItemOrder.selectionItemOrder(newSelectionID, histId,
					orderOrg.getDisporder().v(), orderOrg.getInitSelection().value);

			this.selectionOrderRepo.add(domainOrder);
		});
	}

//	private void createOrderList(String newSelectionID,String oldHistId, String histId) {
//		List<SelectionItemOrder> orderList = this.selectionOrderRepo.getAllOrderSelectionByHistId(oldHistId);
//		orderList.forEach(x -> {
////			String newSelectionID = IdentifierUtil.randomUniqueId();
//			SelectionItemOrder domainOrder = SelectionItemOrder.selectionItemOrder(newSelectionID, histId,
//					x.getDisporder().v(), x.getInitSelection().value);
//
//			this.selectionOrderRepo.add(domainOrder);
//		});
//
//	}
}
