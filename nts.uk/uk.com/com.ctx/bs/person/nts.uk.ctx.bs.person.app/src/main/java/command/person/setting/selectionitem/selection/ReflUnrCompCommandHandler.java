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
import nts.uk.ctx.bs.person.dom.person.setting.selectionitem.PerInfoHistorySelection;
import nts.uk.ctx.bs.person.dom.person.setting.selectionitem.PerInfoHistorySelectionRepository;
import nts.uk.ctx.bs.person.dom.person.setting.selectionitem.selection.Selection;
import nts.uk.ctx.bs.person.dom.person.setting.selectionitem.selection.SelectionItemOrder;
import nts.uk.ctx.bs.person.dom.person.setting.selectionitem.selection.SelectionItemOrderRepository;
import nts.uk.ctx.bs.person.dom.person.setting.selectionitem.selection.SelectionRepository;
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

		// ドメインモデル「選択肢履歴」を取得する(Lấy Domain Model 「選択肢履歴」)
		String selectionItemId = command.getSelectionItemId();
		String companyId = command.getCompanyId();

		List<String> companyIdList = GetListCompanyOfContract.LIST_COMPANY_OF_CONTRACT;

		GeneralDate startDate = GeneralDate.ymd(1900, 1, 1);
		GeneralDate endDate = GeneralDate.ymd(9999, 12, 31);
		DatePeriod period = new DatePeriod(startDate, endDate);

		// Xoa di tat ca lich su cua thang SelectionItemId:
		List<PerInfoHistorySelection> historyList = this.historySelectionRepository
				.getAllPerInfoHistorySelection(selectionItemId, companyId);
		historyList.stream().forEach(x -> this.selectionRepo.remove(x.getSelectionItemId()));

		// Xoa tat ca Selection cua SelectionItem dang lua chon
		String selectionId = command.getSelectionId();
		List<Selection> selectionBySelectionId = this.selectionRepo.getAllSelectionBySelectionID(selectionId);
		selectionBySelectionId.stream().forEach(x->this.selectionRepo.remove(x.getSelectionID()));
		
		// Xoa tat ca OrderSelection:
		List<SelectionItemOrder> orderBySelectionId = this.selectionOrderRepo.getAllOrderBySelectionId(selectionId);
		orderBySelectionId.stream().forEach(x->this.selectionOrderRepo.remove(x.getSelectionID()));
		
		// insert data of all company
		for (String cid : companyIdList) {

			// history:
			PerInfoHistorySelection domainHist = PerInfoHistorySelection.createHistorySelection(newHistId,
					command.getSelectionItemId(), cid, period);
			this.historySelectionRepository.add(domainHist);

			// Selection
			String histId = command.getHistId();
			List<Selection> getAllSelectByHistId = this.selectionRepo.getAllSelectByHistId(histId);
			List<SelectionItemOrder> getAllOrderSelectionByHistId = this.selectionOrderRepo
					.getAllOrderSelectionByHistId(histId);

			for (Selection selection : getAllSelectByHistId) {
				String newSelectionId = IdentifierUtil.randomUniqueId();
				// Tao do main: 選択肢
				Selection domainSelection = Selection.createFromSelection(newSelectionId, newHistId,
						selection.getExternalCD().v(), selection.getSelectionName().v(), selection.getExternalCD().v(),
						selection.getMemoSelection().v());

				// ドメインモデル「選択肢」をコピーする
				this.selectionRepo.add(domainSelection);

				// Lay tat ca gia tri trong domain: 選択肢の並び順と既定値 theo SelectionID
				SelectionItemOrder orderOrg = getAllOrderSelectionByHistId.stream()
						.filter(o -> o.getSelectionID().equals(selection.getSelectionID())).collect(Collectors.toList())
						.get(0);

				// Tao domain: 選択肢の並び順と既定値
				SelectionItemOrder domainSelectionOrder = SelectionItemOrder.selectionItemOrder(newSelectionId,
						newHistId, orderOrg.getDisporder().v(), orderOrg.getInitSelection().value);

				// ドメインモデル「選択肢の並び順と既定値」をコピーする
				this.selectionOrderRepo.add(domainSelectionOrder);
			}
		}

		return newHistId;
	}

}
