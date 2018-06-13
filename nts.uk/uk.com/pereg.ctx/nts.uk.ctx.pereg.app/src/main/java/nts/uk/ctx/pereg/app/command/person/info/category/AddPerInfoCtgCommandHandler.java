package nts.uk.ctx.pereg.app.command.person.info.category;

import java.util.ArrayList;
import java.util.List;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.arc.error.BusinessException;
import nts.arc.layer.app.command.CommandHandler;
import nts.arc.layer.app.command.CommandHandlerContext;
import nts.uk.ctx.pereg.app.command.person.info.item.AddItemCommand;
import nts.uk.ctx.pereg.app.command.person.info.item.MappingDtoToDomain;
import nts.uk.ctx.pereg.dom.company.ICompanyRepo;
import nts.uk.ctx.pereg.dom.person.info.category.CategoryType;
import nts.uk.ctx.pereg.dom.person.info.category.PerInfoCategoryRepositoty;
import nts.uk.ctx.pereg.dom.person.info.category.PersonInfoCategory;
import nts.uk.ctx.pereg.dom.person.info.daterangeitem.DateRangeItem;
import nts.uk.ctx.pereg.dom.person.info.item.PerInfoItemDefRepositoty;
import nts.uk.ctx.pereg.dom.person.info.item.PersonInfoItemDefinition;
import nts.uk.shr.com.context.AppContexts;

@Stateless
public class AddPerInfoCtgCommandHandler extends CommandHandler<AddPerInfoCtgCommand> {

	@Inject
	private PerInfoCategoryRepositoty perInfoCtgRep;

	@Inject
	private PerInfoItemDefRepositoty pernfoItemDefRep;
	
	@Inject
	private ICompanyRepo companyRepo;

	private final static String SPECIAL_CTG_CODE = "CO";
	private final static String SPECIAL_ITEM_CODE = "IO";
	private final static int ITEM_CODE_DEFAUT_NUMBER = 0;

	@Override
	protected void handle(CommandHandlerContext<AddPerInfoCtgCommand> context) {

		AddPerInfoCtgCommand perInfoCtgCommand = context.getCommand();
		String categoryName = perInfoCtgCommand.getCategoryName();
		int categoryType = perInfoCtgCommand.getCategoryType();

		String contractCd = AppContexts.user().contractCode();
		String zeroCompanyId = AppContexts.user().zeroCompanyIdInContract();
		int salary = AppContexts.user().roles().forPayroll() != null ? 1 : 0;
		int personnel = AppContexts.user().roles().forPersonnel() != null ? 1 : 0;
		int employee = AppContexts.user().roles().forAttendance() != null ? 1 : 0;
		
		// validate categoryName
		validateCategoryName(categoryName, zeroCompanyId);

		String newCategoryCode = this.createNewCode(this.perInfoCtgRep.getPerInfoCtgCodeLastest(contractCd),
				SPECIAL_CTG_CODE);
		
		// add PersonInfoCategory of zero-company.
		PersonInfoCategory perInfoCtg = PersonInfoCategory.createFromJavaType(zeroCompanyId, newCategoryCode, categoryName,
				categoryType, salary , employee, personnel );
		this.perInfoCtgRep.addPerInfoCtgRoot(perInfoCtg, contractCd);

		List<String> companyIdList = companyRepo.acquireAllCompany();
		// add PersonInfoCategory for list of companyId. Which have the same contract code.
		this.perInfoCtgRep.addPerInfoCtgWithListCompany(perInfoCtg, contractCd, companyIdList);
		
		// checking category is history information. If it isn't, exits function.
		if (categoryType == CategoryType.SINGLEINFO.value || categoryType == CategoryType.MULTIINFO.value) {
			return;
		}
		
		// do with history category
		addDatePeriodWithHistoryCategory(contractCd, newCategoryCode, perInfoCtg.getPersonInfoCategoryId(),
				companyIdList);
		
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

	private void validateCategoryName(String categoryName, String zeroCompanyId) {
		// need ctgId = ' ' because oracle server can't query ''
		if (CheckNameSpace.checkName(categoryName)) {
			throw new BusinessException("Msg_928");
		}

		if (!this.perInfoCtgRep.checkCtgNameIsUnique(zeroCompanyId, categoryName, " ")) {
			throw new BusinessException("Msg_215");
		}
	}
	
	private void addDatePeriodWithHistoryCategory(String contractCd, String newCategoryCode, String categoryIdOfZeroCompany,
			List<String> companyIdList) {
		// mapping and add with PersonInfoItemDefinition root is Period type
		// default.
		AddItemCommand addItemCommand = null;
		String newItemCodeForPeriod = this.createNewCode(null, SPECIAL_ITEM_CODE);
		addItemCommand = new AddItemCommand(categoryIdOfZeroCompany, newItemCodeForPeriod, null, null, null, 0);
		PersonInfoItemDefinition itemPeriod = MappingDtoToDomain.mappingFromDomaintoDtoForPeriod(addItemCommand);
		this.pernfoItemDefRep.addPerInfoItemDefRoot(itemPeriod, contractCd, newCategoryCode);
		// mapping and Add with PersonInfoItemDefinition root is StartDate type
		// default.
		String newItemCodeStartDate = createNewCode(newItemCodeForPeriod, SPECIAL_ITEM_CODE);
		addItemCommand = new AddItemCommand(categoryIdOfZeroCompany, newItemCodeStartDate, newItemCodeForPeriod, null, null, 0);
		PersonInfoItemDefinition itemStartDate = MappingDtoToDomain.mappingFromDomaintoDtoForStartDate(addItemCommand);
		this.pernfoItemDefRep.addPerInfoItemDefRoot(itemStartDate, contractCd, newCategoryCode);
		// mapping and Add with PersonInfoItemDefinition root is EndDate type
		// default.
		String newItemCodeEndDate = createNewCode(newItemCodeStartDate, SPECIAL_ITEM_CODE);
		addItemCommand = new AddItemCommand(categoryIdOfZeroCompany, newItemCodeEndDate, newItemCodeForPeriod, null, null, 0);
		PersonInfoItemDefinition itemEndDate = MappingDtoToDomain.mappingFromDomaintoDtoForEndtDate(addItemCommand);
		this.pernfoItemDefRep.addPerInfoItemDefRoot(itemEndDate, contractCd, newCategoryCode);
		// add DateRangeItem root.
		this.perInfoCtgRep.addDateRangeItemRoot(
				DateRangeItem.createFromJavaType(categoryIdOfZeroCompany, itemStartDate.getPerInfoItemDefId(),
						itemEndDate.getPerInfoItemDefId(), itemPeriod.getPerInfoItemDefId()));

		// get List PerInfoCtgId.
		List<String> ctgIdList = this.perInfoCtgRep.getPerInfoCtgIdList(companyIdList, newCategoryCode);
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

}
