package command.person.info.category;

import java.util.ArrayList;
import java.util.List;

import javax.ejb.Stateless;
import javax.inject.Inject;

import command.person.info.item.AddItemCommand;
import command.person.info.item.MappingDtoToDomain;
import nts.arc.layer.app.command.CommandHandler;
import nts.arc.layer.app.command.CommandHandlerContext;
import nts.uk.ctx.bs.person.dom.person.info.category.PerInfoCategoryRepositoty;
import nts.uk.ctx.bs.person.dom.person.info.category.PersonInfoCategory;
import nts.uk.ctx.bs.person.dom.person.info.daterangeitem.DateRangeItem;
import nts.uk.ctx.bs.person.dom.person.info.item.PernfoItemDefRepositoty;
import nts.uk.ctx.bs.person.dom.person.info.item.PersonInfoItemDefinition;
import nts.uk.shr.com.context.AppContexts;

@Stateless
public class AddPerInfoCtgCommandHandler extends CommandHandler<AddPerInfoCtgCommand> {

	@Inject
	private PerInfoCategoryRepositoty perInfoCtgRep;

	@Inject
	private PernfoItemDefRepositoty pernfoItemDefRep;

	private final static String SPECIAL_CTG_CODE = "CO";
	private final static String SPECIAL_ITEM_CODE = "IO";
	private final static int ITEM_CODE_DEFAUT_NUMBER = 0;

	@Override
	protected void handle(CommandHandlerContext<AddPerInfoCtgCommand> context) {
		AddPerInfoCtgCommand perInfoCtgCommand = context.getCommand();
		String contractCd = AppContexts.user().contractCode();
		String newCtgCode = createNewCode(perInfoCtgRep.getPerInfoCtgCodeLastest(contractCd), SPECIAL_CTG_CODE);

		List<String> companyIdList = GetListCompanyOfContract.LIST_COMPANY_OF_CONTRACT;
		List<String> ctgIdList = null;
		String newItemCode = null;
		AddItemCommand addItemCommand = null;
		List<String> items = new ArrayList<>();
		PersonInfoCategory perInfoCtg = PersonInfoCategory.createFromJavaType(PersonInfoCategory.ROOT_COMPANY_ID,
				newCtgCode, perInfoCtgCommand.getCategoryName().v(), perInfoCtgCommand.getCategoryType().value);

		this.perInfoCtgRep.addPerInfoCtgRoot(perInfoCtg, contractCd);
		this.perInfoCtgRep.addPerInfoCtgWithListCompany(perInfoCtg, contractCd, companyIdList);
		// get new ItemCode
		newItemCode = createNewCode(null, SPECIAL_ITEM_CODE);
		// mapping and Add with PersonInfoItemDefinition is StartDate type
		// default.
		
		//xem lai add Item
		addItemCommand = new AddItemCommand(perInfoCtg.getPersonInfoCategoryId(), newItemCode, newItemCode, null, 0,
				null);
		PersonInfoItemDefinition itemStartDate = MappingDtoToDomain.mappingFromDomaintoDtoForStartDate(addItemCommand);
		this.pernfoItemDefRep.addPerInfoItemDefRoot(itemStartDate, contractCd);
		ctgIdList = this.perInfoCtgRep.getPerInfoCtgIdList(PersonInfoCategory.ROOT_COMPANY_ID, newCtgCode);
		this.pernfoItemDefRep.addPerInfoItemDefByCtgIdList(itemStartDate, ctgIdList);

		// mapping and Add with PersonInfoItemDefinition is EndDate type
		// default.
		newItemCode = createNewCode(newItemCode, SPECIAL_ITEM_CODE);
		addItemCommand = new AddItemCommand(perInfoCtg.getPersonInfoCategoryId(), newItemCode, newItemCode, null, 0,
				null);
		PersonInfoItemDefinition itemEndDate = MappingDtoToDomain.mappingFromDomaintoDtoForEndtDate(addItemCommand);
		this.pernfoItemDefRep.addPerInfoItemDefRoot(itemEndDate, contractCd);
		ctgIdList = this.perInfoCtgRep.getPerInfoCtgIdList(PersonInfoCategory.ROOT_COMPANY_ID, newCtgCode);
		this.pernfoItemDefRep.addPerInfoItemDefByCtgIdList(itemEndDate, ctgIdList);

		// mapping and Add with PersonInfoItemDefinition is Period type
		// default.
		newItemCode = createNewCode(newItemCode, SPECIAL_ITEM_CODE);
		addItemCommand = new AddItemCommand(perInfoCtg.getPersonInfoCategoryId(), newItemCode, newItemCode, null, 0,
				null);
		items.add(itemStartDate.getPerInfoItemDefId());
		items.add(itemEndDate.getPerInfoItemDefId());
		PersonInfoItemDefinition itemPeriod = MappingDtoToDomain.mappingFromDomaintoDtoForPeriod(addItemCommand, items);
		this.pernfoItemDefRep.addPerInfoItemDefRoot(itemPeriod, contractCd);
		ctgIdList = this.perInfoCtgRep.getPerInfoCtgIdList(PersonInfoCategory.ROOT_COMPANY_ID, newCtgCode);
		this.pernfoItemDefRep.addPerInfoItemDefByCtgIdList(itemPeriod, ctgIdList);
		this.perInfoCtgRep.addDateRangeItem(DateRangeItem.createFromJavaType(perInfoCtg.getPersonInfoCategoryId(),
				itemStartDate.getPerInfoItemDefId(), itemEndDate.getPerInfoItemDefId(),
				itemPeriod.getPerInfoItemDefId()));

	}

	private String createNewCode(String codeLastest, String strSpecial) {
		String numberCode = String.valueOf(ITEM_CODE_DEFAUT_NUMBER + 1);
		if (codeLastest != null) {
			numberCode = String.valueOf(Integer.parseInt(codeLastest.substring(2, 7)) + 1);
		}
		for (int i = 5; i > 0; i++) {
			if (i == numberCode.length()) {
				break;
			}
			strSpecial += "0";
		}
		return strSpecial + numberCode;
	}

}
