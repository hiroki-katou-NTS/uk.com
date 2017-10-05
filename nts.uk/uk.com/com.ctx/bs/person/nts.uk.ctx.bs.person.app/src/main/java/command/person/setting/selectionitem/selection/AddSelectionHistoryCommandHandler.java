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
import nts.uk.ctx.bs.person.dom.person.setting.selection.PerInfoHistorySelection;
import nts.uk.ctx.bs.person.dom.person.setting.selection.PerInfoHistorySelectionRepository;

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
		// Đang hỏi cách kiểm tra quyển User:

		// ドメインモデル「選択肢履歴」を登録する

		return newHistId;
	}

}
