package nts.uk.ctx.pereg.app.command.person.setting.selectionitem.selection;

import java.util.ArrayList;
import java.util.List;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.arc.layer.app.command.CommandHandler;
import nts.arc.layer.app.command.CommandHandlerContext;
import nts.uk.ctx.pereg.dom.person.setting.selectionitem.selectionorder.SelectionItemOrder;
import nts.uk.ctx.pereg.dom.person.setting.selectionitem.selectionorder.SelectionItemOrderRepository;
/**
 * Register/Update domain 選択肢の並び順
 * @author hoatt
 *
 */

@Stateless
public class UpdateSelOrderCommandHandler extends CommandHandler<List<UpdateSelOrderCommand>> {

	@Inject
	private SelectionItemOrderRepository repoSelOrder;

	@Override
	protected void handle(CommandHandlerContext<List<UpdateSelOrderCommand>> context) {
		List<UpdateSelOrderCommand> data = context.getCommand();
		List<SelectionItemOrder> lstSelOrder = new ArrayList<>();
		//convert list UpdateSelOrderCommand to list SelectionItemOrder
		for (UpdateSelOrderCommand selOrder : data) {
			lstSelOrder.add(SelectionItemOrder.selectionItemOrder(selOrder.getSelectionID(), selOrder.getHistId(), selOrder.getDispOrder(), selOrder.isInitSelection() == true? 1: 0));
		}
		//update all list selection order
		repoSelOrder.updateListSelOrder(lstSelOrder);
	}
	


}
