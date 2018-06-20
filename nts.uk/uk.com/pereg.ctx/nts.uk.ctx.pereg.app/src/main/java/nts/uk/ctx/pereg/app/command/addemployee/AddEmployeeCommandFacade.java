package nts.uk.ctx.pereg.app.command.addemployee;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.gul.collection.CollectionUtil;
import nts.uk.ctx.pereg.app.command.facade.PeregCommandFacade;
import nts.uk.ctx.pereg.app.find.initsetting.item.SettingItemDto;
import nts.uk.ctx.pereg.app.find.layout.RegisterLayoutFinder;
import nts.uk.shr.pereg.app.ItemValue;
import nts.uk.shr.pereg.app.SaveDataType;
import nts.uk.shr.pereg.app.command.ItemsByCategory;
import nts.uk.shr.pereg.app.command.PeregInputContainer;

@Stateless
public class AddEmployeeCommandFacade {

	private final List<String> basicCategoriesDefinition = Arrays.asList("CS00001", "CS00002", "CS00003");

	@Inject
	private PeregCommandFacade commandFacade;

	@Inject
	private RegisterLayoutFinder layoutFinder;

	public void addNewFromInputs(String personId, String employeeId, String comHistId, List<ItemsByCategory> inputs) {

		updateBasicCategories(personId, employeeId, comHistId, inputs);

		addNoBasicCategories(personId, employeeId, inputs);

	}
	
	public List<ItemsByCategory> createData(AddEmployeeCommand command) {
		
		// add new category cardNo to input
		if(!command.getCardNo().equals("")) {
			command.getInputs().add(createCardNoCategory(command.getCardNo()));
		}
		
		
		if (command.getCreateType() == 3) {
			return command.getInputs();
		}

		List<SettingItemDto> dataServer = this.layoutFinder.getSetItems(command);

		List<String> categoryCodeList = commandFacade.getAddCategoryCodeList();
		
		dataServer.forEach(settingItem -> {
			if (!categoryCodeList.contains(settingItem.getCategoryCode())) {
				categoryCodeList.add(settingItem.getCategoryCode());
			}
		});

		List<ItemsByCategory> composedData =  categoryCodeList.stream()
				.map(categoryCode -> createItemsByCategory(categoryCode, dataServer,
						command.getCategoryData(categoryCode)))
				.filter(itemsByCategory -> itemsByCategory != null).collect(Collectors.toList());
		
		
		
		return composedData;
		
	}
	
	public ItemsByCategory createCardNoCategory(String cardNo) {
		if (!cardNo.equals("")) {
			ItemValue itemValue = new ItemValue(null, "IS00779", cardNo, SaveDataType.STRING.value);
			return new ItemsByCategory("CS00069", null, Arrays.asList(itemValue));
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

				ItemsByCategory newItemCtg = new ItemsByCategory(category.getCategoryCd(), category.getRecordId(),
						fixedItems);
				updateInputs.add(newItemCtg);
			}

		});

		PeregInputContainer updateContainer = new PeregInputContainer(personId, employeeId, updateInputs);

		this.commandFacade.update(updateContainer);

	}

	private void addNoBasicCategories(String personId, String employeeId, List<ItemsByCategory> inputs) {

		List<ItemsByCategory> noBasicCategories = inputs.stream()
				.filter(category -> !basicCategoriesDefinition.contains(category.getCategoryCd()))
				.collect(Collectors.toList());

		// call add commandFacade
		PeregInputContainer addContainer = new PeregInputContainer(personId, employeeId, noBasicCategories);

		this.commandFacade.add(addContainer);
	}
	
	

	private void addOptionalItemOfBasicCategory(List<ItemsByCategory> fixedInputs, String personId, String employeeId) {
		List<ItemsByCategory> addInputs = new ArrayList<ItemsByCategory>();

		fixedInputs.forEach(category -> {

			List<ItemValue> optionalItems = category.getItems().stream().filter(item -> item.itemCode().charAt(1) == 'O')
					.collect(Collectors.toList());

			if (!CollectionUtil.isEmpty(optionalItems)) {
				ItemsByCategory newItemCtg = new ItemsByCategory(category.getCategoryCd(), null, optionalItems);
				addInputs.add(newItemCtg);
				// add item for get recordId in commandFacade.add
				ItemsByCategory itemCtg = new ItemsByCategory(category.getCategoryCd(), category.getRecordId(), null);
				addInputs.add(itemCtg);

			}

		});

		PeregInputContainer addContainer = new PeregInputContainer(personId, employeeId, addInputs);

		this.commandFacade.add(addContainer);

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
			return new ItemsByCategory(categoryCode, null, items);
		}

	}
	
	private ItemValue convertSettingItemToItemValue(SettingItemDto settingItem) {
		String value = settingItem.getSaveData().getValue() == null ? ""
				: settingItem.getSaveData().getValue().toString();
		switch (settingItem.getDataType()) {
		case SELECTION:
		case SELECTION_BUTTON:
		case SELECTION_RADIO:
			return ItemValue.createItemValue(settingItem.getItemDefId(), settingItem.getItemCode(), value,
					settingItem.getDataType().value, settingItem.getSelectionItemRefType().value,
					settingItem.getSelectionItemRefCd());
		default:
			return ItemValue.createItemValue(settingItem.getItemDefId(), settingItem.getItemCode(), value,
					settingItem.getDataType().value, null, null);
		}

	}

}
