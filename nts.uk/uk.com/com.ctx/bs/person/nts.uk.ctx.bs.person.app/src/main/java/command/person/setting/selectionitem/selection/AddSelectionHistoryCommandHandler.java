package command.person.setting.selectionitem.selection;

import java.util.List;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.arc.error.BusinessException;
import nts.arc.error.RawErrorMessage;
import nts.arc.layer.app.command.CommandHandlerContext;
import nts.arc.layer.app.command.CommandHandlerWithResult;
import nts.arc.time.GeneralDate;
import nts.gul.text.IdentifierUtil;
import nts.uk.ctx.bs.person.dom.person.info.category.PersonInfoCategory;
import nts.uk.ctx.bs.person.dom.person.setting.selectionitem.PerInfoHistorySelection;
import nts.uk.ctx.bs.person.dom.person.setting.selectionitem.PerInfoHistorySelectionRepository;

@Stateless
public class AddSelectionHistoryCommandHandler extends CommandHandlerWithResult<AddSelectionHistoryCommand, String> {

	@Inject
	private PerInfoHistorySelectionRepository historySelectionRepository;

	@Override
	protected String handle(CommandHandlerContext<AddSelectionHistoryCommand> context) {
		AddSelectionHistoryCommand command = context.getCommand();
		String newHistId = IdentifierUtil.randomUniqueId();

		// ドメインモデル「選択肢履歴」のエラーチェッ
		GeneralDate getStartDate = command.getStartDate();
		List<PerInfoHistorySelection> startDateHistoryList = this.historySelectionRepository
				.historyStartDateSelection(getStartDate);
		if (startDateHistoryList.size() > 0) {
			throw new BusinessException(new RawErrorMessage("Msg_102"));
		}

		// ログインしているユーザーの権限をチェックする
		String newId = IdentifierUtil.randomUniqueId();
		GeneralDate startDate = GeneralDate.ymd(1900, 1, 1);
		GeneralDate endDate = GeneralDate.ymd(9999, 12, 31);
		boolean userLogin;
		if (userLogin = true) {
			String cid0 = PersonInfoCategory.ROOT_COMPANY_ID;

			// ドメインモデル「選択肢履歴」を登録する
			PerInfoHistorySelection domainHist = PerInfoHistorySelection.createHistorySelection(newHistId, newId, cid0,
					endDate, startDate);
			this.historySelectionRepository.add(domainHist);
		} else {
			String cid_login = PersonInfoCategory.ROOT_COMPANY_ID;

			// ドメインモデル「選択肢履歴」を登録する
			PerInfoHistorySelection domainHist1 = PerInfoHistorySelection.createHistorySelection(newHistId, newId, cid_login,
					endDate, startDate);
			this.historySelectionRepository.add(domainHist1);
		}

		// ドメインモデル「選択肢」をコピーする
		// TODO:

		// ドメインモデル「選択肢の並び順と既定値」をコピーする
		// TODO:

		return newHistId;
	}

}
