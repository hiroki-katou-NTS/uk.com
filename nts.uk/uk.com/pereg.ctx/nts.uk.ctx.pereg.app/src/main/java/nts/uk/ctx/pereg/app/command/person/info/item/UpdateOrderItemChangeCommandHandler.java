package nts.uk.ctx.pereg.app.command.person.info.item;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.arc.layer.app.command.CommandHandler;
import nts.arc.layer.app.command.CommandHandlerContext;
import nts.uk.ctx.pereg.app.find.person.info.item.ItemOrder;
import nts.uk.ctx.pereg.app.find.person.info.item.PerInfoItemDefFinder;
import nts.uk.ctx.pereg.dom.person.info.category.PerInfoCategoryRepositoty;
import nts.uk.ctx.pereg.dom.person.info.category.PersonInfoCategory;
import nts.uk.ctx.pereg.dom.person.info.item.PerInfoItemDefRepositoty;
import nts.uk.ctx.pereg.dom.person.info.order.PerInfoItemDefOrder;
import nts.uk.shr.com.context.AppContexts;

@Stateless
public class UpdateOrderItemChangeCommandHandler extends CommandHandler<UpdateOrderItemChangeCommand> {

	@Inject
	private PerInfoItemDefRepositoty itemRepo;

	@Inject
	private PerInfoCategoryRepositoty perCtgRep;

	@Inject
	private PerInfoItemDefFinder itemFinder;

	@Override
	protected void handle(CommandHandlerContext<UpdateOrderItemChangeCommand> context) {

		UpdateOrderItemChangeCommand command = context.getCommand();
		String contractCd = AppContexts.user().contractCode();
		PersonInfoCategory ctg = this.perCtgRep.getPerInfoCategory(command.getCategoryId(), contractCd).get();
		List<String> itemId = command.getOrderItemList().stream().map(c -> {
			return c.getId();
		}).collect(Collectors.toList());
		List<ItemOrder> itemOrderLst = this.itemFinder.getAllItemOrderByCtgId(command.getCategoryId(), itemId,
				ctg.getCategoryCode().v());
		List<PerInfoItemDefOrder> itemLst = this.convertData(command, ctg, itemOrderLst);
		this.itemRepo.updateOrderItem(itemLst);

	}

	private List<PerInfoItemDefOrder> convertData(UpdateOrderItemChangeCommand command, PersonInfoCategory ctg,
			List<ItemOrder> itemFullLst) {
		List<OrderItemChange> itemOrderLst = new ArrayList<>();
		List<PerInfoItemDefOrder> itemLst = new ArrayList<>();
		PerInfoItemDefOrder itemDefOrderDomain = new PerInfoItemDefOrder();

		command.getOrderItemList().stream().forEach(c -> {
			ItemOrder itemChild = itemFullLst.stream().filter(item -> item.getItemId().equals(c.getId())).findFirst()
					.get();
			itemOrderLst.add(new OrderItemChange(itemChild.getItemId(), "", itemChild.getDisplayOrder()));
			List<ItemOrder> itemChilds = itemFullLst.stream()
					.filter(item -> item.getParentCode().equals(itemChild.getItemCode())).collect(Collectors.toList());
			if (itemChilds.size() > 0) {
				for (ItemOrder child : itemChilds) {
					itemOrderLst.add(new OrderItemChange(child.getItemId(), "", child.getDisplayOrder()));
					List<ItemOrder> itemChildChild = itemFullLst.stream()
							.filter(item -> item.getParentCode().equals(child.getItemCode()))
							.collect(Collectors.toList());
					if (itemChilds.size() > 0) {
						for (ItemOrder sub : itemChildChild) {
							itemOrderLst.add(new OrderItemChange(sub.getItemId(), "", sub.getDisplayOrder()));
						}
					}
				}
			}

		});
		switch (ctg.getIsFixed()) {
		case FIXED:
			for (OrderItemChange i : itemOrderLst) {
				int itemIndex = itemOrderLst.indexOf(i) + 1;
				itemDefOrderDomain = PerInfoItemDefOrder.createFromJavaType(i.getId(), command.getCategoryId(),
						itemIndex, i.getDisplayOder());
				itemLst.add(itemDefOrderDomain);
			}
			
			break;
		case NOT_FIXED:
			for (OrderItemChange i : itemOrderLst) {
				int itemIndex = itemOrderLst.indexOf(i) + 1;
				itemDefOrderDomain = PerInfoItemDefOrder.createFromJavaType(i.getId(), command.getCategoryId(),
						itemIndex, itemIndex);
				itemLst.add(itemDefOrderDomain);
			}
			
			break;
		}
		return itemLst;
	}
}
