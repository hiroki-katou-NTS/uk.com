package nts.uk.ctx.pereg.app.command.person.info.item;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.arc.layer.app.command.CommandHandler;
import nts.arc.layer.app.command.CommandHandlerContext;
import nts.uk.ctx.pereg.dom.person.info.category.PerInfoCategoryRepositoty;
import nts.uk.ctx.pereg.dom.person.info.category.PersonInfoCategory;
import nts.uk.ctx.pereg.dom.person.info.item.PerInfoItemDefRepositoty;
import nts.uk.ctx.pereg.dom.person.info.item.PersonInfoItemDefinition;
import nts.uk.ctx.pereg.dom.person.info.order.PerInfoItemDefOrder;
import nts.uk.shr.com.context.AppContexts;

@Stateless
public class UpdateOrderItemChangeCommandHandler extends CommandHandler<UpdateOrderItemChangeCommand> {

	@Inject
	private PerInfoItemDefRepositoty itemRepo;

	@Inject
	private PerInfoCategoryRepositoty perCtgRep;

	@Override
	protected void handle(CommandHandlerContext<UpdateOrderItemChangeCommand> context) {

		UpdateOrderItemChangeCommand command = context.getCommand();
		String contractCd = AppContexts.user().contractCode();
		List<PerInfoItemDefOrder> itemLst = new ArrayList<>();
		
		PersonInfoCategory ctg = this.perCtgRep.getPerInfoCategory(command.getCategoryId(), contractCd)
				.get();
		List<PersonInfoItemDefinition> itemFullLst = this.getData(command, ctg);
		itemLst = this.convertData(command, ctg, itemFullLst);
		this.itemRepo.updateOrderItem(itemLst);

	}
	
	private List<PersonInfoItemDefinition> getData(UpdateOrderItemChangeCommand command, PersonInfoCategory perInfoCategory) {
		String contractCd = AppContexts.user().contractCode();
		List<String> itemId = command.getOrderItemList().stream().map(c -> {
			return c.getId();
		}).collect(Collectors.toList());
		return this.itemRepo.getItemLstByListId(itemId, command.getCategoryId(),
				perInfoCategory.getCategoryCode().v(), contractCd);
		
	}
	
	private List<PerInfoItemDefOrder> convertData(UpdateOrderItemChangeCommand command, PersonInfoCategory ctg, List<PersonInfoItemDefinition> itemFullLst){
		List<OrderItemChange> itemOrderLst = new ArrayList<>();
		List<PerInfoItemDefOrder> itemLst = new ArrayList<>();
		PerInfoItemDefOrder itemDefOrderDomain = new PerInfoItemDefOrder();
		
		command.getOrderItemList().stream().forEach(c -> {
			itemOrderLst.add(c);
			PersonInfoItemDefinition itemChild = itemFullLst.stream().filter(item -> item.getPerInfoItemDefId().equals(c.getId()))
					.findFirst().get();
			List<PersonInfoItemDefinition> itemChilds = itemFullLst.stream()
					.filter(item -> (item.getItemParentCode().v().equals(itemChild.getItemCode().v())))
					.collect(Collectors.toList());
			if (itemChilds.size() > 0) {
				for (PersonInfoItemDefinition it : itemChilds) {
					itemOrderLst.add(new OrderItemChange(it.getPerInfoItemDefId(), ""));
				}
			}

		});
		for (OrderItemChange i : itemOrderLst) {

			int itemIndex = itemOrderLst.indexOf(i) + 1;
			switch (ctg.getIsFixed()) {
			case FIXED:
				itemDefOrderDomain = PerInfoItemDefOrder.createFromJavaType(i.getId(), command.getCategoryId(),
						itemIndex);
				break;
			case NOT_FIXED:

				itemDefOrderDomain = PerInfoItemDefOrder.createFromJavaType(i.getId(), command.getCategoryId(),
						itemIndex, itemIndex);
				break;
			}
			itemLst.add(itemDefOrderDomain);
		}
		return itemLst;
	}

}
