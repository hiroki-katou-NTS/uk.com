package command.person.info.item;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.arc.layer.app.command.CommandHandler;
import nts.arc.layer.app.command.CommandHandlerContext;
import nts.uk.ctx.bs.person.dom.person.info.item.PerInfoItemDefRepositoty;
import nts.uk.ctx.bs.person.dom.person.info.item.PersonInfoItemDefinition;
import nts.uk.ctx.bs.person.dom.person.info.item.SystemRequired;
import nts.uk.shr.com.context.AppContexts;

@Stateless
public class UpdateItemChangeCommandHandler extends CommandHandler<UpdateItemChangeCommand> {

	@Inject
	private PerInfoItemDefRepositoty pernfoItemDefRep;

	@Override
	protected void handle(CommandHandlerContext<UpdateItemChangeCommand> context) {
		UpdateItemChangeCommand command = context.getCommand();
		PersonInfoItemDefinition itemDef = this.pernfoItemDefRep
				.getPerInfoItemDefById(command.getId(), AppContexts.user().contractCode()).get();

		PersonInfoItemDefinition itemDefDomain;
		if (itemDef.getSystemRequired().equals(SystemRequired.REQUIRED)) {
			itemDefDomain = PersonInfoItemDefinition.createFromEntity(itemDef.getPerInfoItemDefId(),
					itemDef.getPerInfoCategoryId(), itemDef.getItemCode().v(), itemDef.getItemParentCode().v(),
					command.getItemName(), itemDef.getIsAbolition().value, itemDef.getIsFixed().value,
					itemDef.getIsRequired().value, itemDef.getSystemRequired().value,
					itemDef.getRequireChangable().value);
		} else {
			itemDefDomain = PersonInfoItemDefinition.createFromEntity(itemDef.getPerInfoItemDefId(),
					itemDef.getPerInfoCategoryId(), itemDef.getItemCode().v(), itemDef.getItemParentCode().v(),
					command.getItemName(), command.getIsAbolition(), itemDef.getIsFixed().value,
					command.getIsRequired(), itemDef.getSystemRequired().value, itemDef.getRequireChangable().value);
		}

		this.pernfoItemDefRep.updatePerInfoItemDef(itemDefDomain);
	}

}
