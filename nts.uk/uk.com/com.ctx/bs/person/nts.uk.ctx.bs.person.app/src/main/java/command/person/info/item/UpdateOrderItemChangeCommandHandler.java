package command.person.info.item;

import java.util.List;

import javax.inject.Inject;

import nts.arc.layer.app.command.CommandHandler;
import nts.arc.layer.app.command.CommandHandlerContext;
import nts.uk.ctx.bs.person.dom.person.info.item.PerInfoItemDefRepositoty;
import nts.uk.ctx.bs.person.dom.person.info.item.PersonInfoItemDefinition;
import nts.uk.shr.com.context.AppContexts;

public class UpdateOrderItemChangeCommandHandler extends CommandHandler<UpdateOrderItemChangeCommand> {

	@Inject
	private PerInfoItemDefRepositoty pernfoItemDefRep;

	@Override
	protected void handle(CommandHandlerContext<UpdateOrderItemChangeCommand> context) {

		List<OrderItemChange> listItem = context.getCommand().getOrderItemList();

		for (OrderItemChange i : listItem) {
			PersonInfoItemDefinition itemdef = this.pernfoItemDefRep
					.getPerInfoItemDefById(i.getId(), AppContexts.user().contractCode()).get();

			PersonInfoItemDefinition itemDefDomain = PersonInfoItemDefinition.createFromEntity(
					itemdef.getPerInfoItemDefId(), itemdef.getPerInfoCategoryId(), itemdef.getItemCode().v(),
					itemdef.getItemParentCode().v(), itemdef.getItemName().v(), itemdef.getIsAbolition().value,
					itemdef.getIsFixed().value, itemdef.getIsRequired().value, itemdef.getSystemRequired().value,
					itemdef.getRequireChangable().value);
		}

	}

}
