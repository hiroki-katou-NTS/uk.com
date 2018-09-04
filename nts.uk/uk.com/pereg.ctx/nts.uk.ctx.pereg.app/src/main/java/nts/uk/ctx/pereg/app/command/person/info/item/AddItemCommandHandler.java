package nts.uk.ctx.pereg.app.command.person.info.item;

import java.util.List;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.arc.error.BusinessException;
import nts.arc.layer.app.command.CommandHandlerContext;
import nts.arc.layer.app.command.CommandHandlerWithResult;
import nts.arc.time.GeneralDate;
import nts.uk.ctx.pereg.app.command.person.info.category.CheckNameSpace;
import nts.uk.ctx.pereg.dom.company.ICompanyRepo;
import nts.uk.ctx.pereg.dom.person.info.category.PerInfoCategoryRepositoty;
import nts.uk.ctx.pereg.dom.person.info.category.PersonInfoCategory;
import nts.uk.ctx.pereg.dom.person.info.item.PerInfoItemDefRepositoty;
import nts.uk.ctx.pereg.dom.person.info.item.PersonInfoItemDefinition;
import nts.uk.ctx.pereg.dom.person.setting.selectionitem.selection.Selection;
import nts.uk.ctx.pereg.dom.person.setting.selectionitem.selection.SelectionRepository;
import nts.uk.shr.com.context.AppContexts;

@Stateless
public class AddItemCommandHandler extends CommandHandlerWithResult<AddItemCommand, String> {

	@Inject
	private PerInfoItemDefRepositoty pernfoItemDefRep;

	@Inject
	private PerInfoCategoryRepositoty perInfoCtgRep;

	@Inject
	private SelectionRepository selectionRepo;
	
	@Inject
	private ICompanyRepo companyRepo;

	private final static String SPECIAL_ITEM_CODE = "IO";
	private final static int ITEM_CODE_DEFAUT_NUMBER = 0;

	@Override
	protected String handle(CommandHandlerContext<AddItemCommand> context) {
		AddItemCommand addItemCommand = context.getCommand();
		String contractCd = AppContexts.user().contractCode();
		
		String itemName = addItemCommand.getItemName();
		String categoryId = addItemCommand.getPerInfoCtgId();
		
		// validate
		validateInput(addItemCommand);
		
		PersonInfoCategory perInfoCtg = this.perInfoCtgRep.getPerInfoCategory(categoryId, contractCd).orElse(null);
		if (perInfoCtg == null) {
			return null;
		}
		
		String categoryCd = perInfoCtg.getCategoryCode().v();
		String itemCodeLastes = this.pernfoItemDefRep.getPerInfoItemCodeLastest(contractCd, categoryCd);
		String newItemCode = createNewCode(itemCodeLastes, SPECIAL_ITEM_CODE);
		AddItemCommand newItemCommand = new AddItemCommand(categoryId, newItemCode, null,
				itemName, addItemCommand.getSingleItem(), addItemCommand.getPersonEmployeeType());
		
		// add to zero-company
		PersonInfoItemDefinition perInfoItemDef = MappingDtoToDomain.mappingFromDomaintoCommand(newItemCommand);
		String perInfoItemId = this.pernfoItemDefRep.addPerInfoItemDefRoot(perInfoItemDef, contractCd, categoryCd);
		
		// add to companies in contract
		List<String> companyIdList = companyRepo.acquireAllCompany();
		
		List<String> ctgIdList = this.perInfoCtgRep.getPerInfoCtgIdList(companyIdList, categoryCd);
		if (ctgIdList == null || ctgIdList.isEmpty()) {
			return null;
		}
		this.pernfoItemDefRep.addPerInfoItemDefByCtgIdList(perInfoItemDef, ctgIdList);
		
		return perInfoItemId;
	}

	private String createNewCode(String codeLastest, String strSpecial) {
		String numberCode = String.valueOf(ITEM_CODE_DEFAUT_NUMBER + 1);
		if (codeLastest != null) {
			numberCode = String.valueOf(Integer.parseInt(codeLastest.substring(2, 7)) + 1);
		}
		for (int i = 5; i > 0; i--) {
			if (i == numberCode.length()) {
				break;
			}
			strSpecial += "0";
		}
		return strSpecial + numberCode;
	}
	
	private void validateInput(AddItemCommand addItemCommand) {
		if (CheckNameSpace.checkName(addItemCommand.getItemName())) {
			throw new BusinessException("Msg_928");
		}

		// need perInfoItemDefId = ' ' because oracle server can't query ''
		if (!this.pernfoItemDefRep.checkItemNameIsUnique(addItemCommand.getPerInfoCtgId(), addItemCommand.getItemName(),
				" ")) {
			throw new BusinessException("Msg_358");
		}

		if (addItemCommand.getSingleItem().getDataType() == 6) {
			List<Selection> selection = this.selectionRepo.getAllSelectionByCompanyId(
					AppContexts.user().companyId(), addItemCommand.getSingleItem().getSelectionItemId(),
					GeneralDate.today());
			if (selection == null || selection.size() == 0) {

				throw new BusinessException("Msg_587");
			}
		}
	}

}
