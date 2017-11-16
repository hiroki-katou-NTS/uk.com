package command.person.setting.selectionitem.selection;

import java.util.List;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.arc.error.BusinessException;
import nts.arc.error.RawErrorMessage;
import nts.arc.layer.app.command.CommandHandler;
import nts.arc.layer.app.command.CommandHandlerContext;
import nts.gul.text.IdentifierUtil;
import nts.uk.ctx.bs.person.dom.person.setting.selectionitem.selection.Selection;
import nts.uk.ctx.bs.person.dom.person.setting.selectionitem.selection.SelectionItemOrder;
import nts.uk.ctx.bs.person.dom.person.setting.selectionitem.selection.SelectionItemOrderRepository;
import nts.uk.ctx.bs.person.dom.person.setting.selectionitem.selection.SelectionRepository;

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
		List<Selection> checkSelectionCD = this.selectionRepo.getAllSelectionBySelectionCdAndHistId(command.getSelectionCD(), command.getHistId());
		if (!checkSelectionCD.equals(checkSelectionCD)) {
			throw new BusinessException(new RawErrorMessage("Msg_3"));
		}

		// (Thêm Item đăng ký vào Domain Model 「選択肢」):ドメインモデル「選択肢」に登録した項目を追加する
		String newId = IdentifierUtil.randomUniqueId();
		//String newHistId = IdentifierUtil.randomUniqueId();
		Selection domain = Selection.createFromSelection(newId, command.getHistId(), command.getSelectionCD(),
				command.getSelectionName(), command.getExternalCD(), command.getMemoSelection());

		// Kiem tra quyen User login:
//		boolean isSystemAdmin = false;
//		if (isSystemAdmin == true) {
//			this.selectionRepo.add(domain);
//		} else {
//			this.selectionRepo.add(domain);
//		}
		this.selectionRepo.add(domain);

		// Lay selectionID:
		String histId = context.getCommand().getHistId();
		//String selectionCd = context.getCommand().getSelectionCD();
		List<Selection> selectionList = this.selectionRepo.getAllSelectByHistId( histId);
		if (!selectionList.isEmpty()) {
			//String selectionId = selectionList.get(0).getSelectionID();
			//List<SelectionItemOrder> orderDomainlst = this.selectionOrderRpo.getAllOrderItemSelection(selectionId);
			//if (!orderDomainlst.isEmpty()) {

				// 件数：1件以上の場合: 既定値区分：FLASE, 並び順：並び順の最大値 + 1
				//String selectionCD = context.getCommand().getSelectionCD();
				//List<String> selectionIdList = this.selectionRepo.geSelectionList(selectionCD);

				// Dem tat ca SelectionID trong List:
				int count = selectionList.size();

				// AddOrderSelectionCommand commandO
				SelectionItemOrder domainOrder = SelectionItemOrder.selectionItemOrder(newId, histId,
						count + 1, 0);

				// Add domainOrder:
				this.selectionOrderRpo.add(domainOrder);
			//} else {
				// 件数：0件の場合: 既定値区分：TRUE, 並び順：1
				//SelectionItemOrder domainOrder = SelectionItemOrder.selectionItemOrder(command.getSelectionID(), histId,
				//		1, 1);

				// Add domainOrder:
				//this.selectionOrderRpo.add(domainOrder);
			//}
		}else{
			SelectionItemOrder domainOrder = SelectionItemOrder.selectionItemOrder(newId, histId,
					1, 1);
			this.selectionOrderRpo.add(domainOrder);
		}
	}

}
