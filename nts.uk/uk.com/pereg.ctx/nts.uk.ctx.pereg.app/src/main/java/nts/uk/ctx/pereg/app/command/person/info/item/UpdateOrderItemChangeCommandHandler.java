package nts.uk.ctx.pereg.app.command.person.info.item;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.arc.layer.app.command.CommandHandler;
import nts.arc.layer.app.command.CommandHandlerContext;
import nts.uk.ctx.pereg.dom.person.info.category.IsFixed;
import nts.uk.ctx.pereg.dom.person.info.category.PerInfoCategoryRepositoty;
import nts.uk.ctx.pereg.dom.person.info.category.PersonInfoCategory;
import nts.uk.ctx.pereg.dom.person.info.item.PerInfoItemDefRepositoty;
import nts.uk.ctx.pereg.dom.person.info.order.PerInfoItemDefOrder;
import nts.uk.shr.com.context.AppContexts;

@Stateless
public class UpdateOrderItemChangeCommandHandler extends CommandHandler<UpdateOrderItemChangeCommand> {

	@Inject
	private PerInfoItemDefRepositoty perinfoItemDefRep;

	@Inject
	private PerInfoCategoryRepositoty perCtgRep;

	@Override
	protected void handle(CommandHandlerContext<UpdateOrderItemChangeCommand> context) {

		UpdateOrderItemChangeCommand command = context.getCommand();

		String contractCd = AppContexts.user().contractCode();

		PersonInfoCategory perInfoCategory = this.perCtgRep.getPerInfoCategory(command.getCategoryId(), contractCd)
				.get();

		for (OrderItemChange i : command.getOrderItemList()) {

			int itemIndex = command.getOrderItemList().indexOf(i) + 1;

			PerInfoItemDefOrder itemDefOrderOpt = this.perinfoItemDefRep.getPerInfoItemDefOrdersByItemId(i.getId())
					.get();

			PerInfoItemDefOrder itemDefOrderDomain = PerInfoItemDefOrder.createFromJavaType(
					itemDefOrderOpt.getPerInfoItemDefId(), itemDefOrderOpt.getPerInfoCtgId(), itemIndex,
					perInfoCategory.getIsFixed().equals(IsFixed.FIXED) ? itemDefOrderOpt.getDisplayOrder().v()
							: itemIndex);
			this.perinfoItemDefRep.UpdateOrderItem(itemDefOrderDomain);

		}

	}

}
