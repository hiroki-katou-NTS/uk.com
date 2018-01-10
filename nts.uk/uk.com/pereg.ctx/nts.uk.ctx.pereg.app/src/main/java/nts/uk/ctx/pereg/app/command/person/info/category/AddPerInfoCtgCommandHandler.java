package nts.uk.ctx.pereg.app.command.person.info.category;

import java.util.ArrayList;
import java.util.List;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.arc.error.BusinessException;
import nts.arc.error.RawErrorMessage;
import nts.arc.layer.app.command.CommandHandler;
import nts.arc.layer.app.command.CommandHandlerContext;
import nts.uk.ctx.pereg.app.command.person.info.item.AddItemCommand;
import nts.uk.ctx.pereg.app.command.person.info.item.MappingDtoToDomain;
import nts.uk.ctx.pereg.dom.person.info.category.CategoryType;
import nts.uk.ctx.pereg.dom.person.info.category.PerInfoCategoryRepositoty;
import nts.uk.ctx.pereg.dom.person.info.category.PersonInfoCategory;
import nts.uk.ctx.pereg.dom.person.info.daterangeitem.DateRangeItem;
import nts.uk.ctx.pereg.dom.person.info.item.PerInfoItemDefRepositoty;
import nts.uk.ctx.pereg.dom.person.info.item.PersonInfoItemDefinition;

@Stateless
public class AddPerInfoCtgCommandHandler extends CommandHandler<AddPerInfoCtgCommand> {

	@Inject
	private PerInfoCategoryRepositoty perInfoCtgRep;

	@Inject
	private PerInfoItemDefRepositoty pernfoItemDefRep;

	private final static String SPECIAL_CTG_CODE = "CO";
	private final static String SPECIAL_ITEM_CODE = "IO";
	private final static int ITEM_CODE_DEFAUT_NUMBER = 0;

	@Override
	protected void handle(CommandHandlerContext<AddPerInfoCtgCommand> context) {
		AddPerInfoCtgCommand perInfoCtgCommand = context.getCommand();
		// need ctgId = ' ' becase sql oracle server can't query ''
		if (perInfoCtgCommand.getCategoryName().trim().equals("")) {
			throw new BusinessException(new RawErrorMessage("Msg_928"));
		}

		if (!this.perInfoCtgRep.checkCtgNameIsUnique(PersonInfoCategory.ROOT_COMPANY_ID,
				perInfoCtgCommand.getCategoryName(), " ")) {
			throw new BusinessException(new RawErrorMessage("Msg_215"));
		}
		String contractCd = PersonInfoItemDefinition.ROOT_CONTRACT_CODE;

		String newCtgCode = createNewCode(this.perInfoCtgRep.getPerInfoCtgCodeLastest(contractCd), SPECIAL_CTG_CODE);
		List<String> companyIdList = GetListCompanyOfContract.LIST_COMPANY_OF_CONTRACT;
		AddItemCommand addItemCommand = null;

		// add PersonInfoCategory root.
		PersonInfoCategory perInfoCtg = PersonInfoCategory.createFromJavaType(PersonInfoCategory.ROOT_COMPANY_ID,
				newCtgCode, perInfoCtgCommand.getCategoryName(), perInfoCtgCommand.getCategoryType());
		this.perInfoCtgRep.addPerInfoCtgRoot(perInfoCtg, contractCd);
		// add PersonInfoCategory for list of companyId. Which have the same
		// contract code.
		this.perInfoCtgRep.addPerInfoCtgWithListCompany(perInfoCtg, contractCd, companyIdList);
		// checking Category is history information. If it isn't, exits
		// function.
		if (perInfoCtgCommand.getCategoryType() == CategoryType.SINGLEINFO.value
				|| perInfoCtgCommand.getCategoryType() == CategoryType.MULTIINFO.value) {
			return;
		}
		// mapping and Add with PersonInfoItemDefinition root is Period type
		// default.
		String newItemCodeForPeriod = createNewCode(null, SPECIAL_ITEM_CODE);
		addItemCommand = new AddItemCommand(perInfoCtg.getPersonInfoCategoryId(), newItemCodeForPeriod, null, null,
				null, 0);
		PersonInfoItemDefinition itemPeriod = MappingDtoToDomain.mappingFromDomaintoDtoForPeriod(addItemCommand);
		this.pernfoItemDefRep.addPerInfoItemDefRoot(itemPeriod, contractCd, newCtgCode);
		// mapping and Add with PersonInfoItemDefinition root is StartDate type
		// default.
		String newItemCodeStartDate = createNewCode(newItemCodeForPeriod, SPECIAL_ITEM_CODE);
		addItemCommand = new AddItemCommand(perInfoCtg.getPersonInfoCategoryId(), newItemCodeStartDate,
				newItemCodeForPeriod, null, null, 0);
		PersonInfoItemDefinition itemStartDate = MappingDtoToDomain.mappingFromDomaintoDtoForStartDate(addItemCommand);
		this.pernfoItemDefRep.addPerInfoItemDefRoot(itemStartDate, contractCd, newCtgCode);
		// mapping and Add with PersonInfoItemDefinition root is EndDate type
		// default.
		String newItemCodeEndDate = createNewCode(newItemCodeStartDate, SPECIAL_ITEM_CODE);
		addItemCommand = new AddItemCommand(perInfoCtg.getPersonInfoCategoryId(), newItemCodeEndDate,
				newItemCodeForPeriod, null, null, 0);
		PersonInfoItemDefinition itemEndDate = MappingDtoToDomain.mappingFromDomaintoDtoForEndtDate(addItemCommand);
		this.pernfoItemDefRep.addPerInfoItemDefRoot(itemEndDate, contractCd, newCtgCode);
		// add DateRangeItem root.
		this.perInfoCtgRep.addDateRangeItemRoot(DateRangeItem.createFromJavaType(perInfoCtg.getPersonInfoCategoryId(),
				itemStartDate.getPerInfoItemDefId(), itemEndDate.getPerInfoItemDefId(),
				itemPeriod.getPerInfoItemDefId()));

		// get List PerInfoCtgId.
		List<String> ctgIdList = this.perInfoCtgRep.getPerInfoCtgIdList(companyIdList, newCtgCode);
		if (ctgIdList == null || ctgIdList.isEmpty()) {
			return;
		}
		// add PersonInfoItemDefinition and DateRangeItem with list of category
		// id (ctgIdList).
		List<String> itemDefIdPeriod = this.pernfoItemDefRep.addPerInfoItemDefByCtgIdList(itemPeriod, ctgIdList);
		List<String> itemDefIdStartDates = this.pernfoItemDefRep.addPerInfoItemDefByCtgIdList(itemStartDate, ctgIdList);
		List<String> itemDefIdEndtDates = this.pernfoItemDefRep.addPerInfoItemDefByCtgIdList(itemEndDate, ctgIdList);
		List<DateRangeItem> dateRangeItems = new ArrayList<>();
		for (int i = 0, lengthList = ctgIdList.size(); i < lengthList; i++) {
			DateRangeItem dateRangeItem = DateRangeItem.createFromJavaType(ctgIdList.get(i), itemDefIdStartDates.get(i),
					itemDefIdEndtDates.get(i), itemDefIdPeriod.get(i));
			dateRangeItems.add(dateRangeItem);
		}
		this.perInfoCtgRep.addListDateRangeItem(dateRangeItems);
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

}
