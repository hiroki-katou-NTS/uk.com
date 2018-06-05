package nts.uk.ctx.pereg.app.command.person.setting.selectionitem.selection.remove;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.transaction.Transactional;

import nts.arc.layer.app.command.CommandHandler;
import nts.arc.layer.app.command.CommandHandlerContext;
import nts.uk.ctx.pereg.dom.person.setting.selectionitem.selection.SelectionRepository;
import nts.uk.ctx.pereg.dom.person.setting.selectionitem.selectionorder.SelectionItemOrderRepository;

/**
 * 
 * @author tuannv
 *
 */
@Stateless
@Transactional
public class RemoveSelectionCommandHandler extends CommandHandler<RemoveSelectionCommand> {

	@Inject
	private SelectionRepository selectionRepo;

	@Inject
	private SelectionItemOrderRepository selectionOrderRepo;

	@Override
	protected void handle(CommandHandlerContext<RemoveSelectionCommand> context) {
		RemoveSelectionCommand command = context.getCommand();
		String getSelectionID = command.getSelectionID();

		// Xoa domain: ドメインモデル「選択肢」を削除する(Xóa Domain Model 「選択肢」)
		this.selectionRepo.remove(getSelectionID);

		// Xoa domain: ドメインモデル「選択肢の並び順と既定値」を削除するXóa Domain Model 「選択肢の並び順と既定値」
		this.selectionOrderRepo.remove(getSelectionID);
		
		/*
		List<SelectionItemOrder> getHistId = this.selectionOrderRepo.getAllOrderItemSelection(getSelectionID);
		for (SelectionItemOrder hist : getHistId) {
			this.selectionOrderRepo.remove(hist.getHistId());
		}
		*/
	}

}
