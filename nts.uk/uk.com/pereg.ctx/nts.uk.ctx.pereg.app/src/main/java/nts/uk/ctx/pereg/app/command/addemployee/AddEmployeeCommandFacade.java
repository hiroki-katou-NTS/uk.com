package nts.uk.ctx.pereg.app.command.addemployee;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.arc.enums.EnumAdaptor;
import nts.arc.time.GeneralDate;
import nts.gul.collection.CollectionUtil;
import nts.uk.ctx.pereg.app.command.facade.PeregCommandFacade;
import nts.uk.ctx.pereg.app.find.common.ComboBoxRetrieveFactory;
import nts.uk.ctx.pereg.app.find.initsetting.item.SettingItemDto;
import nts.uk.ctx.pereg.app.find.layout.RegisterLayoutFinder;
import nts.uk.ctx.pereg.app.find.person.info.item.SelectionItemDto;
import nts.uk.ctx.pereg.dom.person.info.category.PerInfoCategoryRepositoty;
import nts.uk.ctx.pereg.dom.person.info.category.PersonEmployeeType;
import nts.uk.ctx.pereg.dom.person.info.category.PersonInfoCategory;
import nts.uk.ctx.pereg.dom.person.info.item.PerInfoItemDefRepositoty;
import nts.uk.ctx.pereg.dom.person.info.item.PersonInfoItemDefinition;
import nts.uk.ctx.pereg.dom.person.info.selectionitem.ReferenceTypes;
import nts.uk.shr.com.context.AppContexts;
import nts.uk.shr.pereg.app.ComboBoxObject;
import nts.uk.shr.pereg.app.ItemValue;
import nts.uk.shr.pereg.app.ItemValueType;
import nts.uk.shr.pereg.app.command.ItemsByCategory;
import nts.uk.shr.pereg.app.command.PeregInputContainer;

@Stateless
public class AddEmployeeCommandFacade {

	private static final List<String> basicCategoriesDefinition = Arrays.asList("CS00001", "CS00002", "CS00003");

	@Inject
	private PeregCommandFacade commandFacade;
	@Inject
	private RegisterLayoutFinder layoutFinder;
	@Inject
	private PerInfoCategoryRepositoty cateRepo;
	@Inject
	private ComboBoxRetrieveFactory comboboxFactory;
	@Inject
	private PerInfoItemDefRepositoty perInfoItemRepo;
	
	

	public void addNewFromInputs(String personId, String employeeId, String comHistId, List<ItemsByCategory> inputs) {

		updateBasicCategories(personId, employeeId, comHistId, inputs);

		addNoBasicCategories(personId, employeeId, inputs);

	}
	
	public List<ItemsByCategory> createData(AddEmployeeCommand command) {
		
		// add new category cardNo to input
		if(!command.getCardNo().equals("")) {
			command.getInputs().add(createCardNoCategory(command.getCardNo()));
		}
		
		// trường hợp phi thẳng vào layout
		if (command.getCreateType() == 3) {
			return command.getInputs();
		}

		List<SettingItemDto> dataServer = this.layoutFinder.getSetItems(command , true);

		List<String> categoryCodeList = commandFacade.getAddCategoryCodeList();
		
		dataServer.forEach(settingItem -> {
			if(settingItem.getCategoryCode().equals("CS00001") && settingItem.getItemCode().equals("IS00001")) {
				settingItem.getSaveData().setValue(command.getEmployeeCode());
				
			}
			if (!categoryCodeList.contains(settingItem.getCategoryCode())) {
				categoryCodeList.add(settingItem.getCategoryCode());
			}
		});

		List<ItemsByCategory> composedData =  categoryCodeList.stream()
				.map(categoryCode -> createItemsByCategory(categoryCode, dataServer,
						command.getCategoryData(categoryCode)))
				.filter(itemsByCategory -> itemsByCategory != null).collect(Collectors.toList());
		
		// fix bug 100117
		// fix bug trong trường hợp coppy category workingCon2 nhưng ở layout không có
		// category này.
		Optional<ItemsByCategory> ctgWorkingCod2_Opt = composedData.stream().filter(ctg -> ctg.getCategoryCd().equals("CS00070")).findFirst();
		if (ctgWorkingCod2_Opt.isPresent()) {
			Optional<ItemsByCategory> ctgWorkingCod1_Opt = composedData.stream().filter(ctg -> ctg.getCategoryCd().equals("CS00020")).findFirst();
			if (ctgWorkingCod1_Opt.isPresent()) {
				composedData.remove(ctgWorkingCod2_Opt.get());
				composedData.stream().filter(ctg -> ctg.getCategoryCd().equals("CS00020")).findFirst().get().getItems().addAll(ctgWorkingCod2_Opt.get().getItems());
			}
		}
		
		return composedData;
		
	}
	
	public ItemsByCategory createCardNoCategory(String cardNo) {
		if (!cardNo.equals("")) {
			Optional<PersonInfoItemDefinition> itemdfOpt = perInfoItemRepo.getPerInfoItemDefByCtgCdItemCdCid("CS00069", "IS00779", AppContexts.user().companyId(), AppContexts.user().contractCode());
			ItemValue itemCardNo = null;
			if(itemdfOpt.isPresent()) {
				PersonInfoItemDefinition itemDf = itemdfOpt.get();
				itemCardNo = new ItemValue(itemDf.getPerInfoItemDefId(), itemDf.getItemCode().toString(), itemDf.getItemName().toString(), cardNo, cardNo, null, null, ItemValueType.STRING.value, ItemValueType.STRING.value);
			}else {
				itemCardNo = new ItemValue(null, "IS00779","カードNo", cardNo, cardNo ,"","",ItemValueType.STRING.value, ItemValueType.STRING.value);
			}
			Optional<PersonInfoCategory> ctgFromServer = cateRepo.getPerInfoCategoryByCtgCD("CS00069" , AppContexts.user().companyId());
			if(ctgFromServer.isPresent()) {
				return new ItemsByCategory(ctgFromServer.get().getPersonInfoCategoryId(),
						ctgFromServer.get().getCategoryCode().v(),
						ctgFromServer.get().getCategoryName().v(),
						0, 
						null,
						false,
						Arrays.asList(itemCardNo));
			} else {
				return new ItemsByCategory(ctgFromServer.get().getPersonInfoCategoryId(),
						"CS00069",
						"",
						0, 
						null,
						false,
						Arrays.asList(itemCardNo));
			}
		}
		return null;
	}
		
	private void updateBasicCategories(String personId, String employeeId, String comHistId,  List<ItemsByCategory> inputs) {

		List<ItemsByCategory> basicCategories = inputs.stream()
				.filter(category -> basicCategoriesDefinition.contains(category.getCategoryCd()))
				.collect(Collectors.toList());
		
		// set recordId(employeeId) for category CS00001
		Optional<ItemsByCategory> employeeInfoCategory = inputs.stream()
				.filter(category -> category.getCategoryCd().equals("CS00001")).findFirst();
		if (employeeInfoCategory.isPresent()) {
			employeeInfoCategory.get().setRecordId(employeeId);
		}

		// set recordId(personId) for category CS00002
		Optional<ItemsByCategory> personCategory = inputs.stream()
				.filter(category -> category.getCategoryCd().equals("CS00002")).findFirst();
		if (personCategory.isPresent()) {
			personCategory.get().setRecordId(personId);
		}

		// set recordId(historyId) for category CS00003
		Optional<ItemsByCategory> affComHistCategory = inputs.stream()
				.filter(category -> category.getCategoryCd().equals("CS00003")).findFirst();
		if (affComHistCategory.isPresent()) {
			affComHistCategory.get().setRecordId(comHistId);
		}

		if (!CollectionUtil.isEmpty(basicCategories)) {

			updateFixItemOfBasicCategory(basicCategories, personId, employeeId);

			addOptionalItemOfBasicCategory(basicCategories, personId, employeeId);

		}

	}

	private void updateFixItemOfBasicCategory(List<ItemsByCategory> basicCategories, String personId,
			String employeeId) {
		List<ItemsByCategory> updateInputs = new ArrayList<ItemsByCategory>();

		basicCategories.forEach(category -> {
			List<ItemValue> fixedItems = category.getItems().stream().filter(item -> item.itemCode().charAt(1) == 'S')
					.collect(Collectors.toList());

			if (!CollectionUtil.isEmpty(fixedItems)) {

				ItemsByCategory newItemCtg = new ItemsByCategory(category.getCategoryCd(),category.getCategoryName(), category.getRecordId(),
						fixedItems);
				updateInputs.add(newItemCtg);
			}

		});

		PeregInputContainer updateContainer = new PeregInputContainer(personId, employeeId, updateInputs);

		this.commandFacade.updateForCPS002(updateContainer);

	}

	private void addNoBasicCategories(String personId, String employeeId, List<ItemsByCategory> inputs) {

		List<ItemsByCategory> noBasicCategories = inputs.stream()
				.filter(category -> !basicCategoriesDefinition.contains(category.getCategoryCd()))
				.collect(Collectors.toList());

		// call add commandFacade
		PeregInputContainer addContainer = new PeregInputContainer(personId, employeeId, noBasicCategories);

		this.commandFacade.addForCPS002(addContainer, null);
	}
	
	

	private void addOptionalItemOfBasicCategory(List<ItemsByCategory> fixedInputs, String personId, String employeeId) {
		List<ItemsByCategory> addInputs = new ArrayList<ItemsByCategory>();

		fixedInputs.forEach(category -> {

			List<ItemValue> optionalItems = category.getItems().stream().filter(item -> item.itemCode().charAt(1) == 'O')
					.collect(Collectors.toList());

			if (!CollectionUtil.isEmpty(optionalItems)) {
				ItemsByCategory newItemCtg = new ItemsByCategory(category.getCategoryCd(),category.getCategoryName(), null, optionalItems);
				addInputs.add(newItemCtg);
				// add item for get recordId in commandFacade.add
				ItemsByCategory itemCtg = new ItemsByCategory(category.getCategoryCd(),category.getCategoryName(), category.getRecordId(), null);
				addInputs.add(itemCtg);

			}

		});

		PeregInputContainer addContainer = new PeregInputContainer(personId, employeeId, addInputs);

		this.commandFacade.addForCPS002(addContainer, null);

	}

	private ItemsByCategory createItemsByCategory(String categoryCode, List<SettingItemDto> dataServer,
			Optional<ItemsByCategory> itemByCategoryOpt) {
		List<SettingItemDto> filterDataServer = dataServer.stream()
				.filter(x -> x.getCategoryCode().equals(categoryCode)).collect(Collectors.toList());
		if (itemByCategoryOpt.isPresent()) {
			// merge data from UI and Server
			ItemsByCategory itemByCategory = itemByCategoryOpt.get();
			List<String> itemIdsFromUI = itemByCategory.getItemIdList();
			
			List<ItemValue> itemFromServer = filterDataServer.stream()
					.filter(x -> !itemIdsFromUI.contains(x.getItemDefId()))
					.map(settingItem -> convertSettingItemToItemValue(settingItem))
					.collect(Collectors.toList());
			itemByCategory.getItems().addAll(itemFromServer);
			
			return itemByCategory;
		} else {
			// create ItemByCategory from data of Server
			if (CollectionUtil.isEmpty(filterDataServer)) {
				return null;
			}
			List<ItemValue> items = filterDataServer.stream()
					.map(settingItem -> convertSettingItemToItemValue(settingItem))
					.collect(Collectors.toList());
			Optional<PersonInfoCategory> ctgFromServer = cateRepo.getPerInfoCategoryByCtgCD(categoryCode , AppContexts.user().companyId());
			if(ctgFromServer.isPresent()) {
				PersonInfoCategory ctg = ctgFromServer.get();
				 return new ItemsByCategory(ctg.getPersonInfoCategoryId(), ctg.getCategoryCode().v(), ctg.getCategoryName().v(), 0, null, false, items);
			}else {
				return new ItemsByCategory("", categoryCode, "", 0, null, false, items);
			}
		}
	}
	
	private ItemValue convertSettingItemToItemValue(SettingItemDto settingItem) {
		String value = settingItem.getSaveData().getValue() == null ? ""
				: settingItem.getSaveData().getValue().toString();
		String text = "";
		switch (settingItem.getDataType()) {
		case SELECTION:
		case SELECTION_BUTTON:
		case SELECTION_RADIO:
			
			SelectionItemDto selectionItemDto = null;
			ReferenceTypes refenceType = EnumAdaptor.valueOf(settingItem.getSelectionItemRefType().value, ReferenceTypes.class);
			switch (refenceType) {
			case ENUM:
				selectionItemDto = SelectionItemDto.createEnumRefDto(settingItem.getSelectionItemRefCd(),
						settingItem.getSelectionItemRefType().value);
				break;
			case CODE_NAME:
				selectionItemDto = SelectionItemDto.createCodeNameRefDto(settingItem.getSelectionItemRefCd(),
						settingItem.getSelectionItemRefType().value);
				break;
			case DESIGNATED_MASTER:
				selectionItemDto = SelectionItemDto.createMasterRefDto(settingItem.getSelectionItemRefCd(),
						settingItem.getSelectionItemRefType().value);
				break;
			}
			List<ComboBoxObject> comboboxs =  this.comboboxFactory.getComboBox(selectionItemDto, AppContexts.user().employeeId(), GeneralDate.today(),
					true, PersonEmployeeType.EMPLOYEE, true, settingItem.getCategoryCode(),null);
			
			if(!comboboxs.isEmpty()) {
				Optional<ComboBoxObject> opt = comboboxs.stream().filter(i -> i.getOptionValue().equals(value)).findFirst();
				if(opt.isPresent()) text = opt.get().getOptionText(); 
			}
			
			return ItemValue.createItemValue(settingItem.getItemDefId(), settingItem.getItemCode(),settingItem.getItemName(), value,text,
					settingItem.getDataType().value, settingItem.getSelectionItemRefType().value,
					settingItem.getSelectionItemRefCd());
		default:
			return ItemValue.createItemValue(settingItem.getItemDefId(), settingItem.getItemCode(),settingItem.getItemName(), value,text,
					settingItem.getDataType().value, null, null);
		}

	}

}
