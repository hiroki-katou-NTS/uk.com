package nts.uk.ctx.pereg.app.command.addemployee;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.arc.layer.app.command.CommandHandler;
import nts.arc.layer.app.command.CommandHandlerContext;
import nts.gul.text.IdentifierUtil;
import nts.uk.ctx.pereg.app.find.initsetting.item.SettingItemDto;
import nts.uk.ctx.pereg.app.find.layout.RegisterLayoutFinder;
import nts.uk.shr.pereg.app.ItemValue;
import nts.uk.shr.pereg.app.command.ItemsByCategory;
import nts.uk.shr.pereg.app.command.PeregCommandFacade;
import nts.uk.shr.pereg.app.command.PeregInputContainer;

/**
 * @author sonnlb
 *
 */
@Stateless
public class AddEmployeeCommandHandler extends CommandHandler<AddEmployeeCommand> {
	@Inject
	private RegisterLayoutFinder layoutFinder;

	@Inject
	private PeregCommandFacade commandFacade;

	@Override
	protected void handle(CommandHandlerContext<AddEmployeeCommand> context) {

		AddEmployeeCommand command = context.getCommand();

		List<SettingItemDto> dataList = this.layoutFinder.loadAllItemByCreateType(command.getCreateType(),
				command.getInitSettingId(), command.getHireDate(), command.getEmployeeCopyId());

		// merge data from client with dataList
		mergeData(dataList, command.getInputContainer().getInputs());

		String personId = IdentifierUtil.randomUniqueId();
		String employeeId = IdentifierUtil.randomUniqueId();

		// 個人基本 1st

		List<ItemsByCategory> inputs = new ArrayList<ItemsByCategory>();

		// 社員情報カテゴリデータ

		List<String> categoryCodeList = Arrays.asList("CS00001", "CS00002", "CS00003", "CS00004");

		dataList.forEach(x -> {

			if (categoryCodeList.indexOf(x.getCategoryCode()) == -1 && x.getCategoryCode().charAt(1) == 'O') {

				categoryCodeList.add(x.getCategoryCode());

			}
		});

		categoryCodeList.forEach(categoryCd -> {

			inputs.add(createNewItemsByCategoryByCtgCode(dataList, categoryCd));

		});

		PeregInputContainer inputContainer = new PeregInputContainer(personId, employeeId, inputs);

		this.commandFacade.add(inputContainer);

	}

	private void mergeData(List<SettingItemDto> dataList, List<ItemsByCategory> inputs) {

		dataList.forEach(x -> {

			x.setSaveData(SettingItemDto.createSaveDataDto(x.getSaveData().getSaveDataType().value,
					getItemValueById(inputs, x.getItemDefId())));
		});

	}

	private Object getItemValueById(List<ItemsByCategory> inputs, String itemDefId) {
		Object returnString = null;

		for (ItemsByCategory ctg : inputs) {

			Optional<ItemValue> optItem = ctg.getItems().stream().filter(x -> x.definitionId().equals(itemDefId))
					.findFirst();
			if (optItem.isPresent()) {
				returnString = optItem.get().value();
				break;
			}

		}
		return returnString;

	}

	private ItemsByCategory createNewItemsByCategoryByCtgCode(List<SettingItemDto> dataList, String categoryCd) {

		List<ItemValue> items = new ArrayList<ItemValue>();
		getAllItemInCategoryByCode(dataList, categoryCd).forEach(item -> {
			items.add(new ItemValue(item.getItemDefId(), item.getCategoryCode(), item.getValueAsString(),
					item.getSaveData().getSaveDataType().value));
		});

		return new ItemsByCategory(categoryCd, null,null, items);

	}

	private List<SettingItemDto> getAllItemInCategoryByCode(List<SettingItemDto> sourceList, String categoryCode) {
		return sourceList.stream().filter(x -> x.getCategoryCode().equals(categoryCode)).collect(Collectors.toList());
	}

}
