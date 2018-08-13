package nts.uk.shr.pereg.app.command;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.val;
import nts.gul.reflection.AnnotationUtil;
import nts.gul.reflection.ReflectionUtil;
import nts.uk.shr.pereg.app.ItemValue;
import nts.uk.shr.pereg.app.PeregEmployeeId;
import nts.uk.shr.pereg.app.PeregItem;
import nts.uk.shr.pereg.app.PeregPersonId;
import nts.uk.shr.pereg.app.PeregRecordId;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class ItemsByCategory {

	private String categoryId;

	/** category code */
	private String categoryCd;
	
	private String categoryName;
	
	private int categoryType;

	/** Record Id, but this is null when new record */
	private String recordId;
	
	/** if category will be deleted when register */
	private boolean delete;

	/** input items */
	private List<ItemValue> items;
	
	public ItemsByCategory(String categoryCd,String categoryName, String recordId, List<ItemValue> items) {
		super();
		this.categoryCd = categoryCd;
		this.categoryName = categoryName;
		this.recordId = recordId;
		this.items = items;
		this.delete = false;
	}
	
	public Object createCommandForSystemDomain(String personId, String employeeId, Class<?> commandClass) {

		val command = ReflectionUtil.newInstance(commandClass);

		// set person ID
		AnnotationUtil.getFieldAnnotated(commandClass, PeregPersonId.class).ifPresent(field -> {
			ReflectionUtil.setFieldValue(field, command, personId);
		});

		// set employee ID
		AnnotationUtil.getFieldAnnotated(commandClass, PeregEmployeeId.class).ifPresent(field -> {
			ReflectionUtil.setFieldValue(field, command, employeeId);
		});

		// set record ID
		AnnotationUtil.getFieldAnnotated(commandClass, PeregRecordId.class).ifPresent(field -> {
			ReflectionUtil.setFieldValue(field, command, this.recordId);
		});
		
		// set item values
		val inputsMap = this.createInputsMap();

		AnnotationUtil.getStreamOfFieldsAnnotated(commandClass, PeregItem.class).forEach(field -> {
			String itemCode = field.getAnnotation(PeregItem.class).value();
			val inputItem = inputsMap.get(itemCode);
			if (inputItem != null) {
				if (inputItem.value() != null && field.getType() == String.class) {
					ReflectionUtil.setFieldValue(field, command, inputItem.value().toString());
				} else {
					ReflectionUtil.setFieldValue(field, command, inputItem.value());
				}
			}
		});

		return command;
	}

	private Map<String, ItemValue> createInputsMap() {
		return this.items.stream()
				// exclude user defined
				.filter(item -> isDefinedBySystem(item.itemCode()))
				.collect(Collectors.toMap(item -> item.itemCode(), item -> item));
	}

	public List<ItemValue> collectItemsDefinedByUser() {
		return this.items.stream().filter(item -> isDefinedByUser(item.itemCode())).collect(Collectors.toList());
	}
	
	public boolean isHaveSomeSystemItems() {
		return this.getItems().stream().anyMatch(i -> i.itemCode().charAt(1) == 'S');
	}

	private static boolean isDefinedBySystem(String itemCode) {
		return !isDefinedByUser(itemCode);
	}

	private static boolean isDefinedByUser(String itemCode) {
		return itemCode.charAt(1) == 'O';
	}
	
	public ItemValue getByItemCode(String itemCode) {

		Optional<ItemValue> optItem = this.items.stream().filter(x -> x.itemCode().equals(itemCode))
				.findFirst();
		if (optItem.isPresent()) {
			return optItem.get();
		}
		return null;
	}
	
	public List<String> getItemIdList() {
		return this.items.stream().map(x -> x.definitionId()).collect(Collectors.toList());
	}

}
