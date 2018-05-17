package nts.uk.ctx.pereg.app.command.person.info.item;

import java.util.List;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.arc.error.BusinessException;
import nts.arc.layer.app.command.CommandHandler;
import nts.arc.layer.app.command.CommandHandlerContext;
import nts.arc.time.GeneralDate;
import nts.uk.ctx.pereg.app.command.person.info.category.CheckNameSpace;
import nts.uk.ctx.pereg.dom.person.info.category.PersonEmployeeType;
import nts.uk.ctx.pereg.dom.person.info.item.ItemType;
import nts.uk.ctx.pereg.dom.person.info.item.ItemTypeState;
import nts.uk.ctx.pereg.dom.person.info.item.PerInfoItemDefRepositoty;
import nts.uk.ctx.pereg.dom.person.info.item.PersonInfoItemDefinition;
import nts.uk.ctx.pereg.dom.person.info.item.SystemRequired;
import nts.uk.ctx.pereg.dom.person.info.selectionitem.ReferenceTypes;
import nts.uk.ctx.pereg.dom.person.info.selectionitem.SelectionItem;
import nts.uk.ctx.pereg.dom.person.info.singleitem.DataTypeValue;
import nts.uk.ctx.pereg.dom.person.info.singleitem.SingleItem;
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

		validateInput(itemDef, command);

		PersonInfoItemDefinition itemDefDomain;
		if (itemDef.getSystemRequired().equals(SystemRequired.REQUIRED)) {
			itemDefDomain = PersonInfoItemDefinition.createFromEntity(itemDef.getPerInfoItemDefId(),
					itemDef.getPerInfoCategoryId(), itemDef.getItemCode().v(), itemDef.getItemParentCode().v(),
					itemName, itemDef.getIsAbolition().value, itemDef.getIsFixed().value, itemDef.getIsRequired().value,
					itemDef.getSystemRequired().value, itemDef.getRequireChangable().value);
		} else {
			itemDefDomain = PersonInfoItemDefinition.createFromEntity(itemDef.getPerInfoItemDefId(),
					itemDef.getPerInfoCategoryId(), itemDef.getItemCode().v(), itemDef.getItemParentCode().v(),
					itemName, command.getIsAbolition(), itemDef.getIsFixed().value, command.getIsRequired(),
					itemDef.getSystemRequired().value, itemDef.getRequireChangable().value);
		}

		this.pernfoItemDefRep.updatePerInfoItemDef(itemDefDomain);
	}

	private void validateInput(PersonInfoItemDefinition itemDef, UpdateItemChangeCommand command) {

		if (CheckNameSpace.checkName(command.getItemName())) {
			throw new BusinessException("Msg_928");
		}

		if (!this.pernfoItemDefRep.checkItemNameIsUnique(itemDef.getPerInfoCategoryId(), command.getItemName(),
				itemDef.getPerInfoItemDefId())) {
			throw new BusinessException("Msg_358");
		}

		if (command.getDataType() != null && command.getDataType() == DataTypeValue.SELECTION.value) {
			List<Selection> selection;

			ItemTypeState itemState = itemDef.getItemTypeState();
			if (itemState.getItemType() == ItemType.SINGLE_ITEM) {
				SingleItem singleItem = (SingleItem) itemState;
				SelectionItem selItem = (SelectionItem) singleItem.getDataTypeState();
				if (selItem.getReferenceTypeState().getReferenceType() == ReferenceTypes.CODE_NAME) {

					if (command.getPersonEmployeeType() == PersonEmployeeType.PERSON.value) {
						selection = this.selectionRepo.getAllSelectionByCompanyId(
								AppContexts.user().zeroCompanyIdInContract(), command.getSelectionItemId(),
								GeneralDate.today());
					} else {
						// PersonEmployeeType.EMPLOYEE
						selection = this.selectionRepo.getAllSelectionByCompanyId(AppContexts.user().companyId(),
								command.getSelectionItemId(), GeneralDate.today());
					}
					if (selection == null || selection.size() == 0) {

						throw new BusinessException("Msg_587");

					}
				}
			}

		}
	}

}
