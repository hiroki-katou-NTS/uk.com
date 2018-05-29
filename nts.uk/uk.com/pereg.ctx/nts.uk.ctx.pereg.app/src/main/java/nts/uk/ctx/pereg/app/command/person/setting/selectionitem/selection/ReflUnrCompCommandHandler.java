package nts.uk.ctx.pereg.app.command.person.setting.selectionitem.selection;

import java.util.List;
import java.util.stream.Collectors;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.arc.layer.app.command.CommandHandlerContext;
import nts.arc.layer.app.command.CommandHandlerWithResult;
import nts.gul.collection.CollectionUtil;
import nts.gul.text.IdentifierUtil;
import nts.uk.ctx.pereg.dom.company.ICompanyRepo;
import nts.uk.ctx.pereg.dom.person.setting.selectionitem.history.PerInfoHistorySelection;
import nts.uk.ctx.pereg.dom.person.setting.selectionitem.history.PerInfoHistorySelectionRepository;
import nts.uk.ctx.pereg.dom.person.setting.selectionitem.selection.Selection;
import nts.uk.ctx.pereg.dom.person.setting.selectionitem.selection.SelectionItemOrder;
import nts.uk.ctx.pereg.dom.person.setting.selectionitem.selection.SelectionItemOrderRepository;
import nts.uk.ctx.pereg.dom.person.setting.selectionitem.selection.SelectionRepository;
import nts.uk.shr.com.context.AppContexts;

/**
 * 
 * @author tuannv
 *
 */
@Stateless
public class ReflUnrCompCommandHandler extends CommandHandlerWithResult<ReflUnrCompCommand, String> {

	@Inject
	private PerInfoHistorySelectionRepository historyRepo;

	@Inject
	private SelectionRepository selectionRepo;

	@Inject
	private SelectionItemOrderRepository selectOrderRepo;

	@Inject
	private ICompanyRepo companyRepo;

	@Override
	protected String handle(CommandHandlerContext<ReflUnrCompCommand> context) {
		ReflUnrCompCommand command = context.getCommand();
		String newHistId = IdentifierUtil.randomUniqueId();
		String selectionItemId = command.getSelectionItemId();

		// 共通アルゴリズム「契約内ゼロ会社の会社IDを取得する」を実行する:Thực thi thuật toán
		// 「契約内ゼロ会社の会社IDを取得する」
		String zeroCompanyId = AppContexts.user().zeroCompanyIdInContract();

		List<String> companyIdList = companyRepo.acquireAllCompany();
		// Delete data:
		for (String cid : companyIdList) {
			// History:
			List<PerInfoHistorySelection> historyList = this.historyRepo
					.getAllBySelecItemIdAndCompanyId(selectionItemId, cid);
			historyList.stream().forEach(x -> {
				String histId = x.getHistId();

				// delete data History:
				this.historyRepo.remove(histId);

				// delete data Selection
				List<Selection> selectionList = this.selectionRepo.getAllSelectByHistId(histId);
				selectionList.forEach(z -> {
					this.selectionRepo.remove(z.getSelectionID());
				});

				// delete data OrderSelection:
				List<SelectionItemOrder> orderList = this.selectOrderRepo.getAllOrderSelectionByHistId(histId);
				orderList.forEach(y -> {
					this.selectOrderRepo.remove(y.getSelectionID());
				});
			});
		}

		// copy
		List<PerInfoHistorySelection> histList = this.historyRepo.getAllHistoryByCompanyID(zeroCompanyId);

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
			this.historyRepo.add(domain);

			// get all data Selection theo histId cty: 000000000000-0000
			createSelectionList(oldHistId, newHistId);
		});

	}

	// copy data Selection cua Histroy trong cty: 000000000000-0000 vao Cty
	// khac:
	private void createSelectionList(String oldHistId, String histId) {
		List<Selection> selectionList = this.selectionRepo.getAllSelectByHistId(oldHistId);
		List<SelectionItemOrder> orderList = this.selectOrderRepo.getAllOrderSelectionByHistId(oldHistId);
		selectionList.forEach(x -> {
			String newSelectionID = IdentifierUtil.randomUniqueId();
			Selection domain = Selection.createFromSelection(newSelectionID, histId, x.getSelectionCD().v(),
					x.getSelectionName().v(), x.getExternalCD().v(), x.getMemoSelection().v());

			this.selectionRepo.add(domain);

			List<SelectionItemOrder> orderOrgs = orderList.stream()
					.filter(o -> o.getSelectionID().equals(x.getSelectionID())).collect(Collectors.toList());

			if (!CollectionUtil.isEmpty(orderOrgs)) {
				SelectionItemOrder orderOrg = orderOrgs.get(0);
				SelectionItemOrder domainOrder = SelectionItemOrder.selectionItemOrder(newSelectionID, histId,
						orderOrg.getDisporder().v(), orderOrg.getInitSelection().value);

				this.selectOrderRepo.add(domainOrder);
			}
		});
	}
}
