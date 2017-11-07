package command.person.setting.selectionitem.selection;

import java.util.List;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.arc.layer.app.command.CommandHandlerContext;
import nts.arc.layer.app.command.CommandHandlerWithResult;
import nts.gul.text.IdentifierUtil;
import nts.uk.ctx.bs.person.dom.person.setting.selectionitem.PerInfoHistorySelection;
import nts.uk.ctx.bs.person.dom.person.setting.selectionitem.PerInfoHistorySelectionRepository;
import nts.uk.ctx.bs.person.dom.person.setting.selectionitem.selection.Selection;
import nts.uk.ctx.bs.person.dom.person.setting.selectionitem.selection.SelectionItemOrderRepository;
import nts.uk.ctx.bs.person.dom.person.setting.selectionitem.selection.SelectionRepository;

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

		// ドメインモデル「選択肢」を取得する(Lấy Domain Model 「選択肢」)
		String selectionId = command.getSelectionId();
		List<Selection> getAllSelectionBySelectionId = this.selectionRepo.getAllSelectionBySelectionID(selectionId);

		// ドメインモデル「選択肢履歴」を取得する(Lấy Domain Model 「選択肢履歴」)
		String selectionItemId = command.getSelectionItemId();
		List<PerInfoHistorySelection> getAllHistoryBySelectionItemId = this.historySelectionRepository
				.getAllHistoryBySelectionItemId(selectionItemId);

		return newHistId;
	}

}
