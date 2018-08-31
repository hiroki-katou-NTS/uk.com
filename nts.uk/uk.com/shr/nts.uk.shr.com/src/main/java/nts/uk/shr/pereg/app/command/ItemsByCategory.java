package nts.uk.shr.pereg.app.command;

import java.util.ArrayList;
import java.util.Arrays;
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
import java.lang.reflect.Field;

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
		
		try {
			Field fieldGrantDateItemName = commandClass.getField("grantDateItemName");
			Field fieldDeadlineDateItemName = commandClass.getField("deadlineDateItemName");
			switch (this.categoryCd) {
			case "CS00039":
				fieldGrantDateItemName.set(command , inputsMap.get("IS00409").itemName());
				fieldDeadlineDateItemName.set(command , inputsMap.get("IS00410").itemName());
				break;
			case "CS00040":
				fieldGrantDateItemName.set(command , inputsMap.get("IS00424").itemName());
				fieldDeadlineDateItemName.set(command , inputsMap.get("IS00425").itemName());
				break;
			case "CS00041":
				fieldGrantDateItemName.set(command , inputsMap.get("IS00439").itemName());
				fieldDeadlineDateItemName.set(command , inputsMap.get("IS00440").itemName());
				break;
			case "CS00042":
				fieldGrantDateItemName.set(command , inputsMap.get("IS00454").itemName());
				fieldDeadlineDateItemName.set(command , inputsMap.get("IS00455").itemName());
				break;
			case "CS00043":
				fieldGrantDateItemName.set(command , inputsMap.get("IS00469").itemName());
				fieldDeadlineDateItemName.set(command , inputsMap.get("IS00470").itemName());
				break;
			case "CS00044":
				fieldGrantDateItemName.set(command , inputsMap.get("IS00484").itemName());
				fieldDeadlineDateItemName.set(command , inputsMap.get("IS00485").itemName());
				break;
			case "CS00045":
				fieldGrantDateItemName.set(command , inputsMap.get("IS00499").itemName());
				fieldDeadlineDateItemName.set(command , inputsMap.get("IS00500").itemName());
				break;
			case "CS00046":
				fieldGrantDateItemName.set(command , inputsMap.get("IS00514").itemName());
				fieldDeadlineDateItemName.set(command , inputsMap.get("IS00515").itemName());
				break;
			case "CS00047":
				fieldGrantDateItemName.set(command , inputsMap.get("IS00529").itemName());
				fieldDeadlineDateItemName.set(command , inputsMap.get("IS00530").itemName());
				break;
			case "CS00048":
				fieldGrantDateItemName.set(command , inputsMap.get("IS00544").itemName());
				fieldDeadlineDateItemName.set(command , inputsMap.get("IS00545").itemName());
				break;
			case "CS00059":
				fieldGrantDateItemName.set(command , inputsMap.get("IS00629").itemName());
				fieldDeadlineDateItemName.set(command , inputsMap.get("IS00630").itemName());
				break;
			case "CS00060":
				fieldGrantDateItemName.set(command , inputsMap.get("IS00644").itemName());
				fieldDeadlineDateItemName.set(command , inputsMap.get("IS00645").itemName());
				break;
			case "CS00061":
				fieldGrantDateItemName.set(command , inputsMap.get("IS00659").itemName());
				fieldDeadlineDateItemName.set(command , inputsMap.get("IS00660").itemName());
				break;
			case "CS00062":
				fieldGrantDateItemName.set(command , inputsMap.get("IS00674").itemName());
				fieldDeadlineDateItemName.set(command , inputsMap.get("IS00675").itemName());
				break;
			case "CS00063":
				fieldGrantDateItemName.set(command , inputsMap.get("IS00689").itemName());
				fieldDeadlineDateItemName.set(command , inputsMap.get("IS00690").itemName());
				break;
			case "CS00064":
				fieldGrantDateItemName.set(command , inputsMap.get("IS00704").itemName());
				fieldDeadlineDateItemName.set(command , inputsMap.get("IS00705").itemName());
				break;
			case "CS00065":
				fieldGrantDateItemName.set(command , inputsMap.get("IS00719").itemName());
				fieldDeadlineDateItemName.set(command , inputsMap.get("IS00720").itemName());
				break;
			case "CS00066":
				fieldGrantDateItemName.set(command , inputsMap.get("IS00734").itemName());
				fieldDeadlineDateItemName.set(command , inputsMap.get("IS00735").itemName());
				break;
			case "CS00067":
				fieldGrantDateItemName.set(command , inputsMap.get("IS00749").itemName());
				fieldDeadlineDateItemName.set(command , inputsMap.get("IS00750").itemName());
				break;
			case "CS00068":
				fieldGrantDateItemName.set(command , inputsMap.get("IS00764").itemName());
				fieldDeadlineDateItemName.set(command , inputsMap.get("IS00765").itemName());
				break;
			case "CS00037":
				fieldGrantDateItemName.set(command , inputsMap.get("IS00385").itemName());
				fieldDeadlineDateItemName.set(command , inputsMap.get("IS00386").itemName());
				break;
			case "CS00038":
				fieldGrantDateItemName.set(command , inputsMap.get("IS00398").itemName());
				fieldDeadlineDateItemName.set(command , inputsMap.get("IS00399").itemName());
				break;
			default:
				break;
			}
			
			
		} catch (NoSuchFieldException | SecurityException | IllegalArgumentException | IllegalAccessException e) {}

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
