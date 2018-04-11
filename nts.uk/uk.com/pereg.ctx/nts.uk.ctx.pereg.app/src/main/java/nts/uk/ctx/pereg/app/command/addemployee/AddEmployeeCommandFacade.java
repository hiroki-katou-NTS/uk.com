package nts.uk.ctx.pereg.app.command.addemployee;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.ejb.Stateless;
import javax.inject.Inject;

import org.apache.commons.lang3.math.NumberUtils;

import nts.arc.enums.EnumAdaptor;
import nts.gul.collection.CollectionUtil;
import nts.uk.ctx.pereg.app.command.facade.PeregCommandFacade;
import nts.uk.ctx.pereg.app.find.initsetting.item.SaveDataDto;
import nts.uk.ctx.pereg.app.find.initsetting.item.SettingItemDto;
import nts.uk.ctx.pereg.app.find.layout.RegisterLayoutFinder;
import nts.uk.ctx.pereg.dom.person.info.dateitem.DateType;
import nts.uk.ctx.pereg.dom.person.info.singleitem.DataTypeValue;
import nts.uk.ctx.pereg.dom.person.setting.init.item.SaveDataType;
import nts.uk.shr.pereg.app.ItemValue;
import nts.uk.shr.pereg.app.command.ItemsByCategory;
import nts.uk.shr.pereg.app.command.PeregInputContainer;

@Stateless
public class AddEmployeeCommandFacade {

	private final List<String> basicCategoriesDefinition = Arrays.asList("CS00001", "CS00002", "CS00003");

	@Inject
	private PeregCommandFacade commandFacade;

	@Inject
	private RegisterLayoutFinder layoutFinder;

	public void addNewFromInputs(String personId, String employeeId, List<ItemsByCategory> inputs) {

		updateBasicCategories(personId, employeeId, inputs);

		addNoBasicCategories(personId, employeeId, inputs);

	}
	
	public List<ItemsByCategory> createData(AddEmployeeCommand command, String personId, String employeeId,
			String comHistId) {

		List<ItemsByCategory> inputs = command.getInputs();

		// merge data from client with dataServer
		if (command.getCreateType() != 3) {

			List<SettingItemDto> dataServer = mergeData(inputs, command);

			// inputs = new ArrayList<ItemsByCategory>();
			List<String> categoryCodeList = commandFacade.getAddCategoryCodeList();
			dataServer.forEach(x -> {

				if (categoryCodeList.indexOf(x.getCategoryCode()) == -1) {

					categoryCodeList.add(x.getCategoryCode());

				}
			});

			return categoryCodeList
					.stream().map(ctgCode -> createNewItemsByCategoryCode(dataServer, ctgCode, employeeId, personId,
							comHistId, command))
					.filter(itemsByCategory -> itemsByCategory != null).collect(Collectors.toList());
		}
		return inputs.stream().map(c -> createNewItemsByCategoryCode(c, employeeId, personId, comHistId, command))
				.filter(c -> c != null).collect(Collectors.toList());

	}

	private void updateBasicCategories(String personId, String employeeId, List<ItemsByCategory> inputs) {

		List<ItemsByCategory> basicCategories = inputs.stream()
				.filter(category -> basicCategoriesDefinition.contains(category.getCategoryCd()))
				.collect(Collectors.toList());

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

	private List<SettingItemDto> mergeData(List<ItemsByCategory> inputs, AddEmployeeCommand command) {

		List<SettingItemDto> dataList = this.layoutFinder.getSetItems(command);

		dataList.forEach(x -> {

			String itemCD = x.getItemCode();
			ItemValue itemVal = getItemById(inputs, itemCD, x.getCategoryCode());
			x.setDataType(getSaveDataType(x.getDataType(), x, itemVal));
			if (itemVal != null) {
				x.setSaveData(new SaveDataDto(x.getSaveData().getSaveDataType(),
						itemVal.value() != null ? itemVal.value().toString() : ""));

				inputs.remove(itemVal);
			}
		});

		inputs.forEach(ctg -> {

			ctg.getItems().stream().forEach(item -> {
				if (!dataList.stream().filter(i -> i.getItemDefId().equals(item.definitionId())).findFirst()
						.isPresent()) {
					dataList.add(new SettingItemDto(ctg.getCategoryCd(), item.definitionId(), item.itemCode(), "", 0,
							new SaveDataDto(EnumAdaptor.valueOf(item.itemValueType().value, SaveDataType.class),
									item.value()),
							EnumAdaptor.valueOf(item.itemValueType().value, DataTypeValue.class), null, null,
							DateType.YEARMONTHDAY, ""));
				}
			});

		});

		return dataList;

	}

	private DataTypeValue getSaveDataType(DataTypeValue dataType, SettingItemDto item, ItemValue value) {

		if (dataType.equals(DataTypeValue.SELECTION) || dataType.equals(DataTypeValue.SELECTION_BUTTON)
				|| dataType.equals(DataTypeValue.SELECTION_RADIO)) {
			switch (item.getSelectionItemRefType()) {
			case ENUM:
				return DataTypeValue.NUMERIC;
			case CODE_NAME:
				return DataTypeValue.STRING;
			case DESIGNATED_MASTER:
				String itemValue = value != null ? value.value().toString() : item.getSaveData().getValue().toString();
				if (NumberUtils.isDigits(itemValue)) {
					if (String.valueOf(Integer.parseInt(itemValue)).equals(itemValue)) {
						return DataTypeValue.NUMERIC;
					} else {
						return DataTypeValue.STRING;
					}
				} else {

					return DataTypeValue.STRING;
				}
			default:
				return dataType;
			}
		} else {

			return dataType;

		}
	}

	private ItemValue getItemById(List<ItemsByCategory> inputs, String itemCode, String ctgCode) {

		for (ItemsByCategory ctg : inputs) {
			if (ctg.getCategoryCd().equals(ctgCode)) {
				Optional<ItemValue> optItem = ctg.getItems().stream().filter(x -> x.itemCode().equals(itemCode))
						.findFirst();
				if (optItem.isPresent()) {
					return optItem.get();
				}

			}
		}
		return null;

	}

	private ItemsByCategory createNewItemsByCategoryCode(List<SettingItemDto> dataList, String categoryCd,
			String employeeId, String personId, String comHistId, AddEmployeeCommand command) {

		List<ItemValue> items = new ArrayList<ItemValue>();
		getAllItemInCategoryByCode(dataList, categoryCd).forEach(item -> {
			String value = item.getSaveData().getValue() == null ? "" : item.getSaveData().getValue().toString();
			items.add(new ItemValue(item.getItemDefId(), item.getItemCode(), value, item.getDataType().value));
		});
		if (CollectionUtil.isEmpty(items)) {
			return null;
		}
		String recordId = null;

		if (categoryCd.equals("CS00001")) {

			recordId = employeeId;
			// set fixed data
			setItemValue("IS00001", items, SaveDataType.STRING, command.getEmployeeCode());
			;
		}

		if (categoryCd.equals("CS00002")) {
			recordId = personId;
			setItemValue("IS00003", items, SaveDataType.STRING, command.getEmployeeName());
		}

		if (categoryCd.equals("CS00003")) {
			recordId = comHistId;
			setItemValue("IS00020", items, SaveDataType.DATE, command.getHireDate().toString());

		}

		return new ItemsByCategory(categoryCd, recordId, items);
	}

	private ItemsByCategory createNewItemsByCategoryCode(ItemsByCategory itemByCtg, String employeeId, String personId,
			String comHistId, AddEmployeeCommand command) {

		List<ItemValue> items = itemByCtg.getItems();

		if (CollectionUtil.isEmpty(items)) {
			return null;
		}
		String recordId = null;
		String categoryCd = itemByCtg.getCategoryCd();

		if (categoryCd.equals("CS00001")) {

			recordId = employeeId;
			// set fixed data
			setItemValue("IS00001", items, SaveDataType.STRING, command.getEmployeeCode());

		}

		if (categoryCd.equals("CS00002")) {

			recordId = personId;

			setItemValue("IS00003", items, SaveDataType.STRING, command.getEmployeeName());
		}

		if (categoryCd.equals("CS00003")) {
			recordId = comHistId;

			setItemValue("IS00020", items, SaveDataType.DATE, command.getHireDate().toString());

		}

		return new ItemsByCategory(categoryCd, recordId, items);
	}

	private void setItemValue(String itemCode, List<ItemValue> items, SaveDataType dataType, String itemData) {
		Optional<ItemValue> itemValOpt = items.stream().filter(item -> item.itemCode().equals(itemCode)).findFirst();

		if (!itemValOpt.isPresent()) {
			items.add(new ItemValue(null, itemCode, itemData, dataType.value));
		}

	}

	private List<SettingItemDto> getAllItemInCategoryByCode(List<SettingItemDto> sourceList, String categoryCode) {
		return sourceList.stream().filter(x -> x.getCategoryCode().equals(categoryCode)).collect(Collectors.toList());
	}

}
