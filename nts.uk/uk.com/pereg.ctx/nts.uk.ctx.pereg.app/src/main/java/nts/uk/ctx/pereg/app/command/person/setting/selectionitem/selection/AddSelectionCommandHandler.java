package nts.uk.ctx.pereg.app.command.person.setting.selectionitem.selection;

import java.util.List;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.arc.error.BusinessException;
import nts.arc.error.RawErrorMessage;
import nts.arc.layer.app.command.CommandHandler;
import nts.arc.layer.app.command.CommandHandlerContext;
import nts.gul.text.IdentifierUtil;
import nts.uk.ctx.pereg.dom.person.setting.selectionitem.selection.Selection;
import nts.uk.ctx.pereg.dom.person.setting.selectionitem.selection.SelectionRepository;
import nts.uk.ctx.pereg.dom.person.setting.selectionitem.selectionorder.SelectionItemOrder;
import nts.uk.ctx.pereg.dom.person.setting.selectionitem.selectionorder.SelectionItemOrderRepository;

/**
 * 
 * @author tuannv
 *
 */
@Stateless
public class AddSelectionCommandHandler extends CommandHandler<AddSelectionCommand> {

	@Inject
	private SelectionRepository selectionRepo;

	@Inject
	private SelectionItemOrderRepository selectionOrderRpo;

	@Override
	protected void handle(CommandHandlerContext<AddSelectionCommand> context) {
		AddSelectionCommand command = context.getCommand();

		// check ton tai selectionCD:
		List<Selection> checkSelectionCD = this.selectionRepo
				.getAllSelectionBySelectionCdAndHistId(command.getSelectionCD(), command.getHistId());
		if (!checkSelectionCD.equals(checkSelectionCD)) {
			throw new BusinessException(new RawErrorMessage("Msg_3"));
		}

		// (Thêm Item đăng ký vào Domain Model 「選択肢」):ドメインモデル「選択肢」に登録した項目を追加する
		String newId = IdentifierUtil.randomUniqueId();
		Selection domain = Selection.createFromSelection(newId, command.getHistId(), command.getSelectionCD(),
				command.getSelectionName(), command.getExternalCD(), command.getMemoSelection());
		this.selectionRepo.add(domain);

		// Lay selectionID:
		String histId = context.getCommand().getHistId();
		List<SelectionItemOrder> selectionList = this.selectionOrderRpo.getAllOrderSelectionByHistId(histId);
		if (!selectionList.isEmpty()) {
			// Dem tat ca SelectionID trong List:
			int length = selectionList.size();
			int count = selectionList.get(length - 1).getDisporder().v();

			// AddOrderSelectionCommand commandO
			SelectionItemOrder domainOrder = SelectionItemOrder.selectionItemOrder(newId, histId, count + 1, 0);

			// Add domainOrder:
			this.selectionOrderRpo.add(domainOrder);

		} else {
			SelectionItemOrder domainOrder = SelectionItemOrder.selectionItemOrder(newId, histId, 1, 1);
			this.selectionOrderRpo.add(domainOrder);
		}
	}

}
