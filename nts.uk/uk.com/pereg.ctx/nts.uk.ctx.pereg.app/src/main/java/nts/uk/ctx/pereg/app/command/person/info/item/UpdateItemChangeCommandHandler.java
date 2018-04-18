package nts.uk.ctx.pereg.app.command.person.info.item;

import java.util.List;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.arc.error.BusinessException;
import nts.arc.layer.app.command.CommandHandler;
import nts.arc.layer.app.command.CommandHandlerContext;
import nts.arc.time.GeneralDate;
import nts.uk.ctx.pereg.app.command.person.info.category.CheckNameSpace;
import nts.uk.ctx.pereg.dom.person.info.item.PerInfoItemDefRepositoty;
import nts.uk.ctx.pereg.dom.person.info.item.PersonInfoItemDefinition;
import nts.uk.ctx.pereg.dom.person.info.item.SystemRequired;
import nts.uk.ctx.pereg.dom.person.setting.selectionitem.selection.Selection;
import nts.uk.ctx.pereg.dom.person.setting.selectionitem.selection.SelectionRepository;
import nts.uk.shr.com.context.AppContexts;

@Stateless
public class UpdateItemChangeCommandHandler extends CommandHandler<UpdateItemChangeCommand> {

	@Inject
	private PerInfoItemDefRepositoty pernfoItemDefRep;
	@Inject
	private SelectionRepository selectionRepo;

	@Override
	protected void handle(CommandHandlerContext<UpdateItemChangeCommand> context) {
		UpdateItemChangeCommand command = context.getCommand();
		String itemName = command.getItemName();
		
		PersonInfoItemDefinition itemDef = this.pernfoItemDefRep
				.getPerInfoItemDefById(command.getId(), AppContexts.user().contractCode()).get();
		
		validateInput(itemName, itemDef, command);
		
		PersonInfoItemDefinition itemDefDomain;
		if (itemDef.getSystemRequired().equals(SystemRequired.REQUIRED)) {
			itemDefDomain = PersonInfoItemDefinition.createFromEntity(itemDef.getPerInfoItemDefId(),
					itemDef.getPerInfoCategoryId(), itemDef.getItemCode().v(), itemDef.getItemParentCode().v(),
					itemName, itemDef.getIsAbolition().value, itemDef.getIsFixed().value,
					itemDef.getIsRequired().value, itemDef.getSystemRequired().value,
					itemDef.getRequireChangable().value);
		} else {
			itemDefDomain = PersonInfoItemDefinition.createFromEntity(itemDef.getPerInfoItemDefId(),
					itemDef.getPerInfoCategoryId(), itemDef.getItemCode().v(), itemDef.getItemParentCode().v(),
					itemName, command.getIsAbolition(), itemDef.getIsFixed().value,
					command.getIsRequired(), itemDef.getSystemRequired().value, itemDef.getRequireChangable().value);
		}

		this.pernfoItemDefRep.updatePerInfoItemDef(itemDefDomain);
	}
	
	private void validateInput(String itemName, PersonInfoItemDefinition itemDef, UpdateItemChangeCommand command) {
		if (CheckNameSpace.checkName(itemName)) {
			throw new BusinessException("Msg_928");
		}

		if (!this.pernfoItemDefRep.checkItemNameIsUnique(itemDef.getPerInfoCategoryId(), itemName,
				itemDef.getPerInfoItemDefId())) {
			throw new BusinessException("Msg_358");
		}

		if (command.getDataType() == 6) {
			List<Selection> selection = this.selectionRepo.getAllSelectionByCompanyId(
					AppContexts.user().zeroCompanyIdInContract(), command.getSelectionItemId(), GeneralDate.today());

			if (selection == null || selection.size() == 0) {

				throw new BusinessException("Msg_587");

			}
		}
	}
	
}
