package nts.uk.ctx.pereg.app.command.person.setting.selectionitem.selection;

import java.util.List;
import java.util.stream.Collectors;

import javax.ejb.Stateless;
import javax.inject.Inject;

import org.apache.commons.lang3.StringUtils;

import nts.arc.error.BusinessException;
import nts.arc.layer.app.command.CommandHandlerContext;
import nts.arc.layer.app.command.CommandHandlerWithResult;
import nts.arc.time.GeneralDate;
import nts.gul.text.IdentifierUtil;
import nts.uk.ctx.pereg.dom.person.info.category.PersonInfoCategory;
import nts.uk.ctx.pereg.dom.person.setting.selectionitem.PerInfoHistorySelection;
import nts.uk.ctx.pereg.dom.person.setting.selectionitem.PerInfoHistorySelectionRepository;
import nts.uk.ctx.pereg.dom.person.setting.selectionitem.selection.Selection;
import nts.uk.ctx.pereg.dom.person.setting.selectionitem.selection.SelectionItemOrder;
import nts.uk.ctx.pereg.dom.person.setting.selectionitem.selection.SelectionItemOrderRepository;
import nts.uk.ctx.pereg.dom.person.setting.selectionitem.selection.SelectionRepository;
import nts.uk.shr.com.context.AppContexts;
import nts.uk.shr.com.context.LoginUserContext;
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
		String newHistId = IdentifierUtil.randomUniqueId();

		// ドメインモデル「選択肢履歴」のエラーチェッ
		GeneralDate getStartDate = command.getStartDate();// startDateNew
		String companyId = command.getCompanyId();
		// check: 最新の履歴の開始日 ＞ 直前の履歴の開始日
		GeneralDate endDateLast = GeneralDate.fromString("9999/12/31", "yyyy/MM/dd");
		List<PerInfoHistorySelection> lstHist = this.historySelectionRepository
				.getHistSelByEndDate(command.getSelectionItemId(), companyId, endDateLast);
		if (!lstHist.isEmpty()) {
			PerInfoHistorySelection histLast = lstHist.get(0);
			if (getStartDate.beforeOrEquals(histLast.getPeriod().start())) {
				throw new BusinessException("Msg_102");
			}
		}

		String selectItemID = command.getSelectionItemId();
		GeneralDate startDate = command.getStartDate();
		DatePeriod period = new DatePeriod(startDate, endDateLast);

		// get GroupCompaniesAdmin
		LoginUserContext loginUserContext = AppContexts.user();
		String roleID = loginUserContext.roles().forGroupCompaniesAdmin();

		// 個人情報共通アルゴリズム「ログイン者がグループ会社管理者かどうか判定する」を実行する
		boolean result = StringUtils.isEmpty(roleID) ? false : true;
		if (!result) {
			// 共通アルゴリズム「契約内ゼロ会社の会社IDを取得する」を実行する
			String cid = AppContexts.user().zeroCompanyIdInContract();

			// ドメインモデル「選択肢履歴」を登録する
			PerInfoHistorySelection domainHist = PerInfoHistorySelection.createHistorySelection(newHistId, selectItemID,
					cid, period);
			this.historySelectionRepository.add(domainHist);
		} else {
			String cid_login = AppContexts.user().companyId();

			// ドメインモデル「選択肢履歴」を登録する
			PerInfoHistorySelection domainHist1 = PerInfoHistorySelection.createHistorySelection(newHistId,
					selectItemID, cid_login, period);
			this.historySelectionRepository.add(domainHist1);
		}
		// if last hist isPresent (not first time create)
		if (!lstHist.isEmpty()) {
			PerInfoHistorySelection lastHist = lstHist.get(0);
			// set end date lastHist = startDate of newHist -1
			DatePeriod lastHistPeriod = new DatePeriod(lastHist.getPeriod().start(), startDate.addDays(-1));

			lastHist.setPeriod(lastHistPeriod);

			this.historySelectionRepository.update(lastHist);

		}

		String histId = command.getHistId();
		List<Selection> getAllSelectByHistId = this.selectionRepo.getAllSelectByHistId(histId);
		List<SelectionItemOrder> getAllOrderSelectionByHistId = this.selectionOrderRepo
				.getAllOrderSelectionByHistId(histId);

		for (Selection selection : getAllSelectByHistId) {
			String newSelectionId = IdentifierUtil.randomUniqueId();

			// Tao do main: 選択肢
			Selection domainSelection = Selection.createFromSelection(newSelectionId, newHistId,
					selection.getSelectionCD().v(), selection.getSelectionName().v(), selection.getExternalCD().v(),
					selection.getMemoSelection().v());

			// ドメインモデル「選択肢」をコピーする
			this.selectionRepo.add(domainSelection);

			// Lay tat ca gia tri trong domain: 選択肢の並び順と既定値 theo SelectionID
			SelectionItemOrder orderOrg = getAllOrderSelectionByHistId.stream()
					.filter(o -> o.getSelectionID().equals(selection.getSelectionID())).collect(Collectors.toList())
					.get(0);

			// Tao domain: 選択肢の並び順と既定値
			SelectionItemOrder domainSelectionOrder = SelectionItemOrder.selectionItemOrder(newSelectionId, newHistId,
					orderOrg.getDisporder().v(), orderOrg.getInitSelection().value);

			// ドメインモデル「選択肢の並び順と既定値」をコピーする
			this.selectionOrderRepo.add(domainSelectionOrder);
		}

		return newHistId;
	}

}
