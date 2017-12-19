package nts.uk.ctx.pereg.app.command.addemployee;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.inject.Inject;
import nts.gul.collection.CollectionUtil;
import nts.uk.ctx.pereg.app.command.facade.PeregCommandFacade;
import nts.uk.ctx.pereg.app.find.initsetting.item.SettingItemDto;
import nts.uk.ctx.pereg.app.find.layout.RegisterLayoutFinder;
import nts.uk.shr.pereg.app.ItemValue;
import nts.uk.shr.pereg.app.ItemValueType;
import nts.uk.shr.pereg.app.command.ItemsByCategory;
import nts.uk.shr.pereg.app.command.PeregInputContainer;

@Stateless
public class AddEmployeeCommandFacade {

	List<String> requiredCtgList = Arrays.asList("CS00001", "CS00002", "CS00003");

	@Inject
	private PeregCommandFacade commandFacade;

	@Inject
	private RegisterLayoutFinder layoutFinder;
	// từ từ ,luong sai @@

	@TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
	public void addNewFromInputs(AddEmployeeCommand command, String personId, String employeeId, String comHistId) {

		List<ItemsByCategory> inputs = createData(command, personId, employeeId, comHistId);

		updateRequiredInputs(inputs, personId, employeeId);

		addNoRequiredInputs(inputs, personId, employeeId);

	}

	public List<ItemsByCategory> createData(AddEmployeeCommand command, String personId, String employeeId,
			String comHistId) {

		List<ItemsByCategory> inputs = command.getInputs();

		// merge data from client with dataServer
		if (command.getCreateType() == 2) {

			List<SettingItemDto> dataServer = new ArrayList<SettingItemDto>();
			mergeData(dataServer, inputs, command);

			// inputs = new ArrayList<ItemsByCategory>();
			List<String> categoryCodeList = commandFacade.getAddCategoryCodeList();
			dataServer.forEach(x -> {

				if (categoryCodeList.indexOf(x.getCategoryCode()) == -1) {

					categoryCodeList.add(x.getCategoryCode());

				}
			});

			return categoryCodeList.stream()
					.map(c -> createNewItemsByCategoryCode(dataServer, c, employeeId, personId, comHistId))
					.filter(c -> c != null).collect(Collectors.toList());
		}
		return inputs;

	}

	public void updateRequiredInputs(List<ItemsByCategory> inputs, String personId, String employeeId) {

		List<ItemsByCategory> fixedInputs = inputs.stream().filter(x -> requiredCtgList.indexOf(x.getCategoryCd()) != -1)
				.collect(Collectors.toList());

		if (!CollectionUtil.isEmpty(fixedInputs)) {

			updateRequiredSystemInputs(fixedInputs, personId, employeeId);

			addRequiredOptinalInputs(fixedInputs, personId, employeeId);

		}

	}

	private void updateRequiredSystemInputs(List<ItemsByCategory> fixedInputs, String personId, String employeeId) {
		List<ItemsByCategory> updateInputs = new ArrayList<ItemsByCategory>();

		fixedInputs.forEach(ctg -> {
			List<ItemValue> lstItem = ctg.getItems().stream().filter(item -> item.itemCode().charAt(1) == 'S')
					.collect(Collectors.toList());
			if (!CollectionUtil.isEmpty(lstItem)) {
				ItemsByCategory newItemCtg = new ItemsByCategory(ctg.getCategoryCd(), ctg.getRecordId(), lstItem);
				updateInputs.add(newItemCtg);

			}

		});

		PeregInputContainer updateContainer = new PeregInputContainer(personId, employeeId, updateInputs);

		this.commandFacade.update(updateContainer);

	}

	public void addNoRequiredInputs(List<ItemsByCategory> inputs, String personId, String employeeId) {

		inputs = inputs.stream().filter(x -> requiredCtgList.indexOf(x.getCategoryCd()) == -1)
				.collect(Collectors.toList());
		// call add commandFacade
		PeregInputContainer addContainer = new PeregInputContainer(personId, employeeId, inputs);

		this.commandFacade.add(addContainer);
	}

	private void addRequiredOptinalInputs(List<ItemsByCategory> fixedInputs, String personId, String employeeId) {
		List<ItemsByCategory> addInputs = new ArrayList<ItemsByCategory>();

		fixedInputs.forEach(ctg -> {

			List<ItemValue> lstItem = ctg.getItems().stream().filter(item -> item.itemCode().charAt(1) == 'O')
					.collect(Collectors.toList());
			if (!CollectionUtil.isEmpty(lstItem)) {
				ItemsByCategory newItemCtg = new ItemsByCategory(ctg.getCategoryCd(), ctg.getRecordId(), lstItem);
				addInputs.add(newItemCtg);

			}

		});

		PeregInputContainer addContainer = new PeregInputContainer(personId, employeeId, addInputs);

		this.commandFacade.add(addContainer);

	}

	private void mergeData(List<SettingItemDto> dataList, List<ItemsByCategory> inputs, AddEmployeeCommand command) {

		dataList = this.layoutFinder.getAllInitItemBySetId(command);

		dataList.forEach(x -> {

			if (x.getDataType() == ItemValueType.SELECTION.value) {
				if (x.getSelectionItemRefType().intValue() == 3) {
					x.setDataType(2);
				}
			}

			ItemValue itemVal = getItemById(inputs, x.getItemCode(), x.getCategoryCode());

			if (itemVal != null) {
				x.setSaveData(SettingItemDto.createSaveDataDto(x.getSaveData().getSaveDataType().value,
						itemVal.value() != null ? itemVal.value().toString() : ""));

			}
		});

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
			String employeeId, String personId, String comHistId) {

		List<ItemValue> items = new ArrayList<ItemValue>();
		getAllItemInCategoryByCode(dataList, categoryCd).forEach(item -> {
			items.add(new ItemValue(item.getItemDefId(), item.getItemCode(), item.getValueAsString(),
					item.getDataType()));
		});
		if (CollectionUtil.isEmpty(items)) {
			return null;
		}
		String recordId = null;

		if (categoryCd == "CS00001") {

			recordId = employeeId;
		}

		if (categoryCd == "CS00002") {
			recordId = personId;
		}

		if (categoryCd == "CS00003") {
			recordId = comHistId;

		}

		return new ItemsByCategory(categoryCd, recordId, items);
	}

	private List<SettingItemDto> getAllItemInCategoryByCode(List<SettingItemDto> sourceList, String categoryCode) {
		return sourceList.stream().filter(x -> x.getCategoryCode().equals(categoryCode)).collect(Collectors.toList());
	}

}
